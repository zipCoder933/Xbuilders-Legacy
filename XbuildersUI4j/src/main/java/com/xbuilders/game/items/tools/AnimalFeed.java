package com.xbuilders.game.items.tools;

import com.xbuilders.engine.items.tool.Tool;

public class AnimalFeed extends Tool {

    public AnimalFeed() {
        super(0, "Animal Feed");
        setIconAtlasPosition(2, 6);
    }

    @Override
    public boolean onPlace(int x, int y, int z) {
        return false;
    }

}
