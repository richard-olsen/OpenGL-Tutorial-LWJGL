#version 330

uniform sampler2D sampleTexture;

out vec4 fragColor;

in vec2 texture;

void main() {
	fragColor = texture2D(sampleTexture, texture); //vec4(1,1,0,1);
}

