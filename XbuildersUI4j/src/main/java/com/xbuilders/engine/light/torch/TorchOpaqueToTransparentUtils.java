// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.light.torch;

import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.player.blockPipeline.BlockHistory;
import com.xbuilders.engine.world.chunk.lightMap.TorchChannelSet;
import com.xbuilders.engine.light.SubChunkNode;
import java.util.Map;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import java.util.HashMap;
import com.xbuilders.engine.world.chunk.SubChunk;

class TorchOpaqueToTransparentUtils
{
    protected static HashMap<Byte, OTTNode> findChannelsAndNeigbors(final SubChunk chunk, final int x, final int y, final int z) {
        final HashMap<Byte, OTTNode> list = new HashMap<Byte, OTTNode>();
        findNeg2(chunk, x + 1, y, z, list);
        findNeg2(chunk, x - 1, y, z, list);
        findNeg2(chunk, x, y + 1, z, list);
        findNeg2(chunk, x, y - 1, z, list);
        findNeg2(chunk, x, y, z + 1, list);
        findNeg2(chunk, x, y, z - 1, list);
        return list;
    }
    
    private static void findNeg2(SubChunk chunk, int x, int y, int z, final HashMap<Byte, OTTNode> list) {
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
        final TorchChannelSet neigboringChannels = chunk.getLightMap().getTorchlight(x, y, z);
        if (neigboringChannels != null) {
            for (final Map.Entry<Byte, Byte> entry : neigboringChannels.list.entrySet()) {
                final byte lightVal = entry.getValue();
                final byte falloff = entry.getKey();
                if (lightVal > 0 && (!list.containsKey(falloff) || list.get(falloff).lightVal < lightVal)) {
                    list.put(falloff, new OTTNode(new SubChunkNode(chunk, x, y, z), lightVal));
                }
            }
        }
    }
    
    static void update(final SubChunk chunk, final int x, final int y, final int z, final BlockHistory hist) {
        final HashMap<Byte, OTTNode> channels = findChannelsAndNeigbors(chunk, x, y, z);
        for (final Map.Entry<Byte, OTTNode> entry2 : channels.entrySet()) {
            final byte falloff = entry2.getKey();
            final SubChunkNode startNode = entry2.getValue().node;
            final ListQueue<SubChunkNode> queue = new ListQueue<SubChunkNode>();
            queue.add(startNode);
            TorchUtils.continueBFS(queue, falloff);
        }
    }
}
