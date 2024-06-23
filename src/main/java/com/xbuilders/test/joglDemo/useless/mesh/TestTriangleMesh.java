// package com.xbuilders.test.joglDemo.useless.mesh;

// import com.jogamp.opengl.GL4;
// import com.xbuilders.test.joglDemo.shader.glTestShader;

// import processing.opengl.PJOGL;

// import java.io.IOException;

// public class TestTriangleMesh {
//     glTestShader shader;
//     glTextureMesh mesh;

//     float[] vertex = {
//             1.0f, 0.0f, 0.0f,
//             0.0f, 1.0f, 0.0f,
//             0.0f, 0.0f, 0.0f,
//     };

//     float[] uv = {
//             0.0f, 0.0f,
//             1.0f, 0.0f,
//             0.0f, 1.0f
//     };


//     public TestTriangleMesh(PJOGL pgl) throws IOException {
//         shader = new glTestShader(pgl);
//         mesh = new glTextureMesh(pgl.gl.getGL4(), shader.attributePosition, shader.attributeUV);
//         mesh.sendToGPU(vertex, uv);
//     }

//     public void draw(GL4 gl) {
// //        //Disable backface culling
//         gl.glDisable(GL4.GL_CULL_FACE);
//         shader.bind();
//         mesh.draw();
//         shader.unbind();
//     }
// }
