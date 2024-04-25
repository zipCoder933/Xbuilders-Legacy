/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.player.blockPipeline;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.light.torch.TorchUtils;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.world.chunk.wcc.WCCi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.joml.Vector3i;

/**
 * @author zipCoder933
 */
public class BlockPipeline {

    private final static Object adjacentBlockQueueLock = new Object();
    private final static Object pipelineLock = new Object();
    private static HashSet<LocalChangeRecord> adjacentBlockEventQueue = new HashSet<>();
    private static HashMap<Vector3i, BlockHistory> blockPipeline = new HashMap<>();

    /**
     * Puts a block in the pipeline
     *
     * @param worldPos the world coordinates of the block
     * @param hist     the block history (old and new block types)
     */
    public static void put(Vector3i worldPos, BlockHistory hist) {
        synchronized (pipelineLock) {
            if (blockPipeline.containsKey(worldPos)) {
                BlockHistory hist2 = new BlockHistory(blockPipeline.get(worldPos).getOld(), hist.getNew());
                blockPipeline.put(worldPos, hist2);
            } else {
                blockPipeline.put(worldPos, hist);
            }
        }
    }

    public static void putAll(HashMap<Vector3i, BlockHistory> lightPipeline) {
        for (HashMap.Entry<Vector3i, BlockHistory> entry : lightPipeline.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public static void resolvePipeline() {
        try {
            synchronized (pipelineLock) {
                if (!blockPipeline.isEmpty()) {
                    boolean sunlightNodesWereAdded = false;
                    for (Map.Entry<Vector3i, BlockHistory> entry : blockPipeline.entrySet()) {
                        final Vector3i blockPos = entry.getKey(); //the block position in world space
                        final BlockHistory hist = entry.getValue();

                        if (VoxelGame.getWorld().inBounds(blockPos)) {
                            WCCi wcc = new WCCi().set(blockPos);

                            if (!hist.getOld().isLuminous() && hist.getNew().isLuminous()) {
                                TorchUtils.setTorchlight(wcc.getSubChunk(),
                                        wcc.subChunkVoxel.x,
                                        wcc.subChunkVoxel.y,
                                        wcc.subChunkVoxel.z, hist.getNew().getLightFalloff());
                            } else if (hist.getOld().isLuminous() && !hist.getNew().isLuminous()) {
                                TorchUtils.removeTorchlight(wcc.getSubChunk(),
                                        wcc.subChunkVoxel.x,
                                        wcc.subChunkVoxel.y,
                                        wcc.subChunkVoxel.z, hist.getOld().getLightFalloff());
                            } else if (!hist.getNew().isLuminous()) {
                                TorchUtils.update(wcc.getSubChunk(), wcc.subChunkVoxel, hist);
                            }
                            //Sunlight propagation:  
//                                    SunlightUtils.update(blockPos, hist);
                            if (hist.getNew().isOpaque() != hist.getOld().isOpaque()) {
                                sunlightNodesWereAdded = true;
                            }
                            VoxelGame.getGame().player.blockTools.blockSetter.setSunlightNodes(wcc, hist.getNew(), hist.getOld());
                        }
                    }
                    blockPipeline.clear();
                    if (sunlightNodesWereAdded) {
                        VoxelGame.getGame().player.blockTools.blockSetter.wakeUp();
                    }
                }
            }
            if(!adjacentBlockEventQueue.isEmpty()) {
                clearLocalChangeQueue();
            }
        } catch (Exception e) {
            ErrorHandler.handleFatalError(e);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Liquid local change(commented)">
    //    public static void startLiquidChange(PointerHandler ph, Vector3i blockPos, Block newBlock) {
    //        BlockHistory hist = new BlockHistory(ph.getWorld().getBlock(blockPos), newBlock);
    //        startLiquidChange(ph, blockPos, hist);
    //    }
    //    public static void startLiquidChange(PointerHandler ph, Vector3i blockPos, BlockHistory hist) {
    //        if (hist.getOld().isLiquid() && !hist.getNew().isLiquid()
    //                || hist.getOld().isSolid() && !hist.getNew().isSolid()) {
    //            if (!ph.getPlayer().blockPanel.curItemEquals(ToolList.LIQUID_REMOVAL_TOOL)) {
    //                checkAndStartLiquid(blockPos.x, blockPos.y - 1, blockPos.z);
    //                checkAndStartLiquid(blockPos.x - 1, blockPos.y, blockPos.z);
    //                checkAndStartLiquid(blockPos.x + 1, blockPos.y, blockPos.z);
    //                checkAndStartLiquid(blockPos.x, blockPos.y, blockPos.z - 1);
    //                checkAndStartLiquid(blockPos.x, blockPos.y, blockPos.z + 1);
    //            }
    //        }
    //    }
    //
    //    private static void checkAndStartLiquid(int x, int y, int z) {
    //        Vector3i pos = new Vector3i(x, y, z);
    //        LocalChangeRecord record = new LocalChangeRecord(null, pos, null);
    //        if (VoxelGame.getWorld().getBlock(x, y, z).isLiquid()) {
    //            synchronized (adjacentBlockQueueLock) {
    //                adjacentBlockEventQueue.add(record);
    //            }
    //        }
    //    }
    //</editor-fold>
    public static void startLocalChange(Vector3i originPos, Block newBlock) {
        BlockHistory hist = new BlockHistory(VoxelGame.getWorld().getBlock(originPos), newBlock);
        checkAndStartBlock(originPos.x, originPos.y - 1, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y + 1, originPos.z, originPos, hist);

        checkAndStartBlock(originPos.x - 1, originPos.y, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x + 1, originPos.y, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y, originPos.z - 1, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y, originPos.z + 1, originPos, hist);

        checkAndStartBlock(originPos.x - 1, originPos.y - 1, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x + 1, originPos.y - 1, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y - 1, originPos.z - 1, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y - 1, originPos.z + 1, originPos, hist);

        checkAndStartBlock(originPos.x - 1, originPos.y + 1, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x + 1, originPos.y + 1, originPos.z, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y + 1, originPos.z - 1, originPos, hist);
        checkAndStartBlock(originPos.x, originPos.y + 1, originPos.z + 1, originPos, hist);
    }

    private static void checkAndStartBlock(int x, int y, int z, Vector3i originPos, BlockHistory hist) {
        Vector3i pos = new Vector3i(x, y, z);
        LocalChangeRecord record = new LocalChangeRecord(originPos, pos, hist);
        if (!VoxelGame.getWorld().getBlock(x, y, z).isAir() && adjacentBlockEventQueue.size() < 25) {
            synchronized (adjacentBlockQueueLock) {
                adjacentBlockEventQueue.add(record);
            }
        }
    }

    private static void clearLocalChangeQueue() {
        synchronized (adjacentBlockQueueLock) {
            System.out.println(adjacentBlockEventQueue.size() + " Local changes; t="+ VoxelGame.ph().getApplet().frameCount);
            for (LocalChangeRecord record : adjacentBlockEventQueue) {
                if (record != null
                        && record.getTargetBlockPosition() != null
                        && record.getChangedBlockHistory() != null
                        && record.getEventBlockPosition() != null) {
                    Block block = VoxelGame.getWorld().getBlock(
                            record.getTargetBlockPosition());
                    block.onLocalChange(record);
                }
            }
        }
        adjacentBlockEventQueue.clear();
    }

}
