package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.utils.BFS.HashQueue;
import com.xbuilders.engine.utils.BFS.Node;
import com.xbuilders.engine.utils.BFS.TravelNode;
import com.xbuilders.engine.utils.preformance.Stopwatch;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.game.blockMode.SettingUtils;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.HashSet;

public class Repaint extends Tool {
    public Repaint(BlockTools blockTools) {
        super("Repaint", blockTools);
    }

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        return window.keyIsPressed(KeyCode.FIVE);
    }


    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        if (isCreationMode) {
//            if (mode == FillMode.PLANAR) {
//                (new Thread() {
//                    @Override
//                    public void run() {
//                        setting.setFloor(item, ray.cursorRay, timeSinceStart, size, data);
//                        watch.calculateElapsedTime();
//                        System.out.println("Finished creating block plane.\tTime elapsed: " + watch.toString());
//                        blockSetter.wakeUp();
//                    }
//                }).start();
//            } else {
            (new Thread(() -> {//Repaint
                if (item == null) return;
                repaint((Block) item.getItem(), ray.cursorRay, toolSize, data, false);
                blockSetter.wakeUp();
            })).start();
//            }
        } else {
            (new Thread(() -> {
                erase(ray.cursorRay, toolSize);
                blockSetter.wakeUp();
            })).start();
        }
        return true;
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void activate() {
        super.activate();
    }

    public void repaint(Block replaceBlock, Ray ray, int size, BlockData orientation, boolean fillMode) {
        Stopwatch watch = new Stopwatch();
        watch.start();
        long timeSinceStart = System.currentTimeMillis();

        HashSet<Vector3i> explored = new HashSet<>();
        HashQueue<Node> queue = new HashQueue<>();

        Vector3i startingPoint = ray.getHitPosPlusNormal();
        Block selectedBlock;
        if (fillMode) {
            selectedBlock = VoxelGame.getWorld().getBlock(ray.getHitPosPlusNormal());
            queue.add(new TravelNode(ray.getHitPosPlusNormal(), 0));
        } else {
            selectedBlock = VoxelGame.getWorld().getBlock(ray.getHitPositionAsInt());
            queue.add(new TravelNode(ray.getHitPositionAsInt(), 0));
        }

        if (!fillMode && selectedBlock.type != replaceBlock.type) {
            VoxelGame.getGame().alert("Can't repaint blocks of different types.");
            return;
        }


        while (!queue.isEmpty()) {
            if (System.currentTimeMillis() - timeSinceStart > SettingUtils.MAX_SET_TIME) {
                VoxelGame.getGame().alert("Maximum time reached. Aborting.");
                return;
            }
            Node node = queue.getAndRemove();

            if (node.getCoords().x < startingPoint.x + size && node.getCoords().x > startingPoint.x - size
                    && node.getCoords().z < startingPoint.z + size && node.getCoords().z > startingPoint.z - size
                    && node.getCoords().y < startingPoint.y + size && node.getCoords().y > startingPoint.y - size
                    && VoxelGame.getWorld().getBlock(node.getCoords()) == selectedBlock) {

                Vector3i coordinate = new Vector3i(node.getCoords());
                if (!explored.contains(coordinate)) {
                    blockSetter.addToBlockQueue(replaceBlock, coordinate, orientation);
                    queue.add(new Node(node.getCoords().x + 1, node.getCoords().y, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x - 1, node.getCoords().y, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z + 1));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z - 1));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y + 1, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y - 1, node.getCoords().z));
                    explored.add(coordinate);
                }
            }
        }
    }

    public void erase(Ray ray, int size) {
        Stopwatch watch = new Stopwatch();
        watch.start();
        long timeSinceStart = System.currentTimeMillis();

        HashSet<Vector3i> explored = new HashSet<>();
        HashQueue<Node> queue = new HashQueue<>();

        Vector3i startingPoint = ray.getHitPosPlusNormal();
        Block highlightedBlock = VoxelGame.getWorld().getBlock(ray.getHitPositionAsInt());
        queue.add(new TravelNode(ray.getHitPositionAsInt(), 0));
        while (!queue.isEmpty()) {
            if (System.currentTimeMillis() - timeSinceStart > SettingUtils.MAX_SET_TIME) {
                VoxelGame.getGame().alert("Maximum time reached. Aborting.");
                return;
            }
            Node node = queue.getAndRemove();

            if (node.getCoords().x < startingPoint.x + size && node.getCoords().x > startingPoint.x - size
                    && node.getCoords().z < startingPoint.z + size && node.getCoords().z > startingPoint.z - size
                    && node.getCoords().y < startingPoint.y + size && node.getCoords().y > startingPoint.y - size
                    && VoxelGame.getWorld().getBlock(node.getCoords()) == highlightedBlock) {

                Vector3i coordinate = new Vector3i(node.getCoords());
                if (!explored.contains(coordinate)) {
                    blockSetter.addToBlockQueue(BlockList.BLOCK_AIR, coordinate, null);
                    queue.add(new Node(node.getCoords().x + 1, node.getCoords().y, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x - 1, node.getCoords().y, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z + 1));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z - 1));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y + 1, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y - 1, node.getCoords().z));
                    explored.add(coordinate);
                }
            }
        }
    }

    @Override
    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();

        int add = 0;
        g.strokeWeight(1.5f);
        g.noFill();
        g.stroke(255);
        g.translate(
                ray.cursorRay.hitPostition.x + 0.5f,
                ray.cursorRay.hitPostition.y + 0.5f,
                ray.cursorRay.hitPostition.z + 0.5f);

//        if (mode == FillMode.PLANAR) {
//            g.translate(ray.cursorRay.hitNormal.x, ray.cursorRay.hitNormal.y, ray.cursorRay.hitNormal.z);
//            if (ray.cursorRay.hitNormal.y == 0 && orientation.getY() == 0) {
//                if (orientation.getXZ() == 0 || orientation.getXZ() == 2) {
//                    g.box((size * 2) - 1f, (size * 2) - 1f, 1 + add);
//                } else {
//                    g.box(1 + add, (size * 2) - 1f, (size * 2) - 1f);
//                }
//            } else {
//                g.box((size * 2) - 1f, 1 + add, (size * 2) - 1f);
//            }
//        } else {
        g.translate(ray.cursorRay.hitNormal.x, ray.cursorRay.hitNormal.y, ray.cursorRay.hitNormal.z);
        g.box((toolSize * 2) - 1f, (toolSize * 2) - 1f, (toolSize * 2) - 1f);
//        }
        return true;
    }

    public String toolDescription() {
        return "Repaint (x" + toolSize + ")";
    }

    @Override
    public void changeMode() {
    }

    @Override
    public boolean keyReleased(BaseWindow window, KeyEvent ke) {
        return false;
    }

    public void mouseWheel(int count) {
        toolSize -= count;
        if (toolSize < 1) {
            toolSize = 1;
        } else if (toolSize > 10) {
            toolSize = 10;
        }
    }

}

