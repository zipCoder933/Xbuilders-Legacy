package com.xbuilders.window;

import org.joml.Matrix4f;
import processing.opengl.PShader;

public class UIShader {
    PShader shader;
    private final float[] matvJOML = new float[4 * 4];

    public void set(String name, Matrix4f mat) {
        matvJOML[0] = mat.m00();
        matvJOML[1] = mat.m01();
        matvJOML[2] = mat.m02();
        matvJOML[3] = mat.m03();
        matvJOML[4] = mat.m10();
        matvJOML[5] = mat.m11();
        matvJOML[6] = mat.m12();
        matvJOML[7] = mat.m13();
        matvJOML[8] = mat.m20();
        matvJOML[9] = mat.m21();
        matvJOML[10] = mat.m22();
        matvJOML[11] = mat.m23();
        matvJOML[12] = mat.m30();
        matvJOML[13] = mat.m31();
        matvJOML[14] = mat.m32();
        matvJOML[15] = mat.m33();
        shader.setUniformImpl(name, 18, matvJOML);
    }
}