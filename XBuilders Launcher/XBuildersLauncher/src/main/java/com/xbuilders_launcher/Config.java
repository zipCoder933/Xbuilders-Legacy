package com.xbuilders_launcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {

    //Default values
    File xbuilders2File = null;
    File xbuilder3File = null;
    boolean runConsoleOut = false;

    public Config(File f) throws IOException {
        //First make sure the config file exists
        if (!f.exists()) {
            //Create the config.txt file
            String str = "XB2\tXBuilders-2-main\\XBuildersUI4J.jar\n" +
                    "XB3\tXBuilders-main\\XBuilders3.jar\n" +
                    "runConsoleOut\tfalse";

            Files.write(f.toPath(), str.getBytes());
        }

        String[] lines = new String(Files.readAllBytes(f.toPath())).split("\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
            String[] parts = lines[i].split("\t");
            String key = parts[0];
            String value = parts[1];
            switch (key) {
                case "XB2":
                    xbuilders2File = getRelativePath(value);
                    break;
                case "XB3":
                    xbuilder3File = getRelativePath(value);
                    break;
                case "runConsoleOut":
                    runConsoleOut = Boolean.parseBoolean(value);
                    break;
            }
        }
    }


    private static File getRelativePath(String relativePath) {
        File currentDir = new File(System.getProperty("user.dir"));
        //If relativePath starts with ../ set currentDir up one level
        while (relativePath.startsWith("../") || relativePath.startsWith("..\\")) {
            currentDir = currentDir.getParentFile();
            relativePath = relativePath.substring(3);
        }
        return new File(currentDir, relativePath);
    }

    public String toString() {
        String str = "XB2\t" + xbuilders2File.getAbsolutePath() + "\n" +
                "XB3\t" + xbuilder3File.getAbsolutePath() + "\n" +
                "runConsoleOut\t" + runConsoleOut + "\n";
        return str;
    }
}
