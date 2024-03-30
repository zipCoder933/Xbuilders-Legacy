/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.copyPaste;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import static com.xbuilders.game.items.other.copyPaste.PasteRotateBlock.isNeighboringPasteBlock;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class PasteVertexBlock extends Block {

    public PasteVertexBlock() {
        super(735, "Paste Vertex Block");
        setRenderType(BlockRenderType.SLAB);
        this.texture = new BlockTexture();
        this.texture.setTOP(20, 3);
        this.texture.setSIDES(23, 3);
        this.texture.setBOTTOM(20, 3);
        setIconAtlasPosition(6, 8);
        tags.add("copy");
        tags.add("paste");
    }

    @Override
    public boolean onClickEvent(int setX, int setY, int setZ) {
        BlockPaste.nextOffsetVertex();
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean saveInChunk() {
        return false;
    }

    Vector3i rotateBlock = null;

    @Override
    public void onLocalChange(LocalChangeRecord record) {
        int x = record.getTargetBlockPosition().x;
        int y = record.getTargetBlockPosition().y;
        int z = record.getTargetBlockPosition().z;
        if (isNeighboringPasteBlock(getPointerHandler(), x, y, z) == null) {
            removeBlock();
        }
    }

    private void removeBlock() {
        if (rotateBlock != null) {
            BlockList.BLOCK_AIR.set(rotateBlock);
        }
        rotateBlock = null;
    }

    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        removeBlock();
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (getPointerHandler().getGame().mode != GameMode.FREEPLAY) {
            VoxelGame.getGame().alert("You cannot do that in " + getPointerHandler().getGame().mode + " mode");
            return false;
        }
        BlockOrientation orientation = isNeighboringPasteBlock(getPointerHandler(), x, y, z);
        if (orientation != null) {
            if (rotateBlock == null) {
                rotateBlock = new Vector3i(x, y, z);
            } else {
                BlockList.BLOCK_AIR.set(rotateBlock);
                rotateBlock = new Vector3i(x, y, z);
            }
            this.set(x, y, z, orientation);
        }
        return true;
    }
}
