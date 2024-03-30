/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.legacyConversion;

import com.xbuilders.engine.utils.MiscUtils;
import static com.xbuilders.engine.utils.MiscUtils.readYesOrNoInput;
import com.xbuilders.engine.utils.preformance.MemoryProfiler;
import com.xbuilders.engine.world.World;
import com.xbuilders.engine.world.WorldUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.chunk.ChunkSavingLoading;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.game.PointerHandler;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author zipCoder933
 */
public class LegacyConverter {

    public LegacyConverter(PointerHandler ph) throws IOException {
        System.out.println(MemoryProfiler.getMemoryUsageAsString());
        System.out.println("\n\nLEGACY CONVERSION PROGRAM");
        if (!readYesOrNoInput("Do you want to continue?")) {
            return;
        }
        for (WorldInfo info : WorldUtils.loadSavedWorlds(ph)) {
            ChunkIterator iterator = new ChunkIterator(info);
            System.out.println("\n\nUpdating world: " + info.getName() + "\t (" + info.getDirectory().getAbsolutePath() + ")");
            int ticker = 0;
            int fileCount = info.getDirectory().listFiles().length;

            while (true) {
                if (iterator.iterator().hasNext()) {
                    ChunkCoords coords = iterator.iterator().next();
                    int progress = (int) (((float) ticker / fileCount) * 100);
                    System.out.println("\tChunk: " + coords + "\t(" + progress + "%)");
                    Chunk chunk = new Chunk(ph);
                    chunk.init(coords);
                    File f = World.chunkFile(info, coords);
                    LegacyChunkLoading.readChunkFromFile(chunk, f);
                    f.delete();
                    ChunkSavingLoading.writeChunkToFile(chunk, f);
                    ticker++;
                } else {
                    break;
                }
            }
        }
        System.out.println("Done! Press the enter key to exit.");
        MiscUtils.waitForKey();
        Runtime.getRuntime().halt(0);
    }
}
