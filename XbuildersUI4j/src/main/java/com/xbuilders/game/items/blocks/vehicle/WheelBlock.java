package com.xbuilders.game.items.blocks.vehicle;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.blockType.BlockRenderType;

public class WheelBlock extends Block {

    public WheelBlock(int id, String name, BlockTexture texture, int renderType) {
        super(id, name, texture, false, renderType);
    }
}
