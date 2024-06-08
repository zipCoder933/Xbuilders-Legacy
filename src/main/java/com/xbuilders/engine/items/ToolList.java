/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.items.tool.Tool;

import java.io.IOException;

/**
 *
 * @author zipCoder933
 */
public class ToolList extends ItemGroup<Tool> {

    public ToolList() throws IOException {
        super(Tool.class);
    }

    @Override
    public void setAndInitItems(Tool[] inputBlocks) {
        assignItems(inputBlocks);
    }

}
