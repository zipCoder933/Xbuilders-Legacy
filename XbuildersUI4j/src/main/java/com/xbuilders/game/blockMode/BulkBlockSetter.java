// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.player.blockPipeline.BlockPipeline;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.player.blockPipeline.BlockHistory;
import com.xbuilders.engine.light.torch.TorchUtils;
import com.xbuilders.engine.light.sunlight.SunlightUtils;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import org.joml.Vector3i;
import com.xbuilders.engine.items.block.Block;
import java.util.ArrayList;

import com.xbuilders.engine.light.SubChunkNode;
import com.xbuilders.engine.light.sunlight.SunInitialNodeUtils;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.world.chunk.Chunk;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BulkBlockSetter {

    SetThread setThread;
    public ListQueue<SubChunkNode> transparentToOpaqueQueue;
    private ListQueue<SubChunkNode> opaqueToTransparentQueue;
    private Set<BlockToSet> fastBlockQueue;
    private ArrayList<BlockToSet> threadQueue;

    public BulkBlockSetter() {
        this.fastBlockQueue = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.threadQueue = new ArrayList<BlockToSet>();
        this.transparentToOpaqueQueue = new ListQueue<SubChunkNode>();
        this.opaqueToTransparentQueue = new ListQueue<SubChunkNode>();
    }

    public void start() {
        this.fastBlockQueue.clear();
        this.threadQueue.clear();
        (this.setThread = new SetThread()).setPriority(1);
        this.setThread.start();
    }

    public void stop() {
        this.setThread.interrupt();
        this.setThread = null;
    }

    public void addToBlockQueue(final Block block, final Vector3i vec, final BlockData data) {
        Block prevBlock = VoxelGame.getWorld().getBlock(vec);

        if (prevBlock != block
                && vec.y < Chunk.CHUNK_Y_LENGTH - 1
                && vec.y > 0
                && VoxelGame.getWorld().inBounds(vec)
                && !this.willCollideWithPlayer(vec)) {

            this.fastBlockQueue.add(new BlockToSet(block, prevBlock, vec, data));
            VoxelGame.getWorld().setBlockAndUpdate(block, data, vec.x, vec.y, vec.z);
        } else {
            VoxelGame.getWorld().setBlockDataAndUpdate(data, vec.x, vec.y, vec.z);
        }
    }

    public void wakeUp() {
        long start = System.currentTimeMillis();
        synchronized (this.setThread) {
            this.setThread.notify();
        }
        long time = (System.currentTimeMillis() - start);
        if (time > 500) {
            System.out.println("It took " + time + "MS to wake up block thread");
        }
    }

    public boolean queueIsEmpty() {
        return this.fastBlockQueue.isEmpty();
    }

    private boolean willCollideWithPlayer(final Vector3i coords) {
        if (VoxelGame.getPlayer().passThroughMode) {
            return false;
        }
        UserControlledPlayer userControlledPlayer2 = VoxelGame.getPlayer();
        final int playerX = (int) userControlledPlayer2.worldPos.x;
        UserControlledPlayer userControlledPlayer1 = VoxelGame.getPlayer();
        final int playerY = (int) userControlledPlayer1.worldPos.y;
        UserControlledPlayer userControlledPlayer = VoxelGame.getPlayer();
        final int playerZ = (int) userControlledPlayer.worldPos.z;
        return coords.x == playerX && coords.z == playerZ && (coords.y == playerY || coords.y == playerY - 1 || coords.y == playerY - 2);
    }

    public void setSunlightNodes(WCCi wcc, Block block, Block prevBlock) {
        if (block.isOpaque() && !prevBlock.isOpaque()) {
            SunInitialNodeUtils.addTransToOpaqueNodes(this.transparentToOpaqueQueue, wcc.getSubChunk(), wcc.subChunkVoxel.x, wcc.subChunkVoxel.y, wcc.subChunkVoxel.z);
        } else if (!block.isOpaque() && prevBlock.isOpaque()) {
            SunInitialNodeUtils.addOpaqueToTransNodes(this.opaqueToTransparentQueue, wcc.getSubChunk(), wcc.subChunkVoxel.x, wcc.subChunkVoxel.y, wcc.subChunkVoxel.z);
        }
    }

    private class SetThread extends Thread {

        @Override
        public void run() {
            while (!Thread.interrupted()) {

                while (true) {
                    synchronized (this) {
                        if (!fastBlockQueue.isEmpty() || hasSunNodes()) {
                            break;
                        }
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(BulkBlockSetter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                try {
                    threadQueue.clear();
                    threadQueue.addAll(fastBlockQueue);
                    for (int i = 0; i < threadQueue.size(); ++i) {
                        //<editor-fold defaultstate="collapsed" desc="Set blocks initially">
                        final BlockToSet b = threadQueue.get(i);
                        final WCCi wcc = new WCCi().set(b.coords);
                        VoxelGame.getGame().showProgressMessage("Setting Blocks", i / (float) threadQueue.size());

                        final SubChunk subChunk = wcc.getSubChunk();
                        if (subChunk != null) {
                            subChunk.getParentChunk().markAsModifiedByUser();
                            setSunlightNodes(wcc, b.block, b.prevBlock);
                            if (!b.prevBlock.isLuminous() && b.block.isLuminous()) {
                                TorchUtils.setTorchlight(wcc.getSubChunk(), wcc.subChunkVoxel.x, wcc.subChunkVoxel.y, wcc.subChunkVoxel.z, b.block.getLightFalloff());
                            } else if (b.prevBlock.isLuminous() && !b.block.isLuminous()) {
                                TorchUtils.removeTorchlight(wcc.getSubChunk(), wcc.subChunkVoxel.x, wcc.subChunkVoxel.y, wcc.subChunkVoxel.z, b.prevBlock.getLightFalloff());
                            } else if (!b.block.isLuminous()) {
                                TorchUtils.update(wcc.getSubChunk(), wcc.subChunkVoxel, new BlockHistory(b.prevBlock, b.block));
                            }
                        }
                        //</editor-fold>
                    }
                    if (hasSunNodes()) {
                        VoxelGame.getGame().showProgressMessage("Setting Sunlight");
                        SunlightUtils.updateFromQueue(opaqueToTransparentQueue, transparentToOpaqueQueue);
                    }
                    for (int i = 0; i < threadQueue.size(); ++i) {
                        final BlockToSet b = threadQueue.get(i);
                        VoxelGame.getGame().showProgressMessage("Setting Events", i / (float) threadQueue.size());
                        if (b.block.isLiquid()) {
                            b.block.setBlock(b.coords.x, b.coords.y, b.coords.z, null);
                        }
                        BlockPipeline.startLocalChange( b.coords, b.block);
                    }
                    VoxelGame.getGame().clearProgressMessage();
                    fastBlockQueue.removeAll(threadQueue);
                } catch (Exception ex) {
                    ErrorHandler.saveErrorToLogFile(ex);
                }
            }
        }

        private boolean hasSunNodes() {
            return opaqueToTransparentQueue.containsNodes() || transparentToOpaqueQueue.containsNodes();
        }
    }
}
