/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xbuilders.engine.gui.mainMenu;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.progress.ProgressData;
import com.xbuilders.game.Main;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import processing.core.KeyCode;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.UIExtension;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class MainMenu extends UIExtension {

    ProgressBarScreen progressBarScreen;
    LoadWorld loadWorld;
    NewWorld newWorld;
    MenuHome home;

    PImage background = null;
    Random wallpaperRandom;

    private void selectRandomBackground() {
        try {
            File[] wallpapers = ResourceUtils.resource("wallpaper").listFiles();
            File f = wallpapers[wallpaperRandom.nextInt(wallpapers.length)];
            background = new PImage(ImageIO.read(f));
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startGame(ProgressData prog) {
        progressBarScreen.setProgressData(prog);
        setPage(MainMenu.MenuPages.PROGRESS_BAR);
    }

    public MainMenu(PointerHandler ph, VoxelGame main, UIExtension parentExt) {
        super(parentExt); //if the menu is disabled, than the pages under this should be too
        wallpaperRandom = new Random();

        selectRandomBackground();

        //==============================
        progressBarScreen = new ProgressBarScreen(main, this, this);
        loadWorld = new LoadWorld(ph, this, this);
        newWorld = new NewWorld(ph, main, this, this);
        home = new MenuHome(main, this, this);
        setPage(MenuPages.HOME);
        addToFrame();
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (keyIsPressed(KeyCode.CTRL) && keyIsPressed(KeyCode.SHIFT) && keyIsPressed(KeyCode.W)
                && !newWallpaper) {
            selectRandomBackground();
            newWallpaper = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        newWallpaper = false;
    }

    @Override
    public void onDisable() {
    }

    public enum MenuPages {
        NEW_WORLD,
        LOAD_WORLD,
        HOME,
        PROGRESS_BAR
    }

    MenuPages currentPage;

    public void setPage(MenuPages page) {
        if (null != page) {
            currentPage = page;
            switch (page) {
                case HOME:
                    home.onOpen();
                    home.enable();
                    loadWorld.disable();
                    newWorld.disable();
                    progressBarScreen.disable();
                    loadWorld.onClose();
                    break;
                case LOAD_WORLD:
                    home.disable();
                    loadWorld.enable();
                    newWorld.disable();
                    progressBarScreen.disable();
                    loadWorld.onOpen();
                    break;
                case NEW_WORLD:
                    home.disable();
                    loadWorld.disable();
                    newWorld.enable();
                    progressBarScreen.disable();
                    newWorld.onOpen();
                    loadWorld.onClose();
                    break;
                case PROGRESS_BAR:
                    home.disable();
                    loadWorld.disable();
                    newWorld.disable();
                    progressBarScreen.enable();
                    loadWorld.onClose();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mouseEvent(MouseEvent me) {
    }

    boolean newWallpaper = false;

    public void draw() {
        drawWallpaper(background);
//
        if (null == currentPage) {
            home.render();
        } else {
            switch (currentPage) {
                case PROGRESS_BAR:
                    progressBarScreen.render();
                    break;
                case NEW_WORLD:
                    newWorld.render();
                    break;
                case LOAD_WORLD:
                    loadWorld.render();
                    break;
                default:
                    home.render();
                    break;
            }
        }
//
        fill(255, 0, 0);
        VoxelGame.getMessageBox().render();
    }

    protected static void menuBackdrop(UIExtensionFrame f, int w, int h) {
        f.noStroke();
        f.fill(255, 100);
        f.textAlign(LEFT, TOP);
        f.textSize(10);
        f.text(Main.VERSION_NOTES + (Main.DEV_MODE ? " (DEV MODE)" : ""), 10, 10);
        f.translate(f.width / 2 - w / 2, f.height / 2 - h / 2);
        f.fill(50, 200);
        f.rect(0, 0, w, h, 4);
        f.textSize(24);
        f.textAlign(CENTER, TOP);
    }

    public void drawWallpaper(PImage image) {
        if (image != null) {
            // getParentFrame().image(wallpaper, 0, 0, getParentFrame().width, getParentFrame().height);
            //Fit the image to the window size, but keep the aspect ratio.
            int imageWidth = image.width;
            int imageHeight = image.height;
            int windowWidth = getParentFrame().width;
            int windowHeight = getParentFrame().height;
            float scaleX = (float) windowWidth / (float) imageWidth;
            float scaleY = (float) windowHeight / (float) imageHeight;
            //Dont want black bars, so we scale the image down to fit the window.
            float scale = scaleX > scaleY ? scaleX : scaleY;
            int imageX = (int) ((windowWidth - imageWidth * scale) / 2);
            int imageY = (int) ((windowHeight - imageHeight * scale) / 2);
            getParentFrame().image(image, imageX, imageY, imageWidth * scale, imageHeight * scale);
        }
    }

}
