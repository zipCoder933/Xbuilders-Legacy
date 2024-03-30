/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.plants.sunflower;

import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.Plant;

/**
 *
 * @author zipCoder933
 */
public class SunflowerStalk extends Plant {

    public SunflowerStalk() {
        super(547, "Sunflower Stalk hidden");
        setRenderType(BlockRenderType.SPRITE);
        this.texture = new BlockTexture(21, 2);
    }

    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        BlockList.BLOCK_AIR.set(x, y, z);

        int i = 1;
        while (true) {
            if (getPointerHandler().getWorld().getBlock(x, y + i, z) == GameItems.SUNFLOWER_STALK
                    || getPointerHandler().getWorld().getBlock(x, y + i, z) == GameItems.SUNFLOWER_HEAD) {
                BlockList.BLOCK_AIR.set(x, y + i, z);
            } else {
                break;
            }
            i++;
        }
        i = -1;
        while (true) {
            if (getPointerHandler().getWorld().getBlock(x, y + i, z) == GameItems.SUNFLOWER_STALK
                    || getPointerHandler().getWorld().getBlock(x, y + i, z) == GameItems.SUNFLOWER_HEAD) {
                BlockList.BLOCK_AIR.set(x, y + i, z);
            } else {
                break;
            }
            i--;
        }
    }


}
