package com.xbuilders.window.shader;

import com.jogamp.opengl.GL4;
import com.xbuilders.test.glShader;
import processing.core.UIFrame;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

import java.io.File;

public class EmptyTextureShader extends Shader {

    public int attribute_pos, attribute_uv;
    public String uniform_zAdd = "zAdd";


    public EmptyTextureShader(UIFrame uiFrame, PJOGL pgl) {
        super(uiFrame, pgl);
        String basePath = new File("").getAbsolutePath();

        init((basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\testVert.glsl"),
                (basePath + "\\src\\main\\java\\com\\xbuilders\\test\\joglDemo\\testFrag.glsl")
        );
        bind();
        // Get the location of the attribute variables.
        attribute_pos = gl.glGetAttribLocation(getID(), "position");
        attribute_uv = gl.glGetAttribLocation(getID(), "color");
        unbind();
    }
}