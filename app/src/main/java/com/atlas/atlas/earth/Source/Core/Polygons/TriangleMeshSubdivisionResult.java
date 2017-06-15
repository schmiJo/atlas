package com.atlas.atlas.earth.Source.Core.Polygons;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;

import java.util.List;


public class TriangleMeshSubdivisionResult {
    private List<Vector3D> positions;
    private List<TriangleIndicesInt> indices;

    public TriangleMeshSubdivisionResult(List<Vector3D> positions, List<TriangleIndicesInt> indices) {
        this.positions = positions;
        this.indices = indices;
    }

    public List<Vector3D> getPositions() {
        return positions;
    }

    public List<TriangleIndicesInt> getIndices() {
        return indices;
    }
}
