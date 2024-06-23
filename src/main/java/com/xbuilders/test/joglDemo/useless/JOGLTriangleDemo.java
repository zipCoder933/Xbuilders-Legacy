// package com.xbuilders.test.joglDemo.useless;/*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */

// import com.jogamp.opengl.GL4;
// import com.xbuilders.test.joglDemo.mesh.TestTriangleMesh;
// import com.xbuilders.window.CameraNavigator;
// import com.xbuilders.window.MVP;
// import org.joml.Matrix4f;
// import processing.core.UIFrame;
// import processing.event.KeyEvent;
// import processing.event.MouseEvent;
// import processing.opengl.PJOGL;

// import java.io.File;
// import java.io.IOException;
// import java.nio.ByteBuffer;
// import java.nio.ByteOrder;
// import java.nio.FloatBuffer;
// import java.nio.IntBuffer;

// class JOGLTriangleDemo extends UIFrame {

//     final static String basePath = new File("").getAbsolutePath();

//     static {
//         System.out.println("Base path: " + basePath);
//     }


//     Matrix4f projMatrix = new Matrix4f();

//     PJOGL pgl;
//     GL4 gl;


//     public JOGLTriangleDemo() {
//         super();
//         startWindow();

// //        //Make cuveUV match uv coordinate style, but the X coordinate is the vert index
// //        int vertexIndex = 0;
// //        for (int i = 0; i < cubeUV.length; i++) {
// //            cubeUV[i] = 0;
// //        }
// //        for (int i = 0; i < cubeUV.length; i += 2) {
// //            cubeUV[i] =(float) vertexIndex / ((float)cubeVerts.length/3);
// //            vertexIndex++;
// //        }

//     }


//     public static void main(String[] args) {
//         new JOGLTriangleDemo();
//     }

//     @Override
//     public void settings() {
//         size(600, 600, P3D);
//     }

//     CameraNavigator cameraNavigator = new CameraNavigator(this);
//     MVP mvp;

//     public void setup() {


//         pgl = (PJOGL) beginPGL();
//         gl = pgl.gl.getGL4();
//         mvp = new MVP();

//         try {
//             mesh = new TestTriangleMesh(pgl);
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }

//         projMatrix.identity().perspective(
//                 (float) Math.toRadians(60.0f),
//                 (float) width / (float) height,
//                 0.1f,
//                 1000.0f
//         );
//         cameraNavigator.getViewMatrix();

//         pgl = (PJOGL) beginPGL();
//     }

//     TestTriangleMesh mesh;

//     public void draw() {
//         background(255);


//         mesh.draw(gl);


//     }

//     @Override
//     public void mouseEvent(MouseEvent event) {

//     }

//     @Override
//     public void keyTyped(KeyEvent event) {

//     }

//     @Override
//     public void keyPressed(KeyEvent event) {

//     }

//     @Override
//     public void keyReleased(KeyEvent event) {

//     }

//     @Override
//     public void windowFocusGained() {

//     }

//     @Override
//     public void windowFocusLost() {

//     }

//     @Override
//     public void windowCloseEvent() {

//     }

//     @Override
//     public void windowResized() {

//     }

//     FloatBuffer allocateDirectFloatBuffer(int n) {
//         return ByteBuffer.allocateDirect(n * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
//     }

//     IntBuffer allocateDirectIntBuffer(int n) {
//         return ByteBuffer.allocateDirect(n * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
//     }
// }