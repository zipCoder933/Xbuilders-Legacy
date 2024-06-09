package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.utils.preformance.Stopwatch;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.window.BaseWindow;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;

public class LineTool extends Tool {

    public LineTool(BlockTools tools) {
        super("Line", tools);
        usesSize = true;
    }

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        return window.keyIsPressed(KeyCode.THREE);
    }

    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        Stopwatch watch = new Stopwatch();
        watch.start();
        long timeSinceStart = System.currentTimeMillis();

        if (isCreationMode) {
            if (item != null && item.getItem().itemType == ItemType.BLOCK) {
                Block block = (Block) item.getItem();
                setting.setLine(block, ray.cursorRay, timeSinceStart, blockTools.getSize(), data);
            }
        } else setting.eraseLine(ray.cursorRay, timeSinceStart, blockTools.getSize());

        blockSetter.wakeUp();
        return true;
    }

    @Override
    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        g.translate(
                ray.cursorRay.hitPostition.x + 0.5f,
                ray.cursorRay.hitPostition.y + 0.5f,
                ray.cursorRay.hitPostition.z + 0.5f);

        int size = blockTools.getSize();
        float add = 0f;
        g.strokeWeight(1.5f);
        g.noFill();
        g.stroke(255);

        g.pushMatrix();
        float transSize = ((int) size / 2) + 0.5f;
        if (size % 2 != 0) {
            transSize += 0.5f;
        }
        if (ray.cursorRay.hitNormal.x > 0) {
            g.translate(transSize, 0, 0);
            g.box(size + 0.1f, 1 + add, 1 + add);
        } else if (ray.cursorRay.hitNormal.x < 0) {
            g.translate(-transSize, 0, 0);
            g.box(size + 0.1f, 1 + add, 1 + add);
        } else {
            if (ray.cursorRay.hitNormal.y > 0) {
                g.translate(0, transSize, 0);
                g.box(1 + add, size + 0.1f, 1 + add);
            } else {
                if (ray.cursorRay.hitNormal.y < 0) {
                    g.translate(0, -transSize, 0);
                    g.box(1 + add, size + 0.1f, 1 + add);
                } else {
                    if (ray.cursorRay.hitNormal.z > 0) {
                        g.translate(0, 0, transSize);
                        g.box(1 + add, 1 + add, size + 0.1f);
                    } else {
                        g.translate(0, 0, -transSize);
                        g.box(1 + add, 1 + add, size + 0.1f);
                    }
                }
            }
            g.popMatrix();

            g.stroke(255, 0, 0);
            g.pushMatrix();
            transSize = ((int) size / 2);
            if (size % 2 == 0) {
                transSize -= 0.5f;
            }
            if (ray.cursorRay.hitNormal.x > 0) {
                g.translate(-transSize, 0, 0);
                g.box(size + 0.1f, 1 + add, 1 + add);
            } else if (ray.cursorRay.hitNormal.x < 0) {
                g.translate(transSize, 0, 0);
                g.box(size + 0.1f, 1 + add, 1 + add);
            } else {
                if (ray.cursorRay.hitNormal.y > 0) {
                    g.translate(0, -transSize, 0);
                    g.box(1 + add, size + 0.1f, 1 + add);
                } else {
                    if (ray.cursorRay.hitNormal.y < 0) {
                        g.translate(0, transSize, 0);
                        g.box(1 + add, size + 0.1f, 1 + add);
                    } else {
                        if (ray.cursorRay.hitNormal.z > 0) {
                            g.translate(0, 0, -transSize);
                            g.box(1 + add, 1 + add, size + 0.1f);
                        } else {
                            g.translate(0, 0, transSize);
                            g.box(1 + add, 1 + add, size + 0.1f);
                        }
                    }
                }
            }

        }
        g.popMatrix();

        return true;
    }
}
