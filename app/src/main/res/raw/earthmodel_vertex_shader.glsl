
in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform vec3 sunPosition;
uniform vec3 cameraPosition;


out vec3 unitNormal;
out vec3 unitToLight;
out vec3 unitToCamera;

mat4 rotationMatrix(vec3 axis, float angle)
{
    axis = normalize(axis);
    float s = sin(angle);
    float c = cos(angle);
    float oc = 1.0 - c;

    return mat4(oc * axis.x * axis.x + c,           oc * axis.x * axis.y - axis.z * s,  oc * axis.z * axis.x + axis.y * s,  0.0,
                oc * axis.x * axis.y + axis.z * s,  oc * axis.y * axis.y + c,           oc * axis.y * axis.z - axis.x * s,  0.0,
                oc * axis.z * axis.x - axis.y * s,  oc * axis.y * axis.z + axis.x * s,  oc * axis.z * axis.z + c,           0.0,
                0.0,                                0.0,                                0.0,                                1.0);
}

void main(){

    mat4 preRotation  = rotationMatrix(vec3(1,0,-1), 3.141592);

    vec4 worldPosition = transformationMatrix * vec4(position,1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    unitNormal = normalize(position);
    unitToLight = normalize(sunPosition - worldPosition.xyz);
    unitToCamera = normalize(cameraPosition - worldPosition.xyz);

}