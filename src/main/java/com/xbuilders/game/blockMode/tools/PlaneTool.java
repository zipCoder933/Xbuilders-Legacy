package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.preformance.Stopwatch;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.function.BiConsumer;

public class PlaneTool extends Tool {

    public PlaneTool(BlockTools tools) {
        super("Boundary", tools);
        boundarySet = (AABB boundary, Boolean isCreationMode) -> {
            HashSet<SubChunk> subChunks = new HashSet<>();
            ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();

            if (isCreationMode) {
                if (item != null) {
                    Item item1 = item.getItem();
                    if (item1.itemType == ItemType.BLOCK) {
                        Block block = (Block) item.getItem();
                        BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
                        for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                            for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                                for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                                    WCCi wcc = new WCCi().set(x, y, z);
                                    subChunks.add(wcc.getSubChunk());
                                    blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
                                }
                            }
                        }
                    }
                }
            } else {
                for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                    for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                        for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                            WCCi wcc = new WCCi().set(x, y, z);
                            subChunks.add(wcc.getSubChunk());
                            blockSetter.addToBlockQueue(BlockList.BLOCK_AIR, new Vector3i(x, y, z), null);
                        }
                    }
                }
            }
            setting.deleteEntitiesInsideBoundary(subChunks, boundary);
            blockSetter.wakeUp();
        };
    }

    private final BiConsumer<AABB, Boolean> boundarySet;

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        if (window.keyIsPressed(KeyCode.THREE)) {
            return true;
        }
        return false;
    }

    boolean centeredMode;

    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        Stopwatch watch = new Stopwatch();
        watch.start();
        long timeSinceStart = System.currentTimeMillis();
        int size = blockTools.getSize();

        if (isCreationMode) {
            if (centeredMode) {
                (new Thread() {
                    @Override
                    public void run() {
                        setting.setFloor(item, ray.cursorRay, timeSinceStart, size, data);
                        watch.calculateElapsedTime();
                        System.out.println("Finished creating block plane.\tTime elapsed: " + watch.toString());
                        blockSetter.wakeUp();
                    }
                }).start();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toolDescription() {
        return "Plane " + (centeredMode ? " (Centered)" : " (Boundary)");
    }

    @Override
    public void activate() {
        centeredMode = false;
        VoxelGame.getPlayer().camera.cursorRay.enableBoundaryMode(boundarySet);
        VoxelGame.getPlayer().camera.cursorRay.boundary_lockToPlane = true;
    }

    @Override
    public void changeMode() {
        centeredMode = !centeredMode;
        if (centeredMode) {
            VoxelGame.getPlayer().camera.cursorRay.disableBoundaryMode();
        } else {
            VoxelGame.getPlayer().camera.cursorRay.enableBoundaryMode(boundarySet);
            VoxelGame.getPlayer().camera.cursorRay.boundary_lockToPlane = true;
        }
    }

    @Override
    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        if (centeredMode) {
            BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
            int size = blockTools.getSize();
            int add = 0;
            g.strokeWeight(1.5f);
            g.noFill();
            g.stroke(255);
            g.translate(
                    ray.cursorRay.hitPostition.x + 0.5f,
                    ray.cursorRay.hitPostition.y + 0.5f,
                    ray.cursorRay.hitPostition.z + 0.5f);

            g.translate(ray.cursorRay.hitNormal.x, ray.cursorRay.hitNormal.y, ray.cursorRay.hitNormal.z);
            if (ray.cursorRay.hitNormal.y == 0 && orientation.getY() == 0) {
                if (orientation.getXZ() == 0 || orientation.getXZ() == 2) {
                    g.box((size * 2) - 1f, (size * 2) - 1f, 1 + add);
                } else {
                    g.box(1 + add, (size * 2) - 1f, (size * 2) - 1f);
                }
            } else {
                g.box((size * 2) - 1f, 1 + add, (size * 2) - 1f);
            }
        }
        return true;
    }

    @Override
    public void deactivate() {
        VoxelGame.getPlayer().camera.cursorRay.disableBoundaryMode();
    }

}
