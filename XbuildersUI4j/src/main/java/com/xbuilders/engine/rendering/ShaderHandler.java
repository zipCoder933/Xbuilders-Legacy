/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.rendering;

import com.xbuilders.engine.VoxelGame;

import static com.xbuilders.engine.VoxelGame.getShaderHandler;

import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTextureAtlas;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;

import java.util.ArrayList;

import com.xbuilders.game.Main;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.PGraphics;
import processing.opengl.PShader;
import processing.ui4j.UIExtensionFrame;

/**
 * @author zipCoder933
 */
public class ShaderHandler {

    /**
     * @return the flashlightMode
     */
    public boolean isFlashlightMode() {
        return flashlightMode;
    }

    /**
     * @return the naturalBackgroundEnabled
     */
    public boolean isNaturalBackgroundEnabled() {
        return naturalBackgroundEnabled;
    }

    private final Matrix4f identityMatrix = new Matrix4f();

    public void resetModelMatrix() {
        blockShader.set("modelMatrix", identityMatrix);
    }

    public void setBlockShaderModelMatrix(Matrix4f modelMatrix) {
        blockShader.set("modelMatrix", modelMatrix);
    }

//    public void setentityShaderModelMatrix(Matrix4f modelMatrix) {
//        //entityShader.set("modelMatrix", modelMatrix);
//    }

    /**
     * @param naturalBackgroundEnabled the naturalBackgroundEnabled to set
     */
    public void setNaturalBackgroundEnabled(boolean naturalBackgroundEnabled) {
        this.naturalBackgroundEnabled = naturalBackgroundEnabled;
    }

    public final Vector3i blackColor = new Vector3i(0, 0, 0);
    public final Vector3i daytimeColor = new Vector3i(180, 204, 255);
    public final Vector3i nightTimeColor = new Vector3i(10, 15, 38);

    /**
     * @return the daylightLevel
     */
    public float getDaylightLevel() {
        return daylightLevel;
    }

    /**
     * @return the currentSkyColor
     */
    public Vector3f getSkyColor() {
        return currentSkyColor;
    }

    public void setSkyColor(int r, int g, int b) {
        this.currentSkyColor.x = r;
        this.currentSkyColor.y = g;
        this.currentSkyColor.z = b;

        float red = (float) r / 255;
        float green = (float) g / 255;
        float blue = (float) b / 255;
        blockShader.set("sky_color", red, green, blue);
        cloudShader.set("sky_color", red, green, blue);
        //entityShader.set("sky_color", red, green, blue);
    }

    @Deprecated
    public void setWorldSpaceOffset(float x, float y, float z) {
        //No longer needed
//        blockShader.set("worldSpaceOffset", x, y, z);
    }

    public void setMaxBrightness(float value) {
        blockShader.set("maxBrightness", value);
    }

    public void setWorldSpaceOffset(Vector3f offset) {
        setWorldSpaceOffset(offset.x, offset.y, offset.z);
    }

    private final Vector3f currentSkyColor;
    private float fogDist;

    public void setFogDistance(float multiplier) {
        //For some reason, I think setting the chunk dist to terrain value cause the fog to be incorrect
        fogDist = (VoxelGame.getSettings().getSettingsFile().chunkRadius - 5) * multiplier;
        if (lastDistance != fogDist) {
            lastDistance = fogDist;
            blockShader.set("fog_dist", fogDist);
            //entityShader.set("fog_dist", fogDist);
        }
    }

    public void setSunlightMultiplier(float value) {
        value = (float) MathUtils.clamp(value, 0, 1);
        if (lastSunlightValue != value) {
            lastSunlightValue = value;
            blockShader.set("sunlightMultiplier", value);
            cloudShader.set("sunlightMultiplier", value);
            //entityShader.set("sunlightMultiplier", value);
        }
    }

    private boolean naturalBackgroundEnabled = true;

    /**
     * Prevents the day/night cycle from interfering with a custom background or
     * fog color
     */
    /**
     * Allows the day/night cycle to automatically set the background and fog
     * color
     */
    public void setAnimatedTexturesEnabled(boolean enabled) {
        blockShader.set("animatedTextures", enabled);
    }

