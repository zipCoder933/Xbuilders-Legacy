package com.xbuilders.test.joglDemo.useless.mesh;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import com.xbuilders.window.BufferUtils;
import com.xbuilders.window.utils.obj.OBJ;
import com.xbuilders.window.utils.obj.OBJBufferSet;
import com.xbuilders.window.utils.obj.OBJLoader;
import com.xbuilders.window.utils.texture.Texture;
import com.xbuilders.window.utils.texture.TextureUtils;
import processing.opengl.PGL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class uselessTextureMesh {

    GL4 gl;

    int length;
    int textureID;

    int posVboId, uvVboId;
    final int shaderPosition, shaderUV;

    public uselessTextureMesh(GL4 gl, int posLoc, int uvLoc) {
        this.gl = gl;
        this.shaderPosition = posLoc;
        this.shaderUV = uvLoc;


//Create the VAO
//        IntBuffer vaoBuffer = BufferUtils.allocateDirectIntBuffer(1);
//        gl.glGenVertexArrays(1, vaoBuffer);
//        vao = vaoBuffer.get(0);
//        gl.glBindVertexArray(vao);

        //Create the VBOs
        IntBuffer intBuffer = IntBuffer.allocate(2);
        gl.glGenBuffers(2, intBuffer);
        posVboId = intBuffer.get(0);
        uvVboId = intBuffer.get(1);

        setVboProperties();
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

        gl.glEnableVertexAttribArray(posLoc);
        gl.glEnableVertexAttribArray(uvLoc);
//        gl.glBindVertexArray(0); //TODO: For some reason this gl command causes
        //OpenGL error 1282 at top endDraw(): invalid operation
        //OpenGL error 1282 at bot endDraw(): invalid operation
    }

    private void setVboProperties() {
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glVertexAttribPointer( //Set VBO properties
                shaderPosition,
                3,
                GL.GL_FLOAT,
                false,
                3 * Float.BYTES,
                0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId);
        gl.glVertexAttribPointer(shaderUV,
                2, GL.GL_FLOAT,
                false,
                2 * Float.BYTES,
                0);
    }

    public void setOBJ(File objModel) throws FileNotFoundException {
        FloatBuffer posBuffer;
        FloatBuffer uvBuffer;

        OBJ o = OBJLoader.loadModel(objModel);

        OBJBufferSet bufferSet = new OBJBufferSet(o,false,false);

        posBuffer = BufferUtils.allocateDirectFloatBuffer(bufferSet.vertBuffer.length);
        uvBuffer = BufferUtils.allocateDirectFloatBuffer(bufferSet.uvBuffer.length);

        posBuffer.rewind();
        posBuffer.put(bufferSet.vertBuffer);
        posBuffer.rewind();

        uvBuffer.rewind();
        uvBuffer.put(bufferSet.uvBuffer);
        uvBuffer.rewind();

        sendToGPU(posBuffer, uvBuffer);
    }

    public void sendToGPU(float[] vertex, float[] uv) throws FileNotFoundException {
        FloatBuffer posBuffer;
        FloatBuffer uvBuffer;
        posBuffer = BufferUtils.allocateDirectFloatBuffer(vertex.length);
        uvBuffer = BufferUtils.allocateDirectFloatBuffer(uv.length);

        posBuffer.rewind();
        posBuffer.put(vertex);
        posBuffer.rewind();

        uvBuffer.rewind();
        uvBuffer.put(uv);
        uvBuffer.rewind();

        sendToGPU(posBuffer, uvBuffer);
    }

    public void sendToGPU(FloatBuffer posBuffer, FloatBuffer uvBuffer) {
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId); // position VBO
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * posBuffer.capacity(), posBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId); // uv VBO
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * uvBuffer.capacity(), uvBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
        length = posBuffer.capacity() / 3;
    }

    public void setTexture(int textureID) {
        this.textureID = textureID;
    }

    public void setTexture(File texture) throws IOException {
        Texture tex = TextureUtils.loadTexture(gl, texture.getAbsolutePath(), false);
        this.textureID = tex.id;

    }

    public void draw() {
        setVboProperties();

        gl.glBindTexture(GL.GL_TEXTURE_2D, textureID);
        gl.glDrawArrays(PGL.TRIANGLES, 0, length);
    }
}
