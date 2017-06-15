package com.atlas.atlas.earth.Source.Renderer.Mesh;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.util.List;

/**
 * Here is all VAO index related data stored.
 */
public class VertexAttributeCollection {

    private List<Vector3F> positions = null;
    private List<Vector3F> normals = null;
    private List<Vector2F> textureCoordinates = null;

    public VertexAttributeCollection(@NonNull List<Vector3F> positions) {
        this.positions = positions;
    }

    public VertexAttributeCollection(@NonNull List<Vector3F> positions, @Nullable List<Vector3F> normals, @Nullable List<Vector2F> textureCoordinates) {
        this.positions = positions;
        this.normals = normals;
        this.textureCoordinates = textureCoordinates;
    }

    public List<Vector3F> getPositions() {
        return positions;
    }

    public List<Vector3F> getNormals() {
        return normals;
    }

    public List<Vector2F> getTextureCoordinates() {
        return textureCoordinates;
    }

    public void clear() {
        positions.clear();
        positions = null;

        if (normals != null) {
            normals.clear();
            normals = null;
        }

        if (textureCoordinates != null) {
            textureCoordinates.clear();
            textureCoordinates = null;
        }
    }
}
