/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.window.ui4j;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import processing.core.KeyCode;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 *
 * @author zipCoder933
 */
public abstract class UIExtension {

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return parentIsEnabled() && enabled;
    }

    public void disable() {
        this.enabled = false;
        onDisable();
    }

    public void enable() {
        this.enabled = true;
        onEnable();
    }

    /**
     * Returns wether the components parent element is enabled. If not, the
     * child element cannot be active either.
     *
     * @return parent is enabled
     */
    public boolean parentIsEnabled() {
        if (parentExtension == null) {
            return true;
        } else {
            return parentExtension.isEnabled();
        }
    }

    public UIExtension(UIExtensionFrame frame) {
        this.parentFrame = frame;
        added = false;
    }

    UIExtensionFrame parentFrame;
    UIExtension parentExtension;

    public UIExtension(UIExtension parentExtension) {
        this.parentFrame = parentExtension.getParentFrame();
        this.parentExtension = parentExtension;
        added = false;
    }

    // <editor-fold defaultstate="collapsed" desc="UI4j Core methods">
    /**
     * Sets the size of the window
     *
     * @param width the width of the window
     * @param height the height of the window
     */
    public final void setWindowSize(double width, double height) {
        parentFrame.getSurface().setSize((int) width, (int) height);
    }

    public final void hideCursor() {
        parentFrame.getSurface().hideCursor();
    }

    /**
     * Shows the cursor
     */
    public final void showCursor() {
        parentFrame.getSurface().showCursor();
    }

    /**
     * Gets the size of the window
     *
     * @return the size of the window
     */
    public final Dimension getWindowSize() {
        return new Dimension(parentFrame.width, parentFrame.height);
    }

    /**
     * Gets the size of the screen
     *
     * @return the size of the screen
     */
    public final Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     *
     * @return the coordinates of the cursor, minus the current rendering
     * translation.
     */
    public PVector getMouseTransCoords() {
        return getParentFrame().getMouseTransCoords();
    }

    /**
     * @return the parentFrame
     */
    public UIExtensionFrame getParentFrame() {
        return parentFrame;
    }

    public final void background(int x) {
        parentFrame.background(x);
    }

    public final void stroke(int x) {
        parentFrame.stroke(x);
    }

    public final void stroke(int x, int y) {
        parentFrame.stroke(x, y);
    }

    public final void stroke(int x, int y, int z) {
        parentFrame.stroke(x, y, z);
    }

    public final void stroke(Color x) {
        parentFrame.stroke(x);
    }

    public final void noStroke() {
        parentFrame.noStroke();
    }

    public final void strokeWeight(int x) {
        parentFrame.strokeWeight(x);
    }

    public final void fill(int x) {
        parentFrame.fill(x);
    }

    public final void fill(int x, int y) {
        parentFrame.fill(x, y);
    }

    public final void fill(int x, int y, int z) {
        parentFrame.fill(x, y, z);
    }

    public final void fill(Color x) {
        parentFrame.fill(x);
    }

    public final void ellipse(float x, float y, float width, float height) {
        parentFrame.ellipse(x, y, width, height);
    }

    public final void rect(float x, float y, float width, float height) {
        parentFrame.rect(x, y, width, height);
    }

    public final void rect(float x, float y, float width, float height, float radius) {
        parentFrame.rect(x, y, width, height, radius);
    }

    public final void line(float x1, float y1, float x2, float y2) {
        parentFrame.line(x1, y1, x2, y2);
    }

    public final void text(String x, float x1, float y1) {
        parentFrame.text(x, x1, y1);
    }

    public final void text(String x, float x1, float y1, float x2, float y2) {
        parentFrame.text(x, x1, y1, x2, y2);
    }

    public final void textAlign(int x) {
        parentFrame.textAlign(x);
    }

    public final void textAlign(int x, int y) {
        parentFrame.textAlign(x, y);
    }

    public final void textSize(int x) {
        parentFrame.textSize(x);
    }

    public final void noFill() {
        parentFrame.noFill();
    }

    public final boolean keyIsPressed(KeyCode key) {
        return parentFrame.keyIsPressed(key);
    }

    public final boolean keysArePressed(KeyCode... keys) {
        return parentFrame.keysArePressed(keys);
    }

    public final void translate(float locationX, float locationY) {
        getParentFrame().translate(locationX, locationY);
    }

    public final void translate(float locationX, float locationY, float locationZ) {
        getParentFrame().translate(locationX, locationY, locationZ);
    }

    //image() methods
    public final void image(PImage image, float x, float y) {
        parentFrame.image(image, x, y);
    }

    public final void image(PImage image, float x, float y, float width, float height) {
        parentFrame.image(image, x, y, width, height);
    }

    // tint() methods
    /**
     * Sets the tint of the current color
     */
    public final void tint(int x) {
        parentFrame.tint(x);
    }

    /**
     * Sets the tint of the current color
     */
    public final void tint(int x, int y) {
        parentFrame.tint(x, y);
    }

    /**
     * Sets the tint of the current color
     */
    public final void tint(int x, int y, int z) {
        parentFrame.tint(x, y, z);
    }

    /**
     * @return the translations caused by translate() methods
     */
    public PVector getTranslations() {
        return parentFrame.getTranslations();
    }

    /**
     * Sets translation annotations caused by translate() methods
     *
     * @param translations the translations to set
     */
    public void setTranslations(int x, int y) {
        parentFrame.setTranslations(x, y);
    }

    // </editor-fold>
    private boolean added = false;

    public void addToFrame() {
        if (added) {
            throw new IllegalAccessError("addToFrame() Should only be called once.");
        }
        parentFrame.addComponent(this);
        added = true;
    }

    public void removeFromFrame() {
        parentFrame.removeComponent(this);
    }

    private boolean enabled = true;

    public abstract void mouseEvent(MouseEvent event);

    public abstract void keyPressed(processing.event.KeyEvent event);

    public abstract void keyReleased(processing.event.KeyEvent event);

    public abstract void onDisable();

    public abstract void onEnable();
}
