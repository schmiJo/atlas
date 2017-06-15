package com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x;

import android.opengl.GLES31;


public class ShaderProgramNameGL3x {
    private final int programID;

    public ShaderProgramNameGL3x() {
        programID = GLES31.glCreateProgram();
    }

    public void dispose() {
        GLES31.glDeleteProgram(programID);
    }

    public int getProgramID() {
        return programID;
    }
}
