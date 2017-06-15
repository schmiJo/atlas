package com.atlas.atlas.earth.Source.Renderer.States.RenderStates;


public class StencilTest {
    private boolean enable;
    private StencilTestFace frontFace;
    private StencilTestFace backFace;

    public StencilTest() {
        enable = false;
        frontFace = new StencilTestFace();
        backFace = new StencilTestFace();
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public StencilTestFace getBackFace() {
        return backFace;
    }

    public void setBackFace(StencilTestFace backFace) {
        this.backFace = backFace;
    }

    public StencilTestFace getFrontFace() {
        return frontFace;
    }

    public void setFrontFace(StencilTestFace frontFace) {
        this.frontFace = frontFace;
    }
}
