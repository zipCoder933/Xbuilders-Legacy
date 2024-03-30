/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.preformance;

/**
 *
 * @author zipCoder933
 */
public class Stopwatch {

    /**
     * @return the elapsedNanoseconds
     */
    public long getElapsedNanoseconds() {
        return elapsedNanoseconds;
    }

    /**
     * @return the elapsedMicroseconds
     */
    public long getElapsedMicroseconds() {
        return elapsedMicroseconds;
    }
//long startTime = System.nanoTime();    
//// ... the code being measured ...    
//long estimatedTime = System.nanoTime() - startTime;

    /**
     * @return the elapsedMinutes
     */
    public int getElapsedMinutes() {
        return elapsedMinutes;
    }

    /**
     * @return the elapsedSeconds
     */
    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    /**
     * @return the elapsedMillis
     */
    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    long startTime = -1;

    private int elapsedMinutes;
    private int elapsedSeconds;
    private long elapsedNanoseconds;
    private long elapsedMicroseconds;
    private long elapsedMilliseconds;

    /**
     * Starts / restarts the stopwatch. After being started, the stopwatch will
     * continue ticking forever unless you start() it again, and then the
     * stopwatch will start ticking from the beginning.
     */
    public void start() {
        startTime = System.nanoTime(); //start the clock
    }

    /**
     * Calculates the elapsed time since the start() method was called.
     */
    public void calculateElapsedTime() {
        if (startTime == -1) {
            throw new IllegalAccessError("Stopwatch has not been started yet.");
        }
        elapsedNanoseconds = (System.nanoTime() - startTime);
        elapsedMicroseconds = getElapsedNanoseconds() / 1000;
        elapsedMilliseconds = getElapsedMicroseconds() / 1000;
        elapsedSeconds = (int) (elapsedMilliseconds / 1000);
        elapsedMinutes = elapsedSeconds / 60;

        //Subtract the lower times from the higher times.
        elapsedNanoseconds -= getElapsedMicroseconds() * 1000;
        elapsedMicroseconds -= getElapsedMilliseconds() * 1000;
        elapsedMilliseconds -= (elapsedSeconds * 1000);
        elapsedSeconds -= (elapsedMinutes * 60);
    }

    @Override
    public String toString() {
        String result = elapsedMinutes + "m " + elapsedSeconds + "s " + elapsedMilliseconds + "ms " + elapsedMicroseconds + "µs " + elapsedNanoseconds + "ns";
        return result;
    }

}

/*public class Timer {

    private long start;

    public Timer() {
        start = System.nanoTime();
    }

    public long getElapedTimeMS() {
        return (System.nanoTime() - start);
    }

    public long getElapedTimeInSeconds() {
        return (System.nanoTime() - start) / 1000;
    }

    public long getElapedTimeInMinutes() {
        return getElapedTimeInSeconds() / 60;
    }

    public boolean elaspedTimeInMinutesIsOver(int val) {
        return getElapedTimeInMinutes() > (val - 1);
    }

    public boolean elaspedTimeInSecondsIsOver(int val) {
        return getElapedTimeInSeconds() > (val - 1);
    }

    public void reset() {
        start = System.nanoTime();
    }

}
 */
