package com.xbuilders.test.joglDemo.demoModified.demo;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
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

class JOGLDemoMod extends UIFrame {
    PShader shader;
    float a;


    PJOGL pgl;
    GL4 gl;

    public JOGLDemoMod() {
        super();
        startWindow();
    }

    BasicMesh mesh;

    public static void main(String[] args) {
        new JOGLDemoMod();
    }

    @Override
    public void settings() {
        size(800, 600, P3D);
        PJOGL.profile = 4;
    }

    public void setup() {

        String localDir = new File("").getAbsolutePath();
        shader = loadShader(
                localDir + "\\src/main/java/com/xbuilders/test/joglDemo/demo/frag.glsl",
                localDir + "\\src/main/java/com/xbuilders/test/joglDemo/demo/vert.glsl");


        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();


        // Get the location of the attribute variables.
        shader.bind();
        int posLoc = gl.glGetAttribLocation(shader.glProgram, "position");
        int colorLoc = gl.glGetAttribLocation(shader.glProgram, "color");
        shader.unbind();

        mesh = new BasicMesh(gl, posLoc, colorLoc);
        mesh.updateMesh(pgl, gl);

        endPGL();

    }

    public void draw() {
        background(255);

        if (mousePressed) {
            fill(255, 0, 0);
            rect(100, 100, 100, 100);
        }

        // Geometry transformations from Processing are automatically passed to the shader
        // as long as the uniforms in the shader have the right names.
        translate(width / 2, height / 2);
        rotateX(a);
        rotateY(a * 2);



        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();

        shader.bind();
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