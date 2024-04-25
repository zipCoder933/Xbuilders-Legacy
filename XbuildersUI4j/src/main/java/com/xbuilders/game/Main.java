/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game;

import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.game.items.blockType.*;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.blockType.wheel.HalfWheelRenderer;
import com.xbuilders.game.items.blockType.wheel.WheelRenderer;
import processing.core.PGraphics;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zipCoder933
 */
public class Main extends VoxelGame {

    public static boolean DEV_MODE = true;
    static PointerHandler ph;
    public static final File BLOCK_ICON_DIR = ResourceUtils.resource("items\\blocks\\icons");

    public final static String VERSION_NOTES = "underwater hybrid vehicles";

    public Main(String args[], int sizeX, int sizeY, File iconPath, ProgramMode mode) throws IOException, InterruptedException {
        super(sizeX, sizeY, iconPath, "X-Builders");

        ItemList.initialize(this);
        ItemList.setAllItems(
                GameItems.getBlockList(),
                GameItems.getEntityList(),
                GameItems.getToolList());

        ItemList.blocks.addBlockType(BlockRenderType.SPRITE, new SpriteRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.SLAB, new SlabRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.STAIRS, new StairsRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.FENCE, new FenceRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.WALL_ITEM, new WallItemRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.TORCH, new TorchRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.PILLAR, new PillarRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.FLOOR, new FloorItemRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.TRACK, new RaisedTrackRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.LAMP, new LampRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.PANE, new PaneRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.LIQUID, new LiquidRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.ORIENTABLE_BLOCK, new OrientableBlockRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.SUNFLOWER_HEAD, new SunflowerRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.WIRE, new WireRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.WHEEL, new WheelRenderer());
        ItemList.blocks.addBlockType(BlockRenderType.WHEEL_HALF, new HalfWheelRenderer());

        ph = init(args, DEV_MODE, mode);
    }

    private static Main main;

    public static PGraphics getPG() {
        return main.getGraphics();
    }

    public static Main getMain() {
        return main;
    }

    public static void main(String[] args) {
        System.out.println("VERSION: " + VERSION_NOTES);
        try {
            List<String> argList = Arrays.asList(args);
            DEV_MODE = argList.contains("devmode");
            System.out.println("Dev Mode: " + DEV_MODE);

            ResourceUtils.initialize(DEV_MODE);

            ProgramMode mode = ProgramMode.DEFAULT;
            if (argList.contains("legacy")) {
                mode = ProgramMode.LEGACY_CONVERSION;
            } else if (argList.contains("blocks")
                    || argList.contains("icons") ||
                    !BLOCK_ICON_DIR.exists()) {
                mode = ProgramMode.BLOCK_ICON_SETUP;
            }
            main = new Main(args, 1000, 700, ResourceUtils.resource("icon.png"), mode);
            ph.getMainThread().run(Thread.currentThread());
        } catch (Exception ex) {
            ErrorHandler.handleFatalError(ex);
        }
    }

}
