package com.xbuilders.engine.player.raycasting;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.wcc.WCCi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.joml.Vector3f;

public class RayCasting {

    /**
     * Also a static class. No instances needed
     */
    /**
     * // * @param rayOrigin the origin point of the ray // * @param
     * rayDirection the direction vector of the ray // * @param
     * intersectionDistance the intersection distance (the distance at // *
     * which the ray hit the object) // * @return the intersection point //
     */
    public static Vector3f getIntersectionPoint(Vector3f rayOrigin, Vector3f rayDirection, float intersectionDistance) {
        Vector3f a = new Vector3f(rayDirection);
        a.mul(intersectionDistance);
        Vector3f intersection_point = new Vector3f(rayOrigin).add(a);
        return intersection_point;
    }

    /**
     * from
     * https://gamedev.stackexchange.com/questions/18436/most-efficient-box-vs-ray-collision-algorithms/18459#18459
     *
     * @param rayOrigin the origin point of the ray
     * @param rayDirection the direction vector of the ray
     * @param aabbMin the min point of the AABB
     * @param aabbMax the max point of the AABB
     * @return the ray intersection distance (Float.MAX_VALUE) if not
     * intersecting
     */
    public static float getAABBIntersectionDistance(Vector3f rayOrigin, Vector3f rayDirection,
            Vector3f aabbMin, Vector3f aabbMax) {

        Vector3f dirfrac = new Vector3f();
        dirfrac.x = 1.0f / rayDirection.x;
        dirfrac.y = 1.0f / rayDirection.y;
        dirfrac.z = 1.0f / rayDirection.z;

        float t1 = (aabbMin.x - rayOrigin.x) * dirfrac.x;
        float t2 = (aabbMax.x - rayOrigin.x) * dirfrac.x;
        float t3 = (aabbMin.y - rayOrigin.y) * dirfrac.y;
        float t4 = (aabbMax.y - rayOrigin.y) * dirfrac.y;
        float t5 = (aabbMin.z - rayOrigin.z) * dirfrac.z;
        float t6 = (aabbMax.z - rayOrigin.z) * dirfrac.z;

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        if (tmax < 0) {// if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
            return Float.MAX_VALUE;// No intersection
        }
        if (tmin > tmax) {// if tmin > tmax, ray doesn't intersect AABB
            return Float.MAX_VALUE;// No intersection
        }
        return tmin;
    }

    public static Vector3f getRayNormalFromAABB(Vector3f rayOrigin, Vector3f rayDirection,
            Vector3f aabbMin, Vector3f aabbMax) {

        Vector3f dirfrac = new Vector3f();
        dirfrac.x = 1.0f / rayDirection.x;
        dirfrac.y = 1.0f / rayDirection.y;
        dirfrac.z = 1.0f / rayDirection.z;

        float t1 = (aabbMin.x - rayOrigin.x) * dirfrac.x;
        float t2 = (aabbMax.x - rayOrigin.x) * dirfrac.x;
        float t3 = (aabbMin.y - rayOrigin.y) * dirfrac.y;
        float t4 = (aabbMax.y - rayOrigin.y) * dirfrac.y;
        float t5 = (aabbMin.z - rayOrigin.z) * dirfrac.z;
        float t6 = (aabbMax.z - rayOrigin.z) * dirfrac.z;

        float tminx = Math.min(t1, t2);
        float tminy = Math.min(t3, t4);
        float tminz = Math.min(t5, t6);

        if (tminx > tminy) {
            if (tminx > tminz) {
                return new Vector3f(dirfrac.x < 0 ? 1 : -1, 0, 0); // x-axis normal
            } else {
                return new Vector3f(0, 0, dirfrac.z < 0 ? 1 : -1); // z-axis normal
            }
        } else {
            if (tminy > tminz) {
                return new Vector3f(0, dirfrac.y < 0 ? 1 : -1, 0); // y-axis normal
            } else {
                return new Vector3f(0, 0, dirfrac.z < 0 ? 1 : -1); // z-axis normal
            }
        }
    }

    @FunctionalInterface
    public interface HitBlockCriteria {

        boolean shouldHitBlock(short block, short forbiddenBlock, int x, int y, int z);
    }

    @FunctionalInterface
    public interface HitEntityCriteria {

        boolean shouldHitEntity(Entity entity);
    }

    public static void traceSimpleRay(
            Ray ray,
            Vector3f origin,
            Vector3f direction,
            int maxDistance,
            World chunks) {
        traceSimpleRay(ray, origin, direction, maxDistance, null, chunks);
    }

