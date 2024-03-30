/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.copyPaste;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityTemplate;
import com.xbuilders.game.items.entities.mobile.Animal;
import com.xbuilders.game.items.entities.mobile.Vehicle;
import com.xbuilders.engine.items.tool.Tool;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.other.boundaryBlocks.BoundarySetEvent;
import java.util.HashSet;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class CopyTool extends Tool {

    public CopyTool() {
        super(5, "Copy Tool");
        setIconAtlasPosition(2, 8);
        tags.add("copy");
        tags.add("paste");
    }

    @Override
    public boolean isInfiniteResource() {
        return true;
    }

    public BoundarySetEvent boundarySetEvent = new BoundarySetEvent() {
        @Override
        public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
            copyBoundary(boundary);
        }
    };

    @Override
    public boolean onPlace(int setX, int setY, int setZ) {
             if (getPointerHandler().getGame().mode != GameMode.FREEPLAY) {
            VoxelGame.getGame().alert("You cannot do that in " + getPointerHandler().getGame().mode + " mode");
            return false;
        }
        System.err.println("Place!");
        GameItems.START_BOUNDARY.place(setX, setY, setZ, boundarySetEvent);
        return false;
    }
    public static Clipboard clipboard = null;

    private void copyBoundary(AABB area) {
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
            getPointerHandler().getGame().alert("Boundary Copied");
        } else {
            clipboard = null;
            getPointerHandler().getGame().alert("Nothing to copy!");
        }
    }
}
