package com.xbuilders.test.joglDemo.demoModified.demo;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.xbuilders.window.BufferUtils;
import com.xbuilders.window.utils.obj.IndexedOBJ;
import com.xbuilders.window.utils.obj.OBJ;
import com.xbuilders.window.utils.obj.OBJLoader;
import com.xbuilders.window.utils.obj.buffers.IndexedOBJBufferSet;
import com.xbuilders.window.utils.obj.buffers.OBJBufferSet;
import com.xbuilders.window.utils.texture.Texture;
import com.xbuilders.window.utils.texture.TextureUtils;
import processing.opengl.PGL;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BasicTextureMesh {

    FloatBuffer posBuffer;
    FloatBuffer uvBuffer;
    IntBuffer indexBuffer;

    int posVboId;
    int uvVboId;
    int indexVboId;

    int posLoc;
    int colorLoc;
    GL4 gl;
    PJOGL pgl;

    public BasicTextureMesh(PJOGL pgl, int posLoc, int colorLoc) {
        this.pgl = pgl;
        this.gl = pgl.gl.getGL4();
        this.posLoc = posLoc;
        this.colorLoc = colorLoc;

        // Get GL ids for all the buffers
        IntBuffer intBuffer = IntBuffer.allocate(3);
        gl.glGenBuffers(3, intBuffer);// Generate 3 VBOs
        // Now buffers[0], buffers[1], and buffers[2] contain the names of the VBOs

        posVboId = intBuffer.get(0);
        uvVboId = intBuffer.get(1);
        indexVboId = intBuffer.get(2);

        setVBOProperties();

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

        gl.glEnableVertexAttribArray(posLoc);
        gl.glEnableVertexAttribArray(colorLoc);
    }

    private void setVBOProperties() {
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glVertexAttribPointer(posLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId);
        gl.glVertexAttribPointer(colorLoc, 2, GL.GL_FLOAT, false, 2 * Float.BYTES, 0);
    }

    final float HALF_PI = (float) Math.PI / 2.0f;
    float[] positions, colors;
    int[] indices;


    public void setOBJ(File objModel) throws FileNotFoundException {
        FloatBuffer posBuffer;
        FloatBuffer uvBuffer;

        OBJ o = OBJLoader.loadModel(objModel);
        IndexedOBJ indexedOBJ = new IndexedOBJ(o);
        IndexedOBJBufferSet bufferSet = new IndexedOBJBufferSet(indexedOBJ);

        posBuffer = BufferUtils.allocateDirectFloatBuffer(bufferSet.vertBuffer.length);
        uvBuffer = BufferUtils.allocateDirectFloatBuffer(bufferSet.uvBuffer.length);
        indexBuffer = BufferUtils.allocateDirectIntBuffer(bufferSet.indicies.length);

        posBuffer.rewind();
        posBuffer.put(bufferSet.vertBuffer);
        posBuffer.rewind();

        uvBuffer.rewind();
        uvBuffer.put(bufferSet.uvBuffer);
        uvBuffer.rewind();

        indexBuffer.rewind();
        indexBuffer.put(bufferSet.indicies);
        indexBuffer.rewind();

        sendToGPU(posBuffer, uvBuffer, indexBuffer);
    }

    public void updateMesh(PJOGL pgl, GL4 gl) {

        positions = new float[32];
        colors = new float[16];
        indices = new int[12];

        colors[0] = 1.0f;
        colors[1] = 0.0f;
        colors[2] = 0.0f;
        colors[3] = 1.0f;
        colors[4] = 0.2f;
        colors[5] = 1.0f;
        colors[6] = 0.0f;
        colors[7] = 0.5f;
        colors[8] = 0.0f;
        colors[9] = 1.0f;
        colors[10] = 0.0f;
        colors[11] = 0.5f;
        colors[12] = 0.0f;
        colors[13] = 0.5f;
        colors[14] = 0.5f;
        colors[15] = 1.0f;

        // Vertex 1
        positions[0] = -200;
        positions[1] = -200;
        positions[2] = 0;
        positions[3] = 1;
        // Vertex 2
        positions[4] = +200;
        positions[5] = -200;
        positions[6] = 0;
        positions[7] = 1;
        // Vertex 3
        positions[8] = -200;
        positions[9] = +200;
        positions[10] = 0;
        positions[11] = 1;
        // Vertex 4
        positions[12] = +200;
        positions[13] = +200;
        positions[14] = 0;
        positions[15] = 1;
        // Vertex 5
        positions[16] = -200;
        positions[17] = -200 * (float) Math.cos(HALF_PI);
        positions[18] = -200 * (float) Math.sin(HALF_PI);
        positions[19] = 1;
        // Vertex 6
        positions[20] = +200;
        positions[21] = -200 * (float) Math.cos(HALF_PI);
        positions[22] = -200 * (float) Math.sin(HALF_PI);
        positions[23] = 1;
        // Vertex 7
        positions[24] = -200;
        positions[25] = +200 * (float) Math.cos(HALF_PI);
        positions[26] = +200 * (float) Math.sin(HALF_PI);
        positions[27] = 1;
        // Vertex 8
        positions[28] = +200;
        positions[29] = +200 * (float) Math.cos(HALF_PI);
        positions[30] = +200 * (float) Math.sin(HALF_PI);
        positions[31] = 1;



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
        uvBuffer = BufferUtils.allocateDirectFloatBuffer(32);
        indexBuffer = BufferUtils.allocateDirectIntBuffer(12);

        posBuffer.rewind();
        posBuffer.put(positions);
        posBuffer.rewind();

        uvBuffer.rewind();
        uvBuffer.put(colors);
        uvBuffer.rewind();

        indexBuffer.rewind();
        indexBuffer.put(indices);
        indexBuffer.rewind();

        sendToGPU(posBuffer, uvBuffer,indexBuffer); //sendToGPU
    }

    public void sendToGPU(FloatBuffer posBuffer, FloatBuffer uvBuffer,IntBuffer indexBuffer){
        // Copy vertex data to VBOs
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indexVboId);
        pgl.bufferData(PGL.ELEMENT_ARRAY_BUFFER, Integer.BYTES * indices.length, indexBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * positions.length, posBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * colors.length, uvBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
    }

    int textureID = 0;

    public void setTexture(int textureID) {
        this.textureID = textureID;
    }

    public void setTexture(File texture) throws IOException {
        Texture tex = TextureUtils.loadTexture(gl, texture.getAbsolutePath(), false);
        this.textureID = tex.id;

    }


    public void drawMesh(PJOGL pgl, GL4 gl) {
        setVBOProperties(); // Required otherwise the mesh will disappear when p3d components are added to the scene

        gl.glBindTexture(GL.GL_TEXTURE_2D, textureID);

        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indexVboId);
        gl.glDrawElements(PGL.TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, 0);
    }
}
