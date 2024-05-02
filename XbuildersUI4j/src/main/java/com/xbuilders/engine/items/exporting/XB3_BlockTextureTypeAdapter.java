package com.xbuilders.engine.items.exporting;

import com.google.gson.*;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class XB3_BlockTextureTypeAdapter implements JsonSerializer<BlockTexture>, JsonDeserializer<BlockTexture> {
    Map<ItemExporting.AtlasPosition, String> textureMap = new HashMap<>();

    public XB3_BlockTextureTypeAdapter(Map<ItemExporting.AtlasPosition, String> textureMap) {
        this.textureMap = textureMap;
    }

    @Override
    public JsonElement serialize(BlockTexture blockTexture, Type type, JsonSerializationContext jsonSerializationContext) {
        if (blockTexture == null) return null;

        //Add TOP as json array
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("neg_y", textureMap.get(new ItemExporting.AtlasPosition(blockTexture.TOP)));
        jsonObject.addProperty("pos_x", textureMap.get(new ItemExporting.AtlasPosition(blockTexture.RIGHT)));
        jsonObject.addProperty("pos_z", textureMap.get(new ItemExporting.AtlasPosition(blockTexture.FRONT)));
        jsonObject.addProperty("neg_z", textureMap.get(new ItemExporting.AtlasPosition(blockTexture.BACK)));
        jsonObject.addProperty("neg_x", textureMap.get(new ItemExporting.AtlasPosition(blockTexture.LEFT)));
        jsonObject.addProperty("pos_y", textureMap.get(new ItemExporting.AtlasPosition(blockTexture.BOTTOM)));
        return jsonObject;
    }

    @Override
    public BlockTexture deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }


}
