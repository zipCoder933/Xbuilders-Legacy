// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.blocks.plants.tallGrass;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.Plant;
import com.xbuilders.game.items.blocks.plants.PlantBlockUtils;

public class TallDryGrassBottom extends Plant {

    public TallDryGrassBottom() {
        super(42, "Tall Dry Grass");
        this.texture = new BlockTexture(8, 14);
        setRenderType(BlockRenderType.SPRITE);
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData orientation) {
        if (VoxelGame.getWorld().getBlock(x, y - 1, z).isAir()
                && PlantBlockUtils.blockIsGrassSnowOrDirt(getPointerHandler(), VoxelGame.getWorld().getBlock(x, y + 1, z))) {
            GameItems.TALL_DRY_GRASS_BOTTOM.set(x, y, z);
            GameItems.TALL_DRY_GRASS_TOP.set(x, y - 1, z);
            return true;
        }
        return false;
    }

    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        if (VoxelGame.getWorld().getBlock(x, y - 1, z) == GameItems.TALL_DRY_GRASS_TOP) {
            BlockList.BLOCK_AIR.set(x, y - 1, z);
        }
    }

}
