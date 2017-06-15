package com.atlas.atlas.earth.Source.Renderer.GL3x;

import android.opengl.GLES31;
import android.support.annotation.Nullable;

import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Renderer.GL3x.BufferGL3x.BufferGL3x;
import com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;

import java.nio.FloatBuffer;


public class VertexArrayGL3x extends VertexArrayNameGL3x {

    public VertexArrayGL3x(FloatBuffer positionBuffer, @Nullable FloatBuffer normalBuffer, @Nullable FloatBuffer textureBuffer) {
        bindAndEnableVAO();

        //Store Positions
        storeFloatBufferInVAO(0, 3, positionBuffer);

        //Check for normals
        if (normalBuffer != null) {
            storeFloatBufferInVAO(1, 3, normalBuffer);
        }

        //Check for Texture Coordinates
        if (textureBuffer != null) {
            storeFloatBufferInVAO(2, 2, textureBuffer);
        }
        unbindAndDisableVAO();
    }

    private void storeFloatBufferInVAO(int index, int dimension, FloatBuffer data) {
        BufferGL3x buffer = new BufferGL3x(ByteFlags.ARRAY_BUFFER, ByteFlags.STATIC_DRAW, data.capacity() * 4);
        buffer.setData(data);
        GLES31.glEnableVertexAttribArray(index);
        GLES31.glVertexAttribPointer(index, dimension, GLES31.GL_FLOAT, false, 0, 0);
    }


}
