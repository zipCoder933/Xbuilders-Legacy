///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.test;
//
//import com.xbuilders.engine.world.terrain.BasicTerrain;
//import com.jsimulation.simulation.SimulationComponent;
//import com.jsimulation.simulation.SimulationWindow;
//import com.xbuilders.engine.items.ItemList;
//import com.xbuilders.engine.items.block.construction.BlockGeometry;
//import com.xbuilders.engine.world.World;
//import com.xbuilders.engine.world.chunk.Chunk;
//import com.xbuilders.engine.world.chunk.BlockGrid;
//import com.xbuilders.engine.world.chunk.ChunkCoords;
//import com.xbuilders.game.PointerHandler;
//import com.xbuilders.game.ResourceUtils;
//import com.xbuilders.game.items.blocks.BlockList;
//import java.io.IOException;
//import processing.core.PGraphics;
//import processing.core.PShape;
//import processing.event.KeyEvent;
//import processing.event.MouseEvent;
//
///**
// *
// * @author zipCoder933
// */
//public class RenderingComp extends SimulationComponent {
//
//    BlockGrid blocks;
//    PShape shape;
//
//    public RenderingComp(SimulationWindow sim) throws IOException {
//        super(sim);
//        ResourceUtils.initialize(true);
//
//        World world = new World();
//        BlockList blockList = new BlockList();
//        ItemList itemList = new ItemList(getParentFrame(), blockList, null, null);
//        blocks = new BlockGrid(Chunk.CHUNK_X_LENGTH, Chunk.CHUNK_Y_LENGTH, Chunk.CHUNK_Z_LENGTH);
//        PointerHandler ph = new PointerHandler(getParentFrame(), world, null, itemList, true, null);
//
//        BlockGeometry.initialize(ph);
//        BasicTerrain terrain = new BasicTerrain(ph);
////        terrain.createTerrainOnChunk(blocks, new ChunkCoords(0, 0));
//        
//        
//        
//        shape = MeshGenerator.generateMesh(blocks, getParentFrame());
//
//        addToFrame();
//
//    }
//
//    @Override
//    public void renderOverlay() {
//    }
//
//    @Override
//    public void renderScene() {
//        PGraphics g = getSim().getScene();
////        g.fill(255,0,0);
////        g.box(100);
//        
//        g.scale(100);
//        g.shape(shape);
//    }
//
//    @Override
//    public void mouseEvent(MouseEvent me) {
//    }
//
//    @Override
//    public void keyEvent(KeyEvent ke) {
//    }
//
//    @Override
//    public void onFrameClose() throws Exception {
//    }
//
//}
