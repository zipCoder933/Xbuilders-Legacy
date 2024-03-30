/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package processing.ui4j.components;

import java.awt.Color;
import processing.ui4j.UIExtension;
import processing.ui4j.UIExtensionFrame;

/**
 *
 * @author sampw
 */
public abstract class UIComponent extends UIExtension {

    protected static int clamp(int val, int min, int max) {
        if (val > max) {
            return max;
        } else if (val < min) {
            return min;
        }

        return val;
    }

    public static final Color ACTIVE = new Color(0, 100, 255);

    public UIComponent(UIExtensionFrame f) {
        super(f);
    }

    public UIComponent(UIExtension f) {
        super(f);
    }
}
