package com.atlas.atlas.earth.Source.Core.Geometry.cartesianCS.Tessellation;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Renderer.Mesh.Mesh;
import com.atlas.atlas.earth.Source.Renderer.Mesh.VertexAttributeCollection;

import java.util.ArrayList;
import java.util.List;


public class SubdivisionSphereTessellator {


    public static Mesh compute(int numberOfSubdivisions) {
        Mesh mesh = new Mesh();

        List<TriangleIndicesShort> triangles = new ArrayList<>(SubdivisionUtility.NumberOfTriangles(numberOfSubdivisions));

        //Initial tetrahedron
        final float rootTwoDivByThree = (float) ((Math.sqrt(2.0) / 3.0));
        final float oneThird = (float) ((1.0 / 3.0));
        final float rootSixDivByThree = (float) ((Math.sqrt(6.0) / 3.0));

        List<Vector3F> positions = new ArrayList<>();
        positions.add(new Vector3F(0, 0, 1));
        positions.add(new Vector3F(0, (2 * rootTwoDivByThree), -oneThird));
        positions.add(new Vector3F(-rootSixDivByThree, -rootTwoDivByThree, -oneThird));
        positions.add(new Vector3F(rootSixDivByThree, -rootTwoDivByThree, -oneThird));

        subdivide(positions, triangles , new TriangleIndicesShort((short) 0, (short) 1, (short) 2), numberOfSubdivisions);
        subdivide(positions, triangles , new TriangleIndicesShort((short) 0, (short) 2, (short) 3), numberOfSubdivisions);
        subdivide(positions, triangles , new TriangleIndicesShort((short) 0, (short) 3, (short) 1), numberOfSubdivisions);
        subdivide(positions, triangles , new TriangleIndicesShort((short) 1, (short) 3, (short) 2), numberOfSubdivisions);


        mesh.addVertexAttributes(new VertexAttributeCollection(positions));
        mesh.addTriangles(triangles);
        return mesh;
    }

    private static void subdivide(List<Vector3F> positions, List<TriangleIndicesShort> triangles , TriangleIndicesShort triangle, int level) {
        if (level > 0) {

            positions.add((positions.get(triangle.getT0()).add(positions.get(triangle.getT1())).multiplyComponents(0.5f)).normalize());
            positions.add((positions.get(triangle.getT1()).add(positions.get(triangle.getT2())).multiplyComponents(0.5f)).normalize());
            positions.add((positions.get(triangle.getT2()).add(positions.get(triangle.getT0())).multiplyComponents(0.5f)).normalize());

            short i01 = (short) (positions.size() - 3);
            short i12 = (short) (positions.size() - 2);
            short i20 = (short) (positions.size() - 1);

            // Subdivide input triangle into four triangles

            --level;
            subdivide(positions, triangles , new TriangleIndicesShort(triangle.getT0(), i01, i20), level);
            subdivide(positions, triangles , new TriangleIndicesShort(i01, triangle.getT1(), i12), level);
            subdivide(positions, triangles , new TriangleIndicesShort(i01, i12, i20), level);
            subdivide(positions, triangles , new TriangleIndicesShort(i20, i12, triangle.getT2()), level);
        } else {
            triangles.add(triangle);
        }
    }
}
