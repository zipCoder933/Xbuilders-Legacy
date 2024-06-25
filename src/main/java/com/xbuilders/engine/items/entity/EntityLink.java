/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.xbuilders.engine.items.entity;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.world.chunk.SubChunk;

import java.util.function.Supplier;

import com.xbuilders.engine.world.wcc.WCCi;

/**
 * @author sampw
 */
public abstract class EntityLink extends Item {

    private Supplier<Entity> supplier;

    public EntityLink(int id, String name, Supplier<Entity> supplier) {
        super(id, name, ItemType.ENTITY_LINK);
        this.supplier = supplier;
    }

    public EntityLink(int id, String name) {
        super(id, name, ItemType.ENTITY_LINK);
    }

    public void setSupplier(Supplier<Entity> supplier) {
        this.supplier = supplier;
    }

//    public Entity makeNew(float worldX, float worldY, float worldZ, byte[] bytes, boolean setByUser) {
//        SubChunk chunk = VoxelGame.getWorld().getSubChunk(WCCi.getSubChunkAtWorldPos((int) worldX, (int) worldY, (int) worldZ));
//        return makeNew(chunk, worldX, worldY, worldZ, bytes, setByUser);
//    }

    /**
     * Makes a new entity but does not place it in the world.
     *
     * @param chunk     the chunk to place the entity in
     * @param worldX    the world position of the entity
     * @param worldY    the world position of the entity
     * @param worldZ    the world position of the entity
     * @param bytes     the bytes of the entity for initialization
     * @param setByUser if true, will mark the chunk as modified by the user
     * @return the new entity
     */
    public Entity makeNew(SubChunk chunk, float worldX, float worldY, float worldZ, byte[] bytes, boolean setByUser) {
        if (supplier == null) {
            throw new IllegalArgumentException("Error! Entity " + this.toString() + " Has a missing supplier!");
        }
        if (setByUser) {
            chunk.getParentChunk().markAsModifiedByUser();
        }
        Entity entity = supplier.get();
        entity.link = this;
        entity.chunk = chunk;
        entity.worldPosition.set(worldX, worldY, worldZ);
        entity.needsInit = true;
        entity.loadBytes = bytes;
        entity.initializeImmediate(bytes, false); //Used for loading entities immediately after creation
        return entity;
    }

    /**
     * Places a new entity in the world.
     *
     * @param worldPosX
     * @param worldPosY
     * @param worldPosZ
     * @param setByUser       if true, will mark the chunk as modified by the user
     * @param checkCollisions if true, will check if the entity is colliding
     *                        with any other entities
     * @return the new entity
     */
    public final Entity placeNew(int worldPosX, int worldPosY, int worldPosZ, boolean setByUser) {
        return placeNew(worldPosX, worldPosY, worldPosZ, null, setByUser);
    }

    /**
     * Places a new entity in the world.
     *
     * @param worldPosX
     * @param worldPosY
     * @param worldPosZ
     * @param bytes           the bytes of the entity for initialization
     * @param setByUser       if true, will mark the chunk as modified by the user
     * @param checkCollisions if true, will check if the entity is colliding
     *                        with any other entities
     * @return the new entity
     */
    public final Entity placeNew(int worldPosX, int worldPosY, int worldPosZ, byte[] bytes, boolean setByUser) {
        if (!VoxelGame.getWorld().inBounds(worldPosY)) {
            return null;
        }
        SubChunk chunk = VoxelGame.getWorld().getSubChunk(WCCi.getSubChunkAtWorldPos(worldPosX, worldPosY, worldPosZ));
        if (chunk == null) {
            return null;
        }
        Entity entity = makeNew(chunk, worldPosX, worldPosY, worldPosZ, bytes, setByUser);

//        if (checkCollisions) {
//            // Check if the entity is colliding with any other entities
//            ArrayList<Vector3i> staticBoxes = entity.getStaticBoxes(worldPosX, worldPosY, worldPosZ);
//            if (staticBoxes != null) {
//                for (Entity e : chunk.getEntities().list) {
//                    ArrayList<Vector3i> otherEntityBoxes = e.getStaticBoxes(
//                            (int) e.worldPosition.x,
//                            (int) e.worldPosition.y,
//                            (int) e.worldPosition.z);
//
//                    if (otherEntityBoxes != null) {
//                        for (Vector3i box : otherEntityBoxes) {
//                            for (Vector3i myBox : staticBoxes) {
//                                if (myBox.equals(box)) {
//                                    return null;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        chunk.getParentChunk().markAsNeedsSaving();
        chunk.getEntities().list.add(entity);
        if (entity.hasStaticMeshes()) {
            chunk.generateStaticEntityMesh();
        }
        return entity;
    }
}
