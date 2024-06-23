// vert.glsl
#version 150

uniform mat4 transform;

in vec4 position;
in vec2 color;

out vec2 vertColor;

void main() {
  gl_Position = transform * position;
  vertColor = color;
}