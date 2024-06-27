package com.xbuilders.engine.rendering.blocks;


import com.xbuilders.engine.items.ItemList;
import com.xbuilders.game.Main;
import com.xbuilders.window.MVP;
import org.joml.Matrix4f;
import processing.core.PShape;

public class p3dBlockMesh extends BlockMesh_Base {
    public PShape shape;
    public int vertices = 100;
    public boolean wireframe = false;
    MVP mvp = new MVP();

    public p3dBlockMesh() {
        this.shape = Main.getFrame().createShape();
        reset();
    }

    public void reset() {
        this.shape = Main.getFrame().createShape();
    }

    public void draw(Matrix4f modelMatrix) {
        shape.draw(Main.getPG());
    }

    public void beginShape() {
        reset();
        shape.beginShape(8);
        shape.texture(ItemList.blocks.textureAtlas.getImage());

        if (wireframe) {
            shape.stroke(255);
            shape.strokeWeight(1);
        } else shape.noStroke();
    }

    public void endShape() {
        shape.endShape();
        vertices = shape.getVertexCount();
    }

    public void vertex(float x, float y, float z, float u, float v) {
        shape.vertex(x, y, z, u, v);
    }


}
