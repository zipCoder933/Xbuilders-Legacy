/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui;

import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.window.ui4j.UIExtensionFrame;

import java.awt.*;

/**
 *
 * @author zipCoder933
 */
public class GuiUtils {

    public static final Color ACTIVE = new Color(0, 100, 255);

    /**
     * Draws a scroll-bar.width = 14
     *
     * @param f
     * @param x x position
     * @param y y position
     * @param height height of the scrollbar
     * @param scroll the scroll amount
     * @param maxScroll the amount of scroll to take the bar to the bottom of
     * the scrollbar.
     */
    public static void drawScrollbar(UIExtensionFrame f, int x, int y, int height, float scroll, float maxScroll) {
        int width = 14;
        int scrollbarHeight = 50;
        f.noStroke();
        //outer box
        f.fill(255, 50);
        f.rect(x, y, width, height);

        //scroll box
        f.fill(255, 150);
        f.rect(x, (float) MathUtils.mapAndClamp(scroll, 0, maxScroll, y, y + height - scrollbarHeight), width, scrollbarHeight);

    }
}
