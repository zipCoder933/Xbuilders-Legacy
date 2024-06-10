package com.xbuilders.test.joglDemo.shader;

import com.jogamp.opengl.GL2ES3;
import processing.core.UIFrame;
import processing.opengl.PShader;

public class ColorShader {
    int attributePosition;
    int attributeColor;
    PShader shader;


    public ColorShader(UIFrame f, GL2ES3 gl) {
        shader = f.loadShader(
                "C:\\Users\\Patron\\Desktop\\Xbuilders-2-main\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\frag.glsl",
                "C:\\Users\\Patron\\Desktop\\Xbuilders-2-main\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\vert.glsl");
        shader.bind();
        attributePosition = gl.glGetAttribLocation(shader.glProgram, "position");
        attributeColor = gl.glGetAttribLocation(shader.glProgram, "color");

        gl.glEnableVertexAttribArray(attributePosition); //By enabling the attribute, it will be used in the shader, otherwise it won't be used
        gl.glEnableVertexAttribArray(attributeColor);
        shader.unbind();
    }

    public void bind() {
        shader.bind();
    }

    public void unbind() {
        shader.unbind();
    }
}
