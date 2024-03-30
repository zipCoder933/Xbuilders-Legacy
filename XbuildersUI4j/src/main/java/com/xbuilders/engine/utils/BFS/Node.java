/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.BFS;

import org.joml.Vector3i;


/**
 *
 * @author zipCoder933
 */
public class Node {

    /**
     * @return the coords
     */
    public Vector3i getCoords() {
        return coords;
    }

    /**
     * @param coords the coords to set
     */
    public void setCoords(Vector3i coords) {
        this.coords = coords;
    }

    public Node(int x, int y, int z) {
        this.coords = new Vector3i(x, y, z);
    }

    public Node(Vector3i coords) {
        this.coords = new Vector3i(coords);
    }

    public Node() {
        this.coords = new Vector3i(0, 0, 0);
    }

    private Vector3i coords;

    public String toFileString(char delimiter) {
        return "" + getCoords().x + "" + delimiter + getCoords().y + "" + delimiter + getCoords().z;
    }

    public void fromFileString(String str, char delimiter) {
        String[] pieces = str.split("" + delimiter);
        getCoords().x = Integer.parseInt(pieces[0]);
        getCoords().y = Integer.parseInt(pieces[1]);
        getCoords().z = Integer.parseInt(pieces[2]);
    }
}
