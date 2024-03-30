/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.block;

/**
 *
 * @author zipCoder933
 */
public class EntityBlockNonSolid extends Block {

    public EntityBlockNonSolid() {
        super(261, "transparent entity block hidden");
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isAir() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
