/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.mobile;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.utils.math.TrigUtils;
import com.xbuilders.engine.utils.worldInteraction.collision.PositionHandler;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.GameItems;
import org.joml.Vector2f;
import org.joml.Vector3f;
import processing.core.PGraphics;

public abstract class Vehicle extends Entity {

    /**
     * Resets the range of the degree back to 0-360
     */
    public float normalizeRotation(float rotationYDeg) {
        return rotationYDeg % 360;
    }

    /**
     * @return the distToPlayer
     */
    public float get3DDistToPlayer() {
        return distToPlayer;
    }

    public boolean isOnRoad() {
        int x = Math.round(worldPosition.x);
        int y = Math.round(worldPosition.y);
        int z = Math.round(worldPosition.z);
        Block b1 = getPointerHandler().getWorld().getBlock(x, y, z);
        Block b2 = getPointerHandler().getWorld().getBlock(x, y + 1, z);

        return (b1 == GameItems.MINECART_ROAD || b1 == GameItems.MINECART_ROAD_SLAB
                || b2 == GameItems.MINECART_ROAD || b2 == GameItems.MINECART_ROAD_SLAB);
    }

    public float rotationYDeg = 0;
    public final PositionHandler posHandler;
    private boolean collisionEnabled = true;
    public Vector3f renderOffset;
    public boolean jumpWithSideCollision = false;

    public Vehicle(Vector3f aabbSize, boolean centeredOnGround) {
        super();
        posHandler = new PositionHandler(VoxelGame.getWorld(), Main.getMain(), aabb,
                VoxelGame.getPlayer(), VoxelGame.playerList);
        float renderOffsetX, renderOffsetY, renderOffsetZ;
        if (aabbSize.x <= 1) {
            renderOffsetX = 0.5f;
        } else {
            renderOffsetX = 0.5f - (1 - aabbSize.x);
        }
        if (centeredOnGround) {
            if (aabbSize.y <= 1) {
                renderOffsetY = 1.0f;
            } else {
                renderOffsetY = 1.0f - (1 - aabbSize.y);
            }
        } else {
            if (aabbSize.y <= 1) {
                renderOffsetY = 0.5f;
            } else {
                renderOffsetY = 0.5f - (1 - aabbSize.y);
            }
        }
        if (aabbSize.z <= 1) {
            renderOffsetZ = 0.5f;
        } else {
            renderOffsetZ = 0.5f - (1 - aabbSize.z);
        }
        this.renderOffset = new Vector3f(renderOffsetX, renderOffsetY, renderOffsetZ);

    }

    /**
     * @return the collisionEnabled
     */
    public boolean isCollisionEnabled() {
        return collisionEnabled;
    }

    /**
     * @param collisionEnabled the collisionEnabled to setBlock
     */
    public void setCollisionEnabled(boolean collisionEnabled) {
        this.collisionEnabled = collisionEnabled;
    }

    public float getAngleToPlayer() {
        UserControlledPlayer userControlledPlayer = VoxelGame.getGameScene().player;
        UserControlledPlayer userControlledPlayer1 = VoxelGame.getGameScene().player;
        return TrigUtils.getAngleOfPoints(worldPosition.x, worldPosition.z, userControlledPlayer1.worldPos.x,
                userControlledPlayer.worldPos.z);
    }

    public abstract void renderMob(PGraphics g);

    /**
     * @return the entity actually moved
     */
    public abstract boolean move();

    private long lastJumpMS = 0;

    @Override
    public boolean update() {
        if (playerIsRidingThis() || distToPlayer < SubChunk.WIDTH) {
            aabb.updateBox();
            if (move()) {
                posHandler.update();
                if (posHandler.collisionHandler.collisionData.sideCollision
                        && !posHandler.collisionHandler.collisionData.sideCollisionIsEntity
                        && jumpWithSideCollision) {
                    if (System.currentTimeMillis() - lastJumpMS > 200 && posHandler.onGround) {
                        posHandler.jump();
                        lastJumpMS = System.currentTimeMillis();
                    }
                }
                if (hasMoved) {
                    markAsModifiedByUser();
                }
            }
        }
        return true;
    }

    @Override
    public void draw(PGraphics g) {
        modelMatrix.translate(renderOffset.x, renderOffset.y, renderOffset.z);
        modelMatrix.rotateY((float) (rotationYDeg * (Math.PI / 180)));
        sendModelMatrixToShader();
        renderMob(g);
    }

    @Override
    public String toString() {
        return "mob \"" + this.link.name + "\" (hash=" + this.hashCode() + ")";
    }

    public UserControlledPlayer getPlayer() {
        return VoxelGame.getGameScene().player;
    }

    public void goForward(float amount, float rotationDegrees) {
        Vector2f vec = TrigUtils.getCircumferencePoint(-rotationDegrees, amount);
        worldPosition.add(vec.x, 0, vec.y);
    }

    public void goForward(float amount) {
        Vector2f vec = TrigUtils.getCircumferencePoint(-rotationYDeg, amount);
        worldPosition.add(vec.x, 0, vec.y);
    }

    public abstract void onDestructionInitiated();

    public abstract void onDestructionCancel();

}
