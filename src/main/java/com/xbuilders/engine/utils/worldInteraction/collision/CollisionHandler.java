package com.xbuilders.engine.utils.worldInteraction.collision;

import com.xbuilders.engine.player.Player;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.Main;
import org.lwjgl.system.MemoryStack;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import static com.xbuilders.engine.utils.worldInteraction.collision.PositionHandler.*;

/**
 * @author zipCoder933
 */
public class CollisionHandler {


    //Collision handler variables
    final private PositionHandler driver;
    final World chunks;
    final WCCi wcc = new WCCi();
    final HashSet<SubChunk> exploredChunks = new HashSet<>();
    final EntityAABB myBox;
    UserControlledPlayer player;
    final List<Player> playerList;
    final AABB stepBox;
    final AABB collisionBox;
    public final CollisionData collisionData;
    boolean setFrozen = false;
    final Consumer<AABB> customConsumer;
    Block b;
    BlockData d;
    SubChunk subChunk;


    public CollisionHandler(World chunks, PositionHandler driver,
                            EntityAABB entityBox,
                            UserControlledPlayer player,
                            List<Player> playerList) {

        this.player = player;
        this.playerList = playerList;
        this.chunks = chunks;
        this.myBox = entityBox;
        this.driver = driver;
        collisionData = new CollisionData();
        stepBox = new AABB();
        collisionBox = new AABB();
        customConsumer = box -> {
            processBox(box, false); //This is not part of the problem
            if (DRAW_COLLISION_CANDIDATES) {
                box.draw(Main.getPG());
            }
        };
    }

    private boolean compareEntityAABB(EntityAABB entityBox) {
        if (entityBox != myBox && entityBox.collisionEnabled) {
            if (myBox.worldPosition.distance(entityBox.worldPosition) < ENTITY_COLLISION_CANDIDATE_CHECK_RADIUS) {
                processBox(entityBox.box, true);
                if (DRAW_COLLISION_CANDIDATES) {
                    entityBox.box.draw(Main.getPG());
                }
                return true;
            }
        }
        return false;
    }

    protected void resolveCollisions(boolean checkPlayerAABB) {
        collisionData.sideCollision = false;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            stepBox.set(myBox.box);
            stepBox.setY(stepBox.maxPoint.y + driver.stepHeight);
            setFrozen = false;
            exploredChunks.clear();

            //Y goes down so that we can sort blocks from top (ceiling) to bottom
            for (int y = (int) (myBox.box.maxPoint.y + BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS); y >= myBox.box.minPoint.y - BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS; y--) {
//        for (int y = (int) (myBox.box.minPoint.y - BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS); y <= myBox.box.maxPoint.y + BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS; y++) {
                for (int x = (int) (myBox.box.minPoint.x - BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS); x <= myBox.box.maxPoint.x + BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS; x++) {
                    for (int z = (int) (myBox.box.minPoint.z - BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS); z <= myBox.box.maxPoint.z + BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS; z++) {
                        wcc.set(x, y, z);
                        subChunk = chunks.getSubChunk(wcc.subChunk);
                        if (subChunk == null) {
                            AABB chunkAABB=new AABB(stack);
                            chunkAABB.setPosAndSize(
                                    wcc.subChunk.x*SubChunk.WIDTH,
                                    wcc.subChunk.y*SubChunk.WIDTH,
                                    wcc.subChunk.z*SubChunk.WIDTH,
                                    SubChunk.WIDTH, SubChunk.WIDTH, SubChunk.WIDTH);
                            customConsumer.accept(chunkAABB);
                        } else {
                            exploredChunks.add(subChunk);
//                            if (Main.specialMode1) {
                            b = ItemList.blocks.getItem(subChunk.data.getBlock(
                                    wcc.subChunkVoxel.x,
                                    wcc.subChunkVoxel.y,
                                    wcc.subChunkVoxel.z));
                            if (b.isSolid()) {
//                                    if (Main.specialMode2) {
                                //TODO: chunk.getBlockData() is the memory culprit!!!
                                //Its ALL in the hashmap...
                                d = subChunk.data.getBlockData(
                                        wcc.subChunkVoxel.x,
                                        wcc.subChunkVoxel.y,
                                        wcc.subChunkVoxel.z);
//                                    }
                                ItemList.blocks.getBlockType(b.type).getCollisionBoxes(customConsumer, collisionBox, b, d, x, y, z);
                            }
//                            }
                        }
                    }
                }
            }

            for (SubChunk chunk : exploredChunks) {
                for (int i = 0; i < chunk.entities.list.size(); i++) {
                    compareEntityAABB(chunk.entities.list.get(i).aabb);
                }
            }
            for (int i = 0; i < playerList.size(); i++) {
                compareEntityAABB(playerList.get(i).aabb);
            }
            //Comparison against user controlled player (all entity and player boxes are skipped if they match themselves)
            if (checkPlayerAABB) compareEntityAABB(player.aabb);
            driver.setFrozen(setFrozen);
        }
    }

    private void processBox(AABB box, boolean isEntity) {
//        if (stepBox.intersects(box)) {
//            stepWillHitCeiling = true;
//            System.out.println("STEP HIT CEILING " + System.currentTimeMillis());
//        }

        if (box.intersects(myBox.box)) {
            collisionData.calculateCollision(box, myBox.box);

            if (isEntity) {
                collisionData.penPerAxes.mul(0.8f);
            }

            if (Math.abs(collisionData.penPerAxes.x) > 0.7f
                    || Math.abs(collisionData.penPerAxes.y) > 0.7f
                    || Math.abs(collisionData.penPerAxes.z) > 0.7f) {
                driver.velocity.y = 0;
                setFrozen = true;
                return;
            }

            if (collisionData.collisionNormal.y == -1) {
                driver.velocity.y = 0;
                driver.onGround = true;
                myBox.box.setY(myBox.box.minPoint.y + collisionData.penPerAxes.y);
            } else if (collisionData.collisionNormal.y == 1 && box.minPoint.y < myBox.box.minPoint.y) {
                driver.velocity.y = 0;
                myBox.box.setY(myBox.box.minPoint.y + collisionData.penPerAxes.y);
            } else if (collisionData.collisionNormal.x != 0) {
                if (myBox.box.maxPoint.y - box.minPoint.y < driver.stepHeight) {
                    myBox.box.setY(myBox.box.minPoint.y - Math.abs(collisionData.penPerAxes.x));
                } else {
                    myBox.box.setX(myBox.box.minPoint.x + collisionData.penPerAxes.x);
                    collisionData.sideCollision = true;
                    collisionData.sideCollisionIsEntity = isEntity;
                }
            } else if (collisionData.collisionNormal.z != 0) {
                if (myBox.box.maxPoint.y - box.minPoint.y < driver.stepHeight) {
                    myBox.box.setY(myBox.box.minPoint.y - Math.abs(collisionData.penPerAxes.z));
                } else {
                    myBox.box.setZ(myBox.box.minPoint.z + collisionData.penPerAxes.z);
                    collisionData.sideCollision = true;
                    collisionData.sideCollisionIsEntity = isEntity;
                }
            }
        }
    }
}
