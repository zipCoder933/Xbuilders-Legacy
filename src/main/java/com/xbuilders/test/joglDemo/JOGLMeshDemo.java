package com.xbuilders.test.joglDemo;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL4;
import com.xbuilders.test.joglDemo.mesh.glTextureMesh;
import com.xbuilders.test.joglDemo.shader.TextureShader;
import com.xbuilders.window.CameraNavigator;
import com.xbuilders.window.MVP;
import com.xbuilders.window.utils.OBJ;
import com.xbuilders.window.utils.OBJBufferSet;
import com.xbuilders.window.utils.OBJLoader;
import com.xbuilders.window.utils.texture.TextureUtils;
import org.joml.Matrix4f;
import processing.core.PImage;
import processing.core.UIFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;
import processing.opengl.Texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

class JOGLMeshDemo extends UIFrame {

    final static String basePath = new File("").getAbsolutePath();

    static {
        System.out.println("Base path: " + basePath);
    }


    private final float cubeVerts[] = {
            -1.0f, -1.0f, -1.0f, // triangle 1 : begin
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, // triangle 1 : end
            1.0f, 1.0f, -1.0f, // triangle 2 : begin
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f, // triangle 2 : end
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f
    };

    private final float cuveUV[] = {
            -1.0f, -1.0f, // triangle 1 : begin
            -1.0f, 1.0f,
            1.0f, 1.0f, // triangle 1 : end
            1.0f, -1.0f, // triangle 2 : begin
            -1.0f, -1.0f,
            1.0f, -1.0f, // triangle 2 : end
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, -1.0f,
            -1.0f, -1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f
    };

    TextureShader shader;
    glTextureMesh mesh;
    float a;
    Matrix4f projMatrix = new Matrix4f();

    FloatBuffer posBuffer;
    FloatBuffer uvBuffer;

    PJOGL pgl;
    GL2ES3 gl;


    public JOGLMeshDemo() {
        super();
        startWindow();
    }

    private ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ColorModel glAlphaColorModel = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8, 8},
                true,
                false,
                Transparency.TRANSLUCENT,
                DataBuffer.TYPE_BYTE
        );

        WritableRaster raster = Raster.createInterleavedRaster(
                DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                4,
                null
        );

        BufferedImage texImage = new BufferedImage(
                glAlphaColorModel,
                raster,
                true,
                new Hashtable<>()
        );

        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, 256, 256);
        g.drawImage(bufferedImage, 0, 0, null);

        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
        ByteBuffer imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    public static void main(String[] args) {
        new JOGLMeshDemo();
    }

    @Override
    public void settings() {
        size(600, 600, P3D);
    }

    CameraNavigator cameraNavigator = new CameraNavigator(this);
    MVP mvp = new MVP();

    public void setup() {

        projMatrix.identity().perspective(
                (float) Math.toRadians(60.0f),
                (float) width / (float) height,
                0.1f,
                1000.0f
        );
        cameraNavigator.getViewMatrix();

        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL2ES3();
        GL4 gl4 = pgl.gl.getGL4();
        shader = new TextureShader(this, gl);

        mesh = new glTextureMesh(this, shader.attributePosition, shader.attributeUV);


        OBJ o = null;
        try {
            o = OBJLoader.loadModel(new File(basePath + "\\res\\items\\entities\\animals\\fox\\fox.obj"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        OBJBufferSet bufferSet = new OBJBufferSet(o);


        bufferSet.makeBuffers();
        bufferSet.vertBuffer = cubeVerts;
        bufferSet.uvBuffer = cuveUV;

        posBuffer = allocateDirectFloatBuffer(bufferSet.vertBuffer.length);
        uvBuffer = allocateDirectFloatBuffer(bufferSet.uvBuffer.length);

        posBuffer.rewind();
        posBuffer.put(bufferSet.vertBuffer);
        posBuffer.rewind();

        uvBuffer.rewind();
        uvBuffer.put(bufferSet.uvBuffer);
        uvBuffer.rewind();

        mesh.sendToGPU(posBuffer, uvBuffer);

//        try {
//            image = TextureUtils.loadTexture("C:\\Users\\Patron\\Desktop\\Xbuilders-2-main\\res\\items\\entities\\animals\\fox\\red.png", false);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        IntBuffer ib = IntBuffer.allocate(1);
        try {
            BufferedImage img = ImageIO.read(new File(basePath + "\\res\\items\\entities\\animals\\fox\\red.png"));
            ByteBuffer imageBuffer =
                    convertImageData(img);


            gl.glGenTextures(1, ib);
            gl.glBindTexture(gl.GL_TEXTURE_2D, ib.get(0));
            gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);
            gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
            gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, gl.GL_RGBA, img.getWidth(), img.getHeight(), 0, gl.GL_RGBA, gl.GL_UNSIGNED_BYTE, imageBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mesh.setTexture(ib.get(0));

        endPGL();
    }

    public void draw() {
        background(255);

        pgl = (PJOGL) beginPGL();
        gl = pgl.gl.getGL2ES3();

        shader.bind();
        cameraNavigator.update();
        mvp.update(projMatrix, cameraNavigator.getViewMatrix());
//        mvp.sendToShader(shader.shader.getID(), shader.uniformMVP);
        shader.setMVP(mvp.mvp);

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