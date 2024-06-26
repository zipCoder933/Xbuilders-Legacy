/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.game.items.entities.mobile.Animal;
import com.xbuilders.engine.utils.BFS.HashQueue;
import com.xbuilders.engine.utils.BFS.Node;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import org.joml.Vector3i;

import java.util.HashSet;

/**
 *
 * @author zipCoder933
 */
public class SettingUtils {

    public static final int MAX_SET_TIME = 30000;

    public SettingUtils(BlockTools parent) {
        this.parent = parent;
    }


    BlockTools parent;

    public void setFloor(ItemQuantity item, Ray ray, long timeSinceStart, int size, BlockData orientation) {
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

   public void setLine(Block item, Ray ray, long timeSinceStart, int size, BlockData orientation) {
        Vector3i position = new Vector3i(ray.getHitPosPlusNormal());
        Vector3i normal = new Vector3i(ray.getHitNormalAsInt());
        for (int i = 0; i < size; i++) {
            if (!voxelIsAvalilable(position)) {
                break;
            }
            parent.blockSetter.addToBlockQueue(item, position, orientation);
            position.add(normal);
        }
    }



    private boolean voxelIsAvalilable(Vector3i position) {
        return !VoxelGame.getWorld().getBlock(position).isSolid();
    }

    private boolean voxelIsAvalilableForRemoval(Vector3i position) {
        return !VoxelGame.getWorld().getBlock(position).isAir();
    }

    public void eraseLine(Ray ray, long timeSinceStart, int size) {
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



    public static void deleteEntitiesInsideBoundary(HashSet<SubChunk> subChunks, AABB boundary) {
        for (SubChunk sc : subChunks) {
            if (sc == null) {
                continue;
            }
            for (Entity e : sc.entities.list) {
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


}
