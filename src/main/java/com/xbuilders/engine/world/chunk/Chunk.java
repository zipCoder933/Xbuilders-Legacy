// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.player.UserControlledPlayer;

import java.util.Objects;

import com.xbuilders.engine.utils.ErrorHandler;

import java.io.IOException;
import java.io.File;

import com.xbuilders.engine.VoxelGame;

import java.util.ArrayList;
import java.util.HashMap;

import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import org.joml.Vector3i;

public class Chunk {

    public ChunkCoords position;
    public static final int WIDTH = SubChunk.WIDTH;
    public static final int SUB_CHUNK_QUANTITY = 16;
    public static final int HEIGHT = SubChunk.WIDTH * SUB_CHUNK_QUANTITY;

    public boolean lightmapInit;
    public AABB aabb;
    public SubChunk[] subChunks;
    private boolean generated;
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

    public boolean hasGeneratedMeshes() {
        return this.generated;
    }

    public Chunk(final PointerHandler ph) {
        this.lightmapInit = false;
        this.generated = false;
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
        this.lightmapInit = false;
        setChunkAABB(this.aabb, coords);
        for (int y = 0; y < SUB_CHUNK_QUANTITY; ++y) {
            this.subChunks[y].init(coords.x, y, coords.z);
        }
        this.generated = false;
        return this.position;
    }

    public boolean inBounds(final int x, final int y, final int z) {
        return x < SubChunk.WIDTH && x >= 0 && y < HEIGHT && y >= 0 && z < SubChunk.WIDTH && z >= 0;
    }

    public boolean isSurroundedByChunks() {
        return this.isSurroundedByChunks(null);
    }

    public ArrayList<Chunk> listNeighboringChunks() {
        final ArrayList<Chunk> list = new ArrayList<Chunk>();
        this.addChunkToNCList(list, this.position.x + 1, this.position.z);
        this.addChunkToNCList(list, this.position.x - 1, this.position.z);
        this.addChunkToNCList(list, this.position.x, this.position.z + 1);
        this.addChunkToNCList(list, this.position.x, this.position.z - 1);
        this.addChunkToNCList(list, this.position.x - 1, this.position.z - 1);
        this.addChunkToNCList(list, this.position.x + 1, this.position.z + 1);
        this.addChunkToNCList(list, this.position.x + 1, this.position.z - 1);
        this.addChunkToNCList(list, this.position.x - 1, this.position.z + 1);
        return list;
    }

    private void addChunkToNCList(final ArrayList<Chunk> list, final int x, final int z) {
        final ChunkCoords coords = new ChunkCoords(this.position.x + x, this.position.z + z);
        if (this.pointerHandler.getWorld().hasChunk(coords)) {
            final Chunk chunk = this.pointerHandler.getWorld().getChunk(coords);
            list.add(chunk);
        }
    }

    public boolean isSurroundedByChunks(final HashMap<ChunkCoords, Chunk> tempChunks) {
        for (int x = -1; x < 2; ++x) {
            for (int z = -1; z < 2; ++z) {
                final ChunkCoords coords = new ChunkCoords(this.getPosition().x + x, this.getPosition().z + z);
                if (tempChunks == null) {
                    if (!VoxelGame.getWorld().hasChunk(coords)) {
                        return false;
                    }
                } else if (!tempChunks.containsKey(coords) && !VoxelGame.getWorld().hasChunk(coords)) {
                    return false;
                }
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
        } catch (IOException e) {
            ErrorHandler.handleFatalError("Error", "The chunk was unable to load.", e);
        } catch (Exception e2) {
            ErrorHandler.handleFatalError("Error", "The chunk was unable to load.", e2);
        }
    }

    public synchronized void load(final File f) {
        try {
            ChunkSavingLoading.readChunkFromFile(this, f);
            markAsModifiedByUser();
        } catch (IOException e) {
            ErrorHandler.handleFatalError("Error", "The chunk was unable to load.", e);
        } catch (Exception e2) {
            ErrorHandler.handleFatalError("Error", "The chunk was unable to load.", e2);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="chunk updating">

    public void update(final int x, final int y, final int z) {
        final int chunkLocation = WCCi.chunkDiv(y);
        final int blockLocation = MathUtils.positiveMod(y, SubChunk.WIDTH);
        update(x, chunkLocation, blockLocation, z);
    }

    public void update(final int x, final int chunkLocation, final int blockLocation, final int z) {
        this.markAsNeedsSaving();
        if (this.hasGeneratedMeshes()) {
            this.markChunksAsNeedsRegenerating(chunkLocation, x, blockLocation, z);
        }
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

    public void generateInitialMeshes() {
        for (int i = 0; i < this.subChunks.length; ++i) {
            this.subChunks[i].generateMesh();
            this.subChunks[i].generateStaticEntityMesh();
        }
        this.generated = true;
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
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.position);
        return hash;
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
