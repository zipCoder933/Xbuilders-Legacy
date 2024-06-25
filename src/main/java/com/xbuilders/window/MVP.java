/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.window;

import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

/**
 * @author zipCoder933
 */
public class MVP {

    FloatBuffer buffer;
    public final Matrix4f matrix; //final just means the object cannot be reassigned

    /*
    TODO: Think About loading projection and view into the constructor
        - we will no longer be putting projection and view matricies into draw mehtods
        - We can still load mvps as static final variables because view and projection are also static
    final Matrix4f projection = new Matrix4f();
    final Matrix4f view = new Matrix4f();
     */

    public MVP() {
        buffer = BufferUtils.allocateDirectFloatBuffer(16);
        matrix = new Matrix4f();
    }

    public MVP(Matrix4f matrix) {
        buffer = BufferUtils.allocateDirectFloatBuffer(16);
        this.matrix = matrix;
    }

    public void update(final Matrix4f projection, final Matrix4f view, final Matrix4f model) {
        matrix.identity().mul(projection).mul(view).mul(model);
        matrix.get(buffer);
    }

    public void update() {
        matrix.get(buffer);
    }

    public void update(Matrix4f matrix) {
        this.matrix.set(matrix);
        this.matrix.get(buffer);
    }

    public void update(Matrix4f projection, Matrix4f view) {
        this.matrix.set(projection).mul(view);
        this.matrix.get(buffer);
    }

//    public void update(final Matrix4f... matrices) {
//        matrix.identity();
//        for (int i = 0; i < matrices.length; i++) {
//            matrix.mul(matrices[i]);
//        }
//        matrix.get(buffer);
//    }

    public void sendToShader(GL4 gl, int programID, int uniformID) {
        gl.glUseProgram(programID);
        gl.glUniformMatrix4fv(uniformID, 1, false, buffer);
    }
}
