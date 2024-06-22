package com.xbuilders.test.joglDemo.demoModified.demo;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.xbuilders.window.CameraNavigator;
import com.xbuilders.window.MVP;

import processing.core.UIFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGL;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;

class JOGLDemoMod extends UIFrame {
    BasicShader shader;
    float a;

    PJOGL pgl;
    GL4 gl;

    public JOGLDemoMod() {
        super();
        startWindow();
    }

    BasicMesh mesh;
    Matrix4f projectMatrix = new Matrix4f();
    Matrix4f viewMatrix;
    Matrix4f modelMatrix = new Matrix4f().scale(0.01f);
    MVP mvp = new MVP();
    CameraNavigator cameraNavigator;

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

        cameraNavigator = new CameraNavigator(this);
        projectMatrix.identity().perspective(
                (float) Math.toRadians(60.0f),
                (float) width / (float) height,
                0.1f,
                1000.0f);
        viewMatrix = cameraNavigator.getViewMatrix();

        shader = new BasicShader(this, pgl);
        mesh = new BasicMesh(gl, shader.attribute_pos, shader.attribute_uv);
        mesh.updateMesh(pgl, gl);

        endPGL();

    }

    public void draw() {
        background(255);

        if (mousePressed) {
            fill(255, 0, 0);
            rect(100, 100, 100, 100);
        }

        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();

        shader.bind();

        cameraNavigator.update();
        mvp.update(projectMatrix, viewMatrix,modelMatrix);
        mvp.sendToShader(gl, shader.getID(), shader.uniform_MVP);

        mesh.drawMesh(pgl, gl);
        shader.unbind();

        endPGL();

        a += 0.01;
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