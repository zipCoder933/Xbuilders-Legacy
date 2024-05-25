/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.plants.trees;

import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.game.terrain.Terrain;
import com.xbuilders.game.items.GameItems;
import java.util.Random;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class AcaciaTreeUtils {

    private static void treeBush(PointerHandler ph, int x, int y, int z, int bushRadius) {
        TreeUtils.roundedSquareLeavesLayer(x, y, z, bushRadius, GameItems.ACACIA_LEAVES);
        TreeUtils.diamondLeavesLayer(x, y - 1, z, bushRadius, GameItems.ACACIA_LEAVES);
    }

    public static void plantTree(PointerHandler ph, Random rand, int x, int y, int z) {
        int height = MiscUtils.randomInt(rand, 3, 4);
        for (int k = 0; k < height; k++) {
            TreeUtils.setBlockAndOverride(GameItems.ACACIA_LOG, x, y - (height - 1) + k, z);
        }

        int length = MiscUtils.randomInt(rand, 2, 4);
        int xDir = MiscUtils.randomInt(rand, -1, 1);
        int zDir = 0;
        if (xDir == 0) {
            zDir = MiscUtils.randomBool(rand) ? -1 : 1;
        }
        Vector3i vec = TreeUtils.generateBranch(x, y - height + 1, z, length, xDir, zDir, GameItems.ACACIA_LOG);
        TreeUtils.setBlockAndOverride(GameItems.ACACIA_LOG, vec.x, vec.y - 1, vec.z);
        treeBush(ph, vec.x, vec.y - 1, vec.z, MiscUtils.randomInt(rand, 3, 4));

        if (MiscUtils.randomBool(rand)) {
            if (xDir == 0) {
                zDir = 0 - zDir;
            } else {
                xDir = 0 - xDir;
            }
        } else {
            if (xDir == 0) {
                xDir += MiscUtils.randomInt(rand, -1, 1);
                zDir = 0;
            } else {
                zDir += MiscUtils.randomInt(rand, -1, 1);
                xDir = 0;
            }
        }
        length = MiscUtils.randomInt(rand, 3, 4);
        vec = TreeUtils.generateBranch(x, y - height + 1, z, length, xDir, zDir, GameItems.ACACIA_LOG);
        TreeUtils.setBlockAndOverride(GameItems.ACACIA_LOG, vec.x, vec.y - 1, vec.z);
        treeBush(ph, vec.x, vec.y - 1, vec.z, MiscUtils.randomInt(rand, 2, 3));
    }

    private static void treeBush(Terrain terrain, Chunk sourceChunk, int x, int y, int z, int bushRadius) {
        PointerHandler ph = terrain.getPointerHandler();
        TreeUtils.roundedSquareLeavesLayer(terrain, sourceChunk, x, y, z, bushRadius, GameItems.ACACIA_LEAVES);
        TreeUtils.diamondLeavesLayer(terrain, sourceChunk, x, y - 1, z, bushRadius, GameItems.ACACIA_LEAVES);
    }

    public static void plantTree(Terrain terrain, Chunk sourceChunk, int x, int y, int z) {
        PointerHandler ph = terrain.getPointerHandler();
        int height = MiscUtils.randomInt(terrain.getRandom(), 3, 4);
        for (int k = 0; k < height; k++) {
            TreeUtils.setBlock(terrain, sourceChunk, GameItems.ACACIA_LOG, x, y - (height - 1) + k, z);
        }

        int length = MiscUtils.randomInt(terrain.getRandom(), 2, 4);
        int xDir = MiscUtils.randomInt(terrain.getRandom(), -1, 1);
        int zDir = 0;
        if (xDir == 0) {
            zDir = MiscUtils.randomBool(terrain.getRandom()) ? -1 : 1;
        }
        Vector3i vec = TreeUtils.generateBranch(terrain, sourceChunk, x, y - height + 1, z, length, xDir, zDir, GameItems.ACACIA_LOG);
        TreeUtils.setBlock(terrain, sourceChunk, GameItems.ACACIA_LOG, vec.x, vec.y - 1, vec.z);
        treeBush(terrain, sourceChunk, vec.x, vec.y - 1, vec.z, MiscUtils.randomInt(terrain.getRandom(), 3, 4));

        if (MiscUtils.randomBool(terrain.getRandom())) {
            if (xDir == 0) {
                zDir = 0 - zDir;
            } else {
                xDir = 0 - xDir;
            }
        } else {
            if (xDir == 0) {
                xDir += MiscUtils.randomInt(terrain.getRandom(), -1, 1);
                zDir = 0;
            } else {
                zDir += MiscUtils.randomInt(terrain.getRandom(), -1, 1);
                xDir = 0;
            }
        }
        length = MiscUtils.randomInt(terrain.getRandom(), 3, 4);
        vec = TreeUtils.generateBranch(terrain, sourceChunk, x, y - height + 1, z, length, xDir, zDir, GameItems.ACACIA_LOG);
        TreeUtils.setBlock(terrain, sourceChunk, GameItems.ACACIA_LOG, vec.x, vec.y - 1, vec.z);
        treeBush(terrain, sourceChunk, vec.x, vec.y - 1, vec.z, MiscUtils.randomInt(terrain.getRandom(), 2, 3));
    }

}
