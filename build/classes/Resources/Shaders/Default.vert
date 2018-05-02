#version 140

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform mat4 normalMatrix;

layout(location=0) in vec3 in_Position;
layout(location=1) in vec3 in_Normal;
layout(location=2) in vec2 in_texCoord;

out vec4 pass_Pos;
out vec3 pass_Normal;
out vec4 pass_Eye;

void main(void) {
    pass_Pos = modelMatrix * vec4(in_Position,1.0);
    pass_Normal = vec4(in_Normal, 0.0) * normalMatrix;
    pass_Eye = vec4(-transpose(mat3(viewMatrix)) * viewMatrix[3].xyz, 1.0);
    gl_Position=projectionMatrix * viewMatrix * modelMatrix * vec4(in_Position,1);
}