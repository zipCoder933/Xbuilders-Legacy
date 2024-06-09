package com.xbuilders.game.items.blocks.plants.trees;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.PlantBlockUtils;

public class BlockBirchSapling extends Block {

    public BlockBirchSapling() {
        super(161, "Birch Sapling");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(15, 4);
    }

    @Override
    public boolean setBlock(final int x, final int y, final int z, BlockData orientation) {
        this.set(x, y, z);
        (new BlockAction(getPointerHandler(), x, y, z) {

            public void onInterrupt() {
                BirchTreeUtils.plantTree(getPointerHandler(), MiscUtils.getRandom(), x, y, z);
            }

            public void preformAction() throws InterruptedException {
                if (!getPointerHandler().isDevMode()) {
                    Thread.sleep(MiscUtils.randomLong(25000, 35000));
                }
                if (getPointerHandler().getWorld().getBlock(x, y, z) == GameItems.BlockBirchSapling
                        && PlantBlockUtils.deepPlantable(getPointerHandler(), x, y, z)) {
                    System.out.println("Planting tree...");
                    BirchTreeUtils.plantTree(getPointerHandler(), MiscUtils.getRandom(), x, y, z);
                }
            }
        }).startAction();
        return true;
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
