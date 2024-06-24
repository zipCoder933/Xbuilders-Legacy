package com.xbuilders.test.joglDemo.demoModified.demo;

import java.io.File;
import com.jogamp.opengl.GL4;
import com.xbuilders.test.glShader;

import processing.core.UIFrame;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class demoTextureShader extends glShader {

    public int attribute_pos, attribute_uv;
    public int uniform_MVP;
    PShader shader;

    PJOGL pgl;
    GL4 gl;

    public demoTextureShader(UIFrame uiFrame, PJOGL pgl) {
        super(pgl);
        this.pgl = pgl;
        this.gl = pgl.gl.getGL4();
        String localDir = new File("").getAbsolutePath();

        shader = uiFrame.loadShader(
                localDir + "/src/main/java/com/xbuilders/test/joglDemo/demoModified/demo/frag.glsl",
                localDir + "/src/main/java/com/xbuilders/test/joglDemo/demoModified/demo/vert.glsl");
              
        // try {//Its something in the shader that is causing the issue
        //     init(
        //             new File(localDir + "/src/main/java/com/xbuilders/test/joglDemo/demoModified/demo/vert.glsl"),
        //             new File(localDir + "/src/main/java/com/xbuilders/test/joglDemo/demoModified/demo/frag.glsl"));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

/**
 * We dont need to replace the shader. As long as it is only getting bound once per frame
 */

        bind();
        // Get the location of the attribute variables.
        attribute_pos = gl.glGetAttribLocation(getID(), "position");
        attribute_uv = gl.glGetAttribLocation(getID(), "color");

        // Get the transform uniform
        uniform_MVP = gl.glGetUniformLocation(getID(), "transform");
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