package com.xbuilders.test.joglDemo.mesh;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES3;
import processing.core.UIFrame;
import processing.opengl.PGL;
import processing.opengl.PJOGL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class glTextureMesh {

    GL2ES3 gl;
    PJOGL pgl;
    UIFrame f;


    int length;
    int textureID;

    int posVboId;
    int uvVboId;
    int posLoc;
    int uvLoc;

    public glTextureMesh(UIFrame f, int posLoc, int uvLoc) {
        this.f = f;
       pgl =  (PJOGL) f.beginPGL();
       gl = pgl.gl.getGL2ES3();
       this.posLoc = posLoc;
       this.uvLoc = uvLoc;

        IntBuffer intBuffer = IntBuffer.allocate(3);
        gl.glGenBuffers(2, intBuffer);
        posVboId = intBuffer.get(0);
        uvVboId = intBuffer.get(1);


        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glVertexAttribPointer( //Set VBO properties
                posLoc,
                4,
                GL.GL_FLOAT,
                false,
                4 * Float.BYTES,
                0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId);
        gl.glVertexAttribPointer(uvLoc,
                2, GL.GL_FLOAT,
                false,
                2 * Float.BYTES,
                0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

        gl.glEnableVertexAttribArray(posLoc);
        gl.glEnableVertexAttribArray(uvLoc);

        f.endPGL();
    }

    public void sendToGPU(FloatBuffer posBuffer, FloatBuffer uvBuffer){
        // Copy vertex data to VBOs
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * posBuffer.capacity(), posBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * uvBuffer.capacity(), uvBuffer, GL.GL_DYNAMIC_DRAW);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

        length = posBuffer.capacity() /3;
    }

    public void setTexture(int textureID){
        this.textureID = textureID;
    }

    public void draw(){
        // Draw the triangle elements
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureID);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, uvVboId);
        gl.glDrawArrays(PGL.TRIANGLES,0, length);
    }


}
