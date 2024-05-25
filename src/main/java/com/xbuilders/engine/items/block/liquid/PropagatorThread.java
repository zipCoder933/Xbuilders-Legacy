/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.block.liquid;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.BFS.HashQueue;
import com.xbuilders.engine.utils.BFS.TravelNode;
import com.xbuilders.engine.utils.ErrorHandler;

import java.util.ConcurrentModificationException;

/**
 *
 * @author zipCoder933
 */
class PropagatorThread extends Thread {

    LiquidPropagator parent;

    public PropagatorThread(LiquidPropagator parent) {
        this.parent = parent;
    }

    public void wake() {
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (!this.isInterrupted()) {
                    synchronized (this) {
                        wait();
                    }
                    if (!parent.queue.isEmpty()) {
                        while (!parent.queue.isEmpty() && !this.isInterrupted()) {
                            Thread.sleep(parent.speed);
                            clearCopyOfQueue();
                        }
                    }
                }
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {
//                System.out.println(parent.parent.getName() + " liquid thread interuppted.");
            }
        }
    }

    private void horizontalSpread(HashQueue queue, int x, int y, int z, int travel) {
        Block block = VoxelGame.ph().getWorld().getBlock(x, y, z);
        if (parent.isPenatrable(block) && !block.isLiquid()) {
            queue.add(new TravelNode(x, y, z, travel));
        }
    }

    private void removeBlock(int x, int y, int z) {
        if (VoxelGame.ph().getWorld().getBlock(x, y, z) == parent.parent) {
            if (parent.smallHead(x, y, z)) {
                BlockList.BLOCK_AIR.set(x, y, z);
                parent.queue.add(new TravelNode(x, y, z, -1));
            }
        }
    }

    private void clearCopyOfQueue() {
        HashQueue<TravelNode> tempQueue;
        synchronized (parent.addLock) {
            try {
                tempQueue = new HashQueue<>(parent.queue);
            } catch (ConcurrentModificationException ex) {
                ErrorHandler.saveErrorToLogFile(ex, "Liquid propagation error");
                tempQueue = new HashQueue<>();
            }
            parent.queue.clear();
        }
        while (!tempQueue.isEmpty() && !this.isInterrupted()) {
            TravelNode node = tempQueue.getAndRemove();
            int nx = node.getCoords().x;
            int ny = node.getCoords().y;
            int nz = node.getCoords().z;

            if (!VoxelGame.ph().getWorld().inBounds(ny)) {
                continue;
            }

            int travel = node.getTravel();
            Block nodeBlock = VoxelGame.ph().getWorld().getBlock(nx, ny, nz);

            if (travel == -1) {
                if (nodeBlock != parent.parent) {
                    Block bottomBlock = VoxelGame.ph().getWorld().getBlock(nx, ny + 1, nz);
                    if (bottomBlock.isLiquid()) {
                        removeBlock(nx, ny + 1, nz);
                        removeBlock(nx + 1, ny + 1, nz);
                        removeBlock(nx - 1, ny + 1, nz);
                        removeBlock(nx, ny + 1, nz + 1);
                        removeBlock(nx, ny + 1, nz - 1);
                    }
                }
            } else {
                if (parent.isPenatrable(nodeBlock) && parent.parent.setBlock(node.getCoords().x, node.getCoords().y, node.getCoords().z)) {
                    Block bottomBlock = VoxelGame.ph().getWorld().getBlock(nx, ny + 1, nz);
                    boolean bottomBlockIsPenetrable = parent.isPenatrable(bottomBlock) || bottomBlock == parent.parent;

                    if (bottomBlockIsPenetrable) {
                        if (!(parent.playerHasLiquidRemovalTool() 
                                && VoxelGame.ph().getWorld().getBlock(nx, ny - 1, nz) != parent.parent)) {
                            parent.queue.add(new TravelNode(nx, ny + 1, nz, travel));//the higher the .99 the better
                        }
                    } else if (!bottomBlock.isLiquid() && travel < parent.maxSpread
                            && !parent.playerHasLiquidRemovalTool()) {
                        horizontalSpread(parent.queue, nx + 1, ny, nz, travel + 1);
                        horizontalSpread(parent.queue, nx - 1, ny, nz, travel + 1);
                        horizontalSpread(parent.queue, nx, ny, nz + 1, travel + 1);
                        horizontalSpread(parent.queue, nx, ny, nz - 1, travel + 1);
                    }
                }
            }
        }
    }
}
