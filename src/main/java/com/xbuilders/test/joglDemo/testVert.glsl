#version 400 core
//precision mediump float;
//precision mediump int;

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 color;

out vec2 vertUV;

void main() {
  gl_Position = position;
  vertUV = uv;
}