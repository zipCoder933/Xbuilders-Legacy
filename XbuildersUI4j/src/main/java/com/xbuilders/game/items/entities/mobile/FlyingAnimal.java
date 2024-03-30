package com.xbuilders.game.items.entities.mobile;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.MathUtils;
import org.joml.Vector3f;

import static com.xbuilders.game.items.entities.mobile.AnimalAction.ActionType.*;

/**
 *
 * @author zipCoder933
 */
public abstract class FlyingAnimal extends Animal {

    /**
     * @return the flyAnimationSpeed
     */
    public float getFlyAnimationSpeed() {
        return flyAnimationSpeed;
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

    /**
     * @return the activity
     */
    public float getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(float activity) {
        this.activity = activity;
    }

    public FlyingAnimal( Vector3f aabbSize, float frustumSphereRadius) {
        super( aabbSize, true, frustumSphereRadius);
        action = new AnimalAction(IDLE, 0);
    }

    private float actionVal1, actionVal2, speedCurve;
    private float activity;
    private AnimalAction action;

    public AnimalAction newRandomAction(Enum lastAction) {
        AnimalAction.ActionType actionType = AnimalAction.getRandomActionType(getRandom().getRandom(), IDLE, WALK, TURN);
        if (lastAction != null) {
            AnimalAction.ActionType lastAction2 = (AnimalAction.ActionType) lastAction;
            if (actionType == lastAction2) {
                switch (lastAction2) {
                    case IDLE:
                        actionType = AnimalAction.ActionType.FLY;
                        break;
                    case TURN:
                        actionType = AnimalAction.ActionType.WALK;
                        break;
                    case WALK:
                        actionType = AnimalAction.ActionType.FLY;
                        break;
                    default:
                        break;
                }
            }
        }

        long actionDuration = 0;

        if (distToPlayer < 20 && playerHasAnimalFeed()) {
            actionType = AnimalAction.ActionType.FOLLOW;
            tameAnimal();
            actionDuration = getRandom().nextLong(4000, 25000);
        } else if (actionType == AnimalAction.ActionType.TURN) {
            actionDuration = getRandom().nextInt(100, 200);
            actionVal1 = (getRandom().nextBoolean() ? 1 : -1);
        } else if (actionType == AnimalAction.ActionType.WALK) {
            actionDuration = getRandom().nextInt(50, 800);
        } else if (actionType == AnimalAction.ActionType.FLY) {

            actionVal1 = getRandom().nextFloat(0.05f, 0.15f);//Vertical velocity
            actionVal2 = getRandom().nextFloat(0.0f, 0.15f);//Horizontal velocity

            if (actionVal1 > 0.10f) {
                actionDuration = getRandom().nextInt(500, 2000);
            } else {
                actionDuration = getRandom().nextInt(500, 8000);
            }

        } else {
            actionDuration = getRandom().nextInt(-100, (int) MathUtils.map(getActivity(), 0.0f, 1.0f, 15000, 200));
            if (actionDuration < 0) {
                actionDuration = 0;
            }
        }

        return new AnimalAction(actionType, actionDuration);
    }


    @Override
    public void animalClicked() {
    }

    private float flyAnimationSpeed = 0;

    @Override
    public boolean move() {
        if (action.pastDuration()) {
            action = newRandomAction(action.getType());
//            System.out.println("Action: " + action.toString());
        }
        if (!getPosHandler().isGravityEnabled()) {
            worldPosition.y += 0.05f;
        }

        if (!getPosHandler().onGround || !getPosHandler().isGravityEnabled() || getRandom().noise(1f) > 0.85) {
            flyAnimationSpeed = 0.5f;
        } else {
            flyAnimationSpeed = 0f;
        }

        if (null != action.getType()) {
            if (getPosHandler().isGravityEnabled()
                    && getRandom().nextFloat() > 0.85) {
                getPosHandler().jump();
            }

            switch (action.getType()) {
                case TURN:
                    if (actionVal1 > 0) {
                        setRotationY(getRotationY() + 6f);
                    } else {
                        setRotationY(getRotationY() - 6f);
                    }
                    break;
                case WALK:
                    goForward(0.05f);
                    break;
                case FLY:
                    getPosHandler().setGravityEnabled(false);
                    if (actionVal2 > 0.02) {
                        addToRotationY(getRandom().noise(2, -5f, 5f));
                        goForward(actionVal2);
                    }

                    if (getPosHandler().onGround) {
                        worldPosition.y -= actionVal1 * 2;
                    } else {
                        worldPosition.y -= actionVal1;
                    }
                    break;
                case FOLLOW:
                    if (distToPlayer < 25 && playerHasAnimalFeed()) {
                        facePlayer();
                        addToRotationY(getRandom().noise(1, -2, 2));
                        if (distToPlayer > 2 || getRandom().nextBoolean()) {
                            actionVal1 = getRandom().noise(1, 0.01f, 0.2f);
                        } else {
                            actionVal1 = 0.0f;
                        }
                        speedCurve = (float) MathUtils.curve(speedCurve, actionVal1, 0.1f);
                        goForward(speedCurve);

                        getPosHandler().setGravityEnabled(false);

                        UserControlledPlayer userControlledPlayer = getPointerHandler().getPlayer();
                        float playerY = userControlledPlayer.worldPos.y;
                        float playerPos = playerY + getRandom().noise(1, -2, 2);
                        worldPosition.y = (float) MathUtils.curve(worldPosition.y, playerPos, 0.05f);
                        eatAnimalFeed();
                    } else {
                        action = newRandomAction(action.getType());
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean moveWhenOutOfFrustum() {
        return false;
    }

    @Override
    public void postProcessMovement() {
        if (getPosHandler().onGround && action.getType() != FLY) {
            getPosHandler().setGravityEnabled(true);
        }
    }

    @Override
    public void onDestructionInitiated() {
        action = new AnimalAction(FLY, 10000);
        actionVal1 = 0.15f;//Vertical velocity
        actionVal2 = 0.15f;//Horizontal velocity
    }

    @Override
    public void onDestructionCancel() {
    }

}
