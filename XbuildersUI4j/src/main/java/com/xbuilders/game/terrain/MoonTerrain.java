// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.terrain;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.items.GameItems;

public class MoonTerrain extends Terrain {

    int height;

    public MoonTerrain(boolean deep) {
        setEnableClouds(false);
        setEnableGradientSky(false);
        height = deep ? 205 : 240;
    }

    @Override
    public void generateChunkInner(final Chunk chunk) {
        for (int x = 0; x < SubChunk.WIDTH; ++x) {
            for (int z = 0; z < SubChunk.WIDTH; ++z) {
                final int wx = x + chunk.getPosition().x * SubChunk.WIDTH;
                final int wz = z + chunk.getPosition().z * SubChunk.WIDTH;
                final int heightmap = this.getHeightmapOfVoxel(wx, wz);
                final Block block = GameItems.BLOCK_ANDESITE;

                for (int y = 0; y < 256; ++y) {

                    if (y > 252) {
                        this.setBlock(chunk, GameItems.BlockBedrock, x, y, z);
                    } else if (y == heightmap) {
                        if (getValueFractal(wx * 6.0f, y * 14.0f, wz * 6.0f) <= 0.5) {
                            this.setBlock(chunk, block, x, y, z);
                            this.setBlock(chunk, block, x, y + 1, z);
                            this.setBlock(chunk, block, x, y + 2, z);
                        }
                    } else if (getValueFractal(wx * 6.0f, y * 14.0f, wz * 6.0f) <= 0.3) {
                        if (y > heightmap) {
                            this.setBlock(chunk, block, x, y, z);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void worldBackground() {
        VoxelGame.getShaderHandler().setNaturalBackgroundEnabled(false);
        VoxelGame.getShaderHandler().setSkyColor(0, 20, 50);
        VoxelGame.getShaderHandler().setFogDistance(1);
    }

    @Override
    public int getHeightmapOfVoxel(final int wx, final int wz) {
        final int heightmap = (int) (height + getValueFractal((float) (wx * 2), (float) (wz * 2)) * 5.0f);
        return heightmap;
    }

    @Override
    public Biome getBiomeOfVoxel(final int x, final int y, final int z) {
        return Biome.DEFAULT;
    }
}
