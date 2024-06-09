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
//import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
//import com.xbuilders.engine.items.entity.EntityTemplate;
//import com.xbuilders.engine.world.blockData.BlockData;
//import com.xbuilders.engine.world.blockData.BlockOrientation;
//import com.xbuilders.game.items.GameItems;
//import static com.xbuilders.game.items.other.copyPaste.BlockPaste.getStartPosOffset;
//import static com.xbuilders.game.items.other.copyPaste.BlockPaste.pasteBlock;
//import static com.xbuilders.game.items.other.copyPaste.BlockPaste.pasteBox;
//import static com.xbuilders.game.items.other.copyPaste.BlockPaste.removePasteBlock;
//import java.util.Map;
//
//import org.joml.Vector3f;
//import org.joml.Vector3i;
//
///**
// *
// * @author zipCoder933
// */
//public class BlockAdditivePaste extends Block {
//
//    public BlockAdditivePaste() {
//        super(551, "Additive Paste Block");
//        texture = new BlockTexture(22, 1);
//        setIconAtlasPosition(4, 8);
//        tags.add("copy");
//        tags.add("paste");
//    }
//
//    @Override
//    public boolean isSolid() {
//        return true;
//    }
//
//    @Override
//    public boolean isOpaque() {
//        return false;
//    }
//
//    public static PastePreview makePasteBox(BlockOrientation orientation, Vector3i start) {
//        if (CopyToolBlock.clipboard != null) {
//            PastePreview holo = new PastePreview(orientation, start, CopyToolBlock.clipboard);
//            return holo;
//        }
//        return null;
//    }
//
//    @Override
//    public boolean setBlock(int x, int y, int z, BlockData data) {
//        if (getPointerHandler().getGame().mode != GameMode.FREEPLAY) {
//            VoxelGame.getGame().alert("You cannot do that in " + getPointerHandler().getGame().mode + " mode");
//            return false;
//        }
//        if (CopyToolBlock.clipboard != null) {
////            orientation = getPointerHandler().getPlayer().getCameraBlockOrientation();
//            this.set(x, y, z);
//            if (pasteBlock != null) {
//                removePasteBlock();
//            }
//            pasteBlock = new Vector3i(x, y, z);
//            if (pasteBox == null || pasteBox.willBeRemoved()) {
//                pasteBox = makePasteBox(null, pasteBlock);
//                VoxelGame.getWorld().hologramList.add(pasteBox);
//            } else {
//                pasteBox.initialize(pasteBlock, CopyToolBlock.clipboard);
//            }
//            getPointerHandler().getPlayer().blockTools.blockSetter.wakeUp();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onClickEvent(int setX, int setY, int setZ) {
//        if (getPointerHandler().getPlayer().blockPanel.curItemEquals(GameItems.PASTE_ROTATE_BLOCK)
//                || getPointerHandler().getPlayer().blockPanel.curItemEquals(GameItems.PASTE_VERTEX_BLOCK)) {
//            return true;
//        }
//        if (CopyToolBlock.clipboard != null) {
//            final Vector3f startPosOfffset = getStartPosOffset(setX, setY, setZ);
//
//            int index = 0;
//
//            for (int x = 0; x < CopyToolBlock.clipboard.blocks.size.x; x++) {
//                for (int y = 0; y < CopyToolBlock.clipboard.blocks.size.y; y++) {
//                    for (int z = 0; z < CopyToolBlock.clipboard.blocks.size.z; z++) {
//                        setBlock(x, y, z, startPosOfffset, index);
//                    }
//                }
//            }
//
//            //<editor-fold defaultstate="collapsed" desc="paste entities">
//            for (Map.Entry<Vector3f, EntityTemplate> e : CopyToolBlock.clipboard.entities.entrySet()) {
//                Vector3f normalizedWorldPos = e.getKey();
//                EntityTemplate entityTemplate = e.getValue();
//
//                Vector3f offset = new Vector3f(startPosOfffset.x, startPosOfffset.y, startPosOfffset.z);
//                Vector3f pos = normalizedWorldPos;
//                pos.add(offset);
////                System.out.println("Setting entity " + e.toString() + " to " + pos);
//                entityTemplate.makeEntity(getPointerHandler(), (int) pos.x, (int) pos.y, (int) pos.z);
//            }
//            //</editor-fold>
//            pasteBox.remove();
//        }
//        removePasteBlock();
//        return false;
//    }
//
//    @Override
//    public void afterRemovalEvent(int x, int y, int z) {
//        if (pasteBox != null) {
//            pasteBox.remove();
//        }
//        removePasteBlock();
//    }
//
//    @Override
//    public boolean saveInChunk() {
//        return false;
//    }
//
//    private void setBlock(int x, int y, int z, Vector3f startPosOffset, int index) {
//        Block block = ItemList.getBlock(CopyToolBlock.clipboard.blocks.get(x, y, z));
//        if (block.isAir()) {
//            return;
//        }
//        BlockData data = CopyToolBlock.clipboard.blocks.getBlockData(x, y, z);
//        getPointerHandler().getPlayer().blockTools.blockSetter.addToBlockQueue(block,
//                new Vector3i(
//                        (int) (x + startPosOffset.x),
//                        (int) (y + startPosOffset.y),
//                        (int) (z + startPosOffset.z)), data);
//        index++;
//    }
//
//}
