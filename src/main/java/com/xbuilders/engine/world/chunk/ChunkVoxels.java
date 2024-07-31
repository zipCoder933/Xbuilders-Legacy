// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.world.blockData.BlockData;
import java.util.HashMap;

import org.joml.Vector3i;

public class ChunkVoxels {

    public final Vector3i size;
    private short[] blocks;
    private HashMap<Integer, BlockData> blockData;
    private boolean isEmpty;

    public ChunkVoxels(final int sizeX, final int sizeY, final int sizeZ) {
        this.size = new Vector3i(sizeX, sizeY, sizeZ);
        this.blocks = new short[sizeX * sizeY * sizeZ];
        this.blockData = new HashMap<>();
        this.isEmpty = true;
    }

    void reset() {
        for (int i = 0; i < this.blocks.length; ++i) {
            this.blocks[i] = BlockList.BLOCK_AIR.id;
        }
        this.blockData.clear();
        this.isEmpty = true;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }


    public Vector3i getSize() {
        return this.size;
    }

    public int getSizeX() {
        return this.getSize().x;
    }

    public int getSizeY() {
        return this.getSize().y;
    }

    public int getSizeZ() {
        return this.getSize().z;
    }

    public short getBlock(final int x, final int y, final int z) {
        try {
            return blocks[this.getIndexOfCoords(x, y, z)];
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Block coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public int getIndexOfCoords(final int x, final int y, final int z) {
        return x + this.getSizeX() * (y + this.getSizeY() * z);
    }

    public void setBlock(final int x, final int y, final int z, final short block) {
        try {
            if(block != BlockList.BLOCK_AIR.id) {
                isEmpty = false;
            }
            this.blocks[this.getIndexOfCoords(x, y, z)] = block;
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public BlockData getBlockData(final int x, final int y, final int z) {
        try {
            return blockData.get(this.getIndexOfCoords(x, y, z));
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public void setBlockData(final int x, final int y, final int z, final BlockData orientation) {
        try {
            final int key = this.getIndexOfCoords(x, y, z);
            if (orientation == null) {
                blockData.remove(key);
            } else {
                blockData.put(key, orientation);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public boolean inBounds(final int x, final int y, final int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < this.getSizeX() && y < this.getSizeY() && z < this.getSizeZ();
    }

}
