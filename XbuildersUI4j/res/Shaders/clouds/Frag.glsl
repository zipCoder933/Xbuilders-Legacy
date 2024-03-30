#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertTexCoord;
varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 ShadowCoord;

float map(float value, float inMin, float inMax, float outMin, float outMax) {
    return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}

float mapAndClamp(float value, float inMin, float inMax, float outMin, float outMax) {
    return clamp(map(value, inMin, inMax, outMin, outMax), outMin, outMax);
}

float dist(vec2 a, vec2 b) {
    return sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2));
}

uniform float sunlightMultiplier;
uniform vec3 sky_color = vec3(98.0f / 255.0f, 144.0f / 255.0f, 219.0f / 255.0f);
uniform int cloudWidth;
uniform int cloudDepth;
// uniform vec2 playerPos;

in vec3 space;
in float frag_distance;
in vec4 vertex_color;

void main() {
    float ySpace = clamp((space.y - 5) / cloudDepth, 0.3, 0.6);
    vec3 cloudWhite = vec3(sunlightMultiplier, sunlightMultiplier, sunlightMultiplier);
    vec3 cloudTexture = mix(cloudWhite, sky_color, ySpace);

    float distance = dist(vec2(space.x, space.z), vec2(cloudWidth/2,cloudWidth/2))/cloudWidth;
    distance = clamp(distance*2,0,1);
    
    float alpha = 1;
    if (frag_distance < 1500) {
        alpha = mapAndClamp(frag_distance, 900, 1500, 0, 1);
        if (alpha < 0.1f) {
            discard;
        }
    }
    gl_FragColor = vec4(mix(cloudTexture, sky_color, distance), alpha);
}
