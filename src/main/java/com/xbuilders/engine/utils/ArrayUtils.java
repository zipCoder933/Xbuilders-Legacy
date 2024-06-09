/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.utils;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.blockData.BlockData;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import processing.core.PImage;

/**
 *
 * @author zipCoder933
 */
public class ArrayUtils {

    public static byte[] ListByteToArray(List<Byte> list) {
        byte[] arr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static Block[] convert3Dto1D(Block[][][] arr3D) {
        Block[] flatBlocks = Arrays.stream(arr3D)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .toArray(Block[]::new);
        return flatBlocks;
    }

    public static BlockData[] convert3Dto1D(BlockData[][][] arr3D) {
        BlockData[] flatBlocks = Arrays.stream(arr3D)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .toArray(BlockData[]::new);
        return flatBlocks;
    }

//    public static <T> T[] convert3Dto1D(T[][][] arr3D) {
//        T[] flatBlocks = Arrays.stream(arr3D)
//                .flatMap(Arrays::stream)
//                .flatMap(Arrays::stream)
//                .toArray(size -> (T[]) Array.newInstance(arr3D.getClass().getComponentType().getComponentType(), size));
//        return flatBlocks;
//    }
    public static Byte[] convert3Dto1D(Byte[][][] arr3D) {
        int size = arr3D.length * arr3D[0].length * arr3D[0][0].length;
        Byte[] arr1D = new Byte[size];
        int index = 0;
        for (Byte[][] subArr2D : arr3D) {
            for (Byte[] subArr1D : subArr2D) {
                System.arraycopy(subArr1D, 0, arr1D, index, subArr1D.length);
                index += subArr1D.length;
            }
        }
        return arr1D;
    }

    public static byte[] convert3Dto1D(byte[][][] arr3D) {
        int size = arr3D.length * arr3D[0].length * arr3D[0][0].length;
        byte[] arr1D = new byte[size];
        int index = 0;
        for (byte[][] subArr2D : arr3D) {
            for (byte[] subArr1D : subArr2D) {
                System.arraycopy(subArr1D, 0, arr1D, index, subArr1D.length);
                index += subArr1D.length;
            }
        }
        return arr1D;
    }

    public static <T> T[] concatArrays(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static PImage cropImage(PImage src, Rectangle rect) {
        BufferedImage img = (BufferedImage) src.getImage();
        BufferedImage dest = img.getSubimage(rect.x, rect.y, rect.width, rect.height);
        return new PImage(dest);
    }
}
