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
public class TravelNode extends Node {

    /**
     * @return the travel
     */
    public int getTravel() {
        return travel;
    }

    /**
     * @param travel the travel to set
     */
    public void setTravel(int travel) {
        this.travel = travel;
    }

    private int travel;

    public TravelNode(int x, int y, int z, int travel) {
        super(x, y, z);
        this.travel = travel;
    }

    public TravelNode(Vector3i coords, int travel) {
        super(coords);
        this.travel = travel;
    }

    public TravelNode() {
        super();
        this.travel = 0;
    }

    @Override
    public String toFileString(char delimiter) {
        return "" + getCoords().x + "" + delimiter + getCoords().y + "" + delimiter + getCoords().z + "" + delimiter + travel;
    }

    @Override
    public void fromFileString(String str, char delimiter) {
        String[] pieces = str.split("" + delimiter);
        getCoords().x = Integer.parseInt(pieces[0]);
        getCoords().y = Integer.parseInt(pieces[1]);
        getCoords().z = Integer.parseInt(pieces[2]);
        travel = Integer.parseInt(pieces[3]);
    }
}
