/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.player.blockPipeline;

import com.xbuilders.engine.items.block.Block;

/**
 *
 * @author zipCoder933
 */
public class BlockHistory {

    Block prevBlock;
    Block newBlock;

    /**
     *
     * @param oldBlock the old block (before the block was placed)
     * @param newBlock the new block
     */
    public BlockHistory(Block oldBlock, Block newBlock) {
        this.prevBlock = oldBlock;
        this.newBlock = newBlock;
    }

    public Block getOld() {
        return prevBlock;
    }

    public Block getNew() {
        return newBlock;
    }

    @Override
    public String toString() {
        return "BlockHistory{" + "prevBlock=" + prevBlock + ", newBlock=" + newBlock + '}';
    }

}
