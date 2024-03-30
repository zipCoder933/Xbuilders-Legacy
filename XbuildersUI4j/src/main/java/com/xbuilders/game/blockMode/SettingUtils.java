/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.game.items.entities.mobile.Animal;
import com.xbuilders.engine.utils.BFS.HashQueue;
import com.xbuilders.engine.utils.BFS.Node;
import com.xbuilders.engine.utils.BFS.TravelNode;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.other.boundaryBlocks.BoundarySetEvent;
import org.joml.Vector3i;

import java.util.HashSet;

/**
 *
 * @author zipCoder933
 */
class SettingUtils {

    final int MAX_SET_TIME = 30000;

    public SettingUtils(BlockMode parent) {
        this.parent = parent;
        sphereBoundaryEvent = new SphereBoundarySetEvent(parent);
        hollowSphereBoundaryEvent = new SphereBoundarySetEvent(parent) {
            @Override
            public boolean checkIfToSet(int x, int y, int z, double radius, Vector3i center) {
                double dist = MathUtils.dist(center.x, center.y, center.z, x, y, z);

                return dist < radius && dist > radius - 2 && !VoxelGame.getWorld().getBlock(x, y, z).isSolid();
            }

        };
    }

    SphereBoundarySetEvent sphereBoundaryEvent;
    BoundarySetEvent hollowSphereBoundaryEvent;

    void sphere(Vector3i hitPosition) {
        GameItems.START_BOUNDARY.place(hitPosition.x, hitPosition.y, hitPosition.z, sphereBoundaryEvent);
    }

    void hollowSphere(Vector3i hitPosition) {
        GameItems.START_BOUNDARY.place(hitPosition.x, hitPosition.y, hitPosition.z, hollowSphereBoundaryEvent);
    }

    BlockMode parent;

    void setFloor(ItemQuantity item, Ray ray, long timeSinceStart, int size, BlockData orientation) {
        HashSet<Vector3i> explored = new HashSet<>();
        HashQueue<Node> queue = new HashQueue<>();

        Vector3i startingPoint = ray.getHitPosPlusNormal();
        queue.add(new Node(startingPoint));
        BlockOrientation orientation2 = VoxelGame.getPlayer().cameraBlockOrientation();
        if (ray.getHitNormalAsInt().y == 0 && orientation2.getY() == 0) {
            if (orientation2.getXZ() == 0 || orientation2.getXZ() == 2) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                while (!queue.isEmpty()) {
                    Node node = queue.getAndRemove();

                    if (!explored.contains(node.getCoords())
                            && node.getCoords().x < startingPoint.x + size && node.getCoords().x > startingPoint.x - size
                            && node.getCoords().y < startingPoint.y + size && node.getCoords().y > startingPoint.y - size
                            && voxelIsAvalilable(node.getCoords())) {
                        parent.blockSetter.addToBlockQueue((Block) item.getItem(), node.getCoords(), orientation);
                        queue.add(new Node(node.getCoords().x, node.getCoords().y + 1, node.getCoords().z));
                        queue.add(new Node(node.getCoords().x, node.getCoords().y - 1, node.getCoords().z));
                        queue.add(new Node(node.getCoords().x + 1, node.getCoords().y, node.getCoords().z));
                        queue.add(new Node(node.getCoords().x - 1, node.getCoords().y, node.getCoords().z));
                        explored.add(node.getCoords());
                    }
                }
//</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="comment">
                while (!queue.isEmpty()) {
                    Node node = queue.getAndRemove();

                    if (!explored.contains(node.getCoords())
                            && node.getCoords().z < startingPoint.z + size && node.getCoords().z > startingPoint.z - size
                            && node.getCoords().y < startingPoint.y + size && node.getCoords().y > startingPoint.y - size
                            && voxelIsAvalilable(node.getCoords())) {
                        parent.blockSetter.addToBlockQueue((Block) item.getItem(), node.getCoords(), orientation);
                        queue.add(new Node(node.getCoords().x, node.getCoords().y + 1, node.getCoords().z));
                        queue.add(new Node(node.getCoords().x, node.getCoords().y - 1, node.getCoords().z));
                        queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z + 1));
                        queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z - 1));
                        explored.add(node.getCoords());
                    }
                }
//</editor-fold>
            }
        } else {
            //<editor-fold defaultstate="collapsed" desc="comment">
            while (!queue.isEmpty()) {
                Node node = queue.getAndRemove();
                if (!explored.contains(node.getCoords())
                        && node.getCoords().x < startingPoint.x + size && node.getCoords().x > startingPoint.x - size
                        && node.getCoords().z < startingPoint.z + size && node.getCoords().z > startingPoint.z - size
                        && voxelIsAvalilable(node.getCoords())) {
                    parent.blockSetter.addToBlockQueue((Block) item.getItem(), node.getCoords(), orientation);
                    queue.add(new Node(node.getCoords().x + 1, node.getCoords().y, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x - 1, node.getCoords().y, node.getCoords().z));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z + 1));
                    queue.add(new Node(node.getCoords().x, node.getCoords().y, node.getCoords().z - 1));
                    explored.add(node.getCoords());
                }
            }
