/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.exporting;

/**
 * @author zipCoder933
 */

import com.google.gson.*;
import com.xbuilders.engine.items.block.Block;

import java.lang.reflect.Type;

public class XB3_BlockTypeAdapter implements JsonSerializer<Block>, JsonDeserializer<Block> {

    XB3_BlockTextureTypeAdapter textureAdapter;

    public XB3_BlockTypeAdapter(XB3_BlockTextureTypeAdapter textureAdapter) {
        this.textureAdapter = textureAdapter;
    }

    @Override
    public JsonElement serialize(Block src, Type typeOfSrc, JsonSerializationContext context) {
//        System.out.println("Using custom type adapter");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.name);
        jsonObject.addProperty("id", src.id);
        jsonObject.add("texture", textureAdapter.serialize(src.texture, typeOfSrc, context));

        jsonObject.addProperty("solid", src.solid);
        jsonObject.addProperty("opaque", src.opaque);
        jsonObject.addProperty("torch", src.isLuminous()? 15:0);
        jsonObject.addProperty("type", src.type);
        return jsonObject;
    }

    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
