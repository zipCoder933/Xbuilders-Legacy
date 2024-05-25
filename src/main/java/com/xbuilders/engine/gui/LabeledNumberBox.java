/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui;

import static processing.core.PConstants.LEFT;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.UIExtension;
import processing.ui4j.UIExtensionFrame;
import processing.ui4j.components.NumberBox;

/**
 *
 * @author zipCoder933
 */
public class LabeledNumberBox extends UIExtension {

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the box
     */
    public NumberBox getBox() {
        return box;
    }

    public LabeledNumberBox(UIExtensionFrame f) {
        super(f);
        box = new NumberBox(f);
    }

    public LabeledNumberBox(UIExtension f) {
        super(f);
        box = new NumberBox(f);
    }

    private NumberBox box;
    private String label;

    public int render(int x, int y, int w) {
//        System.out.println("Rendering \""+label+"\"");
        if (label != null) {
            fill(255, 100);
            textSize(11);
            textAlign(LEFT);
            text(getLabel(), x, y, w, 20);
        }
        textSize(12);
        getBox().render(x, y + 25, w, 30);
        return y + 25 + 35;
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

    @Override
    public void mouseEvent(MouseEvent me) {
    }
}
