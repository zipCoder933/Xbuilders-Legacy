/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.json;

/**
 * @author zipCoder933
 */

import com.google.gson.*;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

import java.lang.reflect.Type;

public class BlockTypeAdapter implements JsonSerializer<Block>, JsonDeserializer<Block> {

    @Override
    public JsonElement serialize(Block src, Type typeOfSrc, JsonSerializationContext context) {
//        System.out.println("Using custom type adapter");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.name);
        jsonObject.addProperty("id", src.id);
        jsonObject.addProperty("solid", src.isSolid());
        jsonObject.addProperty("opaque", src.isOpaque());
        jsonObject.addProperty("animationLength", src.animationLength);
        jsonObject.addProperty("luminous", src.isLuminous());
        jsonObject.addProperty("faloff", src.falloff);
        jsonObject.addProperty("type", src.type);

        //Add texture
        jsonObject.add("texture", JsonManager.texture.serialize(src.texture, typeOfSrc, context));
        return jsonObject;
    }

    private static Gson customTextureDeserializer = new GsonBuilder()
            .registerTypeAdapter(BlockTexture.class, new BlockTextureTypeAdapter())
            .create();

    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        short id = jsonObject.get("id").getAsShort();
        String name = jsonObject.get("name").getAsString();

        Block block = new Block(id, name);
        if (jsonObject.has("solid")) block.solid = jsonObject.get("solid").getAsBoolean();
        if (jsonObject.has("opaque")) block.opaque = jsonObject.get("opaque").getAsBoolean();

        if (jsonObject.has("animationLength"))
            block.animationLength = jsonObject.get("animationLength").getAsShort();
        if (jsonObject.has("luminous")) block.luminous = jsonObject.get("luminous").getAsBoolean();
        if (jsonObject.has("falloff")) block.falloff = jsonObject.get("falloff").getAsByte();

        if (jsonObject.has("type")) block.type = jsonObject.get("type").getAsInt();

        if (jsonObject.has("texture")) { //Add texture
            JsonObject texture = jsonObject.get("texture").getAsJsonObject();
            BlockTexture meta = JsonManager.texture.deserialize(texture, BlockTexture.class, context);
            block.texture = meta;
        }

        // You may need to adjust this part based on your constructor
        return block;
    }
}
