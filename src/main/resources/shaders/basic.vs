#version 330

layout (location = 0) in vec3 position;

uniform mat4 transformWorld;
uniform mat4 transformObject;
uniform mat4 cameraProjection;

void main() { 
	gl_Position = cameraProjection * transformWorld * transformObject * vec4(position, 1);
}

