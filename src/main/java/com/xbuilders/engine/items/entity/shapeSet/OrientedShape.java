/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.entity.shapeSet;

import org.joml.Vector3f;
import processing.core.PShape;

/**
 *
 * @author zipCoder933
 */
public class OrientedShape {

    public OrientedShape(float yRotation, Vector3f offset, PShape shape) {
        this.yRotation = yRotation;
        this.offset = offset;
        this.shape = shape;
    }

    public float yRotation;
    public Vector3f offset;
    public PShape shape;


}
