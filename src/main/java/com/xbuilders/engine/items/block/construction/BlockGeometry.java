package com.xbuilders.engine.items.block.construction;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.blockData.BlockData;

public class BlockGeometry {

    public static BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        if (!block.isAir()) {
            BlockData data = block.getInitialBlockData(player, ray);
            if (data != null) {
                return data;
            }
            return ItemList.blocks.getBlockType(block.type).getInitialBlockData(player, block, ray);
        }
        return null;
    }

}
