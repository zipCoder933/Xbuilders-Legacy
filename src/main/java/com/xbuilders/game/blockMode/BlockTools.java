/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.blockMode.tools.*;
import com.xbuilders.window.BaseWindow;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.ui4j.UIExtensionFrame;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TOP;

/**
 * @author zipCoder933
 */
public class BlockTools {

    public BlockTools(UserControlledPlayer player) {
        blockSetter = new BulkBlockSetter();
        setting = new SettingUtils(this);
        size = 6;
        tools.add(new DefaultTool(this)); //Always the first item on the list
        //-------------------------------------------------------
        tools.add(new LineSet(this));
        tools.add(new BoundarySet(this));
        tools.add(new Fill(this));
        tools.add(new PasteTool(this));
        tools.add(new CopyTool(this));
        tools.add(new SphereTool(this));
    }

    public BulkBlockSetter blockSetter;
    public SettingUtils setting;
    final List<Tool> tools = new ArrayList<>();
    int selectedTool = 0;

    public void keyReleased(BaseWindow window, KeyEvent ke) {
        if (!window.keyIsPressed(KeyCode.SHIFT)
                && VoxelGame.getGame().mode == GameMode.FREEPLAY) {

            if (window.keyIsPressed(KeyCode.M)) {
                getSelectedTool().changeMode();
            } else if (getSelectedTool().keyReleased(window, ke)) {
            } else {
                for (int i = 0; i < tools.size(); i++) {
                    if (tools.get(i).shouldActivate(window, ke)) {
                        setSelectedTool(i);
                        break;
                    }
                }
            }

        }
    }

    public void stop() {
        blockSetter.stop();
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = (int) MathUtils.clamp(size, 1, getSelectedTool().getMaxSize());
    }

    private int size;

    public void resetBlockMode() {
        setSelectedTool(0);
    }

    private void setSelectedTool(int selectedTool) {
        if (this.selectedTool != selectedTool) getSelectedTool().deactivate();
        else getSelectedTool().changeMode();
        if (selectedTool < 0) selectedTool = 0;
        if (selectedTool > tools.size() - 1) selectedTool = tools.size() - 1;
        this.selectedTool = selectedTool;
        getSelectedTool().activate();
    }

    public boolean setBlock(ItemQuantity item, final CursorRaycast ray, final BlockData data
            , boolean isCreationMode) {

//
        return getSelectedTool().setBlock(item, ray, data, isCreationMode);
    }

    public void newGame() {
        resetBlockMode();
        blockSetter.start();
    }

    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        g.pushMatrix();
        boolean result = false;
        try {
            result = getSelectedTool().drawCursor(ray, g);
        } finally {
            g.popMatrix();
        }
        return result;
    }

    public Tool getSelectedTool() {
        return tools.get(selectedTool);
    }


    public void drawGUI(UIExtensionFrame frame) {
        if (VoxelGame.getGame().mode == GameMode.FREEPLAY) {
            String str = "Block Tool: " + getSelectedTool().toolDescription();
            int yOffset = 2;
            int padding = 12;
            frame.textAlign(CENTER, TOP);
            frame.textSize(10);
            frame.noStroke();
            frame.fill(10, 200);
            float boxWidth = frame.textWidth(str) + padding * 2;
            frame.rect((frame.width / 2) - (boxWidth / 2), yOffset, boxWidth, 20 + padding, 2);
            frame.fill(255);
            frame.text(str, frame.width / 2, yOffset + padding - 2);
        }
    }
}
