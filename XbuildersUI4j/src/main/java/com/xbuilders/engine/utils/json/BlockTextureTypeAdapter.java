package com.xbuilders.engine.utils.json;

import com.google.gson.*;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

import java.lang.reflect.Type;

public class BlockTextureTypeAdapter implements JsonSerializer<BlockTexture>, JsonDeserializer<BlockTexture> {

    @Override
    public JsonElement serialize(BlockTexture blockTexture, Type type, JsonSerializationContext jsonSerializationContext) {
        if (blockTexture == null) return null;

        //Add TOP as json array
        JsonObject jsonObject = new JsonObject();
        JsonArray top = new JsonArray();
        top.add(blockTexture.TOP[0]);
        top.add(blockTexture.TOP[1]);
        jsonObject.add("top", top);

        JsonArray bottom = new JsonArray();
        bottom.add(blockTexture.BOTTOM[0]);
        bottom.add(blockTexture.BOTTOM[1]);
        jsonObject.add("bottom", bottom);

        JsonArray front = new JsonArray();
        front.add(blockTexture.FRONT[0]);
        front.add(blockTexture.FRONT[1]);
        jsonObject.add("front", front);

        JsonArray back = new JsonArray();
        back.add(blockTexture.BACK[0]);
        back.add(blockTexture.BACK[1]);
        jsonObject.add("back", back);

        JsonArray left = new JsonArray();
        left.add(blockTexture.LEFT[0]);
        left.add(blockTexture.LEFT[1]);
        jsonObject.add("left", left);

        JsonArray right = new JsonArray();
        right.add(blockTexture.RIGHT[0]);
        right.add(blockTexture.RIGHT[1]);
        jsonObject.add("right", right);

        return jsonObject;
    }

    @Override
    public BlockTexture deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonArray top = jsonObject.get("top").getAsJsonArray();
        JsonArray bottom = jsonObject.get("bottom").getAsJsonArray();
        JsonArray front = jsonObject.get("front").getAsJsonArray();
        JsonArray back = jsonObject.get("back").getAsJsonArray();
        JsonArray left = jsonObject.get("left").getAsJsonArray();
        JsonArray right = jsonObject.get("right").getAsJsonArray();

        BlockTexture blockTexture = new BlockTexture();
        blockTexture.setTOP(top.get(0).getAsShort(), top.get(1).getAsShort());
        blockTexture.setBOTTOM(bottom.get(0).getAsShort(), bottom.get(1).getAsShort());
        blockTexture.setFRONT(front.get(0).getAsShort(), front.get(1).getAsShort());
        blockTexture.setBACK(back.get(0).getAsShort(), back.get(1).getAsShort());
        blockTexture.setLEFT(left.get(0).getAsShort(), left.get(1).getAsShort());
        blockTexture.setRIGHT(right.get(0).getAsShort(), right.get(1).getAsShort());
        return blockTexture;
    }


}
