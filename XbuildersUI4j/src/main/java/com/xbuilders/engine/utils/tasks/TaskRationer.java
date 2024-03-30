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
public class TaskRationer {

    /**
     * @return the totalCompletedTasks
     */
    public int getTotalCompletedTasks() {
        return totalCompletedTasks;
    }

    /**
     * @return the maximumTasksAllowed
     */
    public int getMaximumTasksAllowed() {
        return maximumTasksAllowed;
    }

    /**
     * @param maximumTasksAllowed the maximumTasksAllowed to set
     */
    public void setMaximumTasksAllowed(int maximumTasksAllowed) {
        this.maximumTasksAllowed = maximumTasksAllowed;
    }

    /**
     * @return the runningTasks
     */
    public int getRunningTasks() {
        return runningTasks;
    }

    /**
     * @param runningTasks the runningTasks to set
     */
    public void setRunningTasks(int runningTasks) {
        this.runningTasks = runningTasks;
    }

    public void reset() {
        totalCompletedTasks = 0;
        runningTasks = 0;
    }

    private int totalCompletedTasks = 0;
    private int maximumTasksAllowed;
    private int runningTasks;

    public TaskRationer(int maxTasks) {
        this.maximumTasksAllowed = maxTasks;
        this.runningTasks = 0;
    }

    public int getAvailableTasks() {
        return this.maximumTasksAllowed - this.runningTasks;
    }

//    /**
//     * Waits until there is an available task and then sets the runningTasks
//     * variable to +1
//     *
//     * @param availabilityDelay the number of milliseconds to wait after a new
//     * task has been found (helps prevent the system from slowing down due to
//     * too many tasks all at once.)
//     */
//    public synchronized void startTask(long availabilityDelay) {
//        if (this.runningTasks >= this.maximumTasksAllowed) {
//            waitUntilAvailable();
//        }
//        try {
//            Thread.sleep(availabilityDelay);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TaskRationer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        this.runningTasks++;
//    }
    /**
     * Waits until there is an available task and then sets the runningTasks
     * variable to +1
     *
     * @param thread the task
     */
    public synchronized void startTask(Thread thread) {
        if (this.runningTasks >= this.maximumTasksAllowed) {
            waitUntilAvailable();
        }
        this.runningTasks++;
        if (maximumTasksAllowed == 1) {
            thread.run();
            /**
             * If there is only one thread allowed, there is no point in
             * starting the new thread on a separate one. If there is only one
             * thread allowed, we are already waiting for the thread to finish
             * in order to start a new one, so this has literally the same
             * effect
             */
        } else {
            thread.start();
        }
    }

    public synchronized void waitUntilAllTasksAreComplete() {
        if (runningTasks == 0) {
            return;
        }
        while (this.runningTasks > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    private void waitUntilAvailable() {
        while (this.runningTasks >= this.maximumTasksAllowed) {
            try {
//                Thread.sleep(500);
                wait(); //stops this thread until a notifyAll() is called. nofifyAll() resumes all threads that are waititng.
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * completes a task, allowing a new one to be run, if we are currently
     * running the maximum number of tasks allowed.
     */
    public synchronized void completeTask() {
        runningTasks--;
        totalCompletedTasks++;
        notifyAll();
    }

}
