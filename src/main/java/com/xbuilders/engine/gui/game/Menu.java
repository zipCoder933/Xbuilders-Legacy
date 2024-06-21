/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.game.ScreenshotUtils;
import com.xbuilders.engine.gui.Button;
import com.xbuilders.engine.gui.LabeledNumberBox;
import com.xbuilders.engine.rendering.worldLightMap.ShaderLightMap;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.World;
import com.xbuilders.game.PointerHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static processing.core.PConstants.LEFT;

import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.EventAction;
import com.xbuilders.window.components.NumberBox;

/**
 * @author zipCoder933
 */
class Menu extends GameMenuPage {

    Button screenshots, quit, help, settings, gameMode, setPinpoint, loadPinpoint;//TODO: Add screenshots menu button?
    LabeledNumberBox chunks;

    public Menu(final PointerHandler ph, GameMenu parent, Runnable quitAction) {
        super(parent);
        quit = new Button(this);
        help = new Button(this);

        chunks = new LabeledNumberBox(this);
//        fogDist = new LabeledNumberBox(this);

        chunks.getBox().setValue(ph.getSettingsFile().chunkRadius);
        chunks.setLabel("Chunks to render:");
        chunks.getBox().setOnchangeEvent(new EventAction<NumberBox>() {
            @Override
            public void run(NumberBox t) {
                try {
                    int value = 0;
                    value = (int) MathUtils.clamp(t.getValue(),
                            VoxelGame.getSettings().getSettingsFile().minChunkDistance,
                            VoxelGame.getSettings().getSettingsFile().maxChunkDistance);

                    chunks.getBox().setValue(value);

                    if (value != ph.getSettingsFile().chunkRadius) {
                        VoxelGame.getSettings().setChunkDistance(value);
                        ShaderLightMap.initializeLightmap(ph, this.getClass());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        gameMode = new Button(this);
        gameMode.setAction(() -> {
            int modeIndex = Arrays.asList(GameMode.values()).indexOf(VoxelGame.getGameScene().mode) + 1;
            VoxelGame.getGameScene().mode = GameMode.values()[modeIndex % GameMode.values().length];
        });

        screenshots = new Button(this);
        screenshots.setAction(() -> {
            try {
                ScreenshotUtils.loadScreenshotDir();
            } catch (IOException ex) {
                Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        quit.setAction(quitAction);
        help.setAction(() -> {
            MiscUtils.openHelpMenu();
        });

        settings = new Button(this);
        settings.setAction(() -> {
            if (page == Page.DEFAULT) {
                setPage(Page.SETTINGS);
            } else if (page == Page.SETTINGS) {
                setPage(Page.DEFAULT);
            }
        });

        setPinpoint = new Button(this);
        setPinpoint.setAction(() -> {
            VoxelGame.getWorld().setPinpoint();
        });

        loadPinpoint = new Button(this);
        loadPinpoint.setAction(() -> {
            VoxelGame.getWorld().loadPinpoint();
        });
        setPage(Page.DEFAULT);
        addToFrame();
    }

    private void setPage(Page page2) {
        this.page = page2;
        if (this.page == Page.DEFAULT) {
            screenshots.enable();
            help.enable();
            quit.enable();
            chunks.disable();
//            fogDist.disable();
        } else if (this.page == Page.SETTINGS) {
            screenshots.disable();
            help.disable();
            quit.disable();
            chunks.enable();
//            fogDist.enable();
        }
    }

    @Override
    public void initialize(World world) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void mouseEvent(MouseEvent me) {
    }

    private enum Page {
        DEFAULT, SETTINGS;
    }

    Page page = Page.DEFAULT;

    int width = 440;
    int height = 320;

    @Override
    public void render() {
        if (!getPointerHandler().getWorld().isOpen()) {
            return;
        }
        if (page == Page.DEFAULT) {
            width = 440;
            height = 440;
            int innerWidth = 300;
            int x1 = (getParentFrame().width / 2) - (width / 2);
            int y1 = (getParentFrame().height / 2) - (height / 2);
            int y2 = y1 + 85;

            getParent().menuBackground("Menu", x1, y1, width, height);

            quit.draw("Save and Quit", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 40 + 10;
            help.draw("Help Menu", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 40 + 10;
            screenshots.draw("Screenshots", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 40 + 10;
            setPinpoint.draw("Pinpoint Location", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 40 + 10;
            loadPinpoint.draw("Load Pinpoint", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 40 + 10;
            settings.draw("Settings", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
        } else {
            width = 440;
            height = 330;
            int innerWidth = 300;
            int x1 = (getParentFrame().width / 2) - (width / 2);
            int y1 = (getParentFrame().height / 2) - (height / 2);
            int y2 = y1 + 85;

            getParent().menuBackground("Settings", x1, y1, width, height);

            chunks.render(x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 74;

            fill(255, 100);
            textSize(11);
            textAlign(LEFT);
            text("Game Mode:", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth, 20);
            y2 += 20;
            gameMode.draw(VoxelGame.getGameScene().mode.toString(), x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
            y2 += 74;
            settings.draw("Back", x1 + (width / 2) - (innerWidth / 2), y2, innerWidth);
        }
    }

    @Override
    public void onShow() {
        setPage(Page.DEFAULT);
    }

    @Override
    public void onHide() {
    }
}
