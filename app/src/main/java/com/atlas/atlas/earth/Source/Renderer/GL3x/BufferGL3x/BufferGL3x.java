package com.atlas.atlas.earth.Source.Renderer.GL3x.BufferGL3x;

import android.opengl.GLES31;

import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x.BufferNameGL3x;
import com.atlas.atlas.earth.Source.Renderer.GL3x.TypeconverterGL3x;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


public class BufferGL3x extends BufferNameGL3x {


    private int sizeIinBytes;
    private int bufferType;
    private int usageHint;
    private int dataType;


    public BufferGL3x(byte bufferType, byte usageHint, int sizeInBytes) {
        super();
        this.bufferType = TypeconverterGL3x.convert(bufferType);
        this.usageHint = TypeconverterGL3x.convert(usageHint);
        this.sizeIinBytes = sizeInBytes;
        if(!TypeconverterGL3x.testForValidity(bufferType, ByteFlags.ARRAY_BUFFER, ByteFlags.UNIFORM_BUFFER)){
            throw new IllegalArgumentException("Invalid BufferGL3x target");
        }
        if(!TypeconverterGL3x.testForValidity(usageHint, ByteFlags.STREAM_DRAW, ByteFlags.DYNAMIC_COPY)){
            throw new IllegalArgumentException("Invalid BufferGL3x usage hint");
        }
        TypeconverterGL3x.testForBiggerZero(sizeInBytes);
    }


    public void setData(Buffer data) {
        bind();
        if(data instanceof IntBuffer){
            dataType = GLES31.GL_UNSIGNED_INT;
        }else if(data instanceof ShortBuffer){
            dataType = GLES31.GL_UNSIGNED_SHORT;
        }else if(data instanceof FloatBuffer){
            // TODO: 4/11/2017 test if Medium float works!
            dataType = GLES31.GL_MEDIUM_FLOAT;
        }
        GLES31.glBufferData(bufferType, sizeIinBytes, data, usageHint);
    }

    public int getDataType() {
        return dataType;
    }

    public void bind() {
        GLES31.glBindBuffer(bufferType, super.getId());
    }

    public void unbind() {
        GLES31.glBindBuffer(0, super.getId());
    }
}
