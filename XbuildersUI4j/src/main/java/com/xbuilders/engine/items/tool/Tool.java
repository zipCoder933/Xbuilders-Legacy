/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.tool;

import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemType;

/**
 * @author zipCoder933
 */
public abstract class Tool extends Item {

    public Tool(int id, String name) {
        super(id, name, ItemType.TOOL);
    }

    /**
     * @param x
     * @param y
     * @param z
     * @param player
     * @return the item was actually placed/used in that position
     */
    public abstract boolean onPlace(int x, int y, int z);

    public void onClick() {
    }

    @Override
    public byte maxStackSize() {
        return (byte) 64;
    }

    /**
     * @return If a single item should run out gradually instead of an item
     * getting used up entirely once per click
     */
    @Override
    public boolean continuousUse() {
        return true;
    }


}
