/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import java.util.Objects;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
class BlockToSet {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.coords);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BlockToSet other = (BlockToSet) obj;
        return Objects.equals(this.coords, other.coords);
    }

    public BlockToSet(Block block, Block prevBlock, Vector3i coords, BlockData orientaiton) {
        this.block = block;
        this.prevBlock = prevBlock;
        this.coords = new Vector3i(coords);
        this.blockData = orientaiton;
    }

    public Block block, prevBlock;
    public Vector3i coords;
    public BlockData blockData;
}
