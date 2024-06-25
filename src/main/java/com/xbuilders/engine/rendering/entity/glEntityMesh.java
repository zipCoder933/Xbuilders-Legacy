package com.xbuilders.engine.rendering.entity;

import com.jogamp.opengl.GL4;
import com.xbuilders.window.MVP;
import com.xbuilders.window.mesh.glTextureMesh;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.IOException;

public class glEntityMesh extends glTextureMesh{

    EntityShader shader;
    public MVP modelMatrix;

    public void updateModelMatrix() {
        this.modelMatrix.sendToShader(gl, shader.getID(), shader.uniform_ModelMatrix);
    }

    public void updateModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix.update(modelMatrix);
        this.modelMatrix.sendToShader(gl, shader.getID(), shader.uniform_ModelMatrix);
    }

    public glEntityMesh(UIFrame f, PJOGL pgl, EntityShader shader, Matrix4f modelMatrix) {
        super(pgl, shader.attribute_pos, shader.attribute_uv);
        this.shader = shader;
        this.modelMatrix = new MVP(modelMatrix);
    }

    public glEntityMesh(UIFrame f, PJOGL pgl, EntityShader shader) {
        super(pgl, shader.attribute_pos, shader.attribute_uv);
        this.shader = shader;
        this.modelMatrix = new MVP();
    }
}
