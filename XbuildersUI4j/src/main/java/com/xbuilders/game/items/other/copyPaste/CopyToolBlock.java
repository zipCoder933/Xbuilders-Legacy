///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.game.items.other.copyPaste;
//
//import com.xbuilders.engine.VoxelGame;
//import com.xbuilders.engine.game.GameMode;
//import com.xbuilders.engine.items.ItemList;
//import com.xbuilders.engine.items.block.Block;
//import com.xbuilders.engine.items.entity.Entity;
//import com.xbuilders.engine.items.entity.EntityTemplate;
//import com.xbuilders.game.items.entities.mobile.Animal;
//import com.xbuilders.game.items.entities.mobile.Vehicle;
//import com.xbuilders.engine.items.tool.Tool;
//import com.xbuilders.engine.utils.math.AABB;
//import com.xbuilders.engine.world.chunk.SubChunk;
//import com.xbuilders.engine.world.chunk.wcc.WCCi;
//import com.xbuilders.engine.world.chunk.blockData.BlockData;
//import com.xbuilders.game.items.GameItems;
//import com.xbuilders.game.items.other.boundaryBlocks.BoundarySetEvent;
//import java.util.HashSet;
//import org.joml.Vector3f;
//import org.joml.Vector3i;
//
///**
// *
// * @author zipCoder933
// */
//public class CopyToolBlock extends Tool {
//
//    public CopyToolBlock() {
//        super(5, "Copy Tool");
//        setIconAtlasPosition(2, 8);
//        tags.add("copy");
//        tags.add("paste");
//    }
//
//    @Override
//    public boolean isInfiniteResource() {
//        return true;
//    }
//
//    public BoundarySetEvent boundarySetEvent = new BoundarySetEvent() {
//        @Override
//        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
//            copyBoundary(boundary);
//        }
//    };
//
//    @Override
//    public boolean onPlace(int setX, int setY, int setZ) {
//             if (getPointerHandler().getGame().mode != GameMode.FREEPLAY) {
//            VoxelGame.getGame().alert("You cannot do that in " + getPointerHandler().getGame().mode + " mode");
//            return false;
//        }
//        System.err.println("Place!");
//        GameItems.START_BOUNDARY.place(setX, setY, setZ, boundarySetEvent);
//        return false;
//    }
//
//}
