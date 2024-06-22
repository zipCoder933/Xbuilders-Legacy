package com.xbuilders.test.joglDemo;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.jogamp.opengl.GL4;
import com.xbuilders.test.joglDemo.mesh.glTextureMesh;
import com.xbuilders.test.joglDemo.shader.glTextureShader;
import com.xbuilders.test.joglDemo.shader.uiTextureShader;
import com.xbuilders.window.CameraNavigator;
import com.xbuilders.window.MVP;
import com.xbuilders.window.utils.obj.OBJ;
import com.xbuilders.window.utils.obj.OBJLoader;
import com.xbuilders.window.utils.obj.buffers.OBJBufferSet;
import com.xbuilders.window.utils.texture.Texture;
import com.xbuilders.window.utils.texture.TextureUtils;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

class JOGLMeshDemo extends UIFrame {

    final static String basePath = new File("").getAbsolutePath();

    static {
        System.out.println("Base path: " + basePath);
    }


    uiTextureShader shader;
    glTextureMesh mesh;
    float a;
    Matrix4f projMatrix = new Matrix4f();

    FloatBuffer posBuffer;
    FloatBuffer uvBuffer;

    PJOGL pgl;
    GL4 gl;


    public JOGLMeshDemo() {
        super();
        startWindow();

//        //Make cuveUV match uv coordinate style, but the X coordinate is the vert index
//        int vertexIndex = 0;
//        for (int i = 0; i < cubeUV.length; i++) {
//            cubeUV[i] = 0;
//        }
//        for (int i = 0; i < cubeUV.length; i += 2) {
//            cubeUV[i] =(float) vertexIndex / ((float)cubeVerts.length/3);
//            vertexIndex++;
//        }

    }


    public static void main(String[] args) {
        new JOGLMeshDemo();
    }

    @Override
    public void settings() {
        size(600, 600, P3D);
    }

    CameraNavigator cameraNavigator = new CameraNavigator(this);
    MVP mvp;

    public void setup() {


        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();
        mvp = new MVP();




        projMatrix.identity().perspective(
                (float) Math.toRadians(60.0f),
                (float) width / (float) height,
                0.1f,
                1000.0f
        );
        cameraNavigator.getViewMatrix();

        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();
        try {
            shader = new uiTextureShader(this,pgl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mesh = new glTextureMesh(gl, shader.attributePosition, shader.attributeUV);


        OBJ o = null;
        try {
            o = OBJLoader.loadModel(new File(basePath + "\\res\\items\\entities\\animals\\fox\\fox.obj"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        OBJBufferSet bufferSet = new OBJBufferSet(o);


        bufferSet.makeBuffers();

        posBuffer = allocateDirectFloatBuffer(bufferSet.vertBuffer.length);
        uvBuffer = allocateDirectFloatBuffer(bufferSet.uvBuffer.length);

        posBuffer.rewind();
        posBuffer.put(bufferSet.vertBuffer);
        posBuffer.rewind();

        uvBuffer.rewind();
        uvBuffer.put(bufferSet.uvBuffer);
        uvBuffer.rewind();

        mesh.sendToGPU(posBuffer, uvBuffer);

        //Load texture
        Texture tex;
        try {
            tex = TextureUtils.loadTexture(gl, basePath + "\\res\\items\\entities\\animals\\fox\\red.png", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mesh.setTexture(tex.id);
        endPGL();
    }

    public void draw() {
        background(255);

        //Enable backface culling
        gl.glEnable(GL4.GL_CULL_FACE);
        gl.glCullFace(GL4.GL_BACK);

        if (mousePressed) {
            fill(255, 0, 0);
            rect(100, 100, 100, 100);
        }


        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL4();


        shader.bind();
        cameraNavigator.update();
        mvp.update(projMatrix, cameraNavigator.getViewMatrix());
        mvp.sendToShader(gl, shader.getID(), shader.uniformMVP);
        mesh.draw();
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