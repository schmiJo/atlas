package com.atlas.atlas.earth.Source.Core.Geometry.cartesianCS.Tessellation;

import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Renderer.Mesh.Mesh;
import com.atlas.atlas.earth.Source.Renderer.Mesh.VertexAttributeCollection;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jonas on 4/19/2017.
 */

public class BoxTessellator {

    public static Mesh compute(Vector3F length)
    {
        if (length.x < 0 || length.y < 0 || length.z < 0)
        {
            throw new IllegalArgumentException("Length has to be bigger than zero");
        }

        Mesh mesh = new Mesh();
       mesh.setFrontFaceWindingOrder(ByteFlags.COUNTERCLOCKWISE);



        //
        // 8 corner points
        //

        Vector3F corner =  length.multiplyComponents(0.5f);
        List<Vector3F> positions = new ArrayList<>();
        List<TriangleIndicesShort> indices = new ArrayList<>(36);

        positions.add(new Vector3F(-corner.x, -corner.y, -corner.z));
        positions.add(new Vector3F( corner.x, -corner.y, -corner.z));
        positions.add(new Vector3F( corner.x,  corner.y, -corner.z));
        positions.add(new Vector3F(-corner.x,  corner.y, -corner.z));
        positions.add(new Vector3F(-corner.x, -corner.y,  corner.z));
        positions.add(new Vector3F( corner.x, -corner.y,  corner.z));
        positions.add(new Vector3F( corner.x,  corner.y,  corner.z));
        positions.add(new Vector3F(-corner.x,  corner.y,  corner.z));

        //
        // 6 faces, 2 triangles each
        //
        indices.add(new TriangleIndicesShort((short) 4,(short) 5,(short) 6));    // Top: plane z = corner.Z
        indices.add(new TriangleIndicesShort((short) 4,(short) 6,(short) 7));
        indices.add(new TriangleIndicesShort((short) 1,(short) 0,(short) 3));    // Bottom: plane z = -corner.Z
        indices.add(new TriangleIndicesShort((short) 1,(short) 3,(short) 2));
        indices.add(new TriangleIndicesShort((short) 1,(short) 6,(short) 5));    // Side: plane x = corner.X
        indices.add(new TriangleIndicesShort((short) 1,(short) 2,(short) 6));
        indices.add(new TriangleIndicesShort((short) 2,(short) 3,(short) 7));    // Side: plane y = corner.Y
        indices.add(new TriangleIndicesShort((short) 2,(short) 7,(short) 6));
        indices.add(new TriangleIndicesShort((short) 3,(short) 0,(short) 4));    // Side: plane x = -corner.X
        indices.add(new TriangleIndicesShort((short) 3,(short) 4,(short) 7));
        indices.add(new TriangleIndicesShort((short) 0,(short) 1,(short) 5));    // Side: plane y = -corner.Y
        indices.add(new TriangleIndicesShort((short) 0,(short) 5,(short) 4));

        mesh.addVertexAttributes(new VertexAttributeCollection(positions, null, null));
        mesh.addTriangles(indices);
        return mesh;
    }
}

