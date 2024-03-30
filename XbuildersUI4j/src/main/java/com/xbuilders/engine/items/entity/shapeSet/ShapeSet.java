/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.entity.shapeSet;

import java.util.ArrayList;
import processing.core.PImage;

/**
 *
 * @author zipCoder933
 */
public class ShapeSet {

    public ArrayList<OrientedShape> shapes;
    public PImage texture;

    public ShapeSet(ArrayList<OrientedShape> shapes, PImage texture) {
        this.shapes = shapes;
        this.texture = texture;
    }
}
