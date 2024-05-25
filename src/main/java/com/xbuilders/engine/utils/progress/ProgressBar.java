/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.progress;

import com.xbuilders.engine.utils.math.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author zipCoder933
 */
public class ProgressBar {

    /**
     * @return the progress
     */
    public double getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public synchronized void setProgress(double progress) {
        progress = MathUtils.clamp(progress, 0, 1);
        this.progress = progress;
        if (progress != this.progress) {
            for (int i = 0; i < eventHandlers.size(); i++) {
                eventHandlers.get(i).onProgressChange(getProgress());
            }
        }
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public synchronized void setMax(int max) {
        this.max = max;
    }

    public ProgressBar() {
        eventHandlers = new ArrayList<>();
    }

    /**
     * @return the eventHandlers
     */
    public ArrayList<ProgressEventHandler> getEventHandlers() {
        return eventHandlers;
    }

    private ArrayList<ProgressEventHandler> eventHandlers;

    public void addEventHandler(ProgressEventHandler handler) {
        getEventHandlers().add(handler);
    }

    private double progress;

    /**
     * @return the progress
     */
    public int getValue() {
        return value;
    }

    public void waitUntilComplete() throws InterruptedException {
        while (!isComplete()) {
            Thread.sleep(50);
        }
    }

    public void waitUntilComplete(long interval) throws InterruptedException {
        while (!isComplete()) {
            Thread.sleep(interval);
        }
    }

    public boolean isComplete() {
        return getProgress() >= 0.9999;
    }

    /**
     * @param value the value to set
     */
    public synchronized void setValue(int value) {
        this.value = value;
        this.setProgress((double) getValue() / getMax());
    }

    /**
     * Sets the value and maximum at the same time
     * @param value the bar value
     * @param max the maximum bar value
     */
    public synchronized void set(int value, int max) {
        this.value = value;
        this.max = max;
        this.setProgress((double) getValue() / getMax());
    }

    private int max = 0;
    private int value = 0;

    /*The synchronized keyword used to indicate that a method can be accessed by only one thread at a time. The synchronized modifier can be applied with any of the four access level modifiers.*/
    
    public synchronized void changeValue(int value2) {
        setValue(getValue() + value2);
        this.setProgress((double) getValue() / getMax());
    }
}
