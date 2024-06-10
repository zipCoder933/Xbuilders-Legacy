/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.window.components;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import processing.core.KeyCode;
import com.xbuilders.window.ui4j.EventAction;
import com.xbuilders.window.ui4j.UIExtension;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 * @param <T>
 */
public abstract class Box<T> extends UIExtension {

    public Box(UIExtension e) {
        super(e);
    }

    public Box(UIExtensionFrame e) {
        super(e);
    }

    protected static String getClipboardString() {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        // This represents the paste (Ctrl+V) operation
        try {
            Transferable t = cb.getContents(null);
            if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return t.getTransferData(DataFlavor.stringFlavor).toString();
            }
        } catch (UnsupportedFlavorException | IOException ex) {
        }
        return null;
    }

    protected boolean altPressed() {
        return keyIsPressed(KeyCode.LEFT_ALT) || keyIsPressed(KeyCode.RIGHT_ALT);
    }

    public abstract boolean isOver();

    private double borderWidth = 0.6;

    /**
     * @return the borderWidth
     */
    public final double getBorderWidth() {
        return borderWidth;
    }

    /**
     * @param borderWidth the borderWidth to set
     */
    public final void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }

    private int textSize = 15;

    /**
     * @return the textSize
     */
    public final int getTextSize() {
        return textSize;
    }

    /**
     * @param textSize the textSize to set
     */
    public final void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    protected EventAction<T> onchangeEvent = null;

    /**
     * @param onchangeEvent the onchangeEvent to set
     */
    public void setOnchangeEvent(EventAction<T> onchangeEvent) {
        this.onchangeEvent = onchangeEvent;
    }

}
