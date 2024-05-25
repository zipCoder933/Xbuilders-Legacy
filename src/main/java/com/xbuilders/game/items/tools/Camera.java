package com.xbuilders.game.items.tools;

import com.xbuilders.engine.items.tool.Tool;

public class Camera extends Tool {

    public Camera() {
        super(4, "Camera");
        setIconAtlasPosition(5, 6);
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
        getPointerHandler().getGame().takeScreenshot();
    }

}
