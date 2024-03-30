/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.preformance.Stopwatch;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.other.copyPaste.BlockPaste;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3i;
import processing.core.KeyCode;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TOP;

import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.ui4j.UIExtensionFrame;

/**
 * @author zipCoder933
 */
public class BlockMode {

    public BulkBlockSetter blockSetter;
    SettingUtils setting;

    public void keyReleased(BaseWindow window, KeyEvent ke) {
        if (window.keyIsPressed(KeyCode.SHIFT)) {
            return;
        }
        if (VoxelGame.getGame().mode == GameMode.FREEPLAY) {
            if (window.keyIsPressed(KeyCode.ONE)) {
                mode = Mode.DEFAULT;
            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.L)
                    || window.keyIsPressed(KeyCode.TWO)) {
                if (mode == Mode.LINE) {
                    mode = Mode.ERASE_LINE;
                } else if (mode == Mode.ERASE_LINE) {
                    mode = Mode.LINE;
                } else {
                    mode = Mode.LINE;
                }
            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.R)
                    || window.keyIsPressed(KeyCode.THREE)) {
                if (mode == Mode.REPAINT) {
                    mode = Mode.ERASE_BLOCK;
                } else if (mode == Mode.ERASE_BLOCK) {
                    mode = Mode.REPAINT;
                } else {
                    mode = Mode.REPAINT;
                }
            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.P)
                    || window.keyIsPressed(KeyCode.FOUR)) {
                mode = Mode.PLANE;

            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.K)
                    || window.keyIsPressed(KeyCode.FIVE)) {
                if (null == mode) {
                    mode = Mode.BOUNDARY_CREATE;
                } else {
                    mode = switch (mode) {
                        case BOUNDARY_CREATE -> Mode.BOUNDARY_DELETE;
                        case BOUNDARY_DELETE -> Mode.WALL_BOUNDARY_CREATE;
                        case WALL_BOUNDARY_CREATE -> Mode.HOLLOW_BOUNDARY_CREATE;
                        case HOLLOW_BOUNDARY_CREATE -> Mode.BOUNDARY_CREATE;
                        default -> Mode.BOUNDARY_CREATE;
                    };
                }

            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.C)  || window.keyIsPressed(KeyCode.SEVEN)) {
                mode = Mode.COPY;
            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.V)  || window.keyIsPressed(KeyCode.EIGHT)) {
                if (null == mode) {
                    mode = Mode.PASTE;
                } else {
                    mode = switch (mode) {
                        case PASTE -> Mode.ADDITIVE_PASTE;
                        case ADDITIVE_PASTE -> Mode.PASTE;
                        default -> Mode.PASTE;
                    };
                }
            } else if (window.keysArePressed(KeyCode.CTRL, KeyCode.S) || window.keyIsPressed(KeyCode.SIX)) {
                if (null == mode) {
                    mode = Mode.SPHERE;
                } else {
                    mode = switch (mode) {
                        case SPHERE -> Mode.HOLLOW_SPHERE;
                        case HOLLOW_SPHERE -> Mode.CENTERED_SPHERE;
                        case CENTERED_SPHERE -> Mode.CENTERED_HOLLOW_SPHERE;
                        case CENTERED_HOLLOW_SPHERE -> Mode.SPHERE;
                        default -> Mode.SPHERE;
                    };
                }
            } else if (window.keyIsPressed(KeyCode.V)) {
                BlockPaste.nextOffsetVertex();
            } else if (window.keyIsPressed(KeyCode.R)) {
                BlockPaste.rotatePasteBox();
            }
        }
        clampMode();

    }

    private boolean isSizeDependant(Mode mode) {
        return mode == Mode.LINE
                || mode == Mode.ERASE_LINE
                || mode == Mode.REPAINT
                || mode == Mode.CENTERED_HOLLOW_SPHERE
                || mode == Mode.CENTERED_SPHERE
                || mode == Mode.PLANE
                || mode == Mode.ERASE_BLOCK;
    }

    private boolean isEraseMode() {
        return mode == Mode.ERASE_LINE
                || mode == Mode.ERASE_BLOCK
                || mode == Mode.BOUNDARY_DELETE;
    }

    public enum Mode {
        DEFAULT, LINE, ERASE_LINE, PLANE, REPAINT, ERASE_BLOCK, SPHERE, HOLLOW_SPHERE, CENTERED_SPHERE, CENTERED_HOLLOW_SPHERE,
        BOUNDARY_CREATE, BOUNDARY_DELETE, COPY, PASTE, ADDITIVE_PASTE, HOLLOW_BOUNDARY_CREATE, WALL_BOUNDARY_CREATE
    }

    public BlockMode(UserControlledPlayer player) {
        mode = Mode.DEFAULT;
        size = 6;
        blockSetter = new BulkBlockSetter();
        setting = new SettingUtils(this);
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
        clampMode();
    }

    //<editor-fold defaultstate="collapsed" desc="clamp + key event">


    private void clampMode() {
//        System.out.println("Clamping mode "+mode);
        boolean changeToDeletion = false;
        if (VoxelGame.getPlayer().blockPanel.curItemIsNull()) {
            changeToDeletion = true;
        } else {
            Item item = VoxelGame.getPlayer().blockPanel.getCurItem().getItem();
            if (item.type == ItemType.BLOCK) {
                Block b = (Block) item;
                if (b.isLuminous() || !b.saveInChunk()
                        || !b.isInfiniteResource()) {
                    mode = Mode.DEFAULT;
                }
            } else {
                changeToDeletion = true;
            }
        }

        if (changeToDeletion && !isEraseMode()) {
            mode = switch (mode) {
                case REPAINT -> Mode.ERASE_BLOCK;
                case BOUNDARY_CREATE, HOLLOW_BOUNDARY_CREATE, WALL_BOUNDARY_CREATE -> Mode.BOUNDARY_DELETE;
                case LINE -> Mode.ERASE_LINE;
                case PLANE -> Mode.DEFAULT;
                case SPHERE,HOLLOW_SPHERE,CENTERED_SPHERE,CENTERED_HOLLOW_SPHERE -> Mode.DEFAULT;

                default -> mode;
            };
        }

        if (null == mode) {
            size = 0;
        } else if (mode == Mode.REPAINT || mode == Mode.ERASE_BLOCK) {
            size = (int) MathUtils.clamp(size, 2, VoxelGame.getSettings().getSettingsFile().maxBlockModeSize * 0.6);
        } else {
            size = MathUtils.clamp(size, 2, VoxelGame.getSettings().getSettingsFile().maxBlockModeSize);
        }
//        System.out.println("Mode: " + mode + "\tSize: " + size);
    }


    public Mode mode;
    private int size;

    //</editor-fold>
    public void resetBlockMode() {
        mode = Mode.DEFAULT;
    }

    public boolean setBlock(ItemQuantity item, final Ray ray, final BlockData data) {
        clampMode();
        Stopwatch watch = new Stopwatch();
        watch.start();
        long timeSinceStart = System.currentTimeMillis();


        switch (mode) {
            case LINE -> {
                setting.setLine(item, ray, timeSinceStart, size, data);
                blockSetter.wakeUp();
                return true;
            }
            case PLANE -> {
                (new Thread() {
                    @Override
                    public void run() {
                        setting.setFloor(item, ray, timeSinceStart, size, data);
                        watch.calculateElapsedTime();
                        System.out.println("Finished creating block plane.\tTime elapsed: " + watch.toString());
                        blockSetter.wakeUp();
                    }
                }).start();
                return true;
            }
            case REPAINT -> {
                (new Thread() {
                    @Override
                    public void run() {
                        setting.repaint(item, ray, timeSinceStart, size, data);
                        watch.calculateElapsedTime();
                        System.out.println("Finished repainting.\tTime elapsed: " + watch.toString());
                        blockSetter.wakeUp();
                    }
                }).start();
                return true;
            }
            case ERASE_BLOCK -> {
                (new Thread() {
                    @Override
                    public void run() {
                        setting.erase(ray, timeSinceStart, size);
                        watch.calculateElapsedTime();
                        System.out.println("Finished erasing.\tTime elapsed: " + watch.toString());
                        blockSetter.wakeUp();
                    }
                }).start();
                return true;
            }
            case ERASE_LINE -> {
                setting.eraseLine(ray, timeSinceStart, size);
                return true;
            }
            case SPHERE -> {
                setting.sphere(ray.getHitPosPlusNormal());
                return true;
            }
            case HOLLOW_SPHERE -> {
                setting.hollowSphere(ray.getHitPosPlusNormal());
                return true;
            }
            case CENTERED_SPHERE -> {
                setting.centeredSphere(ray.getHitPosPlusNormal());
                return true;
            }
            case CENTERED_HOLLOW_SPHERE -> {
                setting.centeredHollowSphere(ray.getHitPosPlusNormal());
                return true;
            }
            case BOUNDARY_CREATE -> {
                setting.boundaryCreate(ray.getHitPosPlusNormal());
                return true;
            }
            case HOLLOW_BOUNDARY_CREATE -> {
                setting.hollowBoundaryCreate(ray.getHitPosPlusNormal());
                return true;
            }
            case WALL_BOUNDARY_CREATE -> {
                setting.wallBoundaryCreate(ray.getHitPosPlusNormal());
                return true;
            }
            case BOUNDARY_DELETE -> {
                setting.boundaryDelete(ray.getHitPosPlusNormal());
                return true;
            }
            case COPY -> {
                GameItems.START_BOUNDARY.place(
                        ray.getHitPosPlusNormal().x,
                        ray.getHitPosPlusNormal().y,
                        ray.getHitPosPlusNormal().z, GameItems.COPY_TOOL.boundarySetEvent);
                return true;
            }
            case PASTE -> {
                GameItems.BLOCK_PASTE.setBlock(
                        ray.getHitPosPlusNormal().x,
                        ray.getHitPosPlusNormal().y,
                        ray.getHitPosPlusNormal().z, null);
                return true;
            }
            case ADDITIVE_PASTE -> {
                GameItems.ADDITIVE_PASTE.setBlock(
                        ray.getHitPosPlusNormal().x,
                        ray.getHitPosPlusNormal().y,
                        ray.getHitPosPlusNormal().z, null);
                return true;
            }
        }
        return false;
    }

    public void newGame() {
        mode = Mode.DEFAULT;
        blockSetter.start();
    }

    private boolean isRemovalMode() {
        return mode == Mode.ERASE_LINE || mode == Mode.ERASE_BLOCK || mode == Mode.BOUNDARY_DELETE;
    }

    public void drawScene(Ray ray, PGraphics g) {
        if (VoxelGame.getGame().mode == GameMode.WALKTHOUGH) {
            mode = Mode.DEFAULT;
        }
        if ((ray.hitTarget || VoxelGame.getPlayer().camera.cursorRayHitAllBlocks)
                && mode != null && mode != Mode.DEFAULT) {
            g.strokeWeight(1.5f);
            g.noFill();
            if (isRemovalMode()) {
                g.stroke(255, 50, 0);
            } else {
                g.stroke(255);
            }
            BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
            float add = 0f;

            if (mode == Mode.LINE) {
                float transSize = ((int) size / 2) + 0.5f;
                if (size % 2 != 0) {
                    transSize += 0.5f;
                }
                if (ray.hitNormal.x > 0) {
                    g.translate(transSize, 0, 0);
                    g.box(size + 0.1f, 1 + add, 1 + add);
                } else if (ray.hitNormal.x < 0) {
                    g.translate(-transSize, 0, 0);
                    g.box(size + 0.1f, 1 + add, 1 + add);
                } else {
                    if (ray.hitNormal.y > 0) {
                        g.translate(0, transSize, 0);
                        g.box(1 + add, size + 0.1f, 1 + add);
                    } else {
                        if (ray.hitNormal.y < 0) {
                            g.translate(0, -transSize, 0);
                            g.box(1 + add, size + 0.1f, 1 + add);
                        } else {
                            if (ray.hitNormal.z > 0) {
                                g.translate(0, 0, transSize);
                                g.box(1 + add, 1 + add, size + 0.1f);
                            } else {
                                g.translate(0, 0, -transSize);
                                g.box(1 + add, 1 + add, size + 0.1f);
                            }
                        }
                    }
                }
            } else if (mode == Mode.ERASE_LINE) {
                float transSize = ((int) size / 2);
                if (size % 2 == 0) {
                    transSize -= 0.5f;
                }
                if (ray.hitNormal.x > 0) {
                    g.translate(-transSize, 0, 0);
                    g.box(size + 0.1f, 1 + add, 1 + add);
                } else if (ray.hitNormal.x < 0) {
                    g.translate(transSize, 0, 0);
                    g.box(size + 0.1f, 1 + add, 1 + add);
                } else {
                    if (ray.hitNormal.y > 0) {
                        g.translate(0, -transSize, 0);
                        g.box(1 + add, size + 0.1f, 1 + add);
                    } else {
                        if (ray.hitNormal.y < 0) {
                            g.translate(0, transSize, 0);
                            g.box(1 + add, size + 0.1f, 1 + add);
                        } else {
                            if (ray.hitNormal.z > 0) {
                                g.translate(0, 0, -transSize);
                                g.box(1 + add, 1 + add, size + 0.1f);
                            } else {
                                g.translate(0, 0, transSize);
                                g.box(1 + add, 1 + add, size + 0.1f);
                            }
                        }
                    }
                }
            } else if (mode == Mode.PLANE) {
                g.translate(ray.hitNormal.x, ray.hitNormal.y, ray.hitNormal.z);
                if (ray.hitNormal.y == 0 && orientation.getY() == 0) {
                    if (orientation.getXZ() == 0 || orientation.getXZ() == 2) {
                        g.box((size * 2) - 1f, (size * 2) - 1f, 1 + add);
                    } else {
                        g.box(1 + add, (size * 2) - 1f, (size * 2) - 1f);
                    }
                } else {
                    g.box((size * 2) - 1f, 1 + add, (size * 2) - 1f);
                }
            } else if (mode == Mode.REPAINT || mode == Mode.ERASE_BLOCK
                    || mode == Mode.CENTERED_HOLLOW_SPHERE
                    || mode == Mode.CENTERED_SPHERE) {
                g.translate(ray.hitNormal.x, ray.hitNormal.y, ray.hitNormal.z);
                g.box((size * 2) - 1f, (size * 2) - 1f, (size * 2) - 1f);
            } else {
                g.box(1 + add);
            }
        }
    }

    public void drawGUI(UIExtensionFrame frame) {
        if (VoxelGame.getGame().mode == GameMode.FREEPLAY) {
            String str = "Block Mode: " + (mode.toString().replace("_", " "));
            if (mode != null && isSizeDependant(mode)) {
                str += " (x" + size + ")";
            }

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
