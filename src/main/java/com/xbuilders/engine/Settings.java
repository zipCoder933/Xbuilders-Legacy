/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine;

import com.google.gson.Gson;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author zipCoder933
 */
public class Settings {

    /**
     * @return the settingsFile
     */
    public SettingsFile getSettingsFile() {
        return settingsFile;
    }

    public class SettingsFile {
        public boolean additionalFeatures = false;
        public int chunkRadius = 100;
        public int minChunkDistance = 60;
        public int maxChunkDistance = 200;

        public int extraChunkDistMultiplier = 0;
        public boolean switchMouseButtons = false;
        public boolean dayNightCycles = true;
        public float walkSpeed = 1.4f;
        public float runSpeed = 7.0f;
        public float flySpeed = 2f;
        public int playerRayMaxDistance = 22;
        public long blockAutoSetInterval = 200;
        public int playerInventorySlots = 24;
        public long blockAutoSetTimeThreshold = 500;
        public int maxBlockBoundaryArea = 1000000;
        public float entityMaxDistance = 48; //The distance threshold before an entity is not shown or updated (static meshed entities are still shown)
        public boolean disableVsync = false; //Vsync may improve fps (i havent noticed that) but it also makes memory usage skyrocket
    }

    private SettingsFile settingsFile;
    final File filepath;

    public Settings(boolean devMode) throws IOException {
        System.out.println("Loading settings...");
        filepath = ResourceUtils.worldsResource("settings.json");
        if (filepath.exists()) {
            String file = Files.readString(filepath.toPath());
            settingsFile = new Gson().fromJson(file, SettingsFile.class);
        } else {
            settingsFile = new SettingsFile();
            save();
        }

        if (devMode) {
            System.out.println("DEVMODE: Creating new settings...");
            settingsFile = new SettingsFile();
//            settingsFile.runSpeed = 15;
            settingsFile.dayNightCycles = false;
        }

        settingsFile.chunkRadius = MathUtils.clamp(settingsFile.chunkRadius, 60, settingsFile.maxChunkDistance);
        settingsFile.playerRayMaxDistance = MathUtils.clamp(settingsFile.playerRayMaxDistance, 1, 1000);
    }

    public void setChunkDistance(int dist) throws IOException {
        settingsFile.chunkRadius = dist;
        VoxelGame.getShaderHandler().setFogDistance(1.0f);
        save();
    }

    private void save() throws IOException {
        Files.writeString(filepath.toPath(), new Gson().toJson(getSettingsFile(), SettingsFile.class));
    }

}
