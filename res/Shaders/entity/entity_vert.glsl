#version 400 core

in vec4 position;
in vec2 color;

uniform mat4 projView;
uniform mat4 transform;


out vec2 vertColor;

void main() {
  gl_Position = projView * transform * position;
  gl_Position = vec4(gl_Position.x,-gl_Position.y,gl_Position.z,gl_Position.w);
  vertColor = color;
}