package com.xbuilders.engine.gui;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static processing.core.PConstants.CENTER;

import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.UIExtension;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 * @author zipCoder933
 */
public class Button extends UIExtension {

    boolean over;

    private boolean coords(int x, int y, int x2, int y2) {
//        System.out.print(String.format(buttonTitle.replace(" ", "-") + ":%1$3s%s%s", "",
//                "  mouse=" + getParentFrame().mouseX + ", " + getParentFrame().mouseY
//                + ",\ttrans=" + getTranslations().toString()
//                + ",\t x=" + x + " y=" + y + " x2=" + x2 + " y2=" + y2 + "\tover: " + over + "\n"));

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

    public Button(UIExtensionFrame p) {
        super(p);
        addToFrame();
        over = false;
    }

    public Button(UIExtension e) {
        super(e);
        addToFrame();
        over = false;
    }

    private Runnable action;
    private String buttonTitle = "";

    public void draw(String str, int x, int y, int x1) {
        over = coords(x, y, x1, 40);
        buttonTitle = str;
        this.x = x;
        this.y = y;
        this.x1 = x1;
        getParentFrame().noStroke();
        if (over && getParentFrame().mousePressed && this.isEnabled()) {
            getParentFrame().fill(0, 100, 255);
        } else if (over && this.isEnabled()) {
            getParentFrame().stroke(0, 100, 255);
            getParentFrame().strokeWeight(1);
            getParentFrame().fill(30, 150);
        } else {
            getParentFrame().fill(10, 200);
        }

        getParentFrame().rect(x, y, x1, 40, 2);
        getParentFrame().fill(255);
        getParentFrame().textSize(14);
        getParentFrame().textAlign(CENTER, CENTER);
        getParentFrame().text(str, x + x1 / 2, y + 20);
        if (!isEnabled()) {
            press = false;
        }
    }

    public int draw(String str, int x, int y) {
        over = coords(x, y, x1, 40);
        buttonTitle = str;
        this.x = x;
        this.y = y;

        getParentFrame().noStroke();
        if (over && getParentFrame().mousePressed && this.isEnabled()) {
            getParentFrame().fill(0, 100, 255);
        } else if (over && this.isEnabled()) {
            getParentFrame().stroke(0, 100, 255);
            getParentFrame().strokeWeight(1);
            getParentFrame().fill(30, 150);
        } else {
            getParentFrame().fill(10, 200);
        }

        getParentFrame().textSize(14);
        getParentFrame().textAlign(CENTER, CENTER);
        this.x1 = (int) (getParentFrame().textWidth(str) + 20);

        getParentFrame().rect(x, y, x1, 40, 2);
        getParentFrame().fill(255);

        getParentFrame().text(str, x + x1 / 2, y + 20);
        if (!isEnabled()) {
            press = false;
        }
        return x1;
    }

    int x, y, x1, tx, ty;

    public boolean isOver() {
//        return coords(x + tx, y + ty, x1, 40);
        return over;
        /*The real issue with the message boxes being weird was 1 of 2 reasons:
            1. The coordinates should have been calculated on the frame
            2. the coordinates check here is different than in the draw method.
         */
    }

    boolean press = false;

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

}
