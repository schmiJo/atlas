package com.atlas.atlas.earth.Source.Renderer;

import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Renderer.Shader.EarthShaderProgram;
import com.atlas.atlas.earth.Source.Renderer.States.RenderState;

/**
 * DrawState will delete the VAO Array lists, stored in the memory, and will allocate them into GPU Buffers.
 * Therefore VAO Editing isn't possible after the DrawState Object is created
 */


public class DrawState {
    private RenderState renderState;
    private EarthShaderProgram shaderProgram;
    private Renderable renderable;

    public DrawState(){
        renderState = new RenderState();
    }
    public DrawState(RenderState renderState, EarthShaderProgram earthShaderProgram, Renderable renderable) {
        this.renderState = renderState;
        this.shaderProgram = earthShaderProgram;
        earthShaderProgram.start();
        this.renderable = renderable;

    }

    public RenderState getRenderState() {
        return renderState;
    }

    public void setRenderState(RenderState renderState) {
        this.renderState = renderState;
    }

    public EarthShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(EarthShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public Renderable getRenderable() {
        return renderable;
    }

    public void setRenderable(Renderable renderable) {
        this.renderable = renderable;
    }
}

