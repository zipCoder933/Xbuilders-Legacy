/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode.tools.copyPaste;

import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.engine.world.holograms.Hologram;
import com.xbuilders.engine.rendering.StandaloneBlockMesh;
import org.joml.Vector3i;
import processing.core.PGraphics;
import processing.core.PShape;

/**
 * @author zipCoder933
 */
public class PastePreview extends Hologram {

    public PastePreview(BlockOrientation orientation, Clipboard clipboard) {
        super();
        initialize(clipboard);
    }

    public final Vector3i size = new Vector3i(0, 0, 0);
    private PShape mesh = null;

    public void initialize(Clipboard clipboard) {
        size.set(
                clipboard.blocks.size.x,
                clipboard.blocks.size.y,
                clipboard.blocks.size.z);

        mesh = StandaloneBlockMesh.createMesh(clipboard.blocks, true, true);
        this.worldPosition.set(worldPosition);
    }


    @Override
    public void render(PGraphics g) {  g.resetShader();

        g.shape(mesh);


        g.noFill();
        g.strokeWeight(3);
        g.stroke(0, 200, 0);

        g.translate((float) size.x / 2, (float) size.y / 2, (float) size.z / 2);
        g.box(size.x, size.y, size.z);
    }
}
