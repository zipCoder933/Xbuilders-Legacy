/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.copyPaste;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.entity.EntityTemplate;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.other.BlockGrid;
import static com.xbuilders.game.items.other.copyPaste.BlockAdditivePaste.makePasteBox;
import java.util.Map;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class BlockPaste extends Block {

    public static int offsetVector = 0;

    public static void nextOffsetVertex() {
        if (pasteBlock != null && CopyTool.clipboard != null) {
            offsetVector++;
            offsetVector %= 7;
            pasteBox.initialize(pasteBlock, CopyTool.clipboard);
        }
    }

    public static void rotatePasteBox() {
        if (pasteBlock != null && CopyTool.clipboard != null) {
            CopyTool.clipboard.entities.clear();
            BlockGrid newClipboard = new BlockGrid(
                    CopyTool.clipboard.blocks.size.z,
                    CopyTool.clipboard.blocks.size.y,
                    CopyTool.clipboard.blocks.size.x);
            for (int x = 0; x < CopyTool.clipboard.blocks.size.x; x++) {
                for (int y = 0; y < CopyTool.clipboard.blocks.size.y; y++) {
                    for (int z = 0; z < CopyTool.clipboard.blocks.size.z; z++) {
                        int newX = (CopyTool.clipboard.blocks.size.z - 1) - z;
                        int newZ = x;
                        int newY = y;

                        newClipboard.set(
                                CopyTool.clipboard.blocks.get(x, y, z),
                                newX, newY, newZ);

                        BlockData data = CopyTool.clipboard.blocks.getBlockData(x, y, z);
                        if (data != null) {
                            BlockOrientation orient = BlockOrientation.getBlockOrientation(data);
                            if (orient != null) {
                                byte val = (byte) (orient.getXZ() + 1);
                                if (val > 3) {
                                    val = (byte) 0;
                                }
                                newClipboard.setBlockData(new BlockOrientation(val, (byte) orient.getY()), newX, newY, newZ);
                            }
                        }
                    }
                }
            }
            CopyTool.clipboard.blocks = newClipboard;
            BlockPaste.pasteBox.initialize(BlockPaste.pasteBlock, CopyTool.clipboard);
        }
    }

    public static Vector3f getStartPosOffset(int setX, int setY, int setZ) {
        int startX = (setX + 1) - CopyTool.clipboard.blocks.size.x;
        int startY = (setY + 1) - CopyTool.clipboard.blocks.size.y;
        int startZ = (setZ + 1) - CopyTool.clipboard.blocks.size.z;

        switch (offsetVector) {
            case 0 -> {
                return new Vector3f(setX, setY, setZ);
            }
            case 1 -> {
                return new Vector3f(startX, setY, setZ);
            }
            case 2 -> {
                return new Vector3f(setX, startY, setZ);
            }
            case 3 -> {
                return new Vector3f(setX, setY, startZ);
            }
            case 4 -> {
                return new Vector3f(startX, startY, setZ);
            }
            case 5 -> {
                return new Vector3f(setX, startY, startZ);
            }
            case 6 -> {
                return new Vector3f(startX, setY, startZ);
            }
            default -> {
                return new Vector3f(startX, startY, startZ);
            }
        }
    }

    public BlockPaste() {
        super(544, "Paste Block");
        texture = new BlockTexture(18, 3);
        setIconAtlasPosition(3, 8);
        tags.add("copy");
        tags.add("paste");
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public static Vector3i pasteBlock = null;
    public static PasteBoundingBox pasteBox = null;
//    public static BlockOrientation orientation = null;

    public static void removePasteBlock() {
        if (pasteBlock != null) {
            BlockList.BLOCK_AIR.set(pasteBlock.x, pasteBlock.y, pasteBlock.z);
            pasteBlock = null;
            pasteBox.remove();
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        System.out.println("Setting block: " + x + ", " + y + ", " + z);
             if (getPointerHandler().getGame().mode != GameMode.FREEPLAY) {
            VoxelGame.getGame().alert("You cannot do that in " + getPointerHandler().getGame().mode + " mode");
            return false;
        }
        if (CopyTool.clipboard != null) {
            this.set(x, y, z);
            if (pasteBlock != null) {
                removePasteBlock();
            }
            pasteBlock = new Vector3i(x, y, z);
            if (pasteBox == null || pasteBox.willBeRemoved() || pasteBox.isBuilding()) {
                pasteBox = makePasteBox(getPointerHandler(), null, pasteBlock);
            } else {
                pasteBox.initialize(pasteBlock, CopyTool.clipboard);
            }
        }else VoxelGame.getGame().alert("Nothing to paste");
        return true;
    }

    @Override
    public boolean onClickEvent(int setX, int setY, int setZ) {
        if (getPointerHandler().getPlayer().blockPanel.curItemEquals(GameItems.PASTE_ROTATE_BLOCK)
                || getPointerHandler().getPlayer().blockPanel.curItemEquals(GameItems.PASTE_VERTEX_BLOCK)) {
            return true;
        }
        if (CopyTool.clipboard != null) {
            final Vector3f startPosOfffset = getStartPosOffset(setX, setY, setZ);

            int index = 0;

            for (int x = 0; x < CopyTool.clipboard.blocks.size.x; x++) {
                for (int y = 0; y < CopyTool.clipboard.blocks.size.y; y++) {
                    for (int z = 0; z < CopyTool.clipboard.blocks.size.z; z++) {
                        setBlock(x, y, z, startPosOfffset, index);
                    }
                }
            }

            //<editor-fold defaultstate="collapsed" desc="paste entities">
            for (Map.Entry<Vector3f, EntityTemplate> e : CopyTool.clipboard.entities.entrySet()) {
                Vector3f normalizedWorldPos = e.getKey();
                EntityTemplate entityTemplate = e.getValue();
                Vector3f offset = new Vector3f(startPosOfffset.x, startPosOfffset.y, startPosOfffset.z);
                Vector3f pos = new Vector3f(normalizedWorldPos);
                pos.add(offset);
//                System.out.println("Setting entity " + e.toString() + " to " + pos);
                entityTemplate.makeEntity(getPointerHandler(), (int) pos.x, (int) pos.y, (int) pos.z);
            }
            //</editor-fold>
            pasteBox.markAsBuilding();
            getPointerHandler().getPlayer().blockModes.blockSetter.wakeUp();
        }
        removePasteBlock();
        return false;
    }

    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        if (pasteBox != null) {
            pasteBox.remove();
        }
        removePasteBlock();
    }

    @Override
    public boolean saveInChunk() {
        return false;
    }

    private void setBlock(int x, int y, int z, Vector3f startPosOffset, int index) {
        Block block = ItemList.getBlock(CopyTool.clipboard.blocks.get(x, y, z));
        BlockData data = CopyTool.clipboard.blocks.getBlockData(x, y, z);
        getPointerHandler().getPlayer().blockModes.blockSetter.addToBlockQueue(block,
                new Vector3i(
                        (int) (x + startPosOffset.x),
                        (int) (y + startPosOffset.y),
                        (int) (z + startPosOffset.z)), data);
        index++;
    }

}
