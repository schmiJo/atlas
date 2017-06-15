package com.atlas.atlas.earth.Source.Core.Rendables.EarthModel;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES31;

import com.atlas.atlas.earth.EarthViewOptions;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Geometry.cartesianCS.Tessellation.SubdivisionSphereTessellator;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;
import com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlas.earth.Source.Renderer.Shader.EarthShaderProgram;


class GeographicGlobe extends Renderable {

    private Ellipsoid ellipsoid;
    private Context context;

    GeographicGlobe(Vector3F position, float rotX, float rotY, float rotZ, float scale, Context context) {
        super(position, rotX, rotY, rotZ, scale);
    this.context = context;
    }

    @Override
    public void onCreate() {
        mesh = SubdivisionSphereTessellator.compute(5);
        loadTextures(context);
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {

        EarthShaderProgram earthShaderProgram = (EarthShaderProgram) shaderProgram;

        earthShaderProgram.loadShineVariables(getTexture0().getShineDamper(), getTexture0().getReflectivity());
        earthShaderProgram.loadTextureIdentifier();
        earthShaderProgram.loadFullLightningOption(EarthViewOptions.isFullLightning());

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());

        GLES31.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture1().getTextureID());

        earthShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
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



    public void setShape(Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

}
