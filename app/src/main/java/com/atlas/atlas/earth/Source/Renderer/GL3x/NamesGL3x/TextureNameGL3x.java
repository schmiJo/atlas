package com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x;

import android.opengl.GLES31;


public class TextureNameGL3x {
    private final int[] textureID = new int[1];

    public TextureNameGL3x() {
        GLES31.glGenTextures(1,textureID,0);
    }

    public void dispose() {
        GLES31.glDeleteTextures(1,textureID,0);
    }

    public int getTextureID() {
        return textureID[0];
    }
}
