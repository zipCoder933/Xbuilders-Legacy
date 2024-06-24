package com.xbuilders.engine.rendering;

import com.jogamp.opengl.GL4;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.window.shader.Shader;

import processing.core.UIFrame;
import processing.opengl.PJOGL;

public class BlockShader extends Shader {

    int attribute_pos, attribute_uv;
    int uniform_ProjViewMatrix, uniform_ModelMatrix;
    

    public BlockShader(UIFrame f, PJOGL pgl) {
        super(f, pgl);
        init(
                ResourceUtils.resourcePath("Shaders/Vert.glsl"),
                ResourceUtils.resourcePath("Shaders/Frag.glsl"));

        bind();
        // Get the location of the attribute variables.
        attribute_pos = gl.glGetAttribLocation(getID(), "position");
        attribute_uv = gl.glGetAttribLocation(getID(), "uv");

        // Get the transform uniform
        // uniform_ProjViewMatrix = gl.glGetUniformLocation(getID(), "transform");
        // uniform_ModelMatrix = gl.glGetUniformLocation(getID(), "modelMatrix");
        unbind();
    }

}
