/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package processing.ui4j;

import java.io.File;

/**
 *
 * @author sampw
 */
public class WindowConfiguration {

    /**
     * @return the iconFile
     */
    public File getIconFile() {
        return iconFile;
    }

    /**
     * @param iconFilepath the iconFile to set
     */
    public void setIconFile(File iconFilepath) {
        this.iconFile = iconFilepath;
    }

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

    /**
     * @return the windowType
     */
    public WindowType getWindowType() {
        return windowType;
    }

    /**
     * @param windowType the windowType to set
     */
    public void setWindowType(WindowType windowType) {
        this.windowType = windowType;
    }

    /**
     * @return the fullscreen
     */
    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * @param fullscreen the fullscreen to set
     */
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    /**
     * @return the undecorated
     */
    public boolean isUndecorated() {
        return undecorated;
    }

    /**
     * @param undecorated the undecorated to set
     */
    public void setUndecorated(boolean undecorated) {
        this.undecorated = undecorated;
    }

    private boolean undecorated = false;

    private int width = 0;
    private int height = 0;
    private boolean fullscreen = false;
    private File iconFile = null;

    private WindowType windowType = WindowType.UI2D;

    public WindowConfiguration() {
    }

    public WindowConfiguration(int width, int height, WindowType windowType, boolean undecorated) {
        this.width = width;
        this.height = height;
        this.windowType = windowType;
        this.undecorated = undecorated;
    }

    public WindowConfiguration(int width, int height, WindowType windowType) {
        this.width = width;
        this.height = height;
        this.windowType = windowType;
    }

    public WindowConfiguration(WindowType windowType) {
        this.fullscreen = true;
        this.windowType = windowType;
    }

    public WindowConfiguration(int width, int height, boolean undecorated, WindowType windowType) {
        this.width = width;
        this.height = height;
        this.undecorated = undecorated;
        this.windowType = windowType;
    }

    public WindowConfiguration(int width, int height, boolean undecorated, File iconFilepath, WindowType windowType) {
        this.width = width;
        this.height = height;
        this.windowType = windowType;
        this.undecorated = undecorated;
        this.iconFile = iconFilepath;
    }

    public WindowConfiguration(int width, int height, File iconFilepath, WindowType windowType) {
        this.width = width;
        this.height = height;
        this.windowType = windowType;
        this.iconFile = iconFilepath;
    }

    public WindowConfiguration(File iconFilepath, WindowType windowType) {
        this.fullscreen = true;
        this.windowType = windowType;
        this.iconFile = iconFilepath;
    }

    public enum WindowType {
        UI2D, UI3D
    };

}
