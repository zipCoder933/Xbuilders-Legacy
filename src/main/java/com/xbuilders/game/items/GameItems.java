/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.tool.Tool;
import com.xbuilders.engine.utils.ArrayUtils;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.json.JsonManager;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.game.items.blocks.electronics.PressurePlate;
import com.xbuilders.game.items.blocks.electronics.light.*;
import com.xbuilders.game.items.blocks.liquid.BlockLava;
import com.xbuilders.game.items.blocks.liquid.BlockWater;
import com.xbuilders.game.items.blocks.plants.BlockFarmland;
import com.xbuilders.game.items.blocks.plants.Plant;
import com.xbuilders.game.items.blocks.plants.growablePlants.*;
import com.xbuilders.game.items.blocks.plants.sunflower.BlockSunflowerSeeds;
import com.xbuilders.game.items.blocks.plants.sunflower.SunflowerHead;
import com.xbuilders.game.items.blocks.plants.sunflower.SunflowerStalk;
import com.xbuilders.game.items.blocks.plants.tallGrass.TallDryGrassBottom;
import com.xbuilders.game.items.blocks.plants.tallGrass.TallDryGrassTop;
import com.xbuilders.game.items.blocks.plants.tallGrass.TallGrassBottom;
import com.xbuilders.game.items.blocks.plants.tallGrass.TallGrassTop;
import com.xbuilders.game.items.blocks.plants.trees.*;
import com.xbuilders.game.items.blocks.tnt.BlockTNT;
import com.xbuilders.game.items.blocks.tnt.BlockTNTActive;
import com.xbuilders.game.items.blocks.tnt.BlockTNTLarge;
import com.xbuilders.game.items.blocks.track.*;
import com.xbuilders.game.items.blocks.vehicle.*;
import com.xbuilders.game.items.blocks.wallItems.BambooLadder;
import com.xbuilders.game.items.blocks.wallItems.DarkOakLadder;
import com.xbuilders.game.items.blocks.wallItems.IronLadder;
import com.xbuilders.game.items.blocks.wallItems.OakLadder;
import com.xbuilders.game.items.entities.*;
import com.xbuilders.game.items.entities.animals.*;
import com.xbuilders.game.items.entities.animals.fish.FishALink;
import com.xbuilders.game.items.entities.animals.fish.FishBLink;
import com.xbuilders.game.items.entities.trapdoors.*;
import com.xbuilders.game.items.entities.vehicle.boat.*;
import com.xbuilders.game.items.entities.vehicle.minecart.*;
import com.xbuilders.game.items.other.boundaryBlocks.StartBoundary;
import com.xbuilders.game.items.tools.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.xbuilders.engine.items.BlockList.DEFAULT_BLOCK_TYPE_ID;

/**
 * @author zipCoder933
 */
public class GameItems {

    // <editor-fold defaultstate="collapsed" desc="blocks">
//    public static final PasteRotateBlock PASTE_ROTATE_BLOCK = new PasteRotateBlock();
//    public static final PasteVertexBlock PASTE_VERTEX_BLOCK = new PasteVertexBlock();
    public static final MergeTrack MERGE_TRACK = new MergeTrack();
    public static final BlockTNTLarge BLOCK_TNT_LARGE = new BlockTNTLarge();
    public static final StartBoundary START_BOUNDARY = new StartBoundary();
    //    public static final BlockPaste BLOCK_PASTE = new BlockPaste();
//    public static final BlockAdditivePaste ADDITIVE_PASTE = new BlockAdditivePaste();
    public static final BlockLamp BLOCK_LAMP = new BlockLamp();
    public static final BlockBlueLamp BLOCK_BLUE_LAMP = new BlockBlueLamp();
    public static final Track TRACK = new Track();
    public static final StopTrack BLOCK_TRACK_STOP = new StopTrack();
    public static final SwitchJunction SWITCH_JUNCTION = new SwitchJunction();
    public static final CurvedTrack CURVED_TRACK = new CurvedTrack();
    public static final RaisedTrack RAISED_TRACK = new RaisedTrack();
    public static final MinecartRoadSlab MINECART_ROAD_SLAB = new MinecartRoadSlab();
    public static final MinecartRoad MINECART_ROAD = new MinecartRoad();
    public static final MinecartCrosswalk MINECART_CROSSWALK = new MinecartCrosswalk();
    public static final MinecartRoadMarkings MINECART_ROAD_MARKINGS = new MinecartRoadMarkings();

    public static final BlockFarmland BLOCK_FARMLAND = new BlockFarmland();
    public static final Block BLOCK_DIRT = new Block(6, "Dirt", new BlockTexture(2, 0));
    public static final Block BLOCK_GRASS = new Block(9, "Grass", new BlockTexture(0, 0, 2, 0, 3, 0),
            DEFAULT_BLOCK_TYPE_ID);
    public static final Block BLOCK_JUNGLE_GRASS = new Block(153, "Jungle Grass", new BlockTexture(11, 0, 2, 0, 12, 0),
            DEFAULT_BLOCK_TYPE_ID);
    public static final Block JUNGLE_GRASS_PLANT = new Plant(200, "Jungle Grass Plant",
            new BlockTexture(13, 0, 13, 0, 13, 0), BlockRenderType.SPRITE);
    public static final Block DRY_GRASS_PLANT = new Plant(224, "Dry Grass Plant", new BlockTexture(2, 3, 2, 3, 2, 3),
            BlockRenderType.SPRITE);
    public static final Block BLOCK_DRY_GRASS = new Block(212, "Dry Grass", new BlockTexture(6, 2, 2, 0, 8, 1),
            DEFAULT_BLOCK_TYPE_ID);
    public static final Block BLOCK_SNOW = new Block(265, "Snow Block", new BlockTexture(2, 4, 2, 4, 2, 4),
            DEFAULT_BLOCK_TYPE_ID);
    public static final Block SNOW_BLOCK = new Block(23, "Snow", new BlockTexture(2, 4, 2, 0, 4, 4),
            DEFAULT_BLOCK_TYPE_ID);
    public static final Block GRASS_PLANT = new Plant(14, "Plant Grass", new BlockTexture(7, 2, 7, 2, 7, 2),
            BlockRenderType.SPRITE);
    public static final BlockWater BLOCK_WATER = new BlockWater();
    public static final BlockTNT BLOCK_TNT = new BlockTNT();
    public static final BlockTNTActive TNT_ACTIVE = new BlockTNTActive();
    public static final BlockBeetSeeds BEET_SEEDS = new BlockBeetSeeds();
    public static final BlockWheatSeeds BlockWheatSeeds = new BlockWheatSeeds();
    public static final BlockCarrotSeeds BlockCarrotSeeds = new BlockCarrotSeeds();

    public static final BlockBirchSapling BlockBirchSapling = new BlockBirchSapling();
    public static final BlockOakSapling BlockOakSapling = new BlockOakSapling();
    public static final BlockLava BlockLava = new BlockLava();
    public static final BlockTorch BlockTorch = new BlockTorch();
    public static final Block BlockBedrock = new Block(1, "Bedrock", new BlockTexture(1, 1));
    public static final CrossTrack BLOCK_CROSSTRACK = new CrossTrack();
    public static final Block BLOCK_GRAVEL = new Block(10, "Gravel", new BlockTexture(3, 1));
    public static final Block BlockBamboo = new Plant(15, "Bamboo", new BlockTexture(9, 4), false, false,
            BlockRenderType.SPRITE);

    public static final Block BLOCK_ANDESITE = new Block(18, "Andesite", new BlockTexture(1, 0));
    public static final Block BLOCK_GLASS = new Block(8, "Glass", new BlockTexture(1, 3), true, false);
    public static final Block BlockBrick = new Block(4, "Brick", new BlockTexture(7, 0));
    public static final Block BlockCobblestone = new Block(101, "Cobblestone", new BlockTexture(0, 1));
    public static final Block BLOCK_CEMENT = new Block(159, "Cement", new BlockTexture(8, 4));
    public static final Block BlockWatermelon = new Block(105, "Watermelon") {
        @Override
        public void init() {
            (this.texture = new BlockTexture()).setTOP(9, 8);
            this.texture.setBOTTOM(9, 8);
            this.texture.setSIDES(8, 8);
        }
    };

