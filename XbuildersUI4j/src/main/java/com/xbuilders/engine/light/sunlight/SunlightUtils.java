// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.light.sunlight;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import org.joml.Vector3i;
import java.util.ArrayList;
import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.light.SubChunkNode;
import com.xbuilders.engine.utils.BFS.ListQueue;
import java.util.HashSet;
import processing.core.KeyCode;
import processing.core.UIFrame;

public class SunlightUtils {

    public static synchronized void updateFromQueue(final ListQueue<SubChunkNode> opaqueToTransparent, final ListQueue<SubChunkNode> transparentToOpaque) {
        if (transparentToOpaque.containsNodes()) {
            HashSet<SubChunkNode> adjacentNodes = new HashSet<>();
            eraseSection(transparentToOpaque, adjacentNodes);
            if (!adjacentNodes.isEmpty()) {
                transparentToOpaque.clear();
                transparentToOpaque.add(opaqueToTransparent);
                transparentToOpaque.addNodes(adjacentNodes);
                propagateSunlight(transparentToOpaque);
            } else if (opaqueToTransparent.containsNodes()) {
                propagateSunlight(opaqueToTransparent);
            }
            ShaderLightMap.markAsChanged();
        } else if (opaqueToTransparent.containsNodes()) {
            propagateSunlight(opaqueToTransparent);
            ShaderLightMap.markAsChanged();
        }
    }

//    public static void update(final Vector3i blockPos, final BlockHistory hist) {
//        final WCCi coords = new WCCi(blockPos);
//        final SubChunk chunk = coords.getSubChunk();
//        final Vector3i voxelCoords = coords.subChunkVoxel;
//        ListQueue<SubChunkNode> queue = new ListQueue<>();
//        
//        if (hist.getOld().isOpaque() && !hist.getNew().isOpaque()) {
//            SunOpaqueToTransparentUtils.addQueueNodesFromPosition(queue, chunk, voxelCoords.x, voxelCoords.y, voxelCoords.z);
//            SunlightUtils.propagateSunlight(queue);
//        } else if (!hist.getOld().isOpaque() && hist.getNew().isOpaque()) {
//            SunTransparentToOpaqueUtils.addQueueNodesFromPosition(queue, chunk, voxelCoords.x, voxelCoords.y, voxelCoords.z);
//
//            final ArrayList<SubChunkNode> adjacentNodes = eraseSection(queue);
//            if (!adjacentNodes.isEmpty()) {
//                queue.clear();
//                queue.add(adjacentNodes);
//                SunlightUtils.propagateSunlight(queue);
//            }
//        }
//    }
    public static synchronized void eraseSection(final ListQueue<SubChunkNode> queue, HashSet<SubChunkNode> totalNodes) {
        long timeStart = System.currentTimeMillis();
        VoxelGame.getGame().showProgressMessage("Erasing Sunlight");
        while (queue.containsNodes() && System.currentTimeMillis() - timeStart < 10000) {
            final SubChunkNode node = queue.getAndRemove(0);
            if (node == null) {
                continue;
            }
            final int lightValue = node.chunk.getLightMap().getSunlight(node.coords.x, node.coords.y, node.coords.z);
            node.chunk.getLightMap().setSunlightAndUpdateSLM(node.coords.x, node.coords.y, node.coords.z, (byte) 0);
            totalNodes.remove(node);
            checkNeighborErase(node.chunk, node.coords.x - 1, node.coords.y, node.coords.z, lightValue, queue, totalNodes);
            checkNeighborErase(node.chunk, node.coords.x + 1, node.coords.y, node.coords.z, lightValue, queue, totalNodes);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y, node.coords.z + 1, lightValue, queue, totalNodes);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y, node.coords.z - 1, lightValue, queue, totalNodes);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y + 1, node.coords.z, lightValue, queue, totalNodes);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y - 1, node.coords.z, lightValue, queue, totalNodes);
        }
        queue.clear();
    }

    private static void checkNeighborErase(SubChunk chunk, int x, int y, int z, final int centerLightLevel,
            final ListQueue<SubChunkNode> queue, final HashSet<SubChunkNode> totalNodes) {
        byte thisLevel;
        if (chunk.getVoxels().inBounds(x, y, z)) {
            thisLevel = chunk.getLightMap().getSunlight(x, y, z);
        } else {
            final WCCi newCoords = WCCi.getNeighboringWCC(chunk.getPosition(), x, y, z);
            chunk = newCoords.getSubChunk();
            x = newCoords.subChunkVoxel.x;
            y = newCoords.subChunkVoxel.y;
            z = newCoords.subChunkVoxel.z;
            if (chunk == null) {
                return;
            }
            thisLevel = chunk.getLightMap().getSunlight(x, y, z);
        }
        if (thisLevel > 0) {
            final SubChunkNode node = new SubChunkNode(chunk, x, y, z);
            if (centerLightLevel >= thisLevel && thisLevel < 15) {
                queue.add(node);
            } else if (thisLevel > 1) {
                totalNodes.add(node);
            }
        }
    }

    public static synchronized void propagateSunlight(final ListQueue<SubChunkNode> queue) {
        int originalSize = queue.size();
        UIFrame applet = VoxelGame.getGame().getParentFrame();
        while (queue.containsNodes()
                && !applet.keysArePressed(KeyCode.SHIFT, KeyCode.X)) {
            final SubChunkNode node = queue.getAndRemove(0);
            if (node == null) {
                continue;
            }
            final int lightValue = node.chunk.getLightMap().getSunlight(node.coords);
            checkNeighbor(node.chunk, node.coords.x - 1, node.coords.y, node.coords.z, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x + 1, node.coords.y, node.coords.z, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y, node.coords.z + 1, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y, node.coords.z - 1, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y + 1, node.coords.z, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y - 1, node.coords.z, lightValue, queue);
            float percentage = 1 - ((float) queue.size() / originalSize);
            VoxelGame.getGame().showProgressMessage("Propagating Sunlight (" + Math.round(percentage * 100) + "%)", percentage);
        }
        queue.clear();
        VoxelGame.getGame().clearProgressMessage();
    }

    private static void checkNeighbor(SubChunk chunk, int x, int y, int z, final int lightLevel, final ListQueue<SubChunkNode> queue) {
        Block neigborBlock;
        if (chunk.getVoxels().inBounds(x, y, z)) {
            neigborBlock = ItemList.getBlock(chunk.getVoxels().getBlock(x, y, z));
        } else {
            final Vector3i neighboringSubChunk = WCCi.getNeighboringSubChunk(chunk.getPosition(), x, y, z);
            final Vector3i neighboringSubChunkBlockCoords = WCCi.normalizeToChunkSpace(x, y, z);
            chunk = VoxelGame.getWorld().getSubChunk(neighboringSubChunk);
            x = neighboringSubChunkBlockCoords.x;
            y = neighboringSubChunkBlockCoords.y;
            z = neighboringSubChunkBlockCoords.z;
            if (chunk == null) {
                return;
            }
            neigborBlock = ItemList.getBlock(chunk.getVoxels().getBlock(x, y, z));
        }
        if (!neigborBlock.isOpaque()) {
            final int neighborLevel = chunk.getLightMap().getSunlight(x, y, z);
            if (neighborLevel + 2 <= lightLevel) {
                chunk.getLightMap().setSunlightAndUpdateSLM(x, y, z, (byte) (lightLevel - 1));
                queue.add(new SubChunkNode(chunk, x, y, z));
            }
        }
    }

}
