/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.mobile;

import java.util.Random;

/**
 *
 * @author zipCoder933
 */
public class AnimalAction {

    public enum ActionType {
        TURN,
        IDLE,
        WALK,
        FOLLOW,
        SWIM,
        CLIMB,
        JUMP,
        FLY,
        OTHER
    };

    public static ActionType getRandomActionType(Random random, ActionType... types) {
        int indx = random.nextInt(types.length);
        return types[indx];
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    public AnimalAction(ActionType type, long duration) {
        this.type = type;
        this.duration = duration;
        this.lastActionMS = System.currentTimeMillis();
    }

    public long getTimeSinceCreatedMS() {
        return System.currentTimeMillis() - lastActionMS;
    }

    public boolean pastDuration() {
        return getTimeSinceCreatedMS() > getDuration();
    }

    private ActionType type;
    private long duration;
    long lastActionMS;

    /**
     * @return the type
     */
    public ActionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AnimalAction{" + "type=" + type + ", duration=" + duration + '}';
    }

}
