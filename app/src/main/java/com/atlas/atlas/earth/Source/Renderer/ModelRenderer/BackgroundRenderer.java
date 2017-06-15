package com.atlas.atlas.earth.Source.Renderer.ModelRenderer;


import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.Shader.BackgroundShaderProgram;


public class BackgroundRenderer {

    private BackgroundShaderProgram shaderProgram;
    public BackgroundRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new BackgroundShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, Camera camera){
        shaderProgram.loadViewMatrix(camera);
      renderable.render(shaderProgram);
    }



    public BackgroundShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
