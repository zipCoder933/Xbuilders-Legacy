// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.light.sunlight;

import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.engine.light.SubChunkNode;
import static com.xbuilders.engine.light.sunlight.InitialSunlightUtils.getSunlight;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.world.chunk.SubChunk;

public class SunInitialNodeUtils {

    protected static SubChunkNode getBrightestNeighboringNode(final SubChunk c, final int x, final int y, final int z) {
        int brightestSunlight = 0;
        final int lightVal = 0;
        SubChunkNode brightestNode = null;
        WCCi coords = WCCi.getNeighboringWCC(c.position, x + 1, y, z);
        SubChunk coordsChunk = coords.getSubChunk();
        if (coordsChunk != null && coordsChunk.lightMap.getSunlight(coords.subChunkVoxel) > brightestSunlight) {
            brightestSunlight = lightVal;
            brightestNode = new SubChunkNode(coordsChunk, coords.subChunkVoxel);
        }
        coords = WCCi.getNeighboringWCC(c.position, x - 1, y, z);
        coordsChunk = coords.getSubChunk();
        if (coordsChunk != null && coordsChunk.lightMap.getSunlight(coords.subChunkVoxel) > brightestSunlight) {
            brightestSunlight = lightVal;
            brightestNode = new SubChunkNode(coordsChunk, coords.subChunkVoxel);
        }
        coords = WCCi.getNeighboringWCC(c.position, x, y + 1, z);
        coordsChunk = coords.getSubChunk();
        if (coordsChunk != null && coordsChunk.lightMap.getSunlight(coords.subChunkVoxel) > brightestSunlight) {
            brightestSunlight = lightVal;
            brightestNode = new SubChunkNode(coordsChunk, coords.subChunkVoxel);
        }
        coords = WCCi.getNeighboringWCC(c.position, x, y - 1, z);
        coordsChunk = coords.getSubChunk();
        if (coordsChunk != null && coordsChunk.lightMap.getSunlight(coords.subChunkVoxel) > brightestSunlight) {
            brightestSunlight = lightVal;
            brightestNode = new SubChunkNode(coordsChunk, coords.subChunkVoxel);
        }
        coords = WCCi.getNeighboringWCC(c.position, x, y, z + 1);
        coordsChunk = coords.getSubChunk();
        if (coordsChunk != null && coordsChunk.lightMap.getSunlight(coords.subChunkVoxel) > brightestSunlight) {
            brightestSunlight = lightVal;
            brightestNode = new SubChunkNode(coordsChunk, coords.subChunkVoxel);
        }
        coords = WCCi.getNeighboringWCC(c.position, x, y, z - 1);
        coordsChunk = coords.getSubChunk();
        if (coordsChunk != null && coordsChunk.lightMap.getSunlight(coords.subChunkVoxel) > brightestSunlight) {
            brightestSunlight = lightVal;
            brightestNode = new SubChunkNode(coordsChunk, coords.subChunkVoxel);
        }
        return brightestNode;
    }

    public static void addOpaqueToTransNodes(final ListQueue<SubChunkNode> queue, SubChunk subChunk, final int x, final int y, final int z) {
        final int subChunkY = subChunk.position.y * 16;
        final int aboveLight = getSunlight(subChunk.getParentChunk(), x, subChunkY + (y - 1), z);
        if (aboveLight == 15) {
            int subChunkIdx = subChunk.position.y;
//            boolean firstTime = true;
            int y2 = y;
            while (true) {
                if (y2 >= 16) {
                    y2 = 0;
                    ++subChunkIdx;
                    if (subChunkIdx >= subChunk.getParentChunk().getSubChunks().length) {
                        break;
                    }
                    subChunk = subChunk.getParentChunk().getSubChunks()[subChunkIdx];
                }
                if (ItemList.getBlock(subChunk.data.getBlock(x, y2, z)).isOpaque()) {
                    break;
                }
                subChunk.lightMap.setSunlightAndUpdateSLM(x, y2, z, (byte) 15);
//                if (firstTime || y2 == 0) {//This speeds up the propagation and saves memory, but also causes lighting artifacts
                queue.add(new SubChunkNode(subChunk, x, y2, z));
//                    firstTime = false;
//                }
                ++y2;
            }
        } else {
            final SubChunkNode node = getBrightestNeighboringNode(subChunk, x, y, z);
            if (node != null) {
                queue.add(node);
            }
        }
    }

    public static void addTransToOpaqueNodes(final ListQueue<SubChunkNode> queue, SubChunk subChunk, final int x, final int y, final int z) {
        subChunk.lightMap.setSunlightAndUpdateSLM(x, y, z, (byte) 14);
        queue.add(new SubChunkNode(subChunk, x, y, z));
        int subChunkIdx = subChunk.position.y;
        int originalIndx = subChunkIdx;
        int y2 = y + 1;
        boolean firstTime = true;
        while (subChunkIdx - originalIndx < 3) {
            if (y2 >= 16) {
                y2 = 0;
                ++subChunkIdx;
                if (subChunkIdx >= subChunk.getParentChunk().getSubChunks().length) {
                    break;
                }

                subChunk = subChunk.getParentChunk().getSubChunks()[subChunkIdx];
            }
            if (ItemList.getBlock(subChunk.data.getBlock(x, y2, z)).isOpaque()) {
                break;
            }
            if (firstTime) { //Might save memory
                queue.add(new SubChunkNode(subChunk, x, y2, z));
                firstTime = false;
            }
            subChunk.lightMap.setSunlightAndUpdateSLM(x, y2, z, (byte) 14);
            ++y2;
        }
    }
}
