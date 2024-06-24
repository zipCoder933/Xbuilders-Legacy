package com.xbuilders.test.joglDemo.useless.shader;

import java.io.File;
import com.jogamp.opengl.GL4;
import com.xbuilders.test.glShader;

import processing.core.UIFrame;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class uiTextureShader {

    public int attributePosition;
    public int attributeUV;
    public final int uniformMVP;
    PShader shader;

    PJOGL pgl;
    GL4 gl;
    final static String basePath = new File("").getAbsolutePath();

    public uiTextureShader(UIFrame uiFrame, PJOGL pgl) {
        this.pgl = pgl;
        this.gl = pgl.gl.getGL4();

        shader = uiFrame.loadShader(
                basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\textureFrag.glsl",
                basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\textureVert.glsl");

        /**
         * We dont need to replace the shader. As long as it is only getting bound once
         * per frame
         */
        // try {//Its something in the shader that is causing the issue
        // init(
        // new File(localDir +
        // "/src/main/java/com/xbuilders/test/joglDemo/demoModified/demo/vert.glsl"),
        // new File(localDir +
        // "/src/main/java/com/xbuilders/test/joglDemo/demoModified/demo/frag.glsl"));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        bind(); //Bind also initializes the shader

        // Get the location of the attribute variables.
        attributePosition = gl.glGetAttribLocation(getID(), "position");
        attributeUV = gl.glGetAttribLocation(getID(), "uv");
        // Get the transform uniform
        uniformMVP = gl.glGetUniformLocation(getID(), "transform");

        unbind();
    }

    public void bind() {
        shader.bind();
    }

    public void unbind() {
        shader.unbind();
    }

    public int getID() {
        return shader.glProgram;
    }
}