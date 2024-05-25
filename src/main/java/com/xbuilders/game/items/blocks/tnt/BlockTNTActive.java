package com.xbuilders.game.items.blocks.tnt;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;

public class BlockTNTActive extends Block {

    public BlockTNTActive() {
        super(29, "TNT Active hidden");
        BlockTexture multiTex = new BlockTexture();
        multiTex.setSIDES(18, 16);
        multiTex.setTOP(19, 16);
        multiTex.setBOTTOM(20, 16);
        this.texture = multiTex;
    }

    @Override
    public boolean isLuminous() {
        return true;
    }

    @Override
    public boolean saveInChunk() {
        return false;
    }

    @Override
    public byte getLightFalloff() {
        return (byte) 8;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public int getAnimationLength() {
        return 2;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

}
