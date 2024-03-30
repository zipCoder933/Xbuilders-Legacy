/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.track;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

/**
 *
 * @author zipCoder933
 */
public class MinecartRoad extends Block {

    public MinecartRoad() {
        super(404, "Minecart Road Block");
        this.texture = new BlockTexture();
        this.texture.setTOP(3, 10);
        this.texture.setSIDES(5, 14);
        this.texture.setBOTTOM(5, 14);
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

}
