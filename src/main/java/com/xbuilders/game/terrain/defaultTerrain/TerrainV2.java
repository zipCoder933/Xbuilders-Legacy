// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.terrain.defaultTerrain;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.trees.AcaciaTreeUtils;
import com.xbuilders.game.items.blocks.plants.trees.JungleTreeUtils;
import com.xbuilders.game.terrain.Biome;
import com.xbuilders.game.terrain.Terrain;

public class TerrainV2 extends Terrain {

    Block fern, deadBush;
    final int WORLD_HEIGHT_OFFSET = 138;
    final float OCEAN_THRESH = 25;
    final float MOUNTAIN_THRESH = 20;
    final int WATER_LEVEL = WORLD_HEIGHT_OFFSET + 20;
    DefaultTerrainUtils utils;
    boolean caves = false;
    float caveFrequency;

    public TerrainV2(boolean caves) {
        this.caves = caves;
        fern = GameItems.Fern;
        deadBush = GameItems.DeadBush;
        utils = new DefaultTerrainUtils(this, WATER_LEVEL);
        caveFrequency = MathUtils.clamp(3.9f * this.getFrequency(), 7.0f, 7.5f);
    }


    @Override
    public void generateChunkInner(final Chunk chunk) {
        int wx, wz, heightmap;
        float valley, heat;
        Biome biome;

        for (int x = 0; x < SubChunk.WIDTH; ++x) {
            for (int z = 0; z < SubChunk.WIDTH; ++z) {
                wx = x + chunk.getPosition().x * SubChunk.WIDTH;
                wz = z + chunk.getPosition().z * SubChunk.WIDTH;
                valley = valley(wx, wz);
                heightmap = getTerrainHeight(valley, wx, wz);
                boolean placeWater = true;
                heat = getHeat(wx, wz);

                for (int y = 0; y < 256; ++y) {

                    if (y > 252) {
                        this.setBlock(chunk, GameItems.BlockBedrock, x, y, z);
                    } else if (y == heightmap && y > 1) {//Place sod
                        biome = getBiomeOfVoxel(valley, heat, heightmap, wx, y, wz);
                        final float alpha = getValueFractal((float) wx * 3, (float) wz * 3 - 500.0f);
                        plantSod(x, y, z, wx, wz, alpha, biome, chunk);
                    } else if (y > heightmap && y < heightmap + 3) {
                        if (this.getBlock(chunk, x, y, z).isAir()) {
                            this.setBlock(chunk, GameItems.BLOCK_DIRT, x, y, z);
                        }
                    } else if (y > heightmap &&
                            (!caves || getValueFractal(wx * caveFrequency, y * 14.0f, wz * caveFrequency) <= 0.25)) {
                        this.setBlock(chunk, GameItems.BLOCK_STONE, x, y, z);
                        placeWater = false;
                    } else if (y == WATER_LEVEL && heat < -0.6f) {
                        this.setBlock(chunk, GameItems.BLOCK_ICE, x, y, z);
                    } else if (y > WATER_LEVEL && placeWater) {
                        this.setBlock(chunk, GameItems.BLOCK_WATER, x, y, z);
                    }
                }
            }
        }
    }


    @Override
    public int getHeightmapOfVoxel(int x, int z) {
        return getTerrainHeight(valley(x, z), x, z);
    }

    @Override
    public Biome getBiomeOfVoxel(int x, int y, int z) {
        return getBiomeOfVoxel(
                valley(x, z),
                getHeat(x, z),
                getHeightmapOfVoxel(x, z),
                x, y, z);
    }

    Block randomFlower() {
        switch (getRandom().nextInt(4)) {
            case 0 -> {
                return GameItems.Roses;
            }
            case 1 -> {
                return GameItems.Pansies;
            }
            case 2 -> {
                return GameItems.AzureBluet;
            }
            case 3 -> {
                return GameItems.Dandelion;
            }
            default -> {
                return GameItems.BlueOrchid;
            }
        }
    }

