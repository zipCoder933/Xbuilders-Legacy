package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.function.BiConsumer;

public class BoundarySet extends Tool {

    public BoundarySet(BlockTools tools) {
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


//    private BoundarySetEvent hollowBoundaryCreate = new BoundarySetEvent() {
//        @Override
//        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
//            HashSet<SubChunk> subChunks = new HashSet<>();
//            ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();
//            if (item != null) {
//                Item item1 = item.getItem();
//                if (item1.type == ItemType.BLOCK) {
//                    Block block = (Block) item.getItem();
//                    BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
//                    for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
//                        for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
//                            for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
//                                if (x == boundary.minPoint.x || x == boundary.maxPoint.x - 1
//                                        || y == boundary.minPoint.y || y == boundary.maxPoint.y - 1
//                                        || z == boundary.minPoint.z || z == boundary.maxPoint.z - 1) {
//                                    WCCi wcc = new WCCi().set(x, y, z);
//                                    subChunks.add(wcc.getSubChunk());
//                                    parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            deleteEntitiesInsideBoundary(subChunks, boundary);
//            parent.blockSetter.wakeUp();
//        }
//    };
//    private BoundarySetEvent wallBoundaryCreate = new BoundarySetEvent() {
//        @Override
//        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
//            HashSet<SubChunk> subChunks = new HashSet<>();
//            ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();
//            if (item != null) {
//                Item item1 = item.getItem();
//                if (item1.type == ItemType.BLOCK) {
//                    Block block = (Block) item.getItem();
//                    BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
//                    for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
//                        for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
//                            for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
//                                if (x == boundary.minPoint.x || x == boundary.maxPoint.x - 1
//                                        || z == boundary.minPoint.z || z == boundary.maxPoint.z - 1) {
//                                    WCCi wcc = new WCCi().set(x, y, z);
//                                    subChunks.add(wcc.getSubChunk());
//                                    parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            deleteEntitiesInsideBoundary(subChunks, boundary);
//            parent.blockSetter.wakeUp();
//        }
//    };

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        if (window.keyIsPressed(KeyCode.TWO)) {
            return true;
        }
        return false;
    }

    @Override
    public String toolDescription() {
        return VoxelGame.getPlayer().camera.cursorRay.boundary_lockToPlane ? "Plane (Boundary)" : "Boundary";
    }

    @Override
    public void changeMode() {
        VoxelGame.getPlayer().camera.cursorRay.boundary_lockToPlane = !VoxelGame.getPlayer().camera.cursorRay.boundary_lockToPlane;
        if (VoxelGame.getPlayer().camera.cursorRay.boundary_lockToPlane == false) {
            VoxelGame.getPlayer().camera.cursorRay.boundary_useHitPos = false;
        }
    }

    @Override
    public void activate() {
        VoxelGame.getPlayer().camera.cursorRay.enableBoundaryMode(boundarySet);
    }

    @Override
    public void deactivate() {
        VoxelGame.getPlayer().camera.cursorRay.disableBoundaryMode();
    }

    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        return false;
    }
}
