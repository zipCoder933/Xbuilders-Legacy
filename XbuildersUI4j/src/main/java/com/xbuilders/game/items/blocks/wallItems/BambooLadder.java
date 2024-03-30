// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.blocks.wallItems;

import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.blockType.BlockRenderType;

public class BambooLadder extends Ladder {

    public BambooLadder() {
        super(248, "Bamboo Ladder");
        this.type = BlockRenderType.WALL_ITEM;
        this.texture = new BlockTexture(2, 5);
    }

}
