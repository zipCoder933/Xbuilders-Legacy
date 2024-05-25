/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.terrain;

import com.xbuilders.engine.world.chunk.SubChunk;

/**
 *
 * @author zipCoder933
 */
public class TorchValue {

    public SubChunk chunk;
    public int x, y, z;
    public byte falloff;

    public TorchValue(SubChunk chunk, int x, int y, int z, byte falloff) {
        this.chunk = chunk;
        this.x = x;
        this.y = y;
        this.z = z;
        this.falloff = falloff;
    }
}
