/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode.tools.copyPaste;

import com.xbuilders.engine.items.entity.EntityTemplate;
import com.xbuilders.game.items.other.BlockGrid;

import java.util.HashMap;
import org.joml.Vector3f;

/**
 *
 * @author zipCoder933
 */
public class Clipboard {

    public Clipboard(int sizeX, int sizeY, int sizeZ) {
        blocks = new BlockGrid(sizeX, sizeY, sizeZ);
        entities = new HashMap<>();
    }

    public  BlockGrid blocks;
    public HashMap<Vector3f, EntityTemplate> entities;
}
