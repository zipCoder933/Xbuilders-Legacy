package com.xbuilders.window.utils;

import com.jogamp.opengl.GL4;
import processing.opengl.PJOGL;

import java.nio.IntBuffer;

public class glSampleQuery {
    int queryId;
    GL4 gl;
    PJOGL pgl;
    public long lastQueryTime;
    private IntBuffer samplesPassedLastFrame;
    public boolean queried = false;

    public int getSamplesPassedLastFrame() {
        return samplesPassedLastFrame.get(0);
    }

    public glSampleQuery(PJOGL pgl) {
        queried = true;
        this.pgl = pgl;
        this.gl = pgl.gl.getGL4();

        samplesPassedLastFrame = IntBuffer.allocate(1);
        IntBuffer ib = IntBuffer.allocate(1);
        gl.glGenQueries(1, ib);
        queryId = ib.get(0);
    }

    public void start() {
        gl.glBeginQuery(gl.GL_SAMPLES_PASSED, queryId); //Start the occlusion query
    }

    public void end() {
        queried = true;
        lastQueryTime = System.currentTimeMillis();
        gl.glEndQuery(gl.GL_SAMPLES_PASSED);
    }

    public void getQuery() {
        gl.glGetQueryObjectuiv(queryId, gl.GL_QUERY_RESULT, samplesPassedLastFrame);
        queried = true;
    }
}
