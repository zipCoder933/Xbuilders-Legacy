// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.game.terrain.cityTerrain;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blocks.plants.trees.BirchTreeUtils;
import com.xbuilders.game.items.blocks.plants.trees.OakTreeUtils;
import com.xbuilders.game.items.blocks.plants.trees.SpruceTreeUtils;
import com.xbuilders.game.terrain.Biome;
import com.xbuilders.game.terrain.Terrain;

public class CityTerrain extends Terrain {

    public int terrainHeight;

    public CityTerrain() {
        this.terrainHeight = DEFAULT_TERRAIN_LEVEL;
    }

    @Override
    public Biome getBiomeOfVoxel(int p0, int p1, int p2) {
        return Biome.DEFAULT;
    }

    private final int BUILDING_Y_START = 86 + 8 + 8 + 8; //with each "8", we reduce the buildings by another floor
    //by default the tallest building is 7 stories high

    private final int SKYSCRAPER_FREQ = 8;
    private final float BUILDING_THRESHOLD = 0.2f;
    private final int doorStart = 5;
    private final int doorEnd = 15 - 5;
    private final int lightStart = 4;
    private final int lightEnd = 15 - 4;
    private final int groundHeightVariation = 15;

    private final int DEFAULT_TERRAIN_LEVEL = 170;
    private final int WATER_LEVEL = DEFAULT_TERRAIN_LEVEL + 2;
    private final Block roofEdgeMaterial = GameItems.BLOCK_CEMENT;

