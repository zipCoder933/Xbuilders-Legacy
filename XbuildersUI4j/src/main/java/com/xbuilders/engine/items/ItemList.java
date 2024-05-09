package com.xbuilders.engine.items;

import com.xbuilders.engine.items.exporting.ItemExporting;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.icons.IconManager;
import com.xbuilders.engine.items.tool.Tool;

import java.io.IOException;
import processing.ui4j.UIExtensionFrame;

public class ItemList {

    /**
     * This class is fully static and should not be instantiated or owned by any
     * class. It contains only static members and cannot be instantiated. Use
     * the static members directly without creating an instance of this class.
     */
    public static IconManager iconManager;
    public static BlockList blocks;
    public static EntityList entities;
    public static ToolList tools;
    public static Item[] allItems;

    /**
     * Initializes the program with the given block textures, block icons,
     * entity textures, entity tool icons, and default icon number.
     *
     */
    public static void initialize(UIExtensionFrame applet) throws IOException {
        blocks = new BlockList();
        entities = new EntityList();
        tools = new ToolList();
        iconManager = new IconManager(applet);
    }

    /**
     * Sets all the items in the block list, entity list, and tool list.
     *
     * @param blocks2 an array of Block objects representing the block list
     * @param entity2 an array of EntityLink objects representing the entity
     * list
     * @param tool2 an array of Tool objects representing the tool list
     */
    public static void setAllItems(Block[] blocks2, EntityLink[] entity2, Tool[] tool2) {
        System.out.println("Blocks: " + blocks2.length);
        blocks.setItems(blocks2);
        System.out.println("Entities: " + entity2.length);
        entities.setItems(entity2);
        System.out.println("Tools: " + tool2.length);
        tools.setItems(tool2);

        allItems = concatArrays(entities.getList(), tools.getList(), blocks.getList()
        );

//        try {
//            ItemExporting.exportListToXbuilders3(blocks.textureAtlas, blocks.getList());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        for (Item i : allItems) {
            i.initialize();
        }
        try {
            iconManager.loadIcons(blocks.getList(), blocks.textureAtlas);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Item[] concatArrays(Item[]... arrays) {
        int totalLength = 0;
        for (Item[] array : arrays) {
            totalLength += array.length;
        }
        Item[] resultArray = new Item[totalLength];
        int currentIndex = 0;

        for (Item[] array : arrays) {
            for (Item value : array) {
                resultArray[currentIndex] = value;
                currentIndex++;
            }
        }
        return resultArray;
    }

    public static Block getBlock(short blockID) {
        return blocks.getIdMap().get(blockID);
    }

    public static EntityLink getEntity(short blockID) {
        return entities.getIdMap().get(blockID);
    }

    public static Tool getTool(short blockID) {
        return tools.getIdMap().get(blockID);
    }

    public static Item getItem(short blockID, ItemType type) {
        switch (type) {
            case BLOCK:
                return getBlock(blockID);
            case ENTITY_LINK:
                return getEntity(blockID);
            case TOOL:
                return getTool(blockID);
            default:
                return null;
        }
    }

    public static void worldClose() {
        for (final Block b : blocks.getList()) {//Close all stuff in items
            b.onWorldClose();
        }
        for (final Item b2 : entities.getList()) {
            b2.onWorldClose();
        }
        for (final Item b2 : tools.getList()) {
            b2.onWorldClose();
        }
    }

    public static void worldOpen() {
        for (final Block b : blocks.getList()) {
            b.onWorldOpen();
        }
        for (final Item b2 : entities.getList()) {
            b2.onWorldOpen();
        }
        for (final Item b2 : tools.getList()) {
            b2.onWorldOpen();
        }
    }

}
