// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.tools;

import com.xbuilders.engine.items.tool.Tool;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.game.items.GameItems;

public class Hoe extends Tool {

    public Hoe() {
        super(3, "Hoe");
        setIconAtlasPosition(1, 6);
    }

    @Override
    public boolean onPlace(int x, int y, int z) {
        if (VoxelGame.getWorld().getBlock(x, y + 1, z) == GameItems.BLOCK_DIRT) {
           GameItems.BLOCK_FARMLAND.set(x, y + 1, z);
            return true;
        }
        return false;
    }
}
