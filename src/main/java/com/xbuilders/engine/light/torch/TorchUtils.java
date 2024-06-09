// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.light.torch;

import com.xbuilders.engine.player.blockPipeline.BlockHistory;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.light.SubChunkNode;
import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.engine.world.lightMap.TorchChannelSet;
import java.util.HashSet;
import org.joml.Vector3i;

public class TorchUtils {

    public static boolean isTorchAtThisPosition(final SubChunk chunk, final int x, final int y, final int z) {
        final TorchChannelSet torchChannel = chunk.getLightMap().getTorchlight(x, y, z);
        return torchChannel != null && torchChannel.getChannelWithHighestValue() == 15;
    }

    /**
     *
     * @param chunk the sub-chunk
     * @param x the x coordinate in subChunk space
     * @param y the y coordinate in subChunk space
     * @param z the z coordinate in subChunk space
     * @param lightFallof
     */
    public static void setTorchlight(final SubChunk chunk, final int x, final int y, final int z, byte lightFallof) {
        if (!isTorchAtThisPosition(chunk, x, y, z)) {
            lightFallof = (byte) MathUtils.clamp(lightFallof, 1, 15);
            final ListQueue<SubChunkNode> queue = new ListQueue<SubChunkNode>();
            queue.add(new SubChunkNode(chunk, x, y, z));
            chunk.getLightMap().setTorchlight(x, y, z, lightFallof, (byte) 15);
            continueBFS(queue, lightFallof);
            ShaderLightMap.markAsChanged();
        }
    }

    /**
     *
     * @param chunk the sub-chunk
     * @param x the x coordinate in subChunk space
     * @param y the y coordinate in subChunk space
     * @param z the z coordinate in subChunk space
     * @param lightFallof
     */
    public static void removeTorchlight(final SubChunk chunk, final int x, final int y, final int z, final byte lightFalloff) {
        final TorchChannelSet channels = chunk.getLightMap().getTorchlight(x, y, z);
        if (channels != null && channels.get(lightFalloff) == 15) {
            final ListQueue<SubChunkNode> queue = new ListQueue<SubChunkNode>();
            queue.add(new SubChunkNode(chunk, x, y, z));
            final HashSet<SubChunkNode> edges = eraseSection(queue, lightFalloff);
            queue.clear();
            queue.addNodes(edges);
            continueBFS(queue, lightFalloff);
            ShaderLightMap.markAsChanged();
        }
    }

    public static void update(final SubChunk chunk, final Vector3i blockPos, final BlockHistory hist) {
        if (hist.getOld().isOpaque() && !hist.getNew().isOpaque()) {
            TorchOpaqueToTransparentUtils.update(chunk, blockPos.x, blockPos.y, blockPos.z, hist);
        } else if (!hist.getOld().isOpaque() && hist.getNew().isOpaque()) {
            TorchTransparentToOpaqueUtils.update(chunk, blockPos.x, blockPos.y, blockPos.z, hist);
        }
        ShaderLightMap.markAsChanged();
    }

    public static void continueBFS(final ListQueue<SubChunkNode> queue, final byte falloff) {
        while (queue.containsNodes()) {
            final SubChunkNode node = queue.getAndRemove(0);
            final TorchChannelSet torchChannels = node.chunk.getLightMap().getTorchlight(node.coords.x, node.coords.y, node.coords.z);
            final int lightValue = (torchChannels == null) ? 0 : torchChannels.get(falloff);
            checkNeighborCont(node.chunk, node.coords.x - 1, node.coords.y, node.coords.z, lightValue, queue, falloff);
            checkNeighborCont(node.chunk, node.coords.x + 1, node.coords.y, node.coords.z, lightValue, queue, falloff);
            checkNeighborCont(node.chunk, node.coords.x, node.coords.y, node.coords.z + 1, lightValue, queue, falloff);
            checkNeighborCont(node.chunk, node.coords.x, node.coords.y, node.coords.z - 1, lightValue, queue, falloff);
            checkNeighborCont(node.chunk, node.coords.x, node.coords.y + 1, node.coords.z, lightValue, queue, falloff);
            checkNeighborCont(node.chunk, node.coords.x, node.coords.y - 1, node.coords.z, lightValue, queue, falloff);
        }
    }

