/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.copyPaste;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.world.holograms.Hologram;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;

import static com.xbuilders.game.items.other.copyPaste.BlockPaste.getStartPosOffset;

import com.xbuilders.game.items.other.BlockMesh;
import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.PGraphics;
import processing.core.PShape;

/**
 * @author zipCoder933
 */
public class PasteBoundingBox extends Hologram {

    public PasteBoundingBox(BlockOrientation orientation, Vector3i pasteBlockPos, Clipboard clipboard) {
        super();
        isBuilding = false;
        initialize(pasteBlockPos, clipboard);
    }

    /**
     * @return the isBuilding
     */
    public boolean isBuilding() {
        return isBuilding;
    }

    public final Vector3i size = new Vector3i(0, 0, 0);
    private PShape mesh = null;

    public void initialize(Vector3i pasteBlockPos, Clipboard clipboard) {
        size.set(
                clipboard.blocks.size.x,
                clipboard.blocks.size.y,
                clipboard.blocks.size.z);

        initializeMesh(clipboard, false);

        final Vector3f worldPosition = getStartPosOffset(pasteBlockPos.x, pasteBlockPos.y, pasteBlockPos.z);
        this.worldPosition.set(worldPosition);
    }

    protected void initializeMesh(Clipboard clipboard, boolean wireframe) {
        mesh = BlockMesh.createMesh(clipboard.blocks, wireframe,!wireframe);
    }

    private boolean isBuilding = false;

    public void markAsBuilding() {
        isBuilding = true;
        initializeMesh(CopyTool.clipboard, true);
    }

    @Override
    public void render(PGraphics g) {
        if (size != null) {
            if (isBuilding()) {
                if (VoxelGame.ph().getPlayer().blockTools.blockSetter.queueIsEmpty()) {
                    remove();
                }
            } else {
                if (BlockPaste.pasteBlock == null) {
                    remove();
                }
            }
            g.noFill();
            g.strokeWeight(3);
            g.stroke(0, 200, 0);
            g.shape(mesh);
            g.translate((float) size.x / 2, (float) size.y / 2, (float) size.z / 2);
            g.box(size.x, size.y, size.z);
        }
    }
}
