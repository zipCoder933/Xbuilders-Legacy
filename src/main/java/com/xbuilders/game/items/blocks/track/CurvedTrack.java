/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.track;

import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.blockData.BlockData;
import static com.xbuilders.game.items.blocks.track.Track.isNotSecure;

/**
 *
 * @author zipCoder933
 */
public class CurvedTrack extends Block {

    public CurvedTrack() {
        super(245, "Curved Track hidden");
        setRenderType(BlockRenderType.FLOOR);
        this.texture = new BlockTexture(3, 8);
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
    public void onLocalChange(LocalChangeRecord record) {
        int x = record.getTargetBlockPosition().x;
        int y = record.getTargetBlockPosition().y;
        int z = record.getTargetBlockPosition().z;
        if (isNotSecure(getPointerHandler(), x, y, z)) {
            BlockList.BLOCK_AIR.set(x, y, z);
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (isNotSecure(getPointerHandler(), x, y, z)) {
            return false;
        }
        this.set(x, y, z, data);
        return true;
    }

}
