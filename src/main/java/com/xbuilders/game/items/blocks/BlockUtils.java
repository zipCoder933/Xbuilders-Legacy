/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks;

import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class BlockUtils {

    public static boolean isFlammable(Block block) {
        String blockName = block.name.toLowerCase();
        return blockName.contains("planks")
                || blockName.contains("wood")
                || blockName.contains("log")
                || blockName.contains("oak")
                || blockName.contains("grass")
                || blockName.contains("birch")
                || blockName.contains("acacia")
                || blockName.contains("jungle")
                || blockName.contains("dry");
    }

    public static boolean lightBlockOnFire(PointerHandler ph, int x, int y, int z) {
        Block b1 = ph.getWorld().getBlock(x, y, z);
        if (isFlammable(b1)) {
            if (b1.getRenderType() == BlockRenderType.SPRITE) {
               GameItems.CAMPFIRE.set(x, y, z);
                return true;
            } else if (ph.getWorld().getBlock(x, y - 1, z).isAir()) {
                GameItems.CAMPFIRE.set(x, y - 1, z);
                return true;
            }
        }
        return false;
    }

    public static boolean isNextToBlock(int x, int y, int z, Block block) {
        if (VoxelGame.getWorld().getBlock(x, y + 1, z) == block) {
            return true;
        } else if (VoxelGame.getWorld().getBlock(x, y - 1, z) == block) {
            return true;
        } else if (VoxelGame.getWorld().getBlock(x + 1, y, z) == block) {
            return true;
        } else if (VoxelGame.getWorld().getBlock(x - 1, y, z) == block) {
            return true;
        } else if (VoxelGame.getWorld().getBlock(x, y, z + 1) == block) {
            return true;
        } else if (VoxelGame.getWorld().getBlock(x, y, z - 1) == block) {
            return true;
        }
        return false;

    }

    public static boolean isGrass(Block block) {
        return block == GameItems.BLOCK_GRASS
                || block == GameItems.BLOCK_SNOW
                || block == GameItems.BLOCK_DRY_GRASS
                || block == GameItems.BLOCK_JUNGLE_GRASS;
    }

    public static boolean isGrassOrDirt(Block block) {
        return isGrass(block) || block == GameItems.BLOCK_DIRT;
    }

    public static Vector3i getNeighborBlock(int x, int y, int z, Block block) {
        Item item5 = VoxelGame.getWorld().getBlock(x + 1, y, z);
        if (item5.id == block.id) {
            return new Vector3i(x + 1, y, z);
        }
        Item item4 = VoxelGame.getWorld().getBlock(x, y + 1, z);
        if (item4.id == block.id) {
            return new Vector3i(x, y + 1, z);
        }
        Item item3 = VoxelGame.getWorld().getBlock(x, y, z + 1);
        if (item3.id == block.id) {
            return new Vector3i(x, y, z + 1);
        }
        Item item2 = VoxelGame.getWorld().getBlock(x - 1, y, z);
        if (item2.id == block.id) {
            return new Vector3i(x - 1, y, z);
        }
        Item item1 = VoxelGame.getWorld().getBlock(x, y - 1, z);
        if (item1.id == block.id) {
            return new Vector3i(x, y - 1, z);
        }
        Item item = VoxelGame.getWorld().getBlock(x, y, z - 1);
        if (item.id == block.id) {
            return new Vector3i(x, y, z - 1);
        }
        return null;
    }

}
