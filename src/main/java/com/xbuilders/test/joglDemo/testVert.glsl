#version 400 core

in vec4 position;
in vec2 color;

uniform float zAdd;

out vec2 vertUV;

void main() {
  gl_Position = vec4(position.x, position.y, position.z + zAdd, 1.0);
  vertUV = color;
}