/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

public class Barrel extends Block {
    
    @Override
    public boolean onClickEvent(int setX, int setY, int setZ) {
//        return super.onClickEvent(setX, setY, setZ);
        getPointerHandler().getGame().alert("This barrel is empty!");
        return false;
    }
    
    public Barrel() {
        super(51, "Barrel");
        texture = new BlockTexture();
        texture.setTOP(18, 8);
        texture.setSIDES(17, 8);
        texture.setBOTTOM(16, 8);
    }
}
