package com.atlas.atlas.earth.Source.Core.Testing;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;
import android.util.Log;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;
import com.atlas.atlas.earth.Source.Core.Tools.BufferUtils;

import java.nio.FloatBuffer;


public class PointInWorldspace {

    private Vector3F position;
    private Matrix4f projectionMatrix;
    private Camera camera;

    public PointInWorldspace(Vector3F position, Camera camera, Context context) {
        this.position = position;
        this.camera = camera;
        projectionMatrix = MatricesUtility.createProjectionMatrix(context);
    }

    private void prepare() {
        FloatBuffer vertexData = BufferUtils.convertFloatArrayToFloatBuffer(position.x, position.y, position.z);
        

    }

    private void render() {

    }

    private int createProgramm() {
        int id = GLES31.glCreateProgram();
        GLES31.glAttachShader(id, createShader(GLES31.GL_VERTEX_SHADER));
        GLES31.glAttachShader(id, createShader(GLES31.GL_FRAGMENT_SHADER));
        GLES31.glBindAttribLocation(id, 0, "position");
        GLES31.glLinkProgram(id);
        return id;
    }


    private int createShader(int shaderType) {
        int id = GLES31.glCreateShader(shaderType);
        GLES31.glShaderSource(id, (shaderType == GLES31.GL_VERTEX_SHADER) ? vertexShader : fragmentShader);
        GLES31.glCompileShader(id);
        Log.d("Program", "Shader Type: " + shaderType + ", CompileLog: \n" + GLES31.glGetShaderInfoLog(id));
        return id;
    }

    private int getUniformLocation(int programID, String name) {
        return GLES31.glGetUniformLocation(programID, name);
    }


    private final String vertexShader =
            "in vec4 position;\n" +
                    "uniform mat4 projectionMatrix;" +
                    "uniform mat4 viewMatrix;" +
                    "uniform mat4 transformationMatrix;" +
                    "void main(){gl_Position = projectionMatrix * viewMatrix * transformationMatrix * position;}";

    private final String fragmentShader =
            "uniform vec4 color;\n " +
                    "out vec4 fragmentColor;\n" +
                    "void main(){fragmentColor = vec4(1,0.2,0.2,1);}";
}

