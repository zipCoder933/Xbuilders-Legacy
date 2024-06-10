package com.xbuilders.test.joglDemo.shader;

import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL4;
import com.xbuilders.window.Shader;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.opengl.PShader;

import java.io.File;
import java.io.IOException;

public class TextureShader extends Shader {
    public int attributePosition;
    public int attributeUV;
    public final int uniformMVP;
    final static String basePath = new File("").getAbsolutePath();

    public TextureShader(GL4 gl) throws IOException {
        super(gl);
        init(
                new File(basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\textureVert.glsl"),
                new File(basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\textureFrag.glsl"));

        gl.glUseProgram(getID());
        gl.glEnableVertexAttribArray(attributePosition); //By enabling the attribute, it will be used in the shader, otherwise it won't be used
        gl.glEnableVertexAttribArray(attributeUV);
        uniformMVP = gl.glGetUniformLocation(getID(), "mvp");
        attributePosition = 0;
        attributeUV = 1;
        unbind();
    }
}
