/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.copyPaste;

import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.entity.EntityTemplate;
import com.xbuilders.game.items.other.BlockGrid;
import java.util.ArrayList;
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

    BlockGrid blocks;
    HashMap<Vector3f, EntityTemplate> entities;
}
