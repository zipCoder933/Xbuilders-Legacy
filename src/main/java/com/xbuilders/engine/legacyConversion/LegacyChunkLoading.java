package com.xbuilders.engine.legacyConversion;

import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.lightMap.TorchChannelSet;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 * @author zipCoder933
 */
public class LegacyChunkLoading {

    private static void metadataFromString(Chunk chunk, String metadata) {
        metadata = metadata.replace("lightmap:", "").replace("used:", "");
        String[] items = metadata.split(Pattern.quote("|"));
        chunk.setLightmapInitialized(items[0].equals("1"));
    }

    public static void lightFragFromString(String str, SubChunk subChunk, int x, int y, int z) {
        int i = 0;
        for (String string : str.split("_")) {
            byte number = 0;
            if (!string.equals("")) {
                number = (byte) Short.parseShort(string);
            }
            if (i == 0) {
                subChunk.getLightMap().setSunlight(x, y, z, number);
            } else {
                subChunk.getLightMap().makeTorchlight(x, y, z);
                TorchChannelSet tcs = subChunk.getLightMap().getTorchlight(x, y, z);
                if (tcs.list.containsKey((byte) 1)) { //We have no way of determining what the light falloff of the torch is
                    if (tcs.list.get((byte) 1) < number) {
                        tcs.list.put((byte) 1, number);
                    }
                } else {
                    tcs.list.put((byte) 1, number);
                }
            }
            i++;
        }
    }

    /**
     *
     * @param chunk the chunk
     * @param f the file to load
     * @throws IOException
     */
    public static void readChunkFromFile(Chunk chunk, File f) throws IOException {
//        InputStream is = new FileInputStream(f);
//        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
//        String line = sc.nextLine();
//        metadataFromString(chunk, line);
//
//        boolean skyMode = false;
//        for (int subChunkID = chunk.getSubChunks().length - 1; subChunkID >= 0; subChunkID--) { //From the bottom subchunk to the top
//            SubChunk subChunk = chunk.getSubChunks()[subChunkID];
//
//            outerloop:
//            for (int y = SubChunk.WIDTH - 1; y >= 0; y--) {
//                for (int x = 0; x < CHUNK_X_LENGTH; x++) {
//                    for (int z = 0; z < CHUNK_Z_LENGTH; z++) {
//
//                        if (skyMode) {
//                            subChunk.getLightMap().setSunlight(x, y, z, (byte) 15);
//                        } else if (sc.hasNextLine()) {
//                            line = sc.nextLine();
//                            if (line.equals("entities")) {
//                                subChunk.getLightMap().setSunlight(x, y, z, (byte) 15);
//                                skyMode = true;
//                                continue;
//                            }
//                            String[] pieces = line.split("\\|");
//
//                            short id = 0;
//                            if (!pieces[0].equals("")) {
//                                id = Short.parseShort(pieces[0]);
//                            }
//
//                            if (pieces.length > 1) {
//                                lightFragFromString(pieces[1], subChunk, x, y, z);
//                            }
//                            if (id != 0) { //If the block is NOT an air block (don't check isAir() because some other block use that method too.)...
//                                BlockData blockData = null;
//                                if (pieces.length > 2) {
//                                    String dataStr = pieces[2];
//                                    blockData = new BlockData(dataStr.getBytes());
//                                    subChunk.getVoxels().setBlockData(blockData, x, y, z);
//                                }
//                                Block block = chunk.getPointerHandler().getBlockList().getItem(id);
//                                if (block != null) {
//                                    subChunk.getVoxels().setBlock(block, x, y, z);
//                                }
//                            }
//                        } else {
//                            return;
//                        }
//                    }
//                }
//            }
//        }
//        if (line != null && line.equals("entities")
//                || (sc.hasNextLine() && sc.nextLine().equals("entities"))) {
//            while (true) {
//                if (sc.hasNextLine()) {
//                    line = sc.nextLine();
//                    int pipeIndex = line.indexOf("|");
//                    if (pipeIndex > -1) {
//                        int id = Integer.parseInt(line.substring(0, pipeIndex));
//                        String properties = line.substring(pipeIndex + 1);
//                        EntityLink entityLink = (EntityLink) chunk.getPointerHandler().getItems().entityList.getItem(id);
//                        if (entityLink != null) {
//                            System.out.println("Loading " + entityLink.toString() + ": " + properties);
//                            loadNewEntity(entityLink, properties, chunk);
//                        }
//                    }
//                } else {
//                    break;
//                }
//            }
//        }
    }

    private static Entity loadNewEntity(EntityLink entityLink, String properties, Chunk chunk) {
//        Entity entity = entityLink.newEntity();
//
//        String[] chunks = properties.split(Pattern.quote("|"));
//        String[] positionSubChunk = chunks[0].split(",");
//        entity.worldPosition.set(
//                Float.parseFloat(positionSubChunk[0]),
//                Float.parseFloat(positionSubChunk[1]),
//                Float.parseFloat(positionSubChunk[2]));
//
//        int index = WCCi.chunkDiv((int) entity.worldPosition.y);
//        SubChunk subChunk = chunk.getSubChunks()[index];
//        entity.chunk = subChunk;
//
////        entity.loadLegacy(chunks);
//
//        subChunk.getEntities().add(entity);
//        entity.initialized = true;
        return null;
    }

}
