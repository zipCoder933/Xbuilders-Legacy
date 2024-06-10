/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.game;

import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.clouds.Clouds;
import com.xbuilders.game.PointerHandler;
import org.joml.Vector3f;
import org.joml.Vector3i;
import static processing.core.PConstants.SPHERE;
import processing.core.PShape;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class SkyDome {

    private Clouds clouds;
    private Vector3f bgColor;
    private PShape shape;
    private PointerHandler ph;

    public void newGame() {
        clouds.initialize();
    }

    public SkyDome(UIExtensionFrame f, PointerHandler ph) {
        this.ph = ph;
        f.sphereDetail(5);
        shape = f.createShape(SPHERE, 300);
        shape.setStroke(false);
        clouds = new Clouds(ph);
        bgColor = new Vector3f(0, 0, 0);
    }

    public void drawEffects(UIExtensionFrame parentFrame, ShaderHandler shaderHandler) {
        World world = ph.getWorld();
        if (world.terrain.isEnableClouds()) {
            if (!ph.getPlayer().isInDarkness()) {
                clouds.draw(ph.getApplet().getGraphics());
            }
        }
    }

    public void draw(UIExtensionFrame graphics, ShaderHandler shader) {
        Vector3i targetColor;
        if (shader.isNaturalBackgroundEnabled()) {
            if (ph.getPlayer().isInDarkness()) {
                targetColor = new Vector3i(0, 0, 0);
            } else {
                targetColor = MathUtils.floatToInt(shader.getSkyColor());
            }
            final double TRANSITION_SPEED = 0.1;
            bgColor.x = (float) MathUtils.curve(bgColor.x, targetColor.x, TRANSITION_SPEED);
            bgColor.y = (float) MathUtils.curve(bgColor.y, targetColor.y, TRANSITION_SPEED);
            bgColor.z = (float) MathUtils.curve(bgColor.z, targetColor.z, TRANSITION_SPEED);
        } else {
            bgColor = shader.getSkyColor();
        }

//        if (ph.getSettingsFile().gradientSky && ph.getWorld().getTerrain().isEnableGradientSky() && ph.getPlayer().camera.center != null) {
//            graphics.background(bgColor.x * 0.8f, bgColor.y * 0.8f, bgColor.z);
//            graphics.pushStyle();
//            setDomeColor(bgColor, shader);
//            graphics.shader(shader.backgroundShader);
//            graphics.pushMatrix();
//            graphics.stroke(255);
//                    graphics.camera(0,0,0, //p (camera position)
//                ph.getPlayer().camera.center.x, ph.getPlayer().camera.center.y, ph.getPlayer().camera.center.z, //l (3D scene origin)
//                ph.getPlayer().camera.up.x, ph.getPlayer().camera.up.y, ph.getPlayer().camera.up.z);//u (up)
//            graphics.translate(ph.getPlayer().getPosition().x, ph.getPlayer().getPosition().y, ph.getPlayer().getPosition().z);
//            graphics.shape(shape);
//            graphics.popMatrix();
//            graphics.popStyle();
//        } else {
        graphics.background(bgColor.x, bgColor.y, bgColor.z);
//        }
    }

//    private void setDomeColor(Vector3f bgColor, ShaderHandler shader) {
////        shader.backgroundShader.set("skyColor",
////                (float) (bgColor.x) / 255,
////                (float) (bgColor.y) / 255,
////                (float) (bgColor.z) / 255);
//        if (bgColor.x == 0 && bgColor.y == 0 && bgColor.z == 0) {
//            shader.backgroundShader.set("domeColor", 0.0f, 0.0f, 0.0f);
//        } else {
//            shader.backgroundShader.set("domeColor",
//                    (float) (bgColor.x + 40) / 255,
//                    (float) (bgColor.y + 40) / 255,
//                    (float) (bgColor.z + 40) / 255);
//        }
//    }

}
