package com.xbuilders.game.items.blocks.electronics.light;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blocks.BlockUtils;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import org.joml.Vector3i;

public class BlockCampfire extends Block {

    public BlockCampfire() {
        super(457, "Campfire");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(29, 0);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void onLocalChange(LocalChangeRecord record) {
        Vector3i pos = record.getTargetBlockPosition();
        Block bottomBlock = VoxelGame.getWorld().getBlock(pos.x, pos.y + 1, pos.z);
        if (!bottomBlock.isSolid()) {
            getPointerHandler().getPlayer().setBlock(BlockList.BLOCK_AIR,null,pos.x, pos.y, pos.z);
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData orientation) {
        if (BlockUtils.lightBlockOnFire(getPointerHandler(), x, y, z)
                || BlockUtils.lightBlockOnFire(getPointerHandler(), x - 1, y, z)
                || BlockUtils.lightBlockOnFire(getPointerHandler(), x + 1, y, z)
                || BlockUtils.lightBlockOnFire(getPointerHandler(), x, y - 1, z)
                || BlockUtils.lightBlockOnFire(getPointerHandler(), x, y + 1, z)
                || BlockUtils.lightBlockOnFire(getPointerHandler(), x, y, z - 1)
                || BlockUtils.lightBlockOnFire(getPointerHandler(), x, y, z + 1)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isLuminous() {
        return true;
    }

    @Override
    public int getAnimationLength() {
        return 7;
    }
}
