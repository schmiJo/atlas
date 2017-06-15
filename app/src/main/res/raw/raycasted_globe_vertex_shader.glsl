
in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;





out vec3 worldPosition;


void main(){


    worldPosition = position;
    gl_Position = projectionMatrix * viewMatrix * vec4(position,1.0);
    //gl_Position =  vec4(position,1.0);
}