/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.electronics;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.blockData.BlockData;

/**
 *
 * @author zipCoder933
 */
public class ElectronicDevice extends Block {

    /**
     * @return the orientable
     */
    public boolean isOrientable() {
        return orientable;
    }

    /**
     * @param orientable the orientable to set
     */
    public void setOrientable(boolean orientable) {
        this.orientable = orientable;
    }

    public ElectronicDevice(int id, String name) {
        super(id, name);
        tags.add("electronic");
    }

    private boolean orientable = false;

    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Ray ray) {
        if (isOrientable()) {
            BlockData data = new BlockData(4);
            data.bytes[2] = (byte) player.cameraBlockOrientation().getXZ();
            data.bytes[3] = (byte) player.cameraBlockOrientation().getY();
            return data;
        } else {
            return new BlockData(2);
        }
    }
}
