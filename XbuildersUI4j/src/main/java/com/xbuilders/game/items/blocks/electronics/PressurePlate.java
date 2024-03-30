/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.electronics;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

/**
 *
 * @author zipCoder933
 */
public class PressurePlate extends ElectronicDevice {

    public PressurePlate(int id, String name, BlockTexture texture) {
        super(id, name);
        this.texture = texture;
        opaque = false;
        setRenderType(BlockRenderType.FLOOR);
    }

}
