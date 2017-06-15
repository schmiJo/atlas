package com.atlas.atlas.earth.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Renderer.Light;
import com.atlas.atlas.earth.Source.Renderer.Shader.PostShaderProgram;

import java.util.List;


public class PostRenderer {

    private PostShaderProgram shaderProgram;

    public PostRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new PostShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
    }

    public void render(List<Renderable> renderables, Camera camera, Light light) {
        shaderProgram.loadViewMatrix(camera);
        shaderProgram.loadLight(light);
        for (Renderable renderable : renderables) {
            renderable.render(shaderProgram);
        }
    }

    public PostShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
