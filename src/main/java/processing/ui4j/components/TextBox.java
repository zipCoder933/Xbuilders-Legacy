/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processing.ui4j.components;

import processing.core.KeyCode;
import static processing.core.PConstants.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.UIExtension;
import processing.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class TextBox extends Box {

    String value = "";
    String placeholder;

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public TextBox(UIExtensionFrame frame) {
        super(frame);
        value = "";
        addToFrame();

    }

    public TextBox(UIExtensionFrame frame, String placeholder) {
        super(frame);
        this.placeholder = placeholder;
        addToFrame();

    }

    public TextBox(UIExtension frame) {
        super(frame);
        value = "";
        addToFrame();

    }

    public TextBox(UIExtension frame, String placeholder) {
        super(frame);
        this.placeholder = placeholder;
        addToFrame();

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isFocused() {
        return isFocused;
    }

    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    boolean isFocused = false;
    long cursorTime = 0;
    boolean cursorEnabled = true;
    boolean textHighlighted = false;

    @Override
    public boolean isOver() {
        double mouseX = getMouseTransCoords().x;
        double mouseY = getMouseTransCoords().y;
        return x <= mouseX && mouseX <= x + width
                && y <= mouseY && mouseY <= y + height;
    }

    public void render(int x, int y, int width, int height) {

        height = (int) clamp(height, 14, 1000);
        width = (int) clamp(width, 20, 1000);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        getParentFrame().fill((255));
        getParentFrame().textSize(getTextSize());
        getParentFrame().strokeWeight((float) getBorderWidth());
        if (isFocused) {
            getParentFrame().strokeWeight((float) (getBorderWidth() * 1.2));
            getParentFrame().stroke(0, 50, 255);
            if (isOver()) {
                getParentFrame().cursor(TEXT); //https://p5js.org/reference/#/p5/cursor
            }
        } else {
            getParentFrame().stroke(50);
        }
        getParentFrame().rect(x, y, width, height, 2);

        if (System.currentTimeMillis() - cursorTime > 500) {
            cursorEnabled = !cursorEnabled;
            cursorTime = System.currentTimeMillis();
        }
        getParentFrame().textAlign(LEFT, CENTER);
        if (value.equals("")) {
            if (placeholder != null) {
                getParentFrame().fill((150));
                getParentFrame().text(placeholder, x + 5, y, width - 10, height - 4);
            }
            cursorEnabled = true;
        } else {
            if (textHighlighted) {
                fill(UIComponent.ACTIVE);
                float w = getParentFrame().textWidth(value);
                if (w > width - 15) {
                    w = width - 15;
                }
                getParentFrame().rect(x + 5, y + 4, w, height - 8);
                getParentFrame().fill((255));
            } else {
                getParentFrame().fill((0));
            }

            getParentFrame().text(value, x + 5, y, width - 10, height - 4);
            if (isFocused) {
                if (System.currentTimeMillis() - cursorTime > 500) {
                    cursorEnabled = !cursorEnabled;
                    cursorTime = System.currentTimeMillis();
                }
                if (cursorEnabled) {//Draw the cursor
                    fill((0));
                    noStroke();
                    float textWidth = getParentFrame().textWidth(value);
                    if (textWidth < width - 15) {
                        rect(x + 6 + textWidth, y + 4, 1, height - 8);
                    }
                }
            }
        }

    }

    public void render(int x, int y, int width) {
        int height = (int) (getParentFrame().textAscent() + getParentFrame().textDescent() + 10);
        render(x, y, width, height);
    }

    @Override
    public void mouseEvent(MouseEvent me) {
        if (me.getAction() == MouseEvent.PRESS) {
            isFocused = isOver();
            if (isFocused) {
                if (!value.equals("")) {
                    textHighlighted = !textHighlighted;
                }
            } else {
                if (onchangeEvent != null) {
                    onchangeEvent.run(this);
                }
                textHighlighted = false;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (isFocused) {
            if (event.getKeyCode() == KeyCode.BACKSPACE) {
                if (value.length() > 0) {
                    value = value.substring(0, value.length() - 1);
                }
                if (textHighlighted) {
                    value = "";
                }
                textHighlighted = false;
            } else if (event.getKeyCode().getValue() == 10) {
                isFocused = false;
                if (onchangeEvent != null) {
                    onchangeEvent.run(this);
                }
            } else if (event.getKeyCode().getValue() == 37
                    || event.getKeyCode().getValue() == 39
                    || (keyIsPressed(KeyCode.CTRL) && event.getKeyCode().getValue() == 65)) {
                textHighlighted = true;
            } else if ((event.getKeyCode().getValue() == 32
                    || (event.getKeyCode().getValue() > 40 && event.getKeyCode().getValue() <= 126))
                    && keyIsPressed(KeyCode.CTRL) == false && altPressed() == false) {
                if (textHighlighted) {
                    value = "";
                }
                textHighlighted = false;
                value += event.getKey();
            } else if (keyIsPressed(KeyCode.CTRL) && keyIsPressed(KeyCode.V) && getClipboardString() != null) {
                if (textHighlighted) {
                    value = getClipboardString();
                } else {
                    value += getClipboardString();
                }
            }
        }
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
