// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk.lightMap;

import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.VoxelGame;
import org.joml.Vector3i;
import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.wcc.WCCi;

import java.util.HashMap;

public final class ChunkLightMap {

    private boolean hasChanged;
    private HashMap<Integer, TorchChannelSet> torch;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    SubChunk chunk;
    byte[] sunlight;
    public boolean allDarkness = true;
    private boolean pastedIntoWLM;
    public static final byte MAX_LIGHT = 15;
    public boolean initialized;

    public void reset(final SubChunk parent) {
        synchronized (ShaderLightMap.lightmapInitializationLock) {
            this.pastedIntoWLM = false;
            this.initialized = false;
            this.hasChanged = true;
            this.chunk = parent;
            for (int i = 0; i < this.sunlight.length; ++i) {
                this.sunlight[i] = 0;
            }
            this.torch.clear();
            allDarkness = true;
        }
    }

    public boolean isHasChanged() {
        return this.hasChanged;
    }

    public boolean inBoundsOfSLM() {
        final int halfWidth = 8;
        return ShaderLightMap.inBounds(
                this.chunk.getPosition().x * SubChunk.WIDTH + halfWidth,
                this.chunk.getPosition().y * SubChunk.WIDTH + halfWidth,
                this.chunk.getPosition().z * SubChunk.WIDTH + halfWidth);
    }

    public boolean inSLM() {
        synchronized (ShaderLightMap.lightmapInitializationLock) {
            final int x = 8;
            final int worldX = x + this.chunk.getPosition().x * SubChunk.WIDTH;
            final int worldY = x + this.chunk.getPosition().y * SubChunk.WIDTH;
            final int worldZ = x + this.chunk.getPosition().z * SubChunk.WIDTH;
            final Vector3i lightmapCoords = ShaderLightMap.worldCoordsToLightmapCoords(worldX, worldY, worldZ);
            final int idx = ShaderLightMap.coordsToIndex(lightmapCoords);
            return ShaderLightMap.getImagePixels()[idx] != -2;
        }
    }

