///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.game.items.blocks.electronics;
//
//import com.xbuilders.engine.VoxelGame;
//import com.xbuilders.engine.items.ItemTag;
//import com.xbuilders.engine.items.block.Block;
//import com.xbuilders.game.items.blockType.BlockRenderType;
//import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
//import com.xbuilders.engine.world.blockData.BlockData;
//import com.xbuilders.game.items.blocks.BlockList;
//
///**
// *
// * @author zipCoder933
// */
//public class Button extends ElectronicDevice {
//
//    public Button() {
//        super(747, "Button");
//        renderType = BlockRenderType.ORIENTABLE_BLOCK;
//        texture = new BlockTexture(12, 20, 9, 20, 9, 20);
//        setIconAtlasPosition(8, 6);
//        getTags().add(ItemTag.ELECTRONIC);
//    }
//
//    @Override
//    public boolean onClickEvent(int setX, int setY, int setZ) {
////        BlockData data = VoxelGame.getWorld().getBlockData(setX, setY, setZ);
////        VoxelGame.getWorld().setBlockAndUpdate(BlockList.onSwitch, data, setX, setY, setZ);
//        return false;
//    }
//
//}
