///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.game.items.other.copyPaste;
//
//import com.xbuilders.engine.VoxelGame;
//import com.xbuilders.engine.game.GameMode;
//import com.xbuilders.engine.items.BlockList;
//import com.xbuilders.engine.items.ItemList;
//import com.xbuilders.engine.items.block.Block;
//import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
//import com.xbuilders.engine.items.entity.EntityTemplate;
//import com.xbuilders.engine.world.chunk.blockData.BlockData;
//import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
//import com.xbuilders.game.items.GameItems;
//import com.xbuilders.game.items.other.BlockGrid;
//import static com.xbuilders.game.items.other.copyPaste.BlockAdditivePaste.makePasteBox;
//import java.util.Map;
//import org.joml.Vector3f;
//import org.joml.Vector3i;
//
///**
// *
// * @author zipCoder933
// */
//public class BlockPaste extends Block {
//
//
//
//    public BlockPaste() {
//        super(544, "Paste Block");
//        texture = new BlockTexture(18, 3);
//        setIconAtlasPosition(3, 8);
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
//    public static Vector3i pasteBlock = null;
//    public static PastePreview pasteBox = null;
////    public static BlockOrientation orientation = null;
//
//    public static void removePasteBlock() {
//        if (pasteBlock != null) {
//            BlockList.BLOCK_AIR.set(pasteBlock.x, pasteBlock.y, pasteBlock.z);
//            pasteBlock = null;
//            pasteBox.remove();
//        }
//    }
//
//    @Override
//    public boolean setBlock(int x, int y, int z, BlockData data) {
//        System.out.println("Setting block: " + x + ", " + y + ", " + z);
//             if (getPointerHandler().getGame().mode != GameMode.FREEPLAY) {
//            VoxelGame.getGame().alert("You cannot do that in " + getPointerHandler().getGame().mode + " mode");
//            return false;
//        }
//        if (CopyToolBlock.clipboard != null) {
//            this.set(x, y, z);
//            if (pasteBlock != null) {
//                removePasteBlock();
//            }
//            pasteBlock = new Vector3i(x, y, z);
//            if (pasteBox == null || pasteBox.willBeRemoved()) {
//                pasteBox = makePasteBox( null, pasteBlock);
//            } else {
//                pasteBox.initialize(pasteBlock, CopyToolBlock.clipboard);
//            }
//        }else VoxelGame.getGame().alert("Nothing to paste");
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
//                Vector3f offset = new Vector3f(startPosOfffset.x, startPosOfffset.y, startPosOfffset.z);
//                Vector3f pos = new Vector3f(normalizedWorldPos);
//                pos.add(offset);
////                System.out.println("Setting entity " + e.toString() + " to " + pos);
//                entityTemplate.makeEntity(getPointerHandler(), (int) pos.x, (int) pos.y, (int) pos.z);
//            }
//            //</editor-fold>
//            pasteBox.remove();
//            getPointerHandler().getPlayer().blockTools.blockSetter.wakeUp();
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