//</editor-fold>
        }
    }

    void setLine(ItemQuantity item, Ray ray, long timeSinceStart, int size, BlockData orientation) {
        Vector3i position = new Vector3i(ray.getHitPosPlusNormal());
        Vector3i normal = new Vector3i(ray.getHitNormalAsInt());
        for (int i = 0; i < size; i++) {
            if (!voxelIsAvalilable(position)) {
                break;
            }
            parent.blockSetter.addToBlockQueue((Block) item.getItem(), position, orientation);
            position.add(normal);
        }
    }

    void repaint(ItemQuantity item, Ray ray, long timeSinceStart, int size, BlockData orientation) {
        HashSet<Vector3i> explored = new HashSet<>();
        HashQueue<Node> queue = new HashQueue<>();

        Vector3i startingPoint = ray.getHitPosPlusNormal();
        Block highlightedBlock = VoxelGame.getWorld().getBlock(ray.getHitPositionAsInt());
        queue.add(new TravelNode(ray.getHitPositionAsInt(), 0));
        while (!queue.isEmpty()) {
            if (System.currentTimeMillis() - timeSinceStart > MAX_SET_TIME) {
                return;
            }
            Node node = queue.getAndRemove();

            if (node.getCoords().x < startingPoint.x + size && node.getCoords().x > startingPoint.x - size
                    && node.getCoords().z < startingPoint.z + size && node.getCoords().z > startingPoint.z - size
                    && node.getCoords().y < startingPoint.y + size && node.getCoords().y > startingPoint.y - size
                    && VoxelGame.getWorld().getBlock(node.getCoords()) == highlightedBlock) {

                Vector3i coordinate = new Vector3i(node.getCoords());
                if (!explored.contains(coordinate)) {
                    parent.blockSetter.addToBlockQueue((Block) item.getItem(), coordinate, orientation);
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

    void erase(Ray ray, long timeSinceStart, int size) {
        HashSet<Vector3i> explored = new HashSet<>();
        HashQueue<Node> queue = new HashQueue<>();

        Vector3i startingPoint = ray.getHitPosPlusNormal();
        Block highlightedBlock = VoxelGame.getWorld().getBlock(ray.getHitPositionAsInt());
        queue.add(new TravelNode(ray.getHitPositionAsInt(), 0));
        while (!queue.isEmpty()) {
            if (System.currentTimeMillis() - timeSinceStart > MAX_SET_TIME) {
                return;
            }
            Node node = queue.getAndRemove();

            if (node.getCoords().x < startingPoint.x + size && node.getCoords().x > startingPoint.x - size
                    && node.getCoords().z < startingPoint.z + size && node.getCoords().z > startingPoint.z - size
                    && node.getCoords().y < startingPoint.y + size && node.getCoords().y > startingPoint.y - size
                    && VoxelGame.getWorld().getBlock(node.getCoords()) == highlightedBlock) {

                Vector3i coordinate = new Vector3i(node.getCoords());
                if (!explored.contains(coordinate)) {
                    parent.blockSetter.addToBlockQueue(BlockList.BLOCK_AIR, coordinate, null);
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

    private boolean voxelIsAvalilable(Vector3i position) {
        return !VoxelGame.getWorld().getBlock(position).isSolid();
    }

    private boolean voxelIsAvalilableForRemoval(Vector3i position) {
        return !VoxelGame.getWorld().getBlock(position).isAir();
    }

    void eraseLine(Ray ray, long timeSinceStart, int size) {
        Vector3i position = new Vector3i(ray.getHitPositionAsInt());
        Vector3i normal = ray.getHitNormalAsInt();
        for (int i = 0; i < size; i++) {
            if (!voxelIsAvalilableForRemoval(position)) {
                break;
            }
            parent.blockSetter.addToBlockQueue(BlockList.BLOCK_AIR, position, null);
            position.sub(normal);
        }
        parent.blockSetter.wakeUp();
    }

    private BoundarySetEvent boundaryCreate = new BoundarySetEvent() {
        @Override
        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
//            System.out.println("Setting Blocks BOUNDARY CREATE: " + boundary);
            HashSet<SubChunk> subChunks = new HashSet<>();
            ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();
            if (item != null) {
                Item item1 = item.getItem();
                if (item1.type == ItemType.BLOCK) {
                    Block block = (Block) item.getItem();
                    BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
                    for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                        for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                            for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                                WCCi wcc = new WCCi().set(x, y, z);
                                subChunks.add(wcc.getSubChunk());
                                parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
                            }
                        }
                    }
                }
            }
            deleteEntitiesInsideBoundary(subChunks, boundary);
            parent.blockSetter.wakeUp();
        }
    };

    private BoundarySetEvent hollowBoundaryCreate = new BoundarySetEvent() {
        @Override
        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
            HashSet<SubChunk> subChunks = new HashSet<>();
            ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();
            if (item != null) {
                Item item1 = item.getItem();
                if (item1.type == ItemType.BLOCK) {
                    Block block = (Block) item.getItem();
                    BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
                    for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                        for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                            for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                                if (x == boundary.minPoint.x || x == boundary.maxPoint.x - 1
                                        || y == boundary.minPoint.y || y == boundary.maxPoint.y - 1
                                        || z == boundary.minPoint.z || z == boundary.maxPoint.z - 1) {
                                    WCCi wcc = new WCCi().set(x, y, z);
                                    subChunks.add(wcc.getSubChunk());
                                    parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
                                }
                            }
                        }
                    }
                }
            }
            deleteEntitiesInsideBoundary(subChunks, boundary);
            parent.blockSetter.wakeUp();
        }
    };
    private BoundarySetEvent wallBoundaryCreate = new BoundarySetEvent() {
        @Override
        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
            HashSet<SubChunk> subChunks = new HashSet<>();
            ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();
            if (item != null) {
                Item item1 = item.getItem();
                if (item1.type == ItemType.BLOCK) {
                    Block block = (Block) item.getItem();
                    BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
                    for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                        for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                            for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                                if (x == boundary.minPoint.x || x == boundary.maxPoint.x - 1
                                        || z == boundary.minPoint.z || z == boundary.maxPoint.z - 1) {
                                    WCCi wcc = new WCCi().set(x, y, z);
                                    subChunks.add(wcc.getSubChunk());
                                    parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
                                }
                            }
                        }
                    }
                }
            }
            deleteEntitiesInsideBoundary(subChunks, boundary);
            parent.blockSetter.wakeUp();
        }
    };

    protected static void deleteEntitiesInsideBoundary(HashSet<SubChunk> subChunks, AABB boundary) {
        for (SubChunk sc : subChunks) {
            if (sc == null) {
                continue;
            }
            for (Entity e : sc.getEntities().list) {
                if (e == null) {
                    continue;
                }
                if (e.link.saveInChunk() && !(e instanceof Animal)
                        && e.worldPosition.x >= boundary.minPoint.x
                        && e.worldPosition.x <= boundary.maxPoint.x
                        && e.worldPosition.y >= boundary.minPoint.y
                        && e.worldPosition.y <= boundary.maxPoint.y
                        && e.worldPosition.z >= boundary.minPoint.z) {
                    if (e.worldPosition.z <= boundary.maxPoint.z) {
                        e.destroy(true);
                    }
                }
            }
        }
    }

    private BoundarySetEvent boundaryDelete = new BoundarySetEvent() {
        @Override
        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
            HashSet<SubChunk> subChunks = new HashSet<>();
            for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                    for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                        WCCi wcc = new WCCi().set(x, y, z);
                        subChunks.add(wcc.getSubChunk());
                        parent.blockSetter.addToBlockQueue(BlockList.BLOCK_AIR, new Vector3i(x, y, z), null);
                    }
                }
            }
            deleteEntitiesInsideBoundary(subChunks, boundary);
            parent.blockSetter.wakeUp();
        }
    };

    void boundaryCreate(Vector3i hitPosition) {
        GameItems.START_BOUNDARY.place(hitPosition.x, hitPosition.y, hitPosition.z, boundaryCreate);
    }

    void hollowBoundaryCreate(Vector3i hitPosition) {
        GameItems.START_BOUNDARY.place(hitPosition.x, hitPosition.y, hitPosition.z, hollowBoundaryCreate);
    }

    void boundaryDelete(Vector3i hitPosition) {
        GameItems.START_BOUNDARY.place(hitPosition.x, hitPosition.y, hitPosition.z, boundaryDelete);
    }

    void wallBoundaryCreate(Vector3i hitPosition) {
        GameItems.START_BOUNDARY.place(hitPosition.x, hitPosition.y, hitPosition.z, wallBoundaryCreate);
    }

    void centeredSphere(Vector3i hitPosition) {
        int size2 = parent.getSize() * 2;
        sphereBoundaryEvent.onBoundarySet(
                new AABB().setPosAndSize(hitPosition.x - parent.getSize(),
                        hitPosition.y - parent.getSize(),
                        hitPosition.z - parent.getSize(),
                        size2, size2, size2), null, null);
    }

    void centeredHollowSphere(Vector3i hitPosition) {
        int size2 = parent.getSize() * 2;
        hollowSphereBoundaryEvent.onBoundarySet(
                new AABB().setPosAndSize(hitPosition.x - parent.getSize(),
                        hitPosition.y - parent.getSize(),
                        hitPosition.z - parent.getSize(),
                        size2, size2, size2), null, null);
    }

}
