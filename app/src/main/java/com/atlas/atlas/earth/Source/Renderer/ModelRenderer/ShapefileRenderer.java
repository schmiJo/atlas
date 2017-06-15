package com.atlas.atlas.earth.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.Shader.ShapefileShaderProgram;

import java.util.List;


public class ShapefileRenderer {
    private ShapefileShaderProgram shaderProgram;


    public ShapefileRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new ShapefileShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(List<Renderable> polygons, Camera camera) {
        GLES31.glCullFace(GLES31.GL_CW);
        shaderProgram.loadViewMatrix(camera);
        for (Renderable polygon : polygons) {
            polygon.render(shaderProgram);
        }
        GLES31.glCullFace(GLES31.GL_CCW);
    }


    public ShapefileShaderProgram getShaderProgram() {
        return shaderProgram;
    }


}
