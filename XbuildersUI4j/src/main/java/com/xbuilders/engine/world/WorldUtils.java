// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world;

import java.nio.file.Files;
import java.io.File;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.ResourceUtils;
import java.util.ArrayList;

import com.xbuilders.engine.VoxelGame;
import java.io.IOException;
import com.xbuilders.engine.utils.progress.ProgressData;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.engine.gui.mainMenu.MainMenu;

public class WorldUtils {

    public static final void loadWorld(final MainMenu m, final WorldInfo selectedWorld, final PointerHandler ph) throws IOException {
        final ProgressData prog = new ProgressData();
        WorldInfo info = null;
        try {
            info = new WorldInfo(selectedWorld.getDirectory(), ph);
        } catch (IOException ex) {
        }
        VoxelGame.getWorld().loadWorld(info, prog, false);
        m.startGame(prog);
    }

    public static final void createWorld(final MainMenu m, final WorldInfo info) {
        final ProgressData prog = new ProgressData();
        VoxelGame.getWorld().loadWorld(info, prog, true);
        m.startGame(prog);
    }

    public static WorldInfo getWorldInfoFromName(String name, final PointerHandler ph) throws IOException {
        name = formatWorldName(name);
        for (final WorldInfo info : loadSavedWorlds(ph)) {
            if (info.getName().equals(name)) {
                return info;
            }
        }
        return null;
    }

    public static ArrayList<WorldInfo> loadSavedWorlds(final PointerHandler ph) throws IOException {
        final ArrayList<WorldInfo> arr = new ArrayList<WorldInfo>();
        for (final File subDir : ResourceUtils.WORLDS_DIR.listFiles()) {
            if (subDir.isDirectory()) {
                WorldInfo info = null;
                try {
                    info = new WorldInfo(subDir, ph);
                } catch (Exception e) {
                    ErrorHandler.saveErrorToLogFile(e, "unable to retrieve info on saved world");
                }
                if (info != null) {
                    arr.add(info);
                }
            }
        }
        return arr;
    }

    public static int countSavedWorlds() throws IOException {
        return ResourceUtils.WORLDS_DIR.listFiles().length;
    }

    public static String formatWorldName(final String name) {
        return name.replaceAll("[^A-z\\s0-9_-]", "").replace("^", "").strip();
    }

    public static boolean worldNameAlreadyExists(final String name, final PointerHandler ph) throws IOException {
        for (final WorldInfo world : loadSavedWorlds(ph)) {
            final String fileName = world.getName();
            if (fileName.equals(formatWorldName(name))) {
                return true;
            }
        }
        return false;
    }

    public static File newDirectory(String name) throws IOException {
        System.out.println("Creating new world dir. (\"" + name + "\")");
        File directory = new File(ResourceUtils.WORLDS_DIR.getAbsolutePath() + "\\" + name);

        if (directory.exists()) {
            throw new IOException("A world already exists under that name.");
        }
        return directory;
    }

    public static void deleteWorld(final File dir) {
        try {
            System.out.println("Deleting " + dir.toString());
            for (final File file : dir.listFiles()) {
                file.delete();
            }
            Files.delete(dir.toPath());
        } catch (Throwable t) {
            ErrorHandler.handleFatalError(t);
        }
    }
}
