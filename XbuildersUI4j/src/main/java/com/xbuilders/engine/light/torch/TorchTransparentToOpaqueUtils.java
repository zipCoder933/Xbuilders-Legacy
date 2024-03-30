// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.light.torch;

import com.xbuilders.engine.world.chunk.wcc.WCCi;
import java.util.ArrayList;

import com.xbuilders.engine.world.chunk.lightMap.TorchChannelSet;
import com.xbuilders.engine.light.SubChunkNode;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.player.blockPipeline.BlockHistory;
import com.xbuilders.engine.world.chunk.SubChunk;

class TorchTransparentToOpaqueUtils
{
    static void update(final SubChunk chunk, final int x, final int y, final int z, final BlockHistory hist) {
        final TorchChannelSet channelSet = chunk.getLightMap().getTorchlight(x, y, z);
        if (channelSet != null) {
            for (final byte falloff : channelSet.list.keySet()) {
                final ListQueue<SubChunkNode> queue = new ListQueue<SubChunkNode>();
                queue.add(new SubChunkNode(chunk, x, y, z));
                TorchUtils.eraseSection(queue, falloff);
                queue.addNodes(findNonzeroNeighbors(chunk, x, y, z, falloff));
                TorchUtils.continueBFS(queue, falloff);
            }
        }
    }
    
    private static ArrayList<SubChunkNode> findNonzeroNeighbors(final SubChunk chunk, final int x, final int y, final int z, final byte channel) {
        final ArrayList<SubChunkNode> nodes = new ArrayList<SubChunkNode>();
        for (int x2 = -1; x2 < 2; ++x2) {
            for (int y2 = -1; y2 < 2; ++y2) {
                for (int z2 = -1; z2 < 2; ++z2) {
                    final SubChunkNode node = checkNeigbor(chunk, x + x2, y + y2, z + z2, channel);
                    if (node != null) {
                        nodes.add(node);
                    }
                }
            }
        }
        return nodes;
    }
    
    private static SubChunkNode checkNeigbor(SubChunk chunk, int x, int y, int z, final byte channel) {
        if (!chunk.getVoxels().inBounds(x, y, z)) {
            final WCCi wcc = WCCi.getNeighboringWCC(chunk.getPosition(), x, y, z);
            if (!wcc.subChunkExists()) {
                return null;
            }
            chunk = wcc.getSubChunk();
            x = wcc.subChunkVoxel.x;
            y = wcc.subChunkVoxel.y;
            z = wcc.subChunkVoxel.z;
        }
        final TorchChannelSet fragChannels = chunk.getLightMap().getTorchlight(x, y, z);
        if (fragChannels != null && fragChannels.get(channel) > 0) {
            return new SubChunkNode(chunk, x, y, z);
        }
        return null;
    }
}
