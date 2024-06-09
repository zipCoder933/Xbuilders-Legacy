package com.xbuilders.game.items.blocks.plants.growablePlants;

import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.Plant;
import com.xbuilders.game.items.blocks.plants.PlantBlockUtils;

public class BlockCarrotSeeds extends Plant {

    public BlockCarrotSeeds() {
        super(163, "Carrot Seeds");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(10, 6);
    }

    @Override
    public boolean setBlock(final int x, final int y, final int z, BlockData orientation) {
        boolean wasSet = false;
        if (PlantBlockUtils.cropPlantable(getPointerHandler(), x, y, z)) {
            wasSet = true;
            this.set(x, y, z);
            (new BlockAction(getPointerHandler(), x, y, z) {
                @Override
                public void preformAction() throws InterruptedException {
                    long growSpeed = MiscUtils.randomLong(22000, 35000);
                    PlantBlockUtils.growPlant(getPointerHandler(), growSpeed, x, y, z,
                            GameItems.BlockCarrotSeeds,
                            GameItems.StageA1,
                            GameItems.StageA2,
                            GameItems.CARROTS);
                }

                @Override
                public void onInterrupt() throws Exception {
                    GameItems.CARROTS.set(x, y, z);
                }
            }).startAction();
        }
        return wasSet;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
