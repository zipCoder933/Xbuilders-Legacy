package com.xbuilders.window.shader;

import java.io.File;

import org.joml.Matrix4f;

import com.jogamp.opengl.GL4;
import com.xbuilders.engine.utils.ResourceUtils;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PVector;
import processing.core.UIFrame;
import processing.opengl.PJOGL;
import processing.opengl.PShader;

public class Shader {

    public PShader shader;
    public final PJOGL pgl;
    public final GL4 gl;
    public final UIFrame uiFrame;

    public Shader(UIFrame uiFrame, PJOGL pgl) {
        this.uiFrame = uiFrame;
        this.pgl = pgl;
        this.gl = pgl.gl.getGL4();
    }

    public Shader(UIFrame uiFrame, PJOGL pgl, String vertex, String fragment) {
        this.uiFrame = uiFrame;
        this.pgl = pgl;
        this.gl = pgl.gl.getGL4();
        init(vertex, fragment);
    }

    public void init(String vertex, String fragment) {
        shader = uiFrame.loadShader(fragment, vertex);
        bind();
        unbind();
    }

    /**
     * Sets the uniform variables inside the shader to modify the effect while
     * the program is running.
     *
     * @webref rendering:shaders
     * @webBrief Sets a variable within the shader
     * @param name the name of the uniform variable to modify
     * @param x    first component of the variable to modify
     */
    public void set(String name, int x) {
        shader.set(name, x);
    }

    /**
     * @param y second component of the variable to modify. The variable has to
     *          be declared with an array/vector type in the shader (i.e.: int[2],
     *          vec2)
     */
    public void set(String name, int x, int y) {
        shader.set(name, x, y);
    }

    /**
     * @param z third component of the variable to modify. The variable has to
     *          be declared with an array/vector type in the shader (i.e.: int[3],
     *          vec3)
     */
    public void set(String name, int x, int y, int z) {
        shader.set(name, x, y, z);
    }

    /**
     * @param w fourth component of the variable to modify. The variable has to
     *          be declared with an array/vector type in the shader (i.e.: int[4],
     *          vec4)
     */
    public void set(String name, int x, int y, int z, int w) {
        shader.set(name, x, y, z, w);
    }

    public void set(String name, float x) {
        shader.set(name, x);
    }

    public void set(String name, float x, float y) {
        shader.set(name, x, y);
    }

    public void set(String name, float x, float y, float z) {
        shader.set(name, x, y, z);
    }

    public void set(String name, float x, float y, float z, float w) {
        shader.set(name, x, y, z, w);
    }

    public void set(String name, int[] arr) {
        shader.set(name, arr);
    }

    public void set(String name, float[] arr) {
        shader.set(name, arr);
    }
    
    public void set(String name, Matrix4f mat){
        shader.set(name, mat);
    }


    /**
     * @param tex sets the sampler uniform variable to read from this image
     *            texture
     */
    public void set(String name, PImage tex) {
        shader.set(name, tex);
    }

    public void bind() {
        shader.bind();
    }

    public void bind(PGraphics g) {
        g.shader(this.shader);
    }

    public void unbind() {
        shader.unbind();
    }

    public int getID() {
        return shader.glProgram;
    }

}
