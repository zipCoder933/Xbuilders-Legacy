package com.xbuilders.game.items.blocks.electronics.light;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import org.joml.Vector3i;

public class BlockTorch extends Block {

    public BlockTorch() {
        super(20, "Torch");
        this.type = BlockRenderType.TORCH;
        this.texture = new BlockTexture(22, 10);
        setIconAtlasPosition(1, 8);
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
//        System.out.println("Event occured! "+record.toString());
        checkIfNoTorchSupports(record, getPointerHandler().getPlayer());
    }

    public static void checkIfNoTorchSupports(LocalChangeRecord record, UserControlledPlayer player) {
        Vector3i pos = record.getTargetBlockPosition();
        if (!VoxelGame.getWorld().getBlock(pos.x - 1, pos.y, pos.z).isSolid()
                && !VoxelGame.getWorld().getBlock(pos.x + 1, pos.y, pos.z).isSolid()
                && !VoxelGame.getWorld().getBlock(pos.x, pos.y + 1, pos.z).isSolid()
                && !VoxelGame.getWorld().getBlock(pos.x, pos.y, pos.z - 1).isSolid()
                && !VoxelGame.getWorld().getBlock(pos.x, pos.y, pos.z + 1).isSolid()) {
            player.setBlock(BlockList.BLOCK_AIR,null,pos.x, pos.y, pos.z);
        }
    }

    @Override
    public boolean isLuminous() {
        return true;
    }

    @Override
    public int getAnimationLength() {
        return 4;
    }
}
