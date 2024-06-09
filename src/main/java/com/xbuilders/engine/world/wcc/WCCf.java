// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.wcc;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.utils.MiscUtils;
import static com.xbuilders.engine.utils.math.MathUtils.positiveMod;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.chunk.SubChunk;
import static com.xbuilders.engine.world.wcc.WCCi.chunkDiv;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryStack;

public class WCCf {

    public final Vector3i subChunk;
    public final Vector3f subChunkVoxel;

    public boolean chunkExists() {
        return VoxelGame.getWorld().hasChunk(new ChunkCoords(subChunk.x, subChunk.z));
    }

    public SubChunk getSubChunk() {
        final SubChunk sc = VoxelGame.getWorld().getSubChunk(this.subChunk);
        return sc;
    }

    public WCCf() {
        this.subChunk = new Vector3i();
        this.subChunkVoxel = new Vector3f();
    }

    public WCCf(IntBuffer buff1, FloatBuffer buff2) {
        this.subChunk = new Vector3i(buff1);
        this.subChunkVoxel = new Vector3f(buff2);
    }

    public WCCf(MemoryStack stack) {
        this.subChunk = new Vector3i(stack.mallocInt(3));
        this.subChunkVoxel = new Vector3f(stack.mallocFloat(3));
    }

    /**
     * Assign the values to your specified vectors
     *
     * @param chunk
     * @param block
     */
    public WCCf(final Vector3i chunk, final Vector3f block) {
        this.subChunk = chunk;
        this.subChunkVoxel = block;
    }

    public WCCf set(Vector3f vec) {
        return set(vec.x, vec.y, vec.z);
    }

    public WCCf set(final float worldX, final float worldY, final float worldZ) {
        float blockX = positiveMod(worldX, SubChunk.WIDTH);
        float blockY = positiveMod(worldY, SubChunk.WIDTH);
        float blockZ = positiveMod(worldZ, SubChunk.WIDTH);

        this.subChunkVoxel.set(blockX, blockY, blockZ);

        int chunkX = chunkDiv((int) worldX);
        int chunkY = chunkDiv((int) worldY);
        int chunkZ = chunkDiv((int) worldZ);

        this.subChunk.set(chunkX, chunkY, chunkZ);
        return this;
    }

    @Override
    public String toString() {
        return "WCC{chunk=" + MiscUtils.printVector(subChunk)
                + ", voxel=" + MiscUtils.printVector(subChunkVoxel) + '}';
    }
}
