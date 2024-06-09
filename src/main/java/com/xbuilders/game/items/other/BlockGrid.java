// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.other;

import com.xbuilders.engine.world.chunk.ChunkSavingLoading;
import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import com.xbuilders.engine.world.blockData.BlockData;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.joml.Vector3i;

public class BlockGrid {

    public final Vector3i size;
    private short[] blocks;
    private HashMap<Integer, BlockData> blockData;

    public HashMap<Integer, BlockData> getBlockDataAsHashmap() {
        return this.blockData;
    }

    public short get(final int x, final int y, final int z) {
        try {
            return blocks[this.getIndexOfCoords(x, y, z)];
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Block coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public int getIndexOfCoords(final int x, final int y, final int z) {
        return x + this.size.x * (y + this.size.y * z);
    }

    protected int getCoordsOfIndex(final int index) {
        return index % this.size.x + (index / this.size.x * this.size.x);
    }

    public void set(final short block, final int x, final int y, final int z) {
        try {
            this.blocks[this.getIndexOfCoords(x, y, z)] = block;
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public BlockData getBlockData(final int x, final int y, final int z) {
        try {
            return this.getBlockDataAsHashmap().get(this.getIndexOfCoords(x, y, z));
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public void setBlockData(final BlockData orientation, final int x, final int y, final int z) {
        try {
            final int key = this.getIndexOfCoords(x, y, z);
            if (orientation == null) {
                this.getBlockDataAsHashmap().remove(key);
            } else {
                this.getBlockDataAsHashmap().put(key, orientation);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Coordinates " + x + ", " + y + ", " + z + " out of bounds!");
        }
    }

    public BlockGrid(final int sizeX, final int sizeY, final int sizeZ) {
        this.size = new Vector3i(sizeX, sizeY, sizeZ);
        this.blocks = new short[sizeX * sizeY * sizeZ];
        this.blockData = new HashMap<>();
    }

    public boolean inBounds(final int x, final int y, final int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < this.size.x && y < this.size.y && z < this.size.z;
    }

    public void toBytes(XBFilterOutputStream out) throws IOException {
        //Write the size first!
        out.write(ChunkSavingLoading.shortToBytes(this.size.x));
        out.write(ChunkSavingLoading.shortToBytes(this.size.y));
        out.write(ChunkSavingLoading.shortToBytes(this.size.z));
        for (int i = 0; i < this.blocks.length; ++i) {
            out.write(ChunkSavingLoading.shortToBytes(this.blocks[i]));
            int coords = getCoordsOfIndex(i);
            if (blockData.containsKey(coords)) {
                byte[] bytes = blockData.get(coords).bytes;
                out.write(bytes);
            }
            out.write(ChunkSavingLoading.PIPE_BYTE);
        }
    }

    public void fromBytes(byte[] bytes, int start, int end) {
        //Read and load the size first!
        this.size.x = ChunkSavingLoading.bytesToShort(bytes[start], bytes[start + 1]);
        this.size.y = ChunkSavingLoading.bytesToShort(bytes[start + 2], bytes[start + 3]);
        this.size.z = ChunkSavingLoading.bytesToShort(bytes[start + 4], bytes[start + 5]);
        this.blocks = new short[size.x * size.y * size.z];
        this.blockData = new HashMap<>();
        if (size.x == 0 || size.y == 0 || size.z == 0) {
            return;
        }

//        System.out.println("Loading grid: " + Arrays.toString(subarray(bytes, start+6, end)));
        int index = 0;
        for (int i = start + 6; i < end;) {
            //Count the number of bytes leading up to the next pipe
            int count = 2;
            while (bytes[i + count] != ChunkSavingLoading.PIPE_BYTE) {
                count++;
            }
            //print the section of the bytes using sublist
//            System.out.println("\tLoading block: " + Arrays.toString(subarray(bytes, i, i + count + 1)) + " index: " + index);
            int coords = getCoordsOfIndex(index);
            this.blocks[coords] = ChunkSavingLoading.bytesToShort(bytes[i], bytes[i + 1]);
            i += 2;
            if (count > 2) {
//                System.out.println("\t\tLoading block data: " + Arrays.toString(subarray(bytes, i, i + count - 2)));
                byte[] bytes2 = new byte[count - 2];
                System.arraycopy(bytes, i, bytes2, 0, count - 2);
                this.blockData.put(coords, new BlockData(bytes2));
            }
            i += count - 1;
            index++;
//                System.out.println("Loading block: " + ItemList.getBlock(this.blocks[coords]) + " data: " + this.blockData.get(coords));
        }
    }

//    private static byte[] subarray(byte[] array, int start, int end) {
//        if (start < 0 || start > end || end > array.length) {
//            throw new IndexOutOfBoundsException("start or end is out of bounds");
//        }
//
//        byte[] subarray = new byte[end - start];
//        for (int i = start; i < end; i++) {
//            subarray[i - start] = array[i];
//        }
//        return subarray;
//    }
    public void reset() {
        for (int i = 0; i < this.blocks.length; ++i) {
            this.blocks[i] = 0;
        }
        this.blockData.clear();
    }

    @Override
    public String toString() {
        return "BlockGrid{"
                + "blocks=" + Arrays.toString(blocks)
                + '}';
    }
}