    public void setLightValueAroundPlayer(byte value) {
        if (lastPlayerLight != value) {
            lastPlayerLight = value;
            playerLight = MathUtils.mapAndClamp(value, 0, 15, 0.0f, 1.0f);
            blockShader.set("playerLight", playerLight);
            //entityShader.set("playerLight", playerLight);
        }
    }

    private float daylightLevel, lastPlayerLight;
    public double defaultSunlightLevel = 0;
    long lastUpdate;
    private float playerLight;
    private float lastSunlightValue = -1;
    private float lastDistance = -1;
    private double timeOfDay = 1;

    public void setDaylightLevel(float value) {
        value = MathUtils.clamp(value, 0, 1);
        daylightLevel = value;
        setSunlightMultiplier(MathUtils.map(value, 0, 1, 0.1f, 1.1f));
        if (isNaturalBackgroundEnabled()) {
            if (playerLight < 0.5f) {
                setSkyColor(
                        (int) MathUtils.curve(currentSkyColor.x, 0, 0.01f),
                        (int) MathUtils.curve(currentSkyColor.y, 0, 0.01f),
                        (int) MathUtils.curve(currentSkyColor.z, 0, 0.01f)
                );
            } else {
                setSkyColor(
                        (int) MathUtils.map(value, 1, 0, daytimeColor.x, nightTimeColor.x),
                        (int) MathUtils.map(value, 1, 0, daytimeColor.y, nightTimeColor.y),
                        (int) MathUtils.map(value, 1, 0, daytimeColor.z, nightTimeColor.z));
            }
        }
    }

    /**
     * Preforms all the operations necessary at the beginning of the game.
     */
    public void startGame() {
        setTimeOfDay(VoxelGame.getWorld().infoFile.getInfoFile().timeOfDay);
        setNaturalBackgroundEnabled(true);
        setFlashlightMode(false);
        animatedBlockTime = 0;
    }


    public void update(int frameCount) {
        if (System.currentTimeMillis() - lastUpdate > 200) {
            if (VoxelGame.getSettings().getSettingsFile().dayNightCycles && !Main.DEV_MODE) {
                setTimeOfDay(getTimeOfDay() + 0.001);
                if (getTimeOfDay() > MathUtils.PI * 2) {
                    setTimeOfDay(0);
                }
                VoxelGame.getWorld().infoFile.getInfoFile().timeOfDay = getTimeOfDay();
                defaultSunlightLevel = MathUtils.map(Math.sin(getTimeOfDay()), -1, 1, -0.1, 1.1);
                defaultSunlightLevel = MathUtils.clamp(defaultSunlightLevel, 0.01, 1);
                setDaylightLevel((float) defaultSunlightLevel + 0.1f);
            } else {
                getShaderHandler().setDaylightLevel(1);
            }
            changeAnimatedBlockTime();
            lastUpdate = System.currentTimeMillis();
        }
    }

    int animatedBlockTime = 0;

    public void changeAnimatedBlockTime() {
        animatedBlockTime++;
        if (animatedBlockTime > Integer.MAX_VALUE - 1) {
            animatedBlockTime = 0;
        }
        blockShader.set("tick", animatedBlockTime);
    }

    public static PShader cloudShader, backgroundShader;
    public static PShader blockShader; //entityShader;

