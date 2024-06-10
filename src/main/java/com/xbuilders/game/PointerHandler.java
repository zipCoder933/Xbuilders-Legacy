/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game;

import com.xbuilders.engine.Settings;
import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.world.TerrainUpdater;
import com.xbuilders.engine.world.World;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class PointerHandler {

    /**
     * @return the mainThread
     */
    public MainThread getMainThread() {
        return mainThread;
    }

    /**
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    public Settings.SettingsFile getSettingsFile() {
        return settings.getSettingsFile();
    }

    public PointerHandler(UIExtensionFrame applet, World world,
            GameScene game, boolean devMode, Settings settings,
            MainThread mainThread) {
        this.world = world;
        this.game = game;
        this.applet = applet;
        this.devMode = devMode;
        this.settings = settings;
        this.mainThread = mainThread;
    }

    /**
     * @return the devMode
     */
    public boolean isDevMode() {
        return devMode;
    }

    /**
     * @return the applet
     */
    public UIExtensionFrame getApplet() {
        return applet;
    }

    public World getWorld() {
        return world;
    }

    public TerrainUpdater getTerrainUpdater() {
        return world.updater;
    }

    public GameScene getGame() {
        return game;
    }

    public UserControlledPlayer getPlayer() {
        return game.player;
    }
    private MainThread mainThread;
    private Settings settings;
    private boolean devMode;
    private UIExtensionFrame applet;
    private World world;
    private GameScene game;
}
