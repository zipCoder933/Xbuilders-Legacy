/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils;

import com.xbuilders.engine.items.ItemQuantity;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author zipCoder933
 */
public class InventoryUtils {

    /**
     *
     * @param items the item list
     * @param itemToAppend the items to append
     * @return if the item list could fit the new item
     */
    public static boolean appendBlocksToInventory(ItemQuantity[] items, ItemQuantity itemToAppend) {
        if (itemToAppend.isInfiniteResource()) {
            if (Arrays.asList(items).contains(itemToAppend)) {
                return true;
            } else {
                for (int i = 0; i < items.length; i++) {
                    if (items[i] == null) {
                        items[i] = new ItemQuantity(itemToAppend.getItem(), (byte) itemToAppend.getQuantity());
                        break;
                    } else if (items[i].getItem() == itemToAppend.getItem()) {
                        items[i].addQuantity(itemToAppend.getQuantity());
                        break;
                    }
                }
            }
            return true;
        } else {
            int leftoverQuantity = itemToAppend.getQuantity();
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    items[i] = new ItemQuantity(itemToAppend.getItem(), (byte) leftoverQuantity);
                    break;
                } else if (items[i].getItem() == itemToAppend.getItem()) {
                    if (itemToAppend.getItem().continuousUse()) {
                        continue;
                    }
                    leftoverQuantity = items[i].addQuantity(itemToAppend.getQuantity());

                    if (leftoverQuantity <= 0) {
                        break;
                    }
                }
            }
            return leftoverQuantity <= 0;
        }
    }

    public static boolean isRoomForItem(ItemQuantity[] items, ItemQuantity itemToAppend) {
        if (itemToAppend == null) {
            return false;
        }
        if (itemToAppend.isInfiniteResource()) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    return true;
                }
            }
            return false;
        } else {
            int leftoverQuantity = itemToAppend.getQuantity();
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    return true;
                } else if (items[i].getItem() == itemToAppend.getItem()) {
                    if (itemToAppend.getItem().continuousUse()) {
                        continue;
                    }
                    leftoverQuantity = items[i].getLeftoverQuantity(itemToAppend.getQuantity());

                    if (leftoverQuantity <= 0) {
                        return true;
                    }
                }
            }
            return leftoverQuantity <= 0;
        }
    }
}
