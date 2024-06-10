package com.xbuilders.window;

import com.xbuilders.window.ui4j.UIExtensionFrame;

public abstract class BaseWindow extends UIExtensionFrame {


    public BaseWindow() {
    }

    public double getMsPerFrame() {
        return 1000 / frameRate;
    }


}
