package com.xbuilders.game.items.blocks.plants.growablePlants;

import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.Plant;
import com.xbuilders.game.items.blocks.plants.PlantBlockUtils;

public class BlockWheatSeeds extends Plant {

    public BlockWheatSeeds() {
        super(162, "Wheat Seeds");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(8, 5);

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
                            GameItems.BlockWheatSeeds,
                            GameItems.StageB1,
                            GameItems.StageB2,
                            GameItems.StageB3,
                            GameItems.StageB4,
                            GameItems.StageB5,
                            GameItems.StageB6,
                            GameItems.WHEAT);
                }

                @Override
                public void onInterrupt() throws Exception {
                    GameItems.WHEAT.set(x, y, z);
                }
            }).startAction();
        }
        return wasSet;
    }

}
