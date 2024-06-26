// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.game.terrain;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.Terrain;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.items.GameItems;

public class SimpleTerrain extends Terrain
{
    public int stoneLayer;
    
    public SimpleTerrain() {
        this.stoneLayer = 250;
    }
    
    public void generateChunkInner(final Chunk chunk) {
        for (int x = 0; x < SubChunk.WIDTH; ++x) {
            for (int y = 0; y < 256; ++y) {
                for (int z = 0; z < SubChunk.WIDTH; ++z) {
                    final int wx = x + chunk.getPosition().x * SubChunk.WIDTH;
                    final int wz = z + chunk.getPosition().z * SubChunk.WIDTH;
                    if (y == 255) {
                        this.setBlock(chunk, GameItems.BlockBedrock, x, y, z);
                    }
                    else if (y == 254 && this.getRandom().nextFloat() > 0.5) {
                        this.setBlock(chunk, GameItems.BlockBedrock, x, y, z);
                    }
                    else if (y < 255 && y >= this.stoneLayer) {
                        this.setBlock(chunk, this.stoneMix(wx, y, wz), x, y, z);
                    }
                    else if (y < this.stoneLayer && y > this.stoneLayer - 5) {
                        this.setBlock(chunk, GameItems.BLOCK_DIRT, x, y, z);
                    }
                    else if (y == this.stoneLayer - 5) {
                        this.setBlock(chunk, GameItems.BLOCK_GRASS, x, y, z);
                    }
                    else if (y == this.stoneLayer - 6) {
                        if (this.getRandom().nextFloat() > 0.96) {
                            this.setBlock(chunk, GameItems.GRASS_PLANT, x, y, z);
                        }
                        else if (this.getRandom().nextFloat() > 0.995) {
                            this.setBlock(chunk, GameItems.Fern, x, y, z);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public Biome getBiomeOfVoxel(final int i, final int i1, final int i2) {
        return Biome.DEFAULT;
    }
    
    @Override
    public int getHeightmapOfVoxel(final int wx, final int wz) {
        return this.stoneLayer - 5;
    }
    
    private Block stoneMix(final int wx, final int y, final int wz) {
        Block block = GameItems.BLOCK_STONE;
        if (getValueFractal((float)(wx * 3), (float)(y * 3), (float)(wz * 4)) > 0.4) {
            block = GameItems.BLOCK_DIORITE;
        }
        else if (getValueFractal((float)(wx * 4), (float)(y * 4), (float)(wz * 5)) > 0.5) {
            block = GameItems.BlockCobblestone;
        }
        else {
            if (getValueFractal((float) (wx * 3), (float) (y * 4), (float) (wz * 3)) > 0.6) {
                block = GameItems.BLOCK_ANDESITE;
            }
        }
        return block;
    }
}
