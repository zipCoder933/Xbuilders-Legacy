
package com.xbuilders.window;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.GLBuffers;
import org.joml.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Designed to be the base class of a custom shader
 *
 * @author zipCoder933
 */
public class Shader {

    private int programID;
    private int vertexID;
    private int fragmentID;
    private final GL4 gl;

    public Shader(GL4 gl) {
        this.gl = gl;
    }

    public void delete() {
        gl.glDetachShader(getID(), vertexID);
        gl.glDetachShader(getID(), fragmentID);
        gl.glDeleteShader(vertexID);
        gl.glDeleteShader(fragmentID);
        gl.glDeleteProgram(getID());
    }

    public Shader(GL4 gl, File Vert, File Frag) throws IOException {
        this(gl);
        init(Vert, Frag);
    }

    public Shader(GL4 gl, String Vert, String Frag) throws IOException {
        this(gl);
        init(Vert, Frag);
    }

    public void init(File Vert, File Frag) throws IOException {
        vertexID = loadShader(Vert, GL4.GL_VERTEX_SHADER);
        fragmentID = loadShader(Frag, GL4.GL_FRAGMENT_SHADER);
        programID = gl.glCreateProgram(); //this id is how opengl will know what shader group (program) to use
        gl.glAttachShader(programID, vertexID); //link shaders to the program id
        gl.glAttachShader(programID, fragmentID);

        bindAttributes(); //link components of the shader together

        gl.glLinkProgram(programID); //finalization
        int[] linkStatus = new int[1];
        gl.glGetProgramiv(programID, GL4.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GL4.GL_TRUE) {
            throw new IllegalStateException();
        }
        gl.glValidateProgram(programID);
    }

    public void init(String Vert, String Frag) throws IOException {
        vertexID = loadShaderString(Vert, GL4.GL_VERTEX_SHADER);
        fragmentID = loadShaderString(Frag, GL4.GL_FRAGMENT_SHADER);
        programID = gl.glCreateProgram(); //this id is how opengl will know what shader group (program) to use
        gl.glAttachShader(programID, vertexID); //link shaders to the program id
        gl.glAttachShader(programID, fragmentID);

        bindAttributes(); //link components of the shader together

        gl.glLinkProgram(programID); //finalization
        int[] linkStatus = new int[1];
        gl.glGetProgramiv(programID, GL4.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GL4.GL_TRUE) {
            throw new IllegalStateException();
        }
        gl.glValidateProgram(programID);
    }

    /**
     * What these methods do is they get the Uniform Variable Location and
     * Attribute Variable Location of variables in the GLSL shader. A Uniform
     * Variable is a per-primitive parameter that is used during the whole
     * drawing call, whereas a Attribute Variable is per-vertex, and refers to
     * the UV, Color, Positions.
     */
    /**
     * get the id of a uniform by entering its name
     *
     * @param uniformName
     * @return the uniform id
     */
    public int getUniformLocation(String uniformName) {
        return gl.glGetUniformLocation(programID, uniformName);
    }

    public void bindAttribute(int attribute, String variableName) {
        gl.glBindAttribLocation(programID, attribute, variableName);
    }

    public void loadFloat(int location, float value) {
        gl.glUseProgram(programID);
        gl.glUniform1f(location, value);
    }

    public void loadInt(int location, int value) {
        gl.glUseProgram(programID);
        gl.glUniform1i(location, value);
    }

    public void loadVec3f(int location, Vector3f vector) {
        gl.glUseProgram(programID);
        gl.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public void loadVec4f(int location, Vector4f vector) {
        gl.glUseProgram(programID);
        gl.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    public void loadVec2f(int location, Vector2f vector) {
        gl.glUseProgram(programID);
        gl.glUniform2f(location, vector.x, vector.y);
    }

    public void loadVec3i(int location, Vector3i vector) {
        gl.glUseProgram(programID);
        gl.glUniform3i(location, vector.x, vector.y, vector.z);
    }

    public void loadVec2i(int location, Vector2i vector) {
        gl.glUseProgram(programID);
        gl.glUniform2i(location, vector.x, vector.y);
    }

    public void loadMatrix4f(int location, boolean transpose, FloatBuffer buffer) {
        gl.glUseProgram(programID);
        gl.glUniformMatrix4fv(location, 1, transpose, buffer);
    }

    public void bind() {
        gl.glUseProgram(programID);
    }

    public static void unbind() {
        //reset to default render mode
    }

    public void bindAttributes() {
    }

    private int loadShaderString(String shaderSource, int type) throws IOException {
        int ID = gl.glCreateShader(type);
        gl.glShaderSource(ID, 1, new String[]{shaderSource}, null);
        gl.glCompileShader(ID);

        // Check for compilation errors
        IntBuffer compileStatus = IntBuffer.allocate(1);
        gl.glGetShaderiv(ID, GL4.GL_COMPILE_STATUS, compileStatus);
        if (compileStatus.get(0) != GL4.GL_TRUE) {
            // Retrieve the shader info log
            IntBuffer infoLogLength = IntBuffer.allocate(1);
            gl.glGetShaderiv(ID, GL4.GL_INFO_LOG_LENGTH, infoLogLength);
            ByteBuffer infoLog = ByteBuffer.allocate(infoLogLength.get(0));
            gl.glGetShaderInfoLog(ID, infoLogLength.get(0), null, infoLog);

            // Convert ByteBuffer to a string
            StringBuilder log = new StringBuilder();
            for (int i = 0; i < infoLogLength.get(0); i++) {
                char c = (char) infoLog.get(i);
                if (c != '\0') {
                    log.append(c);
                } else {
                    break;
                }
            }
            throw new IOException("Couldn't compile shader:\n" + log.toString());
        }

        return ID;
    }
    private int loadShader(File file, int type) throws IOException {
        StringBuilder shaderSource = new StringBuilder();
        try {//load the shader text
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException("Can't read shader file \"" + file + "\"", e);
        }
        int ID = gl.glCreateShader(type); //create shader and assign an ID to it. the type tells us what type of shader we want (vert,frag,etc)
        gl.glShaderSource(ID, 1, new String[]{shaderSource.toString()}, null);
        gl.glCompileShader(ID); //compile the shader, and create an id for it
        int[] compileStatus = new int[1];
        gl.glGetShaderiv(ID, GL4.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] != GL4.GL_TRUE) {
            // Retrieve the shader info log
            IntBuffer infoLogLength = IntBuffer.allocate(1);
            gl.glGetShaderiv(ID, GL4.GL_INFO_LOG_LENGTH, infoLogLength);
            ByteBuffer infoLog = ByteBuffer.allocate(infoLogLength.get(0));
            gl.glGetShaderInfoLog(ID, infoLogLength.get(0), null, infoLog);

            // Convert ByteBuffer to a string
            StringBuilder log = new StringBuilder();
            for (int i = 0; i < infoLogLength.get(0); i++) {
                char c = (char) infoLog.get(i);
                if (c != '\0') {
                    log.append(c);
                } else {
                    break;
                }
            }
            throw new IOException("Couldn't compile shader:\n" + log.toString());
        }
        return ID;
    }

    public int getID() {
        return programID;
    }
}