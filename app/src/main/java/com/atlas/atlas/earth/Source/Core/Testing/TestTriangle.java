package com.atlas.atlas.earth.Source.Core.Testing;

import android.opengl.GLES31;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlas.earth.Source.Renderer.Mesh.Mesh;


/**
 * Created by Jonas on 5/7/2017.
 */

public class TestTriangle extends Renderable {

    public TestTriangle() {
        super(new Vector3F(0, 0, 0), 0, 0, 0, 1);
    }


    public void setTriangle(Vector3F p1, Vector3F p2, Vector3F p3) {
        Mesh mesh = new Mesh(
                new int[]{
                        0, 1, 2
                }, new float[]{
                p3.x, p3.y, p3.z,
                p2.x, p2.y, p2.z,
                p1.x, p1.y, p1.z,
        },
                null,
                null);
        super.mesh = mesh;
    }

    @Override
    public void onCreate() {
        //Mesh is created in setTriangle
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {


        TestTriangleShaderProgram testTriangleShaderProgram = (TestTriangleShaderProgram) shaderProgram;

        testTriangleShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
                getPosition(),
                getRotX(),
                getRotY(),
                getRotZ(),
                getScale()));

        mesh.getVertexArray().bindAndEnableVAO();
        mesh.getIndicesBuffer().bind();

        GLES31.glDrawElements(GLES31.GL_TRIANGLES, mesh.getVertexCount(), mesh.getIndicesBuffer().getDataType(), 0);

        mesh.getIndicesBuffer().unbind();
        VertexArrayNameGL3x.unbindAndDisableVAO();
    }

}
