// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.blockData;

import java.util.Arrays;
import java.util.List;

public class BlockData {
    public byte[] bytes;

    public BlockData(final int size) {
        this.bytes = new byte[size];
    }

    public BlockData(final byte[] data) {
        this.bytes = data;
    }

    public BlockData(final List<Byte> blockDataBytes) {
        this.bytes = new byte[blockDataBytes.size()];
        for (int i = 0; i < blockDataBytes.size(); ++i) {
            final byte val = blockDataBytes.get(i);
            if (val == -128) {
                throw new IllegalArgumentException("The byte [-128] is forbidden for use.");
            }
            this.bytes[i] = val;
        }
    }

    public void set(int index, byte value) {
        this.bytes[index] = value;
    }

    public byte get(int index) {
        return this.bytes[index];
    }

    public int getLength() {
        return this.bytes.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.bytes);
    }
}
