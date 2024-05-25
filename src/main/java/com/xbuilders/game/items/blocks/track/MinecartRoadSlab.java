/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.track;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

/**
 *
 * @author zipCoder933
 */
public class MinecartRoadSlab extends Block {

    public MinecartRoadSlab() {
        super(408, "Minecart Road Slab");
        setRenderType(BlockRenderType.SLAB);
        this.texture = new BlockTexture();
        this.texture.setTOP(3, 10);
        this.texture.setSIDES(5, 14);
        this.texture.setBOTTOM(5, 14);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

}
