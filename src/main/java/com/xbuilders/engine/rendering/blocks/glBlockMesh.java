package com.xbuilders.engine.rendering.blocks;


import com.xbuilders.engine.items.ItemList;
import com.xbuilders.game.Main;
import processing.core.PShape;

public class glBlockMesh extends BlockMesh_Base {
    PShape buffer;
    public int vertices = 0;

    public glBlockMesh() {
        this.buffer = Main.getFrame().createShape();
    }

    public void draw() {
        buffer.draw(Main.getPG());
    }

    public void beginShape() {
        buffer.beginShape(8);
        buffer.texture(ItemList.blocks.textureAtlas.getImage());
        buffer.noStroke();
    }

    public void endShape() {
        buffer.endShape();
//        buffer.setTextureMode(0); // This is the secret!
        vertices = buffer.getVertexCount();
    }

    public void vertex(float x, float y, float z) {
        buffer.vertex(x, y, z);
    }

    public void vertex(float x, float y, float z, float u, float v) {
        buffer.vertex(x, y, z, u, v);
    }
}
