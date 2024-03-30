/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine;

import com.google.gson.Gson;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.Main;

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
        public float SLM_RadiusMultiplier = 0.7f;//(was 0.65) Affects the radius of chunk generation as well
        public int chunkRadius = 160;

        public int minChunkDistance = 60;//Maximum chunk size
        public int maxChunkDistance = 208;//Maximum chunk size
        public int extraChunkDistMultiplier = 0;   //2 = render 16*2 extra chunk distance, Also be aware that the terrain updater will delete w/ chunk distance + 16

        //Misc:
        public boolean switchMouseButtons = false;
        public boolean dayNightCycles = true;

        //Player movement
        public float walkSpeed = 1.4f;
        public float runSpeed = 7.0f;
        public float flySpeed = 1.5f;

        //Blocks
        public int playerRayMaxDistance = 22;
        public long blockAutoSetInterval = 200;
        public int playerInventorySlots = 24;
        public long blockAutoSetTimeThreshold = 500;
        public int maxBlockModeSize = 25;

        //boundary
        public int maxBlockBoundaryArea = 1000000;

        //Entities
        public float entityMaxDistance = SubChunk.WIDTH * 3; //The distance threshold before an entity is not shown or updated (static meshed entities are still shown)
    }

    private SettingsFile settingsFile;
    final File filepath;

    public Settings() throws IOException {
        System.out.println("SETTINGS:");
        filepath = ResourceUtils.worldsResource("settings.json");

        if (filepath.exists()) {
            System.out.println("Loading settings...");
            String file = Files.readString(filepath.toPath());
            settingsFile = new Gson().fromJson(file, SettingsFile.class);
        } else {
            System.out.println("Creating new settings...");
            settingsFile = new SettingsFile();
            save();
        }

        if(Main.DEV_MODE){
            settingsFile.runSpeed = 15;
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
