package com.xbuilders.test.joglDemo.demoModified.demo;

import com.jogamp.opengl.GL4;
import com.xbuilders.window.CameraNavigator;
import com.xbuilders.window.MVP;
import com.xbuilders.window.mesh.glTextureMesh;
import com.xbuilders.window.shader.DemoTextureShader;
import org.joml.Matrix4f;
import processing.core.UIFrame;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.IOException;

public class WrappedDemo {

    Matrix4f projectMatrix = new Matrix4f();
    Matrix4f viewMatrix;
    Matrix4f modelMatrix = new Matrix4f();
    DemoTextureShader shader;
    DemoMesh mesh;
    CameraNavigator cameraNavigator;

    UIFrame f;
    PJOGL pgl;
    GL4 gl;

    public WrappedDemo(UIFrame f, PJOGL pgl) {
        shader = new DemoTextureShader(f, pgl);
        projectMatrix.identity().perspective(
                (float) Math.toRadians(60.0f),
                (float) f.width / (float) f.height,
                0.1f,
                1000.0f);

        this.f = f;
        this.pgl = pgl;
        gl = pgl.gl.getGL4();
        cameraNavigator = new CameraNavigator(f);
        viewMatrix = cameraNavigator.getViewMatrix();
        mesh = new DemoMesh(f, pgl);
    }

    public void draw() {
        pgl = (PJOGL) f.beginPGL();
        gl = pgl.gl.getGL4();
        //Enable backface culling
        gl.glEnable(GL4.GL_CULL_FACE);
        gl.glCullFace(GL4.GL_BACK);
        shader.bind();

        cameraNavigator.update();

        mesh.draw(projectMatrix, viewMatrix, modelMatrix);

        shader.unbind();
        f.endPGL();
    }
}
