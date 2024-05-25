/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.legacyConversion;

/**
 *
 * @author zipCoder933
 */
import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.info.WorldInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ChunkIterator implements Iterable<ChunkCoords> {

    private ArrayList<File> arrayList;

    public ChunkIterator(WorldInfo info) {
        arrayList = new ArrayList<>();
        for (File chunkFile : info.getDirectory().listFiles()) {
            if (chunkFile.getName().endsWith(".chunk")) {
                arrayList.add(chunkFile);
            }
        }
        currentIndex = -1;
    }

    private int currentIndex;

    @Override
    public Iterator<ChunkCoords> iterator() {

        Iterator<ChunkCoords> it = new Iterator<ChunkCoords>() {
            @Override
            public boolean hasNext() {
                return currentIndex + 1 < arrayList.size() && arrayList.get(currentIndex + 1) != null;
            }

            @Override
            public ChunkCoords next() {
                currentIndex++;
                File chunkFile = arrayList.get(currentIndex);
                String[] name = chunkFile.getName().replace(".chunk", "").split("_");
                int x = Integer.parseInt(name[0].trim());
                int z = Integer.parseInt(name[1].trim());
                return new ChunkCoords(x, z);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
