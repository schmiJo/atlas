//+4 lines


in vec3 worldPosition;
out vec4 fragmentColor;


uniform mat4x2 modelZToClipCoordinates;

uniform sampler2D texture0; //Day Texture
uniform sampler2D texture1; //NightTexture

uniform vec3 sunPosition;
uniform vec3 cameraPosition;
uniform vec3 cameraPositionSquared;
uniform vec3 globeOneOverRadiiSquared;
uniform vec4 diffuseSpecularAmbientShininess;

uniform float enableFullLightning;
uniform float useAverageDepth;

const float blendDuration = 0.1;
const float blendDurationScale = 5.0; // blendDurationScale = 1/(2*blendDuration);


struct Intersection
{
    bool  Intersects;
    float NearTime;         // Along ray
    float FarTime;          // Along ray
};

Intersection rayIntersectEllipsoid(vec3 rayOrigin, vec3 rayOriginSquared, vec3 rayDirection, vec3 oneOverEllipsoidRadiiSquared){
    float a = dot(rayDirection * rayDirection, oneOverEllipsoidRadiiSquared);
    float b = 2.0 * dot(rayOrigin * rayDirection, oneOverEllipsoidRadiiSquared);
    float c = dot(rayOriginSquared, oneOverEllipsoidRadiiSquared) - 1.0;
    float discriminant = b * b - 4.0 * a * c;

    if (discriminant < 0.0)
    {
        return Intersection(false, 0.0, 0.0);
    }
    else if (discriminant == 0.0)
    {
        float time = -0.5 * b / a;
        return Intersection(true, time, time);
    }

    float t = -0.5 * (b + (b > 0.0 ? 1.0 : -1.0) * sqrt(discriminant));
    float root1 = t / a;
    float root2 = c / t;

    return Intersection(true, min(root1, root2), max(root1, root2));
}

vec3 geodeticSurfaceNormal(vec3 positionOnEllipsoid, vec3 oneOverEllipsoidRadiiSquared){
    return normalize(positionOnEllipsoid * oneOverEllipsoidRadiiSquared);
}

float lightIntensity(vec3 normal, vec3 toLight, vec3 toEye, vec4 diffuseSpecularAmbientShininess){
    vec3 toReflectedLight = reflect(-toLight, normal);

    float diffuse = max(dot(toLight, normal), 0.0);
    float specular = max(dot(toReflectedLight, toEye), 0.0);
    specular = pow(specular, diffuseSpecularAmbientShininess.w);

    return (diffuseSpecularAmbientShininess.x * diffuse) +
           (diffuseSpecularAmbientShininess.y * specular) +
            diffuseSpecularAmbientShininess.z;
}

float computeWorldPositionDepth(vec3 position, mat4x2 modelZToClipCoordinates){
    vec2 v = modelZToClipCoordinates * vec4(position, 1);   // clip coordinates
    v.x /= v.y;                                             // normalized device coordinates
    v.x = (v.x + 1.0) * 0.5;
    return v.x;
}

vec2 computeTextureCoordinates(vec3 normal){
    return vec2(atan(normal.y, normal.x) * og_oneOverTwoPi + 0.5, asin(normal.z) * og_oneOverPi + 0.5);
}

vec4 nightColor(vec3 normal){
    return texture(texture1, computeTextureCoordinates(normal));
}

vec4 dayColor(vec3 normal, vec3 toLight, vec3 toEye, vec4 diffuseSpecularAmbientShininess){
    float intensity = lightIntensity(normal, toLight, toEye, diffuseSpecularAmbientShininess);
    return intensity * texture(texture0, computeTextureCoordinates(normal));
}



void main(){
     vec3 rayDirection = normalize(worldPosition - sunPosition);
     Intersection i = rayIntersectEllipsoid(cameraPosition, cameraPosition * cameraPosition, rayDirection, globeOneOverRadiiSquared);

     if (i.Intersects){
        vec3 position = cameraPosition + (i.NearTime * rayDirection);
        vec3 normal = geodeticSurfaceNormal(position, globeOneOverRadiiSquared);

        vec3 toLight = normalize(/*og_cameraLightPosition -*/ position);
        vec3 toEye = normalize(cameraPosition - position);

        float intensity = lightIntensity(normal, toLight, toEye, diffuseSpecularAmbientShininess);

        fragmentColor = intensity * texture(texture0, computeTextureCoordinates(normal));

        if (useAverageDepth > 0.5){
            position = cameraPosition + (mix(i.NearTime, i.FarTime, 0.5) * rayDirection);
        }

        //gl_FragDepth = computeWorldPositionDepth(position, modelZToClipCoordinates);


     }else{
        discard;
     }

}


