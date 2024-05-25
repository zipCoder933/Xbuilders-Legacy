package com.xbuilders.game.items.blocks.liquid;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.block.liquid.Liquid;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.game.items.GameItems;
import java.awt.Color;

public class BlockWater extends Liquid {

    public BlockWater() {
        super(21, "Water", new BlockTexture(24, 0));
    }

    @Override
    public boolean setBlock(int x, int y, int z) {
        GameItems.BLOCK_WATER.set(x, y, z);
        checkNeighborsAndCoolLava(x, y, z, getPointerHandler().getPlayer());
        return true;
    }

    @Override
    public boolean isPenetrableCustom(Block block) {
        if (block.getRenderType() == BlockRenderType.SPRITE) {
            if (block.name.toLowerCase().equals("sea grass")) return false;
            return !block.name.toLowerCase().contains("coral");
        } else {
            return true;
        }
    }

    @Override
    public int getAnimationLength() {
        return 16;
    }

    private void coolLavaAtVoxel(int x, int y, int z, UserControlledPlayer p) {
        p.setBlock(BlockList.BLOCK_AIR,null,(int) x, (int) y, (int) z);
        GameItems.Obsidian.set(x, y, z);
    }

    private void checkNeighborsAndCoolLava(int x, int y, int z, UserControlledPlayer p) {
        if (getPointerHandler().getWorld().getBlock(x, y, z) == GameItems.BlockLava) {
            coolLavaAtVoxel(x, y, z, p);
        }
        if (getPointerHandler().getWorld().getBlock(x + 1, y, z) == GameItems.BlockLava) {
            coolLavaAtVoxel(x + 1, y, z, p);
        }
        if (getPointerHandler().getWorld().getBlock(x, y + 1, z) == GameItems.BlockLava) {
            coolLavaAtVoxel(x, y + 1, z, p);
        }
        if (getPointerHandler().getWorld().getBlock(x, y, z + 1) == GameItems.BlockLava) {
            coolLavaAtVoxel(x, y, z + 1, p);
        }
        if (getPointerHandler().getWorld().getBlock(x - 1, y, z) == GameItems.BlockLava) {
            coolLavaAtVoxel(x - 1, y, z, p);
        }
        if (getPointerHandler().getWorld().getBlock(x, y - 1, z) == GameItems.BlockLava) {
            coolLavaAtVoxel(x, y - 1, z, p);
        }
        if (getPointerHandler().getWorld().getBlock(x, y, z - 1) == GameItems.BlockLava) {
            coolLavaAtVoxel(x, y, z - 1, p);
        }
    }

    private static final Color darkWater = new Color(51, 73, 128);
    private static final Color lightWater = new Color(23, 47, 83);



    @Override
    public Color inWaterBackgroundColor() {
        return MiscUtils.mixColors(darkWater, lightWater, VoxelGame.getShaderHandler().defaultSunlightLevel);
    }

}
