package com.atlas.atlas.earth.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Renderer.Light;
import com.atlas.atlas.earth.Source.Renderer.Scene.SceneState;
import com.atlas.atlas.earth.Source.Renderer.Shader.RayCastedEarthShaderProgram;


/**
 * Created by Jonas on 4/19/2017.
 */

public class RayCastedGlobeRenderer {

    private RayCastedEarthShaderProgram shaderProgram;


    public RayCastedGlobeRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new RayCastedEarthShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, SceneState sceneState, Light light) {
        shaderProgram.loadLight(light);
        shaderProgram.loadViewMatrix(sceneState.getCamera());
        shaderProgram.loadModelZtoClipCoordinates(sceneState.getModelZToClipCoordinates());
        shaderProgram.loadDiffuseSpecularAmbientLighting(new Vector4F(sceneState.getDiffuseIntensity(), sceneState.getSpecularIntensity(), sceneState.getAmbientIntensity(), sceneState.getShininess()));
        renderable.render(shaderProgram);

    }


    public RayCastedEarthShaderProgram getShaderProgram() {
        return shaderProgram;
    }

}
