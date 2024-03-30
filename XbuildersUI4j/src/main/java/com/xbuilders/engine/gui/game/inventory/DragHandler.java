/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.gui.game.inventory;

/**
 *
 * @author zipCoder933
 */
class DragHandler<T> {

    /**
     * @return the holdStarted
     */
    public boolean isHoldStarted() {
        return holdStarted;
    }

    /**
     * @return the originObject
     */
    public Object getOriginObject() {
        return originObject;
    }

    /**
     * @return the originProperties
     */
    public T getOriginProperties() {
        return originProperties;
    }

    public void startHold(Object originObject, T originProperties) {
        holdStarted = true;
        this.originProperties = originProperties;
        this.originObject = originObject;
    }

    public void endHold() {
        holdStarted = false;
    }

    private boolean holdStarted;
    private Object originObject;
    private T originProperties;
}
