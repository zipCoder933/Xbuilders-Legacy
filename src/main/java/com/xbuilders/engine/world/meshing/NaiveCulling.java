// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.meshing;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.world.blockData.BlockData;
import org.joml.Vector3i;
import processing.core.PShape;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.ChunkVoxels;

public class NaiveCulling {

    public static final boolean WIREFRAME = false;

    private static Block getBlock(final ChunkVoxels grid, final int x, final int y, final int z) {
        return ItemList.getBlock(grid.getBlock(x, y, z));
    }

    private static boolean checkNeighbor(SubChunk neighbor) {
        return neighbor == null || (neighbor.lightMap.isPastedIntoSLM() && !neighbor.lightMap.allDarkness);
    }

    public static boolean checkAllNeighborsForNonDarkness(SubChunk subChunk) {
        SubChunk neighbor = VoxelGame.getWorld().getSubChunk(
                new Vector3i(subChunk.position.x - 1, subChunk.position.y, subChunk.position.z));
        if (checkNeighbor(neighbor)) {
            return true;
        }
        neighbor = VoxelGame.getWorld().getSubChunk(
                new Vector3i(subChunk.position.x + 1, subChunk.position.y, subChunk.position.z));
        if (checkNeighbor(neighbor)) {
            return true;
        }
        neighbor = VoxelGame.getWorld().getSubChunk(
                new Vector3i(subChunk.position.x, subChunk.position.y - 1, subChunk.position.z));
        if (checkNeighbor(neighbor)) {
            return true;
        }
        neighbor = VoxelGame.getWorld().getSubChunk(
                new Vector3i(subChunk.position.x, subChunk.position.y + 1, subChunk.position.z));
        if (checkNeighbor(neighbor)) {
            return true;
        }
        neighbor = VoxelGame.getWorld().getSubChunk(
                new Vector3i(subChunk.position.x, subChunk.position.y, subChunk.position.z - 1));
        if (checkNeighbor(neighbor)) {
            return true;
        }
        neighbor = VoxelGame.getWorld().getSubChunk(
                new Vector3i(subChunk.position.x, subChunk.position.y, subChunk.position.z + 1));
        if (checkNeighbor(neighbor)) {
            return true;
        }

        return false;
    }

    public static synchronized void generateMesh(final SubChunk subChunk,
                                                 final PShape opaqueMesh, final PShape transparentMesh,
                                                 final Vector3i offset) {

        final Block faceDefault = null;
        opaqueMesh.beginShape(8);
        opaqueMesh.texture(ItemList.blocks.textureAtlas.getImage());
        opaqueMesh.noStroke();

        transparentMesh.beginShape(8);
        transparentMesh.texture(ItemList.blocks.textureAtlas.getImage());
        transparentMesh.noStroke();

        int chunkPosXAdd = 0;
        int chunkNegXAdd = 0;
        int chunkPosZAdd = 0;
        int chunkNegZAdd = 0;
        int chunkPosYAdd = 0;
        int chunkNegYAdd = 0;
        Block nx = faceDefault;
        Block px = faceDefault;
        Block ny = faceDefault;
        Block py = faceDefault;
        Block nz = faceDefault;
        Block pz = faceDefault;

        final ChunkVoxels grid = subChunk.getVoxels();
        for (int x = 0 - chunkNegXAdd; x < grid.getSizeX() + chunkPosXAdd; ++x) {
            for (int y = 0 - chunkNegYAdd; y < grid.getSizeY() + chunkPosYAdd; ++y) {
                for (int z = 0 - chunkNegZAdd; z < grid.getSizeZ() + chunkPosZAdd; ++z) {
                    Block block = getBlock(grid, x, y, z);

                    if (!block.isAir() && block.hasTexture()) {
                        // if (inBounds) {
                        nx = faceDefault;
                        px = faceDefault;
                        ny = faceDefault;
                        py = faceDefault;
                        nz = faceDefault;
                        pz = faceDefault;
                        if (x > 0) {
                            nx = getBlock(grid, x - 1, y, z);
                        } else {
                            nx = VoxelGame.getWorld().getBlock(subChunk.getPosition().x * SubChunk.WIDTH + x - 1,
                                    subChunk.getPosition().y * SubChunk.WIDTH + y,
                                    subChunk.getPosition().z * SubChunk.WIDTH + z, true);
                        }

                        if (x < grid.getSizeX() - 1) {
                            px = getBlock(grid, x + 1, y, z);
                        } else {
                            px = VoxelGame.getWorld().getBlock(subChunk.getPosition().x * SubChunk.WIDTH + x + 1,
                                    subChunk.getPosition().y * SubChunk.WIDTH + y,
                                    subChunk.getPosition().z * SubChunk.WIDTH + z, true);
                        }

                        if (y > 0) {
                            ny = getBlock(grid, x, y - 1, z);
                        } else {
                            ny = VoxelGame.getWorld().getBlock(subChunk.getPosition().x * SubChunk.WIDTH + x,
                                    subChunk.getPosition().y * SubChunk.WIDTH + y - 1,
                                    subChunk.getPosition().z * SubChunk.WIDTH + z, true);
                        }

                        if (y < grid.getSizeY() - 1) {
                            py = getBlock(grid, x, y + 1, z);
                        } else {
                            py = VoxelGame.getWorld().getBlock(subChunk.getPosition().x * SubChunk.WIDTH + x,
                                    subChunk.getPosition().y * SubChunk.WIDTH + y + 1,
                                    subChunk.getPosition().z * SubChunk.WIDTH + z, true);
                        }

                        if (z > 0) {
                            nz = getBlock(grid, x, y, z - 1);
                        } else {
                            nz = VoxelGame.getWorld().getBlock(subChunk.getPosition().x * SubChunk.WIDTH + x,
                                    subChunk.getPosition().y * SubChunk.WIDTH + y,
                                    subChunk.getPosition().z * SubChunk.WIDTH + z - 1, true);
                        }

                        if (z < grid.getSizeZ() - 1) {
                            pz = getBlock(grid, x, y, z + 1);
                        } else {
                            pz = VoxelGame.getWorld().getBlock(subChunk.getPosition().x * SubChunk.WIDTH + x,
                                    subChunk.getPosition().y * SubChunk.WIDTH + y,
                                    subChunk.getPosition().z * SubChunk.WIDTH + z + 1, true);
                        }
                        // }

                        BlockData data = grid.getBlockData(x, y, z);
                        ItemList.blocks.getBlockType(block.type).constructBlock(
                                block.isOpaque() ? opaqueMesh : transparentMesh, block, data,
                                nx, px, ny, py, nz, pz,
                                x + offset.x, y + offset.y, z + offset.z);
                    }
                }
            }
        }
        opaqueMesh.endShape();
        transparentMesh.endShape();
    }
}