    public static final Block BlockPalisadeStone = new Block(110, "Palisade Stone", new BlockTexture(6, 0, 6, 0, 5, 0),
            true, true, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlockPalisadeStone2 = new Block(114, "Palisade Stone 2",
            new BlockTexture(5, 13, 5, 13, 5, 13), true, true, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BLOCK_DIORITE = new Block(53, "Diorite", new BlockTexture(0, 2));
    public static final Block BLOCK_CLAY = new Block(65, "Clay", new BlockTexture(7, 1));

    public static final Block BLOCK_SAND = new Block(16, "Sand", new BlockTexture(2, 1, 2, 1, 2, 1), true, true,
            DEFAULT_BLOCK_TYPE_ID);
    public static final Block BLOCK_RED_SAND = new Block(104, "Red Sand", new BlockTexture(2, 2, 2, 2, 2, 2), true,
            true, DEFAULT_BLOCK_TYPE_ID);
    public static final IronLadder IRON_LADDER = new IronLadder();
    public static final BambooLadder BAMBOO_LADDER = new BambooLadder();
    public static final DarkOakLadder DARK_OAK_LADDER = new DarkOakLadder();
    public static final OakLadder OAK_LADDER = new OakLadder();

    public static final BlockBlueTorch BLOCK_BLUE_TORCH = new BlockBlueTorch();
    public static final Block BLOCK_CONTROL_PANEL = new Block(50, "Control Panel") {
        @Override
        public void init() {
            this.texture = new BlockTexture();
            setRenderType(BlockRenderType.SLAB);
            this.texture.setTOP(29, 9);
            this.texture.setBOTTOM(30, 9);
            this.texture.setSIDES(30, 9);
        }

        @Override
        public boolean isOpaque() {
            return false;
        }

        @Override
        public int getAnimationLength() {
            return 4;
        }
    };

    public static final BlockPotatoSeeds BLOCK_POTATO_SEEDS = new BlockPotatoSeeds();

    // Trees
    // -------------------------------------------------------------------------------------
    public static final Block BLOCK_SPRUCE_LEAVES = new Block(733, "Spruce Leaves", new BlockTexture(7, 8), false,
            false);
    public static final Block BLOCK_JUNGLE_LEAVES = new Block(47, "Jungle Leaves", new BlockTexture(4, 0), false,
            false);
    public static final Block BIRCH_LEAVES = new Block(3, "Birch Leaves", new BlockTexture(4, 8), false, false);
    public static final Block OAK_LEAVES = new Block(12, "Oak Leaves", new BlockTexture(4, 12), false, false);
    public static final Block ACACIA_LEAVES = new Block(225, "Acacia Leaves", new BlockTexture(16, 1), false, false);

    public static final Block BLOCK_JUNGLE_LOG = new Block(48, "Jungle Log", new BlockTexture(5, 3, 4, 3),
            BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block BIRCH_LOG = new Block(2, "Birch Log", new BlockTexture(4, 7, 5, 7),
            BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block OAK_LOG = new Block(11, "Oak Log", new BlockTexture(5, 1, 4, 1),
            BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block BLOCK_SPRUCE_LOG = new Block(732, "Spruce Log", new BlockTexture(6, 8, 6, 9),
            BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block ACACIA_LOG = new Block(226, "Acacia Log", new BlockTexture(7, 7, 8, 7),
            BlockRenderType.ORIENTABLE_BLOCK);

    public static final BlockJungleSapling BLOCK_JUNGLE_SAPLING = new BlockJungleSapling();
    public static final BlockSpruceSapling BLOCK_SPRUCE_SAPLING = new BlockSpruceSapling();
    public static final BlockAcaciaSapling ACACIA_SAPLING = new BlockAcaciaSapling();
    // ------------------------------------------------------------------------------------------------------

    public static final BlockCampfire CAMPFIRE = new BlockCampfire();

    public static final Block StageA1 = new Block(166, "A1 hidden", new BlockTexture(11, 6), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageA2 = new Block(167, "A2 hidden", new BlockTexture(12, 6), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageB1 = new Block(168, "B1 hidden", new BlockTexture(9, 5), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageB2 = new Block(169, "B2 hidden", new BlockTexture(10, 5), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageB3 = new Block(170, "B3 hidden", new BlockTexture(11, 5), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageB4 = new Block(171, "B4 hidden", new BlockTexture(12, 5), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageB5 = new Block(172, "B5 hidden", new BlockTexture(13, 5), false, false,
            BlockRenderType.SPRITE);
    public static final Block StageB6 = new Block(173, "B6 hidden", new BlockTexture(14, 5), false, false,
            BlockRenderType.SPRITE);

    public static final BlockWheat WHEAT = new BlockWheat();
    public static final BlockCarrots CARROTS = new BlockCarrots();
    public static final BlockBeets BEETS = new BlockBeets();
    public static final BlockPotatoes POTATOES = new BlockPotatoes();

    public static final TallGrassBottom TALL_GRASS_BOTTOM = new TallGrassBottom();
    public static final TallGrassTop TALL_GRASS_TOP = new TallGrassTop();
    public static final TallDryGrassBottom TALL_DRY_GRASS_BOTTOM = new TallDryGrassBottom();
    public static final TallDryGrassTop TALL_DRY_GRASS_TOP = new TallDryGrassTop();
    public static final Block BLOCK_STONE = new Block(405, "Stone", new BlockTexture(0, 4));

    public static final SunflowerHead SUNFLOWER_HEAD = new SunflowerHead();
    public static final SunflowerStalk SUNFLOWER_STALK = new SunflowerStalk();
    public static final BlockSunflowerSeeds SUNFLOWER_SEEDS = new BlockSunflowerSeeds();

    public static final Block RED_WIRE = new Block(737, "Red Wire", 6, 5, new BlockTexture(10, 19), false, false,
            BlockRenderType.WIRE);
    public static final Block GREEN_WIRE = new Block(738, "Green Wire", 7, 5, new BlockTexture(11, 19), false, false,
            BlockRenderType.WIRE);
    public static final Block BLUE_WIRE = new Block(739, "Blue Wire", 8, 5, new BlockTexture(12, 19), false, false,
            BlockRenderType.WIRE);
    public static final Block GRAY_WIRE = new Block(740, "Gray Wire", 9, 5, new BlockTexture(13, 19), false, false,
            BlockRenderType.WIRE);

    public static final PressurePlate IRON_PLATFORM = new PressurePlate(741, "Steel Pressure Plate",
            new BlockTexture(1, 20));
    public static final PressurePlate STONE_PLATFORM = new PressurePlate(742, "Stone Pressure Plate",
            new BlockTexture(0, 4));
    public static final PressurePlate OAK_PLATFORM = new PressurePlate(743, "Oak Pressure Plate",
            new BlockTexture(6, 12));
    public static final PressurePlate BIRCH_PLATFORM = new PressurePlate(744, "Birch Pressure Plate",
            new BlockTexture(6, 13));
    // public static final OffSwitch offSwitch = new OffSwitch();
    // public static final OnSwitch onSwitch = new OnSwitch();
    // public static final Button BUTTON = new Button();

    public static final Block Candle = new Block(113, "Candle", new BlockTexture(0, 5, 0, 5, 0, 5), true, (byte) 3, 1,
            BlockRenderType.SPRITE);
    public static final Block BlueCandle = new Block(220, "Blue Candle", new BlockTexture(0, 6, 0, 6, 0, 6), true,
            (byte) 3, 1, BlockRenderType.SPRITE);
    public static final Block GreenCandle = new Block(218, "Green Candle", new BlockTexture(0, 7, 0, 7, 0, 7), true,
            (byte) 3, 1, BlockRenderType.SPRITE);
    public static final Block YellowCandle = new Block(216, "Yellow Candle", new BlockTexture(0, 8, 0, 8, 0, 8), true,
            (byte) 3, 1, BlockRenderType.SPRITE);
    public static final Block RedCandle = new Block(43, "Red Candle", new BlockTexture(0, 9, 0, 9, 0, 9), true,
            (byte) 3, 1, BlockRenderType.SPRITE);
    public static final Block ElectricLight = new Block(174, "Electric Light",
            new BlockTexture(24, 17, 24, 17, 24, 17)) {
        @Override
        public void init() {
            luminous = true;
            animationLength = 5;
        }
    };
    public static final Block EdisonLight = new Block(55, "Edison Light", new BlockTexture(4, 13, 4, 13, 4, 13), true,
            true, true, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block SeaLight = new Block(13, "Sea Light") {
        @Override
        public void init() {
            texture = new BlockTexture(22, 5, 22, 5, 22, 5);
            luminous = true;
            animationLength = 4;
        }
    };
    public static final Block GlowRock = new Block(178, "Glow Rock", new BlockTexture(9, 6, 9, 6, 9, 6), true, true,
            true, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block Dandelion = new Plant(59, "Dandelion", new BlockTexture(15, 1, 15, 1, 15, 1), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block PeonyBush = new Plant(678, "Peony Bush", new BlockTexture(19, 7, 19, 7, 19, 7), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block AzureBluet = new Plant(58, "Azure Bluet", new BlockTexture(17, 4, 17, 4, 17, 4), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block YellowFlower = new Plant(44, "Yellow Flower", new BlockTexture(18, 4, 18, 4, 18, 4),
            false, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block RedFlower = new Plant(41, "Red Flower", new BlockTexture(19, 4, 19, 4, 19, 4), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BlueOrchid = new Plant(60, "Blue Orchid", new BlockTexture(20, 4, 20, 4, 20, 4), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Pansies = new Plant(7, "Pansies", new BlockTexture(18, 6, 18, 6, 18, 6), false, false,
            false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Roses = new Plant(126, "Roses", new BlockTexture(19, 6, 19, 6, 19, 6), false, false,
            false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Fern = new Plant(61, "Fern", new BlockTexture(20, 5, 20, 5, 20, 5), false, false, false,
            (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block WhiteRose = new Plant(233, "White Rose", new BlockTexture(19, 5, 19, 5, 19, 5), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block RedRose = new Plant(236, "Red Rose", new BlockTexture(18, 5, 18, 5, 18, 5), false, false,
            false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BlackeyeSusan = new Plant(239, "Black-Eye Susan", new BlockTexture(17, 5, 17, 5, 17, 5),
            false, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block OrangeTulip = new Plant(240, "Orange Tulip", new BlockTexture(16, 5, 16, 5, 16, 5), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block DeadBush = new Plant(241, "Dead Bush", new BlockTexture(3, 7, 3, 7, 3, 7), false, false,
            false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Cake = new Block(36, "Cake", new BlockTexture(12, 8, 12, 8, 12, 8), false, false, false,
            (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Bread = new Block(592, "Bread", new BlockTexture(11, 8, 11, 8, 11, 8), false, false,
            false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Croissant = new Block(407, "Croissant", new BlockTexture(10, 8, 10, 8, 10, 8), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Bottle = new Block(574, "Bottle", new BlockTexture(12, 4, 12, 4, 12, 4), false, false,
            false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block Cup = new Block(578, "Cup", new BlockTexture(13, 4, 13, 4, 13, 4), false, false, false,
            (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block WineGlass = new Block(583, "Wine Glass", new BlockTexture(14, 4, 14, 4, 14, 4), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BLOCK_ICE = new Block(570, "Ice Block", new BlockTexture(3, 4, 3, 4, 3, 4), true, false,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PolishedDiorite = new Block(54, "Polished Diorite", new BlockTexture(1, 2, 1, 2, 1, 2),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PolishedAndesite = new Block(56, "Polished Andesite", new BlockTexture(3, 3, 3, 3, 3, 3),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CrackedStone = new Block(27, "Cracked Stone", new BlockTexture(5, 6, 5, 6, 5, 6), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block StoneWithVines = new Block(28, "Stone with Vines", new BlockTexture(4, 6, 4, 6, 4, 6),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BurgundyBrick = new Block(39, "Burgundy Brick", new BlockTexture(0, 14, 0, 14, 0, 14),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block Bookshelf = new Block(24, "Bookshelf", new BlockTexture(6, 13, 6, 13, 3, 2), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MusicBox = new Block(35, "Music Box", new BlockTexture(11, 4, 10, 4, 10, 4), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block Obsidian = new Block(38, "Obsidian", new BlockTexture(5, 2, 5, 2, 5, 2), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block RedPalisadeSandstone = new Block(175, "Red Palisade Sandstone",
            new BlockTexture(4, 10, 4, 10, 4, 11), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PalisadeSandstone = new Block(177, "Palisade Sandstone",
            new BlockTexture(5, 10, 5, 10, 5, 11), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block EngravedSandstone = new Block(460, "Engraved Sandstone",
            new BlockTexture(11, 13, 11, 13, 3, 14), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block EngravedSandstone2 = new Block(676, "Engraved Sandstone 2",
            new BlockTexture(11, 13, 11, 13, 7, 14), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block EngravedRedSandstone = new Block(461, "Engraved Red Sandstone",
            new BlockTexture(10, 13, 10, 13, 4, 14), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block EngravedRedSandstone2 = new Block(677, "Engraved Red Sandstone 2",
            new BlockTexture(10, 13, 10, 13, 6, 14), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block QuartzPillarBlock = new Block(100, "Quartz Pillar Block",
            new BlockTexture(13, 11, 13, 11, 13, 12), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MarblePillarBlock = new Block(449, "Marble Pillar Block",
            new BlockTexture(14, 11, 14, 11, 14, 12), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PhantomStone = new Block(107, "Phantom Stone", new BlockTexture(1, 0, 1, 0, 1, 0), false,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);

    public static final Block StoneBrick = new Block(19, "Stone Brick", new BlockTexture(6, 3, 6, 3, 6, 3), true, true,
            DEFAULT_BLOCK_TYPE_ID);

    public static final Block PhantomStoneBrick = new Block(108, "Phantom Stone Brick",
            new BlockTexture(6, 3, 6, 3, 6, 3), false, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PhantomSandstone = new Block(176, "Phantom Sandstone",
            new BlockTexture(11, 13, 11, 15, 11, 14), false, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block Wool = new Block(22, "Wool", new BlockTexture(1, 14, 1, 14, 1, 14), true, true, false,
            (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolGray = new Block(142, "Wool Gray", new BlockTexture(2, 7, 2, 7, 2, 7), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolRed = new Block(131, "Wool Red", new BlockTexture(1, 8, 1, 8, 1, 8), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolPink = new Block(132, "Wool Pink", new BlockTexture(2, 8, 2, 8, 2, 8), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolOrange = new Block(103, "Wool Orange", new BlockTexture(2, 13, 2, 13, 2, 13), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolYellow = new Block(133, "Wool Yellow", new BlockTexture(2, 10, 2, 10, 2, 10), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolGreen = new Block(141, "Wool Green", new BlockTexture(2, 9, 2, 9, 2, 9), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolDarkGreen = new Block(140, "Wool Dark Green", new BlockTexture(1, 9, 1, 9, 1, 9),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolTurquoise = new Block(102, "Wool Turquoise", new BlockTexture(1, 13, 1, 13, 1, 13),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolDeepBlue = new Block(138, "Wool Deep Blue", new BlockTexture(1, 11, 1, 11, 1, 11),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolSkyBlue = new Block(139, "Wool Sky Blue", new BlockTexture(2, 11, 2, 11, 2, 11), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolBrown = new Block(134, "Wool Brown", new BlockTexture(1, 10, 1, 10, 1, 10), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolPurple = new Block(127, "Wool Purple", new BlockTexture(1, 12, 1, 12, 1, 12), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolMagenta = new Block(156, "Wool Magenta", new BlockTexture(2, 12, 2, 12, 2, 12), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WoolBlack = new Block(157, "Wool Black", new BlockTexture(1, 7, 1, 7, 1, 7), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block YellowConcrete = new Block(66, "Yellow Concrete", new BlockTexture(0, 16, 0, 16, 0, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlackConcrete = new Block(68, "Black Concrete", new BlockTexture(1, 16, 1, 16, 1, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlueConcrete = new Block(70, "Blue Concrete", new BlockTexture(2, 16, 2, 16, 2, 16), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BrownConcrete = new Block(72, "Brown Concrete", new BlockTexture(3, 16, 3, 16, 3, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CyanConcrete = new Block(74, "Cyan Concrete", new BlockTexture(4, 16, 4, 16, 4, 16), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GrayConcrete = new Block(76, "Gray Concrete", new BlockTexture(5, 16, 5, 16, 5, 16), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GreenConcrete = new Block(78, "Green Concrete", new BlockTexture(6, 16, 6, 16, 6, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LightBlueConcrete = new Block(80, "Light Blue Concrete",
            new BlockTexture(7, 16, 7, 16, 7, 16), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LightGrayConcrete = new Block(82, "Light Gray Concrete",
            new BlockTexture(8, 16, 8, 16, 8, 16), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LimeConcrete = new Block(84, "Lime Concrete", new BlockTexture(9, 16, 9, 16, 9, 16), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MagentaConcrete = new Block(86, "Magenta Concrete",
            new BlockTexture(10, 16, 10, 16, 10, 16), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block OrangeConcrete = new Block(88, "Orange Concrete",
            new BlockTexture(11, 16, 11, 16, 11, 16), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PinkConcrete = new Block(90, "Pink Concrete", new BlockTexture(12, 16, 12, 16, 12, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PurpleConcrete = new Block(92, "Purple Concrete",
            new BlockTexture(13, 16, 13, 16, 13, 16), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block RedConcrete = new Block(94, "Red Concrete", new BlockTexture(14, 16, 14, 16, 14, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WhiteConcrete = new Block(96, "White Concrete", new BlockTexture(15, 16, 15, 16, 15, 16),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block YellowGlazedTeracotta = new Block(67, "Yellow Glazed Teracotta",
            new BlockTexture(0, 17, 0, 17, 0, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block BlackGlazedTeracotta = new Block(69, "Black Glazed Teracotta",
            new BlockTexture(1, 17, 1, 17, 1, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block BlueGlazedTeracotta = new Block(71, "Blue Glazed Teracotta",
            new BlockTexture(2, 17, 2, 17, 2, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block BrownGlazedTeracotta = new Block(73, "Brown Glazed Teracotta",
            new BlockTexture(3, 17, 3, 17, 3, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block CyanGlazedTeracotta = new Block(75, "Cyan Glazed Teracotta",
            new BlockTexture(4, 17, 4, 17, 4, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block GrayGlazedTeracotta = new Block(77, "Gray Glazed Teracotta",
            new BlockTexture(5, 17, 5, 17, 5, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block GreenGlazedTeracotta = new Block(79, "Green Glazed Teracotta",
            new BlockTexture(6, 17, 6, 17, 6, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block LightBlueGlazedTeracotta = new Block(81, "Light Blue Glazed Teracotta",
            new BlockTexture(7, 17, 7, 17, 7, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block LightGrayGlazedTeracotta = new Block(83, "Light Gray Glazed Teracotta",
            new BlockTexture(8, 17, 8, 17, 8, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block LimeGlazedTeracotta = new Block(85, "Lime Glazed Teracotta",
            new BlockTexture(9, 17, 9, 17, 9, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block MagentaGlazedTeracotta = new Block(87, "Magenta Glazed Teracotta",
            new BlockTexture(10, 17, 10, 17, 10, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block OrangeGlazedTeracotta = new Block(89, "Orange Glazed Teracotta",
            new BlockTexture(11, 17, 11, 17, 11, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block PinkGlazedTeracotta = new Block(91, "Pink Glazed Teracotta",
            new BlockTexture(12, 17, 12, 17, 12, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block PurpleGlazedTeracotta = new Block(93, "Purple Glazed Teracotta",
            new BlockTexture(13, 17, 13, 17, 13, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block RedGlazedTeracotta = new Block(95, "Red Glazed Teracotta",
            new BlockTexture(14, 17, 14, 17, 14, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block WhiteGlazedTeracotta = new Block(97, "White Glazed Teracotta",
            new BlockTexture(15, 17, 15, 17, 15, 17), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block YellowstainedGlass = new Block(194, "Yellow-Stained Glass",
            new BlockTexture(0, 18, 0, 18, 0, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlackstainedGlass = new Block(179, "Black-Stained Glass",
            new BlockTexture(1, 18, 1, 18, 1, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BluestainedGlass = new Block(180, "Blue-Stained Glass",
            new BlockTexture(2, 18, 2, 18, 2, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BrownstainedGlass = new Block(181, "Brown-Stained Glass",
            new BlockTexture(3, 18, 3, 18, 3, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CyanstainedGlass = new Block(182, "Cyan-Stained Glass",
            new BlockTexture(4, 18, 4, 18, 4, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GraystainedGlass = new Block(183, "Gray-Stained Glass",
            new BlockTexture(5, 18, 5, 18, 5, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GreenstainedGlass = new Block(184, "Green-Stained Glass",
            new BlockTexture(6, 18, 6, 18, 6, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LightBluestainedGlass = new Block(185, "Light Blue-Stained Glass",
            new BlockTexture(7, 18, 7, 18, 7, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LightGraystainedGlass = new Block(186, "Light Gray-Stained Glass",
            new BlockTexture(8, 18, 8, 18, 8, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LimestainedGlass = new Block(187, "Lime-Stained Glass",
            new BlockTexture(9, 18, 9, 18, 9, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MagentastainedGlass = new Block(188, "Magenta-Stained Glass",
            new BlockTexture(10, 18, 10, 18, 10, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block OrangestainedGlass = new Block(189, "Orange-Stained Glass",
            new BlockTexture(11, 18, 11, 18, 11, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PinkstainedGlass = new Block(190, "Pink-Stained Glass",
            new BlockTexture(12, 18, 12, 18, 12, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PurplestainedGlass = new Block(191, "Purple-Stained Glass",
            new BlockTexture(13, 18, 13, 18, 13, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block RedstainedGlass = new Block(192, "Red-Stained Glass",
            new BlockTexture(14, 18, 14, 18, 14, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WhitestainedGlass = new Block(193, "White-Stained Glass",
            new BlockTexture(15, 18, 15, 18, 15, 18), true, false, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block Granite = new Block(219, "Granite", new BlockTexture(15, 2, 15, 2, 15, 2), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GraniteBrick = new Block(62, "Granite Brick", new BlockTexture(16, 2, 16, 2, 16, 2), true,
            true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block PrismarineBricks = new Block(409, "Prismarine Bricks",
            new BlockTexture(19, 2, 19, 2, 19, 2), true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block DarkPrismarineBricks = new Block(410, "Dark Prismarine Bricks",
            new BlockTexture(20, 2, 20, 2, 20, 2), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LapisLazulBlock = new Block(229, "Lapis Lazul Block",
            new BlockTexture(0, 20, 0, 20, 0, 20), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block IronBlock = new Block(145, "Steel Block", new BlockTexture(1, 20, 1, 20, 1, 20), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GoldBlock = new Block(118, "Gold Block", new BlockTexture(2, 20, 2, 20, 2, 20), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block EmeraldBlock = new Block(238, "Emerald Block", new BlockTexture(3, 20, 3, 20, 3, 20),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block DiamondBlock = new Block(144, "Diamond Block", new BlockTexture(4, 20, 4, 20, 4, 20),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CoalBlock = new Block(46, "Coal Block", new BlockTexture(5, 20, 5, 20, 5, 20), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block Beehive = new Block(52, "Beehive", new BlockTexture(16, 7, 18, 7, 17, 7), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block HayBail = new Block(242, "Hay Bail", new BlockTexture(20, 7, 20, 7, 20, 8), true, true,
            false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block FireCoralBlock = new Block(112, "Fire Coral Block",
            new BlockTexture(17, 10, 17, 10, 17, 10), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block FireCoral = new Plant(115, "Fire Coral", new BlockTexture(18, 10, 18, 10, 18, 10), true,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block FireCoralFan = new Plant(227, "Fire Coral Fan", new BlockTexture(19, 10, 19, 10, 19, 10),
            true, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block HornCoralBlock = new Block(117, "Horn Coral Block",
            new BlockTexture(17, 11, 17, 11, 17, 11), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block HornCoral = new Plant(235, "Horn Coral", new BlockTexture(18, 11, 18, 11, 18, 11), true,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block HornCoralFan = new Plant(119, "Horn Coral Fan", new BlockTexture(19, 11, 19, 11, 19, 11),
            true, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block TubeCoralBlock = new Block(135, "Tube Coral Block",
            new BlockTexture(17, 12, 17, 12, 17, 12), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block TubeCoral = new Plant(136, "Tube Coral", new BlockTexture(18, 12, 18, 12, 18, 12), true,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block TubeCoralFan = new Plant(137, "Tube Coral Fan", new BlockTexture(19, 12, 19, 12, 19, 12),
            true, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BrainCoralBlock = new Block(143, "Brain Coral Block",
            new BlockTexture(17, 13, 17, 13, 17, 13), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BrainCoral = new Plant(98, "Brain Coral", new BlockTexture(18, 13, 18, 13, 18, 13), true,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BrainCoralFan = new Plant(232, "Brain Coral Fan",
            new BlockTexture(19, 13, 19, 13, 19, 13), true, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BubbleCoralBlock = new Block(150, "Bubble Coral Block",
            new BlockTexture(17, 14, 17, 14, 17, 14), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BubbleCoral = new Plant(151, "Bubble Coral", new BlockTexture(18, 14, 18, 14, 18, 14),
            true, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block BubbleCoralFan = new Plant(152, "Bubble Coral Fan",
            new BlockTexture(19, 14, 19, 14, 19, 14), true, false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block FlatVines = new Block(222, "Flat Vines", new BlockTexture(15, 8, 15, 8, 15, 8), true,
            false, false, (byte) 0, 0, BlockRenderType.WALL_ITEM);
    public static final Block Vines = new Block(5, "Vines", new BlockTexture(15, 8, 15, 8, 15, 8), false, false, false,
            (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block FlatDragonVines = new Block(446, "Flat Dragon Vines",
            new BlockTexture(15, 7, 15, 7, 15, 7), true, false, false, (byte) 0, 0, BlockRenderType.WALL_ITEM);
    public static final Block DragonVines = new Block(447, "Dragon Vines", new BlockTexture(15, 7, 15, 7, 15, 7), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block RedVinesFlat = new Block(215, "Red Vines Flat", new BlockTexture(14, 8, 14, 8, 14, 8),
            true, false, false, (byte) 0, 0, BlockRenderType.WALL_ITEM);
    public static final Block RedVines = new Block(221, "Red Vines", new BlockTexture(14, 8, 14, 8, 14, 8), false,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block CaveVinesFlat = new Block(203, "Cave Vines Flat", new BlockTexture(14, 7, 14, 7, 14, 7),
            false, false, false, (byte) 0, 0, BlockRenderType.WALL_ITEM);
    public static final Block CaveVines = new Block(217, "Cave Vines", new BlockTexture(14, 7, 14, 7, 14, 7), true,
            false, false, (byte) 0, 0, BlockRenderType.SPRITE);
    public static final Block PrismarineBrickSlab = new Block(436, " Prismarine Brick Slab",
            new BlockTexture(19, 2, 19, 2, 19, 2), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PrismarineBrickStairs = new Block(201, " Prismarine Brick Stairs",
            new BlockTexture(19, 2, 19, 2, 19, 2), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PrismarineBrickFence = new Block(438, " Prismarine Brick Fence",
            new BlockTexture(19, 2, 19, 2, 19, 2), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block DarkPrismarineBrickSlab = new Block(458, "Dark Prismarine Brick Slab",
            new BlockTexture(20, 2, 20, 2, 20, 2), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block DarkPrismarineBrickStairs = new Block(435, "Dark Prismarine Brick Stairs",
            new BlockTexture(20, 2, 20, 2, 20, 2), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block DarkPrismarineBrickFence = new Block(459, "Dark Prismarine Brick Fence",
            new BlockTexture(20, 2, 20, 2, 20, 2), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block GraniteBrickPillar = new Block(454, "Granite Brick Pillar",
            new BlockTexture(16, 2, 16, 2, 16, 2), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block GraniteBrickSlab = new Block(455, "Granite Brick Slab",
            new BlockTexture(16, 2, 16, 2, 16, 2), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GraniteBrickStairs = new Block(158, "Granite Brick Stairs",
            new BlockTexture(16, 2, 16, 2, 16, 2), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GraniteBrickFence = new Block(456, "Granite Brick Fence",
            new BlockTexture(16, 2, 16, 2, 16, 2), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block JunglePlanksSlab = new Block(31, "Jungle Planks Slab",
            new BlockTexture(6, 10, 6, 10, 6, 10), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block JunglePlanksStairs = new Block(32, "Jungle Planks Stairs",
            new BlockTexture(6, 10, 6, 10, 6, 10), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block JungleFence = new Block(40, "Jungle Fence", new BlockTexture(6, 10, 6, 10, 6, 10), true,
            false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BirchPlanksStairs = new Block(196, "Birch Planks Stairs",
            new BlockTexture(6, 13, 6, 13, 6, 13), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OakPlanksStairs = new Block(197, "Oak Planks Stairs",
            new BlockTexture(6, 12, 6, 12, 6, 12), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block DarkOakPlanksStairs = new Block(198, "Dark Oak Planks Stairs",
            new BlockTexture(6, 11, 6, 11, 6, 11), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block AcaciaPlanksStairs = new Block(250, "Acacia Planks Stairs",
            new BlockTexture(7, 11, 7, 11, 7, 11), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block SandstoneStairs = new Block(202, "Sandstone Stairs",
            new BlockTexture(11, 13, 11, 15, 11, 14), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PolishedAndesiteStairs = new Block(243, "Polished Andesite Stairs",
            new BlockTexture(3, 3, 3, 3, 3, 3), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PolishedDioriteStairs = new Block(204, "Polished Diorite Stairs",
            new BlockTexture(1, 2, 1, 2, 1, 2), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block RedSandstoneStairs = new Block(259, "Red Sandstone Stairs",
            new BlockTexture(10, 13, 10, 15, 10, 14), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GlassStairs = new Block(411, "Glass Stairs", new BlockTexture(1, 3, 1, 3, 1, 3), true,
            false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block StoneBrickStairs = new Block(260, "Stone Brick Stairs",
            new BlockTexture(6, 3, 6, 3, 6, 3), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BrickStairs = new Block(263, "Brick Stairs", new BlockTexture(7, 0, 7, 0, 7, 0), true,
            false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CobblestoneStairs = new Block(266, "Cobblestone Stairs",
            new BlockTexture(0, 1, 0, 1, 0, 1), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PalisadeStoneStairs = new Block(268, "Palisade Stone Stairs",
            new BlockTexture(6, 0, 6, 0, 5, 0), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PalisadeStone2Stairs = new Block(271, "Palisade Stone 2 Stairs",
            new BlockTexture(5, 13, 5, 13, 5, 13), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CrackedStoneStairs = new Block(276, "Cracked Stone Stairs",
            new BlockTexture(5, 6, 5, 6, 5, 6), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block StoneWithVinesStairs = new Block(279, "Stone with Vines Stairs",
            new BlockTexture(4, 6, 4, 6, 4, 6), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BurgundyBrickStairs = new Block(282, "Burgundy Brick Stairs",
            new BlockTexture(0, 14, 0, 14, 0, 14), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block RedPalisadeSandstoneStairs = new Block(287, "Red Palisade Sandstone Stairs",
            new BlockTexture(4, 10, 4, 10, 4, 11), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PalisadeSandstoneStairs = new Block(290, "Palisade Sandstone Stairs",
            new BlockTexture(5, 10, 5, 10, 5, 11), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolStairs = new Block(293, "Wool Stairs", new BlockTexture(1, 14, 1, 14, 1, 14), true,
            false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolGrayStairs = new Block(295, "Wool Gray Stairs", new BlockTexture(2, 7, 2, 7, 2, 7),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolRedStairs = new Block(297, "Wool Red Stairs", new BlockTexture(1, 8, 1, 8, 1, 8),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolPinkStairs = new Block(299, "Wool Pink Stairs", new BlockTexture(2, 8, 2, 8, 2, 8),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolOrangeStairs = new Block(301, "Wool Orange Stairs",
            new BlockTexture(2, 13, 2, 13, 2, 13), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolYellowStairs = new Block(303, "Wool Yellow Stairs",
            new BlockTexture(2, 10, 2, 10, 2, 10), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolGreenStairs = new Block(305, "Wool Green Stairs", new BlockTexture(2, 9, 2, 9, 2, 9),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolDarkGreenStairs = new Block(307, "Wool Dark Green Stairs",
            new BlockTexture(1, 9, 1, 9, 1, 9), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolTurquoiseStairs = new Block(309, "Wool Turquoise Stairs",
            new BlockTexture(1, 13, 1, 13, 1, 13), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolDeepBlueStairs = new Block(311, "Wool Deep Blue Stairs",
            new BlockTexture(1, 11, 1, 11, 1, 11), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolSkyBlueStairs = new Block(313, "Wool Sky Blue Stairs",
            new BlockTexture(2, 11, 2, 11, 2, 11), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolBrownStairs = new Block(315, "Wool Brown Stairs",
            new BlockTexture(1, 10, 1, 10, 1, 10), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolPurpleStairs = new Block(317, "Wool Purple Stairs",
            new BlockTexture(1, 12, 1, 12, 1, 12), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolMagentaStairs = new Block(319, "Wool Magenta Stairs",
            new BlockTexture(2, 12, 2, 12, 2, 12), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WoolBlackStairs = new Block(321, "Wool Black Stairs", new BlockTexture(1, 7, 1, 7, 1, 7),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CementStairs = new Block(414, "Cement Stairs", new BlockTexture(8, 4, 8, 4, 8, 4), true,
            false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block YellowConcreteStairs = new Block(323, "Yellow Concrete Stairs",
            new BlockTexture(0, 16, 0, 16, 0, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlackConcreteStairs = new Block(326, "Black Concrete Stairs",
            new BlockTexture(1, 16, 1, 16, 1, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlueConcreteStairs = new Block(329, "Blue Concrete Stairs",
            new BlockTexture(2, 16, 2, 16, 2, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BrownConcreteStairs = new Block(332, "Brown Concrete Stairs",
            new BlockTexture(3, 16, 3, 16, 3, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CyanConcreteStairs = new Block(335, "Cyan Concrete Stairs",
            new BlockTexture(4, 16, 4, 16, 4, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GrayConcreteStairs = new Block(338, "Gray Concrete Stairs",
            new BlockTexture(5, 16, 5, 16, 5, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GreenConcreteStairs = new Block(341, "Green Concrete Stairs",
            new BlockTexture(6, 16, 6, 16, 6, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightBlueConcreteStairs = new Block(344, "Light Blue Concrete Stairs",
            new BlockTexture(7, 16, 7, 16, 7, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightGrayConcreteStairs = new Block(347, "Light Gray Concrete Stairs",
            new BlockTexture(8, 16, 8, 16, 8, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LimeConcreteStairs = new Block(350, "Lime Concrete Stairs",
            new BlockTexture(9, 16, 9, 16, 9, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MagentaConcreteStairs = new Block(353, "Magenta Concrete Stairs",
            new BlockTexture(10, 16, 10, 16, 10, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OrangeConcreteStairs = new Block(356, "Orange Concrete Stairs",
            new BlockTexture(11, 16, 11, 16, 11, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PinkConcreteStairs = new Block(359, "Pink Concrete Stairs",
            new BlockTexture(12, 16, 12, 16, 12, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PurpleConcreteStairs = new Block(362, "Purple Concrete Stairs",
            new BlockTexture(13, 16, 13, 16, 13, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block RedConcreteStairs = new Block(365, "Red Concrete Stairs",
            new BlockTexture(14, 16, 14, 16, 14, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WhiteConcreteStairs = new Block(368, "White Concrete Stairs",
            new BlockTexture(15, 16, 15, 16, 15, 16), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block YellowGlazedTeracottaStairs = new Block(371, "Yellow Glazed Teracotta Stairs",
            new BlockTexture(0, 17, 0, 17, 0, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlackGlazedTeracottaStairs = new Block(373, "Black Glazed Teracotta Stairs",
            new BlockTexture(1, 17, 1, 17, 1, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlueGlazedTeracottaStairs = new Block(375, "Blue Glazed Teracotta Stairs",
            new BlockTexture(2, 17, 2, 17, 2, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BrownGlazedTeracottaStairs = new Block(377, "Brown Glazed Teracotta Stairs",
            new BlockTexture(3, 17, 3, 17, 3, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CyanGlazedTeracottaStairs = new Block(379, "Cyan Glazed Teracotta Stairs",
            new BlockTexture(4, 17, 4, 17, 4, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GrayGlazedTeracottaStairs = new Block(381, "Gray Glazed Teracotta Stairs",
            new BlockTexture(5, 17, 5, 17, 5, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GreenGlazedTeracottaStairs = new Block(383, "Green Glazed Teracotta Stairs",
            new BlockTexture(6, 17, 6, 17, 6, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightBlueGlazedTeracottaStairs = new Block(385, "Light Blue Glazed Teracotta Stairs",
            new BlockTexture(7, 17, 7, 17, 7, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightGrayGlazedTeracottaStairs = new Block(387, "Light Gray Glazed Teracotta Stairs",
            new BlockTexture(8, 17, 8, 17, 8, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LimeGlazedTeracottaStairs = new Block(389, "Lime Glazed Teracotta Stairs",
            new BlockTexture(9, 17, 9, 17, 9, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MagentaGlazedTeracottaStairs = new Block(391, "Magenta Glazed Teracotta Stairs",
            new BlockTexture(10, 17, 10, 17, 10, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OrangeGlazedTeracottaStairs = new Block(393, "Orange Glazed Teracotta Stairs",
            new BlockTexture(11, 17, 11, 17, 11, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PinkGlazedTeracottaStairs = new Block(395, "Pink Glazed Teracotta Stairs",
            new BlockTexture(12, 17, 12, 17, 12, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PurpleGlazedTeracottaStairs = new Block(397, "Purple Glazed Teracotta Stairs",
            new BlockTexture(13, 17, 13, 17, 13, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block RedGlazedTeracottaStairs = new Block(399, "Red Glazed Teracotta Stairs",
            new BlockTexture(14, 17, 14, 17, 14, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WhiteGlazedTeracottaStairs = new Block(401, "White Glazed Teracotta Stairs",
            new BlockTexture(15, 17, 15, 17, 15, 17), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block ObsidianStairs = new Block(437, "Obsidian Stairs", new BlockTexture(5, 2, 5, 2, 5, 2),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LapisLazulStairs = new Block(419, "Lapis Lazul Stairs",
            new BlockTexture(0, 20, 0, 20, 0, 20), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block IronStairs = new Block(422, "Steel Stairs", new BlockTexture(1, 20, 1, 20, 1, 20), true,
            false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GoldStairs = new Block(425, "Gold Stairs", new BlockTexture(2, 20, 2, 20, 2, 20), true,
            false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block EmeraldStairs = new Block(428, "Emerald Stairs", new BlockTexture(3, 20, 3, 20, 3, 20),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block DiamondStairs = new Block(431, "Diamond Stairs", new BlockTexture(4, 20, 4, 20, 4, 20),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BirchPlanksSlab = new Block(205, "Birch Planks Slab",
            new BlockTexture(6, 13, 6, 13, 6, 13), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OakPlanksSlab = new Block(206, "Oak Planks Slab", new BlockTexture(6, 12, 6, 12, 6, 12),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block DarkOakPlanksSlab = new Block(207, "Dark Oak Planks Slab",
            new BlockTexture(6, 11, 6, 11, 6, 11), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block AcaciaPlanksSlab = new Block(251, "Acacia Planks Slab",
            new BlockTexture(7, 11, 7, 11, 7, 11), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block StoneBrickSlab = new Block(209, "Stone Brick Slab", new BlockTexture(6, 3, 6, 3, 6, 3),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block RedSandstoneSlab = new Block(210, "Red Sandstone Slab",
            new BlockTexture(10, 13, 10, 15, 10, 14), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GlassSlab = new Block(412, "Glass Slab", new BlockTexture(1, 3, 1, 3, 1, 3), true, false,
            false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block SandstoneSlab = new Block(211, "Sandstone Slab", new BlockTexture(11, 13, 11, 15, 11, 14),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PolishedAndesiteSlab = new Block(213, "Polished Andesite Slab",
            new BlockTexture(3, 3, 3, 3, 3, 3), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PolishedDioriteSlab = new Block(244, "Polished Diorite Slab",
            new BlockTexture(1, 2, 1, 2, 1, 2), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BrickSlab = new Block(264, "Brick Slab", new BlockTexture(7, 0, 7, 0, 7, 0), true, false,
            false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CobblestoneSlab = new Block(267, "Cobblestone Slab", new BlockTexture(0, 1, 0, 1, 0, 1),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PalisadeStoneSlab = new Block(269, "Palisade Stone Slab",
            new BlockTexture(6, 0, 6, 0, 5, 0), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PalisadeStone2Slab = new Block(272, "Palisade Stone 2 Slab",
            new BlockTexture(5, 13, 5, 13, 5, 13), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CrackedStoneSlab = new Block(277, "Cracked Stone Slab",
            new BlockTexture(5, 6, 5, 6, 5, 6), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block StoneWithVinesSlab = new Block(280, "Stone with Vines Slab",
            new BlockTexture(4, 6, 4, 6, 4, 6), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BurgundyBrickSlab = new Block(283, "Burgundy Brick Slab",
            new BlockTexture(0, 14, 0, 14, 0, 14), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block RedPalisadeSandstoneSlab = new Block(288, "Red Palisade Sandstone Slab",
            new BlockTexture(4, 10, 4, 10, 4, 11), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PalisadeSandstoneSlab = new Block(291, "Palisade Sandstone Slab",
            new BlockTexture(5, 10, 5, 10, 5, 11), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolSlab = new Block(294, "Wool Slab", new BlockTexture(1, 14, 1, 14, 1, 14), true, false,
            false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolGraySlab = new Block(296, "Wool Gray Slab", new BlockTexture(2, 7, 2, 7, 2, 7), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolRedSlab = new Block(298, "Wool Red Slab", new BlockTexture(1, 8, 1, 8, 1, 8), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolPinkSlab = new Block(300, "Wool Pink Slab", new BlockTexture(2, 8, 2, 8, 2, 8), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolOrangeSlab = new Block(302, "Wool Orange Slab", new BlockTexture(2, 13, 2, 13, 2, 13),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolYellowSlab = new Block(304, "Wool Yellow Slab", new BlockTexture(2, 10, 2, 10, 2, 10),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolGreenSlab = new Block(306, "Wool Green Slab", new BlockTexture(2, 9, 2, 9, 2, 9),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolDarkGreenSlab = new Block(308, "Wool Dark Green Slab",
            new BlockTexture(1, 9, 1, 9, 1, 9), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolTurquoiseSlab = new Block(310, "Wool Turquoise Slab",
            new BlockTexture(1, 13, 1, 13, 1, 13), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolDeepBlueSlab = new Block(312, "Wool Deep Blue Slab",
            new BlockTexture(1, 11, 1, 11, 1, 11), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolSkyBlueSlab = new Block(314, "Wool Sky Blue Slab",
            new BlockTexture(2, 11, 2, 11, 2, 11), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolBrownSlab = new Block(316, "Wool Brown Slab", new BlockTexture(1, 10, 1, 10, 1, 10),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolPurpleSlab = new Block(318, "Wool Purple Slab", new BlockTexture(1, 12, 1, 12, 1, 12),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolMagentaSlab = new Block(320, "Wool Magenta Slab",
            new BlockTexture(2, 12, 2, 12, 2, 12), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WoolBlackSlab = new Block(322, "Wool Black Slab", new BlockTexture(1, 7, 1, 7, 1, 7),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CementSlab = new Block(416, "Cement Slab", new BlockTexture(8, 4, 8, 4, 8, 4), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block YellowConcreteSlab = new Block(324, "Yellow Concrete Slab",
            new BlockTexture(0, 16, 0, 16, 0, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlackConcreteSlab = new Block(327, "Black Concrete Slab",
            new BlockTexture(1, 16, 1, 16, 1, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlueConcreteSlab = new Block(330, "Blue Concrete Slab",
            new BlockTexture(2, 16, 2, 16, 2, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BrownConcreteSlab = new Block(333, "Brown Concrete Slab",
            new BlockTexture(3, 16, 3, 16, 3, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CyanConcreteSlab = new Block(336, "Cyan Concrete Slab",
            new BlockTexture(4, 16, 4, 16, 4, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GrayConcreteSlab = new Block(339, "Gray Concrete Slab",
            new BlockTexture(5, 16, 5, 16, 5, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GreenConcreteSlab = new Block(342, "Green Concrete Slab",
            new BlockTexture(6, 16, 6, 16, 6, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightBlueConcreteSlab = new Block(345, "Light Blue Concrete Slab",
            new BlockTexture(7, 16, 7, 16, 7, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightGrayConcreteSlab = new Block(348, "Light Gray Concrete Slab",
            new BlockTexture(8, 16, 8, 16, 8, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LimeConcreteSlab = new Block(351, "Lime Concrete Slab",
            new BlockTexture(9, 16, 9, 16, 9, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MagentaConcreteSlab = new Block(354, "Magenta Concrete Slab",
            new BlockTexture(10, 16, 10, 16, 10, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OrangeConcreteSlab = new Block(357, "Orange Concrete Slab",
            new BlockTexture(11, 16, 11, 16, 11, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PinkConcreteSlab = new Block(360, "Pink Concrete Slab",
            new BlockTexture(12, 16, 12, 16, 12, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PurpleConcreteSlab = new Block(363, "Purple Concrete Slab",
            new BlockTexture(13, 16, 13, 16, 13, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block RedConcreteSlab = new Block(366, "Red Concrete Slab",
            new BlockTexture(14, 16, 14, 16, 14, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WhiteConcreteSlab = new Block(369, "White Concrete Slab",
            new BlockTexture(15, 16, 15, 16, 15, 16), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block YellowGlazedTeracottaSlab = new Block(372, "Yellow Glazed Teracotta Slab",
            new BlockTexture(0, 17, 0, 17, 0, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlackGlazedTeracottaSlab = new Block(374, "Black Glazed Teracotta Slab",
            new BlockTexture(1, 17, 1, 17, 1, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlueGlazedTeracottaSlab = new Block(376, "Blue Glazed Teracotta Slab",
            new BlockTexture(2, 17, 2, 17, 2, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BrownGlazedTeracottaSlab = new Block(378, "Brown Glazed Teracotta Slab",
            new BlockTexture(3, 17, 3, 17, 3, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CyanGlazedTeracottaSlab = new Block(380, "Cyan Glazed Teracotta Slab",
            new BlockTexture(4, 17, 4, 17, 4, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GrayGlazedTeracottaSlab = new Block(382, "Gray Glazed Teracotta Slab",
            new BlockTexture(5, 17, 5, 17, 5, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GreenGlazedTeracottaSlab = new Block(384, "Green Glazed Teracotta Slab",
            new BlockTexture(6, 17, 6, 17, 6, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightBlueGlazedTeracottaSlab = new Block(386, "Light Blue Glazed Teracotta Slab",
            new BlockTexture(7, 17, 7, 17, 7, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightGrayGlazedTeracottaSlab = new Block(388, "Light Gray Glazed Teracotta Slab",
            new BlockTexture(8, 17, 8, 17, 8, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LimeGlazedTeracottaSlab = new Block(390, "Lime Glazed Teracotta Slab",
            new BlockTexture(9, 17, 9, 17, 9, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MagentaGlazedTeracottaSlab = new Block(392, "Magenta Glazed Teracotta Slab",
            new BlockTexture(10, 17, 10, 17, 10, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OrangeGlazedTeracottaSlab = new Block(394, "Orange Glazed Teracotta Slab",
            new BlockTexture(11, 17, 11, 17, 11, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PinkGlazedTeracottaSlab = new Block(396, "Pink Glazed Teracotta Slab",
            new BlockTexture(12, 17, 12, 17, 12, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PurpleGlazedTeracottaSlab = new Block(398, "Purple Glazed Teracotta Slab",
            new BlockTexture(13, 17, 13, 17, 13, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block RedGlazedTeracottaSlab = new Block(400, "Red Glazed Teracotta Slab",
            new BlockTexture(14, 17, 14, 17, 14, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WhiteGlazedTeracottaSlab = new Block(402, "White Glazed Teracotta Slab",
            new BlockTexture(15, 17, 15, 17, 15, 17), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block ObsidianSlab = new Block(417, "Obsidian Slab", new BlockTexture(5, 2, 5, 2, 5, 2), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LapisLazulBlockSlab = new Block(420, "Lapis Lazul Block Slab",
            new BlockTexture(0, 20, 0, 20, 0, 20), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block IronBlockSlab = new Block(423, "Steel Block Slab", new BlockTexture(1, 20, 1, 20, 1, 20),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GoldBlockSlab = new Block(426, "Gold Block Slab", new BlockTexture(2, 20, 2, 20, 2, 20),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block EmeraldBlockSlab = new Block(429, "Emerald Block Slab",
            new BlockTexture(3, 20, 3, 20, 3, 20), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block DiamondBlockSlab = new Block(432, "Diamond Block Slab",
            new BlockTexture(4, 20, 4, 20, 4, 20), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block StoneBrickFence = new Block(262, "Stone Brick Fence", new BlockTexture(6, 3, 6, 3, 6, 3),
            true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PalisadeStoneFence = new Block(270, "Palisade Stone Fence",
            new BlockTexture(6, 0, 6, 0, 5, 0), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PalisadeStone2Fence = new Block(273, "Palisade Stone 2 Fence",
            new BlockTexture(5, 13, 5, 13, 5, 13), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PolishedDioriteFence = new Block(274, "Polished Diorite Fence",
            new BlockTexture(1, 2, 1, 2, 1, 2), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PolishedAndesiteFence = new Block(275, "Polished Andesite Fence",
            new BlockTexture(3, 3, 3, 3, 3, 3), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block CrackedStoneFence = new Block(278, "Cracked Stone Fence",
            new BlockTexture(5, 6, 5, 6, 5, 6), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block StoneWithVinesFence = new Block(281, "Stone with Vines Fence",
            new BlockTexture(4, 6, 4, 6, 4, 6), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BurgundyBrickFence = new Block(284, "Burgundy Brick Fence",
            new BlockTexture(0, 14, 0, 14, 0, 14), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block RedPalisadeSandstoneFence = new Block(289, "Red Palisade Sandstone Fence",
            new BlockTexture(4, 10, 4, 10, 4, 11), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PalisadeSandstoneFence = new Block(292, "Palisade Sandstone Fence",
            new BlockTexture(5, 10, 5, 10, 5, 11), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block CementFence = new Block(415, "Cement Fence", new BlockTexture(8, 4, 8, 4, 8, 4), true,
            false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block YellowConcreteFence = new Block(325, "Yellow Concrete Fence",
            new BlockTexture(0, 16, 0, 16, 0, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BlackConcreteFence = new Block(328, "Black Concrete Fence",
            new BlockTexture(1, 16, 1, 16, 1, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BlueConcreteFence = new Block(331, "Blue Concrete Fence",
            new BlockTexture(2, 16, 2, 16, 2, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BrownConcreteFence = new Block(334, "Brown Concrete Fence",
            new BlockTexture(3, 16, 3, 16, 3, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block CyanConcreteFence = new Block(337, "Cyan Concrete Fence",
            new BlockTexture(4, 16, 4, 16, 4, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block GrayConcreteFence = new Block(340, "Gray Concrete Fence",
            new BlockTexture(5, 16, 5, 16, 5, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block GreenConcreteFence = new Block(343, "Green Concrete Fence",
            new BlockTexture(6, 16, 6, 16, 6, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block LightBlueConcreteFence = new Block(346, "Light Blue Concrete Fence",
            new BlockTexture(7, 16, 7, 16, 7, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block LightGrayConcreteFence = new Block(349, "Light Gray Concrete Fence",
            new BlockTexture(8, 16, 8, 16, 8, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block LimeConcreteFence = new Block(352, "Lime Concrete Fence",
            new BlockTexture(9, 16, 9, 16, 9, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block MagentaConcreteFence = new Block(355, "Magenta Concrete Fence",
            new BlockTexture(10, 16, 10, 16, 10, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block OrangeConcreteFence = new Block(358, "Orange Concrete Fence",
            new BlockTexture(11, 16, 11, 16, 11, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PinkConcreteFence = new Block(361, "Pink Concrete Fence",
            new BlockTexture(12, 16, 12, 16, 12, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PurpleConcreteFence = new Block(364, "Purple Concrete Fence",
            new BlockTexture(13, 16, 13, 16, 13, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block RedConcreteFence = new Block(367, "Red Concrete Fence",
            new BlockTexture(14, 16, 14, 16, 14, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block WhiteConcreteFence = new Block(370, "White Concrete Fence",
            new BlockTexture(15, 16, 15, 16, 15, 16), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block ObsidianFence = new Block(418, "Obsidian Fence", new BlockTexture(5, 2, 5, 2, 5, 2), true,
            false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block LapisLazulFence = new Block(421, "Lapis Lazul Fence",
            new BlockTexture(0, 20, 0, 20, 0, 20), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block IronFence = new Block(424, "Steel Fence", new BlockTexture(1, 20, 1, 20, 1, 20), true,
            false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block GoldFence = new Block(427, "Gold Fence", new BlockTexture(2, 20, 2, 20, 2, 20), true,
            false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block EmeraldFence = new Block(430, "Emerald Fence", new BlockTexture(3, 20, 3, 20, 3, 20),
            true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block DiamondFence = new Block(433, "Diamond Fence", new BlockTexture(4, 20, 4, 20, 4, 20),
            true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block RedSandstonePillar = new Block(439, "Red Sandstone Pillar",
            new BlockTexture(10, 13, 10, 15, 10, 14), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block StoneBrickPillar = new Block(440, "Stone Brick Pillar",
            new BlockTexture(6, 3, 6, 3, 6, 3), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PalisadeStonePillar = new Block(442, "Palisade Stone Pillar",
            new BlockTexture(6, 0, 6, 0, 5, 0), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PalisadeStone2Pillar = new Block(443, "Palisade Stone 2 Pillar",
            new BlockTexture(5, 13, 5, 13, 5, 13), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block CrackedStonePillar = new Block(444, "Cracked Stone Pillar",
            new BlockTexture(5, 6, 5, 6, 5, 6), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block StoneWithVinesPillar = new Block(445, "Stone with Vines Pillar",
            new BlockTexture(4, 6, 4, 6, 4, 6), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block RedPalisadeSandstonePillar = new Block(448, "Red Palisade Sandstone Pillar",
            new BlockTexture(4, 10, 4, 10, 4, 11), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PalisadeSandstonePillar = new Block(441, "Palisade Sandstone Pillar",
            new BlockTexture(5, 10, 5, 10, 5, 11), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block QuartzPillar = new Block(450, "Quartz Pillar", new BlockTexture(13, 11, 13, 11, 13, 12),
            true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block MarblePillar = new Block(451, "Marble Pillar", new BlockTexture(14, 11, 14, 11, 14, 12),
            true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block CheckerboardChiseledMarble = new Block(463, "Checkerboard Chiseled Marble",
            new BlockTexture(7, 5, 7, 5, 7, 5), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block ChiseledQuartz = new Block(465, "Chiseled Quartz", new BlockTexture(7, 6, 7, 6, 7, 6),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block ChiseledQuartzStairs = new Block(516, "Chiseled Quartz Stairs",
            new BlockTexture(7, 6, 7, 6, 7, 6), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block ChiseledQuartzSlab = new Block(517, "Chiseled Quartz Slab",
            new BlockTexture(7, 6, 7, 6, 7, 6), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block ChiseledQuartzPillar = new Block(518, "Chiseled Quartz Pillar",
            new BlockTexture(7, 6, 7, 6, 7, 6), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block YellowstainedGlassStairs = new Block(473, "Yellow-Stained Glass Stairs",
            new BlockTexture(0, 18, 0, 18, 0, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block YellowstainedGlassSlab = new Block(474, "Yellow-Stained Glass Slab",
            new BlockTexture(0, 18, 0, 18, 0, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlackstainedGlassStairs = new Block(475, "Black-Stained Glass Stairs",
            new BlockTexture(1, 18, 1, 18, 1, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlackstainedGlassSlab = new Block(476, "Black-Stained Glass Slab",
            new BlockTexture(1, 18, 1, 18, 1, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BluestainedGlassStairs = new Block(477, "Blue-Stained Glass Stairs",
            new BlockTexture(2, 18, 2, 18, 2, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BluestainedGlassSlab = new Block(478, "Blue-Stained Glass Slab",
            new BlockTexture(2, 18, 2, 18, 2, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BrownstainedGlassStairs = new Block(479, "Brown-Stained Glass Stairs",
            new BlockTexture(3, 18, 3, 18, 3, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BrownstainedGlassSlab = new Block(480, "Brown-Stained Glass Slab",
            new BlockTexture(3, 18, 3, 18, 3, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CyanstainedGlassStairs = new Block(481, "Cyan-Stained Glass Stairs",
            new BlockTexture(4, 18, 4, 18, 4, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CyanstainedGlassSlab = new Block(482, "Cyan-Stained Glass Slab",
            new BlockTexture(4, 18, 4, 18, 4, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GraystainedGlassStairs = new Block(483, "Gray-Stained Glass Stairs",
            new BlockTexture(5, 18, 5, 18, 5, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GraystainedGlassSlab = new Block(484, "Gray-Stained Glass Slab",
            new BlockTexture(5, 18, 5, 18, 5, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GreenstainedGlassStairs = new Block(485, "Green-Stained Glass Stairs",
            new BlockTexture(6, 18, 6, 18, 6, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GreenstainedGlassSlab = new Block(486, "Green-Stained Glass Slab",
            new BlockTexture(6, 18, 6, 18, 6, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightBluestainedGlassStairs = new Block(487, "Light Blue-Stained Glass Stairs",
            new BlockTexture(7, 18, 7, 18, 7, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightBluestainedGlassSlab = new Block(488, "Light Blue-Stained Glass Slab",
            new BlockTexture(7, 18, 7, 18, 7, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightGraystainedGlassStairs = new Block(489, "Light Gray-Stained Glass Stairs",
            new BlockTexture(8, 18, 8, 18, 8, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightGraystainedGlassSlab = new Block(490, "Light Gray-Stained Glass Slab",
            new BlockTexture(8, 18, 8, 18, 8, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LimestainedGlassStairs = new Block(491, "Lime-Stained Glass Stairs",
            new BlockTexture(9, 18, 9, 18, 9, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LimestainedGlassSlab = new Block(492, "Lime-Stained Glass Slab",
            new BlockTexture(9, 18, 9, 18, 9, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MagentastainedGlassStairs = new Block(493, "Magenta-Stained Glass Stairs",
            new BlockTexture(10, 18, 10, 18, 10, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MagentastainedGlassSlab = new Block(494, "Magenta-Stained Glass Slab",
            new BlockTexture(10, 18, 10, 18, 10, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OrangestainedGlassStairs = new Block(495, "Orange-Stained Glass Stairs",
            new BlockTexture(11, 18, 11, 18, 11, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OrangestainedGlassSlab = new Block(496, "Orange-Stained Glass Slab",
            new BlockTexture(11, 18, 11, 18, 11, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PinkstainedGlassStairs = new Block(497, "Pink-Stained Glass Stairs",
            new BlockTexture(12, 18, 12, 18, 12, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PinkstainedGlassSlab = new Block(498, "Pink-Stained Glass Slab",
            new BlockTexture(12, 18, 12, 18, 12, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PurplestainedGlassStairs = new Block(499, "Purple-Stained Glass Stairs",
            new BlockTexture(13, 18, 13, 18, 13, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PurplestainedGlassSlab = new Block(500, "Purple-Stained Glass Slab",
            new BlockTexture(13, 18, 13, 18, 13, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block RedstainedGlassStairs = new Block(501, "Red-Stained Glass Stairs",
            new BlockTexture(14, 18, 14, 18, 14, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block RedstainedGlassSlab = new Block(502, "Red-Stained Glass Slab",
            new BlockTexture(14, 18, 14, 18, 14, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WhitestainedGlassStairs = new Block(503, "White-Stained Glass Stairs",
            new BlockTexture(15, 18, 15, 18, 15, 18), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WhitestainedGlassSlab = new Block(504, "White-Stained Glass Slab",
            new BlockTexture(15, 18, 15, 18, 15, 18), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CheckerboardChiseledMarbleStairs = new Block(505, "Checkerboard Chiseled Marble Stairs",
            new BlockTexture(7, 5, 7, 5, 7, 5), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CheckerboardChiseledMarbleSlab = new Block(506, "Checkerboard Chiseled Marble Slab",
            new BlockTexture(7, 5, 7, 5, 7, 5), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block ChiseledMarbleStairs = new Block(507, "Chiseled Marble Stairs",
            new BlockTexture(6, 5, 6, 5, 6, 5), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block ChiseledMarbleSlab = new Block(508, "Chiseled Marble Slab",
            new BlockTexture(6, 5, 6, 5, 6, 5), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MarbleTileStairs = new Block(509, "Marble Tile Stairs",
            new BlockTexture(8, 19, 8, 19, 8, 19), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MarbleTileSlab = new Block(510, "Marble Tile Slab", new BlockTexture(8, 19, 8, 19, 8, 19),
            true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MarbleTilePillar = new Block(523, "Marble Tile Pillar",
            new BlockTexture(8, 19, 8, 19, 8, 19), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block GlassPane = new Block(526, "Glass Pane", new BlockTexture(1, 3, 1, 3, 1, 3), true, false,
            false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block YellowstainedGlassPane = new Block(527, "Yellow-Stained Glass Pane",
            new BlockTexture(0, 18, 0, 18, 0, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block BlackstainedGlassPane = new Block(528, "Black-Stained Glass Pane",
            new BlockTexture(1, 18, 1, 18, 1, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block BluestainedGlassPane = new Block(529, "Blue-Stained Glass Pane",
            new BlockTexture(2, 18, 2, 18, 2, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block BrownstainedGlassPane = new Block(530, "Brown-Stained Glass Pane",
            new BlockTexture(3, 18, 3, 18, 3, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block CyanstainedGlassPane = new Block(531, "Cyan-Stained Glass Pane",
            new BlockTexture(4, 18, 4, 18, 4, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block GraystainedGlassPane = new Block(532, "Gray-Stained Glass Pane",
            new BlockTexture(5, 18, 5, 18, 5, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block GreenstainedGlassPane = new Block(533, "Green-Stained Glass Pane",
            new BlockTexture(6, 18, 6, 18, 6, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block LightBluestainedGlassPane = new Block(534, "Light Blue-Stained Glass Pane",
            new BlockTexture(7, 18, 7, 18, 7, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block LightGraystainedGlassPane = new Block(535, "Light Gray-Stained Glass Pane",
            new BlockTexture(8, 18, 8, 18, 8, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block LimestainedGlassPane = new Block(536, "Lime-Stained Glass Pane",
            new BlockTexture(9, 18, 9, 18, 9, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block MagentastainedGlassPane = new Block(537, "Magenta-Stained Glass Pane",
            new BlockTexture(10, 18, 10, 18, 10, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block OrangestainedGlassPane = new Block(538, "Orange-Stained Glass Pane",
            new BlockTexture(11, 18, 11, 18, 11, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block PinkstainedGlassPane = new Block(539, "Pink-Stained Glass Pane",
            new BlockTexture(12, 18, 12, 18, 12, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block PurplestainedGlassPane = new Block(540, "Purple-Stained Glass Pane",
            new BlockTexture(13, 18, 13, 18, 13, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block RedstainedGlassPane = new Block(541, "Red-Stained Glass Pane",
            new BlockTexture(14, 18, 14, 18, 14, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block WhitestainedGlassPane = new Block(542, "White-Stained Glass Pane",
            new BlockTexture(15, 18, 15, 18, 15, 18), true, false, false, (byte) 0, 0, BlockRenderType.PANE);
    public static final Block BambooBlock = new Block(566, "Bamboo Block", new BlockTexture(14, 13, 14, 13, 14, 14),
            true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block BambooWood = new Block(573, "Bamboo Wood", new BlockTexture(15, 14, 15, 14, 15, 14), true,
            true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block MosaicBambooWood = new Block(34, "Mosaic Bamboo Wood",
            new BlockTexture(15, 13, 15, 13, 15, 13), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MarbleTile = new Block(466, "Marble Tile", new BlockTexture(8, 19, 8, 19, 8, 19), true,
            true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block YellowMarbleTile = new Block(568, "Yellow Marble Tile",
            new BlockTexture(0, 22, 0, 22, 0, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlackMarbleTile = new Block(569, "Black Marble Tile",
            new BlockTexture(1, 22, 1, 22, 1, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlueMarbleTile = new Block(467, "Blue Marble Tile", new BlockTexture(2, 22, 2, 22, 2, 22),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BrownMarbleTile = new Block(571, "Brown Marble Tile",
            new BlockTexture(3, 22, 3, 22, 3, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CyanMarbleTile = new Block(572, "Cyan Marble Tile", new BlockTexture(4, 22, 4, 22, 4, 22),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GrayMarbleTile = new Block(470, "Gray Marble Tile", new BlockTexture(5, 22, 5, 22, 5, 22),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GreenMarbleTile = new Block(468, "Green Marble Tile",
            new BlockTexture(6, 22, 6, 22, 6, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PastelblueMarbleTile = new Block(575, "Pastel-Blue Marble Tile",
            new BlockTexture(7, 22, 7, 22, 7, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PastelgreenMarbleTile = new Block(576, "Pastel-Green Marble Tile",
            new BlockTexture(8, 22, 8, 22, 8, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MagentaMarbleTile = new Block(577, "Magenta Marble Tile",
            new BlockTexture(9, 22, 9, 22, 9, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block OrangeMarbleTile = new Block(462, "Orange Marble Tile",
            new BlockTexture(10, 22, 10, 22, 10, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PinkMarbleTile = new Block(579, "Pink Marble Tile",
            new BlockTexture(11, 22, 11, 22, 11, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PurpleMarbleTile = new Block(580, "Purple Marble Tile",
            new BlockTexture(12, 22, 12, 22, 12, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BurgundyMarbleTile = new Block(581, "Burgundy Marble Tile",
            new BlockTexture(13, 22, 13, 22, 13, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PastelredMarbleTile = new Block(582, "Pastel-Red Marble Tile",
            new BlockTexture(14, 22, 14, 22, 14, 22), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block ChiseledMarble = new Block(464, "Chiseled Marble", new BlockTexture(6, 5, 6, 5, 6, 5),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block YellowChiseledMarbleTile = new Block(545, "Yellow Chiseled Marble Tile",
            new BlockTexture(0, 21, 0, 21, 0, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlackChiseledMarbleTile = new Block(553, "Black Chiseled Marble Tile",
            new BlockTexture(1, 21, 1, 21, 1, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlueChiseledMarbleTile = new Block(554, "Blue Chiseled Marble Tile",
            new BlockTexture(2, 21, 2, 21, 2, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BrownChiseledMarbleTile = new Block(555, "Brown Chiseled Marble Tile",
            new BlockTexture(3, 21, 3, 21, 3, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CyanChiseledMarbleTile = new Block(556, "Cyan Chiseled Marble Tile",
            new BlockTexture(4, 21, 4, 21, 4, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GrayChiseledMarbleTile = new Block(557, "Gray Chiseled Marble Tile",
            new BlockTexture(5, 21, 5, 21, 5, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GreenChiseledMarbleTile = new Block(558, "Green Chiseled Marble Tile",
            new BlockTexture(6, 21, 6, 21, 6, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PastelblueChiseledMarbleTile = new Block(559, "Pastel-Blue Chiseled Marble Tile",
            new BlockTexture(7, 21, 7, 21, 7, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PastelgreenChiseledMarbleTile = new Block(560, "Pastel-Green Chiseled Marble Tile",
            new BlockTexture(8, 21, 8, 21, 8, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MagentaChiseledMarbleTile = new Block(561, "Magenta Chiseled Marble Tile",
            new BlockTexture(9, 21, 9, 21, 9, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block OrangeChiseledMarbleTile = new Block(562, "Orange Chiseled Marble Tile",
            new BlockTexture(10, 21, 10, 21, 10, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PinkChiseledMarbleTile = new Block(563, "Pink Chiseled Marble Tile",
            new BlockTexture(11, 21, 11, 21, 11, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PurpleChiseledMarbleTile = new Block(564, "Purple Chiseled Marble Tile",
            new BlockTexture(12, 21, 12, 21, 12, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BurgundyChiseledMarbleTile = new Block(565, "Burgundy Chiseled Marble Tile",
            new BlockTexture(13, 21, 13, 21, 13, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PastelredChiseledMarbleTile = new Block(567, "Pastel-Red Chiseled Marble Tile",
            new BlockTexture(14, 21, 14, 21, 14, 21), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GrayMarbleTileStairs = new Block(511, "Gray Marble Tile Stairs",
            new BlockTexture(5, 22, 5, 22, 5, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GrayMarbleTileSlab = new Block(512, "Gray Marble Tile Slab",
            new BlockTexture(5, 22, 5, 22, 5, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlueMarbleTileStairs = new Block(513, "Blue Marble Tile Stairs",
            new BlockTexture(2, 22, 2, 22, 2, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlueMarbleTileSlab = new Block(514, "Blue Marble Tile Slab",
            new BlockTexture(2, 22, 2, 22, 2, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GreenMarbleTileStairs = new Block(515, "Green Marble Tile Stairs",
            new BlockTexture(6, 22, 6, 22, 6, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GreenMarbleTileSlab = new Block(472, "Green Marble Tile Slab",
            new BlockTexture(6, 22, 6, 22, 6, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OrangeMarbleTileStairs = new Block(471, "Orange Marble Tile Stairs",
            new BlockTexture(10, 22, 10, 22, 10, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OrangeMarbleTileSlab = new Block(469, "Orange Marble Tile Slab",
            new BlockTexture(10, 22, 10, 22, 10, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GrayMarbleTilePillar = new Block(519, "Gray Marble Tile Pillar",
            new BlockTexture(5, 22, 5, 22, 5, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BlueMarbleTilePillar = new Block(520, "Blue Marble Tile Pillar",
            new BlockTexture(2, 22, 2, 22, 2, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block GreenMarbleTilePillar = new Block(521, "Green Marble Tile Pillar",
            new BlockTexture(6, 22, 6, 22, 6, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block OrangeMarbleTilePillar = new Block(522, "Orange Marble Tile Pillar",
            new BlockTexture(10, 22, 10, 22, 10, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block YellowMarbleTileStairs = new Block(586, "Yellow Marble Tile Stairs",
            new BlockTexture(0, 22, 0, 22, 0, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block YellowMarbleTileSlab = new Block(587, "Yellow Marble Tile Slab",
            new BlockTexture(0, 22, 0, 22, 0, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block YellowMarbleTilePillar = new Block(588, "Yellow Marble Tile Pillar",
            new BlockTexture(0, 22, 0, 22, 0, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BlackMarbleTileStairs = new Block(589, "Black Marble Tile Stairs",
            new BlockTexture(1, 22, 1, 22, 1, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlackMarbleTileSlab = new Block(590, "Black Marble Tile Slab",
            new BlockTexture(1, 22, 1, 22, 1, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlackMarbleTilePillar = new Block(591, "Black Marble Tile Pillar",
            new BlockTexture(1, 22, 1, 22, 1, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BrownMarbleTileStairs = new Block(595, "Brown Marble Tile Stairs",
            new BlockTexture(3, 22, 3, 22, 3, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BrownMarbleTileSlab = new Block(596, "Brown Marble Tile Slab",
            new BlockTexture(3, 22, 3, 22, 3, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BrownMarbleTilePillar = new Block(597, "Brown Marble Tile Pillar",
            new BlockTexture(3, 22, 3, 22, 3, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block CyanMarbleTileStairs = new Block(598, "Cyan Marble Tile Stairs",
            new BlockTexture(4, 22, 4, 22, 4, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CyanMarbleTileSlab = new Block(599, "Cyan Marble Tile Slab",
            new BlockTexture(4, 22, 4, 22, 4, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CyanMarbleTilePillar = new Block(600, "Cyan Marble Tile Pillar",
            new BlockTexture(4, 22, 4, 22, 4, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PastelblueMarbleTileStairs = new Block(607, "Pastel-Blue Marble Tile Stairs",
            new BlockTexture(7, 22, 7, 22, 7, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PastelblueMarbleTileSlab = new Block(608, "Pastel-Blue Marble Tile Slab",
            new BlockTexture(7, 22, 7, 22, 7, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PastelblueMarbleTilePillar = new Block(609, "Pastel-Blue Marble Tile Pillar",
            new BlockTexture(7, 22, 7, 22, 7, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PastelgreenMarbleTileStairs = new Block(610, "Pastel-Green Marble Tile Stairs",
            new BlockTexture(8, 22, 8, 22, 8, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PastelgreenMarbleTileSlab = new Block(611, "Pastel-Green Marble Tile Slab",
            new BlockTexture(8, 22, 8, 22, 8, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PastelgreenMarbleTilePillar = new Block(612, "Pastel-Green Marble Tile Pillar",
            new BlockTexture(8, 22, 8, 22, 8, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block MagentaMarbleTileStairs = new Block(613, "Magenta Marble Tile Stairs",
            new BlockTexture(9, 22, 9, 22, 9, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MagentaMarbleTileSlab = new Block(614, "Magenta Marble Tile Slab",
            new BlockTexture(9, 22, 9, 22, 9, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MagentaMarbleTilePillar = new Block(615, "Magenta Marble Tile Pillar",
            new BlockTexture(9, 22, 9, 22, 9, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PinkMarbleTileStairs = new Block(619, "Pink Marble Tile Stairs",
            new BlockTexture(11, 22, 11, 22, 11, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PinkMarbleTileSlab = new Block(620, "Pink Marble Tile Slab",
            new BlockTexture(11, 22, 11, 22, 11, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PinkMarbleTilePillar = new Block(621, "Pink Marble Tile Pillar",
            new BlockTexture(11, 22, 11, 22, 11, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PurpleMarbleTileStairs = new Block(622, "Purple Marble Tile Stairs",
            new BlockTexture(12, 22, 12, 22, 12, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PurpleMarbleTileSlab = new Block(623, "Purple Marble Tile Slab",
            new BlockTexture(12, 22, 12, 22, 12, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PurpleMarbleTilePillar = new Block(624, "Purple Marble Tile Pillar",
            new BlockTexture(12, 22, 12, 22, 12, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BurgundyMarbleTileStairs = new Block(625, "Burgundy Marble Tile Stairs",
            new BlockTexture(13, 22, 13, 22, 13, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BurgundyMarbleTileSlab = new Block(626, "Burgundy Marble Tile Slab",
            new BlockTexture(13, 22, 13, 22, 13, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BurgundyMarbleTilePillar = new Block(627, "Burgundy Marble Tile Pillar",
            new BlockTexture(13, 22, 13, 22, 13, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PastelredMarbleTileStairs = new Block(628, "Pastel-Red Marble Tile Stairs",
            new BlockTexture(14, 22, 14, 22, 14, 22), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PastelredMarbleTileSlab = new Block(629, "Pastel-Red Marble Tile Slab",
            new BlockTexture(14, 22, 14, 22, 14, 22), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PastelredMarbleTilePillar = new Block(630, "Pastel-Red Marble Tile Pillar",
            new BlockTexture(14, 22, 14, 22, 14, 22), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block YellowChiseledMarbleTileStairs = new Block(631, "Yellow Chiseled Marble Tile Stairs",
            new BlockTexture(0, 21, 0, 21, 0, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block YellowChiseledMarbleTileSlab = new Block(632, "Yellow Chiseled Marble Tile Slab",
            new BlockTexture(0, 21, 0, 21, 0, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block YellowChiseledMarbleTilePillar = new Block(633, "Yellow Chiseled Marble Tile Pillar",
            new BlockTexture(0, 21, 0, 21, 0, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BlackChiseledMarbleTileStairs = new Block(634, "Black Chiseled Marble Tile Stairs",
            new BlockTexture(1, 21, 1, 21, 1, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlackChiseledMarbleTileSlab = new Block(635, "Black Chiseled Marble Tile Slab",
            new BlockTexture(1, 21, 1, 21, 1, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlackChiseledMarbleTilePillar = new Block(636, "Black Chiseled Marble Tile Pillar",
            new BlockTexture(1, 21, 1, 21, 1, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BlueChiseledMarbleTileStairs = new Block(637, "Blue Chiseled Marble Tile Stairs",
            new BlockTexture(2, 21, 2, 21, 2, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlueChiseledMarbleTileSlab = new Block(638, "Blue Chiseled Marble Tile Slab",
            new BlockTexture(2, 21, 2, 21, 2, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlueChiseledMarbleTilePillar = new Block(639, "Blue Chiseled Marble Tile Pillar",
            new BlockTexture(2, 21, 2, 21, 2, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BrownChiseledMarbleTileStairs = new Block(640, "Brown Chiseled Marble Tile Stairs",
            new BlockTexture(3, 21, 3, 21, 3, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BrownChiseledMarbleTileSlab = new Block(641, "Brown Chiseled Marble Tile Slab",
            new BlockTexture(3, 21, 3, 21, 3, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BrownChiseledMarbleTilePillar = new Block(642, "Brown Chiseled Marble Tile Pillar",
            new BlockTexture(3, 21, 3, 21, 3, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block CyanChiseledMarbleTileStairs = new Block(643, "Cyan Chiseled Marble Tile Stairs",
            new BlockTexture(4, 21, 4, 21, 4, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CyanChiseledMarbleTileSlab = new Block(644, "Cyan Chiseled Marble Tile Slab",
            new BlockTexture(4, 21, 4, 21, 4, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CyanChiseledMarbleTilePillar = new Block(645, "Cyan Chiseled Marble Tile Pillar",
            new BlockTexture(4, 21, 4, 21, 4, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block GrayChiseledMarbleTileStairs = new Block(646, "Gray Chiseled Marble Tile Stairs",
            new BlockTexture(5, 21, 5, 21, 5, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GrayChiseledMarbleTileSlab = new Block(647, "Gray Chiseled Marble Tile Slab",
            new BlockTexture(5, 21, 5, 21, 5, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GrayChiseledMarbleTilePillar = new Block(648, "Gray Chiseled Marble Tile Pillar",
            new BlockTexture(5, 21, 5, 21, 5, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block GreenChiseledMarbleTileStairs = new Block(649, "Green Chiseled Marble Tile Stairs",
            new BlockTexture(6, 21, 6, 21, 6, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GreenChiseledMarbleTileSlab = new Block(650, "Green Chiseled Marble Tile Slab",
            new BlockTexture(6, 21, 6, 21, 6, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GreenChiseledMarbleTilePillar = new Block(651, "Green Chiseled Marble Tile Pillar",
            new BlockTexture(6, 21, 6, 21, 6, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PastelblueChiseledMarbleTileStairs = new Block(652,
            "Pastel-Blue Chiseled Marble Tile Stairs", new BlockTexture(7, 21, 7, 21, 7, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PastelblueChiseledMarbleTileSlab = new Block(653, "Pastel-Blue Chiseled Marble Tile Slab",
            new BlockTexture(7, 21, 7, 21, 7, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PastelblueChiseledMarbleTilePillar = new Block(654,
            "Pastel-Blue Chiseled Marble Tile Pillar", new BlockTexture(7, 21, 7, 21, 7, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PastelgreenChiseledMarbleTileStairs = new Block(655,
            "Pastel-Green Chiseled Marble Tile Stairs", new BlockTexture(8, 21, 8, 21, 8, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PastelgreenChiseledMarbleTileSlab = new Block(656,
            "Pastel-Green Chiseled Marble Tile Slab", new BlockTexture(8, 21, 8, 21, 8, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PastelgreenChiseledMarbleTilePillar = new Block(657,
            "Pastel-Green Chiseled Marble Tile Pillar", new BlockTexture(8, 21, 8, 21, 8, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block MagentaChiseledMarbleTileStairs = new Block(658, "Magenta Chiseled Marble Tile Stairs",
            new BlockTexture(9, 21, 9, 21, 9, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MagentaChiseledMarbleTileSlab = new Block(659, "Magenta Chiseled Marble Tile Slab",
            new BlockTexture(9, 21, 9, 21, 9, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MagentaChiseledMarbleTilePillar = new Block(660, "Magenta Chiseled Marble Tile Pillar",
            new BlockTexture(9, 21, 9, 21, 9, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block OrangeChiseledMarbleTileStairs = new Block(661, "Orange Chiseled Marble Tile Stairs",
            new BlockTexture(10, 21, 10, 21, 10, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OrangeChiseledMarbleTileSlab = new Block(662, "Orange Chiseled Marble Tile Slab",
            new BlockTexture(10, 21, 10, 21, 10, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OrangeChiseledMarbleTilePillar = new Block(663, "Orange Chiseled Marble Tile Pillar",
            new BlockTexture(10, 21, 10, 21, 10, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PinkChiseledMarbleTileStairs = new Block(664, "Pink Chiseled Marble Tile Stairs",
            new BlockTexture(11, 21, 11, 21, 11, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PinkChiseledMarbleTileSlab = new Block(665, "Pink Chiseled Marble Tile Slab",
            new BlockTexture(11, 21, 11, 21, 11, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PinkChiseledMarbleTilePillar = new Block(594, "Pink Chiseled Marble Tile Pillar",
            new BlockTexture(11, 21, 11, 21, 11, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PurpleChiseledMarbleTileStairs = new Block(601, "Purple Chiseled Marble Tile Stairs",
            new BlockTexture(12, 21, 12, 21, 12, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PurpleChiseledMarbleTileSlab = new Block(602, "Purple Chiseled Marble Tile Slab",
            new BlockTexture(12, 21, 12, 21, 12, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PurpleChiseledMarbleTilePillar = new Block(603, "Purple Chiseled Marble Tile Pillar",
            new BlockTexture(12, 21, 12, 21, 12, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block BurgundyChiseledMarbleTileStairs = new Block(604, "Burgundy Chiseled Marble Tile Stairs",
            new BlockTexture(13, 21, 13, 21, 13, 21), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BurgundyChiseledMarbleTileSlab = new Block(605, "Burgundy Chiseled Marble Tile Slab",
            new BlockTexture(13, 21, 13, 21, 13, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BurgundyChiseledMarbleTilePillar = new Block(606, "Burgundy Chiseled Marble Tile Pillar",
            new BlockTexture(13, 21, 13, 21, 13, 21), true, false, false, (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block PastelredChiseledMarbleTileStairs = new Block(616,
            "Pastel-Red Chiseled Marble Tile Stairs", new BlockTexture(14, 21, 14, 21, 14, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PastelredChiseledMarbleTileSlab = new Block(617, "Pastel-Red Chiseled Marble Tile Slab",
            new BlockTexture(14, 21, 14, 21, 14, 21), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PastelredChiseledMarbleTilePillar = new Block(618,
            "Pastel-Red Chiseled Marble Tile Pillar", new BlockTexture(14, 21, 14, 21, 14, 21), true, false, false,
            (byte) 0, 0, BlockRenderType.PILLAR);
    public static final Block HoneycombBlock = new Block(33, "Honeycomb Block", new BlockTexture(8, 6, 8, 6, 8, 6),
            true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block YellowStainedWood = new Block(584, "Yellow Stained Wood",
            new BlockTexture(0, 23, 0, 23, 0, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlackStainedWood = new Block(585, "Black Stained Wood",
            new BlockTexture(1, 23, 1, 23, 1, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlueStainedWood = new Block(254, "Blue Stained Wood",
            new BlockTexture(2, 23, 2, 23, 2, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block CyanStainedWood = new Block(593, "Cyan Stained Wood",
            new BlockTexture(4, 23, 4, 23, 4, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GrayStainedWood = new Block(666, "Gray Stained Wood",
            new BlockTexture(5, 23, 5, 23, 5, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block GreenStainedWood = new Block(667, "Green Stained Wood",
            new BlockTexture(6, 23, 6, 23, 6, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LightBlueStainedWood = new Block(668, "Light Blue Stained Wood",
            new BlockTexture(7, 23, 7, 23, 7, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block LimeStainedWood = new Block(669, "Lime Stained Wood",
            new BlockTexture(8, 23, 8, 23, 8, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block MagentaStainedWood = new Block(670, "Magenta Stained Wood",
            new BlockTexture(9, 23, 9, 23, 9, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block OrangeStainedWood = new Block(671, "Orange Stained Wood",
            new BlockTexture(10, 23, 10, 23, 10, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PinkStainedWood = new Block(672, "Pink Stained Wood",
            new BlockTexture(11, 23, 11, 23, 11, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block PurpleStainedWood = new Block(673, "Purple Stained Wood",
            new BlockTexture(12, 23, 12, 23, 12, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block RedStainedWood = new Block(129, "Red Stained Wood",
            new BlockTexture(13, 23, 13, 23, 13, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block WhiteStainedWood = new Block(675, "White Stained Wood",
            new BlockTexture(14, 23, 14, 23, 14, 23), true, true, false, (byte) 0, 0, DEFAULT_BLOCK_TYPE_ID);
    public static final Block IceBlockStairs = new Block(679, "Ice Block Stairs", new BlockTexture(3, 4, 3, 4, 3, 4),
            true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block IceBlockSlab = new Block(680, "Ice Block Slab", new BlockTexture(3, 4, 3, 4, 3, 4), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MosaicBambooWoodStairs = new Block(682, "Mosaic Bamboo Wood Stairs",
            new BlockTexture(15, 13, 15, 13, 15, 13), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MosaicBambooWoodSlab = new Block(683, "Mosaic Bamboo Wood Slab",
            new BlockTexture(15, 13, 15, 13, 15, 13), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MosaicBambooWoodFence = new Block(684, "Mosaic Bamboo Wood Fence",
            new BlockTexture(15, 13, 15, 13, 15, 13), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block YellowStainedWoodStairs = new Block(685, "Yellow Stained Wood Stairs",
            new BlockTexture(0, 23, 0, 23, 0, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block YellowStainedWoodSlab = new Block(686, "Yellow Stained Wood Slab",
            new BlockTexture(0, 23, 0, 23, 0, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block YellowStainedWoodFence = new Block(687, "Yellow Stained Wood Fence",
            new BlockTexture(0, 23, 0, 23, 0, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BlackStainedWoodStairs = new Block(688, "Black Stained Wood Stairs",
            new BlockTexture(1, 23, 1, 23, 1, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlackStainedWoodSlab = new Block(689, "Black Stained Wood Slab",
            new BlockTexture(1, 23, 1, 23, 1, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlackStainedWoodFence = new Block(690, "Black Stained Wood Fence",
            new BlockTexture(1, 23, 1, 23, 1, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BlueStainedWoodStairs = new Block(691, "Blue Stained Wood Stairs",
            new BlockTexture(2, 23, 2, 23, 2, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BlueStainedWoodSlab = new Block(692, "Blue Stained Wood Slab",
            new BlockTexture(2, 23, 2, 23, 2, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BlueStainedWoodFence = new Block(693, "Blue Stained Wood Fence",
            new BlockTexture(2, 23, 2, 23, 2, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block CyanStainedWoodStairs = new Block(694, "Cyan Stained Wood Stairs",
            new BlockTexture(4, 23, 4, 23, 4, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block CyanStainedWoodSlab = new Block(695, "Cyan Stained Wood Slab",
            new BlockTexture(4, 23, 4, 23, 4, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block CyanStainedWoodFence = new Block(696, "Cyan Stained Wood Fence",
            new BlockTexture(4, 23, 4, 23, 4, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block GrayStainedWoodStairs = new Block(697, "Gray Stained Wood Stairs",
            new BlockTexture(5, 23, 5, 23, 5, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GrayStainedWoodSlab = new Block(698, "Gray Stained Wood Slab",
            new BlockTexture(5, 23, 5, 23, 5, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GrayStainedWoodFence = new Block(699, "Gray Stained Wood Fence",
            new BlockTexture(5, 23, 5, 23, 5, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block GreenStainedWoodStairs = new Block(700, "Green Stained Wood Stairs",
            new BlockTexture(6, 23, 6, 23, 6, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GreenStainedWoodSlab = new Block(701, "Green Stained Wood Slab",
            new BlockTexture(6, 23, 6, 23, 6, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GreenStainedWoodFence = new Block(702, "Green Stained Wood Fence",
            new BlockTexture(6, 23, 6, 23, 6, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block LightBlueStainedWoodStairs = new Block(703, "Light Blue Stained Wood Stairs",
            new BlockTexture(7, 23, 7, 23, 7, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LightBlueStainedWoodSlab = new Block(704, "Light Blue Stained Wood Slab",
            new BlockTexture(7, 23, 7, 23, 7, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LightBlueStainedWoodFence = new Block(705, "Light Blue Stained Wood Fence",
            new BlockTexture(7, 23, 7, 23, 7, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block LimeStainedWoodStairs = new Block(706, "Lime Stained Wood Stairs",
            new BlockTexture(8, 23, 8, 23, 8, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block LimeStainedWoodSlab = new Block(707, "Lime Stained Wood Slab",
            new BlockTexture(8, 23, 8, 23, 8, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block LimeStainedWoodFence = new Block(708, "Lime Stained Wood Fence",
            new BlockTexture(8, 23, 8, 23, 8, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block MagentaStainedWoodStairs = new Block(709, "Magenta Stained Wood Stairs",
            new BlockTexture(9, 23, 9, 23, 9, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block MagentaStainedWoodSlab = new Block(710, "Magenta Stained Wood Slab",
            new BlockTexture(9, 23, 9, 23, 9, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block MagentaStainedWoodFence = new Block(711, "Magenta Stained Wood Fence",
            new BlockTexture(9, 23, 9, 23, 9, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block OrangeStainedWoodStairs = new Block(712, "Orange Stained Wood Stairs",
            new BlockTexture(10, 23, 10, 23, 10, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block OrangeStainedWoodSlab = new Block(713, "Orange Stained Wood Slab",
            new BlockTexture(10, 23, 10, 23, 10, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block OrangeStainedWoodFence = new Block(714, "Orange Stained Wood Fence",
            new BlockTexture(10, 23, 10, 23, 10, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PinkStainedWoodStairs = new Block(715, "Pink Stained Wood Stairs",
            new BlockTexture(11, 23, 11, 23, 11, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PinkStainedWoodSlab = new Block(716, "Pink Stained Wood Slab",
            new BlockTexture(11, 23, 11, 23, 11, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PinkStainedWoodFence = new Block(717, "Pink Stained Wood Fence",
            new BlockTexture(11, 23, 11, 23, 11, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block PurpleStainedWoodStairs = new Block(718, "Purple Stained Wood Stairs",
            new BlockTexture(12, 23, 12, 23, 12, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block PurpleStainedWoodSlab = new Block(719, "Purple Stained Wood Slab",
            new BlockTexture(12, 23, 12, 23, 12, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block PurpleStainedWoodFence = new Block(720, "Purple Stained Wood Fence",
            new BlockTexture(12, 23, 12, 23, 12, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block RedStainedWoodStairs = new Block(721, "Red Stained Wood Stairs",
            new BlockTexture(13, 23, 13, 23, 13, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block RedStainedWoodSlab = new Block(722, "Red Stained Wood Slab",
            new BlockTexture(13, 23, 13, 23, 13, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block RedStainedWoodFence = new Block(723, "Red Stained Wood Fence",
            new BlockTexture(13, 23, 13, 23, 13, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block WhiteStainedWoodStairs = new Block(724, "White Stained Wood Stairs",
            new BlockTexture(14, 23, 14, 23, 14, 23), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WhiteStainedWoodSlab = new Block(725, "White Stained Wood Slab",
            new BlockTexture(14, 23, 14, 23, 14, 23), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WhiteStainedWoodFence = new Block(726, "White Stained Wood Fence",
            new BlockTexture(14, 23, 14, 23, 14, 23), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block BambooWoodStairs = new Block(208, "Bamboo Wood Stairs",
            new BlockTexture(15, 14, 15, 14, 15, 14), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block BambooWoodSlab = new Block(258, "Bamboo Wood Slab",
            new BlockTexture(15, 14, 15, 14, 15, 14), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block BambooWoodFence = new Block(406, "Bamboo Wood Fence",
            new BlockTexture(15, 14, 15, 14, 15, 14), true, false, false, (byte) 0, 0, BlockRenderType.FENCE);
    public static final Block HoneycombBlockStairs = new Block(147, "Honeycomb Block Stairs",
            new BlockTexture(8, 6, 8, 6, 8, 6), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block HoneycombBlockSlab = new Block(199, "Honeycomb Block Slab",
            new BlockTexture(8, 6, 8, 6, 8, 6), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block WhiteSpaceTile = new Block(727, "White Space Tile", new BlockTexture(7, 9, 7, 9, 7, 9),
            true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block SolarPanel = new Block(681, "Solar Panel", new BlockTexture(9, 19, 9, 20, 9, 20), true,
            false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GraySpaceTile = new Block(728, "Gray Space Tile", new BlockTexture(8, 9, 8, 9, 8, 9),
            true, true, false, (byte) 0, 0, BlockRenderType.ORIENTABLE_BLOCK);
    public static final Block WhiteSpaceTileStairs = new Block(674, "White Space Tile Stairs",
            new BlockTexture(7, 9, 7, 9, 7, 9), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block WhiteSpaceTileSlab = new Block(729, "White Space Tile Slab",
            new BlockTexture(7, 9, 7, 9, 7, 9), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);
    public static final Block GraySpaceTileStairs = new Block(730, "Gray Space Tile Stairs",
            new BlockTexture(8, 9, 8, 9, 8, 9), true, false, false, (byte) 0, 0, BlockRenderType.STAIRS);
    public static final Block GraySpaceTileSlab = new Block(731, "Gray Space Tile Slab",
            new BlockTexture(8, 9, 8, 9, 8, 9), true, false, false, (byte) 0, 0, BlockRenderType.SLAB);

    public static final Block IronOre = new Block(231, "Steel Ore", new BlockTexture(1, 19, 1, 19, 1, 19), true, true,
            false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID) {
        public byte maxStackSize() {
            return 64;
        }
    };
    public static final Block CoalOre = new Block(45, "Coal Ore", new BlockTexture(5, 19, 5, 19, 5, 19), true, true,
            false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID) {
        public byte maxStackSize() {
            return 64;
        }
    };
    public static final Block LapisLazulOre = new Block(228, "Lapis Lazul Ore", new BlockTexture(0, 19, 0, 19, 0, 19),
            true, true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID) {
        public byte maxStackSize() {
            return 64;
        }
    };
    public static final Block DiamondOre = new Block(99, "Diamond Ore", new BlockTexture(4, 19, 4, 19, 4, 19), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID) {
        public byte maxStackSize() {
            return 64;
        }
    };
    public static final Block EmeraldOre = new Block(237, "Emerald Ore", new BlockTexture(3, 19, 3, 19, 3, 19), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID) {
        public byte maxStackSize() {
            return 64;
        }
    };
    public static final Block GoldOre = new Block(234, "Gold Ore", new BlockTexture(2, 19, 2, 19, 2, 19), true, true,
            false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID) {
        public byte maxStackSize() {
            return 64;
        }
    };

    public static final Block LilyPad = new Block(154, "Lily Pad", new BlockTexture(1, 5, 1, 5, 1, 5),
            BlockRenderType.FLOOR);
    public static final Block SeaGrass = new Block(253, "Sea Grass", new BlockTexture(22, 15, 22, 15, 22, 15),
            BlockRenderType.SPRITE) {
        @Override
        public int getAnimationLength() {
            return 8;
        }
    };
    public static final Block BLOCK_CACTUS = new Block(109, "Cactus", new BlockTexture(5, 4, 7, 4, 6, 4), true, false,
            false, (byte) 1, 1, BlockRenderType.PILLAR);
    public static final Block OakFence = new Block(148, "Oak Fence", new BlockTexture(6, 12, 6, 12, 6, 12), true, false,
            false, (byte) 1, 1, BlockRenderType.FENCE);
    public static final Block DarkOakFence = new Block(195, "Dark Oak Fence", new BlockTexture(6, 11, 6, 11, 6, 11),
            true, false, false, (byte) 1, 1, BlockRenderType.FENCE);
    public static final Block BirchFence = new Block(149, "Birch Fence", new BlockTexture(6, 13, 6, 13, 6, 13), true,
            false, false, (byte) 1, 1, BlockRenderType.FENCE);
    public static final Block AcaciaFence = new Block(249, "Acacia Fence", new BlockTexture(7, 11, 7, 11, 7, 11), true,
            false, false, (byte) 1, 1, BlockRenderType.FENCE);
    public static final Block Sandstone = new Block(17, "Sandstone", new BlockTexture(11, 13, 11, 15, 11, 14), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block RedSandstone = new Block(111, "Red Sandstone", new BlockTexture(10, 13, 10, 15, 10, 14),
            true, true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BlockMiniCactus = new Plant(123, "Mini Cactus", new BlockTexture(7, 3, 7, 3, 7, 3),
            BlockRenderType.SPRITE);
    public static final Block Mushroom = new Block(124, "Mushroom", new BlockTexture(13, 1, 13, 1, 13, 1),
            BlockRenderType.SPRITE);
    public static final Block Mushroom2 = new Block(125, "Mushroom 2", new BlockTexture(12, 1, 12, 1, 12, 1),
            BlockRenderType.SPRITE);
    public static final Block OakPlanks = new Block(130, "Oak Planks", new BlockTexture(6, 12, 6, 12, 6, 12), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block JunglePlanks = new Block(30, "Jungle Planks", new BlockTexture(6, 10, 6, 10, 6, 10), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block SprucePlanks = new Block(57, "Spruce Planks", new BlockTexture(6, 11, 6, 11, 6, 11), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block BirchPlanks = new Block(128, "Birch Planks", new BlockTexture(6, 13, 6, 13, 6, 13), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block AcaciaPlanks = new Block(63, "Acacia Planks", new BlockTexture(7, 11, 7, 11, 7, 11), true,
            true, false, (byte) 1, 1, DEFAULT_BLOCK_TYPE_ID);
    public static final Block AmethystCrystal = new Block(64, "Amethyst Crystal", new BlockTexture(17, 2, 17, 2, 17, 2),
            BlockRenderType.SPRITE);
    public static final Block RubyCrystal = new Block(255, "Ruby Crystal", new BlockTexture(18, 1, 18, 1, 18, 1),
            BlockRenderType.SPRITE);
    public static final Block JadeCrystal = new Block(256, "Jade Crystal", new BlockTexture(19, 1, 19, 1, 19, 1),
            BlockRenderType.SPRITE);
    public static final Block AquamarineCrystal = new Block(257, "Aquamarine Crystal",
            new BlockTexture(20, 1, 20, 1, 20, 1), BlockRenderType.SPRITE);

    public static final Engine BLOCK_ENGINE_30HP = new Engine(741, 20);
    public static final Engine BLOCK_ENGINE_60HP = new Engine(51, 30);
    public static final Engine BLOCK_ENGINE_120HP = new Engine(106, 55);
    public static final Engine BLOCK_ENGINE_240HP = new Engine(744, 70);

    public static final Block BLOCK_WHEEL = new WheelBlock(743, "Wheel", new BlockTexture(
            4, 25, 5, 25), BlockRenderType.WHEEL) {
        public void init() {
            tags.add("vehicle");
        }
    };
    public static final Block BLOCK_WHEEL_HALF = new WheelBlock(745, "Wheel Half", new BlockTexture(
            4, 25, 5, 25), BlockRenderType.WHEEL_HALF) {
        public void init() {
            tags.add("vehicle");
        }
    };

    public static final Block BOYANCY_BASE = new WheelBlock(751, "Boyancy Base",
            new BlockTexture(2, 26, 2, 26), DEFAULT_BLOCK_TYPE_ID) {
        public void init() {
            tags.add("vehicle");
        }
    };

    // Vehicle components
    public static final Block PassengerSeat = new Block(742, "Passenger Seat", new BlockTexture(2, 25, 2, 27), false,
            BlockRenderType.SLAB) {
        public void init() {
            setIconAtlasPosition(0, 9);
            tags.add("vehicle");
        }
    };
    public static final Block BLOCK_DRIVERS_SEAT = new DriversSeat();
    public static final Block MetalGrate = new Block(164, "Metal Grate", new BlockTexture(3, 25, 3, 24), false);
    public static final Block BLOCK_JET_THRUSTER = new Block(746, "Jet Thruster", new BlockTexture(0, 26, 1, 26),
            BlockRenderType.ORIENTABLE_BLOCK) {
        public void init() {
            tags.add("vehicle");
        }
    };

    public static final Block BLOCK_WATER_PROPELLER = new Block(
            748, "Water Turbine",
            new BlockTexture(3, 26, 4, 26),
            BlockRenderType.ORIENTABLE_BLOCK) {
        public void init() {
            tags.add("vehicle");
        }
    };

    public static final HelecopterBladeBlock BLOCK_LARGE_HELICOPTER_BLADE = new HelecopterBladeBlock(749, "Large Helicopter Blade", 17);
    public static final HelecopterBladeBlock BLOCK_SMALL_HELICOPTER_BLADE = new HelecopterBladeBlock(750, "Small Helicopter Blade", 9);
    public static final Block BLOCK_SILVER_BRICK = new Block(747, "Silver Brick", new BlockTexture(4, 24), true);

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="entities">
    public static final DoorEntityLink OAK_DOOR = new DoorEntityLink(0, "Oak Door", "oak/oak.png", 9);
    public static final DoorEntityLink OAK_DOOR2 = new DoorEntityLink(62, "Oak Door 2", "oak/oak 2.png", 6);
    public static final DoorEntityLink OAK_DOOR3 = new DoorEntityLink(148, "Renaissance Oak Door",
            "oak/oak renaissance.png", 17);

    public static final DoorEntityLink ACACIA_DOOR = new DoorEntityLink(69, "Acacia Door 2", "acacia/acacia2.png", 8);
    public static final DoorEntityLink ACACIA_DOOR2 = new DoorEntityLink(5, "Acacia Door", "acacia/acacia.png", 5);

    public static final DoorEntityLink SPRUCE_DOOR = new DoorEntityLink(149, "Spruce Door 2", "spruce/spruce 2.png", 6);
    public static final DoorEntityLink SPRUCE_DOOR2 = new DoorEntityLink(3, "Spruce Door", "spruce/spruce.png", 3);

    public static final DoorEntityLink RED_DOOR = new DoorEntityLink(122, "Cladded Red Door", "red/cladded.png", 13);
    public static final DoorEntityLink RED_DOOR2 = new DoorEntityLink(1, "Red Door", "red/red.png", 1);

    public static final DoorEntityLink BIRCH_DOOR = new DoorEntityLink(4, "Birch Door", "birch/birch.png", 4);
    public static final DoorEntityLink BIRCH_DOOR2 = new DoorEntityLink(150, "Cladded Birch Door", "birch/cladded.png",
            14);

    public static final DoorEntityLink JUNGLE_DOOR = new DoorEntityLink(2, "Jungle Door", "jungle/jungle.png", 2);
    public static final DoorEntityLink JUNGLE_DOOR2 = new DoorEntityLink(144, "Jungle Door 2", "jungle/jungle 2.png",
            18, 1);

    public static final DoorEntityLink GLASS_DOOR_LINK = new DoorEntityLink(121, "Glass Door", "glass.png", 11);
    public static final DoorEntityLink BAMBOO_DOOR = new DoorEntityLink(123, "Bamboo Door", "bamboo/bamboo.png", 15);
    public static final DoorEntityLink BAMBOO_DOOR_2 = new DoorEntityLink(143, "Bamboo Door 2", "bamboo/bamboo 2.png",
            19, 1);

    public static final DoorEntityLink WHITE_DOOR = new DoorEntityLink(124, "Italian White Door (Windows)",
            "white/italian windows.png", 16);
    public static final DoorEntityLink WHITE_DOOR2 = new DoorEntityLink(134, "White Door", "white/white.png", 16);

    public static final DoorEntityLink WHITE_DOOR3 = new DoorEntityLink(127, "Italian White Door", "white/italian.png",
            0, 1);
    public static final DoorEntityLink WHITE_DOOR4 = new DoorEntityLink(125, "French White Door (Windows)",
            "white/french windows.png", 16);
    public static final DoorEntityLink WHITE_DOOR5 = new DoorEntityLink(126, "French White Door", "white/french.png", 0,
            1);
    public static final DoorEntityLink WHITE_DOOR6 = new DoorEntityLink(135, "Cladded White Door", "white/cladded.png",
            12);
    public static final DoorEntityLink WHITE_OVAL_DOOR = new DoorEntityLink(142, "Oval White Door", "white/oval.png",
            18);

    public static final DoorEntityLink WEB_DOOR = new DoorEntityLink(147, "Web Door", "web.png", 19);
    public static final DoorEntityLink WARPED_DOOR_LINK = new DoorEntityLink(64, "Blue Door", "warped.png", 10);
    public static final DoorEntityLink STEEL_DOOR = new DoorEntityLink(57, "Steel Door", "steel.png", 7);

    public static final DoorEntityLink GRAY_SPACE_DOOR_2 = new DoorEntityLink(153, "Gray Space Door 2",
            "space/gray 2.png", 24, 1);
    public static final DoorEntityLink WHITE_SPACE_DOOR_2 = new DoorEntityLink(154, "White Space Door 2",
            "space/white 2.png", 23, 1);
    public static final DoorEntityLink GRAY_SPACE_DOOR_2_PANELS = new DoorEntityLink(155, "Gray Space Door 2 (Panels)",
            "space/gray 2 control-panel.png", 24, 1);
    public static final DoorEntityLink WHITE_SPACE_DOOR_2_PANELS = new DoorEntityLink(156,
            "White Space Door 2 (Panels)", "space/white 2 control-panel.png", 23, 1);

    public static final DoorEntityLink GRAY_SPACE_DOOR = new DoorEntityLink(151, "Gray Space Door", "space/gray 1.png",
            24, 0);
    public static final DoorEntityLink WHITE_SPACE_DOOR = new DoorEntityLink(152, "White Space Door",
            "space/white 1.png", 23, 0);

    public static final DoorEntityLink RED_PARTY_DOOR = new DoorEntityLink(157, "Red Party Door", "party/party red.png",
            22, 1);
    public static final DoorEntityLink YELLOW_PARTY_DOOR = new DoorEntityLink(158, "Yellow Party Door",
            "party/party yellow.png", 22, 0);
    public static final DoorEntityLink GREEN_PARTY_DOOR = new DoorEntityLink(159, "Green Party Door",
            "party/party green.png", 21, 0);
    public static final DoorEntityLink BLUE_PARTY_DOOR = new DoorEntityLink(160, "Blue Party Door",
            "party/party blue.png", 21, 1);
    public static final DoorEntityLink PURPLE_PARTY_DOOR = new DoorEntityLink(161, "Purple Party Door",
            "party/party purple.png", 20, 0);
    public static final DoorEntityLink MAGENTA_PARTY_DOOR = new DoorEntityLink(162, "Magenta Party Door",
            "party/party magenta.png", 20, 1);

    // TRAPDOORS
    public static final TrapdoorLink OAK_TRAPDOOR = new TrapdoorLink(6, "Oak Trapoor", "oak.png", 9, 1);
    public static final SteelTrapdoorLink STEEL_TRAPDOOR = new SteelTrapdoorLink();
    public static final RedTrapdoorLink MANGROVE_TRAPDOOR = new RedTrapdoorLink();

    public static final JungleTrapdoorLink JUNGLE_TRAPDOOR = new JungleTrapdoorLink();
    public static final JungleTrapdoor2 JUNGLE_TRAPDOOR_2 = new JungleTrapdoor2();

    public static final SpruceTrapdoorLink DARK_OAK_TRAPDOOR = new SpruceTrapdoorLink();
    public static final BirchTrapdoorLink BIRCH_TRAPDOOR = new BirchTrapdoorLink();
    public static final TrapdoorLink ACACIA_TRAPDOOR = new TrapdoorLink(11, "Acacia Trapdoor", "acacia.png", 5, 1);
    public static final WarpedTrapdoorLink WARPED_TRAPDOOR_LINK = new WarpedTrapdoorLink();
    public static final TrapdoorLink OAK2_TRAPDOOR_LINK = new TrapdoorLink(133, "Oak Trapdoor 2", "oak 2.png", 6, 1);
    public static final TrapdoorLink MAGENTA_TRAPDOOR_LINK = new TrapdoorLink(68, "Magenta Trapdoor", "acacia2.png", 8,
            1);
    public static final GlassTrapdoorLink GLASS_TRAPDOOR_LINK = new GlassTrapdoorLink();
    public static final TrapdoorLink CLADDED_RED_TRAPDOOR = new TrapdoorLink(130, "Cladded Red Trapdoor",
            "cladded redwood.png", 13, 1);
    public static final TrapdoorLink CLADDED_BIRCH_TRAPDOOR = new TrapdoorLink(131, "Cladded Birch Trapdoor",
            "cladded birch.png", 14, 1);
    public static final TrapdoorLink CLADDED_WHITE_TRAPDOOR = new TrapdoorLink(132, "Cladded White Trapdoor",
            "cladded white.png", 12, 1);
    public static final BambooTrapdoor BAMBOO_TRAPDOOR = new BambooTrapdoor();
    public static final BambooTrapdoor2 BAMBOO_TRAPDOOR_2 = new BambooTrapdoor2();
    public static final RenaissanceTrapdoor RENAISSANCE_TRAPDOOR = new RenaissanceTrapdoor();
    public static final WhiteTrapdoor WHITE_TRAPDOOR = new WhiteTrapdoor();

    public static final TrapdoorLink WHITE_SPACE_TRAPDOOR = new TrapdoorLink(163, "White Space Trapoor",
            "space\\white trapdoor.png", 25, 0);
    public static final TrapdoorLink GRAY_SPACE_TRAPDOOR = new TrapdoorLink(164, "Gray Space Trapoor",
            "space\\gray trapdoor.png", 25, 1);

    public static final FenceGateEntityLink OAK_FENCE_GATE = new FenceGateEntityLink(12, "Oak Fence Gate", "oak.png", 0,
            2);
    public static final FenceGateEntityLink MANGROVE_FENCE_GATE = new FenceGateEntityLink(14, "Mangrove Fence Gate",
            "mangrove.png", 1, 2);
    public static final FenceGateEntityLink JUNGLE_FENCE_GATE = new FenceGateEntityLink(19, "Jungle Fence Gate",
            "jungle.png", 2, 2);
    public static final FenceGateEntityLink DARK_OAK_FENCE_GATE = new FenceGateEntityLink(18, "Dark Oak Fence Gate",
            "dark oak.png", 3, 2);
    public static final FenceGateEntityLink BIRCH_FENCE_GATE = new FenceGateEntityLink(13, "Birch Fence Gate",
            "birch.png", 4, 2);
    public static final FenceGateEntityLink BLUE_FENCE_GATE = new FenceGateEntityLink(16, "Blue Fence Gate", "blue.png",
            6, 2);
    public static final FenceGateEntityLink STONE_FENCE_GATE = new FenceGateEntityLink(17, "Stone Fence Gate",
            "stone.png", 7, 2);
    public static final FenceGateEntityLink ACACIA_FENCE_GATE = new FenceGateEntityLink(15, "Acacia Fence Gate",
            "acacia.png", 5, 2);
    public static final FenceGateEntityLink IRON_FENCE_GATE = new FenceGateEntityLink(67, "Steel Fence Gate",
            "iron.png", 8, 2);

    public static final FoxEntityLink RED_FOX = new FoxEntityLink(20, "Red Fox", "red.png");
    public static final FoxEntityLink WHITE_FOX = new FoxEntityLink(21, "White Fox", "white.png");
    public static final FoxEntityLink GRAY_FOX = new FoxEntityLink(22, "Gray Fox", "grey.png");

    public static final HorseEntityLink BROWN_HORSE = new HorseEntityLink(23, "Brown Horse", "brown.png");
    public static final HorseEntityLink CREAMY_HORSE = new HorseEntityLink(28, "Creamy Horse", "creamy.png");
    public static final HorseEntityLink CHESTNUT_HORSE = new HorseEntityLink(29, "Chestnut Horse", "chestnut.png");
    public static final HorseEntityLink BLACK_HORSE = new HorseEntityLink(26, "Black Horse", "black.png");
    public static final HorseEntityLink DARK_BROWN_HORSE = new HorseEntityLink(27, "Dark Brown Horse", "darkbrown.png");
    public static final HorseEntityLink GRAY_HORSE = new HorseEntityLink(24, "Gray Horse", "gray.png");
    public static final HorseEntityLink WHITE_HORSE = new HorseEntityLink(25, "White Horse", "white.png");

    public static final FishBLink TROPICAL_FISH = new FishBLink(30, "Angel Fish", "angel.png");
    public static final FishBLink TROPICAL_FISH1 = new FishBLink(35, "Blue Ring Angelfish", "blue_ring_angel.png");
    public static final FishBLink TROPICAL_FISH2 = new FishBLink(32, "Copperband Butterfly",
            "copperband_butterfly.png");
    public static final FishBLink TROPICAL_FISH3 = new FishBLink(33, "Gold Butterfly Fish", "gold.png");
    public static final FishBLink TROPICAL_FISH4 = new FishBLink(34, "Ornate Butterfly Fish", "ornate_butterfly.png");
    public static final FishBLink TROPICAL_FISH5 = new FishBLink(36, "Red Butterfly Fish", "red_butterfly.png");
    public static final FishBLink TROPICAL_FISH6 = new FishBLink(37, "Regal Tang Fish", "regal_tang.png");
    public static final FishBLink TROPICAL_FISH7 = new FishBLink(38, "Striped Butterfly Fish", "striped_butterfly.png");
    public static final FishBLink TROPICAL_FISH8 = new FishBLink(49, "Yellow Angelfish", "yellow_angel.png");
    public static final FishBLink TROPICAL_FISH9 = new FishBLink(48, "Gray Glitterfish", "gray_glitter.png");

    public static final FishALink TROPICAL_FISH_A = new FishALink(41, "Bicolor Angelfish", "bicolor_angel.png");
    public static final FishALink TROPICAL_FISH_A1 = new FishALink(40, "Clown Fish", "clown.png");
    public static final FishALink TROPICAL_FISH_A2 = new FishALink(43, "Clown Loach", "clown_loach.png");
    public static final FishALink TROPICAL_FISH_A3 = new FishALink(44, "Cotton Candy Betta", "cotton_candy_betta.png");
    public static final FishALink TROPICAL_FISH_A4 = new FishALink(45, "Damsel Fish", "damsel.png");
    public static final FishALink TROPICAL_FISH_A5 = new FishALink(46, "Emporer Angelfish", "emporer_angel.png");
    public static final FishALink TROPICAL_FISH_A6 = new FishALink(39, "Orange Green Betta", "orange_green_betta.png");
    public static final FishALink TROPICAL_FISH_A7 = new FishALink(47, "Royal Gramma Fish", "royal_gramma.png");
    public static final FishALink TROPICAL_FISH_A8 = new FishALink(42, "Salamander Betta", "salamander_betta.png");
    public static final FishALink TROPICAL_FISH_A9 = new FishALink(31, "Tri-Band Betta", "tri_band_betta.png");

    public static final CatEntityLink BLACK_CAT = new CatEntityLink(50, "Black Cat", "black.png");
    public static final CatEntityLink WHITE_CAT = new CatEntityLink(51, "White Cat", "white.png");
    public static final CatEntityLink BRITISH_SHORTHAIR = new CatEntityLink(56, "British Shorthair",
            "british_shorthair.png");
    public static final CatEntityLink CALICO_CAT = new CatEntityLink(60, "Calcio Cat A", "calico.png");
    public static final CatEntityLink CALICO_CAT2 = new CatEntityLink(61, "Calico Cat B", "calico2.png");
    public static final CatEntityLink JELLE_CAT = new CatEntityLink(58, "Jellie Cat", "jellie.png");
    public static final CatEntityLink PERSAIN_CAT = new CatEntityLink(59, "Persian Cat", "persian.png");
    public static final CatEntityLink TABBY_CAT = new CatEntityLink(52, "Tabby Cat", "tabby.png");
    public static final CatEntityLink RED_TABBY_CAT = new CatEntityLink(53, "Red Tabby Cat", "red.png");
    public static final CatEntityLink SIAMESE_CAT = new CatEntityLink(54, "Siamese Cat", "siamese.png");
    public static final CatEntityLink OCELOT = new CatEntityLink(55, "Ocelot", "ocelot.png");

    public static final MuleEntityLink MULE = new MuleEntityLink(70, "Mule", "mule.png");
    public static final MuleEntityLink DONKEY = new MuleEntityLink(71, "Donkey", "donkey.png");


    public static final BannerEntityLink BANNER = new BannerEntityLink(82, "Red Banner", "red.png");
    public static final BannerEntityLink BANNER1 = new BannerEntityLink(81, "Orange Banner", "orange.png");
    public static final BannerEntityLink BANNER2 = new BannerEntityLink(72, "Yellow Banner", "yellow.png");
    public static final BannerEntityLink BANNER3 = new BannerEntityLink(75, "Lime Banner", "lime.png");
    public static final BannerEntityLink BANNER4 = new BannerEntityLink(78, "Green Banner", "green.png");
    public static final BannerEntityLink BANNER5 = new BannerEntityLink(73, "Blue Banner", "blue.png");
    public static final BannerEntityLink BANNER6 = new BannerEntityLink(77, "Gray Banner", "gray.png");
    public static final BannerEntityLink BANNER7 = new BannerEntityLink(76, "Pink Banner", "pink.png");
    public static final BannerEntityLink BANNER8 = new BannerEntityLink(84, "Purple Banner", "purple.png");
    public static final BannerEntityLink BANNER9 = new BannerEntityLink(74, "White Banner", "white.png");
    public static final BannerEntityLink BANNER10 = new BannerEntityLink(83, "Xbuilders Banner", "blue_logo.png");
    public static final BannerEntityLink BANNER11 = new BannerEntityLink(80, "Regal Banner", "regal.png");
    public static final BannerEntityLink BANNER12 = new BannerEntityLink(79, "Royal Banner", "royal.png");
    //
    public static final BedEntityLink BLUE_BED = new BedEntityLink(85, "Blue Bed", "blue.png", 1);
    public static final BedEntityLink CYAN_BED = new BedEntityLink(141, "Cyan Bed", "cyan.png", 1);
    public static final BedEntityLink LIME_BED = new BedEntityLink(140, "Lime Bed", "lime.png", 2);
    public static final BedEntityLink MAGENTA_BED = new BedEntityLink(139, "Magenta Bed", "magenta.png", 4);
    public static final BedEntityLink ORANGE_BED = new BedEntityLink(136, "Orange Bed", "orange.png", 4);
    public static final BedEntityLink PINK_BED = new BedEntityLink(137, "Pink Bed", "pink.png", 4);
    public static final BedEntityLink PURPLE_BED = new BedEntityLink(138, "Purple Bed", "purple.png", 4);
    public static final BedEntityLink GREEN_BED = new BedEntityLink(86, "Green Bed", "green.png", 2);
    public static final BedEntityLink YELLOW_BED = new BedEntityLink(87, "Yellow Bed", "yellow.png", 3);
    public static final BedEntityLink RED_BED = new BedEntityLink(88, "Red Bed", "red.png", 4);
    public static final BedEntityLink BLACK_BED = new BedEntityLink(89, "Black Bed", "black.png", 7);
    public static final BedEntityLink GREY_BED = new BedEntityLink(90, "Gray Bed", "gray.png", 5);
    public static final BedEntityLink WHITE_BED = new BedEntityLink(91, "White Bed", "white.png", 6);

    public static final OakBoat OAK_BOAT = new OakBoat();
    public static final DarkOakBoat DARK_OAK_BOAT = new DarkOakBoat();
    public static final SpruceBoat SPRUCE_BOAT = new SpruceBoat();
    public static final AcaciaBoat ACACIA_BOAT = new AcaciaBoat();
    public static final JungleBoat JUNGLE_BOAT = new JungleBoat();
    public static final BirchBoat BIRCH_BOAT = new BirchBoat();
    //
    public static final IronMinecart WHITE_MINECART = new IronMinecart();
    public static final CharcoalMinecart BLACK_MINECART = new CharcoalMinecart();
    public static final RedMinecart RED_MINECART = new RedMinecart();
    public static final GreenMinecart GREEN_MINECART = new GreenMinecart();
    public static final CyanMinecart CYAN_MINECART = new CyanMinecart();
    public static final BlueMinecart BLUE_MINECART = new BlueMinecart();
    public static final YellowMinecart YELLOW_MINECART = new YellowMinecart();

    public static final TurtleEntityLink GREEN_SEA_TURTLE = new TurtleEntityLink(105, "Green Sea Turtle",
            "big_sea_turtle.png");
    public static final TurtleEntityLink LARGE_TORTOISE = new TurtleEntityLink(106, "Yellow Sea Turtle",
            "yellow_turtle.png");

    public static final RabbitEntityLink WHITE_RABBIT = new RabbitEntityLink(108, "White Rabbit", "white.png");
    public static final RabbitEntityLink BLACK_RABBIT = new RabbitEntityLink(109, "Black Rabbit", "black.png");
    public static final RabbitEntityLink BLACK_AND_WHITE_RABBIT = new RabbitEntityLink(118, "Black and White Rabbit",
            "toast.png");
    public static final RabbitEntityLink BROWN_RABBIT = new RabbitEntityLink(115, "Brown Rabbit", "brown.png");
    public static final RabbitEntityLink CAERBANNOG_RABBIT = new RabbitEntityLink(116, "Caerbannog Rabbit",
            "caerbannog.png");
    public static final RabbitEntityLink GOLD_RABBIT = new RabbitEntityLink(114, "Gold Rabbit", "gold.png");
    public static final RabbitEntityLink SALT_RABBIT = new RabbitEntityLink(119, "Salt Rabbit", "salt.png");
    public static final RabbitEntityLink WHITE_SPLOTCHED_RABBIT = new RabbitEntityLink(117, "White Splotched Rabbit",
            "white_splotched.png");

    public static final ParrotEntityLink BLUE_PARROT = new ParrotEntityLink(110, "Blue Parrot", "parrot_blue.png");
    public static final ParrotEntityLink GREEN_PARROT = new ParrotEntityLink(111, "Green Parrot", "parrot_green.png");
    public static final ParrotEntityLink RED_BLUE_PARROT = new ParrotEntityLink(107, "Red and Blue Parrot",
            "parrot_red_blue.png");
    public static final ParrotEntityLink GREY_PARROT = new ParrotEntityLink(112, "Gray Parrot", "parrot_grey.png");
    public static final ParrotEntityLink YELLOW_PARROT = new ParrotEntityLink(113, "Yellow Blue Parrot",
            "parrot_yellow_blue.png");

    public static final CustomVehicleEntityLink ENTITY_CUSTOM_VEHICLE = new CustomVehicleEntityLink(165);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tools">
//    public static final CopyToolBlock COPY_TOOL = new CopyToolBlock();
    public static final LiquidRemovalTool LIQUID_REMOVAL_TOOL = new LiquidRemovalTool();
    public static final Saddle SADDLE = new Saddle();
    public static final AnimalFeed ANIMAL_FEED = new AnimalFeed();
    public static final Hoe HOE = new Hoe();
    public static final Camera CAMERA = new Camera();
    public static final Flashlight FLASHLIGHT = new Flashlight();
    public static final AnimalLeave ANIMAL_LEAVE = new AnimalLeave();
    // </editor-fold>

    public static Tool[] getToolList() {
        return new Tool[]{CAMERA, FLASHLIGHT, LIQUID_REMOVAL_TOOL, HOE, SADDLE, ANIMAL_FEED,
                ANIMAL_LEAVE};
    }

    public static EntityLink[] getEntityList() {
        return new EntityLink[]{
                // Oak door
                OAK_DOOR, OAK_DOOR2, OAK_DOOR3,
                // red door
                RED_DOOR, RED_DOOR2,
                // jungle door
                JUNGLE_DOOR, JUNGLE_DOOR2,
                // spruce door
                SPRUCE_DOOR, SPRUCE_DOOR2,
                // birch door
                BIRCH_DOOR, BIRCH_DOOR2,
                // acacia door
                ACACIA_DOOR, ACACIA_DOOR2,
                // white door
                WHITE_DOOR, WHITE_DOOR2, WHITE_DOOR3, WHITE_DOOR4, WHITE_DOOR5, WHITE_DOOR6, WHITE_OVAL_DOOR,
                // other door
                STEEL_DOOR, GLASS_DOOR_LINK, WARPED_DOOR_LINK, BAMBOO_DOOR, BAMBOO_DOOR_2, WEB_DOOR, GRAY_SPACE_DOOR,
                WHITE_SPACE_DOOR, GRAY_SPACE_DOOR_2, WHITE_SPACE_DOOR_2, GRAY_SPACE_DOOR_2_PANELS,
                WHITE_SPACE_DOOR_2_PANELS,
                // party door
                RED_PARTY_DOOR, YELLOW_PARTY_DOOR, GREEN_PARTY_DOOR, BLUE_PARTY_DOOR, PURPLE_PARTY_DOOR,
                MAGENTA_PARTY_DOOR,
                // trapdoors
                OAK_TRAPDOOR, OAK2_TRAPDOOR_LINK, RENAISSANCE_TRAPDOOR, MANGROVE_TRAPDOOR, JUNGLE_TRAPDOOR,
                JUNGLE_TRAPDOOR_2, DARK_OAK_TRAPDOOR, BIRCH_TRAPDOOR, ACACIA_TRAPDOOR, MAGENTA_TRAPDOOR_LINK,
                GLASS_TRAPDOOR_LINK, WARPED_TRAPDOOR_LINK, STEEL_TRAPDOOR, CLADDED_RED_TRAPDOOR, CLADDED_BIRCH_TRAPDOOR,
                CLADDED_WHITE_TRAPDOOR, BAMBOO_TRAPDOOR, BAMBOO_TRAPDOOR_2, WHITE_TRAPDOOR, GRAY_SPACE_TRAPDOOR,
                WHITE_SPACE_TRAPDOOR,
                // fence gates
                OAK_FENCE_GATE, MANGROVE_FENCE_GATE, JUNGLE_FENCE_GATE, DARK_OAK_FENCE_GATE, BIRCH_FENCE_GATE,
                ACACIA_FENCE_GATE, BLUE_FENCE_GATE, STONE_FENCE_GATE, IRON_FENCE_GATE,
                // beds
                RED_BED, ORANGE_BED, YELLOW_BED, GREEN_BED, LIME_BED, CYAN_BED, BLUE_BED, PINK_BED, MAGENTA_BED,
                PURPLE_BED, BLACK_BED, GREY_BED, WHITE_BED,
                // banners
                BANNER, BANNER1, BANNER2, BANNER3, BANNER4, BANNER5, BANNER6, BANNER7, BANNER8, BANNER9, BANNER10,
                BANNER11, BANNER12,
                // boats
                OAK_BOAT, DARK_OAK_BOAT, SPRUCE_BOAT, ACACIA_BOAT, JUNGLE_BOAT, BIRCH_BOAT,
                // minecarts
                BLACK_MINECART, WHITE_MINECART, RED_MINECART, YELLOW_MINECART, GREEN_MINECART, CYAN_MINECART,
                BLUE_MINECART,
                // animals
                RED_FOX, GRAY_FOX, WHITE_FOX, BROWN_HORSE, CREAMY_HORSE, CHESTNUT_HORSE, BLACK_HORSE, DARK_BROWN_HORSE,
                GRAY_HORSE, WHITE_HORSE, MULE, DONKEY, BLACK_CAT, WHITE_CAT, BRITISH_SHORTHAIR, CALICO_CAT, CALICO_CAT2,
                JELLE_CAT,
                PERSAIN_CAT, TABBY_CAT, RED_TABBY_CAT, SIAMESE_CAT, OCELOT, TROPICAL_FISH, TROPICAL_FISH1,
                TROPICAL_FISH2,
                TROPICAL_FISH3, TROPICAL_FISH4, TROPICAL_FISH5, TROPICAL_FISH6, TROPICAL_FISH7, TROPICAL_FISH8,
                TROPICAL_FISH9, TROPICAL_FISH_A1,
                TROPICAL_FISH_A2, TROPICAL_FISH_A3, TROPICAL_FISH_A4, TROPICAL_FISH_A5, TROPICAL_FISH_A6,
                TROPICAL_FISH_A7, TROPICAL_FISH_A8,
                TROPICAL_FISH_A9, TROPICAL_FISH_A, GREEN_SEA_TURTLE, LARGE_TORTOISE, WHITE_RABBIT, BLACK_RABBIT,
                BLACK_AND_WHITE_RABBIT, BROWN_RABBIT,
                CAERBANNOG_RABBIT, GOLD_RABBIT, SALT_RABBIT, WHITE_SPLOTCHED_RABBIT, BLUE_PARROT, GREEN_PARROT,
                RED_BLUE_PARROT, GREY_PARROT, YELLOW_PARROT,
                ENTITY_CUSTOM_VEHICLE};
    }

    public static Block[] getBlockList() throws IOException {
        Block[] list = new Block[]{START_BOUNDARY,
                CURVED_TRACK, RAISED_TRACK, BLOCK_CONTROL_PANEL, BLOCK_DIRT, BLOCK_GRASS,
                GRASS_PLANT, BLOCK_SNOW, SNOW_BLOCK, BLOCK_DRY_GRASS, DRY_GRASS_PLANT, BLOCK_JUNGLE_GRASS,
                JUNGLE_GRASS_PLANT, TALL_GRASS_BOTTOM, TALL_GRASS_TOP, TALL_DRY_GRASS_BOTTOM, TALL_DRY_GRASS_TOP,
                BLOCK_WATER, BlockLava, BLOCK_TNT, BLOCK_TNT_LARGE, TNT_ACTIVE, BlockTorch, BLOCK_BLUE_TORCH,
                BLOCK_LAMP, BLOCK_BLUE_LAMP, Candle, BlueCandle, GreenCandle, YellowCandle, RedCandle, ElectricLight,
                EdisonLight, SeaLight, GlowRock, CAMPFIRE, BlockWheatSeeds, BlockCarrotSeeds, BLOCK_POTATO_SEEDS,
                BEET_SEEDS, SUNFLOWER_SEEDS, SUNFLOWER_HEAD, SUNFLOWER_STALK, IRON_LADDER, BAMBOO_LADDER, OAK_LADDER,
                DARK_OAK_LADDER, BLOCK_SPRUCE_SAPLING, BLOCK_SPRUCE_LOG, BLOCK_SPRUCE_LEAVES, BlockBirchSapling,
                BIRCH_LOG, BIRCH_LEAVES, BlockOakSapling, OAK_LOG, OAK_LEAVES, ACACIA_SAPLING, ACACIA_LOG,
                ACACIA_LEAVES, BLOCK_JUNGLE_SAPLING, BLOCK_JUNGLE_LOG, BLOCK_JUNGLE_LEAVES, TRACK, BLOCK_CROSSTRACK,
                MERGE_TRACK, BLOCK_TRACK_STOP, SWITCH_JUNCTION, MINECART_ROAD, MINECART_ROAD_SLAB, MINECART_CROSSWALK,
                MINECART_ROAD_MARKINGS, BlockBedrock, BlockBamboo, BLOCK_SAND, BLOCK_RED_SAND, BLOCK_CLAY, BLOCK_GRAVEL,
                BLOCK_STONE, BLOCK_ANDESITE, BLOCK_DIORITE, BLOCK_GLASS, BlockBrick, StoneBrick, BlockCobblestone,
                BLOCK_CEMENT, BlockWatermelon, BlockPalisadeStone, BlockPalisadeStone2, BLOCK_FARMLAND, WHEAT, CARROTS,
                BEETS, POTATOES, StageA1, StageA2, StageB1, StageB2, StageB3, StageB4, StageB5, StageB6, Dandelion,
                PeonyBush, AzureBluet, YellowFlower, RedFlower, BlueOrchid, Pansies, Roses, Fern, WhiteRose, RedRose,
                BlackeyeSusan, OrangeTulip, BlockMiniCactus, Mushroom, Mushroom2, DeadBush, Cake, Bread, Croissant,
                Bottle, Cup, WineGlass, BLOCK_ICE, PolishedDiorite, PolishedAndesite, CrackedStone, StoneWithVines,
                BurgundyBrick, Bookshelf, MusicBox, Obsidian, RedPalisadeSandstone, PalisadeSandstone,
                EngravedSandstone, EngravedSandstone2, EngravedRedSandstone, EngravedRedSandstone2, QuartzPillarBlock,
                MarblePillarBlock, PhantomStone, PhantomStoneBrick, PhantomSandstone, Wool, WoolGray, WoolRed, WoolPink,
                WoolOrange, WoolYellow, WoolGreen, WoolDarkGreen, WoolTurquoise, WoolDeepBlue, WoolSkyBlue, WoolBrown,
                WoolPurple, WoolMagenta, WoolBlack, YellowConcrete, BlackConcrete, BlueConcrete, BrownConcrete,
                CyanConcrete, GrayConcrete, GreenConcrete, LightBlueConcrete, LightGrayConcrete, LimeConcrete,
                MagentaConcrete, OrangeConcrete, PinkConcrete, PurpleConcrete, RedConcrete, WhiteConcrete,
                YellowGlazedTeracotta, BlackGlazedTeracotta, BlueGlazedTeracotta, BrownGlazedTeracotta,
                CyanGlazedTeracotta, GrayGlazedTeracotta, GreenGlazedTeracotta, LightBlueGlazedTeracotta,
                LightGrayGlazedTeracotta, LimeGlazedTeracotta, MagentaGlazedTeracotta, OrangeGlazedTeracotta,
                PinkGlazedTeracotta, PurpleGlazedTeracotta, RedGlazedTeracotta, WhiteGlazedTeracotta,
                YellowstainedGlass, BlackstainedGlass, BluestainedGlass, BrownstainedGlass, CyanstainedGlass,
                GraystainedGlass, GreenstainedGlass, LightBluestainedGlass, LightGraystainedGlass, LimestainedGlass,
                MagentastainedGlass, OrangestainedGlass, PinkstainedGlass, PurplestainedGlass, RedstainedGlass,
                WhitestainedGlass, Granite, GraniteBrick, PrismarineBricks, DarkPrismarineBricks, LapisLazulBlock,
                IronBlock, GoldBlock, EmeraldBlock, DiamondBlock, CoalBlock, Beehive, HayBail, FireCoralBlock,
                FireCoral, FireCoralFan, HornCoralBlock, HornCoral, HornCoralFan, TubeCoralBlock, TubeCoral,
                TubeCoralFan, BrainCoralBlock, BrainCoral, BrainCoralFan, BubbleCoralBlock, BubbleCoral, BubbleCoralFan,
                FlatVines, Vines, FlatDragonVines, DragonVines, RedVinesFlat, RedVines, CaveVinesFlat, CaveVines,
                PrismarineBrickSlab, PrismarineBrickStairs, OakFence, DarkOakFence, BirchFence, AcaciaFence,
                PrismarineBrickFence, DarkPrismarineBrickSlab, DarkPrismarineBrickStairs, DarkPrismarineBrickFence,
                GraniteBrickPillar, GraniteBrickSlab, GraniteBrickStairs, GraniteBrickFence, JunglePlanksSlab,
                JunglePlanksStairs, JungleFence, BirchPlanksStairs, OakPlanksStairs, DarkOakPlanksStairs,
                AcaciaPlanksStairs, SandstoneStairs, PolishedAndesiteStairs, PolishedDioriteStairs, RedSandstoneStairs,
                GlassStairs, StoneBrickStairs, BrickStairs, CobblestoneStairs, PalisadeStoneStairs,
                PalisadeStone2Stairs, CrackedStoneStairs, StoneWithVinesStairs, BurgundyBrickStairs,
                RedPalisadeSandstoneStairs, PalisadeSandstoneStairs, WoolStairs, WoolGrayStairs, WoolRedStairs,
                WoolPinkStairs, WoolOrangeStairs, WoolYellowStairs, WoolGreenStairs, WoolDarkGreenStairs,
                WoolTurquoiseStairs, WoolDeepBlueStairs, WoolSkyBlueStairs, WoolBrownStairs, WoolPurpleStairs,
                WoolMagentaStairs, WoolBlackStairs, CementStairs, YellowConcreteStairs, BlackConcreteStairs,
                BlueConcreteStairs, BrownConcreteStairs, CyanConcreteStairs, GrayConcreteStairs, GreenConcreteStairs,
                LightBlueConcreteStairs, LightGrayConcreteStairs, LimeConcreteStairs, MagentaConcreteStairs,
                OrangeConcreteStairs, PinkConcreteStairs, PurpleConcreteStairs, RedConcreteStairs, WhiteConcreteStairs,
                YellowGlazedTeracottaStairs, BlackGlazedTeracottaStairs, BlueGlazedTeracottaStairs,
                BrownGlazedTeracottaStairs, CyanGlazedTeracottaStairs, GrayGlazedTeracottaStairs,
                GreenGlazedTeracottaStairs, LightBlueGlazedTeracottaStairs, LightGrayGlazedTeracottaStairs,
                LimeGlazedTeracottaStairs, MagentaGlazedTeracottaStairs, OrangeGlazedTeracottaStairs,
                PinkGlazedTeracottaStairs, PurpleGlazedTeracottaStairs, RedGlazedTeracottaStairs,
                WhiteGlazedTeracottaStairs, ObsidianStairs, LapisLazulStairs, IronStairs, GoldStairs, EmeraldStairs,
                DiamondStairs, BirchPlanksSlab, OakPlanksSlab, DarkOakPlanksSlab, AcaciaPlanksSlab, StoneBrickSlab,
                RedSandstoneSlab, GlassSlab, SandstoneSlab, PolishedAndesiteSlab, PolishedDioriteSlab, BrickSlab,
                CobblestoneSlab, PalisadeStoneSlab, PalisadeStone2Slab, CrackedStoneSlab, StoneWithVinesSlab,
                BurgundyBrickSlab, RedPalisadeSandstoneSlab, PalisadeSandstoneSlab, WoolSlab, WoolGraySlab, WoolRedSlab,
                WoolPinkSlab, WoolOrangeSlab, WoolYellowSlab, WoolGreenSlab, WoolDarkGreenSlab, WoolTurquoiseSlab,
                WoolDeepBlueSlab, WoolSkyBlueSlab, WoolBrownSlab, WoolPurpleSlab, WoolMagentaSlab, WoolBlackSlab,
                CementSlab, YellowConcreteSlab, BlackConcreteSlab, BlueConcreteSlab, BrownConcreteSlab,
                CyanConcreteSlab, GrayConcreteSlab, GreenConcreteSlab, LightBlueConcreteSlab, LightGrayConcreteSlab,
                LimeConcreteSlab, MagentaConcreteSlab, OrangeConcreteSlab, PinkConcreteSlab, PurpleConcreteSlab,
                RedConcreteSlab, WhiteConcreteSlab, YellowGlazedTeracottaSlab, BlackGlazedTeracottaSlab,
                BlueGlazedTeracottaSlab, BrownGlazedTeracottaSlab, CyanGlazedTeracottaSlab, GrayGlazedTeracottaSlab,
                GreenGlazedTeracottaSlab, LightBlueGlazedTeracottaSlab, LightGrayGlazedTeracottaSlab,
                LimeGlazedTeracottaSlab, MagentaGlazedTeracottaSlab, OrangeGlazedTeracottaSlab, PinkGlazedTeracottaSlab,
                PurpleGlazedTeracottaSlab, RedGlazedTeracottaSlab, WhiteGlazedTeracottaSlab, ObsidianSlab,
                LapisLazulBlockSlab, IronBlockSlab, GoldBlockSlab, EmeraldBlockSlab, DiamondBlockSlab, StoneBrickFence,
                PalisadeStoneFence, PalisadeStone2Fence, PolishedDioriteFence, PolishedAndesiteFence, CrackedStoneFence,
                StoneWithVinesFence, BurgundyBrickFence, RedPalisadeSandstoneFence, PalisadeSandstoneFence, CementFence,
                YellowConcreteFence, BlackConcreteFence, BlueConcreteFence, BrownConcreteFence, CyanConcreteFence,
                GrayConcreteFence, GreenConcreteFence, LightBlueConcreteFence, LightGrayConcreteFence,
                LimeConcreteFence, MagentaConcreteFence, OrangeConcreteFence, PinkConcreteFence, PurpleConcreteFence,
                RedConcreteFence, WhiteConcreteFence, ObsidianFence, LapisLazulFence, IronFence, GoldFence,
                EmeraldFence, DiamondFence, RedSandstonePillar, StoneBrickPillar, PalisadeStonePillar,
                PalisadeStone2Pillar, CrackedStonePillar, StoneWithVinesPillar, RedPalisadeSandstonePillar,
                PalisadeSandstonePillar, QuartzPillar, MarblePillar, CheckerboardChiseledMarble, ChiseledQuartz,
                ChiseledQuartzStairs, ChiseledQuartzSlab, ChiseledQuartzPillar, YellowstainedGlassStairs,
                YellowstainedGlassSlab, BlackstainedGlassStairs, BlackstainedGlassSlab, BluestainedGlassStairs,
                BluestainedGlassSlab, BrownstainedGlassStairs, BrownstainedGlassSlab, CyanstainedGlassStairs,
                CyanstainedGlassSlab, GraystainedGlassStairs, GraystainedGlassSlab, GreenstainedGlassStairs,
                GreenstainedGlassSlab, LightBluestainedGlassStairs, LightBluestainedGlassSlab,
                LightGraystainedGlassStairs, LightGraystainedGlassSlab, LimestainedGlassStairs, LimestainedGlassSlab,
                MagentastainedGlassStairs, MagentastainedGlassSlab, OrangestainedGlassStairs, OrangestainedGlassSlab,
                PinkstainedGlassStairs, PinkstainedGlassSlab, PurplestainedGlassStairs, PurplestainedGlassSlab,
                RedstainedGlassStairs, RedstainedGlassSlab, WhitestainedGlassStairs, WhitestainedGlassSlab,
                CheckerboardChiseledMarbleStairs, CheckerboardChiseledMarbleSlab, ChiseledMarbleStairs,
                ChiseledMarbleSlab, MarbleTileStairs, MarbleTileSlab, MarbleTilePillar, GlassPane,
                YellowstainedGlassPane, BlackstainedGlassPane, BluestainedGlassPane, BrownstainedGlassPane,
                CyanstainedGlassPane, GraystainedGlassPane, GreenstainedGlassPane, LightBluestainedGlassPane,
                LightGraystainedGlassPane, LimestainedGlassPane, MagentastainedGlassPane, OrangestainedGlassPane,
                PinkstainedGlassPane, PurplestainedGlassPane, RedstainedGlassPane, WhitestainedGlassPane, BambooBlock,
                BambooWood, MosaicBambooWood, MarbleTile, YellowMarbleTile, BlackMarbleTile, BlueMarbleTile,
                BrownMarbleTile, CyanMarbleTile, GrayMarbleTile, GreenMarbleTile, PastelblueMarbleTile,
                PastelgreenMarbleTile, MagentaMarbleTile, OrangeMarbleTile, PinkMarbleTile, PurpleMarbleTile,
                BurgundyMarbleTile, PastelredMarbleTile, ChiseledMarble, YellowChiseledMarbleTile,
                BlackChiseledMarbleTile, BlueChiseledMarbleTile, BrownChiseledMarbleTile, CyanChiseledMarbleTile,
                GrayChiseledMarbleTile, GreenChiseledMarbleTile, PastelblueChiseledMarbleTile,
                PastelgreenChiseledMarbleTile, MagentaChiseledMarbleTile, OrangeChiseledMarbleTile,
                PinkChiseledMarbleTile, PurpleChiseledMarbleTile, BurgundyChiseledMarbleTile,
                PastelredChiseledMarbleTile, GrayMarbleTileStairs, GrayMarbleTileSlab, BlueMarbleTileStairs,
                BlueMarbleTileSlab, GreenMarbleTileStairs, GreenMarbleTileSlab, OrangeMarbleTileStairs,
                OrangeMarbleTileSlab, GrayMarbleTilePillar, BlueMarbleTilePillar, GreenMarbleTilePillar,
                OrangeMarbleTilePillar, YellowMarbleTileStairs, YellowMarbleTileSlab, YellowMarbleTilePillar,
                BlackMarbleTileStairs, BlackMarbleTileSlab, BlackMarbleTilePillar, BrownMarbleTileStairs,
                BrownMarbleTileSlab, BrownMarbleTilePillar, CyanMarbleTileStairs, CyanMarbleTileSlab,
                CyanMarbleTilePillar, PastelblueMarbleTileStairs, PastelblueMarbleTileSlab, PastelblueMarbleTilePillar,
                PastelgreenMarbleTileStairs, PastelgreenMarbleTileSlab, PastelgreenMarbleTilePillar,
                MagentaMarbleTileStairs, MagentaMarbleTileSlab, MagentaMarbleTilePillar, PinkMarbleTileStairs,
                PinkMarbleTileSlab, PinkMarbleTilePillar, PurpleMarbleTileStairs, PurpleMarbleTileSlab,
                PurpleMarbleTilePillar, BurgundyMarbleTileStairs, BurgundyMarbleTileSlab, BurgundyMarbleTilePillar,
                PastelredMarbleTileStairs, PastelredMarbleTileSlab, PastelredMarbleTilePillar,
                YellowChiseledMarbleTileStairs, YellowChiseledMarbleTileSlab, YellowChiseledMarbleTilePillar,
                BlackChiseledMarbleTileStairs, BlackChiseledMarbleTileSlab, BlackChiseledMarbleTilePillar,
                BlueChiseledMarbleTileStairs, BlueChiseledMarbleTileSlab, BlueChiseledMarbleTilePillar,
                BrownChiseledMarbleTileStairs, BrownChiseledMarbleTileSlab, BrownChiseledMarbleTilePillar,
                CyanChiseledMarbleTileStairs, CyanChiseledMarbleTileSlab, CyanChiseledMarbleTilePillar,
                GrayChiseledMarbleTileStairs, GrayChiseledMarbleTileSlab, GrayChiseledMarbleTilePillar,
                GreenChiseledMarbleTileStairs, GreenChiseledMarbleTileSlab, GreenChiseledMarbleTilePillar,
                PastelblueChiseledMarbleTileStairs, PastelblueChiseledMarbleTileSlab,
                PastelblueChiseledMarbleTilePillar, PastelgreenChiseledMarbleTileStairs,
                PastelgreenChiseledMarbleTileSlab, PastelgreenChiseledMarbleTilePillar, MagentaChiseledMarbleTileStairs,
                MagentaChiseledMarbleTileSlab, MagentaChiseledMarbleTilePillar, OrangeChiseledMarbleTileStairs,
                OrangeChiseledMarbleTileSlab, OrangeChiseledMarbleTilePillar, PinkChiseledMarbleTileStairs,
                PinkChiseledMarbleTileSlab, PinkChiseledMarbleTilePillar, PurpleChiseledMarbleTileStairs,
                PurpleChiseledMarbleTileSlab, PurpleChiseledMarbleTilePillar, BurgundyChiseledMarbleTileStairs,
                BurgundyChiseledMarbleTileSlab, BurgundyChiseledMarbleTilePillar, PastelredChiseledMarbleTileStairs,
                PastelredChiseledMarbleTileSlab, PastelredChiseledMarbleTilePillar, HoneycombBlock, YellowStainedWood,
                BlackStainedWood, BlueStainedWood, CyanStainedWood, GrayStainedWood, GreenStainedWood,
                LightBlueStainedWood, LimeStainedWood, MagentaStainedWood, OrangeStainedWood, PinkStainedWood,
                PurpleStainedWood, RedStainedWood, WhiteStainedWood, IceBlockStairs, IceBlockSlab,
                MosaicBambooWoodStairs, MosaicBambooWoodSlab, MosaicBambooWoodFence, YellowStainedWoodStairs,
                YellowStainedWoodSlab, YellowStainedWoodFence, BlackStainedWoodStairs, BlackStainedWoodSlab,
                BlackStainedWoodFence, BlueStainedWoodStairs, BlueStainedWoodSlab, BlueStainedWoodFence,
                CyanStainedWoodStairs, CyanStainedWoodSlab, CyanStainedWoodFence, GrayStainedWoodStairs,
                GrayStainedWoodSlab, GrayStainedWoodFence, GreenStainedWoodStairs, GreenStainedWoodSlab,
                GreenStainedWoodFence, LightBlueStainedWoodStairs, LightBlueStainedWoodSlab, LightBlueStainedWoodFence,
                LimeStainedWoodStairs, LimeStainedWoodSlab, LimeStainedWoodFence, MagentaStainedWoodStairs,
                MagentaStainedWoodSlab, MagentaStainedWoodFence, OrangeStainedWoodStairs, OrangeStainedWoodSlab,
                OrangeStainedWoodFence, PinkStainedWoodStairs, PinkStainedWoodSlab, PinkStainedWoodFence,
                PurpleStainedWoodStairs, PurpleStainedWoodSlab, PurpleStainedWoodFence, RedStainedWoodStairs,
                RedStainedWoodSlab, RedStainedWoodFence, WhiteStainedWoodStairs, WhiteStainedWoodSlab,
                WhiteStainedWoodFence, BambooWoodStairs, BambooWoodSlab, BambooWoodFence, HoneycombBlockStairs,
                HoneycombBlockSlab, WhiteSpaceTile, SolarPanel, GraySpaceTile, WhiteSpaceTileStairs, WhiteSpaceTileSlab,
                GraySpaceTileStairs, GraySpaceTileSlab, IronOre, CoalOre, LapisLazulOre, DiamondOre, EmeraldOre,
                GoldOre, LilyPad, SeaGrass, BLOCK_CACTUS, Sandstone, RedSandstone, OakPlanks, JunglePlanks,
                SprucePlanks, BirchPlanks, AcaciaPlanks, AmethystCrystal, RubyCrystal, JadeCrystal, AquamarineCrystal,
                /**/ RED_WIRE, GREEN_WIRE, BLUE_WIRE, GRAY_WIRE,
                BLOCK_DRIVERS_SEAT, PassengerSeat, MetalGrate, BLOCK_WHEEL, BLOCK_WHEEL_HALF, BOYANCY_BASE,
                BLOCK_ENGINE_30HP, BLOCK_ENGINE_60HP,
                BLOCK_ENGINE_120HP, BLOCK_ENGINE_240HP, BLOCK_JET_THRUSTER, BLOCK_WATER_PROPELLER,
                BLOCK_LARGE_HELICOPTER_BLADE, BLOCK_SMALL_HELICOPTER_BLADE, BLOCK_SILVER_BRICK};

//        makeNewBlocksAsJson(list, "items\\blocks\\json\\house.json", 752);
        list = ArrayUtils.concatArrays(list, getAllJsonBlocks());
        return list;
    }

    private static Block[] getAllJsonBlocks() {
        try {
            File jsonBlockFile = ResourceUtils.resource("items\\blocks\\json");
            System.out.println("Adding all json blocks from " + jsonBlockFile.getAbsolutePath());
            if (!jsonBlockFile.exists()) jsonBlockFile.mkdirs();
            Block[] allBlocks = new Block[0];

            for (File file : jsonBlockFile.listFiles()) {
                if (!file.getName().endsWith(".json")) continue;
                System.out.println("\tAdding all json blocks from " + file.getAbsolutePath());
                String jsonString = Files.readString(file.toPath());
                Block[] jsonBlocks2 = JsonManager.gson.fromJson(jsonString, Block[].class);
                if (jsonBlocks2 != null && jsonBlocks2.length > 0) {
                    //append to list
                    allBlocks = ArrayUtils.concatArrays(allBlocks, jsonBlocks2);
                }
            }
            return allBlocks;
        } catch (IOException e) {
            ErrorHandler.handleFatalError(e);
        }
        return null;
    }

    public static void makeNewBlocksAsJson(Block[] list, String outputPath, int startingID) {
        //Add carpet
        ArrayList<Block> newBlocks = new ArrayList<>();
        for (Block block : list) {
            if (block.name.toLowerCase().contains("teracotta")
                    &&block.type == BlockRenderType.ORIENTABLE_BLOCK) {
                    Block b = new Block(startingID,
                            block.name.replace("Glazed Teracotta","") +"Carpet");
                    b.solid = false;
                    b.texture = block.texture;
                    b.opaque = false;
                    b.type = BlockRenderType.FLOOR;

                    newBlocks.add(b);
                    startingID++;
                    System.out.println("Added " + b.name);
            }
        }

        HashMap<Integer,String> colorNames = new HashMap<>();
        colorNames.put(0, "Gold");
        colorNames.put(1, "Tan");
        colorNames.put(2, "Brown");
        colorNames.put(3, "Cyan");
        colorNames.put(4, "Gray");
        colorNames.put(5, "Green");
        colorNames.put(6, "Blue");
        colorNames.put(7, "Light Gray");
        colorNames.put(8, "Light Green");
        colorNames.put(9, "Magenta");
        colorNames.put(10, "Orange");
        colorNames.put(11, "Pink");
        colorNames.put(12, "Purple");
        colorNames.put(13, "Red");
        colorNames.put(14, "White");
        colorNames.put(15, "Yellow");

        //Add seats and siding
        for(int i=0;i<16;i++) {
            Block seat = new Block(startingID,
                    colorNames.get(i)+" Seat");
            seat.solid = true;
            seat.texture = new BlockTexture(i,30,i,31,i,31);
            seat.opaque = false;
            seat.type = BlockRenderType.SLAB;
            newBlocks.add(seat);
            startingID++;


            Block siding = new Block(startingID,
                    colorNames.get(i)+" Siding");
            siding.solid = true;
            siding.texture = new BlockTexture(i,28,i,28,i,29);
            siding.opaque = true;
            newBlocks.add(siding);
            startingID++;
        }

        exportBlocksToJson(newBlocks, outputPath);
    }

    public static void exportBlocksToJson(Block[] list,String out) {
        //Save list as json
        try {
            File jsonBlockFile = ResourceUtils.resource(out);
            String jsonString = JsonManager.gson.toJson(list);
            Files.writeString(jsonBlockFile.toPath(), jsonString);
            System.out.println("Saved " + list.length + " blocks to " + jsonBlockFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void exportBlocksToJson(List<Block> list, String out) {
        //Save list as json
        try {
            File jsonBlockFile = ResourceUtils.resource(out);
            String jsonString = JsonManager.gson.toJson(list);
            Files.writeString(jsonBlockFile.toPath(), jsonString);
            System.out.println("Saved " + list.size() + " blocks to " + jsonBlockFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
