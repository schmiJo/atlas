package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;

import java.util.ArrayList;
import java.util.List;


public class Queue {

    private LinkedList<TriangleIndicesInt> linkedList = new LinkedList<>();
    private int index = 0;

    public void enqueue(TriangleIndicesInt triangles){
            linkedList.insertLastLink(triangles, index++);
    }

    public TriangleIndicesInt dequeue(){
        LinkedList.Node removedNode;
        try {
            removedNode = linkedList.firstNode;
            linkedList.firstNode = linkedList.firstNode.next;
            return (TriangleIndicesInt)removedNode.value;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
      return null;
    }

    public TriangleIndicesInt getHead(){
        return (TriangleIndicesInt)linkedList.firstNode.value;
    }

    public TriangleIndicesInt getTail(){
        return (TriangleIndicesInt) linkedList.lastNode.value;
    }

    public int size(){
        return linkedList.lastNode.index-linkedList.firstNode.index;
    }

    public List<TriangleIndicesInt> getAll(){
        LinkedList.Node current = linkedList.firstNode;
        List<TriangleIndicesInt> triangles = new ArrayList<>();

        while (current.next!=null) {
            triangles.add((TriangleIndicesInt) current.value);
            current = current.next;
        }
        return triangles;
    }
}
