/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processing.ui4j.components;

import static processing.core.PConstants.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.EventAction;
import processing.ui4j.UIExtension;
import processing.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class Button extends UIComponent {

    /**
     * @param clickEvent the clickEvent to set
     */
    public void setClickEvent(EventAction<Button> clickEvent) {
        this.clickEvent = clickEvent;
    }

    String value = "button";

    public Button(UIExtensionFrame frame) {
        super(frame);
        value = "";
        addToFrame();
    }

    public Button(UIExtensionFrame frame, String value) {
        super(frame);
        this.value = value;
        addToFrame();
    }

    public Button(UIExtension frame) {
        super(frame);
        value = "";
        addToFrame();
    }

    public Button(UIExtension frame, String value) {
        super(frame);
        this.value = value;
        addToFrame();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;

    public boolean isOver() {
        double mouseX = getMouseTransCoords().x;
        double mouseY = getMouseTransCoords().y;
        return x <= mouseX && mouseX <= x + width
                && y <= mouseY && mouseY <= y + height;
    }

    public void render(int x, int y, int width, int height) {
        height = (int) clamp(height, 10, 1000);
        width = (int) clamp(width, 20, 1000);

        if (isOver()) {
            getParentFrame().cursor(HAND);
        }

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        getParentFrame().textSize(15f);
        getParentFrame().textAlign(CENTER, CENTER);

        getParentFrame().strokeWeight((float) 0.5);
        if (isEnabled()) {
            stroke(UIComponent.ACTIVE);
            if (isOver() && getParentFrame().mousePressed) {
                getParentFrame().fill((170));
            } else if (isOver()) {
                getParentFrame().fill((190));
            } else {
                getParentFrame().fill((200));
            }
        } else {
            getParentFrame().stroke((50));
            getParentFrame().fill((180));
        }
        getParentFrame().rect(x, y, width, height, 2);
        if (isEnabled()) {
            getParentFrame().fill((0));
        } else {
            getParentFrame().fill((50));
        }

        getParentFrame().textAlign(getParentFrame().CENTER, getParentFrame().CENTER);
        getParentFrame().text(value, x, y, width, height - 4);
    }

    public void render(int x, int y, int width) {
        int height = (int) (getParentFrame().textAscent() + getParentFrame().textDescent() + 10);
        render(x, y, (int) width, height);
    }

    public void render(int x, int y) {
        int height = (int) (getParentFrame().textAscent() + getParentFrame().textDescent() + 10);
        render(x, y, (int) getParentFrame().textWidth(value) + 22, height);
    }

    private EventAction clickEvent = null;

    @Override
    public void mouseEvent(MouseEvent me) {
        if (me.getAction() == MouseEvent.RELEASE) {
            if (clickEvent != null && isOver()) {
                clickEvent.run(this);
            }
        }
    }
    @Override
    public String toString() {
        return "Button: \"" + value + "\"";
    }

    @Override
    public void keyPressed(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }
}
