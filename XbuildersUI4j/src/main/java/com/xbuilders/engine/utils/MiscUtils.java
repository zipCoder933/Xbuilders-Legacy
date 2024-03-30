/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils;

import com.xbuilders.engine.utils.math.MathUtils;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.joml.Vector4i;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

/**
 *
 * @author zipCoder933
 */
public class MiscUtils {

    static Scanner scanner = new Scanner(System.in);

    public static boolean readYesOrNoInput(String text) {

        System.out.print(text + " (Y/n):");
        char userInput = scanner.next().charAt(0);
        return userInput == 'y' || userInput == 'Y';
    }

    public static void waitForKey() {
        scanner.next();
    }


    public static PVector[] rotateVerticiesYAxis(PVector[] verts, double rotationRadians) {
        if (rotationRadians == 0) {
            return verts;
        }

        final float center = 0.5f;
        PVector[] verts2 = new PVector[verts.length];

        for (int i = 0; i < verts.length; i++) {
            verts2[i] = new PVector(0, 0, 0);
            verts2[i].y = verts[i].y;
            double sin = Math.sin(rotationRadians);
            double cos = Math.cos(rotationRadians);
            verts2[i].x = (float) (center + (verts[i].x - center) * cos - (verts[i].z - center) * sin);
            verts2[i].z = (float) (center + (verts[i].x - center) * sin + (verts[i].z - center) * cos);
        }
        return verts2;
    }

    public static Vector3f[] rotateVerticiesYAxis(Vector3f[] verts, double rotationRadians) {
        if (rotationRadians == 0) {
            return verts;
        }

        final float center = 0.5f;
        Vector3f[] verts2 = new Vector3f[verts.length];

        for (int i = 0; i < verts.length; i++) {
            verts2[i] = new Vector3f(0, 0, 0);
            verts2[i].y = verts[i].y;
            double sin = Math.sin(rotationRadians);
            double cos = Math.cos(rotationRadians);
            verts2[i].x = (float) (center + (verts[i].x - center) * cos - (verts[i].z - center) * sin);
            verts2[i].z = (float) (center + (verts[i].x - center) * sin + (verts[i].z - center) * cos);
        }
        return verts2;
    }

