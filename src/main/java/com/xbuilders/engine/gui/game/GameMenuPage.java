/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xbuilders.engine.gui.game;

import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.world.World;
import com.xbuilders.game.PointerHandler;
import java.awt.Rectangle;
import com.xbuilders.window.ui4j.UIExtension;

/**
 *
 * @author sampw
 */
public abstract class GameMenuPage extends UIExtension {

    /**
     * @return the gameScene
     */
    public GameScene getGameScene() {
        return gameScene;
    }

    /**
     * @return the parent
     */
    public GameMenu getParent() {
        return parent;
    }

    /**
     * @return the pointerHandler
     */
    public PointerHandler getPointerHandler() {
        return parent.getPointerHandler();
    }
    private final GameScene gameScene;
    private final GameMenu parent;

    public GameMenuPage(GameMenu parent) {
        super(parent);
        this.gameScene = parent.gameScene;
        this.parent = parent;
    }

    public Rectangle drawBackground(int width, int height) {
        int x1 = (getParentFrame().width / 2) - (width / 2);
        int y1 = (getParentFrame().height / 2) - (height / 2);
        getParent().menuBackground("Item Selection", x1, y1, width, height);
        return new Rectangle(x1, y1, width, height);
    }
    
    public abstract void render();

    public abstract void onShow();

    public abstract void onHide();

    public abstract void initialize(World world);
}
