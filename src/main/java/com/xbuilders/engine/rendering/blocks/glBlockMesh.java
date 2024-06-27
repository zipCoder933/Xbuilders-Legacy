package com.xbuilders.engine.rendering.blocks;


import com.xbuilders.engine.items.ItemList;
import com.xbuilders.game.Main;
import com.xbuilders.window.MVP;
import org.joml.Matrix4f;
import processing.core.PShape;

public class glBlockMesh extends BlockMesh_Base {
    public PShape shape;
    public int vertices = 100;
    public boolean wireframe = false;

//    public glTextureMesh textureMesh;
//    ArrayList<Float> positions = new ArrayList<>();
//    ArrayList<Float> uvs = new ArrayList<>();
    MVP mvp = new MVP();

    public glBlockMesh() { //TODO: This mesh HAS to be updated and created ON THE MAIN THREAD
        this.shape = Main.getFrame().createShape();
        reset();
//        PJOGL pgl = Main.beginPJOGL();
//        textureMesh = new glTextureMesh(pgl,
//                ShaderHandler.entityShader.attribute_pos,
//                ShaderHandler.entityShader.attribute_uv);
//        Main.endPJOGL();
    }

    public void reset() {
        this.shape = Main.getFrame().createShape();
//        positions.clear();
//        uvs.clear();
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
        //textureMesh.sendToGPU(positions, uvs);
        vertices = shape.getVertexCount();
    }

    public void vertex(float x, float y, float z, float u, float v) {
        shape.vertex(x, y, z, u, v);

//        positions.add(x);
//        positions.add(y);
//        positions.add(z);
//        positions.add(1.0f);
//        uvs.add(u);
//        uvs.add(v);
    }


}
