// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.terrain;

import com.xbuilders.engine.world.Terrain;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.items.GameItems;

public class DevTerrain extends Terrain {

    public DevTerrain() {
    }

    @Override
    public Biome getBiomeOfVoxel(final int i, final int i1, final int i2) {
        return Biome.DEFAULT;
    }

    private boolean blackChecker(final int row, final int col) {
        return row % 2 == col % 2;
    }

    @Override
    public void generateChunkInner(final Chunk chunk) {
        for (int x = 0; x < SubChunk.WIDTH; ++x) {
            for (int z = 0; z < SubChunk.WIDTH; ++z) {
                this.setBlock(chunk, GameItems.BlockBedrock, x, 255, z);
                if (blackChecker(chunk.getPosition().x, chunk.getPosition().z)) {
                    for (int i = 253; i < 256; i++) {
                        this.setBlock(chunk, GameItems.BLOCK_DIORITE, x, i, z);
                    }
                } else {
                    for (int i = 253; i < 256; i++) {
                        this.setBlock(chunk, GameItems.BLOCK_ANDESITE, x, i, z);
                    }

                }
            }
        }
    }

    @Override
    public int getHeightmapOfVoxel(final int x, final int z) {
        return 128;
    }
}
