/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

/**
 * @author zipCoder933
 */
public class JsonManager {
    public static BlockTextureTypeAdapter texture = new BlockTextureTypeAdapter();
    public static Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Item.class, new BlockTypeAdapter())
            .registerTypeAdapter(BlockTexture.class, texture)
            .create();

}
