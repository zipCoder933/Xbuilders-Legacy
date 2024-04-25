///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.game.items.other.copyPaste;
//
//import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
//import com.xbuilders.engine.items.BlockList;
//import com.xbuilders.engine.items.Item;
//import com.xbuilders.engine.items.block.Block;
//import com.xbuilders.game.items.blockType.BlockRenderType;
//import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
//import com.xbuilders.engine.world.chunk.blockData.BlockData;
//import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
//import com.xbuilders.game.PointerHandler;
//import static com.xbuilders.game.items.other.copyPaste.BlockPaste.rotatePasteBox;
//import org.joml.Vector3i;
//
///**
// *
// * @author zipCoder933
// */
//public class PasteRotateBlock extends Block {
//
//    public PasteRotateBlock() {
//        super(552, "Paste Rotate Block");
//        setRenderType(BlockRenderType.SLAB);
//        this.texture = new BlockTexture();
//        this.texture.setTOP(20, 3);
//        this.texture.setSIDES(22, 3);
//        this.texture.setBOTTOM(20, 3);
//        setIconAtlasPosition(5, 8);
//        tags.add("copy");
//        tags.add("paste");
//    }
//
//
//    @Override
//    public boolean onClickEvent(int setX, int setY, int setZ) {
//        BlockOrientation setOrientation = BlockOrientation.getBlockOrientation(getPointerHandler().getWorld().getBlockData(setX, setY, setZ));
//        if (setOrientation != null) {
//
//            if (setOrientation.getY() == 1
//                    || setOrientation.getY() == -1) {
//                rotatePasteBox();
//            }
//
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isSolid() {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaque() {
//        return false;
//    }
//
//    @Override
//    public boolean saveInChunk() {
//        return false;
//    }
//
//    Vector3i rotateBlock = null;
//
//    @Override
//    public void onLocalChange(LocalChangeRecord record) {
//        int x = record.getTargetBlockPosition().x;
//        int y = record.getTargetBlockPosition().y;
//        int z = record.getTargetBlockPosition().z;
//        if (isNeighboringPasteBlock(getPointerHandler(), x, y, z) == null) {
//            removeBlock();
//        }
//    }
//
//    public static BlockOrientation isNeighboringPasteBlock(PointerHandler ph, int x, int y, int z) {
////        if (getPointerHandler().getWorld().getBlock(x + 1, y, z).getName().toLowerCase().contains("paste")) {
////            return new BlockOrientation((byte) 1, (byte) 0);
////        }
////        if (getPointerHandler().getWorld().getBlock(x - 1, y, z).getName().toLowerCase().contains("paste")) {
////            return new BlockOrientation((byte) 3, (byte) 0);
////        }
//        Item item1 = ph.getWorld().getBlock(x, y + 1, z);
//        if (item1.name.toLowerCase().contains("paste")) {
//            return new BlockOrientation((byte) 0, (byte) 1);
//        }
//        Item item = ph.getWorld().getBlock(x, y - 1, z);
//        if (item.name.toLowerCase().contains("paste")) {
//            return new BlockOrientation((byte) 0, (byte) -1);
//        }
////        if (getPointerHandler().getWorld().getBlock(x, y, z + 1).getName().toLowerCase().contains("paste")) {
////            return new BlockOrientation((byte) 0, (byte) 0);
////        }
////        if (getPointerHandler().getWorld().getBlock(x, y, z - 1).getName().toLowerCase().contains("paste")) {
////            return new BlockOrientation((byte) 2, (byte) 0);
////        }
//        return null;
//
//    }
//
//    private void removeBlock() {
//        if (rotateBlock != null) {
//            BlockList.BLOCK_AIR.set(rotateBlock);
//        }
//        rotateBlock = null;
//    }
//
//    @Override
//    public void afterRemovalEvent(int x, int y, int z) {
//        removeBlock();
//    }
//
//    @Override
//    public boolean setBlock(int x, int y, int z, BlockData data) {
//        BlockOrientation orientation = isNeighboringPasteBlock(getPointerHandler(), x, y, z);
//        if (orientation != null) {
//            if (rotateBlock == null) {
//                rotateBlock = new Vector3i(x, y, z);
//            } else {
//                BlockList.BLOCK_AIR.set(rotateBlock);
//                rotateBlock = new Vector3i(x, y, z);
//            }
//            this.set(x, y, z, orientation);
//        }
//        return true;
//    }
//}
