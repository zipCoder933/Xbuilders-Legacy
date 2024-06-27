package com.xbuilders.engine.rendering.blocks;


public abstract class BlockMesh_Base {

    public abstract void beginShape();

    public abstract void endShape();

    public abstract void vertex(float x, float y, float z);

    public abstract void vertex(float x, float y, float z, float u, float v);

}
