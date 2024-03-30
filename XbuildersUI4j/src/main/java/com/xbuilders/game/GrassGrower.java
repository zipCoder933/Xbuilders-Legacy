/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import static com.xbuilders.engine.items.BlockList.DEFAULT_BLOCK_TYPE_ID;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.liquid.Liquid;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.utils.BFS.Node;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.preformance.Stopwatch;
import com.xbuilders.engine.world.World;
import com.xbuilders.game.terrain.Biome;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.BlockUtils;
import java.util.ArrayList;

import org.joml.Vector3i;
import processing.core.UIFrame;

/**
 *
 * @author zipCoder933
 */
public class GrassGrower {

    final int SCAN_RADIUS = 10;
    long delayMS = 5000;
    long lastUpdate = 0;
    static ArrayList<Node> initialNodes = new ArrayList<>();
    static ListQueue<Node> queue = new ListQueue<>();

    public boolean isObstructedBySolidBlock(World world, Vector3i pos) {
        Block block = world.getBlock(pos.x, pos.y - 1, pos.z);
        return (block.isSolid() && block.getRenderType() == DEFAULT_BLOCK_TYPE_ID);
    }

    public void growGrass(UserControlledPlayer p, World world, UIFrame applet) {
        if (System.currentTimeMillis() - lastUpdate > delayMS) {
            Stopwatch watch = new Stopwatch();
            watch.start();
            queue.clear();
            if (initialNodes.isEmpty() || outOfRange(initialNodes, p)) {
//                VoxelGame.printDev("Regenerating new grass node list...");
                scanAndAddNodes();
                delayMS = 20000;
            } else {
//                VoxelGame.printDev("Grass node list size: " + initialNodes.size());
                queue.addNodes(initialNodes);
                delayMS = 6000;
            }

            int blocksChanged = 0;
            while (queue.containsNodes() && blocksChanged < 6) {
                Node node = queue.getAndRemove(0);
                Block block = world.getBlock(node.getCoords());
                if (BlockUtils.isGrass(block)) {
                    if (isObstructedBySolidBlock(world, node.getCoords())) {
                        GameItems.BLOCK_DIRT.set(node.getCoords());
                    }
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x + 1, node.getCoords().y, node.getCoords().z);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x - 1, node.getCoords().y, node.getCoords().z);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x, node.getCoords().y, node.getCoords().z + 1);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x, node.getCoords().y, node.getCoords().z - 1);

                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x + 1, node.getCoords().y + 1, node.getCoords().z);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x - 1, node.getCoords().y + 1, node.getCoords().z);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x, node.getCoords().y + 1, node.getCoords().z + 1);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x, node.getCoords().y + 1, node.getCoords().z - 1);

                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x + 1, node.getCoords().y - 1, node.getCoords().z);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x - 1, node.getCoords().y - 1, node.getCoords().z);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x, node.getCoords().y - 1, node.getCoords().z + 1);
                    blocksChanged += checkNeighbor(world, queue, node.getCoords().x, node.getCoords().y - 1, node.getCoords().z - 1);
                }
            }
            initialNodes.clear();
            initialNodes.addAll(queue);
//            VoxelGame.printDev("Adding leftover nodes to initialNodes. Size: " + initialNodes.size());
            lastUpdate = System.currentTimeMillis();
            watch.calculateElapsedTime();
//            VoxelGame.printDev("Grass updated. Elapsed time: " + watch.toString());
        }
    }

    private int checkNeighbor(World world, ListQueue queue, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block == GameItems.BLOCK_DIRT
                && !isObstructedBySolidBlock(world, new Vector3i(x, y, z))) {
            Biome biome = world.terrain.getBiomeOfVoxel(x, y, z);
            switch (biome) {
                case JUNGLE -> GameItems.BLOCK_JUNGLE_GRASS.set(x, y, z);
                case SAVANNAH -> GameItems.BLOCK_DRY_GRASS.set(x, y, z);
                case SNOWY -> GameItems.BLOCK_SNOW.set(x, y, z);
                default -> GameItems.BLOCK_GRASS.set(x, y, z);
            }

            queue.add(new Node(x, y, z));
            return 1;
        }
        return 0;
    }

    private boolean outOfRange(ArrayList<Node> initialNodes, UserControlledPlayer p) {
        for (Node node : initialNodes) {
            if (MathUtils.dist(
                    node.getCoords().x,
                    node.getCoords().z,
                    p.worldPos.x,
                    p.worldPos.z) < 70) {
                return false;
            }
        }
        return true;
    }

    private void scanAndAddNodes() {
        UserControlledPlayer userControlledPlayer = VoxelGame.ph().getPlayer();
        Vector3i intPos = MathUtils.floatToInt(userControlledPlayer.worldPos);
        initialNodes.clear();

        for (int x = intPos.x - SCAN_RADIUS; x < intPos.x + SCAN_RADIUS; x++) {
            for (int z = intPos.z - SCAN_RADIUS; z < intPos.z + SCAN_RADIUS; z++) {
                for (int y = intPos.y - SCAN_RADIUS; y < intPos.y + SCAN_RADIUS * 3; y++) {
                    Block block = VoxelGame.getWorld().getBlock(x, y, z);
                    if (!block.isAir() && BlockUtils.isGrass(block)) {
                        Node node = new Node(x, y, z);
                        initialNodes.add(node);
                        queue.add(node);
                    } else if (block.isLiquid()) {
                        ((Liquid) block).startLiquid(x, y, z);
                    }
                }
            }
        }
    }
}
