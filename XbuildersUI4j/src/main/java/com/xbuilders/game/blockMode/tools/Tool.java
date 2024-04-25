package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.game.blockMode.BulkBlockSetter;
import com.xbuilders.game.blockMode.SettingUtils;
import com.xbuilders.window.BaseWindow;
import processing.core.PGraphics;
import processing.event.KeyEvent;

public abstract class Tool {
    public final String name;
    public final SettingUtils setting;
    public final BulkBlockSetter blockSetter;
    public final BlockTools blockTools;

    public Tool(String name, BlockTools blockTools) {
        this.name = name;
        this.blockTools = blockTools;
        this.setting = blockTools.setting;
        this.blockSetter = blockTools.blockSetter;
    }

    public abstract boolean shouldActivate(BaseWindow window, KeyEvent ke);

    public void activate() {
    }

    public void deactivate() {
    }

    public abstract boolean setBlock(ItemQuantity item, final CursorRaycast ray, final BlockData data, boolean isCreationMode);

    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        return false;
    }
}
