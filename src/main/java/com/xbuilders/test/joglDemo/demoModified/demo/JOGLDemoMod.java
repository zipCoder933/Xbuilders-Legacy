package com.xbuilders.test.joglDemo.demoModified.demo;

import com.jogamp.opengl.GL4;
import com.xbuilders.window.CameraNavigator;
import com.xbuilders.window.MVP;

import com.xbuilders.window.mesh.glTextureMesh;
import com.xbuilders.window.shader.DemoTextureShader;
import processing.core.UIFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

class JOGLDemoMod extends UIFrame {

    PJOGL pgl;
    GL4 gl;
    WrappedDemo demo;

    public JOGLDemoMod() {
        super();
        startWindow();
    }


    public static void main(String[] args) {
        new JOGLDemoMod();
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        PJOGL.profile = 4;
    }

    public void setup() {
        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();


        demo = new WrappedDemo(this, pgl);


        endPGL();

    }

    public void draw() {
        background(255);

        if (mousePressed) {
            fill(255, 0, 0);
            rect(100, 100, 100, 100);
        }

        demo.draw();
    }

    @Override
    public void mouseEvent(MouseEvent event) {

    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void windowFocusGained() {

    }

    @Override
    public void windowFocusLost() {

    }

    @Override
    public void windowCloseEvent() {

    }

    @Override
    public void windowResized() {

    }

    FloatBuffer allocateDirectFloatBuffer(int n) {
        return ByteBuffer.allocateDirect(n * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    IntBuffer allocateDirectIntBuffer(int n) {
        return ByteBuffer.allocateDirect(n * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
    }
}