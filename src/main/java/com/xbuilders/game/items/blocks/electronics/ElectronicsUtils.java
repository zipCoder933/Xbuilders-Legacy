/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.electronics;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.utils.BFS.Node;
import com.xbuilders.engine.utils.BFS.HashQueue;
import com.xbuilders.engine.world.blockData.BlockData;

/**
 *
 * @author zipCoder933
 */
public class ElectronicsUtils {

    public static boolean isConductive(Block block) {
        return block.getRenderType() == BlockRenderType.WIRE
                || block.tags.contains("electronic");
    }

    private static void checkNeighbor(final boolean powered, HashQueue<Node> queue,
            final int x, final int y, final int z, final byte nodeValue) {

        if (isConductive(VoxelGame.getWorld().getBlock(x, y, z))) {
            BlockData data = VoxelGame.getWorld().getBlockData(x, y, z);
            if (data != null && data.bytes.length > 1) {

                if (powered) {
                    if (data.bytes[1] <= nodeValue && nodeValue > 0) {
                        data.bytes[0] = (byte) 1;
                        data.bytes[1] = (byte) (nodeValue - 1);
                        queue.add(new Node(x, y, z));
                    }
                } else {
                    if (data.bytes[1] > 0 && data.bytes[1] <= nodeValue) {
                        data.bytes[0] = (byte) 0;
                        data.bytes[1] = (byte) 0;
                        queue.add(new Node(x, y, z));
                    }
                }

            }
        }
    }

    public static void setWireToPowered(boolean powered, int x, int y, int z) {
        HashQueue<Node> queue = new HashQueue<>();
        queue.add(new Node(x, y, z));
        if (powered) {
            BlockData nodeData = VoxelGame.getWorld().getBlockData(x, y, z);
            if (nodeData != null) {
                nodeData.bytes[1] = (byte) (powered ? 10 : 0);
            }
        }

        while (!queue.isEmpty()) {
            Node node = queue.getAndRemove();
            BlockData nodeData = VoxelGame.getWorld().getBlockData(node.getCoords().x, node.getCoords().y, node.getCoords().z);
            if (nodeData != null && nodeData.bytes.length > 1) {
                checkNeighbor(powered, queue, node.getCoords().x + 1, node.getCoords().y, node.getCoords().z, nodeData.bytes[1]);
                checkNeighbor(powered, queue, node.getCoords().x - 1, node.getCoords().y, node.getCoords().z, nodeData.bytes[1]);
                checkNeighbor(powered, queue, node.getCoords().x, node.getCoords().y + 1, node.getCoords().z, nodeData.bytes[1]);
                checkNeighbor(powered, queue, node.getCoords().x, node.getCoords().y - 1, node.getCoords().z, nodeData.bytes[1]);
                checkNeighbor(powered, queue, node.getCoords().x, node.getCoords().y, node.getCoords().z + 1, nodeData.bytes[1]);
                checkNeighbor(powered, queue, node.getCoords().x, node.getCoords().y, node.getCoords().z - 1, nodeData.bytes[1]);
            }
        }
    }
}
