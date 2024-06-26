/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.plants.trees;

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
public class OakTreeUtils {

    public static void plantTree(PointerHandler ph, Random rand, int x, int y, int z) {
        int height = MiscUtils.randomInt(rand, 5, 7);
        for (int k = 0; k < height; k++) {
           GameItems.OAK_LOG.set(x, y - k, z);
        }

        TreeUtils.roundedSquareLeavesLayer(x, y - height + 2, z, 2, GameItems.OAK_LEAVES);
        TreeUtils.roundedSquareLeavesLayer(x, y - height + 1, z, 2, GameItems.OAK_LEAVES);
        TreeUtils.diamondLeavesLayer(x, y - height, z, 2, GameItems.OAK_LEAVES);
        if (rand.nextDouble() > 0.8) {
            TreeUtils.diamondLeavesLayer(x, y - height - 1, z, 2, GameItems.OAK_LEAVES);
        }
    }

    public static void plantTree(Terrain terrain, Chunk source, int x, int y, int z) {
        PointerHandler ph = terrain.getPointerHandler();
        int height = MiscUtils.randomInt(terrain.getRandom(), 5, 7);
        for (int k = 0; k < height; k++) {
            TreeUtils.setBlock(terrain, source, GameItems.OAK_LOG, x, y - k, z);
        }

        TreeUtils.roundedSquareLeavesLayer(terrain, source, x, y - height + 2, z, 2, GameItems.OAK_LEAVES);
        TreeUtils.roundedSquareLeavesLayer(terrain, source, x, y - height + 1, z, 2, GameItems.OAK_LEAVES);
        TreeUtils.diamondLeavesLayer(terrain, source, x, y - height, z, 2, GameItems.OAK_LEAVES);
        if (terrain.getRandom().nextDouble() > 0.8) {
            TreeUtils.diamondLeavesLayer(terrain, source, x, y - height - 1, z, 2, GameItems.OAK_LEAVES);
        }
    }
}
