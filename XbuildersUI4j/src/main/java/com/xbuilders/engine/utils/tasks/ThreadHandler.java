/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.tasks;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zipCoder933
 */
public class ThreadHandler {

    /**
     * @return the threadsCompleted
     */
    public int getThreadsCompleted() {
        return threadsCompleted;
    }

    public synchronized void threadComplete() {
        threadsCompleted++;
        notifyAll();
    }

    public synchronized void waitUntilAllThreadsAreDone(int threadsRunning) {
        try {
            while (true) {
                if (threadsCompleted >= threadsRunning) {
//                    System.out.println("all "+threadsRunning+" threads complete!");
                    break;
                } else {
                    wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int threadsCompleted = 0;

    public ThreadHandler() {
        threadsCompleted = 0;
    }
}
