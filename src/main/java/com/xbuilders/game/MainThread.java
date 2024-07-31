package com.xbuilders.game;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.preformance.MemoryProfiler;

import java.io.IOException;

/**
 * This thread is designed to run while the player is in a world. It's main
 * purpose is currently to save changed chunks and to do garbage collection. It
 * runs on the main thread
 *
 * @author sampw
 */
public class MainThread {

    //<editor-fold defaultstate="collapsed" desc="main thread">
    public void initialize(PointerHandler ph) {
        this.pointerHandler = ph;
    }

    protected void run(Thread parentThread) {
        while (true) {
            try {
                if (pointerHandler.getWorld().isOpen()) {
                    Thread.sleep(1000);
                    mainThreadLoop();
                } else {
                    synchronized (Main.class) {
                        Main.class.wait();
                    }
                }
            } catch (Exception ex) {
                ErrorHandler.report("Error", "Error in main thread", ex);
                pointerHandler.getApplet().stop();
                saveWorld();
                return;
            }
        }
    }
//</editor-fold>

    final int SAVE_INTERVAL_MS = 25000;
    final int GARBAGE_COLLECTION_INTERVAL_MS = 30000;

    private PointerHandler pointerHandler;
    private long lastSaveTime;
    long saveTimer, gcTimer;

    public long getTimeSinceLastSaveMS() {
        return System.currentTimeMillis() - lastSaveTime;
    }

    public String getTimeSinceLastSaved() {
        int secondsSinceSaved = (int) (getTimeSinceLastSaveMS() / 1000);
        return (secondsSinceSaved < 5 ? "just now" : secondsSinceSaved + "s ago");
    }

    //Called when the thread starts
    public void start() {
        lastSaveTime = System.currentTimeMillis();
        synchronized (Main.class) {
            Main.class.notifyAll();
        }
    }

    //Called when the thread ends
    public void end() {
        saveWorld();
    }

    /**
     * Save world info, chunks and OBC
     */
    public synchronized void saveWorld() {
        System.out.println("(Main Thread): Saving world...");
        pointerHandler.getWorld().saveWorldInfo();
        if (VoxelGame.getWorld().saveChangedChunks()) {
            lastSaveTime = System.currentTimeMillis();
        }
    }

    private void mainThreadLoop() throws IOException {
        float memoryUsedPercent = MemoryProfiler.getMemoryUsagePercent();
        if (System.currentTimeMillis() - saveTimer > SAVE_INTERVAL_MS) {
            saveWorld();
            saveTimer = System.currentTimeMillis();
        } else if (memoryUsedPercent > 0.7 && System.currentTimeMillis() - gcTimer > 2000) {
            gcTimer = System.currentTimeMillis();
            // System.gc();
        } else if (System.currentTimeMillis() - gcTimer > GARBAGE_COLLECTION_INTERVAL_MS) {
            gcTimer = System.currentTimeMillis();
            System.gc();
        }
    }
}
