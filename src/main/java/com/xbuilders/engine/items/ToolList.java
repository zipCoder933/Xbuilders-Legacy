/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.items.tool.Tool;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author zipCoder933
 */
public class ToolList extends ItemGroup<Tool> {

    public ToolList() throws IOException {
        super();
    }

    @Override
    public void setItems(Tool[] inputBlocks) {
        if (inputBlocks == null) {
            itemList = new Tool[0];return;
        }
        assignIDMapAndCheckIDs(inputBlocks);
        itemList = new Tool[idMap.size()];
        int i = 0;
        for (Tool block : getIdMap().values()) {
            itemList[i] = block;
            i++;
        }
    }

}
