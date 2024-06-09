// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.blockData;

public class BlockOrientation extends BlockData
{
    public static BlockOrientation getBlockOrientation(final BlockData data) {
        BlockOrientation orientation = null;
        if (data != null && data.getLength() >= 2) {
            orientation = new BlockOrientation(data.get(0), data.get(1));
        }
        return orientation;
    }

    public int getXZ() {
        return this.bytes[0];
    }
    
    public int getY() {
        if (this.bytes[1] == 2) {
            return -1;
        }
        return this.bytes[1];
    }
    
    public void setXZ(final byte val) {
        this.bytes[0] = val;
    }
    
    public void setY(byte val) {
        if (val == -1) {
            val = 2;
        }
        this.bytes[1] = val;
    }
    
    public BlockOrientation(final byte xz, final byte y) {
        super(2);
        this.setXZ(xz);
        this.setY(y);
    }
}
