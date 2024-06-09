package com.xbuilders.game.items.blocks.plants;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.world.blockData.BlockData;

import static com.xbuilders.engine.items.BlockList.BLOCK_AIR;

public class Plant extends Block {

    public Plant(int id, String name) {
        super(id, name);
    }

    public Plant(int id, String name, boolean solid, boolean opaque) {
        super(id, name, solid, opaque);
    }

    public Plant(int id, String name, boolean solid, boolean opaque, int renderType) {
        super(id, name, solid, opaque, renderType);
    }

    public Plant(int id, String name, BlockTexture texture, boolean solid, boolean opaque) {
        super(id, name, texture, solid, opaque);
    }

    public Plant(int id, String name, BlockTexture texture, int renderType) {
        super(id, name, texture, renderType);
    }

    public Plant(int id, String name, BlockTexture texture) {
        super(id, name, texture);
    }

    public Plant(int id, String name, BlockTexture texture, boolean solid, boolean opaque, int renderType) {
        super(id, name, texture, solid, opaque, renderType);
    }

    public Plant(int id, String name, BlockTexture texture, boolean opaque, int renderType) {
        super(id, name, texture, opaque, renderType);
    }

    public Plant(int id, String name, BlockTexture texture, boolean opaque) {
        super(id, name, texture, opaque);
    }

    public Plant(int id, String name, int iconAtlasX, int iconAtlasY, boolean solid, boolean opaque, int renderType) {
        super(id, name, iconAtlasX, iconAtlasY, solid, opaque, renderType);
    }

    public Plant(int id, String name, int iconAtlasX, int iconAtlasY, BlockTexture texture, boolean solid, boolean opaque, int renderType) {
        super(id, name, iconAtlasX, iconAtlasY, texture, solid, opaque, renderType);
    }

    public Plant(int id, String name, BlockTexture texture, boolean solid, boolean opaque, boolean luminous, byte falloff, int animationLength, int renderType) {
        super(id, name, texture, solid, opaque, luminous, falloff, animationLength, renderType);
    }

    public Plant(int id, String name, BlockTexture texture, boolean luminous, byte falloff, int animationLength, int renderType) {
        super(id, name, texture, luminous, falloff, animationLength, renderType);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void onLocalChange(LocalChangeRecord record) {
        //Delete this block if there is nothing under it
        Block below = VoxelGame.getWorld().getBlock(
                record.getTargetBlockPosition().x,
                record.getTargetBlockPosition().y + 1,
                record.getTargetBlockPosition().z);

        if (below == BlockList.BLOCK_AIR || below.isLiquid()) {
            BLOCK_AIR.set(record.getTargetBlockPosition().x, record.getTargetBlockPosition().y, record.getTargetBlockPosition().z);
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        Block below = VoxelGame.getWorld().getBlock(
                x, y + 1, z);

        if (below != BlockList.BLOCK_AIR && !below.isLiquid()) {
            this.set(x, y, z, data);
            return true;
        }
        return false;
    }
}
