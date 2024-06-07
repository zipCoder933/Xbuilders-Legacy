// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.light.sunlight;

import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.light.SubChunkNode;
import com.xbuilders.engine.utils.BFS.ListQueue;
import java.util.HashMap;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import java.util.Map;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.light.torch.TorchUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.progress.ProgressData;
import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import org.joml.Vector3i;

public class InitialSunlightUtils {

    public static void generateSunlightForWorldInitially(final ProgressData prog, final World chunks, final int uninitializedChunks) throws InterruptedException {
        if (uninitializedChunks == 0) {
            return;
        }
        prog.setTask("Propagating Initial Sunlight", 0, uninitializedChunks);
        prog.getBar().set(0, uninitializedChunks);
        for (final Map.Entry<ChunkCoords, Chunk> set : VoxelGame.getWorld().chunks.entrySet()) {
            final Chunk chunk = set.getValue();
            if (!chunk.lightmapInit) {
                InitialSunlightUtils.generateInitialSunlight(chunk, true);
                prog.getBar().changeValue(1);
                prog.setTask("Propagating Sunlight", prog.getBar());
            }
        }
    }

    public static synchronized boolean generateInitialSunlight(final Chunk chunk, final boolean checkForNeighboringChunks) {
        return generateInitialSunlight(chunk, null, checkForNeighboringChunks);
    }

    private static ListQueue<SubChunkNode> queue = new ListQueue<SubChunkNode>();

    public static synchronized boolean generateInitialSunlight(final Chunk chunk, final HashMap<ChunkCoords, Chunk> otherChunks, final boolean checkForNeighboringChunks) {
        if (chunk.lightmapInit) {
            return true;
        }
        if (checkForNeighboringChunks && !chunk.isSurroundedByChunks(otherChunks)) {
            return false;
        }
        int chunkLocation, blockLocation;
        queue.clear();

        for (int x = 0; x < Chunk.WIDTH; ++x) {
            for (int z = 0; z < Chunk.WIDTH; ++z) {
                boolean addSun = true;
                for (int y = 0; y < Chunk.HEIGHT; ++y) {
                    chunkLocation = WCCi.chunkDiv(y);
                    blockLocation = MathUtils.positiveMod(y, SubChunk.WIDTH);
                    SubChunk subChunk = chunk.getSubChunks()[chunkLocation];
                    Block block = ItemList.getBlock(subChunk.getVoxels().getBlock(x, blockLocation, z));

                    if (block.isLuminous()) {
                        TorchUtils.setTorchlight(subChunk, x, blockLocation, z, block.getLightFalloff());
                    }
                    if (addSun) {
                        if (block.isOpaque()) {
                            chunkLocation = WCCi.chunkDiv(y - 1);
                            if(chunkLocation < 0) chunkLocation = 0;
                            blockLocation = MathUtils.positiveMod((y - 1), SubChunk.WIDTH);
                            subChunk = chunk.getSubChunks()[chunkLocation];
                            queue.add(new SubChunkNode(subChunk, x, blockLocation, z));
                            addSun = false;
                        } else {
                            setSunlight(chunk, x, y, z, (byte) 15);
                            if (!subChunk.getVoxels().isEmpty()) {
                                queue.add(new SubChunkNode(subChunk, x, blockLocation, z));
                            }
                        }
                    }
                }
            }
        }
        propagateSunlightInitial(queue);
        for (final SubChunk sc : chunk.getSubChunks()) {
            sc.getLightMap().initialized = true;
        }
        return chunk.lightmapInit = true;
    }

    protected static void setSunlight(Chunk chunk, final int x, final int y, final int z, final byte b) {
        final int chunkLocation = WCCi.chunkDiv(y);
        final int blockLocation = MathUtils.positiveMod(y, SubChunk.WIDTH);
        chunk.subChunks[chunkLocation].getLightMap().setSunlight(x, blockLocation, z, b);
    }

    protected static byte getSunlight(Chunk chunk, final int x, final int y, final int z) {
        final int chunkLocation = WCCi.chunkDiv(y);
        final int blockLocation = MathUtils.positiveMod(y, SubChunk.WIDTH);
        return chunk.subChunks[chunkLocation].getLightMap().getSunlight(x, blockLocation, z);
    }

    private static synchronized void propagateSunlightInitial(final ListQueue<SubChunkNode> queue) {
        while (queue.containsNodes()) {
            final SubChunkNode node = queue.getAndRemove(0);
//            unusedNodes.add(node);
            final int lightValue = node.chunk.getLightMap().getSunlight(node.coords);
            checkNeighbor(node.chunk, node.coords.x - 1, node.coords.y, node.coords.z, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x + 1, node.coords.y, node.coords.z, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y, node.coords.z + 1, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y, node.coords.z - 1, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y + 1, node.coords.z, lightValue, queue);
            checkNeighbor(node.chunk, node.coords.x, node.coords.y - 1, node.coords.z, lightValue, queue);
        }
    }

    private static synchronized void checkNeighbor(SubChunk chunk, int x, int y, int z, final int lightLevel, final ListQueue<SubChunkNode> queue) {
        Block neigborBlock = null;
        if (chunk.getVoxels().inBounds(x, y, z)) {
            neigborBlock = ItemList.getBlock(chunk.getVoxels().getBlock(x, y, z));
        } else {
            final Vector3i neighboringChunk = new Vector3i();
            WCCi.getNeighboringSubChunk(neighboringChunk, chunk.getPosition(), x, y, z);
            chunk = VoxelGame.getWorld().getSubChunk(neighboringChunk);
            if (chunk != null) {
                x = MathUtils.positiveMod(x, SubChunk.WIDTH);
                y = MathUtils.positiveMod(y, SubChunk.WIDTH);
                z = MathUtils.positiveMod(z, SubChunk.WIDTH);
                neigborBlock = ItemList.getBlock(chunk.getVoxels().getBlock(x, y, z));
            }
        }
        if (neigborBlock != null && !neigborBlock.isOpaque()) {
            final int neighborLevel = chunk.getLightMap().getSunlight(x, y, z);
            if (neighborLevel + 2 <= lightLevel) {
                chunk.getLightMap().setSunlightAndUpdateSLM(x, y, z, (byte) (lightLevel - 1));
                queue.add(new SubChunkNode(chunk, x, y, z));
            }
        }
    }
}
