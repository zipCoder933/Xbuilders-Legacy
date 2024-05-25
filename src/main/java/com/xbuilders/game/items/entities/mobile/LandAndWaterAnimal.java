/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.mobile;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.math.TrigUtils;
import com.xbuilders.engine.utils.random.RandomUtils;
import com.xbuilders.engine.world.chunk.wcc.WCCf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import processing.core.PGraphics;

import static com.xbuilders.game.items.entities.mobile.AnimalAction.ActionType.*;

/**
 *
 * @author zipCoder933
 */
public abstract class LandAndWaterAnimal extends Animal {

    /**
     * @return the inWater
     */
    public boolean isInWater() {
        return inWater;
    }

    /**
     * @param includeEntityPenetrationInJumps the
     * includeEntityPenetrationInJumps to set
     */
    public void includeEntityPenetrationInJumps(boolean includeEntityPenetrationInJumps) {
        this.includeEntityPenetrationInJumps = includeEntityPenetrationInJumps;
    }

    /**
     * @return the walkAmt
     */
    public float getWalkAmt() {
        return walkAmt;
    }

    /**
     * @return the action
     */
    public AnimalAction getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(AnimalAction action) {
        this.action = action;
    }

    private float maxSpeed = 0.10f;
    private float activity = 0.8f;
    private float actionVelocity;
    private AnimalAction action;
    float dist2;
    private boolean inWater;
    private float walkAmt = 0;
    long lastJumpMS;
    private boolean includeEntityPenetrationInJumps = false;
    float yVelocity, rotationVelocity, forwardVelocity;

    public LandAndWaterAnimal(Vector3f aabbSize, float animalRadius) {
        super(aabbSize, true, animalRadius);
        setNeedsConstantSaving(true);
    }

    /**
     * @return the actionVelocity
     */
    public float getActionVelocity() {
        return actionVelocity;
    }

    /**
     * @return the activity
     */
    public float getActivity() {
        return activity;
    }

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

    /**
     * @param activity the activity to set
     */
    public void setActivity(float activity) {
        this.activity = MathUtils.clamp(activity, 0, 1);
    }

    @Override
    public void animalClicked() {
    }

    @Override
    public void onDestructionInitiated() {
        setAction(new AnimalAction(AnimalAction.ActionType.WALK, 10000));
        actionVelocity = maxSpeed * 0.8f;
    }

    @Override
    public void onDestructionCancel() {
        getPosHandler().jump();
        setAction(new AnimalAction(AnimalAction.ActionType.IDLE, 2000));
    }

    public abstract void renderAnimal(PGraphics g);

    public AnimalAction newRandomAction(Enum lastAction) {
        int val = getRandom().nextInt(AnimalAction.ActionType.values().length - 1);
        getRandom().nextInt(AnimalAction.ActionType.values().length - 1);
        AnimalAction.ActionType actionType = AnimalAction.getRandomActionType(getRandom().getRandom(), IDLE, WALK, TURN);

        if (lastAction != null) {
            AnimalAction.ActionType lastAction2 = (AnimalAction.ActionType) lastAction;
            if (actionType == lastAction2) {
                switch (lastAction2) {
                    case IDLE:
                        actionType = AnimalAction.ActionType.TURN;
                        break;
                    case TURN:
                        actionType = AnimalAction.ActionType.WALK;
                        break;
                    case FOLLOW:
                        actionType = AnimalAction.ActionType.TURN;
                        break;
                    default:
                        break;
                }
            }
        }

        long actionDuration = 0;

        if (distToPlayer < 5 && playerHasAnimalFeed()) {
            actionType = AnimalAction.ActionType.FOLLOW;
            tameAnimal();
            actionDuration = getRandom().nextLong(4000, 25000);
            actionVelocity = getMaxSpeed() / 2;
        } else if (actionType == AnimalAction.ActionType.TURN) {
            actionDuration = 50 + (getRandom().nextInt(100));
            setVelocityOfTurnAction();
        } else if (actionType == AnimalAction.ActionType.WALK) {
            actionDuration = 600 + (getRandom().nextInt(500) - 250);
            calculateWalkVelocity();
        } else {
            if (activity > 0.8) {
                actionDuration = RandomUtils.randInt(-40, 200);
                if (actionDuration < 0) {
                    actionDuration = 0;
                }
            } else {
                actionDuration = RandomUtils.randInt(50, 400);
            }
            actionDuration *= MathUtils.map(getActivity(), 0, 1, 5, 1);
            if (getRandom().noise(10) > MathUtils.map(getActivity(), 0, 1, -0.2f, 0.3f)) {
                actionDuration *= MathUtils.map(getActivity(), 0, 1, 10, 3);
            }
        }
        return new AnimalAction(actionType, actionDuration);
    }

    @Override
    public boolean move() {
        if (getPointerHandler().getApplet().frameCount % 5 == 0) {
            inWater = inWater();
        }
        setAlwaysInFrustum(false);

        if (isInWater()) {
            if (playerCanSeeAnimalUnderwater()) {
                moveInWater();
                return true;
            }
        } else {
            moveOnLand();
            return true;
        }

        return false;
    }

    public boolean playerCanSeeAnimalUnderwater() {
        if (distToPlayer < 10) {
            return true;
        } else if (!isInWater()
                || (isInWater() && VoxelGame.getGame().player.getBlockPlayerIsIn().isLiquid())) {
            return true;
        }
        return false;
    }

