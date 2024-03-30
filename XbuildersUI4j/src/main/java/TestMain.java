/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PImage;
import processing.core.UIFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 *
 * @author zipCoder933
 */
public class TestMain extends UIFrame {

    private String getMemoryUsageAsString() {
        long free = Runtime.getRuntime().freeMemory();
        long total = Runtime.getRuntime().totalMemory();
        return String.format("Used: %s    Total: %s",
                formatMemoryValue(total - free),
                formatMemoryValue(total));
    }

    private String formatMemoryValue(long bytes) {
        if (bytes > 1024 * 1024) {
            return String.format("%.2f", ((float) ((float) bytes / 1024) / 1024)) + "mb";
        } else if (bytes > 1024) {
            return String.format("%.2f", ((float) bytes / 1024)) + "kb";
        } else {
            return String.format("%.2f", bytes) + "b";
        }
    }

    public static void main(String args[]) {
        new TestMain();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public TestMain() {
        super();
        startWindow();
    }
    PImage image;

    @Override
    public void settings() {
        size(800, 800, P3D);
        File file = new File("C:\\Users\\sampw\\OneDrive\\Code Projects\\Java\\Projects\\XBuilders\\app\\resources\\icon.png");
        setIcon(file);
        try {
            image = new PImage(file);
        } catch (IOException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void setup() {
        noStroke();
    }

    @Override
    public void keyTyped(KeyEvent event) {
        System.out.println("Key event: " + event.toString());
    }

    @Override
    public void windowFocusGained() {
        System.out.println("Focus gained");
    }

    @Override
    public void windowFocusLost() {
        System.out.println("Focus lost");
    }

    @Override
    public void windowResized() {
    }

    @Override
    public void draw() {
        image(image, 0, 0, width, height);
        setTitle(getMemoryUsageAsString());
        translate(150, 150);
        fill(255, 0, 0);
        rect(0, 0, 500, 500);
        fill(0);
        text(frameRate + "", 20, 20);
    }

    @Override
    public void mouseEvent(MouseEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void windowCloseEvent() {
        System.out.println("Close event!");
//        stopWindow();
//        stop();
//        closeWindow();
    }

}
