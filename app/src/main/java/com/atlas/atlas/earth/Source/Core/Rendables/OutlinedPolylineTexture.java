package com.atlas.atlas.earth.Source.Core.Rendables;

import com.atlas.atlas.earth.Source.Renderer.DrawState;
import com.atlas.atlas.earth.Source.Renderer.States.RenderState;

/**
 * Created by Jonas on 3/21/2017.
 */

public class OutlinedPolylineTexture {


    private DrawState drawState;
    private double width;
    private double outlineWidth;


    public OutlinedPolylineTexture()
    {
        RenderState renderState = new RenderState();
        renderState.getFaceCulling().setEnabled(false);
       // renderState.Blending.Enabled = true;
       // renderState.Blending.SourceRGBFactor = SourceBlendingFactor.SourceAlpha;
       // renderState.Blending.SourceAlphaFactor = SourceBlendingFactor.SourceAlpha;
       // renderState.Blending.DestinationRGBFactor = DestinationBlendingFactor.OneMinusSourceAlpha;
       // renderState.Blending.DestinationAlphaFactor = DestinationBlendingFactor.OneMinusSourceAlpha;

        drawState = new DrawState();
        drawState.setRenderState(renderState);

        width = 3;
        outlineWidth = 2;
    }
}
