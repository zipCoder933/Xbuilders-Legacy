/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game.inventory;

import com.xbuilders.window.ui4j.UIExtension;
import com.xbuilders.window.ui4j.UIExtensionFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 *
 * @author zipCoder933
 */
public class InventoryBlockButton extends UIExtension {

    boolean over;

    private boolean coords(int x, int y, int x2, int y2) {
        return (getParentFrame().mouseX - getTranslations().x > x
                && getParentFrame().mouseY - getTranslations().y > y
                && getParentFrame().mouseX - getTranslations().x < x + x2
                && getParentFrame().mouseY - getTranslations().y < y + y2);
    }

    /**
     * @param action the action to set
     */
    public void setAction(Runnable action) {
        this.action = action;
    }

    public InventoryBlockButton(UIExtensionFrame p) {
        super(p);
        addToFrame();
        over = false;
    }

    public InventoryBlockButton(UIExtension e) {
        super(e);
        addToFrame();
        over = false;
    }
    private Runnable action;
    private String buttonTitle = "";

    public void draw(int x, int y, int w, int h) {
        over = coords(x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        getParentFrame().noStroke();
        getParentFrame().noFill();
        if (over && getParentFrame().mousePressed && this.isEnabled()) {
        } else if (over && this.isEnabled()) {
            getParentFrame().stroke(0, 100, 255);
            getParentFrame().strokeWeight(3);
        }
        getParentFrame().rect(x, y, w, h, 2);
        getParentFrame().fill(255);
        if (!isEnabled()) {
            press = false;
        }
    }

    int x, y, w, h, tx, ty;

    public boolean isOver() {
//        return coords(x + tx, y + ty, x1, h);
        return over;
        /*The real issue with the message boxes being weird was 1 of 2 reasons:
            1. The coordinates should have been calculated on the frame
            2. the coordinates check here is different than in the draw method.
         */
    }
    boolean press = false;

    @Override
    public void mouseEvent(MouseEvent me) {
        if (me.getAction() == MouseEvent.RELEASE && isOver() && press) {
            if (action != null) {
                action.run();
            }
            press = false;
        } else if (me.getAction() == MouseEvent.PRESS && isOver()) {
            press = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }
}
