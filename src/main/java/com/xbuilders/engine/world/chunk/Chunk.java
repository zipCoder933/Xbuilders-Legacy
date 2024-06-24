// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.player.UserControlledPlayer;

import com.xbuilders.engine.utils.ErrorHandler;

import java.io.IOException;
import java.io.File;
import java.util.Arrays;

import com.xbuilders.engine.VoxelGame;

import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.terrain.Terrain;
import org.joml.Vector3i;

public class Chunk {

    public ChunkCoords position;
    public static final int WIDTH = SubChunk.WIDTH;
    public static final int SUB_CHUNK_QUANTITY = 16;
    public static final int HEIGHT = SubChunk.WIDTH * SUB_CHUNK_QUANTITY;

    // Generation status (keep these as separate variables for now)
    public boolean terrainLoaded;
    public boolean lightmapInitialized;
    public boolean meshesGenerated;

    public AABB aabb;
    public SubChunk[] subChunks;

    private final PointerHandler pointerHandler;
    private boolean modifiedByUser;
    private boolean needsSaving;

    public PointerHandler getPointerHandler() {
        return this.pointerHandler;
    }

    public boolean isModifiedByUser() {
        return this.modifiedByUser;
    }

    public ChunkCoords getPosition() {
        return this.position;
    }

    public boolean needsSaving() {
        return this.needsSaving;
    }

    public void markAsNeedsSaving() {
        this.needsSaving = true;
    }

    public SubChunk[] getSubChunks() {
        return this.subChunks;
    }

    public static AABB makeNewChunkAABB(final ChunkCoords pos) {
        return new AABB().setPosAndSize((float) (pos.x * SubChunk.WIDTH), 0.0f, (float) (pos.z * SubChunk.WIDTH),
                SubChunk.WIDTH, HEIGHT, SubChunk.WIDTH);
    }

    public static void setChunkAABB(final AABB box, final ChunkCoords pos) {
        box.setPosAndSize((float) (pos.x * SubChunk.WIDTH), 0.0f, (float) (pos.z * SubChunk.WIDTH), SubChunk.WIDTH,
                HEIGHT, SubChunk.WIDTH);
    }

    public Chunk(final PointerHandler ph) {
        this.lightmapInitialized = false;
        this.meshesGenerated = false;
        this.modifiedByUser = false;
        this.needsSaving = false;
        this.pointerHandler = ph;
        this.aabb = new AABB();
        this.subChunks = new SubChunk[SUB_CHUNK_QUANTITY];
        for (int i = 0; i < SUB_CHUNK_QUANTITY; ++i) {
            this.subChunks[i] = new SubChunk(this);
        }
    }

    public ChunkCoords init(final ChunkCoords coords) {
        // if (this.generated = null) {
        // System.out.println("Initializing " + this.position);
        // }

        this.position = new ChunkCoords(coords);
        this.modifiedByUser = false;
        this.needsSaving = false;
        terrainLoaded = false;
        this.lightmapInitialized = false;
        setChunkAABB(this.aabb, coords);
        for (int y = 0; y < SUB_CHUNK_QUANTITY; ++y) {
            this.subChunks[y].init(coords.x, y, coords.z);
        }
        for (int i = 0; i < 8; ++i) {// Reset neighbors TODO: we could replace this with cacheNeighbors
            this.neighbors[i] = null;
        }
        this.meshesGenerated = false;
        return this.position;
    }

    public boolean inBounds(final int x, final int y, final int z) {
        return x < SubChunk.WIDTH && x >= 0 && y < HEIGHT && y >= 0 && z < SubChunk.WIDTH && z >= 0;
    }

    final Chunk[] neighbors = new Chunk[8];

    public void cacheNeighbors() {
        neighbors[0] = addChunkToNCList(this.position.x + 1, this.position.z);
        neighbors[1] = addChunkToNCList(this.position.x - 1, this.position.z);
        neighbors[2] = addChunkToNCList(this.position.x, this.position.z + 1);
        neighbors[3] = addChunkToNCList(this.position.x, this.position.z - 1); // The first 4 indicies are facing
                                                                               // neighbors
        neighbors[4] = addChunkToNCList(this.position.x - 1, this.position.z - 1);
        neighbors[5] = addChunkToNCList(this.position.x + 1, this.position.z + 1);
        neighbors[6] = addChunkToNCList(this.position.x + 1, this.position.z - 1);
        neighbors[7] = addChunkToNCList(this.position.x - 1, this.position.z + 1);
        // System.out.println("Caching neighbors "+ Arrays.toString(neighbors));
    }

    private Chunk addChunkToNCList(final int x, final int z) {
        final ChunkCoords coords = new ChunkCoords(this.position.x + x, this.position.z + z);
        return VoxelGame.getWorld().getChunk(coords);
    }

