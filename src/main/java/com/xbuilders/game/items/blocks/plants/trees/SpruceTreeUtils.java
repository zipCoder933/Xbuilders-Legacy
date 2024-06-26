/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.plants.trees;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.Terrain;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;
import java.util.Random;

/**
 *
 * @author zipCoder933
 */
public class SpruceTreeUtils {

    public static void plantTree(PointerHandler ph, Random rand, int x, int y, int z) {
        int height = MiscUtils.randomInt(rand, 7, 14);
        for (int k = 0; k < height; k++) {
            VoxelGame.getWorld().setBlock(GameItems.BLOCK_SPRUCE_LOG, x, y - k, z);
        }

        int heightVal = 4;
        int layerValue = 2;

        TreeUtils.squareLeavesLayer(x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
        heightVal--;
        TreeUtils.diamondLeavesLayer(x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
        heightVal--;
        layerValue--;

        if (height > 8) {
            layerValue++;
            TreeUtils.squareLeavesLayer(x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            TreeUtils.diamondLeavesLayer(x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            layerValue--;
        }
        if (height > 10) {
            layerValue++;
            TreeUtils.squareLeavesLayer(x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            TreeUtils.diamondLeavesLayer(x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            layerValue--;
        }

        while (layerValue > 0) {
            TreeUtils.squareLeavesLayer(x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            TreeUtils.diamondLeavesLayer(x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
            if (rand.nextBoolean()) {
                TreeUtils.diamondLeavesLayer(x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
            }
            heightVal--;
            layerValue--;
        }
        VoxelGame.getWorld().setBlock(GameItems.BLOCK_SPRUCE_LEAVES, x, y - height + heightVal, z);
    }

    public static void plantTree(Terrain terrain, Chunk source, int x, int y, int z) {
        int height = MiscUtils.randomInt(terrain.getRandom(), 7, 14);
        for (int k = 0; k < height; k++) {
            VoxelGame.getWorld().setBlock(GameItems.BLOCK_SPRUCE_LOG, x, y - k, z);
        }

        int heightVal = 2;
        int layerValue = 2;

        TreeUtils.squareLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
        heightVal--;
        TreeUtils.diamondLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
        heightVal--;
        layerValue--;

        if (height > 8) {
            layerValue++;
            TreeUtils.squareLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            TreeUtils.diamondLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            layerValue--;
        }
//        if (height > 10) {
//            layerValue++;
//            TreeUtils.squareLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
//            heightVal--;
//            TreeUtils.diamondLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
//            heightVal--;
//            layerValue--;
//        }

        while (layerValue > 0) {
            TreeUtils.squareLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            TreeUtils.diamondLeavesLayer(terrain, source, x, y - height + heightVal, z, layerValue + 1, GameItems.BLOCK_SPRUCE_LEAVES);
            heightVal--;
            layerValue--;
        }
        TreeUtils.setBlockAndOverride(GameItems.BLOCK_SPRUCE_LEAVES, x, y - height + heightVal, z);
    }
}
