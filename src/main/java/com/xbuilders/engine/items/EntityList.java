/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.items.entity.EntityLink;

import java.io.IOException;

/**
 * @author zipCoder933
 */
public class EntityList extends ItemGroup<EntityLink> {

    public EntityList() throws IOException {
        super(EntityLink.class);
    }

    @Override
    public void setAndInitItems(EntityLink[] inputBlocks) {
        assignItems(inputBlocks);
    }

}
