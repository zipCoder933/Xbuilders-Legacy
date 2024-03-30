package com.xbuilders.game.items.blocks.plants.sunflower;

import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.Plant;
import com.xbuilders.game.items.blocks.plants.PlantBlockUtils;

public class BlockSunflowerSeeds extends Plant {

    public BlockSunflowerSeeds() {
        super(549, "Sunflower Seeds");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(10, 6);
    }

    @Override
    public boolean setBlock(final int x, final int y, final int z, BlockData data) {
              boolean wasSet = false;
        if (PlantBlockUtils.cropPlantable(getPointerHandler(), x, y, z)) {
            wasSet = true;
            this.set(x, y, z);
            BlockAction action = new BlockAction(getPointerHandler(), x, y, z) {

                long growSpeed;
                int height;

                @Override
                public void preformAction() throws InterruptedException {
                    if (getPointerHandler().isDevMode()) {
                        growSpeed = MiscUtils.randomLong(220, 440);
                    } else {
                        growSpeed = MiscUtils.randomLong(22000, 44000);
                    }
                    height = MiscUtils.randomInt(3, 5);
                    Thread.sleep(growSpeed);
                    if (PlantBlockUtils.plantable(getPointerHandler(), x, y, z)) {
                        setStalk(x, y, z);
                        for (int i = 1; i <= height; i++) {
                            Thread.sleep(growSpeed);
                            if (PlantBlockUtils.plantable(getPointerHandler(), x, y, z)
                                    && getPointerHandler().getWorld().getBlock(x, y, z) == GameItems.SUNFLOWER_STALK) {
                                if (i == height) {
                                    setHead(x, y - i, z);
                                } else {
                                    setStalk(x, y - i, z);
                                }
                            } else {
                                return;
                            }
                        }
                    }
                }

                //<editor-fold defaultstate="collapsed" desc="If interrupted, completely plant the sunflower">
                @Override
                public void onInterrupt() throws Exception {
                    if (PlantBlockUtils.plantable(getPointerHandler(), x, y, z) && getPointerHandler().getWorld().getBlock(x, y, z) == GameItems.SUNFLOWER_STALK) {
                        for (int i = 1; i <= height; i++) {
                            if (i == height) {
                                setHead(x, y - i, z);
                            } else {
                                setStalk(x, y - i, z);
                            }
                        }
                    }
                }
//</editor-fold>

            };
            action.startAction();
        }
        return wasSet;
    }

    private void setStalk(int x, int y, int z) {
        if (!getPointerHandler().getWorld().getBlock(x, y, z).isOpaque()) {
            GameItems.SUNFLOWER_STALK.set(x, y, z);
        }
    }

    private void setHead(int x, int y, int z) {
        if (!getPointerHandler().getWorld().getBlock(x, y, z).isOpaque()) {
            GameItems.SUNFLOWER_HEAD.set(x, y, z);
        }
    }


}
