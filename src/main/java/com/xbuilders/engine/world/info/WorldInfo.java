// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.info;

import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.VoxelGame;
import org.joml.Vector3f;
import java.text.DateFormat;
import java.nio.file.OpenOption;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.xbuilders.engine.utils.ErrorHandler;
import java.awt.image.RenderedImage;
import com.xbuilders.engine.world.WorldUtils;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.nio.file.Files;
import com.xbuilders.engine.items.ItemQuantity;
import com.google.gson.GsonBuilder;
import com.xbuilders.game.PointerHandler;
import java.io.IOException;
import org.joml.Vector3i;
import com.google.gson.Gson;
import processing.core.PImage;
import java.io.File;

public class WorldInfo
{
    public final int LATEST_TERRAIN_VERSION = 1;
    private File directory;
    final String IMAGE_FILE = "image.png";
    final String INFO_FILE = "info.json";
    PImage image;
    private String name;
    private Gson gson;
    private InfoFile infoFile;
    
    public boolean terrainIsLegacyVersion() {
        return this.getInfoFile().terrainVersion < 1;
    }
    
    public InfoFile getInfoFile() {
        return this.infoFile;
    }
    
    public File getDirectory() {
        return this.directory;
    }
    
    public String getName() {
        return this.name;
    }
    
    public PImage getImage() {
        return this.image;
    }
    
    public float getResolution() {
        return this.getInfoFile().resolution;
    }
    
    public void setResolution(final float resolution) {
        this.infoFile.resolution = resolution;
    }
    
    public boolean hasImage() {
        return new File(this.getDirectory(), "image.png").exists();
    }
    
    public int getTerrainVersion() {
        return this.infoFile.terrainVersion;
    }
    
    public Vector3i getSpawnPoint() {
        if (this.getInfoFile().spawnX == -1.0) {
            return null;
        }
        return new Vector3i((int)this.getInfoFile().spawnX, (int)this.getInfoFile().spawnY, (int)this.getInfoFile().spawnZ);
    }
    
    public int getSize() {
        return this.getInfoFile().size;
    }
    
    public String getLastSaved() {
        return this.getInfoFile().lastSaved;
    }


    
    public int getSeed() {
        return this.getInfoFile().seed;
    }
    
    public void setSeed(final int seed) throws IOException {
        this.infoFile.seed = seed;
        this.save();
    }
    
    private void initialize(final PointerHandler ph) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ItemQuantity.class, new ItemCreator());
        this.gson = gsonBuilder.create();
    }
    
    public WorldInfo(final File directory, final PointerHandler ph) throws IOException {
        if (!directory.exists()) {
            throw new IOException("Directory does not exist");
        }
        this.directory = directory;
        this.name = directory.getName();
        this.initialize(ph);
        this.infoFile = this.gson.fromJson(Files.readString(new File(directory, "info.json").toPath()), InfoFile.class);
        final int BACKPACK_SIZE = ph.getSettingsFile().playerInventorySlots;
        if (this.infoFile.backpack == null) {
            this.infoFile.backpack = new ItemQuantity[BACKPACK_SIZE];
        }
        else if (this.infoFile.backpack.length != BACKPACK_SIZE) {
            final ArrayList<ItemQuantity> finiteResources = new ArrayList<ItemQuantity>();
            for (final ItemQuantity iq : this.infoFile.backpack) {
                if (iq != null && !iq.isInfiniteResource()) {
                    finiteResources.add(iq);
                }
            }
            this.infoFile.backpack = new ItemQuantity[BACKPACK_SIZE];
            try {
                for (int i = 0; i < this.infoFile.backpack.length; ++i) {
                    if (finiteResources.size() > i) {
                        this.infoFile.backpack[i] = finiteResources.get(i);
                    }
                }
            }
            catch (Exception ex) {
                System.out.println("Error filling finite resources into new backpack!");
            }
        }
        if (this.hasImage()) {
            try {
                this.image = new PImage(ImageIO.read(new File(directory, "image.png")));
            }
            catch (IOException ex2) {
                System.out.println("Error loading image file...");
                new File(directory, "image.png").delete();
            }
        }
    }
    
    public WorldInfo(final String name, final String type, final PointerHandler ph) throws IOException {
        this.initialize(ph);
        this.name = WorldUtils.formatWorldName(name);
        this.directory = WorldUtils.newDirectory(name);
        if (!this.directory.exists()) {
            this.directory.mkdirs();
        }
        new File(this.getDirectory(), "info.json").createNewFile();
        this.infoFile = new InfoFile(0, type, ph);
        this.save();
    }
    
    public void saveImage(final PImage image) {
        try {
            ImageIO.write((RenderedImage)image.getImage(), "png", new File(this.getDirectory(), "image.png"));
        }
        catch (IOException e) {
            ErrorHandler.saveErrorToLogFile(e);
        }
    }
    
    public void save() {
        try {
            final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            this.infoFile.lastSaved = dateFormat.format(new Date());
            Files.writeString(new File(this.getDirectory(), "info.json").toPath(), this.gson.toJson(this.getInfoFile()), new OpenOption[0]);
        }
        catch (IOException e) {
            ErrorHandler.report(e);
        }
    }
    
    public void save(final Vector3f playerPos) {
        this.infoFile.spawnX = (int)playerPos.x;
        this.infoFile.spawnY = (int)playerPos.y;
        this.infoFile.spawnZ = (int)playerPos.z;
        this.save();
    }
    
    public void checkAndRemoveNoQuantityItems() {
        for (int i = 0; i < this.getInfoFile().backpack.length; ++i) {
            if (this.getInfoFile().backpack[i] != null) {
                if (VoxelGame.getGameScene().mode == GameMode.WALKTHOUGH && this.getInfoFile().backpack[i].getQuantity() == Item.INFINITE_RESOURCE_AMT) {
                    this.getInfoFile().backpack[i].setQuantity(this.getInfoFile().backpack[i].getItem().maxStackSize());
                }
                else if (this.getInfoFile().backpack[i].getQuantity() <= 0) {
                    this.getInfoFile().backpack[i] = null;
                }
            }
        }
    }
 
    public class InfoFile
    {
        public int size;
        public double spawnX;
        public double spawnY;
        public double spawnZ;
        public int terrainVersion;
        public String lastSaved;
        public String worldType;
        public int seed;
        public float resolution;
        public ItemQuantity[] backpack;
        public double timeOfDay;
        public boolean resetLight;
        
        public InfoFile(final int size, final String worldType, final PointerHandler ph) {
            this.spawnX = -1.0;
            this.spawnY = -1.0;
            this.spawnZ = -1.0;
            this.terrainVersion = 1;
            this.resolution = 1.5f;
            this.timeOfDay = 0.7;
            this.resetLight = false;
            this.backpack = new ItemQuantity[ph.getSettingsFile().playerInventorySlots];
            this.size = size;
            this.worldType = worldType;
        }
 
    }
}
