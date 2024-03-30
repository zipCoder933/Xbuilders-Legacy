// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.rendering.worldLightMap;

import com.xbuilders.engine.utils.math.MathUtils;
import java.util.Arrays;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.chunk.SubChunk;

public class SLMUpdatingUtils
{
    static int lastStartX;
    static int lastStartZ;
    
    public static boolean wouldBeWithinWLM(final float playerX, final float playerZ, final ChunkCoords chunkCoords) {
        int minX = (int)playerX - ShaderLightMap.Xdim / 2 + 8;
        int minZ = (int)playerZ - ShaderLightMap.Zdim / 2 + 8;
        minX -= MathUtils.positiveMod(minX , SubChunk.WIDTH);
        minZ -= MathUtils.positiveMod(minZ , SubChunk.WIDTH);
        final int x2 = chunkCoords.x * SubChunk.WIDTH - minX;
        final int z2 = chunkCoords.z * SubChunk.WIDTH - minZ;
        return x2 < ShaderLightMap.Xdim && x2 >= 0 && z2 < ShaderLightMap.Zdim && z2 >= 0;
    }
    
    public static void shiftLightmap(final int xShift, final int zShift) {
        if (xShift == 0 && zShift == 0) {
            return;
        }
        final int[] newPixels = new int[ShaderLightMap.getImagePixels().length];
        Arrays.fill(newPixels, -2);
        for (int x = 0; x < ShaderLightMap.Xdim; ++x) {
            for (int y = 0; y < ShaderLightMap.Ydim; ++y) {
                for (int z = 0; z < ShaderLightMap.Zdim; ++z) {
                    final int fromIdx = ShaderLightMap.coordsToIndex(x, y, z);
                    final int toIdx = ShaderLightMap.coordsToIndex(x + xShift, y, z + zShift);
                    if (ShaderLightMap.lightmapCoordsInBounds(x + xShift, y, z + zShift)) {
                        newPixels[toIdx] = ShaderLightMap.getImagePixels()[fromIdx];
                    }
                }
            }
        }
        ShaderLightMap.setImagePixels(newPixels);
        ShaderLightMap.minX -= xShift;
        ShaderLightMap.minZ -= zShift;
    }
    
    public static void setStartPosition(final int Xstart, final int Zstart) {
        final int minX = ShaderLightMap.minX;
        final int minZ = ShaderLightMap.minZ;
        final int shiftX = minX - Xstart;
        final int shiftZ = minZ - Zstart;
        shiftLightmap(shiftX, shiftZ);
    }
    
    public static void setStartPositionAroundPlayer(final float playerX, final float playerZ) {
        final int Xdim = ShaderLightMap.Xdim / 2;
        final int Zdim = ShaderLightMap.Zdim / 2;
        int startX = (int)playerX - Xdim + 8;
        int startZ = (int)playerZ - Zdim + 8;
        startX -= MathUtils.positiveMod(startX , SubChunk.WIDTH);
        startZ -= MathUtils.positiveMod(startZ , SubChunk.WIDTH);
        if (startX != SLMUpdatingUtils.lastStartX || startZ != SLMUpdatingUtils.lastStartZ) {
            setStartPosition(startX, startZ);
            SLMUpdatingUtils.lastStartX = startX;
            SLMUpdatingUtils.lastStartZ = startZ;
        }
    }
}
