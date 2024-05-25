// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.game.items.blocks;

import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.block.Block;

public class BlockCraftingTable extends Block
{
    public BlockCraftingTable() {
        super(106, "Workbench Table");
        (this.texture = new BlockTexture()).setSIDES(12, 3);
        this.texture.setTOP(11, 2);
        this.texture.setBOTTOM(9, 1);
    }
}