    public static void stopThreadAndWaitForCompletion(Thread thread) throws InterruptedException {
        thread.interrupt();
        while (thread.isAlive()) {
            Thread.sleep(100);
        }
        Thread.sleep(100);
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(MiscUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Random random2 = new Random();

    public static Random getRandom() {
        return random2;
    }

    public static boolean randomBool() {
        return random2.nextBoolean();
    }

    public static double random(int lowerBound, int upperBound) {
        return random2.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static double random(double lowerBound, double upperBound) {
        return (random2.nextDouble() * (upperBound - lowerBound)) + lowerBound;
    }

    public static int randomInt(int lowerBound, int upperBound) {
        return random2.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static long randomLong(long lowerBound, long upperBound) {
        return random2.nextInt((int) (upperBound - lowerBound)) + lowerBound;
    }

    public static boolean randomBool(Random random) {
        return random.nextBoolean();
    }

    public static double random(Random random, int lowerBound, int upperBound) {
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static double random(Random random, double lowerBound, double upperBound) {
        return (random.nextDouble() * (upperBound - lowerBound)) + lowerBound;
    }

    public static int randomInt(Random random, int lowerBound, int upperBound) {
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static long randomLong(Random random, long lowerBound, long upperBound) {
        return random.nextInt((int) (upperBound - lowerBound)) + lowerBound;
    }

    public static void openHelpMenu() {
        System.out.println("Opening Help Menu...");
        File htmlFile = new File(ResourceUtils.resourcePath("help-menu\\help.html"));
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    /**
     *
     * @param start
     * @param end
     * @param percent
     * @return
     */
    public static Color mixColors(Color start, Color end, double percent) {
        percent = MathUtils.clamp(percent, 0, 1);
        return new Color((int) (end.getRed() * percent + start.getRed() * (1.0 - percent)),
                (int) (end.getGreen() * percent + start.getGreen() * (1.0 - percent)),
                (int) (end.getBlue() * percent + start.getBlue() * (1.0 - percent)));
    }

    public static Color colorSatutation(Color color, double saturation) {
        int val = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        Color colorGrey = new Color(val, val, val);
        return mixColors(colorGrey, color, saturation);
    }

    public static String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        if (str.length() == 1) {
            return str.toUpperCase();
        }

        //split the string by space
        String[] parts = str.split(" ");

        StringBuilder sb = new StringBuilder(str.length());

        for (String part : parts) {

            if (part.length() > 1) {
                sb.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1).toLowerCase());
            } else {
                sb.append(part.toUpperCase());
            }

            sb.append(" ");
        }

        return sb.toString().trim();
    }

    public static String timeSinceStr(long lastSaveTime) {
        int secondsAgo = (int) (System.currentTimeMillis() - lastSaveTime) / 1000;

        if (secondsAgo < 5) {
            return "Just now";
        } else if (secondsAgo < 60) {
            return secondsAgo + "s ago";
        } else {
            return ((int) secondsAgo / 60) + "m ago";
        }
    }

    public static String snakeToCamel(String str) {
        // Capitalize first letter of string
        str = str.substring(0, 1).toUpperCase()
                + str.substring(1);

        // Convert to StringBuilder
        StringBuilder builder
                = new StringBuilder(str);

        // Traverse the string character by
        // character and remove underscore
        // and capitalize next letter
        for (int i = 0; i < builder.length(); i++) {

            // Check char is a space
            if (builder.charAt(i) == ' ') {

                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        // Return in String type
        return builder.toString();
    }

    public static <K, V> void printHashmap(HashMap<K, V> map) {
        for (Map.Entry<K, V> set : new HashMap<>(map).entrySet()) {
            System.out.println(set.getKey().toString() + " = " + set.getValue().toString());
        }
        System.out.println("");
    }

    public static PShape makeShape(PApplet applet, File shapePath, File texturePath) {
        PShape shape = applet.loadShape(shapePath.getAbsolutePath());
        shape.setTexture(applet.loadImage(texturePath.getAbsolutePath()));
        return shape;
    }

    public static PShape makeShape(PApplet applet, File shapePath, PImage texture) {
        PShape shape = applet.loadShape(shapePath.getAbsolutePath());
        shape.setTexture(texture);
        return shape;
    }

    public static boolean isBlackCube(int x, int y, int z) {
        // Assume that the checkerboard has a size of 1 unit by 1 unit by 1 unit, and
        // that the origin (0, 0, 0) is black
        // To determine the color of any point (x, y, z), you can use the formula: (x +
        // y + z) % 2 == 0
        // This means that if the sum of the coordinates is even, the point is black;
        // otherwise, it is white
        return (x + y + z) % 2 == 0;
    }

    // Print Vector methods
    public static String printVector(Vector2f vec) {
        return vec.x + "," + vec.y;
    }

    public static String printVector(Vector2i vec) {
        return vec.x + "," + vec.y;
    }

    public static String printVector(Vector2d vec) {
        return vec.x + "," + vec.y;
    }

    public static String printVector(Vector3f vec) {
        return vec.x + "," + vec.y + "," + vec.z;
    }

    public static String printVector(Vector3i vec) {
        return vec.x + "," + vec.y + "," + vec.z;
    }

    public static String printVector(Vector3d vec) {
        return vec.x + "," + vec.y + "," + vec.z;
    }

    public static String printVector(Vector4f vec) {
        return vec.x + "," + vec.y + "," + vec.z + "," + vec.w;
    }

    public static String printVector(Vector4i vec) {
        return vec.x + "," + vec.y + "," + vec.z + "," + vec.w;
    }

    public static String printVector(Vector4d vec) {
        return vec.x + "," + vec.y + "," + vec.z + "," + vec.w;
    }

}
