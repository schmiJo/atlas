package com.atlas.atlas.earth.Source.Core.Polygons;


import android.util.Log;

import com.atlas.atlas.earth.Source.Core.BoundingVolumes.ContainmentTests;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class EarClippingOnEllipsoid {


    public static List<TriangleIndicesInt> Triangulate(List<Vector3D> positions) {
        if (positions == null) {
            throw new IllegalArgumentException("positions can't be null");
        }

        if (positions.size() < 3) {
            throw new IllegalArgumentException("At least three positions are required.");
        }


        LinkedList<Vector3D> remainingPositions = new LinkedList<>();

        int index = 0;
        for (Vector3D position : positions) {
            remainingPositions.insertLastLink(new Vector3D(position.x, position.y, position.z), index++);
        }


        List<TriangleIndicesInt> indices = new ArrayList<>((remainingPositions.size() - 2));


        LinkedList.Node previousNode = remainingPositions.firstNode;
        LinkedList.Node node = previousNode.next;
        LinkedList.Node nextNode = node.next;

        int bailCount = remainingPositions.size() * remainingPositions.size();



        while (remainingPositions.size() > 3) {
            Vector3D p0 = (Vector3D)previousNode.value;
            Vector3D p1 = (Vector3D)node.value;
            Vector3D p2 = (Vector3D)nextNode.value;

            if (IsTipConvex(p0, p1, p2)) {
                boolean isEar = true;


                for (LinkedList.Node n = ((nextNode.next != null) ? nextNode.next : remainingPositions.firstNode);
                     n != previousNode;
                     n = ((n.next != null) ? n.next : remainingPositions.firstNode)) {
                    if (ContainmentTests.PointInsideThreeSidedInfinitePyramid((Vector3D)n.value, new Vector3D(0, 0, 0), p0, p1, p2)) {
                        isEar = false;
                        break;
                    }
                }

                if (isEar) {
                    indices.add(new TriangleIndicesInt(previousNode.index, node.index, nextNode.index));
                    Log.d("debug", "Removed Node: "  + node.index);
                    remainingPositions.removeNode(node.index);

                    node = nextNode;
                    nextNode = (nextNode.next != null) ? nextNode.next : remainingPositions.firstNode;
                    continue;
                }
            }

            previousNode = (previousNode.next != null) ? previousNode.next : remainingPositions.firstNode;
            node = (node.next != null) ? node.next : remainingPositions.firstNode;
            nextNode = (nextNode.next != null) ? nextNode.next : remainingPositions.firstNode;

            if (--bailCount == 0) {
                break;
            }
        }

        LinkedList.Node n0 = remainingPositions.firstNode;
        LinkedList.Node n1 = n0.next;
        LinkedList.Node n2 = n1.next;
        indices.add(new TriangleIndicesInt(n0.index, n1.index, n2.index));

        return indices;
    }

    private static boolean IsTipConvex(Vector3D p0, Vector3D p1, Vector3D p2) {
        Vector3D u = p1.substract(p0);
        Vector3D v = p2.substract(p1);

        return u.cross(v).dot(p1) >= 0.0;
    }
}
