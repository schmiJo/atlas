package com.atlas.atlas.earth.Source.Renderer.States.RenderStates;

import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Renderer.GL3x.TypeconverterGL3x;


public class DepthTest {

    private boolean enabled;
    private byte depthTestFunction;


    public DepthTest() {
        enabled = false;
        depthTestFunction = ByteFlags.LESS;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public byte getDepthTestFunction() {
        return depthTestFunction;
    }

    public void setDepthTestFunction(byte depthTestFunction) {
        TypeconverterGL3x.testForValidity(depthTestFunction, ByteFlags.NEVER, ByteFlags.ALWAYS);
        this.depthTestFunction = depthTestFunction;
    }
}
