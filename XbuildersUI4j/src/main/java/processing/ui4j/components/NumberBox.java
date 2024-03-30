/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processing.ui4j.components;

import processing.core.KeyCode;
import processing.core.PConstants;
import static processing.core.PConstants.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.UIExtension;
import processing.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class NumberBox extends Box {

    String value = "0";

    public NumberBox(UIExtensionFrame frame) {
        super(frame);
        value = "0";
        addToFrame();
    }

    public NumberBox(UIExtension ext) {
        super(ext);
        value = "0";
        addToFrame();
    }

    public void setValue(double value) {
        this.value = value + "";
    }

    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    boolean selected = false;
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
        if (selected) {
            getParentFrame().strokeWeight((float) (getBorderWidth() * 1.2));
            getParentFrame().stroke(0, 50, 255);
            if (isOver()) {
                getParentFrame().cursor(PConstants.TEXT); //https://p5js.org/reference/#/p5/cursor
            }
        } else {
            stroke(UIComponent.ACTIVE);
        }
        getParentFrame().rect(x, y, width, height, 2);

        if (System.currentTimeMillis() - cursorTime > 500) {
            cursorEnabled = !cursorEnabled;
            cursorTime = System.currentTimeMillis();
        }
        getParentFrame().textAlign(LEFT, CENTER);

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

        if ((cursorEnabled || value.equals("")) && selected) {//Draw the cursor
            fill((0));
            noStroke();
            float textWidth = getParentFrame().textWidth(value);
            if (textWidth < width - 15) {
                rect(x + 6 + textWidth, y + 4, 1, height - 8);
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
            selected = isOver();
            if (selected) {
                if (!value.equals("")) {
                    textHighlighted = !textHighlighted;
                }
            } else {
                textHighlighted = false;
                if ("".equals(value.trim())) {
                    value = "0";
                }
                if (!lastVal.equals(value)) {
                    if (onchangeEvent != null) {
                        onchangeEvent.run(this);
                    }
                }
                lastVal = value;
            }
        }
    }

    String lastVal = "0";

    @Override
    public void keyPressed(KeyEvent ke) {
       
            if (selected) {
                if (ke.getKeyCode() == KeyCode.BACKSPACE) {
                    if (value.length() > 0) {
                        value = value.substring(0, value.length() - 1);
                    }
                    if (textHighlighted) {
                        value = "";
                    }
                    textHighlighted = false;
                } else if (ke.getKeyCode() == KeyCode.ENTER) {
                    selected = false;
                    getValue2();
                    if ("".equals(value.trim())) {
                        value = "0";
                    }
                    if (!lastVal.equals(value)) {
                        if (onchangeEvent != null) {
                            onchangeEvent.run(this);
                        }
                    }
                    lastVal = value;
                } else if (ke.getKeyCode().getValue() == 37
                        || ke.getKeyCode().getValue() == 39
                        || (keyIsPressed(KeyCode.CTRL) && ke.getKeyCode().getValue() == 65)) {
                    textHighlighted = true;
                } else if (ke.getKeyCode().getValue() == 38) {
                    double val = getValue2() + 1;
                    value = val + "";
                } else if (ke.getKeyCode().getValue() == 40) {
                    double val = getValue2() - 1;
                    value = val + "";
                } else if ((ke.getKeyCode().getValue() == 46 || ke.getKeyCode().getValue() == 69 || ke.getKeyCode().getValue() == 45
                        || (ke.getKeyCode().getValue() >= 48 && ke.getKeyCode().getValue() <= 57))
                        && keyIsPressed(KeyCode.CTRL) == false && altPressed() == false) {
                    if (textHighlighted) {
                        value = "";
                    }
                    textHighlighted = false;
                    value += ke.getKey();
                } else if (keyIsPressed(KeyCode.CTRL) && keyIsPressed(KeyCode.V) && getClipboardString() != null) {
                    textHighlighted = true;
                    value = getClipboardString();
                }
            }
      
    }

    private double getValue2() {
        try {
            double number = Double.parseDouble(value.trim());
            value = number + "";
            return number;
        } catch (NumberFormatException ex) {
            value = "0";
            return 0;
        }
    }

    public double getValue() {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }


    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

}
