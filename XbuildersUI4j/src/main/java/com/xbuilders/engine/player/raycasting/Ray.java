package com.xbuilders.engine.player.raycasting;

import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;

import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.PGraphics;

public class Ray {


    public Vector3i getHitPositionAsInt() {
        return MathUtils.floatToInt(hitPostition);
    }

    public Vector3i getHitNormalAsInt() {
        return MathUtils.floatToInt(hitNormal);
    }

    /**
     * @return the hitPosPlusNormal
     */
    public Vector3i getHitPosPlusNormal() {
        return MathUtils.floatToInt(hitPostition).add((int) hitNormal.x, (int) hitNormal.y, (int) hitNormal.z);
    }

    public Ray() {
        hitTarget = false;
        hitPostition = new Vector3f();
        hitNormal = new Vector3f();
        origin = new Vector3f();
        direction = new Vector3f();
    }

    public boolean hitTarget;
    public final Vector3f origin;
    public final Vector3f direction;
    public final Vector3f hitPostition;
    public final Vector3f hitNormal;
    public float distanceTraveled;
    public Entity entity;
    public List<AABB> cursorBoxes;

    @Override
    public String toString() {
        return "Ray{" + "hitTarget=" + hitTarget + ", origin=" + MiscUtils.printVector(origin) + ", direction=" + MiscUtils.printVector(direction) + ",\n"
                + "hitPostition=" + MiscUtils.printVector(hitPostition) + ", hitNormal=" + MiscUtils.printVector(hitNormal) + ", distanceTraveled=" + distanceTraveled + ",\n"
                + "entity=" + entity + ", cursorBoxes=" + cursorBoxes + '}';
    }

    protected void reset() {
        distanceTraveled = 0;
        hitTarget = false;
        hitNormal.set(0, 0, 0);
        hitPostition.set(0, 0, 0);
        cursorBoxes = null;
        entity = null;
    }

//    private static final float add = 0.025f;
//    private static float halfAdd = add / 2;
    public void drawCursorBlock(PGraphics graphics) {
            graphics.pushStyle();
            graphics.stroke(255);
            graphics.noFill();
            if (cursorBoxes == null) {
                graphics.strokeWeight(2);
                graphics.translate(0.5f + hitPostition.x, 0.5f + hitPostition.y, 0.5f + hitPostition.z);
                graphics.box(1.02f);
            } else {
                graphics.strokeWeight(4);
                List<AABB> cursorAABBs1 = cursorBoxes;
                for (int i = 0; i < cursorAABBs1.size(); i++) {
                    AABB box = cursorAABBs1.get(i);
                    graphics.pushMatrix();
                    graphics.translate(
                            box.minPoint.x + (box.getXLength() / 2),
                            box.minPoint.y + (box.getYLength() / 2),
                            box.minPoint.z + (box.getZLength() / 2));
                    graphics.box(
                            box.getXLength(),
                            box.getYLength(),
                            box.getZLength());
                    graphics.popMatrix();
                }
                graphics.translate(0.5f + hitPostition.x, 0.5f + hitPostition.y, 0.5f + hitPostition.z);
            }
            graphics.popStyle();
        }
}
