/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.items.entity.EntityLink;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author zipCoder933
 */
public class EntityList extends ItemGroup<EntityLink> {

    public EntityList() throws IOException {
        super();
    }

    @Override
    public void setItems(EntityLink[] inputBlocks) {
        if (inputBlocks == null) {
            itemList = new EntityLink[0];return;
        }
        assignIDMapAndCheckIDs(inputBlocks);
        itemList = new EntityLink[idMap.size()];
        int i = 0;
        for (EntityLink block : getIdMap().values()) {
            itemList[i] = block;
            i++;
        }
    }

}
