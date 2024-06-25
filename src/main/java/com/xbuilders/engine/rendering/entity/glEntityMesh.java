package com.xbuilders.engine.rendering;

import com.jogamp.opengl.GL4;
import com.xbuilders.window.MVP;
import com.xbuilders.window.mesh.glTextureMesh;
import com.xbuilders.window.shader.DemoTextureShader;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.IOException;

public class glEntityMesh {
    MVP mvp = new MVP();
    public glTextureMesh mesh;
    DemoTextureShader shader;

    UIFrame f;
    PJOGL pgl;
    GL4 gl;

    public glEntityMesh(UIFrame f, PJOGL pgl) {
        this.f = f;
        this.pgl = pgl;
        gl = pgl.gl.getGL4();
        shader = new DemoTextureShader(f, pgl);
        mesh = new glTextureMesh(pgl, shader.attribute_pos, shader.attribute_uv);
        try {
            String basePath = new File("").getAbsolutePath();
            mesh.setTexture(new File(basePath + "\\res\\items\\entities\\animals\\fox\\red.png"));
            mesh.setOBJ(new File(basePath + "\\res\\items\\entities\\animals\\fox\\fox.obj"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Matrix4f projectMatrix, Matrix4f viewMatrix, Matrix4f modelMatrix) {
        shader.bind();
        mvp.update(projectMatrix, viewMatrix, modelMatrix);
        mvp.sendToShader(gl, shader.getID(), shader.uniform_MVP);
        mesh.draw();
        shader.unbind();
    }
}
