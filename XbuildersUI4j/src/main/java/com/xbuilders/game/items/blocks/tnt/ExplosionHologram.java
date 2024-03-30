/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.tnt;

import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.holograms.Hologram;
import com.xbuilders.game.PointerHandler;

import java.awt.Color;
import java.util.ArrayList;

import org.joml.Vector3f;
import processing.core.PGraphics;

/**
 * @author zipCoder933
 */
public class ExplosionHologram extends Hologram {

    long startTime;
    ArrayList<Vector3f> points;
    float val = 1.35f;
    float velocityMultiplier;
    boolean large;

    public ExplosionHologram(PointerHandler ph, Vector3f origin, boolean large) {
        super();
        this.velocityMultiplier = large ? 1.03f : 1f;
        this.large = large;
        final Vector3f worldPosition = new Vector3f(origin).add(0.5f, 0.5f, 0.5f);
        this.worldPosition.set(worldPosition);
        startTime = System.currentTimeMillis();
        points = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            points.add(new Vector3f(
                    randomFloat(),
                    randomFloat(),
                    randomFloat()));
        }
    }

    private float randomFloat() {
        return (float) (Math.random() - 0.5f) * 0.2f;
    }

    @Override
    public void render(PGraphics graphics) {
        int time = (int) (System.currentTimeMillis() - startTime);
        if (time < 900) {
            Color color = MiscUtils.mixColors(Color.WHITE, Color.DARK_GRAY, (double) time / 1000);
            graphics.stroke(color.getRed(), color.getGreen(), color.getBlue());
            graphics.strokeWeight((float) MathUtils.mapAndClamp(time, 0, 1000, 15, 2));

            for (int i = 0; i < points.size(); i++) {
                graphics.pushMatrix();
                graphics.translate(points.get(i).x, points.get(i).y, points.get(i).z);
                points.get(i).mul(val * velocityMultiplier);
                graphics.rotateX((float) Math.random());
                graphics.rotateZ((float) Math.random());
                graphics.box(large ? 0.6f : 0.45f);
                graphics.popMatrix();
            }
            val -= 0.015f * velocityMultiplier;
        }
        if (time > 1000 || val < 1) {
            remove();
        }
    }

}
