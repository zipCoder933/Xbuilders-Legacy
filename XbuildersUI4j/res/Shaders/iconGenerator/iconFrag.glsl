#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 vertTexCoord;
varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;
varying vec4 ShadowCoord;

uniform sampler2D texture;

void main() {
    vec4 fragColor = texture2D(texture, vertTexCoord.st);
    if (fragColor.w == 0.0f) {  // the w represents the alpha
        discard;
    }
    vec3 blockTexture = fragColor.xyz;
    blockTexture *= 1.1;

    gl_FragColor = vec4(blockTexture, fragColor.w);
}
