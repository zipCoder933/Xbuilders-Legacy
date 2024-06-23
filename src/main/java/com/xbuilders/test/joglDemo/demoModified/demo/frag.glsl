// frag.glsl
#version 150

uniform mat4 transform;
uniform sampler2D texture;

in vec2 vertColor;

out vec4 fragColor;

void main() {
  fragColor = texture2D(texture, vertColor);
  //fragColor = vec4(vertColor.r,vertColor.g,0.0,1.0);
}



