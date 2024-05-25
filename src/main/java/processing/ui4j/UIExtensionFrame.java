/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processing.ui4j;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.CopyOnWriteArrayList;
import processing.core.KeyCode;
import processing.core.PVector;
import processing.core.UIFrame;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 *
 * @author zipCoder933
 */
public abstract class UIExtensionFrame extends UIFrame {

    /**
     * @return the components
     */
    public CopyOnWriteArrayList<UIExtension> getComponents() {
        return components;
    }

    public UIExtensionFrame() {
        super();
        components = new CopyOnWriteArrayList<>();
        translations = new PVector();
    }

    private CopyOnWriteArrayList<UIExtension> components;

    public void addComponent(UIExtension component) {
        getComponents().add(component);
    }

    public void removeComponent(UIExtension component) {
        if (getComponents().contains(component)) {
            getComponents().remove(component);
        }
    }

    /**
     * @return the translations caused by translate() methods
     */
    public PVector getTranslations() {
        return translations;
    }

    /**
     * Sets translation annotations caused by translate() methods
     *
     * @param translations the translations to set
     */
    public void setTranslations(int x, int y) {
        this.translations.x = x;
        this.translations.y = y;
    }

    private PVector translations;

    @Override
    public final void translate(float locationX, float locationY) {
        translations.x += locationX;
        translations.y += locationY;
        super.translate(locationX, locationY);
    }

    @Override
    public final void translate(float locationX, float locationY, float locationZ) {
//        translations.x += locationX;
//        translations.y += locationY;
//        translations.z += locationZ;
        super.translate(locationX, locationY, locationZ);
    }

    /**
     *
     * @return the coordinates of the cursor, minus the current rendering
     * translation.
     */
    public PVector getMouseTransCoords() {
        return new PVector(mouseX - getTranslations().x,
                mouseY - getTranslations().y);
    }

    @Override
    public final void draw() {
        setTranslations(0, 0);
        render();
    }

    public abstract void render();

    public abstract void uiMouseEvent(MouseEvent event);

    public abstract void uikeyPressed(KeyEvent event);

    public abstract void uikeyReleased(KeyEvent event);

    @Override
    public final void mouseEvent(MouseEvent event) {
        uiMouseEvent(event);
        if (event.getAction() != MouseEvent.MOVE
                && event.getAction() != MouseEvent.DRAG) {
            for (int i = 0; i < getComponents().size(); i++) {
                UIExtension ext = getComponents().get(i);
                if (ext.isEnabled()) {
                    ext.mouseEvent(event);
                }
            }
        }
    }

    @Override
    public final void keyPressed(KeyEvent event) {
        uikeyPressed(event);
        for (int i = 0; i < getComponents().size(); i++) {
            UIExtension ext = getComponents().get(i);
            if (ext.isEnabled()) {
                ext.keyPressed(event);
            }
        }
    }

    @Override
    public final void keyReleased(KeyEvent event) {
        uikeyReleased(event);
        for (int i = 0; i < getComponents().size(); i++) {
            UIExtension ext = getComponents().get(i);
            if (ext.isEnabled()) {
                ext.keyReleased(event);
            }
        }
    }

}
