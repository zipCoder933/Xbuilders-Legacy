package com.xbuilders.game.items.other;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.blockData.BlockData;
import processing.core.PShape;

public class BlockMesh {

    public static PShape createMesh(BlockGrid grid, boolean wireframe, boolean textured) {
//        Block faceDefault = BlockList.BLOCK_AIR;
//
//        PShape mesh = VoxelGame.ph().getApplet().createShape();
//        mesh.beginShape(VoxelGame.ph().getApplet().TRIANGLE);
//        mesh.noFill();
//
//        if (wireframe) {
//            mesh.stroke(255);
//            mesh.strokeWeight(1);
//        } else mesh.noStroke();
//
//        if (textured) {
//            mesh.tint(180, 180, 180);
//            mesh.texture(ItemList.blocks.textureAtlas.getImage());
//        }
//
//        for (int x = 0; x < grid.size.x; x++) {
//            for (int y = 0; y < grid.size.y; y++) {
//                for (int z = 0; z < grid.size.z; z++) {
//                  Block block = ItemList.getBlock(grid.get(x, y, z));
//
//                    if (block != null) {
//                        Block nx = faceDefault;
//                        if (x - 1 > 0) {
//                            nx = ItemList.getBlock(grid.get(x - 1, y, z));
//                        }
//                        Block px = faceDefault;
//                        if (x < grid.size.x - 1) {
//                            px = ItemList.getBlock(grid.get(x + 1, y, z));
//                        }
//                        Block ny = faceDefault;
//                        if (y - 1 > 0) {
//                            ny = ItemList.getBlock(grid.get(x, y - 1, z));
//                        }
//                        Block py = faceDefault;
//                        if (y < grid.size.y - 1) {
//                            py = ItemList.getBlock(grid.get(x, y + 1, z));
//                        }
//                        Block nz = faceDefault;
//                        if (z - 1 > 0) {
//                            nz = ItemList.getBlock(grid.get(x, y, z - 1));
//                        }
//                        Block pz = faceDefault;
//                        if (z < grid.size.z - 1) {
//                            pz = ItemList.getBlock(grid.get(x, y, z + 1));
//                        }
//
//                        if (!block.isAir() && block.hasTexture()) {
//                            BlockData data = grid.getBlockData(x, y, z);
//
//                            ItemList.blocks.getBlockType(block.type).
//                                    constructBlock(mesh, block, data,
//                                            nx, px, ny, py, nz, pz,
//                                            (int) (x), (int) (y), (int) (z));
//                        }
//                    }
//                }
//            }
//        }
//        mesh.endShape();

//        return mesh;
        return null;
    }

}
