// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.player.UserControlledPlayer;

import com.xbuilders.engine.utils.ErrorHandler;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;

import com.xbuilders.engine.VoxelGame;

import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.Terrain;
import org.joml.Vector3i;

public class Chunk {

    public ChunkCoords position;
    public static final int WIDTH = SubChunk.WIDTH;
    public static final int SUB_CHUNK_QUANTITY = 16;
    public static final int HEIGHT = SubChunk.WIDTH * SUB_CHUNK_QUANTITY;

    // Generation status (keep these as separate variables for now)
    public boolean gen_terrainGenerated;
    public boolean gen_lightmapGenerated;
    public boolean gen_meshesGenerated;

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
        this.gen_lightmapGenerated = false;
        this.gen_meshesGenerated = false;
        this.modifiedByUser = false;
        this.needsSaving = false;
        this.pointerHandler = ph;
        this.aabb = new AABB();
        this.subChunks = new SubChunk[SUB_CHUNK_QUANTITY];
        neighbors = new NeighborCollection(this);
        for (int i = 0; i < SUB_CHUNK_QUANTITY; ++i) {
            this.subChunks[i] = new SubChunk(this);
        }
    }

    public NeighborCollection neighbors;

    public ChunkCoords init(final ChunkCoords coords) {
        // if (this.generated = null) {
        // System.out.println("Initializing " + this.position);
        // }

        this.position = new ChunkCoords(coords);
        this.modifiedByUser = false;
        this.needsSaving = false;
        gen_terrainGenerated = false;
        this.gen_lightmapGenerated = false;
        setChunkAABB(this.aabb, coords);
        for (int y = 0; y < SUB_CHUNK_QUANTITY; ++y) {
            this.subChunks[y].init(coords.x, y, coords.z);
        }
        neighbors.reset();
        this.gen_meshesGenerated = false;
        return this.position;
    }

    public boolean inBounds(final int x, final int y, final int z) {
        return x < SubChunk.WIDTH && x >= 0 && y < HEIGHT && y >= 0 && z < SubChunk.WIDTH && z >= 0;
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

    public void load(WorldInfo infoFile, Terrain terrain, HashMap<Vector3i, FutureChunk> futureChunks) {
        try {
            final File file = World.chunkFile(infoFile, position);
            if (file.exists()) {
                ChunkSavingLoading.readChunkFromFile(this, file);
            } else {
                terrain.createTerrainOnChunk(this);
            }

            //Set future subchunks
            for (SubChunk sc : this.getSubChunks()) {
                if (futureChunks.containsKey(sc.position)) {
                    futureChunks.get(sc.position).setBlocksInChunk(sc);
                    futureChunks.remove(sc.position);
                }
            }

            gen_terrainGenerated = true;
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
    public boolean meshGen_LoadedNeighbors;

    public void drawUpdate() {
        /**
         * Waiting for all neighbors to be loaded before generating meshes causes the mesh generation to slow down
         * Not only that, but it might cause additional memory overhead, maybe?
         * The only other solution at this point is just to load the mesh of edges of chunks that havent been loaded yet
         */
        if (!gen_meshesGenerated && gen_terrainGenerated) {
            if (neighbors.allFacingNeighborsLoaded()) {
                new Thread(() -> {
                    generateInitialMeshes();
                }).start();
            } else {
                if (System.currentTimeMillis() - lastTick > 500) {
                    neighbors.cacheAllFacingNeighbors();
                }
            }
        }
    }


    public void generateInitialMeshes() {
        for (int i = 0; i < this.subChunks.length; ++i) {
            this.subChunks[i].generateMesh();
            this.subChunks[i].generateStaticEntityMesh();
        }
        this.gen_meshesGenerated = true;
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

    public boolean checkInFrustum() {
        inFrustum = VoxelGame.getPlayer().camera.frustum.isAABBInside(this.aabb);
        return inFrustum;
    }

    public boolean inFrustum;

    @Override
    public String toString() {
        return this.getPosition().toString();
    }

    public void setGen_lightmapGenerated(final boolean unparseBool) {
        for (final SubChunk sc : this.getSubChunks()) {
            sc.lightMap.initialized = unparseBool;
        }
    }

    public boolean inSLM() {
        synchronized (ShaderLightMap.lightmapInitializationLock) {
            SubChunk subChunk = this.getSubChunks()[0];
            return subChunk.lightMap.inSLM();
        }
    }

    public String genStatus() {
        return "mesh=" + (gen_meshesGenerated ? 1 : 0) + " light=" + (gen_lightmapGenerated ? 1 : 0) + " load=" + (gen_terrainGenerated ? 1 : 0);
    }
}