    public boolean pasteIntoSLM() {
        int placesSet = 0;
        int worldX, worldY, worldZ, idx;
        WCCi wcc = new WCCi();
        Vector3i lightmapCoords = new Vector3i();

        for (int x = -1; x < this.getSizeX() + 1; ++x) {
            for (int z = -1; z < this.getSizeZ() + 1; ++z) {
                for (int y = 0; y < this.getSizeY(); ++y) {
                    worldX = x + this.chunk.getPosition().x * SubChunk.WIDTH;
                    worldY = y + this.chunk.getPosition().y * SubChunk.WIDTH;
                    worldZ = z + this.chunk.getPosition().z * SubChunk.WIDTH;
                    if (ShaderLightMap.inBounds(worldX, worldY, worldZ)) {
                        ShaderLightMap.worldCoordsToLightmapCoords(lightmapCoords, worldX, worldY, worldZ);
                        idx = ShaderLightMap.coordsToIndex(lightmapCoords);

                        if (this.chunk.getVoxels().inBounds(x, y, z)) {
                            ShaderLightMap.setImagePixelValue(idx,
                                    ShaderLightMap.getLightmapValue(this.chunk, x, y, z));
                            ++placesSet;
                        } else if (VoxelGame.getWorld().inBounds(worldY)) {
                            try {
                                wcc.set(worldX, worldY, worldZ);
                                final SubChunk neighboringChunk = VoxelGame.getWorld().getSubChunk(wcc.subChunk);
                                if (neighboringChunk != null && neighboringChunk.getLightMap().initialized) {
                                    ShaderLightMap.setImagePixelValue(idx,
                                            ShaderLightMap.getLightmapValue(neighboringChunk,
                                                    wcc.subChunkVoxel.x, wcc.subChunkVoxel.y, wcc.subChunkVoxel.z));
                                }
                            } catch (IndexOutOfBoundsException ex) {
                                ErrorHandler.handleFatalError(ex);
                            }
                        }
                    }
                }
            }
        }
        return placesSet > 0;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public int getSizeZ() {
        return this.sizeZ;
    }

    public ChunkLightMap(final SubChunk chunk, final int sizeX1, final int sizeY1, final int sizeZ1) {
        this.hasChanged = false;
        this.initialized = false;
        this.sizeX = sizeX1;
        this.sizeY = sizeY1;
        this.sizeZ = sizeZ1;
        this.initialized = false;
        this.hasChanged = true;
        this.chunk = chunk;
        this.sunlight = new byte[this.getSizeX() * this.getSizeY() * this.getSizeZ()];
        this.torch = new HashMap<Integer, TorchChannelSet>();
        this.pastedIntoWLM = false;
    }

    public int getIndexOfCoords(final int x, final int y, final int z) {
        return x + this.getSizeX() * (y + this.getSizeY() * z);
    }

    public byte getSunlight(final int x, final int y, final int z) {
        try {
            return this.sunlight[this.getIndexOfCoords(x, y, z)];
        } catch (IndexOutOfBoundsException ex) { // Take note if this causes any bugs elsewhere
            ErrorHandler.saveErrorToLogFile(ex, "Sunlight index out of bounds");
            return 15;
        }
    }

    public byte getSunlight(final Vector3i vec) {
        return this.getSunlight(vec.x, vec.y, vec.z);
    }

    public void setSunlight(final int x, final int y, final int z, final byte val) {
        try {
            this.sunlight[this.getIndexOfCoords(x, y, z)] = val;
            if (val > 0)
                this.allDarkness = false;
        } catch (IndexOutOfBoundsException ex) {
            ErrorHandler.saveErrorToLogFile((Throwable) ex);
            ErrorHandler.haltProgram(this.chunk.getPointerHandler());
        }
    }

    public void setSunlight(final Vector3i vec, final byte val) {
        this.setSunlight(vec.x, vec.y, vec.z, val);
    }

    public void setSunlightAndUpdateSLM(final int x, final int y, final int z, final byte val) {
        this.setSunlight(x, y, z, val);
        this.updateSLM(x, y, z, false);
    }

    public void makeTorchlight(final int x, final int y, final int z) {
        final int key = this.getIndexOfCoords(x, y, z);
        this.allDarkness = false;

        if (!this.torch.containsKey(key)) {
            this.torch.put(key, new TorchChannelSet());
        }
    }

    public TorchChannelSet getTorchlight(final int x, final int y, final int z) {
        final int key = this.getIndexOfCoords(x, y, z);
        return this.torch.get(key);
    }

    public TorchChannelSet getTorchlight(final Vector3i vec) {
        return this.getTorchlight(vec.x, vec.y, vec.z);
    }

    public void setTorchlight(final int x, final int y, final int z, final byte channel, final byte val) {
        final int key = this.getIndexOfCoords(x, y, z);
        if (val > 0)
        this.allDarkness = false;

        if (this.torch.containsKey(key)) {
            final TorchChannelSet channelSet = this.torch.get(key);
            channelSet.set(channel, val);
        } else {
            final TorchChannelSet channelSet = new TorchChannelSet();
            channelSet.set(channel, val);
            this.torch.put(key, channelSet);
        }
    }

    public void updateSLM(final int x, final int y, final int z, final boolean isTorchlight) {
        if (this.isPastedIntoSLM()) {
            ShaderLightMap.updateLightmapVoxel(this.chunk, x, y, z);
            if (this.initialized && isTorchlight) {
                this.chunk.getParentChunk().markAsModifiedByUser();
            }
            this.hasChanged = true;
            this.chunk.getParentChunk().markAsNeedsSaving();
        }
    }

    public void markAsPastedIntoSLM() {
        this.pastedIntoWLM = true;
    }

    public boolean isPastedIntoSLM() {
        return this.pastedIntoWLM;
    }

    public int getSunAndTorchAsPackedByte(final int x, final int y, final int z) {
        final int sun = this.getSunlight(x, y, z);
        int torch = 0;
        final TorchChannelSet torchChannels = this.getTorchlight(x, y, z);
        if (torchChannels != null) {
            torch = torchChannels.getBrightestChannel();
        }
        final int packed = sun << 4 | (torch & 0xF);
        return packed;
    }

    public int getSunPlusTorch(final int x, final int y, final int z) {
        final int sun = this.getSunlight(x, y, z);
        int torch = 0;
        final TorchChannelSet torchChannels = this.getTorchlight(x, y, z);
        if (torchChannels != null) {
            torch = torchChannels.getBrightestChannel();
        }
        return sun + torch;
    }

    public static int getSunlightFromPackedByte(final int packed) {
        return packed >> 4 & 0xF;
    }

    public static int getTorchLightFromPackedByte(final int packed) {
        return packed & 0xF;
    }

    public boolean inBounds(final Vector3i coords) {
        return coords.x < this.sizeX && coords.y < this.sizeY && coords.z < this.sizeZ && coords.x >= 0 && coords.y >= 0
                && coords.z >= 0;
    }

}
