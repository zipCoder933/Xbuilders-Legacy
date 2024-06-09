/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.wallItems;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.engine.world.blockData.BlockData;

/**
 *
 * @author zipCoder933
 */
public class Ladder extends Block {

    public Ladder(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    private boolean isSolid2(int x, int y, int z) {
        return VoxelGame.getWorld().getBlock(x, y, z).isSolid();
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        if (orientation != null) {
            int xzOrientation = orientation.getXZ();
            if ((xzOrientation == 0 && isSolid2(x, y, z - 1))
                    || (xzOrientation == 2 && isSolid2(x, y, z + 1))
                    || (xzOrientation == 1 && isSolid2(x + 1, y, z))
                    || (xzOrientation == 3 && isSolid2(x - 1, y, z))) {
                this.set(x, y, z, orientation);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
