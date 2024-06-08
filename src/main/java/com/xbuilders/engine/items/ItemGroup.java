/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.utils.IntMap;

import java.util.HashMap;

/**
 *
 * @author zipCoder933
 * @param <T> the item
 */
abstract class ItemGroup<T extends Item> {

    // public final File iconDirectory;
    protected final IntMap<T> idMap;
    protected T[] itemList;
    Class<T> type;
    // protected int defaultIcon;

    public ItemGroup(Class<T> T) {
        // this.iconDirectory = iconDirectory;
        // this.defaultIcon = defaultIcon;
        type = T;
        idMap = new IntMap<>(T);
    }

    public abstract void setAndInitItems(T[] inputBlocks);


    /**
     * @return the items
     */
    public T[] getList() {
        return itemList;
    }

    public T getItem(short id) {
        return idMap.get(id);
    }

    protected int assignItems(T[] inputItems) {
        int highestId = 0;
        HashMap<Integer, T> itemMap = new HashMap<>();

        for (int i = 0; i < inputItems.length; i++) {
            short id = inputItems[i].id;
            if (itemMap.containsKey(id)) {
                throw new RuntimeException(type.getName()+" ID " + inputItems[i] + " conflicts with an existing ID: " + id);
            }
            itemMap.put((int) id, inputItems[i]);
            if (id > highestId) {
                highestId = id;
            }
        }
        itemList = inputItems;
        idMap.setList(itemMap);

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
