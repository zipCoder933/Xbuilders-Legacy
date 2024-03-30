/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.player;

import com.xbuilders.engine.items.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @author zipCoder933
 */
public class PositionLock {


    public PositionLock(Entity lock) {
        this.entity = lock;
        this.playerDisplacement = new Matrix4f();
    }

    public PositionLock(Entity lock, float yOffset) {
        this.entity = lock;
        this.playerDisplacement = new Matrix4f().translate(0.5f, yOffset, 0.5f);
    }

    public final Entity entity;
    public final Matrix4f playerDisplacement;//Meant to be updated by the entity every frame or so
    private final Vector3f translation = new Vector3f();//Calculated from displacement, used to position the player relatively to the entity
    private final Vector3f position = new Vector3f();

    public Vector3f getPosition() {
        position.set(entity.worldPosition).add(playerDisplacement.getTranslation(translation));
        return position;
    }
}
