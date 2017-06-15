package com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x;


import android.opengl.GLES31;

public class VertexArrayNameGL3x {

    private int[] id = new int[1];

    protected VertexArrayNameGL3x() {
        GLES31.glGenVertexArrays(1, id, 0);
    }

    public int getId() {
        return id[0];
    }

    public void bindAndEnableVAO(){
        GLES31.glBindVertexArray(id[0]);
        GLES31.glEnableVertexAttribArray(0);
        GLES31.glEnableVertexAttribArray(1);
        GLES31.glEnableVertexAttribArray(2);
    }

    public static void unbindAndDisableVAO(){
        GLES31.glDisableVertexAttribArray(0);
        GLES31.glDisableVertexAttribArray(1);
        GLES31.glDisableVertexAttribArray(2);
        GLES31.glBindVertexArray(0);
    }

    public void dispose(){
        GLES31.glDeleteVertexArrays(1, id, 0);

    }
}
