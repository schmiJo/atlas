package com.atlas.atlas.earth.Source.Core.Testing;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;


/**
 * Created by Jonas on 5/7/2017.
 */

public class TestTriangleRenderer {

    private TestTriangleShaderProgram shaderProgram;

    public TestTriangleRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new TestTriangleShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, Camera camera){
        shaderProgram.loadViewMatrix(camera);
        renderable.render(shaderProgram);
    }


    public TestTriangleShaderProgram getShaderProgram() {
        return shaderProgram;
    }

}
