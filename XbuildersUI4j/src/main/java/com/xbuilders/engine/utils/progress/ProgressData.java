/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.progress;

/**
 *
 * @author zipCoder933
 */
public class ProgressData {

    /**
     * @return the spinMode
     */
    public boolean isSpinMode() {
        return spinMode;
    }

    /**
     * @param spinMode the spinMode to set
     */
    public void setSpinMode(boolean spinMode) {
        this.spinMode = spinMode;
    }

    /**
     * @return the bar
     */
    public ProgressBar getBar() {
        return bar;
    }

    /**
     * @return the progressError
     */
    public Bulletin getBulletin() {
        return progressBulletin;
    }

    /**
     * @param progressError the progressError to set
     */
    public void createBulletin(Bulletin bulletin) {
        this.progressBulletin = bulletin;
    }

    private String title = null;
    private String progressDesc = "";
    private ProgressBar bar;

    private boolean done = false;

    public ProgressData() {
        done = false;
        taskAborted = false;
        bar = new ProgressBar();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    private boolean spinMode = false;

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the done
     */
    public boolean isFinished() {
        return done;
    }

    /**
     * Sets the progressBar to finished
     */
    public void finish() {
        this.done = true;
    }

    /**
     * @return the progressDesc
     */
    public String getTask() {
        return progressDesc;
    }

    /**
     * @param progressDesc the progressDesc to set
     */
    public void setTask(String progressDesc) {
        this.progressDesc = progressDesc;

    }

    public void setTask(String progressDesc, ProgressBar bar) {
        this.progressDesc = progressDesc + " (" + bar.getValue() + "/" + bar.getMax() + ")";
    }

    public void setTask(String progressDesc, int value, int max) {
        this.progressDesc = progressDesc + " (" + value + "/" + max + ")";
    }

    private Bulletin progressBulletin = null;

    /**
     * Suppresses any error currently being raised.
     *
     */
    public void supressBulletins() {
        createBulletin(null);
    }

    boolean taskAborted = false;

    public void abort() {
        taskAborted = true;
    }

    public boolean isAborted() {
        return taskAborted;
    }

    /**
     * @param bar the bar to set
     */
    public void setBar(ProgressBar bar) {
        this.bar = bar;
    }

}
