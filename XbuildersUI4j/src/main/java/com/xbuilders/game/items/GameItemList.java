/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.tool.Tool;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author zipCoder933
 */
public class GameItemList extends ArrayList<Item> {

    final int TOOL_STARTING_INDEX = 0;

    public Item get(ItemType type, int id) {
        for (Item item : this) {
            if (item.type == type && item.id == id) {
                return item;
            }
        }
        return null;
    }

    public boolean texturesMatch(Block block1, Block block2) {
        BlockTexture tex1 = block1.texture;
        BlockTexture tex2 = block2.texture;
        if (tex1 == null || tex2 == null) {
            return false;
        }
        return tex1.TOP[0] == tex2.TOP[0]
                && tex1.TOP[1] == tex2.TOP[1]
                && tex1.FRONT[0] == tex2.FRONT[0]
                && tex1.FRONT[1] == tex2.FRONT[1]
                && tex1.BOTTOM[0] == tex2.BOTTOM[0]
                && tex1.BOTTOM[1] == tex2.BOTTOM[1];
    }

    Block[] blocks;
    EntityLink[] entities;
    Tool[] tools;

    public GameItemList(Block[] blocks, EntityLink[] entities, Tool[] tools) {
        super();
        this.blocks = blocks;
        this.entities = entities;
        this.tools = tools;
        updateList();
    }

    private void updateList() {
        this.clear();
        for (Block block : blocks) {
            if (isVisibleToGameList(block)) {
                add(block);
            }
        }
        ArrayList<Item> tempList = new ArrayList<>();
        for (EntityLink entity : entities) {
            if (isVisibleToGameList(entity)) {
                tempList.add(entity);
            }
        }
        addAll(tempList);
        tempList.clear();
        for (Tool tool : tools) {
            if (isVisibleToGameList(tool)) {
                tempList.add(tool);
            }
        }
        addAll(TOOL_STARTING_INDEX, tempList);

        System.out.println("Sorting items by correlation...");
        ArrayList<Item> newList = new ArrayList<>();
        newList.add(remove(0));
        int j = 0;
        while (!this.isEmpty()) {
            Item newListItem = newList.get(j);
            int i = 0;
            if (newListItem.tags.size() > 0) {
                for (; i < size(); i++) {//Iterate over old list

                    Item oldListItem = get(i);
                    if (!newListItem.equals(oldListItem)//If the two are not equal
                            && (countSharedTags(newListItem, oldListItem) > 0)) { //And there is a shared tag
                        break;
                    }
                }
                if (i >= size()) {
                    i = 0;
                }
            }
            newList.add(remove(i));
            j++;
        }
        this.addAll(newList);
    }

    public static boolean isVisibleToGameList(Item item) {
        return !item.name.toLowerCase().contains("hidden");
    }

    public boolean matchingNames(Item item1, Item item2) {
        int len = Math.min(item1.name.length(), item2.name.length()) - 1;
        if (len <= 0) {
            return false;
        }
        return item1.name.regionMatches(0, item2.name, 0, len);
    }

    public int countSharedTags(Item item1, Item item2) {
        int count = 0;

        for (String tag : item1.tags) {
            if (item2.tags.contains(tag)) {
                count++;
            }
        }
        return count;
    }
}
