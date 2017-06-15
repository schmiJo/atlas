package com.atlas.atlas.earth.Source.Core.CustomDataTypes;

import android.util.Log;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesInt;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;

import java.util.ArrayList;
import java.util.List;


public class LinkedList<T> {


    public Node firstNode;
    public Node lastNode;
    private int size;


    public LinkedList() {
        firstNode = null;
    }


    // Returns true if LinkList is empty
    public boolean isEmpty() {

        return (firstNode == null);
    }


    @SuppressWarnings("unchecked")
    public <B extends T> void insertFirstLink(B value, int index) {
        if (isEmpty()) {
            lastNode = firstNode;
        }
        size++;
        Node newLink = new Node(value, index);

        // Connects the firstNode field to the new Node
        newLink.next = firstNode;

        firstNode = newLink;

    }

    @SuppressWarnings("unchecked")
    public <B extends T> void insertLastLink(B value, int index) {
        size++;
        Node node = new Node(value, index);
        if (isEmpty()) {
            firstNode = node;
            lastNode = node;
        } else {
            lastNode.next = node;
            lastNode = node;
        }
    }


    public Node removeFirst() {

        Node linkReference = firstNode;

        if (!isEmpty()) {

            // Removes the Node from the List
            firstNode = firstNode.next;
            size--;
        } else {

            Log.d("debug", "Empty LinkedList");

        }


        return linkReference;


    }

    public Node find(int index) {


        Node theNode = firstNode;


        if (!isEmpty()) {

            while (theNode.index != index) {

                // Checks if at the end of the LinkedList
                if (theNode.next == null) {

                    // Got to the end of the Links in LinkedList
                    // without finding a match

                    return null;

                } else {

                    // Found a matching Node in the LinkedList
                    theNode = theNode.next;
                }
            }
        } else {
            System.out.println("Empty LinkedList");
        }


        return theNode;
    }


    public Node removeNode(int index) {
        if (firstNode == null) {
            return null;
        }
        Node currentNode = firstNode;
        Node previousNode = firstNode;

        // Keep searching as long as a match isn't made
        while (currentNode.index != index) {


            // Check if at the last Node in the LinkedList
            if (currentNode.next == null) {

                // bookName not found so leave the method
                return null;

            } else {

                // We checked here so let's look in the
                // next Node on the list
                previousNode = currentNode;
                currentNode = currentNode.next;

            }
        }

        if (currentNode == firstNode) {

            // If you are here that means there was a match
            // in the reference stored in firstNode in the
            // LinkedList so just assign next to firstNode

            firstNode = firstNode.next;
            size--;

        } else {

            // If you are here there was a match in a Node other
            // than the firstNode. Assign the value of next for
            // the Node you want to delete to the Node that's
            // next previously pointed to the reference to remove

            System.out.println("FOUND A MATCH");
            System.out.println("currentNode: " + currentNode);
            System.out.println("firstNode: " + firstNode);
            previousNode.next = currentNode.next;
            size--;
        }

        return currentNode;
    }


    @SuppressWarnings("unchecked")
    public <B extends T> List<B> asArrayList() {

        Node currentNode = firstNode;
        List<B> arrayList = new ArrayList<>(size);
        while (currentNode != null) {
            arrayList.add((B) currentNode.value);
            currentNode = currentNode.next;
        }

        return arrayList;
    }

    public int size() {
        return size;
    }


    public class Node<B extends T> {

        // Set to public so getters & setters aren't needed
        public B value;
        public int index;


        // Reference to next link made in the LinkList
        // Holds the reference to the Node that was created before it
        // Set to null until it is connected to other links

        public Node next;


        public Node(B value, int index) {
            this.value = value;
            this.index = index;
        }


        public String toString() {
            if (value instanceof Vector3D) {
                return "Position: " + value.toString() + ", with index: " + index;
            }
            if (value instanceof TriangleIndicesInt) {
                return "Indices: " + value.toString() + ", with index: " + index;
            }
            return "Unsolved Datatype";
        }
    }
}
