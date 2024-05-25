#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform float sunlightMultiplier;

uniform mat4 transform; //the orientation of the camera
   uniform mat4 projmodelview;
   uniform mat3 normalMatrix;
   uniform vec3 lightNormal;
   uniform mat4 texMatrix;

   uniform mat4 depthBiasMVP;

   attribute vec4 position; //The position within the chunk
   attribute vec4 color;
   attribute vec3 normal;
   attribute vec2 texCoord;

   varying vec4 vertColor;
   varying vec4 vertTexCoord;
   varying vec3 vertNormal;
   varying vec3 vertLightDir;

   varying vec4 ShadowCoord;
   out vec3 space;
   out vec4 vertex_color;


  void main() {
    gl_Position = transform * position;
    ShadowCoord = depthBiasMVP * position;
    vertColor = color;
    vertex_color = color;
    space = vec3(position.x,position.y,position.z);
    vertNormal = normalize(normalMatrix * normal);
    vertLightDir = -lightNormal;
  }
