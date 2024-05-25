/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.player.blockPipeline;

import org.joml.Vector3i;


/**
 *
 * @author zipCoder933
 */
public class LocalChangeRecord {

    public LocalChangeRecord(Vector3i originPosition, Vector3i neighboringBlock, BlockHistory history) {
        this.changedBlockHistory = history;
        this.changedBlock = originPosition;
        this.targetBlock = neighboringBlock;
    }

    /**
     * @return the position of the changed block. (not the block that
     * will receive the localChange event)
     */
    public Vector3i getEventBlockPosition() {
        return changedBlock;
    }

    /**
     * @return the blockHistory
     */
    public BlockHistory getChangedBlockHistory() {
        return changedBlockHistory;
    }

    /**
     * @return the block that was called for the local change (The block that
     * will receive the local change event) block position of the local change
     * event
     */
    public Vector3i getTargetBlockPosition() {
        return targetBlock;
    }

    private BlockHistory changedBlockHistory;
    private Vector3i targetBlock, changedBlock;

    @Override
    public String toString() {
        return "changed: " + (changedBlock != null ? changedBlock.toString() : "null")
                + ", target: " + (targetBlock != null ? targetBlock.toString() : "null")
                + ", change history: " + (changedBlockHistory != null ? changedBlockHistory.toString() : "null");
    }
}
