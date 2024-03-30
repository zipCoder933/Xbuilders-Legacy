#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

//METHODS =============================================================================
//=====================================================================================
float map(float value, float inMin, float inMax, float outMin, float outMax) {
    return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}

vec2 map(vec2 value, vec2 inMin, vec2 inMax, vec2 outMin, vec2 outMax) {
    return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}

vec3 map(vec3 value, vec3 inMin, vec3 inMax, vec3 outMin, vec3 outMax) {
    return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}

vec4 map(vec4 value, vec4 inMin, vec4 inMax, vec4 outMin, vec4 outMax) {
    return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}

/**
 * image: the image texture
 * coords: the 3D coordinate to pick from the virtual 3d map
 * dataShape: the shape of the virtual 3d map
 *
 * Returns: the actual light value of the pixel of your choice from the lightmap
 */
int fetchData(sampler2D image, ivec3 coords, ivec3 dataShape) {
    coords.xyz = coords.zyx;

    ivec2 imgSize = textureSize(image, 0);
    int idx = (coords.z * dataShape.y + coords.y) * dataShape.x + coords.x;
    int j = idx / imgSize.x;
    int i = idx - j * imgSize.x;
    ivec4 v = ivec4(texelFetch(image, ivec2(i, j), 0) * 255.0);
    return ((v.a * 256 + v.r) * 256 + v.g) * 256 + v.b;
}

bool inBoundsOfData(ivec3 dataShape, ivec3 coords){
    if(coords.x >= 0 && coords.y >= 0 && coords.z >= 0){
        return (coords.x <= dataShape.x && coords.y <= dataShape.y && coords.z <= dataShape.z);
    }else{
        return false;
    }
}

/**
 * Get the bits XXXX0000
 *
 * @return the lightMap
 */
int getSunlightFromPackedByte(int packedInt) {
    return (packedInt >> 4) & 0xF;
}

/**
 * Get the bits 0000XXXX
 *
 * @return the lightMap
 */
int getTorchLightFromPackedByte(int packedInt) {
    return packedInt & 0xF;
}
//=====================================================================================
//=====================================================================================

uniform ivec2 chunkCoords;
uniform sampler2D lights;
uniform ivec3 lightsStart;
uniform ivec3 lightsShape;
uniform float sunlightMultiplier;

const int CHUNK_X_LENGTH = 16;
const int CHUNK_Y_LENGTH = 256;
const int CHUNK_Z_LENGTH = 16;

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

   out float frag_distance;
   out vec3 chunk_space;
   out vec3 world_space;
   out vec4 vertex_color;
   out float vertex_light;
   out float totalSunlight;

  void main() {
    gl_Position = transform * position;
    ShadowCoord = depthBiasMVP * position;
    
    vertColor = color;
    vertex_color = color;

    vertTexCoord = texMatrix * vec4(texCoord, 1.0, 1.0);
    vertNormal = normalize(normalMatrix * normal);
    vertLightDir = -lightNormal;
}
