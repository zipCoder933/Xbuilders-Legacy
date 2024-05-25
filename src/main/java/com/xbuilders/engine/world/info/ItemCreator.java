// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.info;

import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemType;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import com.xbuilders.engine.items.ItemQuantity;
import com.google.gson.stream.JsonWriter;
import com.xbuilders.engine.items.ItemList;
import com.google.gson.TypeAdapter;

public class ItemCreator extends TypeAdapter {


    public ItemCreator() {
    }

    @Override
    public void write(final JsonWriter writer, final Object t) throws IOException {
        final ItemQuantity item = (ItemQuantity) t;
        if (item == null || item.getItem() == null) {
            writer.beginObject();
            writer.name("iqid");
            writer.value(-1L);
            writer.endObject();
        } else {
            writer.beginObject();
            writer.name("iqid");
            Item item1 = item.getItem();
            writer.value(item1.id);
            writer.name("type");
            Item item2 = item.getItem();
            writer.value(item2.itemType.toString());
            writer.name("quantity");
            writer.value(item.getQuantity());
            writer.endObject();
        }
    }

    @Override
    public Object read(final JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldname = null;
        short id = 0;
        int quantity = 0;
        ItemType type = null;
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                fieldname = reader.nextName();
                if ("iqid".equals(fieldname)) {
                    token = reader.peek();
                    id = (short) reader.nextInt();
                    if (id == -1) {
                        reader.endObject();
                        return null;
                    }
                    continue;
                } else if ("quantity".equals(fieldname)) {
                    token = reader.peek();
                    quantity = reader.nextInt();
                } else {
                    if (!"type".equals(fieldname)) {
                        continue;
                    }
                    token = reader.peek();
                    type = ItemType.valueOf(reader.nextString());
                }
            }
        }
        reader.endObject();
        // final Item item = this.itemList.get(type, id);
        // Change the access of item to retrieve the item by first checking the type

        Item item = null;
        if (type == ItemType.BLOCK) {
            item = ItemList.blocks.getItem(id);
        } else if (type == ItemType.ENTITY_LINK) {
            item = ItemList.entities.getItem(id);
        } else if (type == ItemType.TOOL) {
            item = ItemList.tools.getItem(id);
        }
        if (item == null) {
            return null;
        }
        return new ItemQuantity(item, (byte) quantity);
    }
}
