package com.xbuilders.engine.rendering.entity;

import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.window.MVP;
import com.xbuilders.window.shader.Shader;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.opengl.PJOGL;

public class EntityShader extends Shader {

    public int attribute_pos, attribute_uv, uniform_ModelMatrix, uniform_projViewMatrix;
    MVP projViewMatrix;

    public void updateProjViewMatrix(Matrix4f projection, Matrix4f view) {
        projViewMatrix.update(projection, view);
        projViewMatrix.sendToShader(gl, getID(), uniform_projViewMatrix);
    }

    public EntityShader(UIFrame f, PJOGL pgl) {
        super(f, pgl);
        init(
                ResourceUtils.resourcePath("Shaders/entity/entity_vert.glsl"),
                ResourceUtils.resourcePath("Shaders/entity/entity_frag.glsl"));

        projViewMatrix = new MVP();
        bind();
        // Get the location of the attribute variables.
        attribute_pos = gl.glGetAttribLocation(getID(), "position");
        attribute_uv = gl.glGetAttribLocation(getID(), "color");

        // Get the transform uniform
        uniform_ModelMatrix = gl.glGetUniformLocation(getID(), "transform");
        uniform_projViewMatrix = gl.glGetUniformLocation(getID(), "projView");
        unbind();
    }

}