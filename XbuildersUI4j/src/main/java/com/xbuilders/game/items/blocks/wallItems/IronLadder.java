// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.blocks.wallItems;

import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.blockType.BlockRenderType;

public class IronLadder extends Ladder {

    public IronLadder() {
        super(214, "Iron Ladder");
        this.type = BlockRenderType.WALL_ITEM;
        this.texture = new BlockTexture(3, 6);
    }

}
