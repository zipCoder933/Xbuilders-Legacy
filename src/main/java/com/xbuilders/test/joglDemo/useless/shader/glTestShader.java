package com.xbuilders.test.joglDemo.useless.shader;

import com.jogamp.opengl.GL4;
import com.xbuilders.window.Shader;

import processing.opengl.PJOGL;

import java.io.File;
import java.io.IOException;

public class glTestShader extends Shader {
    public int attributePosition;
    public int attributeUV;
    public final int uniformMVP;
    final static String basePath = new File("").getAbsolutePath();

    public glTestShader(PJOGL pgl) throws IOException {
        super(pgl);
        init(
                new File(basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\testVert.glsl"),
                new File(basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\testFrag.glsl"));

        gl.glUseProgram(getID());
        gl.glEnableVertexAttribArray(attributePosition); //By enabling the attribute, it will be used in the shader, otherwise it won't be used
        gl.glEnableVertexAttribArray(attributeUV);
        uniformMVP = gl.glGetUniformLocation(getID(), "mvp");
        attributePosition = 0;
        attributeUV = 1;
        unbind();
    }
}
