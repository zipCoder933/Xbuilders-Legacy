// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.utils.ArrayUtils;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.lightMap.TorchChannelSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.joml.Vector3f;

public class ChunkSavingLoading {

    public static final byte NEWLINE_BYTE = Byte.MIN_VALUE;
    public static final byte PIPE_BYTE = -127;
    public static final byte ENTITY_BYTE = -126;
    public static final byte SKIP_BYTE = -125;
    public static final int METADATA_BYTES = 10;

    private static void writeAndVerifyBlockData(final OutputStream out, final byte[] bytes) throws IOException {
        if (bytes != null) {
            for (final byte b : bytes) {
                if (b == NEWLINE_BYTE) {
                    throw new IllegalArgumentException("The byte [NEWLINE_BYTE] is forbidden for use.");
                }
                out.write(b);
            }
        }
    }

    private static byte[] metadata(final Chunk chunk) {
        final byte lightmapInit = (byte) (chunk.lightmapInitialized ? 1 : 0);
        final byte[] bytes = new byte[10];
        bytes[0] = lightmapInit;
        return bytes;
    }

    private static void writeEntityChunkCoordinates(final OutputStream out, final Vector3f chunkPosition)
            throws IOException {
        final short x = (short) (chunkPosition.x * 100.0f);
        final short y = (short) (chunkPosition.y * 100.0f);
        final short z = (short) (chunkPosition.z * 100.0f);
        out.write(shortToBytes(x));
        out.write(shortToBytes(y));
        out.write(shortToBytes(z));
    }

    private static int readEntityChunkCoordinates(final byte[] bytes, int start, final Vector3f chunkPosition) {
        chunkPosition.x = bytesToShort(bytes[start], bytes[start + 1]) / 100.0f;
        chunkPosition.y = bytesToShort(bytes[start + 2], bytes[start + 3]) / 100.0f;
        chunkPosition.z = bytesToShort(bytes[start + 4], bytes[start + 5]) / 100.0f;
        start += 6;
        return start;
    }

    public static void writeChunkToFile(final Chunk chunk, final File f) throws IOException {
        final FileOutputStream fos = new FileOutputStream(f);
        try {
            final GZIPOutputStream out = new GZIPOutputStream(fos);
            try {
                out.write(metadata(chunk));
                boolean iterateOverVoxels = true;

                // int skyStop = Chunk.CHUNK_Y_LENGTH; //We cant stop at the sky, because dark
                // propagation stops after 3 sub chunks.
                // skyStop = calculateSkyMark(chunk, skyStop);
                for (int subChunkID = chunk.getSubChunks().length - 1; subChunkID >= 0; --subChunkID) {
                    final SubChunk subChunk = chunk.getSubChunks()[subChunkID];
                    XBFilterOutputStream fout = new XBFilterOutputStream(out);
                    for (final Entity entity : subChunk.entities.list) {
                        out.write(ENTITY_BYTE);
                        out.write(shortToBytes(entity.link.id));
                        writeEntityChunkCoordinates(out, entity.getChunkPosition());

                        entity.toBytes(fout);

                        out.write(NEWLINE_BYTE);
                    }
                    if (iterateOverVoxels) {
                        for (int y = 15; y >= 0; --y) {
                            // boolean layerIsAllSky = true;
                            for (int x = 0; x < SubChunk.WIDTH; ++x) {
                                for (int z = 0; z < SubChunk.WIDTH; ++z) {
                                    final Block block = ItemList.getBlock(subChunk.data.getBlock(x, y, z));
                                    final TorchChannelSet torch = subChunk.lightMap.getTorchlight(x, y, z);
                                    final byte sunlight = subChunk.lightMap.getSunlight(x, y, z);
                                    // if (layerIsAllSky) {
                                    // final boolean fragIsSkyFrag = sunlight == 15 && torch == null;
                                    // if (!block.isAir() || !fragIsSkyFrag
                                    // || ((subChunk.getPosition().getY() * SubChunk.WIDTH) + y) > skyStop) {
                                    // layerIsAllSky = false;
                                    // }
                                    // }
                                    if (sunlight > 0 || torch != null) {
                                        out.write(sunlight);
                                        if (torch != null) {//Write torch values
                                            for (final Map.Entry<Byte, Byte> entry : torch.list.entrySet()) {
                                                if (entry.getValue() > 0) {
                                                    out.write(entry.getKey());
                                                    out.write(entry.getValue());
                                                }
                                            }
                                        }
                                    }
                                    if (!block.isAir() && block.saveInChunk()) {
                                        out.write(PIPE_BYTE);
                                        final byte[] b = shortToBytes(block.id);
                                        out.write(b);
                                        final BlockData blockData = subChunk.data.getBlockData(x, y, z);
                                        if (blockData != null) {
                                            writeAndVerifyBlockData(out, blockData.bytes);
                                        }
                                    }
                                    out.write(NEWLINE_BYTE);
                                }
                            }
                            // if (layerIsAllSky) {
                            // iterateOverVoxels = false;
                            // out.write(SKIP_BYTE);
                            // break;
                            // }
                        }
                    } else {
                        out.write(SKIP_BYTE);
                    }
                }
                out.finish();
                out.close();
            } catch (Throwable t) {
                ErrorHandler.handleFatalError("Error saving chunk", "The chunk saving algorithm threw an exception", t);
            }
            fos.close();
        } catch (Throwable t2) {
            ErrorHandler.handleFatalError("Error saving chunk", "The chunk saving algorithm threw an exception", t2);
        }
    }

