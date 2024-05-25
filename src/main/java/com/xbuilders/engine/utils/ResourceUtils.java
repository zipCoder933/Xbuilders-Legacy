/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xbuilders.engine.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channel;
import java.nio.file.Path;
import java.nio.file.Paths;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * @author zipCoder933
 */
public class ResourceUtils {

    public final static File RESOURCE_DIR;
    public final static File APP_DATA_DIR;
    public static final File LOCAL_DIR;
    public static File WORLDS_DIR;

    static {
        System.out.println("RESOURCES:");
        LOCAL_DIR = new File(System.getProperty("user.dir"));
        System.out.println("\tLocal dir: " + LOCAL_DIR);

        RESOURCE_DIR = new File(LOCAL_DIR, "res");
        RESOURCE_DIR.mkdirs();
        System.out.println("\tResource dir: " + RESOURCE_DIR);

        APP_DATA_DIR = new File(System.getenv("LOCALAPPDATA") + "\\xbuilders");
        APP_DATA_DIR.mkdirs();
        System.out.println("\tApp Data Dir: " + APP_DATA_DIR);
    }

    public static void initialize(boolean gameDevResources) {
        WORLDS_DIR = new File(APP_DATA_DIR + (gameDevResources ? "\\game_dev" : "\\game"));
        WORLDS_DIR.mkdirs();
        System.out.println("\tGame Dir: " + WORLDS_DIR);
    }

    public static File resource(String path) {
        return new File(RESOURCE_DIR, path);
    }

    public static String resourcePath(String path) {
        return RESOURCE_DIR.getAbsolutePath() + "\\" + path;
    }

    public static File appDataResource(String path) {
        return new File(APP_DATA_DIR, path);
    }

    public static String appDataResourcePath(String path) {
        return APP_DATA_DIR.getAbsolutePath() + "\\" + path;
    }

    public static File worldsResource(String path) {
        return new File(WORLDS_DIR, path);
    }

    public static String gameResourcePath(String path) {
        return WORLDS_DIR.getAbsolutePath() + "\\" + path;
    }

    public static boolean fileIsBeingUsed(File file) {
        boolean used;
        Channel channel = null;
        try {
            channel = new RandomAccessFile(file, "rw").getChannel();
            used = false;
        } catch (Exception ex) {
            used = true;
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException ex) {
                    // exception handling
                }
            }
        }
        return used;
    }

}