    /**
     * Trace a simple ray (don't account for block shape or entities)
     *
     * @param ray
     * @param origin the origin vector
     * @param direction the direction vector
     * @param maxDistance the max ray distance
     */
    public static void traceSimpleRay(
            Ray ray,
            Vector3f origin,
            Vector3f direction,
            int maxDistance,
            HitBlockCriteria blockCriteria,
            World chunks) {

        // <editor-fold defaultstate="collapsed" desc="Setting up">
        // consider raycast vector to be parametrized by t
        // vec = [px,py,pz] + t * [dx,dy,dz]
        // algo below is as described by this paper:
        // http://www.cse.chalmers.se/edu/year/2010/course/TDA361/grid.pdf
        ray.reset();
        ray.origin.set(origin);
        ray.direction.set(direction);

        float px = origin.x;
        float py = origin.y;
        float pz = origin.z;
        float dx = direction.x;
        float dy = direction.y;
        float dz = direction.z;

        float t = 0.0f;
        // Adjust initial voxel indices based on player's position
        int ix = (int) Math.floor(px);
        int iy = (int) Math.floor(py);
        int iz = (int) Math.floor(pz);

        int stepx = (dx > 0) ? 1 : -1;
        int stepy = (dy > 0) ? 1 : -1;
        int stepz = (dz > 0) ? 1 : -1;

        // dx,dy,dz are already normalized
        float txDelta = Math.abs(1f / dx);
        float tyDelta = Math.abs(1f / dy);
        float tzDelta = Math.abs(1f / dz);

        float txMax = txDelta * (stepx > 0 ? (ix + 1 - px) : (px - ix));
        float tyMax = tyDelta * (stepy > 0 ? (iy + 1 - py) : (py - iy));
        float tzMax = tzDelta * (stepz > 0 ? (iz + 1 - pz) : (pz - iz));

        float steppedIndex = -1;

        WCCi wcc = new WCCi();

        short forbiddenBlock = 0;
        Block headBlock = chunks.getBlock(ix, iy, iz);
        if (!headBlock.isSolid()) {
            forbiddenBlock = headBlock.id;
        }
        // </editor-fold>

        // main update along raycast vector
        while (t <= maxDistance) {
            wcc.set(ix, iy, iz);
            if (chunks.hasChunk(wcc.subChunk)
                    && !(ix == (int) origin.x && iy == (int) origin.y && iz == (int) origin.z)) {
                if (SubChunk.inBounds(
                        wcc.subChunkVoxel.x,
                        wcc.subChunkVoxel.y,
                        wcc.subChunkVoxel.z)) {

                    SubChunk chunk = chunks.getSubChunk(wcc.subChunk);
                    short block = chunk.data.getBlock(
                            wcc.subChunkVoxel.x,
                            wcc.subChunkVoxel.y,
                            wcc.subChunkVoxel.z);

                    if (blockCriteria == null
                            ? block != BlockList.BLOCK_AIR.id && block != forbiddenBlock
                            : blockCriteria.shouldHitBlock(block, forbiddenBlock,ix, iy, iz)) {
                        ray.hitTarget = true;
                        ray.hitPostition.x = ix;
                        ray.hitPostition.y = iy;
                        ray.hitPostition.z = iz;
                        if (steppedIndex == 0) {
                            ray.hitNormal.x = -stepx;
                        }
                        if (steppedIndex == 1) {
                            ray.hitNormal.y = -stepy;
                        }
                        if (steppedIndex == 2) {
                            ray.hitNormal.z = -stepz;
                        }
                        return;
                    }
                }
            }
            // advance t to next nearest voxel boundary
            if (txMax < tyMax) {
                if (txMax < tzMax) {
                    ix += stepx;
                    t = txMax;
                    txMax += txDelta;
                    steppedIndex = 0;
                } else {
                    iz += stepz;
                    t = tzMax;
                    tzMax += tzDelta;
                    steppedIndex = 2;
                }
            } else {
                if (tyMax < tzMax) {
                    iy += stepy;
                    t = tyMax;
                    tyMax += tyDelta;
                    steppedIndex = 1;
                } else {
                    iz += stepz;
                    t = tzMax;
                    tzMax += tzDelta;
                    steppedIndex = 2;
                }
            }

            // Accumulate the distance traveled
            ray.distanceTraveled = t;
        }

        ray.hitPostition.x = ix;
        ray.hitPostition.y = iy;
        ray.hitPostition.z = iz;
        if (steppedIndex == 0) {
            ray.hitNormal.x = -stepx;
        }
        if (steppedIndex == 1) {
            ray.hitNormal.y = -stepy;
        }
        if (steppedIndex == 2) {
            ray.hitNormal.z = -stepz;
        }
    }

