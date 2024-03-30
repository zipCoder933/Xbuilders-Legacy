///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.test;
//
//import com.xbuilders.engine.items.block.Block;
//import com.xbuilders.engine.items.block.construction.BlockGeometry;
//import com.xbuilders.engine.world.World;
//import com.xbuilders.engine.world.chunk.ChunkVoxels;
//import com.xbuilders.game.PointerHandler;
//import com.xbuilders.game.items.blocks.BlockList;
//import processing.core.PApplet;
//import processing.core.PConstants;
//import processing.core.PShape;
//
///**
// *
// * @author zipCoder933
// */
//public class MeshGenerator {
//
//    public static PShape generateMesh(ChunkVoxels blocks, PApplet applet) {
//        PShape mesh;
//        Block faceDefault = BlockList.BLOCK_AIR;
//        mesh = applet.createShape();
//        mesh.beginShape(PConstants.TRIANGLE);
//
//        mesh.stroke(255, 255, 255);
//        mesh.strokeWeight(1);
//        mesh.fill(0);
////        mesh.texture(ph.getBlockList().getTextureAtlas().getImage());
//
//        for (int x = 0; x < blocks.getSizeX(); x++) {
//            for (int y = 0; y < blocks.getSizeY(); y++) {
//                for (int z = 0; z < blocks.getSizeZ(); z++) {
//                    Block block = blocks.getBlock(x, y, z);
//                    if (block.isAir()) {
//                        continue;
//                    }
//                    System.out.println("Block: "+block);
//
//                    Block nx = faceDefault;
//                    Block px = faceDefault;
//                    Block ny = faceDefault;
//                    Block py = faceDefault;
//                    Block nz = faceDefault;
//                    Block pz = faceDefault;
//                    if (x > 0) {
//                        nx = blocks.getBlock(x - 1, y, z);
//                    }
//                    if (x < blocks.getSizeX() - 1) {
//                        px = blocks.getBlock(x + 1, y, z);
//                    }
//                    if (y > 0) {
//                        ny = blocks.getBlock(x, y - 1, z);
//                    }
//                    if (y < blocks.getSizeY() - 1) {
//                        py = blocks.getBlock(x, y + 1, z);
//                    }
//                    if (z > 0) {
//                        nz = blocks.getBlock(x, y, z - 1);
//                    }
//                    if (z < blocks.getSizeZ() - 1) {
//                        pz = blocks.getBlock(x, y, z + 1);
//                    }
//                    BlockGeometry.constructBlock(block, blocks.getBlockData(x, y, z),
//                            mesh, nx, px, ny, py, nz, pz, x, y, z);
//                }
//            }
//        }
//        mesh.endShape();
//        return mesh;
//    }
//}
