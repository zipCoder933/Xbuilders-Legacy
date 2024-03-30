/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.boundaryBlocks;

import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.holograms.Hologram;
import com.xbuilders.game.items.other.BlockGrid;
import com.xbuilders.game.items.other.BlockMesh;
import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.PGraphics;
import processing.core.PShape;

import static com.xbuilders.game.items.other.copyPaste.BlockPaste.getStartPosOffset;

/**
 * @author zipCoder933
 */
public class BoundingBox extends Hologram {

    public static AABB makeAABBFrom2Points(Vector3i start, Vector3i end) {
        Vector3i end2 = new Vector3i(end);
        Vector3i start2 = new Vector3i(start);

        if (start.x <= end.x) {
            end2.x++;
        } else {
            end2.x--;
        }

        if (start.y <= end.y) {
            end2.y++;
        } else {
            end2.y--;
        }

        if (start.z <= end.z) {
            end2.z++;
        } else {
            end2.z--;
        }

        AABB aabb = new AABB().setPosAndSize(
                Math.min(start2.x, end2.x + 1),
                Math.min(start2.y, end2.y + 1),
                Math.min(start2.z, end2.z + 1),
                (int) MathUtils.dist((int) start2.x, (int) end2.x),
                (int) MathUtils.dist((int) start2.y, (int) end2.y),
                (int) MathUtils.dist((int) start2.z, (int) end2.z));

        return aabb;
    }

    public final Vector3i size = new Vector3i();
    private StartBoundary startBoundary;

    public BoundingBox(StartBoundary startBoundary, AABB aabb) {
        super();
        this.startBoundary = startBoundary;
        set(aabb);
    }

    public BoundingBox() {
        super();
    }

    public void set(AABB aabb) {
        size.set((int) aabb.getXLength(), (int) aabb.getYLength(), (int) aabb.getZLength());
        final Vector3f worldPosition = new Vector3f(aabb.minPoint);
        this.worldPosition.set(worldPosition);
    }

    public void set(Vector3i point1, Vector3i point2) {
        AABB aabb = makeAABBFrom2Points(point1, point2);
        size.set((int) aabb.getXLength(), (int) aabb.getYLength(), (int) aabb.getZLength());
        final Vector3f worldPosition = new Vector3f(aabb.minPoint);
        this.worldPosition.set(worldPosition);
    }

    private PShape mesh = null;

    public void setMesh(Vector3i startPos, BlockGrid clipboard, boolean wireframe, boolean textured) {
        size.set(
                clipboard.size.x,
                clipboard.size.y,
                clipboard.size.z);
        mesh = BlockMesh.createMesh(clipboard, wireframe, textured);
        final Vector3f worldPosition = getStartPosOffset(startPos.x, startPos.y, startPos.z);
        this.worldPosition.set(worldPosition);
    }

    public void setMesh(BlockGrid clipboard, boolean wireframe, boolean textured) {
        size.set(
                clipboard.size.x,
                clipboard.size.y,
                clipboard.size.z);
        mesh = BlockMesh.createMesh(clipboard, wireframe, textured);
        this.worldPosition.set(worldPosition);
    }

    boolean isShown = true;

    public void show() {
        isShown = true;
    }

    public void hide() {
        isShown = false;
    }

    public boolean isShown() {
        return isShown;
    }

    @Override
    public void render(PGraphics g) {
        if (startBoundary != null
                && (startBoundary.firstMarker == null || startBoundary.secondMarker == null)) {
            remove();
        }
        if (isShown) {
            g.noFill();
            g.strokeWeight(3);
            g.stroke(0, 0, 255);
            if (mesh != null) g.shape(mesh);
            g.translate((float) size.x / 2, (float) size.y / 2, (float) size.z / 2);
            g.box(size.x, size.y, size.z);
        }
    }

}
