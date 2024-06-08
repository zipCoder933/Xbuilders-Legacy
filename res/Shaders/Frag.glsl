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
}
//=====================================================================================
//=====================================================================================

const int CHUNK_X_LENGTH = 16;
const int CHUNK_Y_LENGTH = 256;
const int CHUNK_Z_LENGTH = 16;

varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 ShadowCoord;
varying vec2 v_texCoord;

uniform float tileSize;  // Size of a single tile in the atlas
uniform vec3 sky_color = vec3(98.0f / 255.0f, 144.0f / 255.0f, 219.0f / 255.0f);
uniform float fog_dist = 9000.0f;  // Higher values mean more distance
uniform sampler2D texture;
uniform ivec2 chunkCoords;
uniform sampler2D lights;
uniform ivec3 lightsStart;
uniform ivec3 lightsShape;
uniform float playerLight;
uniform float sunlightMultiplier;
uniform int numberOfTilesWidth;

in float frag_distance;
in vec3 chunk_space;  // the vertex position in chunk space
in vec3 world_space;  // the vertex position in world space
in float totalSunlight;
in float empty;
in vec2 textureStart;
// in float tileTextures;

void main() {
    vec4 fragColor;
    // if (tileTextures == 1.0) { //Texture Tiling
    //     vec2 tiledTexCoord = fract(v_texCoord * numberOfTilesWidth);
    //     tiledTexCoord *= tileSize;
    //     tiledTexCoord += textureStart;
    //     fragColor = texture2D(texture, tiledTexCoord);
    // } else {
    fragColor = texture2D(texture, v_texCoord);
    // }

    if (fragColor.w < 0.1f) {  // the w represents the alpha
        discard;
    }

    vec3 blockTexture = fragColor.xyz;
    vec3 fog_color = sky_color;

    if (empty > 0.0f) {
        blockTexture *= sunlightMultiplier;
    } else {
        blockTexture *= vertColor.xyz;
    }

    if (totalSunlight <= 12 && playerLight < 0.5) {
        fog_color = map(vec3(totalSunlight), vec3(12.0), vec3(0.0), sky_color, vec3(0.0));
        if (empty > 0.0f) {
            discard;
        }
    }

    float fogAmt = 0.0;
    if (frag_distance > fog_dist) {
        fogAmt = clamp(map(frag_distance, fog_dist, fog_dist+24, 0.0, 1.0), 0.0, 1.0);
    }

    gl_FragColor = vec4(mix(blockTexture, fog_color, fogAmt), fragColor.w);
}
