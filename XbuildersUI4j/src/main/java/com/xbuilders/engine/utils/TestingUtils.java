/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils;

/**
 *
 * @author zipCoder933
 */
public class TestingUtils {

    public static void assertEquals(String desc, boolean condition) {
        System.out.println("\n" + desc + ":\t" + (condition ? "SUCCEEDED" : "FAILED"));
        if (!condition) {
            System.exit(1);
        }
    }
}
