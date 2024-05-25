/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xbuilders.engine.gui.mainMenu;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.EventAction;
import processing.ui4j.UIExtension;

/**
 *
 * @author zipCoder933
 */
public class ListItem extends UIExtension {

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the highlighted
     */
    public boolean isHighlighted() {
        return highlighted;
    }

    /**
     * @param highlighted the highlighted to set
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    boolean hovering = false;

    private boolean coords(int x, int y, int x2, int y2) {
        return (getParentFrame().mouseX - getTranslations().x > x
                && getParentFrame().mouseY - getTranslations().y > y
                && getParentFrame().mouseX - getTranslations().x < x + x2
                && getParentFrame().mouseY - getTranslations().y < y + y2);
    }

    /**
     * @param action the action to set
     */
    public void setAction(EventAction<ListItem> action) {
        this.action = action;
    }

//    public Button(UIExtensionFrame p) {
//        super(p);
//        addToFrame();
//    }
    public ListItem(UIExtension e) {
        super(e);
        addToFrame();
    }
    private EventAction action;

    private int index=0;
    private boolean highlighted = false;

    public void draw(String str, int x, int y, int x1) {
        getParentFrame().noStroke();
        hovering = coords(x, y, x1, 40);

        if (((hovering && getParentFrame().mousePressed) || isHighlighted()) && this.isEnabled()) {
            getParentFrame().fill(0, 100, 255);
        } else if (hovering && this.isEnabled()) {
            getParentFrame().fill(30, 100);
        } else {
            getParentFrame().fill(10, 150);
        }

        getParentFrame().rect(x, y, x1, 40);
        getParentFrame().fill(255);
        getParentFrame().textSize(12);
        getParentFrame().textAlign(LEFT, TOP);
        getParentFrame().text(str, x + 10, y + 15, x1 - 10, 20);
    }

    @Override
    public void mouseEvent(MouseEvent me) {
        if (me.getAction() == MouseEvent.RELEASE && hovering) {
            if (action != null) {
                action.run(this);
            }
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
