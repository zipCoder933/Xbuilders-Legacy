package com.xbuilders.engine.utils.xb3;

import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.other.BlockGrid;
import org.joml.Vector3i;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public class PrefabUtils {
    public static final long ENTITY_MAX_BYTES = (long) (Math.pow(2, 32) - 1); //Unsigned int
    public static int BLOCK_DATA_MAX_BYTES = (int) (Math.pow(2, 16) - 1); //Unsigned short

    public static void writeBlockData(BlockData data, OutputStream out) throws IOException {
        if (data == null) {
            out.write(new byte[]{0, 0});//Just write 0 for the length
            return;
        }

        if (data.size() > BLOCK_DATA_MAX_BYTES) {
            ErrorHandler.report(new Throwable("Block data too large: " + data.size()));
            out.write(new byte[]{0, 0});//Just write 0 for the length
            return;
        }
        //First write the length of the block data as an unsigned short
        out.write(ByteUtils.shortToBytes(data.size() & 0xffff));

        //Then write the bytes
        byte[] bytes = data.toByteArray();
        out.write(bytes);
    }

    public static BlockData readBlockData(byte[] bytes, AtomicInteger start) {
        //Get the length from unsigned short to int
        int length = ByteUtils.bytesToShort(bytes[start.get()], bytes[start.get() + 1]) & 0xffff;
        start.set(start.get() + 2);

        //Read the bytes
        byte[] data = new byte[length];
        System.arraycopy(bytes, start.get(), data, 0, length);
        start.set(start.get() + length);

        return new BlockData(data);
    }


    public static void toBytes(BlockGrid blocks, OutputStream out) throws IOException {
        //Write the size first!
        out.write(ByteUtils.shortToBytes(blocks.size.x));
        out.write(ByteUtils.shortToBytes(blocks.size.y));
        out.write(ByteUtils.shortToBytes(blocks.size.z));

        for (int x = 0; x < blocks.size.x; x++) {
            for (int y = 0; y < blocks.size.y; y++) {
                for (int z = 0; z < blocks.size.z; z++) {
                    short block = blocks.getBlock(x, y, z);
                    out.write(ByteUtils.shortToBytes(block));//Block
                    writeBlockData(blocks.getBlockData(x, y, z), out); //Block data
                }
            }
        }
    }

    public static BlockGrid fromBytes(byte[] bytes, int start, int end) {
        Vector3i size = new Vector3i(0, 0, 0);
        //Read and load the size first!
        size.x = ByteUtils.bytesToShort(bytes[start], bytes[start + 1]);
        size.y = ByteUtils.bytesToShort(bytes[start + 2], bytes[start + 3]);
        size.z = ByteUtils.bytesToShort(bytes[start + 4], bytes[start + 5]);

        if (size.x == 0 || size.y == 0 || size.z == 0) {
            return new BlockGrid(0, 0, 0);
        }

        BlockGrid data = new BlockGrid(size.x, size.y, size.z);


        AtomicInteger startIndex = new AtomicInteger(start + 6);
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                for (int z = 0; z < size.z; z++) {
                    short block = (short) ByteUtils.bytesToShort(bytes[startIndex.get()], bytes[startIndex.get() + 1]);
                    data.setBlock(block, x, y, z);
                    startIndex.set(startIndex.get() + 2);

                    BlockData blockData = readBlockData(bytes, startIndex);
                    data.setBlockData(blockData, x, y, z);
                }
            }
        }
        return data;
    }


    public static BlockGrid loadPrefabFromFile(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return fromBytes(bytes, 0, bytes.length);
    }

    public static void savePrefabToFile(BlockGrid data, File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toBytes(data, baos);
        Files.write(file.toPath(), baos.toByteArray());
    }

    public static BlockGrid loadPrefabFromFileDialog() {
        File outFile = FileDialog.fileDialog((fd) -> {
            File prefabFolder = getPrefabFolder();
            if (!prefabFolder.exists()) {
                prefabFolder.mkdirs();
            }
            fd.setDirectory(prefabFolder.getAbsolutePath());
            fd.setFilenameFilter(filter);
            fd.setFile("*.xbprefab");
            fd.setMode(java.awt.FileDialog.LOAD);
        });

        if (outFile != null) {
            try {
                return loadPrefabFromFile(outFile);
            } catch (IOException e) {
                ErrorHandler.report(e);
            }
        }
        return null;
    }

    static FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".xbprefab");
        }
    };

    public static void savePrefabToFileDialog(BlockGrid data) {
        FileDialog.fileDialog((fd) -> {
            File prefabFolder = getPrefabFolder();
            if (!prefabFolder.exists()) {
                prefabFolder.mkdirs();
            }
            fd.setDirectory(prefabFolder.getAbsolutePath());
            fd.setFilenameFilter(filter);
            fd.setFile("*.xbprefab");
            fd.setMode(java.awt.FileDialog.SAVE);
        }, (file) -> {
            if (file == null) {
                return;
            }
            try {
                PrefabUtils.savePrefabToFile(data, file);
            } catch (IOException e) {
                ErrorHandler.report(e);
            }
        });
    }

    private static File getPrefabFolder() {
        File xb3AppDataDir = new File(System.getenv("LOCALAPPDATA") + "\\xbuilders");
        if (xb3AppDataDir.exists()) {
            return new File(xb3AppDataDir, "prefabs");
        }
        return ResourceUtils.appDataResource("prefabs");
    }


}
