package com.xbuilders.game.items.blocks.plants.growablePlants;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.game.items.blocks.plants.Plant;

public class BlockCarrots extends Plant {

    public BlockCarrots() {
        super(122, "Carrots hidden <PLANT>");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(13,6);
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
