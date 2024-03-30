package com.xbuilders.game.items.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.tool.Tool;

public class Flashlight extends Tool {

    public Flashlight() {
        super(6, "Flashlight");
        setIconAtlasPosition(6, 6);
    }

    @Override
    public boolean isInfiniteResource() {
        return true;
    }

    @Override
    public byte maxStackSize() {
        return (byte) 1;
    }

    @Override
    public boolean onPlace(int x, int y, int z) {
        return false;
    }

    @Override
    public void onClick() {
        VoxelGame.getShaderHandler().setFlashlightMode(!VoxelGame.getShaderHandler().isFlashlightMode());
    }

    @Override
    public void onDropEvent() {
        VoxelGame.getShaderHandler().setFlashlightMode(false);
    }
}
