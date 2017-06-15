package com.atlas.atlas.earth.Source.Core.Testing;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.R;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;


/**
 * Created by Jonas on 5/7/2017.
 */

public class TestTriangleShaderProgram extends ShaderProgramGL3x {
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_transMat;


    public TestTriangleShaderProgram(Context context) {
        super(context, R.raw.testtriangle_vertex_shader, R.raw.testtriangle_fragment_shader);
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
    }
    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_transMat = super.getUniformLocation("transformationMatrix");
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
}
