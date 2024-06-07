package com.xbuilders.engine;

import static com.xbuilders.engine.VoxelGame.Page.GAME;
import static com.xbuilders.engine.VoxelGame.Page.MENU;

import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.player.Player;
import com.xbuilders.engine.gui.MessageBox;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.icons.BlockIconGenerator;
import com.xbuilders.engine.legacyConversion.LegacyConverter;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.utils.ErrorHandler;

import static com.xbuilders.engine.utils.ResourceUtils.resourcePath;

import com.xbuilders.engine.utils.preformance.MemoryProfiler;
import com.xbuilders.engine.world.World;
import com.xbuilders.game.MainThread;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.gui.mainMenu.MainMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xbuilders.window.BaseWindow;
import processing.core.KeyCode;
import processing.core.PFont;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;

public class VoxelGame extends BaseWindow {


    /**
     * @return the pointerHandler
     */
    public static PointerHandler ph() {
        return pointerHandler;
    }

    public static UserControlledPlayer getPlayer() {
        return pointerHandler.getPlayer();
    }


    public static List<Player> playerList = new ArrayList<>();
    public static boolean LOAD_WORLD_ON_STARTUP = false;

    public PointerHandler init(String[] args, boolean devMode, ProgramMode mode) throws IOException {
        List<String> argList = Arrays.asList(args);
        LOAD_WORLD_ON_STARTUP = argList.contains("loadWorldOnStartup");
        //===========================================
        //INITIALIZATION
        //===========================================

        messageBox = new MessageBox(this);
        game = new GameScene(this, messageBox.getBackground());
        world = new World();
        mainThread = new MainThread();

        ph = new PointerHandler(this, world, game, devMode, getSettings(), mainThread);
        world.setPointerHandler(ph);
        menu = new MainMenu(ph, this, messageBox.getBackground());
        mainThread.initialize(ph);
        this.pointerHandler = ph;

        if (mode == ProgramMode.LEGACY_CONVERSION) {
            startWindow();
            noLoop();
            surface.setVisible(false);
            LegacyConverter legacyConverter = new LegacyConverter(ph);
            return ph;
        } else if (mode == ProgramMode.BLOCK_ICON_SETUP) {
            System.out.println("BLOCK ICONS");
            noLoop();
            BlockIconGenerator iconGenerator = new BlockIconGenerator(ph);
            return ph;
        } else {
            startWindow();
            return ph;
        }
    }

    private static PointerHandler pointerHandler;

    /**
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * @return the messageBox
     */
    public static MessageBox getMessageBox() {
        return messageBox;
    }

    /**
     * @return the settings
     */
    public static Settings getSettings() {
        return settings;
    }

    /**
     * @return the world
     */
    public static World getWorld() {
        return world;
    }

    /**
     * @return the menu
     */
    public MainMenu getMenu() {
        return menu;
    }

    /**
     * @return the game
     */
    public static GameScene getGame() {
        return game;
    }

    @Override
    public void uiMouseEvent(MouseEvent me) {
    }

    @Override
    public void uikeyPressed(KeyEvent ke) {
        if (ph.isDevMode()) {
            if (keyIsPressed(KeyCode.BACKTICK)) {
                getShaderHandler().setTimeOfDay(getShaderHandler().getTimeOfDay() + 0.2f);
            } else if (keyIsPressed(KeyCode.ONE)) {
                getWorld().saveChangedChunks();
            }
        }
    }

    @Override
    public void uikeyReleased(KeyEvent ke) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void windowFocusGained() {
    }

    @Override
    public void windowFocusLost() {
    }

    @Override
    public void windowResized() {
    }

    private void setTitle() {
        StringBuilder sb = new StringBuilder();
        if (world.infoFile != null && getPage() == Page.GAME) {
            sb.append(world.infoFile.getName());
            sb.append(" - ");
        }
        sb.append(windowTitle);
        sb.append("   (mpf: ");
        sb.append(Math.round(getMsPerFrame()));
        sb.append("   fps: ");
        sb.append(Math.round(frameRate));
        sb.append("   Memory: ");
        sb.append(MemoryProfiler.getMemoryUsageAsString());
        sb.append(")");
        surface.setTitle(sb.toString());
    }

    @Override
    public void windowCloseEvent() {
        System.out.println("Window close event!");
        if (page == Page.GAME) {
            ph.getMainThread().saveWorld();
        } else {
            Runtime.getRuntime().halt(0);
        }
    }

    public enum Page {
        MENU, GAME, LOADING
    }

    private static MessageBox messageBox;

    private Page page;
    private MainMenu menu;
    private static GameScene game;
    private static Settings settings;
    private static World world;
    private static ShaderHandler shaderHandler;
    private static PointerHandler ph;

    public void setPage(Page page) {
        if (page == Page.MENU) {
            getMenu().enable();
            getGame().disable();
        } else if (page == Page.GAME) {
            getMenu().disable();
            getGame().enable();
        }

        if (this.getPage() == GAME && page == MENU) {
            int w = width;
            int h = height;
            setWindowSize(w + 1, h);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(VoxelGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            setWindowSize(w, h);
        }

        this.page = page;
    }

    @Override
    public void settings() {
        noSmooth();
        size(sizeX, sizeY, P3D);
        setIcon(iconPath);
    }

    private String windowTitle;
    private File iconPath;
    private int sizeX, sizeY;

    public VoxelGame(int sizeX, int sizeY, File iconPath, String windowTitle) throws IOException, InterruptedException {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.iconPath = iconPath;
        this.windowTitle = windowTitle;
        settings = new Settings();
    }

    @Override
    public void setup() {
        try {
            PFont font = createFont(resourcePath("fonts/Press_Start_2P/PressStart2P-Regular.ttf"), 16);
            textFont(font, 18);
            shaderHandler = new ShaderHandler(this,
                    ItemList.blocks.textureAtlas, ItemList.blocks.getList());

            hint(DISABLE_TEXTURE_MIPMAPS);//see https://processing.org/reference/hint_.html
            ((PGraphicsOpenGL) g).textureSampling(2);

            game.setPointerHandler(ph);

            System.out.println("OpenGL: " + PGraphicsOpenGL.OPENGL_VERSION);
            textureMode(NORMAL);
            if (getSettings().getSettingsFile().disableVsync) frameRate(5000); //Disable vsync

            surface.setResizable(true);
            setPage(Page.MENU);
        } catch (Exception ex) {
            ErrorHandler.handleFatalError(ex);
        }
    }

    MainThread mainThread;

    public enum ProgramMode {
        DEFAULT,
        BLOCK_ICON_SETUP,
        LEGACY_CONVERSION;
    }

    int mywidth, myheight;

    @Override
    public void render() {
        if (frameCount % 10 == 0) {
            setTitle();
        }
        if (mywidth != width || myheight != height) {
            mywidth = width;
            myheight = height;
            onResizeEvent(width, height);
        }


        try {
            if (getPage() == Page.GAME) {
                getGame().draw();
            } else {
                getMenu().draw();
            }
        } catch (Exception e) {
            ErrorHandler.handleFatalError(e);
            noLoop();
        }
    }


    private void onResizeEvent(int width, int height) {
//        System.out.println("RESIZE: " + width + "x" + height);
//        width = width / 2;
//        height = height / 2;
//        getGraphics().setSize(width/2, height/2);
    }

    /**
     * @return the shaderHandler
     */
    public static ShaderHandler getShaderHandler() {
        return shaderHandler;
    }

}
