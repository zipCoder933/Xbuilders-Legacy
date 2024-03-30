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

uniform vec3 skyColor;
uniform vec3 domeColor;

in vec3 space;
in vec4 vertex_color;

void main() {
    float spaceCoords = clamp((space.y / 200.0) + 0.6, 0.0, 1.0);
    if (spaceCoords < 0.01) {
        discard;
    }else{
        gl_FragColor = vec4(domeColor, spaceCoords);
    }
}