    private static void checkNeighborCont(SubChunk chunk, int x, int y, int z, final int lightLevel, final ListQueue<SubChunkNode> queue, final byte lightFallof) {
        if (!chunk.getVoxels().inBounds(x, y, z)) {
            final WCCi wcc = WCCi.getNeighboringWCC(chunk.getPosition(), x, y, z);
            if (!wcc.subChunkExists()) {
                return;
            }
            chunk = wcc.getSubChunk();
            x = wcc.subChunkVoxel.x;
            y = wcc.subChunkVoxel.y;
            z = wcc.subChunkVoxel.z;
        }
        final SubChunkNode node = new SubChunkNode(chunk, x, y, z);
        final TorchChannelSet torchChannel = chunk.getLightMap().getTorchlight(x, y, z);
        final int neighborLevel = (torchChannel == null) ? 0 : torchChannel.get(lightFallof);
        final Block block = ItemList.getBlock(chunk.getVoxels().getBlock(x, y, z));
        if ((!block.isOpaque() || block.isLuminous()) && neighborLevel + (lightFallof + 1) <= lightLevel) {
            chunk.getLightMap().setTorchlight(x, y, z, lightFallof, (byte) (lightLevel - lightFallof));
            chunk.getLightMap().updateSLM(x, y, z, true);
            queue.add(node);
        }
    }

    public static HashSet<SubChunkNode> eraseSection(final ListQueue<SubChunkNode> queue, final byte falloff) {
        final HashSet<SubChunkNode> edgeNodes = new HashSet<SubChunkNode>();
        while (queue.containsNodes()) {
            final SubChunkNode node = queue.getAndRemove(0);
            final TorchChannelSet torchChannels = node.chunk.getLightMap().getTorchlight(node.coords.x, node.coords.y, node.coords.z);
            final int lightValue = (torchChannels == null) ? 0 : torchChannels.get(falloff);
            node.chunk.getLightMap().setTorchlight(node.coords.x, node.coords.y, node.coords.z, falloff, (byte) 0);
            node.chunk.getLightMap().updateSLM(node.coords.x, node.coords.y, node.coords.z, true);
            checkNeighborErase(node.chunk, node.coords.x - 1, node.coords.y, node.coords.z, lightValue, queue, edgeNodes, falloff);
            checkNeighborErase(node.chunk, node.coords.x + 1, node.coords.y, node.coords.z, lightValue, queue, edgeNodes, falloff);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y, node.coords.z + 1, lightValue, queue, edgeNodes, falloff);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y, node.coords.z - 1, lightValue, queue, edgeNodes, falloff);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y + 1, node.coords.z, lightValue, queue, edgeNodes, falloff);
            checkNeighborErase(node.chunk, node.coords.x, node.coords.y - 1, node.coords.z, lightValue, queue, edgeNodes, falloff);
        }
        return edgeNodes;
    }

    private static void checkNeighborErase(SubChunk chunk, int x, int y, int z, final int lightLevel, final ListQueue<SubChunkNode> queue, final HashSet<SubChunkNode> edgeNodes, final byte falloff) {
        if (!chunk.getVoxels().inBounds(x, y, z)) {
            final WCCi wcc = WCCi.getNeighboringWCC(chunk.getPosition(), x, y, z);
            if (!wcc.subChunkExists()) {
                return;
            }
            chunk = wcc.getSubChunk();
            x = wcc.subChunkVoxel.x;
            y = wcc.subChunkVoxel.y;
            z = wcc.subChunkVoxel.z;
        }
        final SubChunkNode node = new SubChunkNode(chunk, x, y, z);
        final TorchChannelSet torchChannels = chunk.getLightMap().getTorchlight(x, y, z);
        final int neighborLevel = (torchChannels == null) ? 0 : torchChannels.get(falloff);
        if (neighborLevel < lightLevel) {
            queue.add(node);
        } else {
            edgeNodes.add(node);
        }
    }
}
