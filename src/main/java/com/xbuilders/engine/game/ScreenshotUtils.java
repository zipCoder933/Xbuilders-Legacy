/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.game;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.utils.ResourceUtils;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class ScreenshotUtils {

    public static File SCREEN_SHOTS_DIR;
    static boolean saveImage = false;
    static int screenshotId = 0;
    static int flashTime = 0;

    public static void initialize() {
        SCREEN_SHOTS_DIR = ResourceUtils.appDataResource("screenshots");
    }

    protected static void newGame() {
        saveImage = false;
        screenshotId = 0;
    }

    protected static void takeScreenshot(GameScene game) {
        screenshotId++;
        game.alert("Screenshot " + screenshotId + " Saved!");
        saveImage = true;
    }

    protected static void draw(UIExtensionFrame frame) {
        if (saveImage) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
            File path = ScreenshotUtils.screenShotFile(VoxelGame.getWorld().infoFile.getName() + " screenshot (" + date + ").png");
            System.out.println("Saving screenshot to:\t" + path);
            frame.saveFrame(path.getAbsolutePath());
            flashTime = 0;
            saveImage = false;
        }
        if (flashTime < 2) {
            frame.background(230);
            flashTime++;
        }
    }

    public static File screenShotFile(String path) {
        if (!SCREEN_SHOTS_DIR.exists()) {
            SCREEN_SHOTS_DIR.mkdirs();
        }
        return new File(SCREEN_SHOTS_DIR.getAbsolutePath() + "\\" + path);
    }

    public static void loadScreenshotDir() throws IOException {
        if (!SCREEN_SHOTS_DIR.exists()) {
            SCREEN_SHOTS_DIR.mkdirs();
        }
        Desktop.getDesktop().open(SCREEN_SHOTS_DIR);
    }
}
