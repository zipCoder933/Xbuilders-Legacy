package com.xbuilders.engine.rendering.blocks;

import processing.core.PShape;

public class BlockBufferSet {
    PShape buffer;


    public BlockBufferSet(PShape buffer) {
        this.buffer = buffer;
    }

    public void endShape() {
        buffer.endShape();
    }

    public void vertex(float x, float y, float z) {
        buffer.vertex(x, y, z);
    }
    public void vertex(float x, float y, float z, float u, float v) {
        buffer.vertex(x, y, z, u, v);
    }
}
