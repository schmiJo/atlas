package com.atlas.atlas.earth.Source.Core.Polygons;

import android.util.Log;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Edge;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Queue;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TriangleMeshSubdivision {

    public static TriangleMeshSubdivisionResult Compute(List<Vector3D> positions, List<TriangleIndicesInt> indices, double granularity)
    {
        if (positions == null)
        {
            throw new IllegalArgumentException("Positions can't be null");
        }


        if (indices == null || indices.size() < 1)
        {
            throw new IllegalArgumentException("At least three indices are required.");
        }



        if (granularity <= 0.0)
        {
            throw new IllegalArgumentException("Granularity must be greater than zero.");
        }

        //
        // Use two queues:  one for triangles that need (or might need) to be
        // subdivided and other for triangles that are fully subdivided.
        //
        Queue triangles = new Queue();
        Queue done = new Queue();

        for (TriangleIndicesInt triangle : indices)
        {
            triangles.enqueue(triangle);
        }
//Triangles are Accurrate

        //
        // Used to make sure shared edges are not split more than once.
        //
        Map<Edge, Integer> edges = new HashMap<>();

        //
        // Subdivide triangles until we run out
        //
        int couter = 0;
        while (triangles.size() != 0)
        {
            TriangleIndicesInt triangle = triangles.dequeue();
            couter++;
            //Log.d("debug", "while loop called " + couter + " times. Size is: " + triangles.size());

            Vector3D v0 = positions.get(triangle.getT0());
            Vector3D v1 = positions.get(triangle.getT1());
            Vector3D v2 = positions.get(triangle.getT2());

            double g0 = v0.angleBetween(v1);
            double g1 = v1.angleBetween(v2);
            double g2 = v2.angleBetween(v0);

            double max = Math.max(g0, Math.max(g1, g2));
//NonAccurate by 23
            if (max > granularity)
            {
                if (g0 == max)
                {
                    Edge edge = new Edge(Math.min(triangle.getT0(), triangle.getT1()), Math.max(triangle.getT0(), triangle.getT1()));

                    Integer i = edges.get(edge);
                    if (i == null)
                    {
                        positions.add((v0.add(v1)).multiplyComponents(0.5));
                        i = positions.size() - 1;
                        edges.put(edge, i);
                    }else{
                        Log.d("debug", "Edge is contained in Hashmap");
                    }

                    triangles.enqueue(new TriangleIndicesInt(triangle.getT0(), i, triangle.getT2()));
                    triangles.enqueue(new TriangleIndicesInt(i, triangle.getT1(), triangle.getT2()));
                }
                else if (g1 == max)
                {
                    Edge edge = new Edge(Math.min(triangle.getT1(), triangle.getT2()), Math.max(triangle.getT1(), triangle.getT2()));
                    Integer i = edges.get(edge);
                    if (i == null)
                    {
                        positions.add((v1.add(v2)).multiplyComponents(0.5));
                        i = positions.size() - 1;
                        edges.put(edge, i);
                    }else{
                        Log.d("debug", "Edge is contained in Hashmap");
                    }

                    triangles.enqueue(new TriangleIndicesInt(triangle.getT1(), i, triangle.getT0()));
                    triangles.enqueue(new TriangleIndicesInt(i, triangle.getT2(), triangle.getT0()));
                }
                else if (g2 == max)
                {
                    Edge edge = new Edge(Math.min(triangle.getT2(), triangle.getT0()), Math.max(triangle.getT2(), triangle.getT0()));
                    Integer i = edges.get(edge);
                    if (i == null)
                    {
                        positions.add((v2.add(v0)).multiplyComponents(0.5));
                        i = positions.size() - 1;
                        edges.put(edge, i);
                    }else{
                        Log.d("debug", "Edge is contained in Hashmap");
                    }

                    triangles.enqueue(new TriangleIndicesInt(triangle.getT2(), i, triangle.getT1()));
                    triangles.enqueue(new TriangleIndicesInt(i, triangle.getT0(), triangle.getT1()));
                }
            }
            else
            {
                done.enqueue(triangle);
            }
        }
        return new TriangleMeshSubdivisionResult(positions, done.getAll());
    }
}
