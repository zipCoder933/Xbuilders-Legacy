/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui;

import com.xbuilders.engine.utils.progress.Bulletin;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.UIExtension;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 * @author zipCoder933
 */
public class MessageBox extends UIExtension {

    /**
     * @return the background
     */
    public UIExtension getBackground() {
        return background;
    }

    private UIExtension background;

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    Button ok, cancel;


    public MessageBox(UIExtensionFrame e) {
        super(e);
        ok = new Button(this);
        body = "";
        cancel = new Button(this);
        background = new UIExtension(e) {
            {
                addToFrame();
            }

            @Override
            public void mouseEvent(MouseEvent me) {
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

        };

        hide();
        ok.setAction(new Runnable() {
            @Override
            public void run() {
                hide();
                if (okButtonEvent != null) okButtonEvent.run();
            }
        });
        cancel.setAction(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });
        addToFrame();
    }

    private int width = 550;
    private int height = 350;
    private String title, body;
    Runnable okButtonEvent;

    public synchronized void show(String title, String body, final Runnable okButtonEvent) {
        this.title = title;
        this.body = body;
        this.okButtonEvent = okButtonEvent;
        showMsgBox();
    }

    public synchronized void show(String title, String body) {
        this.title = title;
        this.body = body;
        okButtonEvent = null;
        showMsgBox();
    }

    //<editor-fold defaultstate="collapsed" desc="Other show methods">
    public synchronized void show(String body, Runnable okButtonEvent) {
        show(null, body, okButtonEvent);
    }

    public synchronized void show(String body) {
        show(null, body);
    }

    public synchronized void show(Bulletin bulletin) {
        show(bulletin.getTitle(), bulletin.getBody());
    }
//</editor-fold>

    public synchronized void hide() {
        disable();
        getBackground().enable();
//        System.out.println("Visible: " + visible + ", enabled: " + isEnabled());
    }

    private void showMsgBox() {
        getBackground().disable();
        enable();
    }

    public void render() {
        if (isEnabled()) {
            translate(0 - getTranslations().x, 0 - getTranslations().y);

            int x1 = (getParentFrame().width / 2) - (getWidth() / 2);
            int y1 = (getParentFrame().height / 2) - (getHeight() / 2);

            strokeWeight(1);
            getParentFrame().stroke(0, 100, 255);

            fill(30, 250);
            rect(x1, y1, getWidth(), getHeight(), 2);

            fill(255);
            if (title == null) {
                if (body != null) {
                    textSize(12);
                    textAlign(LEFT, TOP);
                    getParentFrame().text(body, x1 + 25, y1 + 25, getWidth() - 50, getHeight() - 50);
                }
            } else {
                textSize(16);
                textAlign(LEFT, TOP);
                getParentFrame().text(title, x1 + 25, y1 + 25, getWidth() - 50, 20);
                if (body != null) {
                    fill(240);
                    textSize(12);
                    getParentFrame().text(body, x1 + 25, y1 + 25 + 40, getWidth() - 50, getHeight() - 50);
                }
            }
            ok.draw("Ok", x1 + 25, y1 + getHeight() - 40 - 25, 50);
            if (okButtonEvent != null) {
                cancel.draw("Cancel", x1 + 25 + 55, y1 + getHeight() - 40 - 25, 110);
            }
        }
    }

    public void handleError(Throwable ex, String title) {
        show(title, ex.getMessage());
        ex.printStackTrace();
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