    final float treeOdds = 0.998f;
    final float jungleTreeOdds = 0.99f;

    private void plantSod(int x, int y, int z, int wx, int wz, float alpha, Biome biome, Chunk chunk) {
        float f = getRandom().nextFloat();
        boolean makePlants = true;
        if (f < 0.02 && y < WATER_LEVEL - 1) {
            this.setBlock(chunk, deadBush, x, y - 1, z);
            makePlants = false;
        }

        switch (biome) {
            case DEFAULT -> {
                this.setBlock(chunk, GameItems.BLOCK_GRASS, x, y, z);
                if (makePlants) {
                    if (f > treeOdds) {
                        utils.plantBirchOrOakTree(chunk, wx, y, wz);
                    } else if (f > 0.95) {
                        this.setBlock(chunk, fern, x, y - 1, z);
                    } else if (f > 0.9) {
                        this.setBlock(chunk, GameItems.GRASS_PLANT, x, y - 1, z);
                    } else if (f > 0.89) {
                        this.setBlock(chunk, randomFlower(), x, y - 1, z);
                    }
                }
            }
            case SNOWY -> {
                this.setBlock(chunk, GameItems.BLOCK_SNOW, x, y, z);
                if (makePlants) {
                    if (f > treeOdds) {
                        utils.plantBirchOrOakTree(chunk, wx, y, wz);
                    } else if (f > 0.98) {
                        this.setBlock(chunk, fern, x, y - 1, z);
                    } else if (f > 0.96) {
                        this.setBlock(chunk, GameItems.GRASS_PLANT, x, y - 1, z);
                    }
                }
            }
            case BEACH -> {
                if (alpha > 0) {
                    this.setBlock(chunk, GameItems.BLOCK_SAND, x, y, z);
                    this.setBlock(chunk, GameItems.BLOCK_SAND, x, y + 1, z);
                } else {
                    this.setBlock(chunk, GameItems.BLOCK_GRAVEL, x, y, z);
                    this.setBlock(chunk, GameItems.BLOCK_GRAVEL, x, y + 1, z);
                }
                if (y > WATER_LEVEL + 2) {
                    if (this.getRandom().nextFloat() > 0.9) {
                        switch (getRandom().nextInt(6)) {
                            case 0 -> {
                                this.setBlock(chunk, GameItems.FireCoralFan, x, y - 1, z);
                            }
                            case 1 -> {
                                this.setBlock(chunk, GameItems.HornCoralFan, x, y - 1, z);
                            }
                            case 2 -> {
                                this.setBlock(chunk, GameItems.BubbleCoralFan, x, y - 1, z);
                            }
                            case 3 -> {
                                this.setBlock(chunk, GameItems.TubeCoralFan, x, y - 1, z);
                            }
                            default -> {
                                this.setBlock(chunk, GameItems.SeaGrass, x, y - 1, z);
                            }
                        }
                    }
                } else if (makePlants && y < WATER_LEVEL - 2) {
                    float rand = this.getRandom().nextFloat();
                    if (rand > 0.99) {
                        this.setBlock(chunk, GameItems.BlockBamboo, x, y - 1, z);
                        this.setBlock(chunk, GameItems.BlockBamboo, x, y - 2, z);
                        this.setBlock(chunk, GameItems.BlockBamboo, x, y - 3, z);
                    } else if (rand > 0.98) {
                        this.setBlock(chunk, GameItems.BlockMiniCactus, x, y - 1, z);
                    }
                }
            }
            case DESERT -> {
                if (alpha > 0) {
                    this.setBlock(chunk, GameItems.BLOCK_SAND, x, y, z);
                    this.setBlock(chunk, GameItems.BLOCK_SAND, x, y + 1, z);
                } else {
                    this.setBlock(chunk, GameItems.BLOCK_RED_SAND, x, y, z);
                    this.setBlock(chunk, GameItems.BLOCK_RED_SAND, x, y + 1, z);
                }
                if (makePlants) {
                    if (this.getRandom().nextFloat() > 0.99 && y > 4 && y < 140) {
                        this.setBlock(chunk, GameItems.BLOCK_CACTUS, x, y - 1, z);
                        this.setBlock(chunk, GameItems.BLOCK_CACTUS, x, y - 2, z);
                        this.setBlock(chunk, GameItems.BLOCK_CACTUS, x, y - 3, z);
                    }
                }
            }
            case SAVANNAH -> {
                this.setBlock(chunk, GameItems.BLOCK_DRY_GRASS, x, y, z);
                if (makePlants) {
                    if (f > treeOdds) {
                        AcaciaTreeUtils.plantTree(this, chunk, wx, y, wz);
                    } else if (f < 0.15) {
                        this.setBlock(chunk, GameItems.DRY_GRASS_PLANT, x, y - 1, z);
                    } else if (f > 0.99) {
                        this.setBlock(chunk, GameItems.TALL_DRY_GRASS_BOTTOM, x, y - 1, z);
                        this.setBlock(chunk, GameItems.TALL_DRY_GRASS_TOP, x, y - 2, z);
                    }
                }
            }
            case JUNGLE -> {
                this.setBlock(chunk, GameItems.BLOCK_JUNGLE_GRASS, x, y, z);
                if (makePlants) {
                    if (f > jungleTreeOdds) {
                        JungleTreeUtils.plantTree(this, chunk, wx, y, wz);
                    } else if (f < 0.15) {
                        this.setBlock(chunk, GameItems.JUNGLE_GRASS_PLANT, x, y - 1, z);
                    } else if (f > 0.98) {
                        this.setBlock(chunk, GameItems.TALL_GRASS_BOTTOM, x, y - 1, z);
                        this.setBlock(chunk, GameItems.TALL_GRASS_TOP, x, y - 2, z);
                    }
                }
            }
        }
    }