    @Override
    public void generateChunkInner(final Chunk chunk) {
        int waterLevel = 0;
        float val = getValueFractal(
                chunk.getPosition().x * SKYSCRAPER_FREQ,
                chunk.getPosition().z * SKYSCRAPER_FREQ);
        float xPlus = getValueFractal(
                (chunk.getPosition().x + 1) * SKYSCRAPER_FREQ,
                chunk.getPosition().z * SKYSCRAPER_FREQ);
        float zMinus = getValueFractal(
                chunk.getPosition().x * SKYSCRAPER_FREQ,
                (chunk.getPosition().z - 1) * SKYSCRAPER_FREQ);
        float zPlus = getValueFractal(
                chunk.getPosition().x * SKYSCRAPER_FREQ,
                (chunk.getPosition().z + 1) * SKYSCRAPER_FREQ);
        float xMinus = getValueFractal(
                (chunk.getPosition().x - 1) * SKYSCRAPER_FREQ,
                chunk.getPosition().z * SKYSCRAPER_FREQ);

        int buildingHeight = (val > 0.3 ? BUILDING_Y_START + 8 : BUILDING_Y_START);
        terrainHeight = DEFAULT_TERRAIN_LEVEL - groundHeightVariation;

        Block wallMaterial;
        switch (getRandom().nextInt(10)) {
            case 0 ->
                wallMaterial = GameItems.LightBlueConcrete;
            case 1 ->
                wallMaterial = GameItems.BlueConcrete;
            case 2 ->
                wallMaterial = GameItems.LightGrayConcrete;
            case 3 ->
                wallMaterial = GameItems.GrayConcrete;
            default ->
                wallMaterial = GameItems.BLOCK_STONE;
        }

        Block floorMaterial = (getRandom().nextInt(3) == 0
                ? GameItems.GrayMarbleTile
                : GameItems.MarbleTile);
        boolean flatGround
                = xPlus > BUILDING_THRESHOLD
                || xMinus > BUILDING_THRESHOLD
                || zPlus > BUILDING_THRESHOLD
                || zMinus > BUILDING_THRESHOLD
                || //----
                getValueFractal((chunk.getPosition().x + 1) * SKYSCRAPER_FREQ,
                        (chunk.getPosition().z + 1) * SKYSCRAPER_FREQ) > BUILDING_THRESHOLD
                || getValueFractal(
                        (chunk.getPosition().x - 1) * SKYSCRAPER_FREQ,
                        (chunk.getPosition().z - 1) * SKYSCRAPER_FREQ) > BUILDING_THRESHOLD
                || getValueFractal(
                        (chunk.getPosition().x - 1) * SKYSCRAPER_FREQ,
                        (chunk.getPosition().z + 1) * SKYSCRAPER_FREQ) > BUILDING_THRESHOLD
                || getValueFractal(
                        (chunk.getPosition().x + 1) * SKYSCRAPER_FREQ,
                        (chunk.getPosition().z - 1) * SKYSCRAPER_FREQ) > BUILDING_THRESHOLD;
        boolean buildSkyscraper = val > BUILDING_THRESHOLD;

        for (int x = 0; x < SubChunk.WIDTH; ++x) {
            for (int z = 0; z < SubChunk.WIDTH; ++z) {
                final int wx = x + chunk.getPosition().x * SubChunk.WIDTH;
                final int wz = z + chunk.getPosition().z * SubChunk.WIDTH;
                boolean firstTime = true;
                boolean onEdge = (x == 0 || x == 15 || z == 0 || z == 15);
//                boolean onCorner = (x == 0 && z == 0)
//                        || (x == 0 && z == 15)
//                        || (x == 15 && z == 0)
//                        || (x == 15 && z == 15);

                if (!buildSkyscraper && !flatGround) {
                    terrainHeight = (int) (DEFAULT_TERRAIN_LEVEL + (getValueFractal(wx / 2, wz / 2) * groundHeightVariation));
                    waterLevel = 0;

                    float w = getValueFractal(wx * 0.25f, wz * 0.25f);
                    if (w > 0) {
                        waterLevel = (int) (w * 50);//original depth was 80
                        terrainHeight += waterLevel;
                    }
                }
                for (int y = BUILDING_Y_START; y < 256; ++y) {
                    //<editor-fold defaultstate="collapsed" desc="stone and grass">
                    if (y == 255) {
                        this.setBlock(chunk, GameItems.BlockBedrock, x, y, z);
                    } else if (y == 254 && this.getRandom().nextFloat() > 0.5) {
                        this.setBlock(chunk, GameItems.BlockBedrock, x, y, z);
                    } else if (y < 255 && y >= this.terrainHeight) {
                        this.setBlock(chunk, this.stoneMix(wx, y, wz), x, y, z);
                    } else if (y < this.terrainHeight && y > this.terrainHeight - 5) {
                        if (flatGround) {
                            this.setBlock(chunk, roofEdgeMaterial, x, y, z);
                        } else {
                            this.setBlock(chunk, GameItems.BLOCK_DIRT, x, y, z);
                        }
                    }
                    //</editor-fold>

                    if (y < this.terrainHeight - 4) {
                        Block block = null;
                        //<editor-fold defaultstate="collapsed" desc="skyscraper">
                        if (buildSkyscraper && y > buildingHeight) {//Build skyscraper
                            if (y == this.terrainHeight - 5) {
                                block = GameItems.MINECART_ROAD;
                            } else if (y % 8 == 0) {
                                if ((x == lightEnd && z == lightEnd)
                                        || (x == lightStart && z == lightEnd)
                                        || (x == lightStart && z == lightStart)
                                        || (x == lightEnd && z == lightStart)) {
                                    block = GameItems.EdisonLight;
                                } else {
                                    block = y > this.terrainHeight - 13 ? roofEdgeMaterial : wallMaterial;
                                }
                                if (onEdge) {
                                    this.setBlock(chunk, roofEdgeMaterial, x, y - 1, z);
                                } else {
                                    this.setBlock(chunk, (firstTime) ? roofEdgeMaterial : floorMaterial, x, y - 1, z);
                                }
                                firstTime = false;
                            }
                            //<editor-fold defaultstate="collapsed" desc="walls">
                            if (onEdge) {
                                //<editor-fold defaultstate="collapsed" desc="windows">
                                if (x > doorStart && x < doorEnd) {
                                    if (y < this.terrainHeight - 10) {
                                        if (firstTime) {
                                            block = wallMaterial;
                                        } else if (!((z == 15 && zPlus > BUILDING_THRESHOLD) || (z == 0 && zMinus > BUILDING_THRESHOLD))) {
                                            this.setBlock(chunk,GameItems.GlassPane,
                                                    new BlockData(new byte[]{0, 0}), x, y, z);
                                        }
                                    }
                                } else if (z > doorStart && z < doorEnd) {
                                    if (y < this.terrainHeight - 10) {
                                        if (firstTime) {
                                            block = wallMaterial;
                                        } else if (!((x == 15 && xPlus > BUILDING_THRESHOLD) || (x == 0 && xMinus > BUILDING_THRESHOLD))) {
                                            this.setBlock(chunk,
                                                    GameItems.GlassPane,
                                                    new BlockData(new byte[]{1, 0}), x, y, z);
                                        }
                                    }
                                } //</editor-fold>
                                else {
                                    block = y > this.terrainHeight - 13 ? GameItems.BLOCK_CEMENT : wallMaterial;
                                }
                            }
//</editor-fold>
                        } //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ground layer">
                        else if (y == this.terrainHeight - 5) {
                            if (flatGround) {
                                block = GameItems.BLOCK_CEMENT;
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="parks and rec">
                                if (waterLevel > 0) {
                                    if (getValueFractal(wz * 2, wx * 2) > 0) {
                                        block = GameItems.BLOCK_GRAVEL;
                                    } else {
                                        block = GameItems.BLOCK_SAND;
                                    }
                                } else {
                                    block = GameItems.BLOCK_GRASS;
                                    if (this.getRandom().nextFloat() > 0.96) {
                                        this.setBlock(chunk, GameItems.GRASS_PLANT, x, y - 1, z);
                                    } else if (this.getRandom().nextFloat() > 0.995) {
                                        this.setBlock(chunk, GameItems.Fern, x, y - 1, z);
                                    } else if (this.getRandom().nextFloat() < 0.001) {
//                                    System.out.println("TREE");
                                        switch (getRandom().nextInt(3)) {
                                            case 0 ->
                                                BirchTreeUtils.plantTree(this, chunk, wx, y, wz);
                                            case 1 ->
                                                OakTreeUtils.plantTree(this, chunk, wx, y, wz);
                                            default ->
                                                SpruceTreeUtils.plantTree(this, chunk, wx, y, wz);
                                        }
                                    }
                                }
//</editor-fold>
                            }
                        } else if (y > WATER_LEVEL) {
                            block = GameItems.BLOCK_WATER;
                        }
//</editor-fold>
                        if (block != null) {
                            this.setBlock(chunk, block, x, y, z);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getHeightmapOfVoxel(final int wx, final int wz) {
        return this.terrainHeight - 5;
    }

    private Block stoneMix(final int wx, final int y, final int wz) {
        Block block = GameItems.BLOCK_STONE;
        if (getValueFractal((float) (wx * 3), (float) (y * 3), (float) (wz * 4)) > 0.4) {
            block = GameItems.BLOCK_DIORITE;
        } else if (getValueFractal((float) (wx * 4), (float) (y * 4), (float) (wz * 5)) > 0.5) {
            block = GameItems.BlockCobblestone;
        } else {
            if (getValueFractal((float) (wx * 3), (float) (y * 4), (float) (wz * 3)) > 0.6) {
                block = GameItems.BLOCK_ANDESITE;
            }
        }
        return block;
    }
}
