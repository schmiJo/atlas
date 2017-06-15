in vec4 position;
in vec2 texCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;


out vec2 textureCoords;

void main(){
    gl_Position =  projectionMatrix * transformationMatrix * position;
    textureCoords = texCoord;
}