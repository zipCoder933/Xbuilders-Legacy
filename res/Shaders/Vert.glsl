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

const int maxAnimatedTextures = 20;
const int CHUNK_X_LENGTH = 16;
const int CHUNK_Y_LENGTH = 256;
const int CHUNK_Z_LENGTH = 16;

uniform int[maxAnimatedTextures] animatedX;
uniform int[maxAnimatedTextures] animatedY;
uniform int[maxAnimatedTextures] animationDuration;
uniform float tileSize;
uniform vec3 worldSpaceOffset;
uniform sampler2D lights;
uniform ivec3 lightsStart;
uniform ivec3 lightsShape;
uniform float sunlightMultiplier;
uniform int tick;
uniform bool animatedTextures;
uniform float maxBrightness;
uniform int numberOfTilesWidth;

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
out float empty;
out vec2 textureStart;
// out float tileTextures;

void main() {
    float vertex_light = 1.0f;
    gl_Position = transform * modelMatrix * position;
    frag_distance = length(gl_Position.xyz);

    // if (color.r == 1.0 && color.g == 1.0) { //Texture Tiling
    // tileTextures = 0.0;
    // } else{
    //     tileTextures = 1.0;
    //     float startX = int(color.r * 255) / 32.0;
    //     float startY = int(color.g * 255) / 32.0;
    //     textureStart = vec2(startX, startY);
    // }

    int animationTime = 0;

    if (animatedTextures
        // && tileTextures == 0.0
    ) {
        if (frag_distance < 1000) {
            for (int i = 0; i < maxAnimatedTextures; i++) {
                if (animatedX[i] == -1) {
                    break;
                } else {
                    float animX = animatedX[i] * tileSize;
                    float animY = animatedY[i] * tileSize;
                    if (
                        texCoord.x >= animX && texCoord.x <= animX + tileSize &&
                        texCoord.y >= animY && texCoord.y <= animY + tileSize) {
                        animationTime = animationDuration[i];
                        break;
                    }
                }
            }
        }
        if (animationTime > 1) {
            int maxTicks = int(tick / animationTime);
            // vec4 v_texCoord2 = texMatrix * vec4(texCoord.x,texCoord.y + (tileSize*(tick-(animationTime*maxTicks))), 1.0, 1.0);
            v_texCoord = vec2(texCoord.x, texCoord.y + (tileSize * (tick - (animationTime * maxTicks))));
        } else {
            // vec4 v_texCoord2 = texMatrix * vec4(texCoord.x,texCoord.y, 1.0, 1.0);
            v_texCoord = texCoord;
        }
    } else {
        //    vec4 v_texCoord2 = texMatrix * vec4(texCoord.x,texCoord.y, 1.0, 1.0);
        v_texCoord = texCoord;
    }

    // vertNormal = normalize(normalMatrix * normal);
    // vertLightDir = -lightNormal;

    chunk_space = (modelMatrix * position).xyz;
    world_space = chunk_space;
    // world_space = vec3(chunk_space.x + worldSpaceOffset.x, chunk_space.y + worldSpaceOffset.y, chunk_space.z + worldSpaceOffset.z);

    // LIGHTMAP RENDERING ==================================================
    ivec3 lightmap_space = ivec3(world_space - lightsStart);
    //============================================================

    float totalLight = 0;
    float totalTorchlight = 0;
    totalSunlight = 0;

    int totalTransparentBlocks = 0;
    int totalOpaqueBlocks = 0;

    empty = 0.0;

    if (fetchData(lights, lightmap_space, lightsShape) == -2) {  // Empty value
        empty = 1.0;
    } else if (inBoundsOfData(lightsShape, lightmap_space)) {
        for (int x = -1; x <= 0; x++) {
            for (int y = -1; y <= 0; y++) {
                for (int z = -1; z <= 0; z++) {
                    int lightmapVal = fetchData(lights, lightmap_space + ivec3(x, y, z), lightsShape);
                    if (lightmapVal > -1) {
                        float sunlight = getSunlightFromPackedByte(lightmapVal);

                        totalSunlight += sunlight;

                        sunlight *= sunlightMultiplier;
                        int torchlight = getTorchLightFromPackedByte(lightmapVal);
                        totalTorchlight += torchlight;

                        if (sunlight < 15) {
                            sunlight += torchlight;
                        }
                        if (sunlight > 15) {
                            sunlight = 15;
                        }

                        totalLight += sunlight;
                        totalTransparentBlocks += 1;
                    } else {
                        totalOpaqueBlocks += 1;
                    }
                }
            }
        }

        float lightMaxReach = 30.0;//in voxels

        if (totalTransparentBlocks == 0) {
            if (flashlightMode) {
                float flashlight = max(0.0, map(frag_distance, 0.0, lightMaxReach, 1.0, 0.0));
                vertex_light = clamp(flashlight, 0.0, 1.0);
            } else {
                vertex_light = 0.0;
            }
        } else {
            totalLight /= totalTransparentBlocks;
            totalTorchlight /= totalTransparentBlocks;
            totalSunlight /= totalTransparentBlocks;

            if (flashlightMode) {
                float flashlight = max(0.0, map(frag_distance, 0.0, lightMaxReach, 15.0, 0.0));
                totalLight = clamp(totalLight + flashlight, 0.0, 15.0);
            }

            float minLight = 0.1;

            float minAO = 0.1;
            float maxAO = 1.2;
            totalLight -= totalOpaqueBlocks * clamp(map(totalLight, 0.0, 15.0, minAO, maxAO), minAO, maxAO);
            vertex_light = clamp(map(totalLight, 0.0, 15.0, minLight, maxBrightness), minLight, maxBrightness);

            if (totalTorchlight > 13) {
                vertex_light *= 1.2;
            }
        }
    } else {  // Out of bounds
        // vertColor = vec4(sunlightMultiplier, sunlightMultiplier, sunlightMultiplier, 1.0);
        empty = 1.0;
    }

    // vertColor = color;
    vertColor = vec4(vertex_light, vertex_light, vertex_light, 1.0);  // Used to tint the frag shader texture
}
