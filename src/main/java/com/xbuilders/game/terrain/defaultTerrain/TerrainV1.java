// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.terrain.defaultTerrain;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.terrain.Biome;
import com.xbuilders.engine.world.Terrain;
import com.xbuilders.game.items.GameItems;

//TODO: Optimize the default terrain to make it run faster
public class TerrainV1 extends Terrain {

    final int HEIGHTMAP_ADD = 120;
    DefaultTerrainUtils utils;
    final int WATER_LEVEL = 172;
    float WORLD_HEIGHT;
    public boolean caves = false;

    @Override
    public Biome getBiomeOfVoxel(final int x, final int y, final int z) {
        final float fractal = this.getValueFractal((float) x, (float) z) * 0.01f;
        final double valley = this.getValleyValue(x, z);
        final boolean savannah = this.getValueFractal((float) (x / 7), 200.0f, (float) (z / 7)) > 0.29;
        if (valley > 0.36 && savannah) {
            return Biome.SAVANNAH;
        }
        if (valley > 0.7) {
            return Biome.DESERT;
        }
        if (y / (float) Chunk.HEIGHT > 0.65f - fractal) {
            if (valley > 0.36 && !savannah) {
                return Biome.DESERT;
            }
            return Biome.BEACH;
        } else {
            if (this.getValueFractal((float) (x / 6), (float) (z / 6), -500.0f) > 0.25) {
                return Biome.SNOWY;
            }
            if (this.getValueFractal((float) (x / 7), (float) (z / 7), 500.0f) > 0.25) {
                return Biome.JUNGLE;
            }
            return Biome.DEFAULT;
        }
    }

    public TerrainV1(boolean caves) {
        super();
        this.caves = caves;
        this.WORLD_HEIGHT = 0.6f;
        this.utils = new DefaultTerrainUtils(this, WATER_LEVEL);
    }

    private double heightmapFunction(final int wy, final double val) {
        return Math.pow(wy / (float) Chunk.HEIGHT * 4.0f, 1.1) - val * this.WORLD_HEIGHT;
    }

    public void generateChunkInner(final Chunk chunk) {
        final float caveFrequency = MathUtils.clamp(3.9f * this.getFrequency(), 7.0f, 7.5f);


        for (int cx = 0; cx < SubChunk.WIDTH; ++cx) {
            for (int cz = 0; cz < SubChunk.WIDTH; ++cz) {
                final int wx = cx + chunk.getPosition().x * SubChunk.WIDTH;
                final int wz = cz + chunk.getPosition().z * SubChunk.WIDTH;
                double valley = this.getValleyValue(wx, wz);
                double rand = 0;
                boolean placeSod = true;

                for (int wy2 = 0; wy2 < 136; ++wy2) {
                    if (placeSod) rand = this.getHeightmapValue(valley, wx, wy2, wz);
                    final int worldY = wy2 + 120;

                    if (worldY >= 254) {
                        this.setBlock(chunk, GameItems.BlockBedrock, cx, worldY, cz);
                    } else if (worldY > 251) {
                        if (this.getRandom().nextBoolean()) {
                            this.setBlock(chunk, GameItems.BlockBedrock, cx, worldY, cz);
                        }
                    } else if (rand > 0.5) {
                        if (!this.caves
                                || worldY <= 180.0f || worldY >= 250.0f
                                || noise.GetValueFractal(wx * caveFrequency, wy2 * 14.0f, wz * caveFrequency) <= 0.25) {
                            if (placeSod) {
                                TerrainSod.placeSod(this, chunk, worldY, cx, cz, wx, wz);
                                placeSod = false;
                            } else if (this.getBlock(chunk, cx, worldY, cz).isAir()) {
                                if (rand > 0.4) {
                                    this.setBlock(chunk, GameItems.BLOCK_DIORITE, cx, worldY, cz);
                                    if (this.getRandom().nextFloat() > 0.97 && this.getBlock(chunk, cx, worldY - 1, cz).isAir()) {
                                        this.setBlock(chunk, GameItems.AmethystCrystal, cx, worldY - 1, cz);
                                    }
                                } else if (rand > 0.3) {
                                    this.setBlock(chunk, GameItems.Granite, cx, worldY, cz);
                                } else if (rand > 0.2) {
                                    this.setBlock(chunk, GameItems.BLOCK_ANDESITE, cx, worldY, cz);
                                } else {

                                    this.setBlock(chunk, GameItems.BLOCK_STONE, cx, worldY, cz);
                                }
                            }
                        }
                    }
                    if (this.getBlock(chunk, cx, worldY, cz).isAir()) {
                        if (worldY == WATER_LEVEL && valley < 0.3) {
                            final Block aboveBlock = this.getBlock(chunk, cx, worldY - 1, cz);
                            if (aboveBlock.isAir()) {
                                this.setBlock(chunk, GameItems.BLOCK_WATER, cx, worldY, cz);
//                                if (this.placeAnimals) {
//                                    this.placeFish(chunk, cx, worldY, cz, randomFish1, 0.996f);
//                                }
                            }
                        } else if (worldY > WATER_LEVEL) {
                            final Block aboveBlock = this.getBlock(chunk, cx, worldY - 1, cz);
                            if (aboveBlock == GameItems.BLOCK_WATER) {
                                this.setBlock(chunk, GameItems.BLOCK_WATER, cx, worldY, cz);
//                                if (this.placeAnimals) {
//                                    this.placeFish(chunk, cx, worldY, cz, randomFish1, 0.996f);
//                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private double getValleyValue(final int wx, final int wz) {
        final double valleyLayer = noise.GetValueFractal((float) (wx / 2), (float) (wz / 2));
        return valleyLayer;
    }

    private double getHeightmapValue(double valleyLayer, final int wx, final int wy, final int wz) {
        final double valleyHeight = 0.5;
        double regularHeight = noise.GetValueFractal(wx * this.getFrequency(), wy * this.getFrequency(), wz * this.getFrequency());
        if (regularHeight > 0.59) {
            regularHeight += (regularHeight - 0.59) * 1.1;
        }
        double rand;
        if (valleyLayer > 0.25) {
            rand = (float) MathUtils.mapAndClamp((float) valleyLayer, 0.25f, 0.8f, (float) regularHeight, (float) (valleyHeight - regularHeight * 0.1f));
        } else {
            rand = regularHeight;
        }
        return this.heightmapFunction(wy, rand);
    }

    @Override
    public int getHeightmapOfVoxel(final int wx, final int wz) {
        double valleyLayer = this.getValleyValue(wx, wz);
        for (int wy = Chunk.HEIGHT - 120; wy > 0; --wy) {
            final double rand = this.getHeightmapValue(valleyLayer, wx, wy, wz);
            if (rand < 0.5) {
                return wy + 120;
            }
        }
        return -1;
    }
}
