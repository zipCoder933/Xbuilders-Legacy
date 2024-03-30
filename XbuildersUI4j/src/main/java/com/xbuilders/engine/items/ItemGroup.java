/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author zipCoder933
 * @param <T> the item
 */
abstract class ItemGroup<T extends Item> {

    // public final File iconDirectory;
    protected final HashMap<Short, T> idMap;
    protected T[] itemList;
    // protected int defaultIcon;

    public ItemGroup() {
        // this.iconDirectory = iconDirectory;
        // this.defaultIcon = defaultIcon;
        idMap = new HashMap<>();
    }

    public abstract void setItems(T[] inputBlocks);

    /**
     * @return the idMap
     */
    public HashMap<Short, T> getIdMap() {
        return idMap;
    }

    /**
     * @return the items
     */
    public T[] getList() {
        return itemList;
    }

    public T getItem(short id) {
        return idMap.get(id);
    }

    protected int assignIDMapAndCheckIDs(T[] inputItems) {
        int highestId = 0;
        idMap.clear();
        for (int i = 0; i < inputItems.length; i++) {
            short id = inputItems[i].id;
            if (idMap.containsKey(id)) {
                System.err.println("Block " + inputItems[i] + " ID conflicts with an existing ID: " + id);
            }
            idMap.put(id, inputItems[i]);
            if (id > highestId) {
                highestId = id;
            }
        }
        System.out.println("\t(The highest item ID is: " + highestId + ")");
        System.out.println("\tID Gaps: ");
        for (int id = 1; id < highestId; id++) {
            boolean found = false;
            for (Item item : inputItems) {
                if (item.id == id) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.print(id + ", ");
            }
        }
        System.out.println("");
        return highestId;
    }
}
