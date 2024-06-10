// vert.glsl
#version 150

uniform mat4 mvp;

in vec4 position;
in vec2 uv;

out vec2 vertUV;

void main() {
  gl_Position = mvp * position;
  vertUV = uv;
}