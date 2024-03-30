/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game;

import com.xbuilders.engine.gui.game.inventory.Inventory;
import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.gui.Button;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.world.World;

import java.io.IOException;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.UIExtension;

/**
 * @author zipCoder933
 */
public class GameMenu extends UIExtension {

    /**
     * @return the pointerHandler
     */
    public PointerHandler getPointerHandler() {
        return pointerHandler;
    }

    /**
     * @return the show
     */
    public boolean isShown() {
        return show;
    }

    /**
     * @return the background
     */
    public UIExtension getBackground() {
        return background;
    }

    private UIExtension background;

    Button close;

    final GameScene gameScene;
    private PointerHandler pointerHandler;

    public GameMenu(final PointerHandler ph, final GameScene gameScene, Runnable quitAction) throws IOException {
        super(gameScene);
        this.pointerHandler = ph;
        close = new Button(this);
        this.gameScene = gameScene;

        close.setAction(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });

//<editor-fold defaultstate="collapsed" desc="background">
        background = new UIExtension(gameScene) {
            {
                addToFrame();
            }

            @Override
            public void mouseEvent(MouseEvent me) {
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


        };
//</editor-fold>
        menu = new Menu(ph, this, quitAction);
        inventory = new Inventory(this);
        menu.disable();
        inventory.disable();
        addToFrame();
    }

    @Override
    public void mouseEvent(MouseEvent me) {
    }


    public boolean inventoryScreenIsShown() {
        return type == MenuType.INVENTORY && inventory.isEnabled();
    }

    private boolean show = false;

    MenuType type = MenuType.BASE_MENU;
    Menu menu;
    public Inventory inventory;

    public void show(MenuType type) {
        this.type = type;
        switch (type) {
            case BASE_MENU:
                menu.enable();
                inventory.disable();
                menu.onShow();
                break;
            case INVENTORY:
                inventory.enable();
                menu.disable();
                inventory.onShow();
                break;
        }
        getBackground().disable();
        enable();
        show = true;
    }

    public void hide() {
        show = false;
        disable();
        getBackground().enable();
        switch (type) {
            case BASE_MENU:
                menu.onHide();
                break;
            case INVENTORY:
                inventory.onHide();
                break;
        }
    }

    public void menuBackground(String title, int x1, int y1, int width, int height) {
        rect(x1, y1, width, height, 2);
        fill(255);
        getParentFrame().text(title, x1 + 25, y1 + 25, width - 50, 20);
        close.draw("X", x1 + width - 25 - 40, y1 + 25, 40);
    }

    public void render() {
        if (isShown()) {
            strokeWeight(1);
            getParentFrame().stroke(0, 100, 255);
            fill(30, 250);
            textSize(16);
            textAlign(LEFT, TOP);

            switch (type) {
                case BASE_MENU:
                    menu.render();
                    break;
                case INVENTORY:
                    inventory.render();
                    break;
            }
        }
    }

    public void initialize(World world) {
        inventory.initialize(world);
        menu.initialize(world);
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
