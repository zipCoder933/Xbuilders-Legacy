/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.mobile;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.math.TrigUtils;
import com.xbuilders.engine.world.wcc.WCCf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import processing.core.PGraphics;

/**
 *
 * @author zipCoder933
 */
public abstract class FishAnimal<ActionEnum> extends Animal {

    public FishAnimal(Vector3f aabbSize) {
        super( aabbSize, false, 1.5f);
        enableCollisionWithPlayer(false);
        setNeedsConstantSaving(false);
    }

    public abstract void renderFish(PGraphics g);

    public enum ActionEnum {
        SWIM,
    };

    /**
     * @return the maxSpeed
     */
    public float getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * @param maxSpeed the maxSpeed to set
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }



    @Override
    public void onDestructionInitiated() {
    }

    @Override
    public void onDestructionCancel() {
    }

    @Override
    public void initAnimal(byte[] bytes) {
        lastInWater = System.currentTimeMillis();
        inWater = inWater();
    }

    private float maxSpeed = 0.17f;
    private float rotationVelocity;

    private long lastActionChange;
    private long lastInWater;

    float forwardVelocity = 0;
    boolean inWater = false;
    float yVelocity;
    long actionDuration;

    @Override
    public boolean move() {
        if (getPointerHandler().getApplet().frameCount % 5 == 0) {
            inWater = inWater();
        }
        if (playerIsInSameMediumAsFish()) {
            if (System.currentTimeMillis() - lastActionChange > actionDuration) {
                rotationVelocity = getRandom().noise(2) * 2;
                forwardVelocity = (float) MathUtils.mapAndClamp(getRandom().noise(4), -0.5f, 1, 0.01f, getMaxSpeed());
                yVelocity = getRandom().noise(1, -0.06f, 0.05f);
                actionDuration = getRandom().nextLong(50, 2000);
                lastActionChange = System.currentTimeMillis();
            }

            if (inWater) {

                lastInWater = System.currentTimeMillis();
                getPosHandler().setGravityEnabled(false);
                if (isPendingDestruction()) {
                    setRotationY(getRotationY() + rotationVelocity / 3);
                } else if (distToPlayer < 10 && playerHasAnimalFeed()) {
                    tameAnimal();

                    UserControlledPlayer userControlledPlayer = getPointerHandler().getPlayer();
                    float playerY = userControlledPlayer.worldPos.y;
                    float playerPos = playerY + getRandom().noise(1, -2, 2);
                    worldPosition.y = (float) MathUtils.curve(worldPosition.y, playerPos, 0.05f);

                    float distAngle = TrigUtils.getSignedAngleDistance(getAngleToPlayer(), getRotationY());
                    if (Math.abs(distAngle) > 170) {
                        setRotationY(distAngle);
                    } else {
                        setRotationY(getRotationY() + distAngle * 0.1f);
                    }
                    if (distToPlayer < 3) {
                        eatAnimalFeed();
                    }
                } else {
                    setRotationY(getRotationY() + rotationVelocity);
                }
                worldPosition.y += yVelocity;
            } else {
                yVelocity = 0.02f;
                worldPosition.y += yVelocity;
                getPosHandler().setGravityEnabled(true);
                forwardVelocity = 0.01f;
                setRotationY(getRotationY() + getRandom().noise(20) * 30);
            }

            WCCf wcc = new WCCf().set(worldPosition);
            if (wcc.chunkExists()) {
                if (isPendingDestruction() && inWater) {
                    Vector2f vec = TrigUtils.getCircumferencePoint(-getRotationY(), maxSpeed * 0.7f);
                    worldPosition.add(vec.x, 0, vec.y);
                } else {
                    Vector2f vec = TrigUtils.getCircumferencePoint(-getRotationY(), forwardVelocity);
                    worldPosition.add(vec.x, 0, vec.y);
                }
            }
            if (isTamed() && System.currentTimeMillis() - lastInWater > 10000) {
                makeAnimalLeave(true);
            }
        }
        return true;
    }

    private boolean playerIsInSameMediumAsFish() {
        if (distToPlayer < 6) {
            return true;
        } else if (!inWater
                || (inWater && VoxelGame.getGameScene().player.getBlockPlayerIsIn().isLiquid())) {
            return true;
        }
        return false;
    }

    @Override
    public final void renderAnimal(PGraphics g) {
        if (playerIsInSameMediumAsFish()) {
            if (inWater) {
                float waggle = (float) (Math.sin(getPointerHandler().getApplet().frameCount / 2) * MathUtils.mapAndClamp(forwardVelocity, 0, maxSpeed, 0, 0.25f));
                modelMatrix.rotateY(waggle);
                modelMatrix.translate(waggle * -0.1f, 0, 0);
                sendModelMatrixToShader();
            }
            renderFish(g);
        }
    }

    @Override
    public boolean moveWhenOutOfFrustum() {
//        if (getPointerHandler().getApplet().frameCount % 5 == 0) {
//            inWater = inWater();
//        }
//        if (playerIsInSameMediumAsFish()) {
//            if (System.currentTimeMillis() - lastActionChange > actionDuration) {
//                rotationVelocity = getRandom().noise(2) * 2;
//                actionDuration = 100;
//                lastActionChange = System.currentTimeMillis();
//            }
//            if (inWater) {
//                lastInWater = System.currentTimeMillis();
//                getPosHandler().setGravityEnabled(false);
//                rotationY += rotationVelocity;
//                worldPosition.y += yVelocity;
//            } else {
//                getPosHandler().setGravityEnabled(true);
//                forwardVelocity = 0.01f;
//                rotationY += getRandom().noise(20) * 30;
//            }
//            WCCi wcc = VoxelGame.getWorld().getWCCfromWorldVoxelPos(new Vector3i(worldPosition));
//            boolean hasCurrentChunk = VoxelGame.getWorld().getChunks().hasChunk(wcc.getChunkCoords());
//            if (hasCurrentChunk) {
//                float speed = maxSpeed * 0.8f;
//                worldPosition.add(MiscUtils.getCircumferencePoint(worldPosition.x, worldPosition.z, -rotationY, speed));
//            }
//            if (isSetByUser() && System.currentTimeMillis() - lastInWater > 10000) {
//                setPendingDestruction(true);
//            }
//        }
        return false;
    }

    long lastJumpMS;

    @Override
    public void postProcessMovement() {
        if (inWater) {
//            if (pen != null) {
//                if (Math.abs(pen.getBlockPenatatration().x) > 0.02 || Math.abs(pen.getBlockPenatatration().z) > 0.02) {
//                    setRotationY(getRotationY() + rotationVelocity * 20);
//                }
//            }
        } else {
            if (System.currentTimeMillis() - lastJumpMS > 300) {
                getPosHandler().jump();
                lastJumpMS = System.currentTimeMillis();
            }
        }
    }

}
