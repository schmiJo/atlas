package com.atlas.atlas.earth.Source.Core.ExternalMeshData;

import android.content.Context;
import android.util.Log;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Renderer.Mesh.Mesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ObjLoader {


    public static Mesh loadOBJ(int rawID, Context context) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(rawID)));
        String line;

        List<Vector3F> positions = new ArrayList<>();
        List<Vector3F> normals = new ArrayList<>();
        List<Vector2F> texture = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        int[] indicesArray;
        float[] positionArray;
        float[] sortedNormals = null;
        float[] sortedTextures = null;

        try {
            while (true) {
                line = reader.readLine();
                if (line.startsWith("v ")) {
                    String[] currentLine = line.split(" ");
                    Vector3F position = new Vector3F(Float.valueOf(currentLine[1]), Float.valueOf(currentLine[2]), Float.valueOf(currentLine[3]));
                    positions.add(position);
                } else if (line.startsWith("vt ")) {
                    String[] currentLine = line.split(" ");
                    Vector2F textureCoordinate = new Vector2F(Float.valueOf(currentLine[1]), Float.valueOf(currentLine[2]));
                    texture.add(textureCoordinate);
                } else if (line.startsWith("vn ")) {
                    String[] currentLine = line.split(" ");
                    Vector3F normal = new Vector3F(Float.valueOf(currentLine[1]), Float.valueOf(currentLine[2]), Float.valueOf(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    Log.d("debug", "Ready loading OBJ FILE");
                    sortedTextures = new float[positions.size() * 2];
                    sortedNormals = new float[positions.size() * 3];
                    break;
                }
            }

            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                line = line.replace("//", "/");
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                sortVertexData(vertex1, indices, texture, normals, sortedTextures, sortedNormals);
                sortVertexData(vertex2, indices, texture, normals, sortedTextures, sortedNormals);
                sortVertexData(vertex3, indices, texture, normals, sortedTextures, sortedNormals);
                line = reader.readLine();
            }
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        positionArray = new float[positions.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3F position : positions) {
            positionArray[vertexPointer++] = position.x;
            positionArray[vertexPointer++] = position.y;
            positionArray[vertexPointer++] = position.z;
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return new Mesh(indicesArray, positionArray, sortedNormals, sortedTextures);
    }

    private static void sortVertexData(String[] vertexData, List<Integer> indices,
                                       List<Vector2F> textures, List<Vector3F> normals, float[] textureArray,
                                       float[] normalsArray) {

        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        if(vertexData.length == 3) {
            Vector2F currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
            textureArray[currentVertexPointer * 2] = currentTex.x;
            textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
            Vector3F currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
            normalsArray[currentVertexPointer * 3] = currentNorm.x;
            normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
            normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
        } else if(vertexData.length == 2){
            Vector3F currentNorm = normals.get(Integer.parseInt(vertexData[1]) - 1);
            normalsArray[currentVertexPointer * 3] = currentNorm.x;
            normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
            normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
        }

        }

}
