package com.atlas.atlas.earth.Source.Renderer.Shader;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.R;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;


public class BackgroundShaderProgram extends ShaderProgramGL3x {

    private int location_projectionMatrix;
    private int location_transMat;
    private int location_viewMatrix;
    private int location_texture0;

    private static final int vsRawID = R.raw.background_vertex_shader;
    private static final int fsRawID = R.raw.background_fragment_shader;

    public BackgroundShaderProgram(Context context) {
        super(context, vsRawID, fsRawID);
    }

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
        super.bindAttribute(2, "texCoord");
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_transMat = super.getUniformLocation("transformationMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_texture0 = super.getUniformLocation("texture0");
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        super.loadMatrix(location_transMat, transformation);
    }
    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MatricesUtility.createViewMatrix(camera));
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
    public void loadTextureIdentifier(){
        super.loadInt(location_texture0, 0);
    }


}
