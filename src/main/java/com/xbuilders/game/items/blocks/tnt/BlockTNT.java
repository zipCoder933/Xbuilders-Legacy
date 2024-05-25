package com.xbuilders.game.items.blocks.tnt;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.GameItems;

public class BlockTNT extends Block {

    public BlockTNT() {
        super(120, "TNT");
        BlockTexture multiTex = new BlockTexture();
        multiTex.setSIDES(8, 0);
        multiTex.setTOP(9, 0);
        multiTex.setBOTTOM(10, 0);
        this.texture = multiTex;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    int EXPLOSTION_RADIUS = 4;

    @Override
    public boolean onClickEvent(final int setX, final int setY, final int setZ) {
        (new BlockAction(getPointerHandler(), setX, setY, setZ) {
            @Override
            public void preformAction() throws Exception {
                TNTUtils.startTNT(getPointerHandler(), GameItems.BLOCK_TNT, EXPLOSTION_RADIUS, 3000, setX, setY, setZ);
            }

            @Override
            public void onInterrupt() throws Exception {
            }
        }).startAction();
        return false;
    }

}
