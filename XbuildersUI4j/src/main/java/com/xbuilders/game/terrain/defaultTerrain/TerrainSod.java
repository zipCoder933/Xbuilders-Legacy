//
// Decompiled by Procyon v0.5.36
//

package com.xbuilders.game.terrain.defaultTerrain;

import java.util.Objects;

import com.xbuilders.game.items.blocks.plants.trees.JungleTreeUtils;
import com.xbuilders.game.items.blocks.plants.trees.AcaciaTreeUtils;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.game.items.GameItems;

public class TerrainSod {
    public static final float animalOdds = 0.999f;

    public static Block placeSod(final TerrainV1 terrain, final Chunk chunk, final int wy, final int cx, final int cz, final int wx, final int wz) {
        switch (terrain.getBiomeOfVoxel(wx, wy, wz)) {
            case BEACH: {
                decorateBeachBiome(terrain, chunk, cx, cz, wx, wz, wy);
                final float value = terrain.getValueFractal((float) (wx * 4), (float) (wz * 4));
                if (value >= -0.25) {
                    terrain.setBlock(chunk, GameItems.BLOCK_SAND, cx, wy, cz);
                    terrain.setBlock(chunk, GameItems.Sandstone, cx, wy + 1, cz);
                    terrain.setBlock(chunk, GameItems.Sandstone, cx, wy + 2, cz);
                    terrain.setBlock(chunk, GameItems.Sandstone, cx, wy + 3, cz);
                    return GameItems.BLOCK_SAND;
                }
                terrain.setBlock(chunk, GameItems.BLOCK_GRAVEL, cx, wy, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_GRAVEL, cx, wy + 1, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_GRAVEL, cx, wy + 2, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_GRAVEL, cx, wy + 3, cz);
                return GameItems.BLOCK_GRAVEL;
            }
            case DESERT: {
                if (false && terrain.getRandom().nextFloat() > 0.999f) {
                    if (terrain.getRandom().nextBoolean()) {
                        terrain.setEntity(chunk, GameItems.OCELOT, cx, wy - 1, cz);
                    } else {
                        terrain.setEntity(chunk, GameItems.MULE, cx, wy - 1, cz);
                    }
                }
                decorateDesertBiome(terrain, chunk, cx, wy, cz);
                if (terrain.getValueFractal((float) (wx * 4), (float) (wz * 4)) < -0.25f) {
                    terrain.setBlock(chunk, GameItems.BLOCK_CLAY, cx, wy, cz);
                    terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 1, cz);
                    terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 2, cz);
                    terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 3, cz);
                    return GameItems.BLOCK_CLAY;
                }
                if (terrain.getValueFractal((float) (wx / 2), (float) (wz / 2), 1000.0f) > 0.1) {
                    terrain.setBlock(chunk, GameItems.BLOCK_SAND, cx, wy, cz);
                    terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 1, cz);
                    terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 2, cz);
                    terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 3, cz);
                    return GameItems.BLOCK_SAND;
                }
                terrain.setBlock(chunk, GameItems.BLOCK_RED_SAND, cx, wy, cz);
                terrain.setBlock(chunk, GameItems.Sandstone, cx, wy + 1, cz);
                terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 2, cz);
                terrain.setBlock(chunk, GameItems.RedSandstone, cx, wy + 3, cz);
                return GameItems.BLOCK_RED_SAND;
            }
            case SNOWY: {
                terrain.setBlock(chunk, GameItems.BLOCK_SNOW, cx, wy, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 1, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 2, cz);
                decorateSnowyBiome(terrain, chunk, cx, cz, wx, wz, wy);
                if (false && terrain.getRandom().nextFloat() > 0.999f) {
                    switch (terrain.getRandom().nextInt(5)) {
                        case 0: {
                            terrain.setEntity(chunk, GameItems.DONKEY, cx, wy - 1, cz);
                            break;
                        }
                        case 1: {
                            terrain.setEntity(chunk, GameItems.GRAY_FOX, cx, wy - 1, cz);
                            break;
                        }
                        case 2: {
                            terrain.setEntity(chunk, GameItems.TABBY_CAT, cx, wy - 1, cz);
                            break;
                        }
                        case 3: {
                            terrain.setEntity(chunk, GameItems.CAERBANNOG_RABBIT, cx, wy - 1, cz);
                            break;
                        }
                        default: {
                            terrain.setEntity(chunk, GameItems.WHITE_FOX, cx, wy - 1, cz);
                            break;
                        }
                    }
                }
                return GameItems.BLOCK_SNOW;
            }
            case SAVANNAH: {
                decorateSavannahBiome(terrain, chunk, cx, cz, wx, wz, wy);
                terrain.setBlock(chunk, GameItems.BLOCK_DRY_GRASS, cx, wy, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 1, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 2, cz);
                if (false && terrain.getRandom().nextFloat() > 0.999f) {
                    switch (terrain.getRandom().nextInt(4)) {
                        case 0: {
                            terrain.setEntity(chunk, GameItems.OCELOT, cx, wy - 1, cz);
                            break;
                        }
                        case 1: {
                            terrain.setEntity(chunk, GameItems.RED_FOX, cx, wy - 1, cz);
                            break;
                        }
                        case 2: {
                            terrain.setEntity(chunk, GameItems.RED_TABBY_CAT, cx, wy - 1, cz);
                            break;
                        }
                        default: {
                            terrain.setEntity(chunk, GameItems.GRAY_FOX, cx, wy - 1, cz);
                            break;
                        }
                    }
                }
                return GameItems.BLOCK_DRY_GRASS;
            }
            case JUNGLE: {
                decorateJungleBiome(terrain, chunk, cx, cz, wx, wz, wy);
                terrain.setBlock(chunk, GameItems.BLOCK_JUNGLE_GRASS, cx, wy, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 1, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 2, cz);
                if (false && terrain.getRandom().nextFloat() > 0.999f) {
                    switch (terrain.getRandom().nextInt(4)) {
                        case 0: {
                            terrain.setEntity(chunk, GameItems.OCELOT, cx, wy - 1, cz);
                            break;
                        }
                        case 1: {
                            terrain.setEntity(chunk, GameItems.RED_FOX, cx, wy - 1, cz);
                            break;
                        }
                        default: {
                            terrain.setEntity(chunk, GameItems.RED_BLUE_PARROT, cx, wy - 1, cz);
                            break;
                        }
                    }
                }
                return GameItems.BLOCK_JUNGLE_GRASS;
            }
            default: {
                decorateGrassBiome(terrain, chunk, cx, cz, wx, wz, wy);
                terrain.setBlock(chunk, GameItems.BLOCK_GRASS, cx, wy, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 1, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_DIRT, cx, wy + 2, cz);
                if (false && terrain.getRandom().nextFloat() > 0.999f) {
                    switch (terrain.getRandom().nextInt(3)) {
                        case 1: {
                            terrain.setEntity(chunk, GameItems.GREEN_PARROT, cx, wy - 1, cz);
                            break;
                        }
                        case 2: {
                            terrain.utils.placeHorse(chunk, cx, wy - 1, cz);
                            break;
                        }
                        default: {
                            terrain.setEntity(chunk, GameItems.RED_FOX, cx, wy - 1, cz);
                            break;
                        }
                    }
                }
                return GameItems.BLOCK_GRASS;
            }
        }
    }

    private static void decorateDesertBiome(final TerrainV1 terrain, final Chunk chunk, final int cx, final int wy, final int cz) {
        if (terrain.getRandom().nextFloat() < 0.15) {
            if (terrain.getRandom().nextFloat() < 0.05) {
                terrain.setBlock(chunk, GameItems.BLOCK_CACTUS, cx, wy - 1, cz);
                terrain.setBlock(chunk, GameItems.BLOCK_CACTUS, cx, wy - 2, cz);
                if (terrain.getRandom().nextBoolean()) {
                    terrain.setBlock(chunk, GameItems.BLOCK_CACTUS, cx, wy - 3, cz);
                    if (terrain.getRandom().nextFloat() > 0.9f) {
                        terrain.setBlock(chunk, GameItems.BLOCK_CACTUS, cx, wy - 4, cz);
                    }
                }
            } else if (terrain.getRandom().nextFloat() > 0.92f) {
                terrain.setBlock(chunk, GameItems.DeadBush, cx, wy - 1, cz);
            }
        }
    }

    private static void decorateSnowyBiome(final TerrainV1 terrain, final Chunk chunk, final int cx, final int cz, final int wx, final int wz, final int wy) {
        if (terrain.getRandom().nextFloat() < 0.01) {
            terrain.setBlock(chunk, GameItems.GRASS_PLANT, cx, wy - 1, cz);
        } else if (terrain.getRandom().nextFloat() > 0.9f) {
            if (terrain.getRandom().nextFloat() > 0.91) {
                terrain.setBlock(chunk, GameItems.DeadBush, cx, wy - 1, cz);
            }
        }
    }

    private static void decorateSavannahBiome(final TerrainV1 terrain, final Chunk chunk, final int cx, final int cz, final int wx, final int wz, final int wy) {
        if (terrain.getRandom().nextFloat() < 0.15) {
            terrain.setBlock(chunk, GameItems.DRY_GRASS_PLANT, cx, wy - 1, cz);
        } else if (terrain.getRandom().nextFloat() > 0.88f) {
            final float rand = terrain.getRandom().nextFloat();
            if (rand > 0.9) {
                terrain.setBlock(chunk, GameItems.DeadBush, cx, wy - 1, cz);
            } else if (rand > 0.81) {
                terrain.setBlock(chunk, GameItems.TALL_DRY_GRASS_BOTTOM, cx, wy - 1, cz);
                terrain.setBlock(chunk, GameItems.TALL_DRY_GRASS_TOP, cx, wy - 2, cz);
            } else if (rand > 0.79) {
                terrain.setBlock(chunk, GameItems.WHEAT, cx, wy - 1, cz);
            } else if (terrain.getRandom().nextFloat() > 0.97) {
                AcaciaTreeUtils.plantTree(terrain, chunk, wx, wy, wz);
            }
        }
    }

    private static void decorateGrassBiome(final TerrainV1 terrain, final Chunk chunk, final int cx, final int cz, final int wx, final int wz, final int wy) {
        if (terrain.getRandom().nextFloat() < 0.1) {
            terrain.setBlock(chunk, GameItems.GRASS_PLANT, cx, wy - 1, cz);
        } else if (terrain.getRandom().nextFloat() > 0.9f) {
            final float rand = terrain.getRandom().nextFloat();
            if (rand > 0.7) {
                terrain.setBlock(chunk, GameItems.Fern, cx, wy - 1, cz);
            } else if (terrain.getRandom().nextFloat() > 0.95) {

                terrain.utils.plantBirchOrOakTree(chunk, wx, wy, wz);

            }
        }
    }

    private static void decorateJungleBiome(final TerrainV1 terrain, final Chunk chunk, final int cx, final int cz, final int wx, final int wz, final int wy) {
        if (terrain.getRandom().nextFloat() < 0.2) {
            terrain.setBlock(chunk, GameItems.JUNGLE_GRASS_PLANT, cx, wy - 1, cz);
        } else if (terrain.getRandom().nextFloat() < 0.07) {
            terrain.setBlock(chunk, GameItems.TALL_GRASS_BOTTOM, cx, wy - 1, cz);
            terrain.setBlock(chunk, GameItems.TALL_GRASS_TOP, cx, wy - 2, cz);
        } else if (terrain.getRandom().nextFloat() > 0.9f) {
            final float rand = terrain.getRandom().nextFloat();
            if (rand > 0.6) {
                terrain.setBlock(chunk, GameItems.Fern, cx, wy - 1, cz);
            } else if (terrain.getRandom().nextFloat() > 0.4) {
                JungleTreeUtils.plantTree(terrain, chunk, wx, wy, wz);
            }
        }
    }

    private static void decorateBeachBiome(final TerrainV1 terrain, final Chunk chunk, final int cx, final int cz, final int wx, final int wz, final int wy) {
        Objects.requireNonNull(terrain);
        if (wy > 172 + 4 && terrain.getRandom().nextFloat() > 0.97f) {
//            switch (terrain.getRandom().nextInt(4)) {
//                case 0 -> {
//                    terrain.utils.propagateCoral(wx, wy, wz, GameItems.FireCoralBlock, GameItems.FireCoral, GameItems.FireCoralFan);
//                }
//                case 1 -> {
//                    terrain.utils.propagateCoral(wx, wy, wz, GameItems.HornCoralBlock, GameItems.HornCoral, GameItems.HornCoralFan);
//                }
//                case 2 -> {
//                    terrain.utils.propagateCoral(wx, wy, wz, GameItems.BubbleCoralBlock, GameItems.BubbleCoral, GameItems.BubbleCoralFan);
//                }
//                default -> {
//                    terrain.utils.propagateCoral(wx, wy, wz, GameItems.TubeCoralBlock, GameItems.TubeCoral, GameItems.TubeCoralFan);
//                }
//            }
        } else if (terrain.getRandom().nextFloat() > 0.96) {
            Objects.requireNonNull(terrain);
            if (wy <= 172) {
                if (terrain.getRandom().nextFloat() < 0.24) {
                    for (int i = 1; i <= terrain.getRandom().nextFloat() * 5.0f + 2.0f; ++i) {
                        terrain.setBlock(chunk, GameItems.BlockBamboo, cx, wy - i, cz);
                    }
                } else if (terrain.getRandom().nextFloat() > 0.5) {
                    terrain.setBlock(chunk, GameItems.BlockMiniCactus, cx, wy - 1, cz);
                } else {
                    terrain.setBlock(chunk, GameItems.DeadBush, cx, wy - 1, cz);
                }
            } else {
                Objects.requireNonNull(terrain);
                if (wy > 172 + 1) {
                    terrain.setBlock(chunk, GameItems.SeaGrass, cx, wy - 1, cz);
                }
            }
        }
    }
}
