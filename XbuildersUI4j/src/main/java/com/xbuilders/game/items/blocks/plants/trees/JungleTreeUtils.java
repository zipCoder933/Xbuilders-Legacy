/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.plants.trees;

import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.random.RandomUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.game.terrain.Terrain;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;
import java.util.ArrayList;
import java.util.Random;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class JungleTreeUtils {

    static class VineBranchPair {

        public ArrayList<Vector3i> vines;
        public ArrayList<Vector3i> branches;
    }

    private static VineBranchPair setVinesAndBranches(Random random, int x, int z, int radius) {
        int lowerBoundX = x - radius;
        int upperBoundX = x + radius;
        int lowerBoundZ = z - radius;
        int upperBoundZ = z + radius;
        VineBranchPair ret = new VineBranchPair();

        ret.vines = new ArrayList<>();
        ret.branches = new ArrayList<>();

        for (int x2 = lowerBoundX; x2 <= upperBoundX; x2++) {
            for (int z2 = lowerBoundZ; z2 <= upperBoundZ; z2++) {

                if (!((x2 == lowerBoundX && z2 == lowerBoundZ)
                        || (x2 == upperBoundX && z2 == upperBoundZ)
                        || (x2 == lowerBoundX && z2 == upperBoundZ)
                        || (x2 == upperBoundX && z2 == lowerBoundZ))) {
                    if (!(x2 == 0 && z2 == 0)) {
                        if (random.nextFloat() > 0.95) {
                            ret.branches.add(new Vector3i(x2, 0, z2));
                        } else if (random.nextFloat() > 0.96) {
                            ret.vines.add(new Vector3i(x2, 0, z2));
                        }
                    }
                }
            }
        }
        return ret;
    }

    public static void plantTree(PointerHandler ph, Random rand, int x, int y, int z) {
        int height = MiscUtils.randomInt(rand, 5, 13);
        int firstLayerWide = 0;
        firstLayerWide = RandomUtils.randInt(rand, 2, 4);
        TreeUtils.roundedSquareLeavesLayer(x, (y - height + 2), z, firstLayerWide, GameItems.BLOCK_JUNGLE_LEAVES);
        TreeUtils.roundedSquareLeavesLayer(x + RandomUtils.randInt(rand, -1, 1),
                (y - height + 1),
                z + RandomUtils.randInt(rand, -1, 1),
                firstLayerWide, GameItems.BLOCK_JUNGLE_LEAVES);

        VineBranchPair vb = setVinesAndBranches(rand, x, z, firstLayerWide);
        int h4 = (int) (height * 0.4);
        for (int k = 0; k < height; k++) {
         GameItems.BLOCK_JUNGLE_LOG.set(x, y - k, z);
            if (k < height - 1) {
                if (k > h4) {
                    for (Vector3i branch : vb.branches) {
                        TreeUtils.setBlock(GameItems.BLOCK_JUNGLE_LEAVES, branch.x, y - k, branch.z);
                    }
                }
                for (Vector3i vine : vb.vines) {
                    TreeUtils.setBlock(GameItems.Vines, vine.x, y - k, vine.z);
                }
            }
        }

        TreeUtils.diamondLeavesLayer(x, y - height, z, 3, GameItems.BLOCK_JUNGLE_LEAVES);
        if (rand.nextDouble() > 0.8) {
            TreeUtils.diamondLeavesLayer(x, y - height - 1, z, 2, GameItems.BLOCK_JUNGLE_LEAVES);
        }
    }

    public static void plantTree(Terrain terrain, Chunk source, int x, int y, int z) {
      
        int height = MiscUtils.randomInt(terrain.getRandom(), 5, 13);
        int firstLayerWide = 0;
        firstLayerWide = RandomUtils.randInt(terrain.getRandom(), 2, 4);
        TreeUtils.roundedSquareLeavesLayer(terrain, source, x, (y - height + 2), z, firstLayerWide, GameItems.BLOCK_JUNGLE_LEAVES);
        TreeUtils.roundedSquareLeavesLayer(terrain, source,
                x + RandomUtils.randInt(terrain.getRandom(), -1, 1),
                (y - height + 1),
                z + RandomUtils.randInt(terrain.getRandom(), -1, 1),
                firstLayerWide, GameItems.BLOCK_JUNGLE_LEAVES);

        VineBranchPair vb = setVinesAndBranches(terrain.getRandom(), x, z, firstLayerWide);
        int h4 = (int) (height * 0.4);
        for (int k = 0; k < height; k++) {
            TreeUtils.setBlock(terrain, source, GameItems.BLOCK_JUNGLE_LOG, x, y - k, z);
            if (k < height - 1) {
                if (k > h4) {
                    for (Vector3i branch : vb.branches) {
                        if (branch.x != x && branch.z != z) {
                            TreeUtils.setBlock(terrain, source, GameItems.BLOCK_JUNGLE_LEAVES, branch.x, y - k, branch.z);
                        }
                    }
                }
                for (Vector3i vine : vb.vines) {
                    if (vine.x != x && vine.z != z) {
                        TreeUtils.setBlock(terrain, source, GameItems.Vines, vine.x, y - k, vine.z);
                    }
                }
            }
        }

        TreeUtils.diamondLeavesLayer(terrain, source, x, y - height, z, 3, GameItems.BLOCK_JUNGLE_LEAVES);
        if (terrain.getRandom().nextDouble() > 0.8) {
            TreeUtils.diamondLeavesLayer(terrain, source, x, y - height - 1, z, 2, GameItems.BLOCK_JUNGLE_LEAVES);
        }
    }
}
