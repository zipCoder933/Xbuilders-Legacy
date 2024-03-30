// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk.wcc;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import static com.xbuilders.engine.utils.math.MathUtils.positiveMod;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.chunk.SubChunk;
import java.nio.IntBuffer;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryStack;

public class WCCi {

    /**
     * The constructor is merely to specify HOW to store the data, and set is
     * what the data actually is
     */
    public final Vector3i subChunk;
    public final Vector3i subChunkVoxel;

    public WCCi() {
        this.subChunk = new Vector3i();
        this.subChunkVoxel = new Vector3i();
    }

    public WCCi(IntBuffer buff1, IntBuffer buff2) {
        this.subChunk = new Vector3i(buff1);
        this.subChunkVoxel = new Vector3i(buff2);
    }

    public WCCi(MemoryStack stack) {
        this.subChunk = new Vector3i(stack.mallocInt(3));
        this.subChunkVoxel = new Vector3i(stack.mallocInt(3));
    }

    public WCCi(final Vector3i chunk, final Vector3i block) {
        this.subChunk = chunk;
        this.subChunkVoxel = block;
    }

    public WCCi set(Vector3i vec) {
        return set(vec.x, vec.y, vec.z);
    }

    public WCCi set(final int worldX, final int worldY, final int worldZ) {
        int blockX = positiveMod(worldX, SubChunk.WIDTH);
        int blockY = positiveMod(worldY, SubChunk.WIDTH);
        int blockZ = positiveMod(worldZ, SubChunk.WIDTH);

        this.subChunkVoxel.set(blockX, blockY, blockZ);

        int chunkX = chunkDiv(worldX);
        int chunkY = chunkDiv(worldY);
        int chunkZ = chunkDiv(worldZ);

        this.subChunk.set(chunkX, chunkY, chunkZ);
        return this;
    }

    @Override
    public String toString() {
        return "WCC{chunk=" + MiscUtils.printVector(subChunk)
                + ", voxel=" + MiscUtils.printVector(subChunkVoxel) + '}';
    }

    public static WCCi getNeighboringWCC(final Vector3i currentChunk, final int x, final int y, final int z) {
        return new WCCi(getNeighboringSubChunk(currentChunk, x, y, z), normalizeToChunkSpace(x, y, z));
    }

    public Chunk getChunk() {
        final Chunk chunk = VoxelGame.getWorld().getChunk(new ChunkCoords(subChunk.x, subChunk.z));
        return chunk;
    }

    public SubChunk getSubChunk() {
        final SubChunk sc = VoxelGame.getWorld().getSubChunk(this.subChunk);
        return sc;
    }

    public boolean chunkExists() {
        return VoxelGame.getWorld().hasChunk(new ChunkCoords(subChunk.x, subChunk.z));
    }

    public boolean subChunkExists() {
        return VoxelGame.getWorld().hasChunk(this.subChunk);
    }

    // <editor-fold defaultstate="collapsed" desc="static methods">
    /**
     * The subChunk coordinate should START when the subChunk-block coordinate
     * is at 0, and END when the block PASSES subChunk width-1.
     *
     *
     * @param worldN the world coordinate
     * @return the subChunk coordinate
     */
    public static int chunkDiv(int worldN) {
        return worldN < 0
                ? ((worldN + 1) / SubChunk.WIDTH) - 1
                : (worldN / SubChunk.WIDTH);
    }

    public static float chunkDiv(float worldN) {
        return worldN < 0
                ? ((worldN + 1) / SubChunk.WIDTH) - 1
                : (worldN / SubChunk.WIDTH);
    }

//    public static int chunkMod(int worldN) {
//        return ((worldN % SubChunk.WIDTH) + SubChunk.WIDTH) % SubChunk.WIDTH;
////         int modulus = dividend % divisor;
////        if (modulus < 0) {
////            modulus += divisor;
////        }
//    }
//
//    public static float chunkMod(float worldN) {
//        return ((worldN % SubChunk.WIDTH) + SubChunk.WIDTH) % SubChunk.WIDTH;
//    }

    public static void getNeighboringSubChunk(Vector3i vec,
            final Vector3i chunkPos,
            final int x, final int y, final int z) {
        int coordsX = chunkPos.x;
        int coordsY = chunkPos.y;
        int coordsZ = chunkPos.z;
        if (x < 0) {
            --coordsX;
        } else if (x >= SubChunk.WIDTH) {
            ++coordsX;
        }
        if (y < 0) {
            --coordsY;
        } else if (y >= SubChunk.WIDTH) {
            ++coordsY;
        }
        if (z < 0) {
            --coordsZ;
        } else if (z >= SubChunk.WIDTH) {
            ++coordsZ;
        }
        vec.set(coordsX, coordsY, coordsZ);
    }

    public static Vector3i getNeighboringSubChunk(
            final Vector3i chunkPos,
            final int x, final int y, final int z) {
        Vector3i vec = new Vector3i();
        getNeighboringSubChunk(vec, chunkPos, x, y, z);
        return vec;
    }

    public static void getSubChunkAtWorldPos(final Vector3i vec,
            final int worldX, final int worldY, final int worldZ) {
        int chunkX = WCCi.chunkDiv(worldX);
        int chunkY = WCCi.chunkDiv(worldY);
        int chunkZ = WCCi.chunkDiv(worldZ);
        vec.set(chunkX, chunkY, chunkZ);
    }

    public static Vector3i getSubChunkAtWorldPos(
            final int worldX, final int worldY, final int worldZ) {
        int chunkX = WCCi.chunkDiv(worldX);
        int chunkY = WCCi.chunkDiv(worldY);
        int chunkZ = WCCi.chunkDiv(worldZ);
        return new Vector3i(chunkX, chunkY, chunkZ);
    }

    public static void normalizeToChunkSpace(Vector3i vec,
            final int worldX, final int worldY, final int worldZ) {

        int blockX = positiveMod(worldX, SubChunk.WIDTH);
        int blockY = positiveMod(worldY, SubChunk.WIDTH);
        int blockZ = positiveMod(worldZ, SubChunk.WIDTH);
        vec.set(blockX, blockY, blockZ);
    }

    public static Vector3i normalizeToChunkSpace(
            final int worldX, final int worldY, final int worldZ) {

        int blockX = positiveMod(worldX, SubChunk.WIDTH);
        int blockY = positiveMod(worldY, SubChunk.WIDTH);
        int blockZ = positiveMod(worldZ, SubChunk.WIDTH);
        return new Vector3i(blockX, blockY, blockZ);
    }

    public static Vector3i chunkSpaceToWorldSpace(final Vector3i chunkPos,
            final int worldX, final int worldY, final int worldZ) {
        return new Vector3i(
                worldX + chunkPos.x * SubChunk.WIDTH,
                worldY + chunkPos.y * SubChunk.WIDTH,
                worldZ + chunkPos.z * SubChunk.WIDTH);
    }
    // </editor-fold>

    public static double chunkDistToPlayer(
            final int chunkCoordsX, final int chunkCoordsZ,
            final float playerX, final float playerZ) {
        return MathUtils.dist(playerX, playerZ, chunkCoordsX * SubChunk.WIDTH + 8, chunkCoordsZ * SubChunk.WIDTH + 8);
    }
}
