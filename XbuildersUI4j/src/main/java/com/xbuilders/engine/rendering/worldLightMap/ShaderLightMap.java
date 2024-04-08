// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.rendering.worldLightMap;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import java.util.Arrays;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import org.joml.Vector3i;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.utils.math.MathUtils;
import processing.core.PImage;

public class ShaderLightMap {

    private static volatile PImage image;
    public static boolean hasChanged;
    public static int minX;
    public static int minZ;
    public static int Xdim;
    public static int Ydim;
    public static int Zdim;
    public static final int EMPTY_VALUE = -2;
    public static boolean initializing;
    public static final Object lightmapInitializationLock;

    public static int[] getImagePixels() {
        return ShaderLightMap.image.pixels;
    }

    public static void setImagePixels(final int[] arr2) {
        ShaderLightMap.image.pixels = arr2;
    }

    public static void setImagePixelValue(final int index, final int value) {
        ShaderLightMap.image.pixels[index] = value;
    }

    public static void markAsChanged() {
        ShaderLightMap.hasChanged = true;
    }

    public static synchronized void sendLightmapToShader() {
        if (ShaderLightMap.image != null && !ShaderLightMap.initializing && ShaderLightMap.hasChanged) {
            ShaderLightMap.image.updatePixels();
            ShaderHandler.blockShader.set("lightsStart", ShaderLightMap.minX, 0, ShaderLightMap.minZ);
            ShaderHandler.blockShader.set("lights", ShaderLightMap.image);
            ShaderLightMap.hasChanged = false;
        }
    }

    public static boolean lightmapCoordsInBounds(final Vector3i lightmapCoords) {
        return lightmapCoordsInBounds(lightmapCoords.x, lightmapCoords.y, lightmapCoords.z);
    }

    public static boolean lightmapCoordsInBounds(final int x2, final int y2, final int z2) {
        return x2 >= 0 && y2 >= 0 && z2 >= 0 && x2 < ShaderLightMap.Xdim && y2 < ShaderLightMap.Ydim && z2 < ShaderLightMap.Zdim;
    }

    public static boolean inBounds(final int x, final int y, final int z) {
        final int x2 = x - ShaderLightMap.minX;
        final int z2 = z - ShaderLightMap.minZ;
        return lightmapCoordsInBounds(x2, y, z2);
    }

    public static boolean inBounds(final ChunkCoords chunk, final Vector3i chunkCoords) {
        final int x2 = chunk.x * SubChunk.WIDTH + chunkCoords.x - ShaderLightMap.minX;
        final int z2 = chunk.z * SubChunk.WIDTH + chunkCoords.z - ShaderLightMap.minZ;
        return lightmapCoordsInBounds(x2, chunkCoords.y, z2);
    }

    public static boolean inBounds(final ChunkCoords chunkCoords) {
        final int x2 = chunkCoords.x * SubChunk.WIDTH - ShaderLightMap.minX;
        final int z2 = chunkCoords.z * SubChunk.WIDTH - ShaderLightMap.minZ;
        return x2 < ShaderLightMap.Xdim && x2 >= 0 && z2 < ShaderLightMap.Zdim && z2 >= 0;
    }

    public static void updateLightmapVoxel(final SubChunk c, final int x, final int y, final int z) {
        final int worldPosX = c.getPosition().x * SubChunk.WIDTH + x;
        final int worldPosY = c.getPosition().y * SubChunk.WIDTH + y;
        final int worldPosZ = c.getPosition().z * SubChunk.WIDTH + z;
        final int x2 = worldPosX - ShaderLightMap.minX;
        final int y2 = worldPosY;
        final int z2 = worldPosZ - ShaderLightMap.minZ;
        if (!inBounds(worldPosX, worldPosY, worldPosZ)) {
            return;
        }
        final int idx = coordsToIndex(x2, y2, z2);
        ShaderLightMap.image.loadPixels();
        ShaderLightMap.image.pixels[idx] = getLightmapValue(c, x, y, z);
        ShaderLightMap.image.updatePixels();
    }

    public static Vector3i lightmapCoordsToWorldCoords(final int x, final int y, final int z) {
        final int wx = ShaderLightMap.minX + x;
        final int wz = ShaderLightMap.minZ + z;
        final Vector3i worldPos = new Vector3i(wx, y, wz);
        return worldPos;
    }

    public static Vector3i lightmapCoordsToWorldCoords(final Vector3i vec) {
        return lightmapCoordsToWorldCoords(vec.x, vec.y, vec.z);
    }

    public static Vector3i worldCoordsToLightmapCoords(final int x, final int y, final int z) {
        final int x2 = x - ShaderLightMap.minX;
        final int z2 = z - ShaderLightMap.minZ;
        final Vector3i lightmapCoords = new Vector3i(x2, y, z2);
        return lightmapCoords;
    }

    public static void worldCoordsToLightmapCoords(Vector3i lightmapCoords, final int x, final int y, final int z) {
        final int x2 = x - ShaderLightMap.minX;
        final int z2 = z - ShaderLightMap.minZ;
        lightmapCoords.set(x2, y, z2);
    }

