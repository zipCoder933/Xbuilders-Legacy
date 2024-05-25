package com.xbuilders.game.items.blocks.plants.growablePlants;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.game.items.blocks.plants.Plant;

public class BlockPotatoes extends Plant {

    public BlockPotatoes() {
        super(146, "Potatoes hidden<PLANT>");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(14,6);

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
