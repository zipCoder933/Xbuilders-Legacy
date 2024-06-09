package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityTemplate;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.game.blockMode.tools.copyPaste.Clipboard;
import com.xbuilders.game.items.entities.mobile.Animal;
import com.xbuilders.game.items.entities.mobile.Vehicle;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3f;
import processing.core.KeyCode;
import processing.event.KeyEvent;

import java.util.HashSet;

public class CopyTool extends Tool {

    public CopyTool(BlockTools blockTools) {
        super("Copy", blockTools);
    }

    @Override
    public void activate() {
        VoxelGame.getPlayer().camera.cursorRay.enableBoundaryMode((aabb, aBoolean) -> {
            copyBoundary(aabb);
        });
    }

    public static Clipboard clipboard = null;

    public static void copyBoundary(AABB area) {
        boolean foundNonAirBlock = false;
        //Scan the area
        HashSet<SubChunk> chunksEncountered = new HashSet<>();

        clipboard = new Clipboard((int) area.getXLength(), (int) area.getYLength(), (int) area.getZLength());
        for (int x = 0; x < area.getXLength(); x++) {
            for (int z = 0; z < area.getZLength(); z++) {
                for (int y = 0; y < area.getYLength(); y++) {
                    int xCoord = (int) (x + area.minPoint.x);
                    int yCoord = (int) (y + area.minPoint.y);
                    int zCoord = (int) (z + area.minPoint.z);

                    WCCi wcc = new WCCi().set(xCoord, yCoord, zCoord);
                    if (wcc.chunkExists()) {
                        SubChunk chunk = wcc.getSubChunk();
                        chunksEncountered.add(chunk);

                        Block block = ItemList.getBlock(chunk.getVoxels().getBlock(
                                wcc.subChunkVoxel.x,
                                wcc.subChunkVoxel.y,
                                wcc.subChunkVoxel.z));

                        if (!block.isAir() && block.saveInChunk() && block.isInfiniteResource()) {
                            foundNonAirBlock = true;
                            BlockData data = chunk.getVoxels().getBlockData(
                                    wcc.subChunkVoxel.x,
                                    wcc.subChunkVoxel.y,
                                    wcc.subChunkVoxel.z);
                            clipboard.blocks.set(block.id, x, y, z);
                            clipboard.blocks.setBlockData(data, x, y, z);
                        }
                    }
                }
            }
        }

        //<editor-fold defaultstate="collapsed" desc="copy entities">
        for (SubChunk chunk : chunksEncountered) {
            for (Entity e : chunk.getEntities().list) {
                if (e.link.saveInChunk()
                        && !(e instanceof Animal)
                        && !(e instanceof Vehicle)) {
                    if (e.worldPosition.x >= area.minPoint.x
                            && e.worldPosition.y >= area.minPoint.y
                            && e.worldPosition.z >= area.minPoint.z
                            && e.worldPosition.x <= area.maxPoint.x
                            && e.worldPosition.y <= area.maxPoint.y) {
                        if (e.worldPosition.z <= area.maxPoint.z) {

                            Vector3f areaMaxPoint = new Vector3f(area.minPoint);
                            Vector3f normalizedPosition = new Vector3f(e.worldPosition);
                            normalizedPosition.sub(areaMaxPoint);
                            clipboard.entities.put(normalizedPosition, new EntityTemplate(e));
                            foundNonAirBlock = true;
                        }
                    }
                }
            }
        }
//</editor-fold>
        if (foundNonAirBlock) {
            VoxelGame.getGame().alert("Boundary Copied");
        } else {
            clipboard = null;
            VoxelGame.getGame().alert("Nothing to copy!");
        }
    }

    @Override
    public void deactivate() {
        VoxelGame.getPlayer().camera.cursorRay.disableBoundaryMode();
    }

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        return window.keysArePressed(KeyCode.CTRL, KeyCode.C);
    }

    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        return false;
    }
}
