// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.clouds;

import processing.core.PShape;
import processing.core.PVector;

public class CloudBlockRenderer
{
    static PVector[] vertices;
    static int[] blockIndices;
    
    public static void constructBlock(final PShape shape, final boolean negativeX, final boolean positiveX, final boolean negativeY, final boolean positiveY, final boolean negativeZ, final boolean positiveZ, final int x, final int y, final int z) {
        if (negativeX) {
            constructLeft(shape, x, y, z);
        }
        if (positiveX) {
            constructRight(shape, x, y, z);
        }
        if (negativeY) {
            constructTop(shape, x, y, z);
        }
        if (positiveY) {
            constructBottom(shape, x, y, z);
        }
        if (negativeZ) {
            constructBack(shape, x, y, z);
        }
        if (positiveZ) {
            constructFront(shape, x, y, z);
        }
    }
    
    private static void constructBack(final PShape shape, final int x, final int y, final int z) {
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[0]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[0]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[0]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[1]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[1]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[1]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[2]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[2]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[2]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[3]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[3]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[3]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[4]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[4]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[4]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[5]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[5]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[5]].z + z);
    }
    
    private static void constructRight(final PShape shape, final int x, final int y, final int z) {
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[6]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[6]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[6]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[7]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[7]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[7]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[8]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[8]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[8]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[9]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[9]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[9]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[10]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[10]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[10]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[11]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[11]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[11]].z + z);
    }
    
    private static void constructFront(final PShape shape, final int x, final int y, final int z) {
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[12]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[12]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[12]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[13]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[13]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[13]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[14]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[14]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[14]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[15]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[15]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[15]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[16]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[16]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[16]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[17]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[17]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[17]].z + z);
    }
    
    private static void constructLeft(final PShape shape, final int x, final int y, final int z) {
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[18]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[18]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[18]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[19]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[19]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[19]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[20]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[20]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[20]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[21]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[21]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[21]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[22]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[22]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[22]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[23]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[23]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[23]].z + z);
    }
    
    private static void constructTop(final PShape shape, final int x, final int y, final int z) {
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[24]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[24]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[24]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[25]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[25]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[25]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[26]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[26]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[26]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[27]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[27]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[27]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[28]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[28]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[28]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[29]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[29]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[29]].z + z);
    }
    
    private static void constructBottom(final PShape shape, final int x, final int y, final int z) {
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[30]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[30]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[30]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[31]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[31]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[31]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[32]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[32]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[32]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[33]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[33]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[33]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[34]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[34]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[34]].z + z);
        shape.vertex(CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[35]].x + x, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[35]].y + y, CloudBlockRenderer.vertices[CloudBlockRenderer.blockIndices[35]].z + z);
    }
    
    static {
        CloudBlockRenderer.vertices = new PVector[] { new PVector(0.0f, 0.0f, 0.0f), new PVector(1.0f, 0.0f, 0.0f), new PVector(1.0f, 0.0f, 1.0f), new PVector(0.0f, 0.0f, 1.0f), new PVector(0.0f, 1.0f, 0.0f), new PVector(1.0f, 1.0f, 0.0f), new PVector(1.0f, 1.0f, 1.0f), new PVector(0.0f, 1.0f, 1.0f) };
        CloudBlockRenderer.blockIndices = new int[] { 0, 1, 5, 0, 5, 4, 1, 2, 6, 1, 6, 5, 2, 3, 7, 2, 7, 6, 3, 0, 4, 3, 4, 7, 2, 1, 0, 3, 2, 0, 4, 5, 6, 4, 6, 7 };
    }
}
