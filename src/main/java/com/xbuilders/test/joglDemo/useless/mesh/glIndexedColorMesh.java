package com.xbuilders.test.joglDemo.useless.mesh;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES3;
import processing.core.UIFrame;
import processing.opengl.PGL;
import processing.opengl.PJOGL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class glIndexedColorMesh {

    GL2ES3 gl;
    PJOGL pgl;
    UIFrame f;


    int length;

    int posVboId;
    int colorVboId;
    int indexVboId;
    int posLoc;
    int colorLoc;

    public glIndexedColorMesh(UIFrame f, int posLoc, int colorLoc) {
        this.f = f;
       pgl =  (PJOGL) f.beginPGL();
       gl = pgl.gl.getGL2ES3();
       this.posLoc = posLoc;
       this.colorLoc = colorLoc;

        IntBuffer intBuffer = IntBuffer.allocate(3);
        gl.glGenBuffers(3, intBuffer);
        posVboId = intBuffer.get(0);
        colorVboId = intBuffer.get(1);
        indexVboId = intBuffer.get(2);

        gl.glDisableVertexAttribArray(posLoc);
        gl.glDisableVertexAttribArray(colorLoc);

        f.endPGL();
    }

    public void sendToGPU(FloatBuffer posBuffer, FloatBuffer colorBuffer, IntBuffer indexBuffer){
        // Copy vertex data to VBOs
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, posVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * posBuffer.capacity(), posBuffer, GL.GL_DYNAMIC_DRAW);
        gl.glVertexAttribPointer(posLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, colorVboId);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * colorBuffer.capacity(), colorBuffer, GL.GL_DYNAMIC_DRAW);
        gl.glVertexAttribPointer(colorLoc, 4, GL.GL_FLOAT, false, 4 * Float.BYTES, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);

        // Draw the triangle elements
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indexVboId);
        pgl.bufferData(PGL.ELEMENT_ARRAY_BUFFER,  Integer.BYTES * indexBuffer.capacity(), indexBuffer, GL.GL_DYNAMIC_DRAW);
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, 0);
        length = indexBuffer.capacity();
    }

    public void draw(){
        // Draw the triangle elements
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, indexVboId);
        gl.glDrawElements(PGL.TRIANGLES, length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindBuffer(PGL.ELEMENT_ARRAY_BUFFER, 0);
    }


}
