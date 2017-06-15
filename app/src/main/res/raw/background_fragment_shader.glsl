uniform sampler2D texture0;

in vec2 textureCoords;
out vec4 fragmentColor;

void main(){
fragmentColor = vec4(0.3, 0.3, 0.3, 1.0)*texture(texture0, textureCoords);
}