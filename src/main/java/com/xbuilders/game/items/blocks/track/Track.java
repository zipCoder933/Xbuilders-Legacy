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
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.entities.vehicle.minecart.MinecartUtils;
import java.util.ArrayList;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class Track extends Block {

    public Track() {
        super(155, "Track");
        setRenderType(BlockRenderType.FLOOR);
        this.texture = new BlockTexture(3, 9);
    }

    private void addNeighbor(ArrayList<Vector3i> trackPositions, int x2, int y2, int z2) {
        Block b = getPointerHandler().getWorld().getBlock(x2, y2, z2);
        if (MinecartUtils.isTrack(b)) {
            trackPositions.add(new Vector3i(x2, y2, z2));
        }
    }

    private boolean changeBlock(int x, int y, int z) {
        ArrayList<Vector3i> trackPositions = new ArrayList<>();
        BlockOrientation orientation = new BlockOrientation((byte) 0, (byte) 0);
        addNeighbor(trackPositions, x + 1, y, z);
        addNeighbor(trackPositions, x - 1, y, z);
        addNeighbor(trackPositions, x, y, z + 1);
        addNeighbor(trackPositions, x, y, z - 1);

        addNeighbor(trackPositions, x + 1, y + 1, z);
        addNeighbor(trackPositions, x - 1, y + 1, z);
        addNeighbor(trackPositions, x, y + 1, z + 1);
        addNeighbor(trackPositions, x, y + 1, z - 1);

        addNeighbor(trackPositions, x + 1, y - 1, z);
        addNeighbor(trackPositions, x - 1, y - 1, z);
        addNeighbor(trackPositions, x, y - 1, z + 1);
        addNeighbor(trackPositions, x, y - 1, z - 1);

        boolean curvedTrack = false;
        boolean straightTrack = false;

        if (!trackPositions.isEmpty()) {
            if (isTrackAtPos2(trackPositions, x - 1, y, z) && isTrackAtPos2(trackPositions, x, y, z - 1)) {
                orientation.setXZ((byte) 2);
                GameItems.CURVED_TRACK.set(x, y, z, orientation);
                curvedTrack = true;
                return true;
            } else if (isTrackAtPos2(trackPositions, x + 1, y, z) && isTrackAtPos2(trackPositions, x, y, z - 1)) {
                orientation.setXZ((byte) 3);
                GameItems.CURVED_TRACK.set(x, y, z, orientation);
                curvedTrack = true;
                return true;
            } else if (isTrackAtPos2(trackPositions, x - 1, y, z) && isTrackAtPos2(trackPositions, x, y, z + 1)) {
                orientation.setXZ((byte) 1);
                GameItems.CURVED_TRACK.set(x, y, z, orientation);
                curvedTrack = true;
                return true;
            } else if (isTrackAtPos2(trackPositions, x + 1, y, z) && isTrackAtPos2(trackPositions, x, y, z + 1)) {
                orientation.setXZ((byte) 0);
                GameItems.CURVED_TRACK.set(x, y, z, orientation);
                curvedTrack = true;
                return true;
            } //=====================
            else if (isTrackAtPos2(trackPositions, x - 1, y, z) || isTrackAtPos2(trackPositions, x + 1, y, z)) {
                orientation.setXZ((byte) 1);
                straightTrack = true;
                this.set(x, y, z, orientation);
            } else if (isTrackAtPos2(trackPositions, x, y, z - 1) || isTrackAtPos2(trackPositions, x, y, z + 1)) {
                orientation.setXZ((byte) 0);
                this.set(x, y, z, orientation);
                straightTrack = true;
            }
        }
        if (!curvedTrack) {
            if (isTrackAtPos(trackPositions, x + 1, y - 1, z)) {
                orientation.setXZ((byte) 1);
                GameItems.RAISED_TRACK.set(x, y, z, orientation);
                return true;
            } else if (isTrackAtPos(trackPositions, x - 1, y - 1, z)) {
                orientation.setXZ((byte) 3);
                GameItems.RAISED_TRACK.set(x, y, z, orientation);
                return true;
            } else if (isTrackAtPos(trackPositions, x, y - 1, z + 1)) {
                orientation.setXZ((byte) 2);
                GameItems.RAISED_TRACK.set(x, y, z, orientation);
                return true;
            } else if (isTrackAtPos(trackPositions, x, y - 1, z - 1)) {
                orientation.setXZ((byte) 0);
                GameItems.RAISED_TRACK.set(x, y, z, orientation);
                return true;
            }
        }
        return straightTrack;
    }

    public static boolean isNotSecure(PointerHandler ph, int x, int y, int z) {
        Block b = ph.getWorld().getBlock(x, y + 1, z);
        return (MinecartUtils.isTrack(b) || !b.isSolid());
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (isNotSecure(getPointerHandler(), x, y, z)) {
            return false;
        }
        if (!changeBlock(x, y, z)) {
            this.set(x, y, z, data);
            return true;
        }
        return false;
    }

    @Override
    public void onLocalChange(LocalChangeRecord record) {
        int x = record.getTargetBlockPosition().x;
        int y = record.getTargetBlockPosition().y;
        int z = record.getTargetBlockPosition().z;
        if (isNotSecure(getPointerHandler(), x, y, z)) {
            BlockList.BLOCK_AIR.set(x, y, z);
        } else {
            changeBlock(x, y, z);
        }
    }

    private boolean isTrackAtPos(ArrayList<Vector3i> trackPositions, int x, int y, int z) {
        return trackPositions.contains(new Vector3i(x, y, z));
    }

    private boolean isTrackAtPos2(ArrayList<Vector3i> trackPositions, int x, int y, int z) {
        return trackPositions.contains(new Vector3i(x, y, z))
                || trackPositions.contains(new Vector3i(x, y + 1, z))
                || trackPositions.contains(new Vector3i(x, y - 1, z));
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
