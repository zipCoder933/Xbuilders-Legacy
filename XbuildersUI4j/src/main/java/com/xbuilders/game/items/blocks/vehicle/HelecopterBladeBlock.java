package com.xbuilders.game.items.blocks.vehicle;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

public class HelecopterBladeBlock extends Block {
    public final int length;

    public HelecopterBladeBlock(int id, String name, int length) {
        super(id, name, new BlockTexture(0, 27, 1, 27));
        tags.add("vehicle");
        this.length = length;
    }


    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
