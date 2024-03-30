package com.xbuilders.window;

import processing.ui4j.UIExtensionFrame;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public abstract class BaseWindow extends UIExtensionFrame {


    public BaseWindow() {
    }

    public double getMsPerFrame() {
        return 1000 / frameRate;
    }


}
