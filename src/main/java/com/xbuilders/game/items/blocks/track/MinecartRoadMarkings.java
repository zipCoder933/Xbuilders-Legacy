/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.track;

import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;

/**
 *
 * @author zipCoder933
 */
public class MinecartRoadMarkings extends Block {

    public MinecartRoadMarkings() {
        super(453, "Road Markings");
        setRenderType(BlockRenderType.FLOOR);
        this.texture = new BlockTexture(0, 11);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public void onLocalChange(LocalChangeRecord record) {
        int x = record.getTargetBlockPosition().x;
        int y = record.getTargetBlockPosition().y;
        int z = record.getTargetBlockPosition().z;

        if (getPointerHandler().getWorld().getBlock(x, y + 1, z) != GameItems.MINECART_ROAD) {
            BlockList.BLOCK_AIR.set(x, y, z);
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (getPointerHandler().getWorld().getBlock(x, y + 1, z) == GameItems.MINECART_ROAD) {
            this.set(x, y, z, data);
            return true;
        }
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
