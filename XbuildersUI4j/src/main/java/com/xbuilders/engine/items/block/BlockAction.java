/*
 * Designed to make block actions pauseble and resumable
 */
package com.xbuilders.engine.items.block;

import com.xbuilders.engine.world.chunk.ChunkCoords;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.game.PointerHandler;
import java.util.ArrayList;
import java.util.Arrays;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public abstract class BlockAction {

    private static ArrayList<ActionThread> actionThreads = new ArrayList<>();
    private static final Object interruptLock = new Object();

    public static String getStatusToString() {
        int aliveThreads = 0;
        synchronized (interruptLock) {
            for (ActionThread a : actionThreads) {
                if (a != null && a.isAlive()) {
                    aliveThreads++;
                }
            }
        }
        return "Alive Block-Action Threads: " + aliveThreads;
    }

    public static void interruptAllBlockActions() {
        synchronized (interruptLock) {
            System.out.println("============================\n"
                    + "INTERRUPTING ALL BLOCK ACTIONS...\n"
                    + "============================");
            for (ActionThread a : actionThreads) {
                if (a != null && a.isAlive()) {
                    a.interrupt();
                    if (a.parent.inBounds()) {
                        a.interruptedMethod();
                    }
                }
            }
            actionThreads.clear();
        }
    }

    public static void interruptActionOnChunk(ChunkCoords chunk) {
        synchronized (interruptLock) {
            ArrayList<ActionThread> threadsToDelete = new ArrayList<>();
            for (ActionThread a : actionThreads) {
                if (a != null && a.isAlive()) {
                    Vector3i vec = new Vector3i();
                    WCCi.getSubChunkAtWorldPos(vec,
                            a.parent.origin.x,
                            a.parent.origin.y,
                            a.parent.origin.z);
                    if (vec.x == chunk.x && vec.z == chunk.z) {
                        a.interrupt();
                        a.interruptedMethod();
                        threadsToDelete.add(a);
                    }
                }
            }
            actionThreads.removeAll(threadsToDelete);
        }
    }

    public BlockAction(PointerHandler ph, int x, int y, int z) {
        this.ph = ph;
        this.origin = new Vector3i(x, y, z);
        thread = new ActionThread(this);
    }

    Vector3i origin;
    PointerHandler ph;
    ActionThread thread;

    public boolean inBounds() {
        return inBounds(origin);
    }

    public boolean inBounds(Vector3i pos) {
        WCCi wcc = new WCCi().set(pos);
        return wcc.chunkExists();
    }

    public void startAction() {
        synchronized (interruptLock) {
            actionThreads.add(thread);
            thread.start();
            thread.setPriority(1);
        }
    }

    private void print(String message) {
        System.out.println("BLOCK-ACTION " + origin.toString() + ":\t" + message);
    }

    public abstract void preformAction() throws Exception;

    public abstract void onInterrupt() throws Exception;

    class ActionThread extends Thread {

        public BlockAction parent;

        public ActionThread(BlockAction parent) {
            this.parent = parent;
        }

        public void interruptedMethod() {
            try {
                onInterrupt();
            } catch (Exception ex1) {
                print("Error at " + Arrays.toString(ex1.getStackTrace()));
            }
        }

        @Override
        public void run() {
            WCCi wcc = new WCCi().set(origin);
            if (wcc.chunkExists()) {
                try {
                    preformAction();
                    synchronized (interruptLock) {
                        if (actionThreads.contains(this)) {
                            actionThreads.remove(this);
                        }
                    }
                } catch (InterruptedException ex) {
                    print("Interrupted");
                } catch (Exception ex) {
                    print("Error at " + Arrays.toString(ex.getStackTrace()));
                }
            }
        }
    }
}
