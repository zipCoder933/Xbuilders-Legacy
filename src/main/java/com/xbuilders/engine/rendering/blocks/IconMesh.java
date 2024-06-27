package com.xbuilders.engine.rendering.blocks;

import com.xbuilders.engine.items.ItemList;
import com.xbuilders.game.Main;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.UIFrame;

public class IconMesh extends BlockMesh_Base {
    PShape buffer;
    public int vertices = 0;

    public IconMesh(UIFrame frame) {
        this.buffer = frame.createShape();
    }

    public void draw(PGraphics pg) {
        buffer.draw(pg);
    }

    @Override
    public void beginShape() {
        buffer.beginShape(PConstants.TRIANGLES);
        buffer.texture(ItemList.blocks.textureAtlas.getImage());
        buffer.noStroke();
    }

    @Override
    public void endShape() {
        buffer.endShape();
        buffer.setTextureMode(0); // This is the secret!
        vertices = buffer.getVertexCount();
    }


    @Override
    public void vertex(float x, float y, float z, float u, float v) {
        buffer.vertex(x, y, z, u, v);
    }
}
