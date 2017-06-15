package com.atlas.atlas.earth.Source.Core.Tools;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


public class BufferUtils {


    public static FloatBuffer convertFloatArrayToFloatBuffer(float... data) {
        if(data.length <3){
            throw new IllegalArgumentException("For VertexBuffer are at least three Vertices required!");
        }
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static IntBuffer convertIntArrayToIntBuffer(int... data) {
        IntBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    public static ShortBuffer convertShortArrayToShortBuffer(short... data) {
        ShortBuffer buffer = ByteBuffer.allocateDirect(data.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