    public float valley(final int wx, final int wz) {
        return getValueFractal((float) wz - 10000, (float) wx);
    }


    public int getTerrainHeight(float valley, final int wx, final int wz) {
        int val = (int) (getValueFractal((float) wx, (float) wz) * 50f);

        if (val > OCEAN_THRESH) {
            val = (int) (((val - OCEAN_THRESH) * 1.5f) + OCEAN_THRESH);
        }
        //else if (val < -MOUNTAIN_THRESH) {
//            val = (int) (((val + MOUNTAIN_THRESH) * 2f) - MOUNTAIN_THRESH);
//        }
        if (val < 0) {//If the height value is less than 0, normalize it
            val = (int) MathUtils.map(valley, -1, 1, val, val / 10f);//Normalize for valleys
        }

        return WORLD_HEIGHT_OFFSET + val;
    }

    public float getHeat(int x, int z) {
        return (float) (getValueFractal((float) x / 5, (float) z / 5) + 1) / 2.0f;
    }

    public Biome getBiomeOfVoxel(float valley, float heat, int heightmap, final int x, final int y, final int z) {
        if (y > WATER_LEVEL - 10) {
            return Biome.BEACH;
        }

        if (heat > 0.55f && heightmap > WORLD_HEIGHT_OFFSET - 8 - (heat * 5)) {//0.6 - 1
            //We lower down the minimum temperature of desert to compensate for it only being at the bottom of the terrain
            return Biome.DESERT;
        } else if (heat > 0.2f) {//0.2 - 0.6
            return Biome.SAVANNAH;
        } else if (heat > -0.2f) {//-0.2 - 0.2
            return Biome.DEFAULT;
        } else if (heat > -0.6f) {//-0.6 - -0.2
            return Biome.JUNGLE;
        } else {//-1 - -0.6
            return Biome.SNOWY;
        }
    }
}
