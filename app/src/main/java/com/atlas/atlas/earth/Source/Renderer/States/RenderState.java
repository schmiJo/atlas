package com.atlas.atlas.earth.Source.Renderer.States;


import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Renderer.States.RenderStates.ColorMask;
import com.atlas.atlas.earth.Source.Renderer.States.RenderStates.DepthTest;
import com.atlas.atlas.earth.Source.Renderer.States.RenderStates.FaceCulling;
import com.atlas.atlas.earth.Source.Renderer.States.RenderStates.ScissorTest;
import com.atlas.atlas.earth.Source.Renderer.States.RenderStates.StencilTest;


public class RenderState {

    private DepthTest depthTest;
    private FaceCulling faceCulling;
    private StencilTest stencilTest;
    private ScissorTest scissorTest;
    private ColorMask colorMask;

    public RenderState() {
        depthTest = new DepthTest();
        faceCulling = new FaceCulling();
        stencilTest = new StencilTest();
        scissorTest = new ScissorTest();
        colorMask = new ColorMask(true, true, true, true);
    }
    public void loadGlobalDefaults() {
        faceCulling.setEnabled(true);
        faceCulling.setCullFace(ByteFlags.BACK);
        faceCulling.setWindingOrder(ByteFlags.COUNTERCLOCKWISE);
        depthTest.setDepthTestFunction(ByteFlags.LESS);
        depthTest.setEnabled(true);
    }


    public ColorMask getColorMask() {
        return colorMask;
    }

    public void setColorMask(ColorMask colorMask) {
        this.colorMask = colorMask;
    }

    public DepthTest getDepthTest() {
        return depthTest;
    }

    public void setDepthTest(DepthTest depthTest) {
        this.depthTest = depthTest;
    }

    public FaceCulling getFaceCulling() {
        return faceCulling;
    }

    public void setFaceCulling(FaceCulling faceCulling) {
        this.faceCulling = faceCulling;
    }

    public ScissorTest getScissorTest() {
        return scissorTest;
    }

    public void setScissorTest(ScissorTest scissorTest) {
        this.scissorTest = scissorTest;
    }

    public StencilTest getStencilTest() {
        return stencilTest;
    }

    public void setStencilTest(StencilTest stencilTest) {
        this.stencilTest = stencilTest;
    }
}
