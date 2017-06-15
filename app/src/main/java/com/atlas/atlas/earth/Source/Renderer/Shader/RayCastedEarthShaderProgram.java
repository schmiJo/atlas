package com.atlas.atlas.earth.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.R;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Matrices.Matrix4x2f;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlas.earth.Source.Renderer.Light;


public class RayCastedEarthShaderProgram extends ShaderProgramGL3x {

    private int location_projectionMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_viewMatrix;
    private int location_cameraPosition;
    private int location_texture0;
    private int location_texture1;
    private int location_fullLightOption;
    private int location_globeOverRadiiSquared;
    private int location_cameraPositionSquared;
    private int location_diffuseSpecularAmbientLighting;
    private int location_useAverageDepth;
    private int location_modelZToClipCoordinates;

    public RayCastedEarthShaderProgram(Context context) {
        super(context, R.raw.raycasted_globe_vertex_shader, R.raw.raycasted_globe_fragment_shader);
    }
    @Override
    protected String globalConstantsVS() {
        return "";
    }
    @Override
    protected String globalConstantsFS() {
        return "const float og_oneOverPi = 0.3183098861837907; \n" +
                "const float og_oneOverTwoPi = 0.15915494309189535; \n";
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_lightPosition = super.getUniformLocation("sunPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_cameraPosition = super.getUniformLocation("cameraPosition");
        location_texture0 = super.getUniformLocation("texture0");
        location_texture1 = super.getUniformLocation("texture1");
        location_fullLightOption = super.getUniformLocation("enableFullLightning");
        location_globeOverRadiiSquared = super.getUniformLocation("globeOneOverRadiiSquared");
        location_cameraPositionSquared = super.getUniformLocation("cameraPositionSquared");
        location_diffuseSpecularAmbientLighting = super.getUniformLocation("diffuseSpecularAmbientShininess");
        location_useAverageDepth = super.getUniformLocation("loadUseAverageDepth");
        location_modelZToClipCoordinates = super.getUniformLocation("modelZToClipCoordinates");
    }

    public void loadGlobeOverRadiiSquaredValue(Vector3F value){
        super.loadVector3F(location_globeOverRadiiSquared, value);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
        super.loadVector3F(location_cameraPosition, camera.getPosition());
        super.loadVector3F(location_cameraPositionSquared, camera.getPosition().multiplyComponents(camera.getPosition()));
    }

    public void loadDiffuseSpecularAmbientLighting(Vector4F value){
        super.loadVector4F(location_diffuseSpecularAmbientLighting, value);
    }
    public void loadUseAverageDepth(boolean value){
        super.loadBoolean(location_useAverageDepth, value);
    }
    public void loadModelZtoClipCoordinates(Matrix4x2f value){
        super.loadMatrix4x2(location_modelZToClipCoordinates, value);
    }

    public void loadLight(Light light) {
        super.loadLight(location_lightPosition, location_lightColor, light);
    }
    public void loadTextureIdentifier(){
        super.loadInt(location_texture0, 0);
        super.loadInt(location_texture1, 1);
    }
    public void loadFullLightningOption(boolean enabled){
        super.loadBoolean(location_fullLightOption, enabled);
    }

}
