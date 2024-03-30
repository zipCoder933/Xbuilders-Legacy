package com.xbuilders.game.items.blocks.plants;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;

public class PlantBlockUtils {

    public static boolean isPlant(PointerHandler ph, Block block) {
        String name = block.name.toLowerCase();
        return name.contains("grass") || name.contains("rose") || name.contains("flower")
                || block == GameItems.Fern
                || block == GameItems.BlueOrchid
                || block == GameItems.AzureBluet
                || block == GameItems.BlackeyeSusan
                || block == GameItems.DeadBush
                || block == GameItems.Dandelion
                || block == GameItems.Pansies;
    }

    public static void growPlant(PointerHandler ph, long growSpeed, final int x, final int y, final int z,
            final Block initialSeed, final Block... stages) throws InterruptedException {

        if (ph.isDevMode()) {
            growSpeed /= 10;
        }
        Block lastStage = initialSeed;

        int i = 0;

        for (Block stage : stages) {
            Thread.sleep(growSpeed);
            if (VoxelGame.getWorld().getBlock(x, y, z) == lastStage
                    && cropPlantable(ph, x, y, z)) {
              stage.set(x, y, z);
                lastStage = stage;
            } else {
                if (i == 0) {
                  BlockList.BLOCK_AIR.set(x, y, z);
                }
                return;
            }
            i++;
        }
    }

    public static boolean deepPlantable(PointerHandler ph, final int x, final int y, final int z) {
        boolean val = blockIsGrassSnowOrDirt(ph, VoxelGame.getWorld().getBlock(x, y + 1, z))
                && blockIsGrassSnowOrDirt(ph, VoxelGame.getWorld().getBlock(x, y + 2, z));
        return val;
    }

    public static boolean cropPlantable(PointerHandler ph, final int x, final int y, final int z) {
        return VoxelGame.getWorld().getBlock(x, y + 1, z) == GameItems.BLOCK_FARMLAND;
    }

    public static boolean plantable(PointerHandler ph, final int x, final int y, final int z) {
        return blockIsGrassSnowOrDirt(ph, VoxelGame.getWorld().getBlock(x, y + 1, z));
    }

    public static boolean blockIsGrassSnowOrDirt(PointerHandler ph, Block block) {
        return block == GameItems.BLOCK_FARMLAND
                || block == GameItems.BLOCK_DIRT
                || block == GameItems.BLOCK_GRASS
                || block == GameItems.BLOCK_SNOW
                || block == GameItems.BLOCK_JUNGLE_GRASS
                || block == GameItems.BLOCK_DRY_GRASS;
    }
}
