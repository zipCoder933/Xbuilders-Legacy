package com.xbuilders.test.joglDemo.demoModified.demo;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.xbuilders.window.BufferUtils;
import processing.opengl.PGL;
import processing.opengl.PJOGL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BasicMesh {


    FloatBuffer posBuffer;
    FloatBuffer colorBuffer;
    IntBuffer indexBuffer;

    int posVboId;
    int colorVboId;
    int indexVboId;

    int posLoc;
    int colorLoc;

    public BasicMesh(GL4 gl, int posLoc, int colorLoc) {
        this.posLoc = posLoc;
        this.colorLoc = colorLoc;

        // Get GL ids for all the buffers
        IntBuffer intBuffer = IntBuffer.allocate(3);
        gl.glGenBuffers(3, intBuffer);// Generate 3 VBOs
// Now buffers[0], buffers[1], and buffers[2] contain the names of the VBOs

        posVboId = intBuffer.get(0);
        colorVboId = intBuffer.get(1);
        indexVboId = intBuffer.get(2);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glVertexAttribPointer(posLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, colorVboId);
        gl.glVertexAttribPointer(colorLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

        gl.glEnableVertexAttribArray(posLoc);
        gl.glEnableVertexAttribArray(colorLoc);
    }


    final float HALF_PI = (float) Math.PI / 2.0f;
    float[] positions, colors;
    int[] indices;

    public void updateMesh(PJOGL pgl, GL4 gl) {

        positions = new float[32];
        colors = new float[32];
        indices = new int[12];

        // Vertex 1
        positions[0] = -200;
        positions[1] = -200;
        positions[2] = 0;
        positions[3] = 1;

        colors[0] = 1.0f;
        colors[1] = 0.0f;
        colors[2] = 0.0f;
        colors[3] = 1.0f;

        // Vertex 2
        positions[4] = +200;
        positions[5] = -200;
        positions[6] = 0;
        positions[7] = 1;

        colors[4] = 1.0f;
        colors[5] = 1.0f;
        colors[6] = 0.0f;
        colors[7] = 1.0f;

        // Vertex 3
        positions[8] = -200;
        positions[9] = +200;
        positions[10] = 0;
        positions[11] = 1;

        colors[8] = 0.0f;
        colors[9] = 1.0f;
        colors[10] = 0.0f;
        colors[11] = 1.0f;

        // Vertex 4
        positions[12] = +200;
        positions[13] = +200;
        positions[14] = 0;
        positions[15] = 1;

        colors[12] = 0.0f;
        colors[13] = 1.0f;
        colors[14] = 1.0f;
        colors[15] = 1.0f;

        // Vertex 5
        positions[16] = -200;
        positions[17] = -200 * (float)Math.cos(HALF_PI);
        positions[18] = -200 * (float)Math.sin(HALF_PI);
        positions[19] = 1;

        colors[16] = 0.0f;
        colors[17] = 0.0f;
        colors[18] = 1.0f;
        colors[19] = 1.0f;

        // Vertex 6
        positions[20] = +200;
        positions[21] = -200 *(float)Math.cos(HALF_PI);
        positions[22] = -200 * (float)Math.sin(HALF_PI);
        positions[23] = 1;

        colors[20] = 1.0f;
        colors[21] = 0.0f;
        colors[22] = 1.0f;
        colors[23] = 1.0f;

        // Vertex 7
        positions[24] = -200;
        positions[25] = +200 *(float)Math. cos(HALF_PI);
        positions[26] = +200 * (float)Math.sin(HALF_PI);
        positions[27] = 1;

        colors[24] = 0.0f;
        colors[25] = 0.0f;
        colors[26] = 0.0f;
        colors[27] = 1.0f;

        // Vertex 8
        positions[28] = +200;
        positions[29] = +200 *(float)Math. cos(HALF_PI);
        positions[30] = +200 * (float)Math.sin(HALF_PI);
        positions[31] = 1;

        colors[28] = 1.0f;
        colors[29] = 1.0f;
        colors[30] = 1.1f;
        colors[31] = 1.0f;

        // Triangle 1
        indices[0] = 0;
        indices[1] = 1;
        indices[2] = 2;

        // Triangle 2
        indices[3] = 2;
        indices[4] = 3;
        indices[5] = 1;

        // Triangle 3
        indices[6] = 4;
        indices[7] = 5;
        indices[8] = 6;

        // Triangle 4
        indices[9] = 6;
        indices[10] = 7;
        indices[11] = 5;

        posBuffer = BufferUtils.allocateDirectFloatBuffer(32);
        colorBuffer = BufferUtils.allocateDirectFloatBuffer(32);
        indexBuffer = BufferUtils.allocateDirectIntBuffer(12);

        posBuffer.rewind();
        posBuffer.put(positions);
        posBuffer.rewind();

        colorBuffer.rewind();
        colorBuffer.put(colors);
        colorBuffer.rewind();

        indexBuffer.rewind();
        indexBuffer.put(indices);
        indexBuffer.rewind();


        // Copy vertex data to VBOs
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indexVboId);
        pgl.bufferData(PGL.ELEMENT_ARRAY_BUFFER, Integer.BYTES * indices.length, indexBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * positions.length, posBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, colorVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * colors.length, colorBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    }

    public void drawMesh(PJOGL pgl, GL4 gl) {
        //Specify the vertex attributes
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glVertexAttribPointer(posLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, colorVboId);
        gl.glVertexAttribPointer(colorLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);

        // Draw the triangle elements
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indexVboId);
        gl.glDrawElements(PGL.TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, 0);
    }
}