    private static void readMetadata(final Chunk chunk, final InputStream is) throws IOException {
        final byte[] data = new byte[10];
        final int bytesRead = is.readNBytes(data, 0, data.length);
        if (bytesRead != -1) {
            chunk.setLightmapInitialized(data[0] == 1);
            return;
        }
        throw new IOException("No metadata in chunk");
    }

    protected static void readChunkFromFile(final Chunk chunk, final File f) throws IOException {
        final FileInputStream fis = new FileInputStream(f);
        try {
            final GZIPInputStream input = new GZIPInputStream(fis);
            try {
                readMetadata(chunk, input);
                final byte[] bytes = input.readAllBytes();
                Label_0186: for (int start = 0, subChunkID = chunk.getSubChunks().length - 1; subChunkID >= 0
                        && start < bytes.length; --subChunkID) {
                    final SubChunk subChunk = chunk.getSubChunks()[subChunkID];
                    start = readEntities(bytes, subChunk, start);
                    for (int y = 15; y >= 0; --y) {
                        for (int x = 0; x < SubChunk.WIDTH; ++x) {
                            for (int z = 0; z < SubChunk.WIDTH; ++z) {
                                final byte startByte = bytes[start];
                                if (startByte == SKIP_BYTE) {
                                    ++start;
                                    continue Label_0186;
                                }
                                if (startByte == ENTITY_BYTE) {
                                    continue Label_0186;
                                }
                                if (startByte == NEWLINE_BYTE) {
                                    ++start;
                                } else {
                                    start = readVoxel(bytes, startByte, subChunk, x, y, z, start);
                                }
                            }
                        }
                    }
                }
                input.close();
            } catch (Throwable t) {
                try {
                    input.close();
                } catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
            fis.close();
        } catch (Throwable t2) {
            try {
                fis.close();
            } catch (Throwable exception2) {
                t2.addSuppressed(exception2);
            }
            throw t2;
        }
    }

    private static int readVoxel(final byte[] bytes, final byte startByte, final SubChunk subChunk, final int x,
            final int y, final int z, int start) {
        if (startByte != PIPE_BYTE) {
            final byte sunlight = startByte;
            subChunk.lightMap.setSunlight(x, y, z, sunlight);
            ++start;
            //Read torch values
            while (true) {
                final byte b1 = bytes[start];
                if (b1 == NEWLINE_BYTE) {
                    return start + 1;
                }
                if (b1 == PIPE_BYTE) {
                    break;
                }
                final byte value = bytes[start + 1];
                if (value > 0) //only add torchlight if value is > 0
                    subChunk.lightMap.setTorchlight(x, y, z, b1, value);
                start += 2;
            }
        }
        final short blockID = bytesToShort(bytes[start + 1], bytes[start + 2]);
        final Block block = ItemList.blocks.getItem(blockID);
        if (block == null) {
            subChunk.data.setBlock(BlockList.BLOCK_AIR.id, x, y, z);
        } else {
            subChunk.data.setBlock(block.id, x, y, z);
        }
        start += 3;
        final ArrayList<Byte> blockDataBytes = new ArrayList<Byte>();
        while (true) {
            final byte b3 = bytes[start];
            if (b3 == NEWLINE_BYTE) {
                break;
            }
            blockDataBytes.add(b3);
            ++start;
        }
        if (!blockDataBytes.isEmpty()) {
            subChunk.data.setBlockData(new BlockData(blockDataBytes), x, y, z);
        }
        return start + 1;
    }

    public static byte[] shortToBytes(final int x) {
        final byte b1 = (byte) x;
        final byte b2 = (byte) (x >> 8);
        return new byte[] { b1, b2 };
    }

    public static short bytesToShort(final byte b1, final byte b2) {
        final short result = (short) (b2 << 8 | (b1 & 0xFF));
        return result;
    }

    private static int readEntities(final byte[] bytes, final SubChunk subChunk, int start) {
        final ArrayList<Byte> entityBytes = new ArrayList<Byte>();
        while (bytes[start] == ENTITY_BYTE) {
            entityBytes.clear();
            final short entityID = bytesToShort(bytes[start + 1], bytes[start + 2]);
            final Vector3f chunkPos = new Vector3f();
            start = readEntityChunkCoordinates(bytes, start + 3, chunkPos);
            final EntityLink item = ItemList.entities.getItem(entityID);
            while (true) {
                final byte b = bytes[start];
                if (b == NEWLINE_BYTE) {
                    break;
                }
                entityBytes.add(b);
                ++start;
            }
            if (item != null) {
                try {
                    float worldX = chunkPos.x + (subChunk.position.x * SubChunk.WIDTH);
                    float worldY = chunkPos.y + (subChunk.position.y * SubChunk.WIDTH);
                    float worldZ = chunkPos.z + (subChunk.position.z * SubChunk.WIDTH);
                    final byte[] entityData = ArrayUtils.ListByteToArray(entityBytes);
                    Entity entity = item.makeNew(subChunk, worldX, worldY, worldZ, entityData, false);
                    subChunk.entities.list.add(entity);
                } catch (Exception ex) {
                    ErrorHandler.handleFatalError(ex);
                    ex.printStackTrace();
                }
            }
            ++start;
        }
        return start;
    }
}
