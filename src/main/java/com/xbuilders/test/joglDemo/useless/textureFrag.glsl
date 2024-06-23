#version 400 core

uniform sampler2D texture;
in vec2 vertUV;

out vec4 fragColor;


void main() {
  //fragColor = vec4(vertUV.x,vertUV.y,0.0,1.0);
  fragColor = texture2D(texture, vertUV);
}