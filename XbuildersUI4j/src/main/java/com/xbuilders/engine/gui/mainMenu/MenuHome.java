/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.mainMenu;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.gui.Button;
import static com.xbuilders.engine.gui.mainMenu.MainMenu.menuBackdrop;
import com.xbuilders.engine.game.ScreenshotUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TOP;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.UIExtension;

/**
 *
 * @author zipCoder933
 */
public class MenuHome extends UIExtension implements MenuPage {

    Button loadWorldButton, newWorldButton, quitButton, loadScreenshots;

    MainMenu menu;

    public MenuHome(VoxelGame main, final MainMenu menu, UIExtension f) {
        super(f);
        this.menu = menu;

        loadWorldButton = new Button(this);
        newWorldButton = new Button(this);
        quitButton = new Button(this);
        loadScreenshots = new Button(this);

        quitButton.setAction(new Runnable() {
            @Override
            public void run() {
                getParentFrame().windowCloseEvent();
            }
        });

        newWorldButton.setAction(new Runnable() {
            @Override
            public void run() {
                menu.setPage(MainMenu.MenuPages.NEW_WORLD);
            }

        });

        loadWorldButton.setAction(new Runnable() {
            @Override
            public void run() {
                menu.setPage(MainMenu.MenuPages.LOAD_WORLD);
            }
        });

        loadScreenshots.setAction(new Runnable() {
            @Override
            public void run() {
                try {
                    ScreenshotUtils.loadScreenshotDir();
                } catch (IOException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });


    }

    @Override
    public void mouseEvent(MouseEvent me) {
    }

    @Override
    public void onOpen() {
    }

    final int w = 450;
    final int h = 450;

    @Override
    public void render() {
        menuBackdrop(getParentFrame(), w, h);

        getParentFrame().fill(255);
        getParentFrame().text("X-BUILDERS", w / 2, 20);
        getParentFrame().textSize(14);
        getParentFrame().fill(200, 150);
        getParentFrame().textAlign(CENTER, TOP);
        getParentFrame().text("Block Game", (w / 2), 65);
        int y = 150;
        loadWorldButton.draw("Load World", 30, y, w - 60);//, getParentFrame().width / 2 - w / 2, getParentFrame().height / 2 - h / 2
        y += 50;
        newWorldButton.draw("New World", 30, y, w - 60);//, getParentFrame().width / 2 - w / 2, getParentFrame().height / 2 - h / 2
        y += 50;
        loadScreenshots.draw("View Screenshots", 30, y, w - 60);
        y += 50;
        quitButton.draw("Quit", 30, y, w - 60);//getParentFrame().width / 2 - w / 2, getParentFrame().height / 2 - h / 2
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

}