    public void walkForward(float speed) {
        if (isInWater()) {
            walkAmt = speed / 2;
        } else {
            walkAmt = speed;
        }
        WCCf wcc = new WCCf().set(worldPosition);
        if (wcc.chunkExists()) {
            Vector2f vec = TrigUtils.getCircumferencePoint(-getRotationY(), speed);
            worldPosition.add(vec.x, 0, vec.y);
        }
    }

    @Override
    public boolean moveWhenOutOfFrustum() {
        if (getAction() != null && getAction().getType() == AnimalAction.ActionType.FOLLOW) {
            return false;
        } else {
            walkForward(maxSpeed);
            walkAmt = 0;
            return true;
        }
    }

    @Override
    public void postProcessMovement() {
//        if (collisionOut != null) {
//            Vector3f penetration = collisionOut.getBlockPenatatration();
//            if (includeEntityPenetrationInJumps) {
//                penetration = new Vector3f(penetration).add(collisionOut.getEntityPenatatration());
//            }
//            if (Math.abs(penetration.x) > 0.02 || Math.abs(penetration.z) > 0.02) {
//                if (System.currentTimeMillis() - lastJumpMS > 400) {
//                    getPosHandler().jump(worldPosition);
//                    lastJumpMS = System.currentTimeMillis();
//                }
//            }
//            if (isInWater()) {
//                if (Math.abs(penetration.x) > 0.02 || Math.abs(penetration.z) > 0.02) {
//                    setRotationY(getRotationY() + rotationVelocity * 3);
//                }
//            }
//        }
    }

    private void calculateWalkVelocity() {
        if (getRandom().getSeed() > SEED_MAXIMUM * 0.7) {
            actionVelocity = getRandom().nextFloat(0.01f, getMaxSpeed() * 0.6f);
        } else {
            actionVelocity = getRandom().nextFloat(0.01f, getMaxSpeed());
        }
        if (activity < 0.66 && getRandom().nextBoolean()) {
            actionVelocity /= 3;
        }
    }

    private void setVelocityOfTurnAction() {
        float rotationAction = MathUtils.clamp(getActivity(), 0.2f, 1);
        actionVelocity = getRandom().noise(10) > 0 ? rotationAction * 10 : rotationAction * -10;
    }

    private void moveInWater() {
        getPosHandler().setGravityEnabled(false);
        if (action == null || action.pastDuration()) {
            if (action != null && action.getType() == AnimalAction.ActionType.FOLLOW) {
                action = new AnimalAction(FOLLOW, getRandom().nextLong(50, 2000));
            } else {
                action = new AnimalAction(SWIM, getRandom().nextLong(50, 2000));
            }
            rotationVelocity = getRandom().noise(2) * 3;
            forwardVelocity = (float) MathUtils.mapAndClamp(getRandom().noise(4), -0.5f, 1, 0.01f, getMaxSpeed());
            yVelocity = getRandom().noise(1, -0.06f, 0.05f);
        }
        if (isPendingDestruction()) {
            setRotationY(getRotationY() + rotationVelocity / 3);
        } else if (distToPlayer < 10 && playerHasAnimalFeed()) {
            tameAnimal();
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
            if (action.getType() == AnimalAction.ActionType.FOLLOW) {
                facePlayer();
            } else {
                setRotationY(getRotationY() + rotationVelocity);
            }
        }
        worldPosition.y += yVelocity;

        WCCf wcc = new WCCf().set(worldPosition);
        if (wcc.chunkExists()) {
            if (isPendingDestruction()) {
                walkForward(maxSpeed * 0.7f);
            } else {
                walkForward(forwardVelocity);
            }
        }
    }

    private void moveOnLand() {
        getPosHandler().setGravityEnabled(true);
        if (getAction() == null || getAction().pastDuration()) {
            if (getAction() == null) {
                setAction(newRandomAction(null));
            } else {
                setAction(newRandomAction(getAction().getType()));
            }
        }

        if (null != getAction().getType()) {
            switch (getAction().getType()) {
                case FOLLOW:
                    if (getAction().getTimeSinceCreatedMS() < 50 || getPointerHandler().getApplet().frameCount % 25 == 0) {
                        UserControlledPlayer userControlledPlayer = VoxelGame.getGame().player;
                        UserControlledPlayer userControlledPlayer1 = VoxelGame.getGame().player;
                        dist2 = (float) MathUtils.dist(worldPosition.x, worldPosition.z,
                                userControlledPlayer1.worldPos.x, userControlledPlayer.worldPos.z);
                    }
                    if (distToPlayer < 15 && playerHasAnimalFeed()) {
                        if (getAction().getTimeSinceCreatedMS() > 500
                                && dist2 > 4
                                && getRandom().noise(4) > -0.5f) {
                            walkForward(getActionVelocity());
                            setRotationY(getRotationY() + getRandom().noise(2f, -3, 3));
                        } else {
                            walkAmt = 0;
                            if (dist2 < 4) {
                                eatAnimalFeed();
                            }
                        }
                        facePlayer();
                    } else {
                        action = new AnimalAction(AnimalAction.ActionType.IDLE, 500);
                    }
                    break;
                case TURN:
                    setRotationY(getRotationY() + getActionVelocity());
                    walkAmt = 0;
                    break;
                case WALK:
                    if (isPendingDestruction() && getRandom().nextFloat() > 0.6) {
                        setRotationY(getRotationY() + getRandom().nextFloat() * 2);
                    }
                    walkForward(getActionVelocity());
                    break;
                default:
                    walkAmt = 0;
                    break;
            }
        }
    }

}