    public ShaderHandler(UIExtensionFrame f, BlockTextureAtlas tex, Block[] blockList) {
        blockShader = f.loadShader(ResourceUtils.resourcePath("Shaders/Frag.glsl"),
                ResourceUtils.resourcePath("Shaders/Vert.glsl"));
        //entityShader = f.loadShader(ResourceUtils.resourcePath("Shaders/entity/entity_frag.glsl"),
//                ResourceUtils.resourcePath("Shaders/entity/entity_vert.glsl"));
        cloudShader = f.loadShader(ResourceUtils.resourcePath("Shaders/clouds/Frag.glsl"),
                ResourceUtils.resourcePath("Shaders/clouds/Vert.glsl"));
        backgroundShader = f.loadShader(ResourceUtils.resourcePath("Shaders/background/Frag.glsl"),
                ResourceUtils.resourcePath("Shaders/background/Vert.glsl"));
        initializeAnimatedBlocks(blockList, blockShader);
        currentSkyColor = new Vector3f(daytimeColor);
        setDaylightLevel(0);
        animatedBlockTime = 0;
        float textureMapWidth = ItemList.blocks.textureAtlas.getImageWidth();
        float individualTextureSize = ItemList.blocks.textureAtlas.getIndividualTextureSize();
        float tileSize = (individualTextureSize / textureMapWidth);
        int numberOfTilesWidth = (int) (textureMapWidth / individualTextureSize);

        blockShader.set("textureMapWidth", textureMapWidth);
        blockShader.set("individualTextureSize", individualTextureSize);
        blockShader.set("numberOfTilesWidth", numberOfTilesWidth);
        blockShader.set("tileSize", tileSize);
        //entityShader.set("textureMapWidth", textureMapWidth);
        //entityShader.set("individualTextureSize", individualTextureSize);
        //entityShader.set("numberOfTilesWidth", numberOfTilesWidth);
        //entityShader.set("tileSize", tileSize);
        setMaxBrightness(1.1f);
        setFogDistance(1);
    }

    // <editor-fold defaultstate="collapsed" desc="Initialize animated textures">
    final static int MAX_ANIMATED_TEXTURES = 99;

    public void initializeAnimatedBlocks(Block[] blockList, PShader blockShader) {
        ArrayList<Integer> animatedX = new ArrayList<>();
        ArrayList<Integer> animatedY = new ArrayList<>();
        ArrayList<Integer> animatedDist = new ArrayList<>();
        // System.out.println("Configuring animated items:");

        for (Block block : blockList) {
            if (block.getAnimationLength() > 1) {
                // System.out.println("\tAnimated block: " + block.getName());
                animatedX.add(block.texture.FRONT[0]);
                animatedY.add(block.texture.FRONT[1]);
                animatedDist.add(block.getAnimationLength());

                if (block.texture.TOP[0] != block.texture.FRONT[0]
                        || block.texture.TOP[1] != block.texture.FRONT[1]) {
                    animatedX.add(block.texture.TOP[0]);
                    animatedY.add(block.texture.TOP[1]);
                    animatedDist.add(block.getAnimationLength());
                }
                if (block.texture.BOTTOM[0] != block.texture.FRONT[0]
                        || block.texture.BOTTOM[1] != block.texture.FRONT[1]) {
                    animatedX.add(block.texture.BOTTOM[0]);
                    animatedY.add(block.texture.BOTTOM[1]);
                    animatedDist.add(block.getAnimationLength());
                }
            }
        }
        animatedX.add(-1);
        animatedY.add(-1);
        animatedDist.add(0);

        int[] animX = new int[MAX_ANIMATED_TEXTURES];
        int[] animY = new int[MAX_ANIMATED_TEXTURES];
        int[] animationDurtion = new int[MAX_ANIMATED_TEXTURES];
        for (int i = 0; i < animatedX.size(); i++) {
            if (i >= MAX_ANIMATED_TEXTURES) {
                System.out.println("NUMBER OF ANIMATED TEXTURES EXCEED MAX LIMIT.");
                break;
            }
            animX[i] = animatedX.get(i);
            animY[i] = animatedY.get(i);
            animationDurtion[i] = animatedDist.get(i);
        }
        System.out.println("Animated textures in use: " + animatedX.size());
        blockShader.set("animatedX", animX);
        blockShader.set("animatedY", animY);
        blockShader.set("animationDuration", animationDurtion);
    }
    // </editor-fold>

    /**
     * @return the timeOfDay
     */
    public double getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * @param timeOfDay the timeOfDay to set
     */
    public void setTimeOfDay(double timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public static final float DAYLIGHT_TIME_OF_DAY = 0.7f;
    // public static double NIGHT_TIME_OF_DAY = 1.2f;
    private boolean flashlightMode = false;

    public void setFlashlightMode(boolean b) {
        flashlightMode = b;
        blockShader.set("flashlightMode", b);
        //entityShader.set("flashlightMode", b);
    }

//    public static void setEntityLightLevel(int level) {
//        entityShader.set("lightLevel", level);
//    }

    public void setShader(PGraphics graphics) {
        graphics.shader(blockShader);
    }

}
