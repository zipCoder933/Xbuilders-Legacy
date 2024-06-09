package com.xbuilders.game.items.blocks.plants.trees;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.PlantBlockUtils;

public class BlockJungleSapling extends Block {

    public BlockJungleSapling() {
        super(37, "Jungle Sapling");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(14, 0);

    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean setBlock(final int x, final int y, final int z, BlockData orientation) {
      this.set(x, y, z);
        (new BlockAction(getPointerHandler(), x, y, z) {

            public void onInterrupt() {
                JungleTreeUtils.plantTree(getPointerHandler(), MiscUtils.getRandom(), x, y, z);
            }

            public void preformAction() throws InterruptedException {
                if (!getPointerHandler().isDevMode()) {
                    Thread.sleep(MiscUtils.randomLong(25000, 40000));
                }
                if (VoxelGame.getWorld().getBlock(x, y, z) == GameItems.BLOCK_JUNGLE_SAPLING
                        && PlantBlockUtils.deepPlantable(getPointerHandler(), x, y, z)) {
                    System.out.println("Planting tree...");
                    JungleTreeUtils.plantTree(getPointerHandler(), MiscUtils.getRandom(), x, y, z);
                }
            }
        }).startAction();
        return true;
    }

}
