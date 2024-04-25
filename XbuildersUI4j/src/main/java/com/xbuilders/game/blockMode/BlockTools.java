/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.game.blockMode.tools.BoundarySet;
import com.xbuilders.game.blockMode.tools.DefaultTool;
import com.xbuilders.game.blockMode.tools.LineSet;
import com.xbuilders.game.blockMode.tools.Tool;
import com.xbuilders.game.items.other.copyPaste.BlockPaste;
import com.xbuilders.window.BaseWindow;
import processing.core.KeyCode;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TOP;

import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.ui4j.UIExtensionFrame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zipCoder933
 */
public class BlockTools {

    public BulkBlockSetter blockSetter;
    public SettingUtils setting;
    final List<Tool> tools = new ArrayList<>();
    int selectedTool = 0;

    public void keyReleased(BaseWindow window, KeyEvent ke) {
        if (!window.keyIsPressed(KeyCode.SHIFT)
                && VoxelGame.getGame().mode == GameMode.FREEPLAY) {

            if (window.keyIsPressed(KeyCode.V)) {
                BlockPaste.nextOffsetVertex();
            } else if (window.keyIsPressed(KeyCode.R)) {
                BlockPaste.rotatePasteBox();
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

    public BlockTools(UserControlledPlayer player) {
        blockSetter = new BulkBlockSetter();
        setting = new SettingUtils(this);
        size = 6;
        tools.add(new DefaultTool(this)); //Always the first item on the list
        //-------------------------------------------------------
        tools.add(new LineSet(this));
        tools.add(new BoundarySet(this));
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
        this.size = size;
    }

    private int size;

    public void resetBlockMode() {
        setSelectedTool(0);
    }

    private void setSelectedTool(int selectedTool) {
        getSelectedTool().deactivate();
        if (selectedTool < 0) selectedTool = 0;
        if (selectedTool > tools.size() - 1) selectedTool = tools.size() - 1;
        this.selectedTool = selectedTool;
        getSelectedTool().activate();
    }

    public boolean setBlock(ItemQuantity item, final CursorRaycast ray, final BlockData data
            , boolean isCreationMode) {


//        switch (mode) {
//            case LINE -> {
//
//                return true;
//            }
//            case PLANE -> {
//                (new Thread() {
//                    @Override
//                    public void run() {
//                        setting.setFloor(item, ray.cursorRay, timeSinceStart, size, data);
//                        watch.calculateElapsedTime();
//                        System.out.println("Finished creating block plane.\tTime elapsed: " + watch.toString());
//                        blockSetter.wakeUp();
//                    }
//                }).start();
//                return true;
//            }
//            case REPAINT -> {
//                (new Thread() {
//                    @Override
//                    public void run() {
//                        setting.repaint(item, ray.cursorRay, timeSinceStart, size, data);
//                        watch.calculateElapsedTime();
//                        System.out.println("Finished repainting.\tTime elapsed: " + watch.toString());
//                        blockSetter.wakeUp();
//                    }
//                }).start();
//                return true;
//            }
//            case ERASE_BLOCK -> {
//                (new Thread() {
//                    @Override
//                    public void run() {
//                        setting.erase(ray.cursorRay, timeSinceStart, size);
//                        watch.calculateElapsedTime();
//                        System.out.println("Finished erasing.\tTime elapsed: " + watch.toString());
//                        blockSetter.wakeUp();
//                    }
//                }).start();
//                return true;
//            }
//            case ERASE_LINE -> {
//                setting.eraseLine(ray.cursorRay, timeSinceStart, size);
//                return true;
//            }
//            case SPHERE -> {
//                setting.sphere(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case HOLLOW_SPHERE -> {
//                setting.hollowSphere(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case CENTERED_SPHERE -> {
//                setting.centeredSphere(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case CENTERED_HOLLOW_SPHERE -> {
//                setting.centeredHollowSphere(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case BOUNDARY_CREATE -> {
//                setting.boundaryCreate(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case HOLLOW_BOUNDARY_CREATE -> {
//                setting.hollowBoundaryCreate(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case WALL_BOUNDARY_CREATE -> {
//                setting.wallBoundaryCreate(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case BOUNDARY_DELETE -> {
//                setting.boundaryDelete(ray.cursorRay.getHitPosPlusNormal());
//                return true;
//            }
//            case COPY -> {
//                GameItems.START_BOUNDARY.place(
//                        ray.cursorRay.getHitPosPlusNormal().x,
//                        ray.cursorRay.getHitPosPlusNormal().y,
//                        ray.cursorRay.getHitPosPlusNormal().z, GameItems.COPY_TOOL.boundarySetEvent);
//                return true;
//            }
//            case PASTE -> {
//                GameItems.BLOCK_PASTE.setBlock(
//                        ray.cursorRay.getHitPosPlusNormal().x,
//                        ray.cursorRay.getHitPosPlusNormal().y,
//                        ray.cursorRay.getHitPosPlusNormal().z, null);
//                return true;
//            }
//            case ADDITIVE_PASTE -> {
//                GameItems.ADDITIVE_PASTE.setBlock(
//                        ray.cursorRay.getHitPosPlusNormal().x,
//                        ray.cursorRay.getHitPosPlusNormal().y,
//                        ray.cursorRay.getHitPosPlusNormal().z, null);
//                return true;
//            }
//        }
        return getSelectedTool().setBlock(item, ray, data, isCreationMode);
    }

    public void newGame() {
        resetBlockMode();
        blockSetter.start();
    }

    private boolean isRemovalMode() {
        return false;
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
//        if (VoxelGame.getGame().mode == GameMode.WALKTHOUGH) {
//            resetBlockMode();
//        }
//        if (ray.hitTarget() && mode != null && mode != Mode.DEFAULT) {
//            g.strokeWeight(1.5f);
//            g.noFill();
//            if (isRemovalMode()) {
//                g.stroke(255, 50, 0);
//            } else {
//                g.stroke(255);
//            }
//            BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
//            float add = 0f;
//
//           else if (mode == Mode.PLANE) {
//                g.translate(ray.cursorRay.hitNormal.x, ray.cursorRay.hitNormal.y, ray.cursorRay.hitNormal.z);
//                if (ray.cursorRay.hitNormal.y == 0 && orientation.getY() == 0) {
//                    if (orientation.getXZ() == 0 || orientation.getXZ() == 2) {
//                        g.box((size * 2) - 1f, (size * 2) - 1f, 1 + add);
//                    } else {
//                        g.box(1 + add, (size * 2) - 1f, (size * 2) - 1f);
//                    }
//                } else {
//                    g.box((size * 2) - 1f, 1 + add, (size * 2) - 1f);
//                }
//            } else if (mode == Mode.REPAINT || mode == Mode.ERASE_BLOCK
//                    || mode == Mode.CENTERED_HOLLOW_SPHERE
//                    || mode == Mode.CENTERED_SPHERE) {
//                g.translate(ray.cursorRay.hitNormal.x, ray.cursorRay.hitNormal.y, ray.cursorRay.hitNormal.z);
//                g.box((size * 2) - 1f, (size * 2) - 1f, (size * 2) - 1f);
//            } else {
//                g.box(1 + add);
//            }
//        }
    }

    public Tool getSelectedTool() {
        return tools.get(selectedTool);
    }


    public void drawGUI(UIExtensionFrame frame) {
        if (VoxelGame.getGame().mode == GameMode.FREEPLAY) {
            String str = "Block Mode: " + getSelectedTool().name;
//            if (mode != null && isSizeDependant(mode)) {
//                str += " (x" + size + ")";
//            }

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
