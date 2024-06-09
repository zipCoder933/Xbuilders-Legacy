package com.xbuilders.game.items.blocks.vehicle;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.blockType.BlockRenderType;

public class Engine extends Block {
   public final int horsepower;

    public Engine(int id, int horsepower) {
        super(id, horsepower + " HP Engine",
                new BlockTexture(0, 24,
                        1, 24,
                        0, 25,
                        0, 25,
                        1, 25,
                        1, 25), true, BlockRenderType.ORIENTABLE_BLOCK);

        this.horsepower = horsepower;
        setIconAtlasPosition(1, 9);
        tags.add("vehicle");
    }

    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Ray ray) {
        BlockData data = new BlockData(2);
        data.set(0, (byte) player.camera.simplifiedPanTilt.x);
        data.set(1, (byte) 0);
        return data;
    }
}


