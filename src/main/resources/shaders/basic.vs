#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texture_coord;

uniform mat4 transformWorld;
uniform mat4 transformObject;
uniform mat4 cameraProjection;

out vec2 texture;

void main() {
	texture = texture_coord;
	gl_Position = cameraProjection * transformWorld * transformObject * vec4(position, 1);
}

