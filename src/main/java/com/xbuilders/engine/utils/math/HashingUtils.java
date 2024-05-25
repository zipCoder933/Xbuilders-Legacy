/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils.math;

/**
 *
 * @author zipCoder933
 */
public class HashingUtils {

    /**
     * maps two integers into one long, in a unique and deterministic way.<br>
     * Uses Szudzik's function to hash the parameters. See
     * https://stackoverflow.com/questions/919612/mapping-two-integers-to-one-in-a-unique-and-deterministic-way
     *
     * @return the unique value. (the output is guaranteed not to go outside of
     * the returned number range)
     */
    public static long PerfectlyHashThem(int a, int b) {
        long A = (long) (a >= 0 ? 2 * (long) a : -2 * (long) a - 1);
        long B = (long) (b >= 0 ? 2 * (long) b : -2 * (long) b - 1);
        long C = (long) ((A >= B ? A * A + A + B : A + B * B) / 2);
        return a < 0 && b < 0 || a >= 0 && b >= 0 ? C : -C - 1;
    }

    /**
     * maps two shorts into one integer, in a unique and deterministic way.<br>
     * Uses Szudzik's function to hash the parameters. See
     * https://stackoverflow.com/questions/919612/mapping-two-integers-to-one-in-a-unique-and-deterministic-way
     *
     * @return the unique value. (the output is guaranteed not to go outside of
     * the returned number range)
     *
     *
     */
    public static int PerfectlyHashThem(short a, short b) {
        int A = (int) (a >= 0 ? 2 * a : -2 * a - 1);
        int B = (int) (b >= 0 ? 2 * b : -2 * b - 1);
        int C = (int) ((A >= B ? A * A + A + B : A + B * B) / 2);
        return a < 0 && b < 0 || a >= 0 && b >= 0 ? C : -C - 1;
    }
}
