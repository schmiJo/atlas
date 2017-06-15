package com.atlas.atlas.earth.Source.Core.Scene.Shapefile;

import com.atlas.atlas.earth.Source.Core.BoundingVolumes.EllipsoidTangentPlane;
import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlas.earth.Source.Core.Geometry.CSConverter;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlas.earth.Source.Core.Polygons.EarClippingOnEllipsoid;
import com.atlas.atlas.earth.Source.Core.Polygons.TriangleMeshSubdivision;
import com.atlas.atlas.earth.Source.Core.Polygons.TriangleMeshSubdivisionResult;
import com.atlas.atlas.earth.Source.Core.Rendables.Shapefiles.SimplePolygonAlgorithms;
import com.atlas.atlas.earth.Source.Renderer.Mesh.Mesh;
import com.atlas.atlas.earth.Source.Renderer.Mesh.VertexAttributeCollection;

import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Polygon {

    private Vector4F color;
    private Mesh mesh;


    public Polygon(Ellipsoid globeShape, List<Vector3D> positions) {
        mesh = new Mesh();
        //
        // Pipeline Stage 1a:  Clean up - Remove duplicate positions
        //

        List<Vector3D> cleanPositions = SimplePolygonAlgorithms.cleanUp(positions);

        //
        // Pipeline Stage 1b:  Clean up - Swap winding order
        //
        EllipsoidTangentPlane plane = new EllipsoidTangentPlane(globeShape, cleanPositions);
        List<Vector2D> positionsOnPlane = plane.ComputePositionsOnPlane(cleanPositions);


        if (SimplePolygonAlgorithms.ComputeWindingOrder(positionsOnPlane) == ByteFlags.CLOCKWISE) {
            Collections.reverse(cleanPositions);
        }

        //
        // Pipeline Stage 2:  Triangulate
        //

        List<TriangleIndicesInt> indices = EarClippingOnEllipsoid.Triangulate(cleanPositions);


        //
        // Pipeline Stage 3:  Subdivide
        //
        TriangleMeshSubdivisionResult result = TriangleMeshSubdivision.Compute(cleanPositions, indices, CSConverter.toRadians(1));

        //
        // Pipeline Stage 4:  Set height
        //

        for (int i = 0; i < result.getPositions().size(); i++) {
            result.getPositions().set(i, globeShape.scaleToGeodeticSurface(result.getPositions().get(i)));
        }

        mesh.addVertexAttributes(new VertexAttributeCollection(Vector3D.toVector3FArray(result.getPositions()), null, null));
        mesh.addTriangles(result.getIndices());

        Random random = new Random();
        color = new Vector4F((random.nextInt(100) + 1) / 100f, (random.nextInt(100) + 1) / 100f, (random.nextInt(100) + 1) / 100f, 0.3f);

    }


    public Mesh getMesh() {
        return mesh;
    }

    public Vector4F getColor() {
        return color;
    }
}
