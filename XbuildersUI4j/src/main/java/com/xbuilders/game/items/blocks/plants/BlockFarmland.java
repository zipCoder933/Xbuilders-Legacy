// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.blocks.plants;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

public class BlockFarmland extends Plant {

    public BlockFarmland() {
        super(452, "Farmland hidden");
        this.texture = new BlockTexture();
        this.texture.setTOP(6, 1);
        this.texture.setBOTTOM(2, 0);
        this.texture.setSIDES(2, 0);
    }

}