    private static class AABBNode {

        List<AABB> boxes;
        Entity entity;

        public AABB getBox() {
            return boxes.get(0);
        }

        public AABBNode(List<AABB> boxes, Entity e) {
            this.boxes = boxes;
            this.entity = e;
        }

        public AABBNode(AABB box, Entity e) {
            this.boxes = new ArrayList<>();
            boxes.add(box);
            this.entity = e;
        }
    }

    public static void traceComplexRay(
            Ray ray,
            Vector3f origin,
            Vector3f direction,
            int maxDistance,
            HitBlockCriteria blockCriteria,
            HitEntityCriteria entityCriteria,
            World chunks) {

        // <editor-fold defaultstate="collapsed" desc="Setting up">
        // consider raycast vector to be parametrized by t
        // vec = [px,py,pz] + t * [dx,dy,dz]
        // algo below is as described by this paper:
        // http://www.cse.chalmers.se/edu/year/2010/course/TDA361/grid.pdf
        ray.reset();
        ray.origin.set(origin);
        ray.direction.set(direction);

        float px = origin.x;
        float py = origin.y;
        float pz = origin.z;
        float dx = direction.x;
        float dy = direction.y;
        float dz = direction.z;

        float t = 0.0f;
        // Adjust initial voxel indices based on player's position
        int ix = (int) Math.floor(px);
        int iy = (int) Math.floor(py);
        int iz = (int) Math.floor(pz);

        int stepx = (dx > 0) ? 1 : -1;
        int stepy = (dy > 0) ? 1 : -1;
        int stepz = (dz > 0) ? 1 : -1;

        // dx,dy,dz are already normalized
        float txDelta = Math.abs(1f / dx);
        float tyDelta = Math.abs(1f / dy);
        float tzDelta = Math.abs(1f / dz);

        // location of nearest voxel boundary, in units of t
        float txMax = txDelta * (stepx > 0 ? (ix + 1 - px) : (px - ix));
        float tyMax = tyDelta * (stepy > 0 ? (iy + 1 - py) : (py - iy));
        float tzMax = tzDelta * (stepz > 0 ? (iz + 1 - pz) : (pz - iz));

        float steppedIndex = -1;
        WCCi wcc = new WCCi();

        short forbiddenBlock = 0;
        Block headBlock = chunks.getBlock(ix, iy, iz);
        if (!headBlock.isSolid()) {
            forbiddenBlock = headBlock.id;
        }
        // </editor-fold>

        HashSet<SubChunk> traversedChunks = new HashSet<>();
        ArrayList<AABBNode> entityNodes = new ArrayList<>();

        // main update along raycast vector
        while (t <= maxDistance) {
            wcc.set(ix, iy, iz);
            if (chunks.hasChunk(wcc.subChunk)
                    && !(ix == (int) origin.x && iy == (int) origin.y && iz == (int) origin.z)) {

                if (SubChunk.inBounds(
                        wcc.subChunkVoxel.x,
                        wcc.subChunkVoxel.y,
                        wcc.subChunkVoxel.z)) {

                    SubChunk chunk = chunks.getSubChunk(wcc.subChunk);
                    if (!traversedChunks.contains(chunk)) {
                        //Add entities in chunk
                        for (int i = 0; i < chunk.entities.list.size(); i++) {
                            Entity entity = chunk.entities.list.get(i);
                            entityNodes.add(new AABBNode(entity.aabb.cursorBox, entity));
                        }
                        traversedChunks.add(chunk);
                    }

                    short block = chunk.data.getBlock(
                            wcc.subChunkVoxel.x,
                            wcc.subChunkVoxel.y,
                            wcc.subChunkVoxel.z);

                    boolean blockIsHittable = blockCriteria == null
                            ? block != BlockList.BLOCK_AIR.id && block != forbiddenBlock
                            : blockCriteria.shouldHitBlock(block, forbiddenBlock,ix, iy, iz);

                    if (blockIsHittable) {
                        Block realBlock = ItemList.getBlock(block);
                        BlockType blockType = ItemList.blocks.getBlockType(realBlock.type);

                        if (!blockType.isCubeShape() || !entityNodes.isEmpty()) {
                            AABBNode node = null;

                            //Compare the distances of the AABBs of the entity
                            float closestDistance = Float.MAX_VALUE;
                            for (int i = 0; i < entityNodes.size(); i++) {
                                AABBNode aabb = entityNodes.get(i);
                                if (entityCriteria == null || entityCriteria.shouldHitEntity(aabb.entity)) {
                                    float distance = getAABBIntersectionDistance(ray.origin, ray.direction,
                                            aabb.getBox().minPoint, aabb.getBox().maxPoint);
                                    if (distance < closestDistance) {
                                        closestDistance = distance;
                                        node = aabb;
                                    }
                                }
                            }
                            entityNodes.clear();

                            //Compare the AABBs of the voxel
                            ArrayList<AABB> voxelAABBs = new ArrayList<>();
                            BlockData data = chunk.data.getBlockData(wcc.subChunkVoxel.x, wcc.subChunkVoxel.y, wcc.subChunkVoxel.z);
                            blockType.getCursorBoxes((box) -> {
                                voxelAABBs.add(new AABB(box));
                            }, new AABB(), realBlock, data, ix, iy, iz);
                            for (int i = 0; i < voxelAABBs.size(); i++) {
                                AABB aabb = voxelAABBs.get(i);
                                float distance = getAABBIntersectionDistance(ray.origin, ray.direction, aabb.minPoint, aabb.maxPoint);
                                if (distance < closestDistance) {
                                    closestDistance = distance;
                                    node = new AABBNode(aabb, null);
                                }
                            }

                            if (node != null) {
                                if (node.entity != null) {
                                    ray.hitTarget = true;
                                    ray.entity = node.entity;
                                    ray.cursorBoxes = node.boxes;
                                    ray.hitNormal.set(getRayNormalFromAABB(ray.origin, ray.direction,
                                            node.getBox().minPoint, node.getBox().maxPoint));
//                                    System.out.println("Entity hit "+ray.entity.toString());
                                } else if (node.getBox() != null) {
                                    ray.hitTarget = true;
                                    ray.cursorBoxes = voxelAABBs;
                                    ray.hitNormal.set(getRayNormalFromAABB(ray.origin, ray.direction,
                                            node.getBox().minPoint, node.getBox().maxPoint));
                                }
                            }
                        } else {
                            ray.hitTarget = true;
                        }

                        if (ray.hitTarget) {
                            ray.hitPostition.x = ix;
                            ray.hitPostition.y = iy;
                            ray.hitPostition.z = iz;

                            if (steppedIndex == 0) {
                                ray.hitNormal.x = -stepx;
                            }
                            if (steppedIndex == 1) {
                                ray.hitNormal.y = -stepy;
                            }
                            if (steppedIndex == 2) {
                                ray.hitNormal.z = -stepz;
                            }
                            return;
                        }

                    }
                }
            }
            // advance t to next nearest voxel boundary
            if (txMax < tyMax) {
                if (txMax < tzMax) {
                    ix += stepx;
                    t = txMax;
                    txMax += txDelta;
                    steppedIndex = 0;
                } else {
                    iz += stepz;
                    t = tzMax;
                    tzMax += tzDelta;
                    steppedIndex = 2;
                }
            } else {
                if (tyMax < tzMax) {
                    iy += stepy;
                    t = tyMax;
                    tyMax += tyDelta;
                    steppedIndex = 1;
                } else {
                    iz += stepz;
                    t = tzMax;
                    tzMax += tzDelta;
                    steppedIndex = 2;
                }
            }
            // Accumulate the distance traveled
            ray.distanceTraveled = t;
        }

        //If we didnt hit anything, check the entity nodes anyway
        float closestDistance = ray.distanceTraveled;
        AABBNode node = null;
        for (int i = 0; i < entityNodes.size(); i++) {
            AABBNode aabb = entityNodes.get(i);
            float distance = getAABBIntersectionDistance(ray.origin, ray.direction,
                    aabb.getBox().minPoint, aabb.getBox().maxPoint);
            if (distance < closestDistance) {
                closestDistance = distance;
                node = aabb;
            }
        }
        if (node != null && node.entity != null) {
            ray.hitTarget = true;
            ray.entity = node.entity;
            ray.hitNormal.set(getRayNormalFromAABB(
                    ray.origin, ray.direction,
                    node.getBox().minPoint, node.getBox().maxPoint));
        } else {
            if (steppedIndex == 0) {
                ray.hitNormal.x = -stepx;
            }
            if (steppedIndex == 1) {
                ray.hitNormal.y = -stepy;
            }
            if (steppedIndex == 2) {
                ray.hitNormal.z = -stepz;
            }
            ray.hitPostition.x = ix;
            ray.hitPostition.y = iy;
            ray.hitPostition.z = iz;
        }
    }
}