    public static int getChunkRadius(final PointerHandler ph) {
        return (int) (ph.getSettingsFile().chunkRadius * ph.getSettingsFile().SLM_RadiusMultiplier);
    }

    public static String boundariesToString() {
        return "min: " + minX + ", " + minZ
                + "  max: " + (Xdim + minX) + ", " + Ydim + ", " + (Zdim + minZ);
    }

    public static void initializeLightmap(final PointerHandler ph, final Class user) {
        UserControlledPlayer userControlledPlayer1 = ph.getPlayer();
        final float playerX = userControlledPlayer1.worldPos.x;
        UserControlledPlayer userControlledPlayer = ph.getPlayer();
        final float playerZ = userControlledPlayer.worldPos.z;

        synchronized (ShaderLightMap.lightmapInitializationLock) {
            ShaderLightMap.initializing = true;
            final int diameter = getChunkRadius(ph) * 2;
            ShaderLightMap.Ydim = 257;
            ShaderLightMap.Xdim = diameter;
            ShaderLightMap.Zdim = diameter;
            ShaderLightMap.Xdim -= MathUtils.positiveMod(ShaderLightMap.Xdim , SubChunk.WIDTH);
            ShaderLightMap.Zdim -= MathUtils.positiveMod(ShaderLightMap.Zdim , SubChunk.WIDTH);
            ShaderLightMap.minX = (int) playerX - ShaderLightMap.Xdim / 2 + 8;
            ShaderLightMap.minZ = (int) playerZ - ShaderLightMap.Zdim / 2 + 8;
            ShaderLightMap.minX -= MathUtils.positiveMod(ShaderLightMap.minX , SubChunk.WIDTH);
            ShaderLightMap.minZ -= MathUtils.positiveMod(ShaderLightMap.minZ , SubChunk.WIDTH);
            final int imageSize = (int) Math.sqrt(((ShaderLightMap.Xdim - 1) * ShaderLightMap.Ydim + (ShaderLightMap.Ydim - 1)) * ShaderLightMap.Xdim + (ShaderLightMap.Zdim - 1)) + 20;
            (ShaderLightMap.image = new PImage(imageSize, imageSize, 2)).loadPixels();
            Arrays.fill(ShaderLightMap.image.pixels, -2);
            for (int x = 0; x < ShaderLightMap.Xdim; ++x) {
                for (int y = 0; y < ShaderLightMap.Ydim; ++y) {
                    for (int z = 0; z < ShaderLightMap.Zdim; ++z) {
                        final int idx = (x * ShaderLightMap.Ydim + y) * ShaderLightMap.Xdim + z;
                        final int x2 = ShaderLightMap.minX + x;
                        final int z2 = ShaderLightMap.minZ + z;
                        final Vector3i worldPos = new Vector3i(x2, y, z2);
                        if (VoxelGame.getWorld().inBounds(worldPos)) {
                            final Vector3i chunkCoords = WCCi.getSubChunkAtWorldPos(worldPos.x, worldPos.y, worldPos.z);
                            final SubChunk subChunk = VoxelGame.getWorld().getSubChunk(chunkCoords);
                            if (subChunk != null && subChunk.getLightMap().initialized) {
                                subChunk.getLightMap().markAsPastedIntoSLM();
                                ShaderLightMap.image.pixels[idx] = getLightmapValue(subChunk,
                                        MathUtils.positiveMod(x2, SubChunk.WIDTH),
                                        MathUtils.positiveMod(y, SubChunk.WIDTH),
                                        MathUtils.positiveMod(z2, SubChunk.WIDTH));
                            }
                        }
                    }
                }
            }
            ShaderLightMap.image.updatePixels();
            ShaderHandler.blockShader.set("lightsStart", ShaderLightMap.minX, 0, ShaderLightMap.minZ);
            ShaderHandler.blockShader.set("lightsShape", ShaderLightMap.Xdim, ShaderLightMap.Ydim, ShaderLightMap.Zdim);
            ShaderHandler.blockShader.set("lights", ShaderLightMap.image);
            ShaderLightMap.initializing = false;
        }
    }

    public static int coordsToIndex(final int x, final int y, final int z) {
        return (x * ShaderLightMap.Ydim + y) * ShaderLightMap.Xdim + z;
    }

    public static int coordsToIndex(final Vector3i vec) {
        return coordsToIndex(vec.x, vec.y, vec.z);
    }

    public static int getLightmapValue(final SubChunk c, final int coordsX, final int coordsY, final int coordsZ) {
        final Block block = ItemList.getBlock(c.getVoxels().getBlock(coordsX, coordsY, coordsZ));
        if (block.isOpaque() && !block.isLuminous()) {
            return -1;
        }
        return c.getLightMap().getSunAndTorchAsPackedByte(coordsX, coordsY, coordsZ);
    }

    static {
        ShaderLightMap.image = null;
        ShaderLightMap.hasChanged = false;
        ShaderLightMap.initializing = false;
        lightmapInitializationLock = new Object();
    }
}
