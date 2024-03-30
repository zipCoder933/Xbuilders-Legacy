/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.worldInteraction.collision;

import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author zipCoder933
 */
public class EntityAABB {

    public EntityAABB() {
        box = new AABB();
        offset = new Vector3f();
        size = new Vector3f();
        worldPosition = new Vector3f();
        setOffsetAndSize(0, 0, 0, 1, 1, 1);
    }

    /**
     * Sets the offset and size of the object.
     *
     * @param offsetX the X coordinate of the offset
     * @param offsetY the Y coordinate of the offset
     * @param offsetZ the Z coordinate of the offset
     * @param sizeX   the size along the X axis
     * @param sizeY   the size along the Y axis
     * @param sizeZ   the size along the Z axis
     */
    public void setOffsetAndSize(float offsetX, float offsetY, float offsetZ,
                                 float sizeX, float sizeY, float sizeZ) {
        offset.set(offsetX, offsetY, offsetZ);
        size.set(sizeX, sizeY, sizeZ);
        updateBox();
    }

    public void updateBox() {
        worldPosition.y = MathUtils.clamp(worldPosition.y, -1000, 254);
        worldPosition.x = MathUtils.clamp(worldPosition.x, SubChunk.WIDTH * -1500, SubChunk.WIDTH * 1500);
        worldPosition.z = MathUtils.clamp(worldPosition.z, SubChunk.WIDTH * -1500, SubChunk.WIDTH * 1500);
//        System.out.println("EntityAABB: " +  MiscUtils.printVector(offset) + " " + MiscUtils.printVector(size));
        box.setPosAndSize(
                worldPosition.x + offset.x,
                worldPosition.y + offset.y,
                worldPosition.z + offset.z,
                size.x, size.y, size.z);
//        System.out.println("EntityAABB: " +  MiscUtils.printVector(offset) + " " + MiscUtils.printVector(size));
    }


    public final Vector3f worldPosition;
    public final AABB box;
    public final Vector3f offset;
    public final Vector3f size;
    public boolean collisionEnabled=true;
}