    public boolean allNeighborsLoaded() {
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] == null || !neighbors[i].terrainLoaded) {
                return false;
            }
        }
        return true;
    }

    public boolean allFacingNeighborsLoaded() {
        for (int i = 0; i < 4; i++) {
            if (neighbors[i] == null || !neighbors[i].terrainLoaded) {
                return false;
            }
        }
        return true;
    }

    public void markAsModifiedByUser() {
        this.modifiedByUser = true;
    }

    public synchronized void save(final File f) throws IOException {
        try {
            ChunkSavingLoading.writeChunkToFile(this, f);
            this.needsSaving = false;
        } catch (Exception e2) {
            ErrorHandler.handleFatalError("Error", "Unable to save chunk (" + this.toString() + ").", e2);
        }
    }

    public void load(WorldInfo infoFile, Terrain terrain) {
        try {
            final File file = World.chunkFile(infoFile, position);
            if (file.exists()) {
                ChunkSavingLoading.readChunkFromFile(this, file);
            } else {
                terrain.createTerrainOnChunk(this);
            }
            terrainLoaded = true;
            markAsModifiedByUser();
        } catch (Exception e) {
            ErrorHandler.handleFatalError("Error", "Unable to load chunk (" + this.toString() + ").", e);
        }
    }

    public void update(final int x, final int chunkLocation, final int blockLocation, final int z) {
        this.markAsNeedsSaving();
        this.markChunksAsNeedsRegenerating(chunkLocation, x, blockLocation, z);
    }

    public void markChunksAsNeedsRegenerating(final int chunkYLoc, final int blockX, final int blockY,
            final int blockZ) {
        // System.out.println("\nREGEN \t " + blockX + "," + blockY + "," + blockZ);
        if (blockY == 15 && chunkYLoc + 1 < this.subChunks.length) {
            this.subChunks[chunkYLoc + 1].needsRegenerating = true;
        } else if (blockY == 0 && chunkYLoc - 1 > 0) {
            this.subChunks[chunkYLoc - 1].needsRegenerating = true;
        }

        if (blockX == 15) {
            final SubChunk neighbor = VoxelGame.getWorld()
                    .getSubChunk(new Vector3i(this.position.x + 1, chunkYLoc, this.position.z));
            if (neighbor != null) {
                // System.out.println("Regenerating " + neighbor.toString());
                neighbor.needsRegenerating = true;
            }
        } else if (blockX == 0) {
            final SubChunk neighbor = VoxelGame.getWorld()
                    .getSubChunk(new Vector3i(this.position.x - 1, chunkYLoc, this.position.z));
            if (neighbor != null) {
                // System.out.println("Regenerating " + neighbor.toString());
                neighbor.needsRegenerating = true;
            }
        }
        if (blockZ == 15) {
            final SubChunk neighbor = VoxelGame.getWorld()
                    .getSubChunk(new Vector3i(this.position.x, chunkYLoc, this.position.z + 1));
            if (neighbor != null) {
                // System.out.println("Regenerating " + neighbor.toString());
                neighbor.needsRegenerating = true;
            }
        } else if (blockZ == 0) {
            final SubChunk neighbor = VoxelGame.getWorld()
                    .getSubChunk(new Vector3i(this.position.x, chunkYLoc, this.position.z - 1));
            if (neighbor != null) {
                // System.out.println("Regenerating " + neighbor.toString());
                neighbor.needsRegenerating = true;
            }
        }
        this.subChunks[chunkYLoc].needsRegenerating = true;
    }
    // </editor-fold>

    long lastTick = 0;

    public void drawUpdate() {
        // checkInFrustum(VoxelGame.getPlayer());
        if (!this.meshesGenerated && terrainLoaded) {
            // if (System.currentTimeMillis() - lastTick > 1000) { //TODO: only generate a
            // mesh if it has all facing neighbors generated
            // lastTick = System.currentTimeMillis();
            // cacheNeighbors();
            // }
            // allFacingNeighborsLoaded()
            new Thread(() -> {
                generateInitialMeshes();
            }).start();
        }
    }

    public void generateInitialMeshes() {
        for (int i = 0; i < this.subChunks.length; ++i) {
            this.subChunks[i].generateMesh();
            this.subChunks[i].generateStaticEntityMesh();
        }
        this.meshesGenerated = true;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj.getClass() == Chunk.class) {
            final Chunk otherChunk = (Chunk) obj;
            return this.getPosition().equals(otherChunk.getPosition());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.position.hashCode();
    }

    public boolean checkInFrustum(final UserControlledPlayer player) {
        inFrustum = player.camera.frustum.isAABBInside(this.aabb);
        return inFrustum;
    }

    public boolean inFrustum;

    @Override
    public String toString() {
        return this.getPosition().toString();
    }

    public void setLightmapInitialized(final boolean unparseBool) {
        for (final SubChunk sc : this.getSubChunks()) {
            sc.getLightMap().initialized = unparseBool;
        }
    }

    public boolean inSLM() {
        synchronized (ShaderLightMap.lightmapInitializationLock) {
            return this.getSubChunks()[0].getLightMap().inSLM();
        }
    }

}
