#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

// METHODS =============================================================================
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

    // vec2 position = ( coords.xy / imgSize.xy );
    // vec4 color = texture2D(image, position);
    // return color.r;
}

bool inBoundsOfData(ivec3 dataShape, ivec3 coords) {
    if (coords.x >= 0 && coords.y >= 0 && coords.z >= 0) {
        return (coords.x <= dataShape.x && coords.y <= dataShape.y && coords.z <= dataShape.z);
    } else {
        return false;
    }
}

/**
 * Get the bits XXXX0000
 *
 * @return the lightMap
 */
int getSunlightFromPackedByte(int packedByte) {
    // return (0x35 & 0b11110000) >> 4;
    return (packedByte >> 4) & 0xF;
}

/**
 * Get the bits 0000XXXX
 *
 * @return the lightMap
 */
int getTorchLightFromPackedByte(int packedByte) {
    // return 0x35 & 0b00001111;
    return packedByte & 0xF;
}
//=====================================================================================
//=====================================================================================

const int CHUNK_X_LENGTH = 16;
const int CHUNK_Y_LENGTH = 256;
const int CHUNK_Z_LENGTH = 16;

uniform float tileSize;
uniform vec3 worldSpaceOffset;
uniform sampler2D lights;
uniform ivec3 lightsStart;
uniform ivec3 lightsShape;
uniform float sunlightMultiplier;
uniform float maxBrightness;
uniform int lightLevel;

uniform mat4 modelMatrix;

// DONT TOUCH THESE!!!! They are probabbly ALL given from processing core
uniform mat4 transform;  // the orientation of the camera
uniform mat4 projmodelview;
uniform mat3 normalMatrix;
uniform vec3 lightNormal;
uniform mat4 texMatrix;
uniform mat4 depthBiasMVP;
uniform bool flashlightMode;
attribute vec4 position;  // The position within the chunk
attribute vec4 color;
attribute vec3 normal;
attribute vec2 texCoord;  // the uv texture coordinates at this vertex
varying vec4 vertColor;
varying vec2 v_texCoord;
varying vec3 vertNormal;
varying vec3 vertLightDir;
//-------------------------------------------------------------------

out float frag_distance;
out vec3 chunk_space;
out vec3 world_space;
out float totalSunlight;
out vec2 textureStart;
// out float tileTextures;

void main() {
    float vertex_light = lightLevel/15.0;
    gl_Position = transform * modelMatrix * position;
    frag_distance = length(gl_Position.xyz);
    v_texCoord = texCoord;

    // vertNormal = normalize(normalMatrix * normal);
    // vertLightDir = -lightNormal;
    // chunk_space.xyz = position.xyz;
    // world_space = vec3(chunk_space.x + worldSpaceOffset.x, chunk_space.y + worldSpaceOffset.y, chunk_space.z + worldSpaceOffset.z);

    float lightMaxReach = 30.0;  // in voxels

    if (flashlightMode) {
        float flashlight = max(vertex_light, map(frag_distance, 0.0, lightMaxReach, 1.0, 0.0));
        vertex_light = clamp(flashlight, 0.0, 1.0);
    }
      
    vertColor = vec4(vertex_light, vertex_light, vertex_light, 1.0);  // Used to tint the frag shader texture
}
