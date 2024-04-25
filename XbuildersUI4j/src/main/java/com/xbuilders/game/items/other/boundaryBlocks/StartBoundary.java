/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.boundaryBlocks;

import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.TerrainUpdater;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class StartBoundary extends Block {

    /**
     * @param boundaryCreatedEvent the boundary set event runnable to run when
     * the boundary is set
     */
    public void setBoundaryCreatedEvent(BoundarySetEvent boundaryCreatedEvent) {
        this.boundaryCreatedEvent = boundaryCreatedEvent;
    }

    /**
     * Places a boundary endpoint in the world.
     *
     * @param x
     * @param y
     * @param z
     * @param runnable the boundary set event runnable to run when the boundary
     * is set
     */
    public void place(int x, int y, int z, BoundarySetEvent runnable) {
        synchronized (eventLock) {
            setBoundaryCreatedEvent(runnable);
            setBlock(x, y, z, null);
        }
    }

    protected final Object eventLock = new Object();
//    private EndBoundary endBoundary;
    protected Vector3i firstMarker = null;
    protected Vector3i secondMarker = null;
    protected BoundingBox box = null;
    protected BoundarySetEvent boundaryCreatedEvent = null;


    public StartBoundary() {
        super(543, "Start Boundary hidden");
        (this.texture = new BlockTexture()).setTOP(12, 13);
        this.texture.setBOTTOM(12, 13);
        this.texture.setSIDES(12, 14);
//        endBoundary = new EndBoundary(this);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    protected boolean bothMarkersExist() {
        return firstMarker != null && secondMarker != null;
    }

    protected void clearFirstMarker() {
        if (firstMarker != null) {
            BlockList.BLOCK_AIR.set(firstMarker);
        }
        firstMarker = null;
        if (box != null) {
            box.remove();
        }
    }

    protected void deleteBothMarkerBlocks() {
        if (firstMarker != null) {
            BlockList.BLOCK_AIR.set(firstMarker);
        }
        if (secondMarker != null) {
            BlockList.BLOCK_AIR.set(secondMarker);
        }
    }

    protected void deleteBothMarkerCoordinates() {
        firstMarker = null;
        secondMarker = null;
        if (box != null) {
            box.remove();
        }
    }

    protected void clearSecondMarker() {
        if (secondMarker != null) {
            BlockList.BLOCK_AIR.set(secondMarker);
        }
        secondMarker = null;
        if (box != null) {
            box.remove();
        }
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockData data) {
        if (firstMarker == null) {
            firstMarker = new Vector3i(x, y, z);
            this.set(x, y, z, null);
            if (secondMarker != null) {
                box = makeCopyBox();
            }
        } else if (secondMarker == null) {
            secondMarker = new Vector3i(x, y, z);
            this.set(x, y, z, null);
            makeCopyBox();
        } else {
            clearFirstMarker();
            clearSecondMarker();
        }
        return true;
    }

    protected BoundingBox makeCopyBox() {
        int maxArea = getPointerHandler().getSettingsFile().maxBlockBoundaryArea;
        int maxWidth = getPointerHandler().getSettingsFile().chunkRadius / 2;

        if (bothMarkersExist()) {
            AABB aabb = BoundingBox.makeAABBFrom2Points(firstMarker, secondMarker);
            if (aabb.getXLength() * aabb.getYLength() * aabb.getZLength() >= maxArea) {
                clearFirstMarker();
                clearSecondMarker();
                getPointerHandler().getGame().alert("The boundary exceeds a maximum area of " + maxArea + " blocks!");
            } else {
                TerrainUpdater terrainUpdater = getPointerHandler().getTerrainUpdater();
                if (terrainUpdater.regularViewDistance
                        && (aabb.getXLength() >= maxWidth || aabb.getZLength() >= maxWidth)) {
                    clearFirstMarker();
                    clearSecondMarker();
                    getPointerHandler().getGame().alert("The boundary dimensions exceeds max width of " + maxWidth + " blocks!");
                } else {
                    getPointerHandler().getGame().alert("Boundary set! ("
                            + ((int) aabb.getXLength()) + " x "
                            + ((int) aabb.getYLength()) + " x "
                            + ((int) aabb.getZLength()) + ")");

                    BoundingBox holo = new BoundingBox(this, aabb);
                    getPointerHandler().getWorld().hologramList.add(holo);
                    return holo;
                }
            }
        }
        return null;
    }

    @Override
    public boolean onClickEvent(int setX, int setY, int setZ) {
        if (bothMarkersExist()) {
            if (boundaryCreatedEvent != null) {
                System.out.println("Boundary event: " + firstMarker + " " + secondMarker);
                AABB aabb = BoundingBox.makeAABBFrom2Points(firstMarker, secondMarker);
                synchronized (eventLock) {
                    deleteBothMarkerBlocks();
                    boundaryCreatedEvent.onBoundarySet(aabb,
                            new Vector3i(firstMarker),
                            new Vector3i(secondMarker));
                    setBoundaryCreatedEvent(null);
                }
            } else {
                getPointerHandler().getGame().alert("#### No event attatched to boundary! ####");
            }
        } else {
            getPointerHandler().getGame().alert("Error! a copy pinpoint block is missing!");
        }

        deleteBothMarkerCoordinates();
        return false;
    }

    @Override
    public void afterRemovalEvent(int x, int y, int z) {
        clearFirstMarker();
        clearSecondMarker();
    }

    @Override
    public boolean saveInChunk() {
        return false;
    }

}
