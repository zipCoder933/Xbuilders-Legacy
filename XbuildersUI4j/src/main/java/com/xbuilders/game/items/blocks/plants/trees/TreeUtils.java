/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.plants.trees;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.BFS.HashQueue;
import com.xbuilders.engine.utils.BFS.TravelNode;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.game.terrain.Terrain;
import java.util.HashSet;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class TreeUtils {

    public static void squareLeavesLayer(int x, int y, int z, int radius, Block leaves) {
        int lowerBoundX = x - radius;
        int upperBoundX = x + radius;
        int lowerBoundZ = z - radius;
        int upperBoundZ = z + radius;

        for (int x2 = lowerBoundX; x2 <= upperBoundX; x2++) {
            for (int z2 = lowerBoundZ; z2 <= upperBoundZ; z2++) {

                setBlock(leaves, x2, y, z2);

            }
        }
    }

    public static void squareLeavesLayer(Terrain terrain, Chunk source, int x, int y, int z, int radius, Block leaves) {
        int lowerBoundX = x - radius;
        int upperBoundX = x + radius;
        int lowerBoundZ = z - radius;
        int upperBoundZ = z + radius;

        for (int x2 = lowerBoundX; x2 <= upperBoundX; x2++) {
            for (int z2 = lowerBoundZ; z2 <= upperBoundZ; z2++) {
                if (!VoxelGame.getWorld().getBlock(x2, y, z2).isSolid()) {
                    terrain.setBlockWorld(source, leaves, x2, y, z2);
                }
            }
        }
    }

    public static void roundedSquareLeavesLayer(int x, int y, int z, int radius, Block leaves) {
        int lowerBoundX = x - radius;
        int upperBoundX = x + radius;
        int lowerBoundZ = z - radius;
        int upperBoundZ = z + radius;

        for (int x2 = lowerBoundX; x2 <= upperBoundX; x2++) {
            for (int z2 = lowerBoundZ; z2 <= upperBoundZ; z2++) {

                if (!((x2 == lowerBoundX && z2 == lowerBoundZ)
                        || (x2 == upperBoundX && z2 == upperBoundZ)
                        || (x2 == lowerBoundX && z2 == upperBoundZ)
                        || (x2 == upperBoundX && z2 == lowerBoundZ))) {
                    setBlock(leaves, x2, y, z2);
                }
            }
        }
    }

    public static void roundedSquareLeavesLayer(Terrain terrain, Chunk source, int x, int y, int z, int radius, Block leaves) {
        int lowerBoundX = x - radius;
        int upperBoundX = x + radius;
        int lowerBoundZ = z - radius;
        int upperBoundZ = z + radius;

        for (int x2 = lowerBoundX; x2 <= upperBoundX; x2++) {
            for (int z2 = lowerBoundZ; z2 <= upperBoundZ; z2++) {

                if (!((x2 == lowerBoundX && z2 == lowerBoundZ)
                        || (x2 == upperBoundX && z2 == upperBoundZ)
                        || (x2 == lowerBoundX && z2 == upperBoundZ)
                        || (x2 == upperBoundX && z2 == lowerBoundZ))) {
                    if (!VoxelGame.getWorld().getBlock(x2, y, z2).isSolid()) {
                        terrain.setBlockWorld(source, leaves, x2, y, z2);
                    }
                }
            }
        }
    }

    //Terrain generators create terrains in world space,
    //so there is nothing we have to change here.
    public static void setBlock(Block b, int x, int y, int z) {
        if (!VoxelGame.getWorld().getBlock(x, y, z).isSolid()) {
            b.set(x, y, z);
        }
    }

    public static void setBlockAndOverride(Block b, int x, int y, int z) {
        b.set(x, y, z);
    }

    //Terrain generators create terrains in world space,
    //so there is nothing we have to change here.
    public static void diamondLeavesLayer(int x, int y, int z, int travelDist, Block leaves) {
        HashSet<TravelNode> exploredNodes = new HashSet<>();
        HashQueue<TravelNode> queue = new HashQueue<>();
        queue.add(new TravelNode(x, y, z, 0));

        while (!queue.isEmpty()) {
            TravelNode node = queue.getAndRemove();
            Vector3i coords = node.getCoords();

            if (!exploredNodes.contains(node) && node.getTravel() < travelDist) {
                setBlock(leaves, coords.x, coords.y, coords.z);
                queue.add(new TravelNode(coords.x + 1, coords.y, coords.z, node.getTravel() + 1));
                queue.add(new TravelNode(coords.x, coords.y, coords.z + 1, node.getTravel() + 1));
                queue.add(new TravelNode(coords.x - 1, coords.y, coords.z, node.getTravel() + 1));
                queue.add(new TravelNode(coords.x, coords.y, coords.z - 1, node.getTravel() + 1));
                exploredNodes.add(node);
            }
        }
    }

    public static void setBlock(Terrain terrain, Chunk sourceChunk, Block b, int x, int y, int z) {
        if (!VoxelGame.getWorld().getBlock(x, y, z).isSolid()) {
            terrain.setBlockWorld(sourceChunk, b, x, y, z);
        }
    }

    public static void diamondLeavesLayer(Terrain terrain, Chunk source, int x, int y, int z, int travelDist, Block leaves) {
        HashQueue<TravelNode> queue = new HashQueue<>();
        queue.add(new TravelNode(x, y, z, 0));

        while (!queue.isEmpty()) {
            TravelNode node = queue.getAndRemove();
            Vector3i coords = node.getCoords();
            Block block = VoxelGame.getWorld().getBlock(coords);

            if (!block.equals(leaves) && node.getTravel() < travelDist) {
                if (!block.isSolid()) {
                    terrain.setBlockWorld(source, leaves, coords.x, coords.y, coords.z);
                }
                queue.add(new TravelNode(coords.x + 1, coords.y, coords.z, node.getTravel() + 1));
                queue.add(new TravelNode(coords.x, coords.y, coords.z + 1, node.getTravel() + 1));
                queue.add(new TravelNode(coords.x - 1, coords.y, coords.z, node.getTravel() + 1));
                queue.add(new TravelNode(coords.x, coords.y, coords.z - 1, node.getTravel() + 1));
            }
        }
    }

    public static Vector3i generateBranch(int x, int y, int z, int length, int xDir, int zDir, Block logType) {
        for (int i = 0; i < length; i++) {
            x += xDir;
            z += zDir;
            y--;
            setBlock(logType, x, y, z);
        }
        return new Vector3i(x, y, z);
    }

    public static Vector3i generateBranch(Terrain terrain, Chunk source, int x, int y, int z, int length, int xDir, int zDir, Block logType) {
        for (int i = 0; i < length; i++) {
            x += xDir;
            z += zDir;
            y--;
            if (!VoxelGame.getWorld().getBlock(x, y, z).isSolid()) {
                terrain.setBlockWorld(source, logType, x, y, z);
            }
        }
        return new Vector3i(x, y, z);
    }
}
