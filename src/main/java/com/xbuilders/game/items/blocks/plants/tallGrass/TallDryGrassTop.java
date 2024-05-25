// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.blocks.plants.tallGrass;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.Plant;

public class TallDryGrassTop extends Plant {

    public TallDryGrassTop() {
        super(26, "tall dry grass top (hidden)");
        this.texture = new BlockTexture(8, 13);
        setRenderType(BlockRenderType.SPRITE);
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
           this.set(x, y, z, data);
           return true;
    }


    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        if (VoxelGame.getWorld().getBlock(x, y + 1, z) == GameItems.TALL_DRY_GRASS_BOTTOM) {
            VoxelGame.getWorld().setBlock(BlockList.BLOCK_AIR, x, y + 1, z);
        }
    }

}
