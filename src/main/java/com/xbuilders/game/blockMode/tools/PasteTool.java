package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.EntityTemplate;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.game.blockMode.tools.copyPaste.PastePreview;
import com.xbuilders.game.items.other.BlockGrid;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.Map;

public class PasteTool extends Tool {

    public static int offsetVector = 0;


    public static void nextOffsetVertex(PastePreview pasteBox) {
        offsetVector++;
        offsetVector %= 7;
        if (pasteBox != null) pasteBox.initialize(CopyTool.clipboard);
    }

    public static void rotatePasteBox(PastePreview pasteBox) {
        if (CopyTool.clipboard != null) {
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
            if (pasteBox != null) pasteBox.initialize(CopyTool.clipboard);
        }
    }

    public static Vector3f getStartPosOffset(Vector3i pos) {
        int setX = pos.x;
        int setY = pos.y;
        int setZ = pos.z;
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

    public PasteTool(BlockTools blockTools) {
        super("Paste", blockTools);
    }

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        return window.keysArePressed(KeyCode.CTRL, KeyCode.V);
    }

    public static PastePreview makePasteBox(BlockOrientation orientation) {
        if (CopyTool.clipboard != null) {
            PastePreview holo = new PastePreview(orientation, CopyTool.clipboard);
            return holo;
        }
        return null;
    }

    @Override
    public void activate() {
        if (pasteBox == null || pasteBox.willBeRemoved()) {
            pasteBox = makePasteBox(null);
        } else {
            pasteBox.initialize(CopyTool.clipboard);
        }
    }


    private void paste(Vector3i setPos, boolean additive) {
        if (CopyTool.clipboard != null) {
            final Vector3f startPosOfffset = getStartPosOffset(setPos);

            for (int x = 0; x < CopyTool.clipboard.blocks.size.x; x++) {
                for (int y = 0; y < CopyTool.clipboard.blocks.size.y; y++) {
                    for (int z = 0; z < CopyTool.clipboard.blocks.size.z; z++) {
                        Block block = ItemList.getBlock(CopyTool.clipboard.blocks.get(x, y, z));
                        Block previousBlock = VoxelGame.ph().getWorld().getBlock(
                                (int) startPosOfffset.x + x,
                                (int) startPosOfffset.y + y,
                                (int) startPosOfffset.z + z);

                        if (!additive || !previousBlock.isSolid()) {
                            BlockData data = CopyTool.clipboard.blocks.getBlockData(x, y, z);
                            VoxelGame.ph().getPlayer().blockTools.blockSetter.addToBlockQueue(block,
                                    new Vector3i(
                                            (int) (x + startPosOfffset.x),
                                            (int) (y + startPosOfffset.y),
                                            (int) (z + startPosOfffset.z)), data);
                        }
                    }
                }
            }

            //<editor-fold desc="paste entities">
            for (Map.Entry<Vector3f, EntityTemplate> e : CopyTool.clipboard.entities.entrySet()) {
                Vector3f normalizedWorldPos = e.getKey();
                EntityTemplate entityTemplate = e.getValue();
                Vector3f offset = new Vector3f(startPosOfffset.x, startPosOfffset.y, startPosOfffset.z);
                Vector3f pos = new Vector3f(normalizedWorldPos);
                pos.add(offset);
//                System.out.println("Setting entity " + e.toString() + " to " + pos);
                entityTemplate.makeEntity(VoxelGame.ph(), (int) pos.x, (int) pos.y, (int) pos.z);
            }
            //</editor-fold>
            VoxelGame.ph().getPlayer().blockTools.blockSetter.wakeUp();
        }
    }

    PastePreview pasteBox;
    boolean additive = true;

    @Override
    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        if (pasteBox != null && ray.hitTarget()) {
            Vector3f pos = getStartPosOffset(ray.getHitPosPlusNormal());
            g.translate(pos.x, pos.y, pos.z);
            pasteBox.worldPosition.set(ray.getHitPosPlusNormal());
            pasteBox.render(g);
        }
        return false;
    }


    @Override
    public boolean keyReleased(BaseWindow window, KeyEvent ke) {
        if (window.keyIsPressed(KeyCode.V) && !window.keyIsPressed(KeyCode.CTRL)) {
            nextOffsetVertex(pasteBox);
            return true;
        } else if (window.keyIsPressed(KeyCode.R)) {
            rotatePasteBox(pasteBox);
            return true;
        }
        return false;
    }

    @Override
    public void changeMode() {
        additive = !additive;
    }

    @Override
    public String toolDescription() {
        return additive ? "Additive Paste" : "Paste" + (" (v" + offsetVector + ")");
    }

    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        if (CopyTool.clipboard != null) {
            paste(ray.getHitPosPlusNormal(), additive);
            return true;
        }
        return false;
    }
}
