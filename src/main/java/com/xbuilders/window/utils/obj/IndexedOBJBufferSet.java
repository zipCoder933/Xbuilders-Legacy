/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.window.utils.obj;

public class IndexedOBJBufferSet {

    public float[] vertBuffer, uvBuffer, normalsBuffer;
    public int[] indices;

    // Constructor
    public IndexedOBJBufferSet(IndexedOBJ obj, boolean vec4Position) {
        // Convert lists to arrays if needed
        indices = obj.indices;

        vertBuffer = new float[obj.indexedVertices.size() * (vec4Position ? 4 : 3)];
        normalsBuffer = new float[obj.indexedVertices.size() * 3];
        uvBuffer = new float[obj.indexedVertices.size() * 2];

        int j = 0;
        for (int i = 0; i < obj.indexedVertices.size(); i++) {
            vertBuffer[j] = obj.indexedVertices.get(i).x;
            j++;
            vertBuffer[j] = obj.indexedVertices.get(i).y;
            j++;
            vertBuffer[j] = obj.indexedVertices.get(i).z;
            j++;

            if (vec4Position) {
                vertBuffer[j] = 1.0f;
                j++;
            }
        }

        j = 0;
        for (int i = 0; i < obj.indexedTextureCoords.size(); i++) {
            uvBuffer[j] = obj.indexedTextureCoords.get(i).x;
            j++;
            uvBuffer[j] = -obj.indexedTextureCoords.get(i).y;
            j++;
        }

        if (obj.indexedNormals != null) {
            j = 0;
            for (int i = 0; i < obj.indexedNormals.size(); i++) {
                normalsBuffer[j] = obj.indexedNormals.get(i).x;
                j++;
                normalsBuffer[j] = obj.indexedNormals.get(i).y;
                j++;
                normalsBuffer[j] = obj.indexedNormals.get(i).z;
                j++;
            }
        }

        // Now you have the required indexed arrays for rendering
    }
}
