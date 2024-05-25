// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.items.blocks.electronics.light;

import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import static com.xbuilders.game.items.blocks.electronics.light.BlockTorch.checkIfNoTorchSupports;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;

public class BlockBlueLamp extends Block {

    public BlockBlueLamp() {
        super(525, "Blue Lamp");
        this.type = BlockRenderType.LAMP;
        this.texture = new BlockTexture(9, 12);
    }

    @Override
    public void onLocalChange(LocalChangeRecord record) {
        checkIfNoTorchSupports(record, getPointerHandler().getPlayer());
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isLuminous() {
        return true;
    }

}
