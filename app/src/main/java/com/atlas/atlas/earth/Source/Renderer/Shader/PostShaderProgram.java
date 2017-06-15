package com.atlas.atlas.earth.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.R;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlas.earth.Source.Renderer.Light;


public class PostShaderProgram extends ShaderProgramGL3x {

    public PostShaderProgram(Context context) {
        super(context, R.raw.post_vertex_shader, R.raw.post_fragment_shader);
    }

    private int location_transMat;
    private int location_projectionMatrix;
    private int location_viewMatrix;
       private int location_lightPosition;
    private int location_lightColor;
    private int location_texture0;


    @Override
    protected String globalConstantsVS() {
        return "";
    }
    @Override
    protected String globalConstantsFS() {
        return "";
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "normal");
        super.bindAttribute(2, "texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transMat = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_texture0 = super.getUniformLocation("texture0");
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        super.loadMatrix(location_transMat, transformation);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
    }

    public void loadLight(Light light) {
        super.loadLight(location_lightPosition, location_lightColor, light);
    }

    public void loadTextureIdentifier(){
        super.loadInt(location_texture0, 0);
    }
}
