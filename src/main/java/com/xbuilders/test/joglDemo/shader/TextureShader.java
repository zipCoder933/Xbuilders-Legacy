package com.xbuilders.test.joglDemo.shader;

import com.jogamp.opengl.GL2ES3;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.opengl.PShader;

import java.io.File;

public class TextureShader {
    public final int attributePosition;
    public final int attributeUV;
    public final int uniformMVP;
    PShader shader;
    GL2ES3 gl;
    final static String basePath = new File("").getAbsolutePath();
    public TextureShader(UIFrame f, GL2ES3 gl) {
        this.gl = gl;
        shader = f.loadShader(
                basePath+"\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\textureFrag.glsl",
                basePath+"\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\textureVert.glsl");
        shader.bind();
        gl.glUseProgram(shader.glProgram);
        attributePosition = gl.glGetAttribLocation(shader.glProgram, "position");
        attributeUV = gl.glGetAttribLocation(shader.glProgram, "uv");
        uniformMVP = gl.glGetUniformLocation(shader.glProgram, "mvp");

        gl.glEnableVertexAttribArray(attributePosition); //By enabling the attribute, it will be used in the shader, otherwise it won't be used
        gl.glEnableVertexAttribArray(attributeUV);
        shader.unbind();

    }

    public void bind() {
        //Todo: The shader.bind() also updates all uniforms every frame. This is hurting to performance.
        //The solution should be to load all uniforms, ESPECIALY the MVP using regular opengl + joml
        //The easiest way to set these things up will be to start with a working opengl triangle demo using jogl and then come back to this
        shader.bind();
        gl.glUseProgram(shader.glProgram);
    }

    public void unbind() {
        shader.unbind();
    }

    public void setMVP(Matrix4f mvp) {

       // float[] v = ((float[]) val.value);
//        updateFloatBuffer(v);
//        gl.uniformMatrix4fv(loc, 1, false, floatBuffer);

        shader.set("mvp", mvp);
    }
}
