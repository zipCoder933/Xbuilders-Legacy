/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.worldInteraction.collision;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.Player;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.world.World;
import com.xbuilders.game.Main;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zipCoder933
 */
public class PositionHandler {

    final static boolean DRAW_COLLISION_CANDIDATES = false;
    final static float BLOCK_COLLISION_CANDIDATE_CHECK_RADIUS = 2;
    final static float ENTITY_COLLISION_CANDIDATE_CHECK_RADIUS = 10;
    public float stepHeight = 0.6f;
    public final boolean DRAW_ENTITY_BOX = false;

    public boolean isFrozen() {
        return frozen && collisionsEnabled;
    }

    /**
     * @param frozen the frozen to setPosAndSize
     */
    protected void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * @return the gravityEnabled
     */
    public boolean isGravityEnabled() {
        return gravityEnabled;
    }

    /**
     * @param gravityEnabled the gravityEnabled to setPosAndSize
     */
    public void setGravityEnabled(boolean gravityEnabled) {
        this.gravityEnabled = gravityEnabled;
    }

    public Vector3f velocity;
    private boolean frozen = false;
    private boolean gravityEnabled;
    public boolean onGround;
    public final BaseWindow window;
    public final float friction = 0.75f;
    public static float fallConstant = 0.0003f;
    public static float jumpConstant = fallConstant * 20;
    public boolean collisionsEnabled = true;
    EntityAABB aabb;
    public CollisionHandler collisionHandler;
    UserControlledPlayer player;


    public PositionHandler(World chunks, BaseWindow window,
                           EntityAABB thisAABB,
                           UserControlledPlayer player,
                           List<Player> playerList) {

        this.player = player;
        this.window = window;
        this.velocity = new Vector3f(0f, 0f, 0f);
        frozen = false;
        gravityEnabled = true;
        this.aabb = thisAABB;
        collisionsEnabled = true;
        collisionHandler = new CollisionHandler(chunks, this,
                thisAABB, player, playerList);
    }

    public Vector4f color = new Vector4f(0, 0, 0, 1);
    public Block blockAtPosition = BlockList.BLOCK_AIR;
    private Block blockAtLastPosition = BlockList.BLOCK_AIR;


    public void update() {
        blockAtPosition = VoxelGame.getWorld().getBlock((int)
                Math.floor(aabb.worldPosition.x), (int)
                Math.floor(aabb.worldPosition.y), (int)
                Math.floor(aabb.worldPosition.z));

        if (blockAtPosition != blockAtLastPosition) {
            blockAtLastPosition = blockAtPosition;
            if (!blockAtPosition.isAir()) {
                velocity.set(0, 0, 0);
            }
        }

        aabb.updateBox();
        if (DRAW_ENTITY_BOX) {
            aabb.box.draw(Main.getPG());
        }

        if (!isFrozen()) {
            velocity.x *= friction;
            velocity.z *= friction;
            if (collisionsEnabled && isGravityEnabled()) {
                float fallSpeed = (float) (fallConstant * window.getMsPerFrame());

                if (blockAtPosition.isLiquid() && fallSpeed > 0.001) fallSpeed = 0.001f;

                this.velocity.sub(0, fallSpeed, 0);
            } else {
                velocity.y *= friction;
            }
            aabb.box.setX(aabb.box.minPoint.x + velocity.x);
            aabb.box.setY(aabb.box.minPoint.y - velocity.y);
            aabb.box.setZ(aabb.box.minPoint.z + velocity.z);
        }
        if (collisionsEnabled) {
            //Disabling player collisions for an entity, if the player is riding the entity, is VITALLY important
            if (player.positionLock != null //If this is an entity and the player is riding this
                    && player.positionLock.entity.aabb == aabb) {
//                System.out.println("RIDE");
                collisionHandler.resolveCollisions(false);
            } else {
                collisionHandler.resolveCollisions(true);
            }

        }
        aabb.worldPosition.x = aabb.box.minPoint.x - aabb.offset.x;
        aabb.worldPosition.y = aabb.box.minPoint.y - aabb.offset.y;
        aabb.worldPosition.z = aabb.box.minPoint.z - aabb.offset.z;
    }

    public final void jump() {
        if (onGround && isGravityEnabled()) {
            this.velocity.y += jumpConstant * window.getMsPerFrame();
            onGround = false;
        }
    }

}
