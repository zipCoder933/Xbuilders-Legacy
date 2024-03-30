///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.engine.utils.BFS;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author zipCoder933
// */
//public abstract class QueueSaver<T> {
//
//    public void load(HashQueue<T> hashset, File queueFile) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(queueFile))) {
//            String line = reader.readLine();
//            while (line != null) {
//                line = reader.readLine();
//                if (line == null || line.isBlank()) {
//                    continue;
//                }
//
//                T node = nodeFromString(line, ',');
//                hashset.add(node);
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(HashQueue.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(HashQueue.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void load(ListQueue<T> list, File queueFile) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(queueFile))) {
//            String line = reader.readLine();
//            while (line != null) {
//                line = reader.readLine();
//                if (line == null || line.isBlank()) {
//                    continue;
//                }
//
//                T node = nodeFromString(line, ',');
//                list.add(node);
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(HashQueue.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(HashQueue.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void save(HashQueue<T> queue, File queueFile) {
//        try (FileWriter fw = new FileWriter(queueFile)) {
//            for (T node : queue.getHashset()) {
//                fw.write(nodeToFileString(node, ',') + "\n");
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(HashQueue.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void save(ListQueue<T> queue, File queueFile) {
//        try (FileWriter fw = new FileWriter(queueFile)) {
//            for (T node : queue.getList()) {
//                fw.write(nodeToFileString(node, ',') + "\n");
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(HashQueue.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public abstract T nodeFromString(String line, char delimiter);
//
//    public abstract String nodeToFileString(T node, char delimiter);
//}
