/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.entity;

import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.PointerHandler;

/**
 * @author zipCoder933
 */
public class EntityTemplate {

    EntityLink link;
    byte[] data;

    public EntityTemplate(Entity e) {
        this.link = e.link;
        data = e.toByteArray();
    }

    public Entity makeEntity(PointerHandler ph, int worldX, int worldY, int worldZ) {
        SubChunk chunk = ph.getWorld().getSubChunk(WCCi.getSubChunkAtWorldPos(worldX, worldY, worldZ));
        if (chunk != null) {
            Entity newEntity = link.makeNew(chunk, worldX, worldY, worldZ, data, false);
            chunk.entities.list.add(newEntity);
            chunk.getParentChunk().markAsNeedsSaving();
            chunk.generateStaticEntityMesh();
            return newEntity;
        }
        return null;
    }

}
