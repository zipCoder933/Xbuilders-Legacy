package com.xbuilders.game.items.tools;

import com.xbuilders.engine.items.tool.Tool;

public class Saddle extends Tool {

    public Saddle() {
        super(1, "Saddle");
        setIconAtlasPosition(3, 6);
    }

    /**
     *
     *
     * @return If a single item should run out gradually instead of an item
     * getting used up entirely once per click
     */
    @Override
    public boolean continuousUse() {
        return false;
    }

    @Override
    public byte maxStackSize() {
        return (byte) 1;
    }

    @Override
    public boolean onPlace(int x, int y, int z) {
        return false;
    }

}
