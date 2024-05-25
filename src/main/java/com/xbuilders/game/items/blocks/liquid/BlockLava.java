package com.xbuilders.game.items.blocks.liquid;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.block.liquid.Liquid;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.BlockUtils;
import java.awt.Color;

public class BlockLava extends Liquid {

    @Override
    public int getAnimationLength() {
        return 32;
    }

    public BlockLava() {
        super(25, "Lava", new BlockTexture(26, 0));
        setFallSpeed(900);
    }

    private boolean nextToWater(int x, int y, int z) {
        if (VoxelGame.getWorld().getBlock(x, y, z) == GameItems.BLOCK_WATER) {
            return true;
        }
        if (VoxelGame.getWorld().getBlock(x + 1, y, z) == GameItems.BLOCK_WATER) {
            return true;
        }
        if (VoxelGame.getWorld().getBlock(x, y + 1, z) == GameItems.BLOCK_WATER) {
            return true;
        }
        if (VoxelGame.getWorld().getBlock(x, y, z + 1) == GameItems.BLOCK_WATER) {
            return true;
        }
        if (VoxelGame.getWorld().getBlock(x - 1, y, z) == GameItems.BLOCK_WATER) {
            return true;
        }
        if (VoxelGame.getWorld().getBlock(x, y - 1, z) == GameItems.BLOCK_WATER) {
            return true;
        }
        if (VoxelGame.getWorld().getBlock(x, y, z - 1) == GameItems.BLOCK_WATER) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isLuminous() {
        return true;
    }

    @Override
    public byte getLightFalloff() {
        return (byte) 4;
    }

    @Override
    public boolean setBlock(int x, int y, int z) {
        BlockUtils.lightBlockOnFire(getPointerHandler(), x + 1, y, z);
        BlockUtils.lightBlockOnFire(getPointerHandler(), x - 1, y, z);
        BlockUtils.lightBlockOnFire(getPointerHandler(), x, y - 1, z);
        BlockUtils.lightBlockOnFire(getPointerHandler(), x, y, z + 1);
        BlockUtils.lightBlockOnFire(getPointerHandler(), x, y, z - 1);

        if (nextToWater(x, y, z)) {
            GameItems.Obsidian.set(x, y, z);
            return false;
        } else {
            if (getPointerHandler().getWorld().getBlock(x, y, z) != GameItems.BlockLava) {
                GameItems.BlockLava.set(x, y, z);
            }
            //<editor-fold defaultstate="collapsed" desc="Convert grass to dirt">
            Block bottomBlock = VoxelGame.getWorld().getBlock(x, y + 1, z);
            if (bottomBlock == GameItems.BLOCK_GRASS
                    || bottomBlock == GameItems.BLOCK_DRY_GRASS
                    || bottomBlock == GameItems.BLOCK_JUNGLE_GRASS
                    || bottomBlock == GameItems.BLOCK_SNOW) {
                GameItems.BLOCK_DIRT.set(x, y + 1, z);
            }
            //</editor-fold>
            return true;
        }
    }

    @Override
    public Color inWaterBackgroundColor() {
        return Color.RED;
    }

}
