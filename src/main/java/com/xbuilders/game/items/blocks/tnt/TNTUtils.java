/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blocks.tnt;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;

import java.util.ArrayList;
import java.util.HashSet;

import com.xbuilders.game.items.entities.mobile.Animal;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * @author zipCoder933
 */
public class TNTUtils {

    public static void startTNT(PointerHandler ph, Block thisBlock, final int EXPLOSTION_RADIUS, long fuseDelay, int setX, int setY, int setZ) {
        try {
            GameItems.TNT_ACTIVE.set(setX, setY, setZ);
            Thread.sleep(fuseDelay);
            if (VoxelGame.getWorld().getBlock(setX, setY, setZ) == GameItems.TNT_ACTIVE) {
                TNTUtils.removeBlock(ph, setX, setY, setZ);
                HashSet<Vector3i> explosionList = new HashSet<>();
                explosionList.addAll(explosion(ph, thisBlock, EXPLOSTION_RADIUS, new Vector3i(setX, setY, setZ)));
                System.out.println("Finished with explosion of size " + EXPLOSTION_RADIUS + ". New explosions: " + explosionList.size());
                while (!explosionList.isEmpty()) {
                    ArrayList<Vector3i> explosionList2 = new ArrayList<>();
                    for (Vector3i pos : explosionList) {
                        if (ph.getWorld().getBlock(pos) == thisBlock) {
                            GameItems.TNT_ACTIVE.set(pos);
                        }
                    }
                    Thread.sleep((long) (fuseDelay * 0.6f));
                    for (Vector3i pos : explosionList) {
                        if (ph.getWorld().getBlock(pos) == GameItems.TNT_ACTIVE) {
                            Thread.sleep(300);
                            explosionList2.addAll(TNTUtils.explosion(ph, thisBlock, EXPLOSTION_RADIUS, pos));
                        }
                    }
                    explosionList.clear();
                    System.out.println("Adding " + explosionList2.size() + " new explosions to list.");
                    explosionList.addAll(explosionList2);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void removeBlock(PointerHandler ph, int x, int y, int z) {
        ph.getPlayer().setBlock(BlockList.BLOCK_AIR, null, x, y, z);
    }

    public static ArrayList<Vector3i> explosion(PointerHandler ph, Block thisBlock, final int size, Vector3i position) throws InterruptedException {
        removeBlock(ph, position.x, position.y, position.z);
        boolean large = size > 5;
        if (!VoxelGame.getWorld().getBlock(position.x, position.y - 1, position.z).isLiquid()) {

            if (large) {
                removeBlocksWithinRadius(ph, size - 2, position);
                Thread.sleep(200);
            }

            ph.getWorld().hologramList.add(new ExplosionHologram(ph, new Vector3f(position), large));
            return removeEverythingWithinRadius(ph, thisBlock, size, position);
        }
        return new ArrayList<>();
    }

    public static void removeBlocksWithinRadius(PointerHandler ph, final int radius, Vector3i position) {
        int setX = position.x;
        int setY = position.y;
        int setZ = position.z;
        for (int x = 0 - radius; x < radius; x++) {
            for (int y = 0 - radius; y < radius; y++) {
                for (int z = 0 - radius; z < radius; z++) {
                    if (MathUtils.dist(setX, setY, setZ, setX + x, setY + y, setZ + z) < radius) {
                        Block highlightedBlock = VoxelGame.getWorld().getBlock(setX + x, setY + z, setZ + y);
                        if (isPenetrable(highlightedBlock)) {
                            removeBlock(ph, setX + x, setY + z, setZ + y);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param ph pointer handler
     * @param thisBlock this TNT block type
     * @param position the center-point of the explosion
     * @param size the radius of the explosion
     * @return the TNT blocks found within the radius (as checked by thisBlock)
     */
    public static ArrayList<Vector3i> removeEverythingWithinRadius(PointerHandler ph, Block thisBlock, int size, Vector3i position) {
        int setX = position.x;
        int setY = position.y;
        int setZ = position.z;

        HashSet<Vector3i> chunks = new HashSet<>();
        ArrayList<Vector3i> explosionList = new ArrayList<>();
        for (int x = 0 - size; x < size; x++) {
            for (int y = 0 - size; y < size; y++) {
                for (int z = 0 - size; z < size; z++) {
                    if (MathUtils.dist(setX, setY, setZ, setX + x, setY + y, setZ + z) < size) {
                        Block highlightedBlock = VoxelGame.getWorld().getBlock(setX + x, setY + z, setZ + y);
                        if (isPenetrable(highlightedBlock)) {
                            chunks.add(new WCCi().set(setX + x, setY + z, setZ + y).subChunk);
                            removeBlock(ph, setX + x, setY + z, setZ + y);
                        } else if (highlightedBlock == thisBlock) {
                            explosionList.add(new Vector3i(setX + x, setY + z, setZ + y));
                        }
                    }
                }
            }
        }

        ArrayList<Entity> entitiesToDelete = new ArrayList<>();
        for (Vector3i cc : chunks) {
            SubChunk chunk = VoxelGame.getWorld().getSubChunk(cc);
            if (chunk != null) {
                for (Entity e : chunk.getEntities().list) {
                    if (MathUtils.dist(setX, setY, setZ, e.worldPosition.x, e.worldPosition.y, e.worldPosition.z) < size
                           && !(e instanceof Animal)) {
                        entitiesToDelete.add(e);
                    }
                }
            }
        }
        for (Entity e : entitiesToDelete) {
            e.destroy(true);
        }
        return explosionList;
    }

    private static boolean isPenetrable(Block block) {
        return !block.isAir()
                && block != GameItems.BLOCK_TNT
                && block != GameItems.BLOCK_TNT_LARGE
                && block != GameItems.BlockBedrock;
    }

}
