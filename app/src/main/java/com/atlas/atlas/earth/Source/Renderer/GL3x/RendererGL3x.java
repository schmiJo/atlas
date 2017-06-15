package com.atlas.atlas.earth.Source.Renderer.GL3x;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlas.earth.Source.Core.Rendables.EarthModel.EarthModel;
import com.atlas.atlas.earth.Source.Core.Rendables.EarthModel.RayCastedGlobe;
import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Core.Rendables.Shapefiles.ShapefileRenderable;
import com.atlas.atlas.earth.Source.Core.Rendables.SpaceBackground;
import com.atlas.atlas.earth.Source.Core.Testing.TestTriangle;
import com.atlas.atlas.earth.Source.Core.Testing.TestTriangleRenderer;
import com.atlas.atlas.earth.Source.Renderer.Light;
import com.atlas.atlas.earth.Source.Renderer.ModelRenderer.BackgroundRenderer;
import com.atlas.atlas.earth.Source.Renderer.ModelRenderer.EarthModelRenderer;
import com.atlas.atlas.earth.Source.Renderer.ModelRenderer.PostRenderer;
import com.atlas.atlas.earth.Source.Renderer.ModelRenderer.RayCastedGlobeRenderer;
import com.atlas.atlas.earth.Source.Renderer.ModelRenderer.ShapefileRenderer;
import com.atlas.atlas.earth.Source.Renderer.Scene.SceneState;
import com.atlas.atlas.earth.Source.Renderer.States.RenderState;

import java.util.ArrayList;
import java.util.List;


public class RendererGL3x {

    private Matrix4f projectionMatrix;
    private EarthModelRenderer earthRenderer;
    private RayCastedGlobeRenderer rayCastedGlobeRenderer;
    private BackgroundRenderer backgroundRenderer;
    private ShapefileRenderer shapefileRenderer;
    private PostRenderer postRenderer;
    private TestTriangleRenderer testTriangleRenderer;
    private List<Renderable> renderables;
    private LinkedList posts;


    public RendererGL3x(Context context, RenderState renderState, SceneState sceneState) {
        projectionMatrix = sceneState.getProjectionMatrix();
        earthRenderer = new EarthModelRenderer(context, projectionMatrix);
        rayCastedGlobeRenderer = new RayCastedGlobeRenderer(context, projectionMatrix);
        backgroundRenderer = new BackgroundRenderer(context, projectionMatrix);
        shapefileRenderer = new ShapefileRenderer(context, projectionMatrix);
        postRenderer = new PostRenderer(context, projectionMatrix);
        testTriangleRenderer = new TestTriangleRenderer(context, projectionMatrix);
        forceRenderStates(renderState);
    }

    /**
     * Loader methods
     */
    public void postRendables(List<Renderable> renderables) {
        this.renderables = renderables;
    }
    public void postPosts(LinkedList posts) {
        this.posts = posts;
    }

    public void render(Light light, SceneState sceneState) {
        sceneState.getCamera().calculateCameraPosition();
        light.calculateAngleByTime();

        int renderableCounter = renderables.size();
        boolean lastRenderable = false;
        List<Renderable> shapefiles = new ArrayList<>();
        clearBuffers();


        for (Renderable renderable : renderables) {
            renderableCounter--;
            if (renderableCounter == 0) {
                lastRenderable = true;
            }

            if (renderable instanceof SpaceBackground) {
                backgroundRenderer.getShaderProgram().start();
                backgroundRenderer.render(renderable, sceneState.getCamera());
                backgroundRenderer.getShaderProgram().stop();

            } else if (renderable instanceof EarthModel) {
                earthRenderer.getShaderProgram().start();
                earthRenderer.render(renderable, sceneState.getCamera(), light);
                earthRenderer.getShaderProgram().stop();

            } else if (renderable instanceof RayCastedGlobe) {
                rayCastedGlobeRenderer.getShaderProgram().start();
                rayCastedGlobeRenderer.render(renderable, sceneState, light);
                rayCastedGlobeRenderer.getShaderProgram().stop();

            } else if (renderable instanceof ShapefileRenderable) {
                shapefiles.add(renderable);
                if (lastRenderable) {
                    shapefileRenderer.getShaderProgram().start();
                    shapefileRenderer.render(shapefiles, sceneState.getCamera());
                    shapefileRenderer.getShaderProgram().stop();
                }

            } else if (renderable instanceof TestTriangle) {
                GLES31.glDisable(GLES31.GL_DEPTH_TEST);
                testTriangleRenderer.getShaderProgram().start();
                testTriangleRenderer.render(renderable, sceneState.getCamera());
                testTriangleRenderer.getShaderProgram().stop();
                GLES31.glEnable(GLES31.GL_DEPTH_TEST);
                GLES31.glDepthFunc(GLES31.GL_LESS);
                GLES31.glDepthRangef(0.1f, 50f);
            }
            if (posts.size() > 0) {
                postRenderer.getShaderProgram().start();
                postRenderer.render(posts.<Renderable>asArrayList(), sceneState.getCamera(), light);
                postRenderer.getShaderProgram().stop();
            }
        }
    }

    /**
     * Worker Methods
     */
    private void forceRenderStates(RenderState renderState) {
        if (renderState.getDepthTest().isEnabled()) {
           GLES31.glEnable(GLES31.GL_DEPTH_TEST);
           GLES31.glDepthFunc(TypeconverterGL3x.convert(renderState.getDepthTest().getDepthTestFunction()));
           GLES31.glDepthRangef(0.1f, 50f);
        }
        if (renderState.getFaceCulling().isEnabled()) {
            GLES31.glEnable(GLES31.GL_CULL_FACE);
            GLES31.glCullFace(TypeconverterGL3x.convert(renderState.getFaceCulling().getCullFace()));
            GLES31.glFrontFace(TypeconverterGL3x.convert(renderState.getFaceCulling().getWindingOrder()));
        }
    }


    private void clearBuffers() {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        GLES31.glClearColor(0.05f, 0.05f, 0.1f, 1);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


}
