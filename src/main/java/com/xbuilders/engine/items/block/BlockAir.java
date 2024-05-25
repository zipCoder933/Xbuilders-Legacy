// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.items.block;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;

public class BlockAir extends Block {

    public BlockAir() {
        super(0, "Air");
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isAir() {
        return true;
    }
}
