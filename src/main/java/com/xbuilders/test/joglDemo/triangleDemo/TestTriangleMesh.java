package com.xbuilders.test.joglDemo.triangleDemo;

import com.jogamp.opengl.GL4;

import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.window.mesh.glTextureMesh;
import processing.core.UIFrame;
import processing.opengl.PJOGL;

import java.io.IOException;

public class TestTriangleMesh {
    EmptyTextureShader shader;
    glTextureMesh mesh;
    PJOGL pgl;
    UIFrame f;

    float[] vertex = {
            1.0f, 0.0f, -0.5f, 1.0f,
            0.0f, 1.0f, 0f, 1.0f,
            -1.0f, 0.0f, 0.5f, 1.0f,
    };

    float[] uv = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f
    };

    int[] index = {
            0, 1, 2
    };


    public TestTriangleMesh(UIFrame f, PJOGL pgl) throws IOException {
        this.f = f;
        this.pgl = pgl;
        shader = new EmptyTextureShader(f, pgl);
        mesh = new glTextureMesh(pgl, shader.attribute_pos, shader.attribute_uv);
        mesh.sendToGPU(vertex, uv, index);
    }

//    MVP mvp = new MVP();

    public void draw() {
        float val= MathUtils.map(f.mouseX, 0, f.width, -1, 1);
        if (Math.abs(val - 0.0f) < 0.01f) {
            val = 0.0f;
        }
        System.out.println("Draw: " + val);
        PJOGL pgl = (PJOGL) f.beginPGL();
        GL4 gl = pgl.gl.getGL4();


        shader.set(shader.uniform_zAdd, val);
//        mvp.update(projMatrix, viewMatrix);
//        mvp.sendToShader(gl, shader.getID(), shader.uniform_MVP);

        gl.glDisable(GL4.GL_CULL_FACE);
        shader.bind();
        mesh.draw();
        shader.unbind();
        f.endPGL();
    }
}
