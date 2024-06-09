/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.track;

import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import static com.xbuilders.game.items.blocks.track.Track.isNotSecure;
import com.xbuilders.game.items.entities.vehicle.minecart.MinecartUtils;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class StopTrack extends Block {

    public StopTrack() {
        super(286, "Track Stop");
        setRenderType(BlockRenderType.FLOOR);
        this.texture = new BlockTexture(3, 11);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    private boolean isTrackAtPos(int x, int y, int z) {
        Block a = getPointerHandler().getWorld().getBlock(new Vector3i(x, y, z));
        Block b = getPointerHandler().getWorld().getBlock(new Vector3i(x, y - 1, z));
        Block c = getPointerHandler().getWorld().getBlock(new Vector3i(x, y + 1, z));
        return MinecartUtils.isTrack(a) || MinecartUtils.isTrack(b) || MinecartUtils.isTrack(c);
    }

    @Override
    public void onLocalChange(LocalChangeRecord record) {
        int x = record.getTargetBlockPosition().x;
        int y = record.getTargetBlockPosition().y;
        int z = record.getTargetBlockPosition().z;
        if (isNotSecure(getPointerHandler(), x, y, z)) {
            BlockList.BLOCK_AIR.set(x, y, z);
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (isNotSecure(getPointerHandler(), x, y, z)) {
            return false;
        }
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        if (orientation == null) {
            orientation = new BlockOrientation((byte) 0, (byte) 0);
        }

        if (isTrackAtPos(x - 1, y, z) || isTrackAtPos(x + 1, y, z)) {
            orientation.setXZ((byte) 1);
            this.set(x, y, z, orientation);
            return true;
        } else if (isTrackAtPos(x, y, z - 1) || isTrackAtPos(x, y, z + 1)) {
            orientation.setXZ((byte) 0);
            this.set(x, y, z, orientation);
            return true;
        } else {
            this.set(x, y, z, data);
            return true;
        }
    }

}
