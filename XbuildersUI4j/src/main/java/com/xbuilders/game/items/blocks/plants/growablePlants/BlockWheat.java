package com.xbuilders.game.items.blocks.plants.growablePlants;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.game.items.blocks.plants.Plant;

public class BlockWheat extends Plant {

    public BlockWheat(){
        super(121,"Wheat hidden");
        this.type = BlockRenderType.SPRITE;
        this.texture = new BlockTexture(15,5);

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
