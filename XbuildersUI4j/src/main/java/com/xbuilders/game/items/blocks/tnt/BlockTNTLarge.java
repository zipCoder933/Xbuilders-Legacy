package com.xbuilders.game.items.blocks.tnt;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.game.items.GameItems;

public class BlockTNTLarge extends Block {

    public BlockTNTLarge() {
        super(548, "Mega TNT");
        BlockTexture multiTex = new BlockTexture();
        multiTex.setSIDES(17, 0);
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

    final int EXPLOSTION_RADIUS = 7;

    @Override
    public boolean onClickEvent(final int setX, final int setY, final int setZ) {
        (new BlockAction(getPointerHandler(), setX, setY, setZ) {
            @Override
            public void preformAction() throws Exception {
                TNTUtils.startTNT(getPointerHandler(), GameItems.BLOCK_TNT_LARGE, EXPLOSTION_RADIUS, 6000,  setX, setY, setZ);
            }

            @Override
            public void onInterrupt() throws Exception {
            }
        }).startAction();
        return false;
    }

}
