// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.terrain.defaultTerrain;

import java.util.Random;

import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.world.World;
import com.xbuilders.game.terrain.Terrain;
import org.joml.Vector3i;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.BFS.TravelNode;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blocks.plants.trees.OakTreeUtils;
import com.xbuilders.game.items.blocks.plants.trees.BirchTreeUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.game.items.GameItems;

public class DefaultTerrainUtils {

    int WATER_LEVEL;
    Terrain terrain;

    public void plantBirchOrOakTree(final Chunk sourceChunk, final int x, final int y, final int z) {
        if (this.terrain.getRandom().nextBoolean()) {
            BirchTreeUtils.plantTree(this.terrain, sourceChunk, x, y, z);
        } else {
            OakTreeUtils.plantTree(this.terrain, sourceChunk, x, y, z);
        }
    }

    public void placeHorse(final Chunk chunk, final int cx, final int wy, final int cz) {
        switch (this.terrain.getRandom().nextInt(6)) {
            case 0: {
                this.terrain.setEntity(chunk, GameItems.CHESTNUT_HORSE, cx, wy, cz);
                break;
            }
            case 1: {
                this.terrain.setEntity(chunk, GameItems.BROWN_HORSE, cx, wy, cz);
                break;
            }
            case 2: {
                this.terrain.setEntity(chunk, GameItems.WHITE_HORSE, cx, wy, cz);
                break;
            }
            case 3: {
                this.terrain.setEntity(chunk, GameItems.BLACK_HORSE, cx, wy, cz);
                break;
            }
            case 4: {
                this.terrain.setEntity(chunk, GameItems.CREAMY_HORSE, cx, wy, cz);
                break;
            }
            case 5: {
                this.terrain.setEntity(chunk, GameItems.DARK_BROWN_HORSE, cx, wy, cz);
                break;
            }
            case 6: {
                this.terrain.setEntity(chunk, GameItems.GRAY_HORSE, cx, wy, cz);
                break;
            }
        }
    }

    public DefaultTerrainUtils(final Terrain terrain, final int WATER_LEVEL) {
        this.terrain = terrain;
        this.WATER_LEVEL = WATER_LEVEL;
    }

    public boolean isStone(final Block b) {
        if (b != GameItems.BLOCK_DIORITE) {
            if (b != GameItems.BLOCK_ANDESITE) {
                if (b != GameItems.BlockCobblestone) {
                    if (b != GameItems.RedSandstone) {
                        return false;
                    }
                }
            }
        }
        return true;
    }



    public void propagateCoral(final int x, final int y, final int z, final Block block, final Block coralBlock1, final Block coralBlock2) {
        final ListQueue<TravelNode> queue = new ListQueue<>();
        final int maxTravel = MiscUtils.randomInt(this.terrain.getRandom(), 7, 8);
        queue.add(new TravelNode(x, y, z, 0));
        while (queue.containsNodes()) {
            final TravelNode node = queue.getAndRemove(0);
            final int travel = node.getTravel();
            final Vector3i coords = node.getCoords();
            if (travel < maxTravel) {
                this.checkNeighbor2(coords.x - 1, coords.y, coords.z, queue, travel, block, coralBlock1, coralBlock2);
                this.checkNeighbor2(coords.x + 1, coords.y, coords.z, queue, travel, block, coralBlock1, coralBlock2);
                this.checkNeighbor2(coords.x, coords.y, coords.z + 1, queue, travel, block, coralBlock1, coralBlock2);
                this.checkNeighbor2(coords.x, coords.y, coords.z - 1, queue, travel, block, coralBlock1, coralBlock2);
                this.checkNeighbor2(coords.x, coords.y + 1, coords.z, queue, travel, block, coralBlock1, coralBlock2);
                this.checkNeighbor2(coords.x, coords.y - 1, coords.z, queue, travel, block, coralBlock1, coralBlock2);
            }
        }
    }

    public boolean penetrableByCoral(final Block b) {
        if (!b.isLiquid()) {
            if (b != GameItems.SeaGrass) {
                return false;
            }
        }
        return true;
    }

    public void checkNeighbor2(final int x, final int y, final int z, final ListQueue<TravelNode> queue, final int travel, final Block block, final Block coralBlock1, final Block coralBlock2) {
        final Block b = this.terrain.getPointerHandler().getWorld().getBlock(x, y, z);
        final Block higherBlock = this.terrain.getPointerHandler().getWorld().getBlock(x, y - 1, z);
        if (b.isSolid()) {
            if (!b.name.toLowerCase().contains("coral") && b != block && this.travelOdds(this.terrain.getRandom(), travel)) {
                if (travel <= 5) {
                    this.terrain.getPointerHandler().getWorld().setBlock(block, x, y, z);
                }
                if (y > this.WATER_LEVEL + 3 && this.penetrableByCoral(higherBlock)) {
                    if (this.terrain.getRandom().nextFloat() > 0.8) {
                        this.terrain.getPointerHandler().getWorld().setBlock(coralBlock1, x, y - 1, z);
                    } else if (this.terrain.getRandom().nextFloat() > 0.5) {
                        this.terrain.getPointerHandler().getWorld().setBlock(coralBlock2, x, y - 1, z);
                    } else {
                        final Block block2 = higherBlock;
                        if (block2 != GameItems.BLOCK_WATER) {
                            final World world = this.terrain.getPointerHandler().getWorld();
                            world.setBlock(GameItems.BLOCK_WATER, x, y - 1, z);
                        }
                    }
                }
                queue.add(new TravelNode(x, y, z, travel + 1));
            }
        }
    }

    private boolean travelOdds(final Random rand, final int travel) {
        return travel <= 2 || rand.nextFloat() > 0.2;
    }
}
