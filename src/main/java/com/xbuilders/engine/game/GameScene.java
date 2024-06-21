/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xbuilders.engine.game;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.BlockAction;
import com.xbuilders.engine.items.entity.ChunkEntitySet;
import com.xbuilders.engine.player.camera.frustum.Frustum;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.GrassGrower;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.engine.gui.game.GameMenu;
import com.xbuilders.engine.gui.game.MenuType;
import com.xbuilders.engine.gui.mainMenu.MainMenu;
import com.xbuilders.game.items.GameItemList;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import processing.core.KeyCode;

import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.TOP;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGL;
import processing.opengl.PJOGL;
import com.xbuilders.window.ui4j.UIExtension;

/**
 * @author zipCoder933
 */
public class GameScene extends UIExtension {

    /**
     * @return the devStatsOpen
     */
    public boolean isDevStatsOpen() {
        return devStatsOpen;
    }

    /**
     * @param devStatsOpen the devStatsOpen to set
     */
    public void setDevStatsOpen(boolean devStatsOpen) {
        this.devStatsOpen = devStatsOpen;
    }


    /**
     * @return the ready
     */
    public boolean isReady() {
        return ready;
    }

    public GameMenu menu;
    boolean drawEntities = true;
    public UserControlledPlayer player;
    public final Matrix4f projection = new Matrix4f();
    public final Matrix4f view = new Matrix4f();

    VoxelGame main;
    public GameMode mode = GameMode.FREEPLAY;

    public GameScene(final VoxelGame main, final UIExtension frame) throws IOException {
        super(frame);
        this.main = main;
        ready = false;

    }

    private boolean ready = false;

    public GameItemList gameItems;

    public void setPointerHandler(PointerHandler ph) throws IOException {
        this.ph = ph;
        grassGrower = new GrassGrower();
        this.gameItems = new GameItemList(
                ItemList.blocks.getList(),
                ItemList.entities.getList(),
                ItemList.tools.getList());
        menu = new GameMenu(ph, this, new Runnable() {
            @Override
            public void run() {
//                alert("Exiting world. Please standby...");
                stopGame();
            }
        });
        player = new UserControlledPlayer(menu.getBackground());
        player.disable();
        player.initialize();
        skyDome = new SkyDome(getParentFrame(), ph);
        addToFrame();
    }

    public void takeScreenshot() {
        ScreenshotUtils.takeScreenshot(this);
    }

    public void startGame() throws IOException {
        ready = false;
        alertMessage = null;
        progressMessage = null;
        VoxelGame.getShaderHandler().startGame();
        player.newGame();
        player.enable();
        ready = true;
        menu.hide();
        skyDome.newGame();
        lastSavedTime = System.currentTimeMillis();
        ScreenshotUtils.newGame();
        menu.initialize(VoxelGame.getWorld());
        ready = true;
        drawEntities = true;
        updateProjectionMatrix();
    }

    SkyDome skyDome;

    public void stopGame() {
        try {
            System.out.println("STOPPING GAME...");
            BlockAction.interruptAllBlockActions();
            VoxelGame.getWorld().closeWorld();
            player.endGame();
            player.disable();
            ready = false;
            disable();
            System.gc();
            main.getMenu().setPage(MainMenu.MenuPages.HOME);
            menu.disable();
            main.setPage(VoxelGame.Page.MENU);
            System.out.println("PAGE: " + main.getPage());
        } catch (Exception ex) {
            ErrorHandler.handleFatalError(ex);
        }
    }

    PJOGL pgl;
    GrassGrower grassGrower;

    public static final float cameraFOV = (float) (80/*degrees*/ * (Math.PI / 180));
    public static final float cameraNearDist = 0.1f;
    public static final float cameraFarDist = 400;
    PointerHandler ph;


    public void draw() throws IOException, Exception {
        if (ready) {  //frameTester.startFrame();
            updateProjectionMatrix();
            skyDome.draw(getParentFrame(), VoxelGame.getShaderHandler());
            //------------------------------------------------------------------

            getParentFrame().noFill();
            VoxelGame.getShaderHandler().update(getParentFrame().frameCount);
            //--------------------------------
            //Main Pass
            //--------------------------------
            pgl = (PJOGL) getParentFrame().beginPGL();
            pgl.frontFace(PGL.CCW);
            pgl.enable(PGL.CULL_FACE);
            //frameTester.startProcess();
            VoxelGame.getWorld().draw(getParentFrame().getGraphics(), drawEntities);
            //frameTester.endProcess("World Draw");

            skyDome.drawEffects(getParentFrame(), VoxelGame.getShaderHandler());

            //frameTester.startProcess();
            worldDrawSave();
            //frameTester.endProcess("World Save");

            //frameTester.startProcess();
            grassGrower.growGrass(player, VoxelGame.getWorld(), main);
            //frameTester.endProcess("Grass Grow");

            //frameTester.startProcess();
            player.update(getParentFrame().getGraphics());
            //frameTester.endProcess("Player Draw");

            getParentFrame().endPGL();
            getParentFrame().resetShader();
            //--------------------------------
            //GUI Pass
            //--------------------------------
            getParentFrame().pushMatrix();
            getParentFrame().resetMatrix();
            getParentFrame().ortho();
            getParentFrame().translate(-getParentFrame().width / 2, -getParentFrame().height / 2);
            getParentFrame().pushStyle();
            drawPlayerUI();
            getParentFrame().popStyle();
            getParentFrame().popMatrix();

            player.camera.isMouseFocused = (!menu.isShown() && this.isEnabled());

        }

    }

    public final Frustum frustum = new Frustum();

    public void updateProjectionMatrix() {
        float screenRatio = (float) getParentFrame().width / getParentFrame().height;
        getParentFrame().perspective(cameraFOV, screenRatio, cameraNearDist, cameraFarDist);
        projection.identity().perspective(cameraFOV, screenRatio, cameraNearDist, cameraFarDist);
        frustum.setCamInternals(cameraFOV, screenRatio, cameraNearDist, cameraFarDist);
    }

    public void updateViewMatrix(PGraphics graphics, Vector3f position, Vector3f target, Vector3f up) {
        graphics.camera(position.x, position.y, position.z, //p (camera position)
                target.x, target.y, target.z, //l (3D scene origin)
                up.x, up.y, up.z);//u (up)
        view.identity().lookAt(position, target, up);
    }

    //<editor-fold defaultstate="collapsed" desc="World Saving">
    long lastSavedTime;

    private void worldDrawSave() throws IOException {
        ScreenshotUtils.draw(getParentFrame());
        if (System.currentTimeMillis() - lastSavedTime > 30000) {
            //Save image, outer block cache and world info file

            int imageWidth = getParentFrame().width;
            int imageHeight = getParentFrame().height;

            if (imageHeight > 500) {
                imageHeight = 500;
            }
            if (imageWidth > imageHeight * 1.1) {
                imageWidth = (int) (imageHeight * 1.1);
            }
            PImage image = getParentFrame().get((getParentFrame().width / 2) - (imageWidth / 2), (getParentFrame().height / 2) - (imageHeight / 2), imageWidth, imageHeight);
            VoxelGame.getWorld().infoFile.saveImage(image);
            lastSavedTime = System.currentTimeMillis();
        }
    }

    //</editor-fold>
    public void drawPlayerUI(PGraphics graphics, boolean showBlockPanel) {
        noStroke();
        //Draw bedtime overlay
        if (player.bedtimeMode) {
            long timeSinceBedtime = System.currentTimeMillis() - player.bedTimeModeStart;
            if (timeSinceBedtime < 4000) {
                getParentFrame().fill(0, 0, 0, (float) MathUtils.map(timeSinceBedtime, 0, 4000, 0, 255));
                rect(-2, -2, getParentFrame().width + 4, getParentFrame().height + 4);
            } else if (timeSinceBedtime < 7000) {
                if (!player.bedtime_timeChanged) {
                    VoxelGame.getShaderHandler().setTimeOfDay(ShaderHandler.DAYLIGHT_TIME_OF_DAY);
                    player.bedtime_timeChanged = true;
                }
                getParentFrame().fill(0, 0, 0, (float) MathUtils.map(timeSinceBedtime, 4000, 7000, 255, 0));
                rect(-2, -2, getParentFrame().width + 4, getParentFrame().height + 4);
            } else {
                player.cancelBedtimeMode();
            }
        }

        if (!player.headBlock.isAir()
                && (!player.headBlock.isOpaque() //if head block is transparent or not pass through or in first person
                || (player.positionLock == null && !player.passThroughMode && player.camera.getThirdPersonDist() == 0))) {
            player.headBlock.drawOverlayInPlayerHead(); //Drawing
        }
        //Block panel
        if (showBlockPanel) {
            player.blockPanel.render(graphics);
        }
    }

    private final static int crosshairSize = 55;
    private final static int crosshairWidth = 2;

    public void drawCrosshair(boolean drawBar, float val) {
        getParentFrame().fill(255);
        getParentFrame().noStroke();

        getParentFrame().rect(getParentFrame().width / 2 - crosshairWidth, getParentFrame().height / 2 - 15, crosshairWidth * 2, 30);
        getParentFrame().rect(getParentFrame().width / 2 - 15, getParentFrame().height / 2 - crosshairWidth, 30, crosshairWidth * 2);

        //draw bar progress
        if (drawBar) {
            if (val < 1 && val > 0) {
                getParentFrame().strokeWeight(2);
                getParentFrame().stroke(255);
                getParentFrame().noFill();
                float barWidth2 = (float) MathUtils.map(val, 0, 1, 0, crosshairSize);
                rect((getParentFrame().width / 2) - (crosshairSize / 2),
                        (getParentFrame().height / 2) - 45, crosshairSize, 15);

                getParentFrame().fill(255, 200);
                getParentFrame().noStroke();
                rect((getParentFrame().width / 2) - (crosshairSize / 2),
                        (getParentFrame().height / 2) - 45, barWidth2, 15);
            }
        }
    }

    private void drawPlayerUI() {
        drawPlayerUI(getParentFrame().getGraphics(), !menu.inventoryScreenIsShown());

        //Crosshair
        if (!menu.isShown()) {
            boolean drawBar = player.breakItem != null
                    && getParentFrame().mousePressed
                    && mode == GameMode.WALKTHOUGH
                    && player.mouseButton == UserControlledPlayer.MouseButton.DESTROY;
            float val = drawBar == false ? 1 : (float) (System.currentTimeMillis() - player.lastMousePress) / player.breakItem.breakTimeMS();
            drawCrosshair(drawBar, val);
        }
        getParentFrame().noStroke();
        printTextStatus();

        drawAlertMessage();
        setTranslations(0, 0); //This is important. It removes all previous transformations that offset button clicks
        menu.render();
        VoxelGame.getMessageBox().render(); //We have to let the game scene render the message box in its own way
    }

    private boolean devStatsOpen = false;

    private String alertMessage;
    private long lastAlertTime;
    private String progressMessage;
    private float progress = -1;

    public void alert(String message) {
        lastAlertTime = System.currentTimeMillis();
        alertMessage = message;
    }

    public void showProgressMessage(String message) {
//        System.out.println("Prog = "+message);
        progressMessage = message;
        progress = -1;
    }

    /**
     * @param message
     * @param progress a float from 0 to 1
     */
    public void showProgressMessage(String message, float progress) {
//            System.out.println("Prog = "+message);
        progressMessage = message;
        this.progress = progress;
    }

    public void clearProgressMessage() {
//            System.out.println("Prog = null");
        progressMessage = null;
        progress = -1;
    }

    private void drawSpinnerBar() {
        if (progressMessage != null) {
            getParentFrame().textSize(12);
            getParentFrame().fill(255, 200);
            textAlign(RIGHT, TOP);
            getParentFrame().text(progressMessage, getParentFrame().width / 2 - 15, getParentFrame().height - 135);
            noFill();
            stroke(255);
            strokeWeight(3);

            int progBarX = getParentFrame().width / 2 + 15;
            int progBarY = getParentFrame().height - 140;
            int progBarWidth = 120;
            int progBarHeight = 20;
            int progInnerWidth = 20;

            rect(progBarX, progBarY, progBarWidth, progBarHeight);

            noStroke();
            fill(255);
            if (progress == -1) {
                double val = Math.sin(getParentFrame().frameCount / 10);
                rect((float) MathUtils.map(val, -1, 1,
                                progBarX,
                                progBarX + progBarWidth - progInnerWidth),
                        progBarY, progInnerWidth, progBarHeight);
            } else {
                progress = MathUtils.clamp(progress, 0, 1);
                rect(progBarX, progBarY, progBarWidth * progress, progBarHeight);
            }
        }
    }

    private void drawAlertMessage() {
        drawSpinnerBar();
        long timeSinceAlert = System.currentTimeMillis() - lastAlertTime;
        if (timeSinceAlert < 3000) {
            int alertYOffset = 125;
            if (progressMessage != null) {
                alertYOffset += 35;
            }
            getParentFrame().textSize(12);
            getParentFrame().fill(255, 200);
            textAlign(CENTER, BOTTOM);
            if (alertMessage != null) {
                getParentFrame().text(alertMessage, getParentFrame().width / 2, getParentFrame().height - alertYOffset);
            }
        }
    }

    private void printTextStatus() {
        String gameText = "Esc: Menu";
        if (isDevStatsOpen()) {
            gameText += "\nX: " + Math.round(player.worldPos.x)
                    + "  Y: " + Math.round(player.worldPos.y)
                    + "  Z: " + Math.round(player.worldPos.z);
            gameText += "\n" + ph.getWorld().updater.getStatusString();
        }
        if (player.passThroughMode) {
            gameText += "\n  (Passthough mode)";
        }
        gameText += "\nLast saved: " + ph.getMainThread().getTimeSinceLastSaved();
        player.blockTools.drawGUI(getParentFrame());
        textAlign(LEFT, TOP);
        getParentFrame().fill(255, 165);
        textSize(10);
        text(gameText, 10, 10);

        if (isDevStatsOpen()) {
            textAlign(RIGHT, TOP);
            textSize(8);
            try {
                String stats = GameDevModeStats.getDevModeStats(this);
                fill(0);
                text(stats, getParentFrame().width - 11, 11);
                fill(255);
                text(stats, getParentFrame().width - 10, 10);

            } catch (IOException ex) {
                Logger.getLogger(GameScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isProgressMessageBeingShown() {
        return progressMessage != null;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (keyIsPressed(KeyCode.ESCAPE)) {
            if (menu.isShown() && menu.inventory.isWillingToClose()) {
                menu.hide();
            } else {
                menu.show(MenuType.BASE_MENU);
            }
        } else if (!menu.isShown()) {
            if (keysArePressed(KeyCode.CTRL, KeyCode.SHIFT, KeyCode.E)) {
                ChunkEntitySet.drawEntities = !ChunkEntitySet.drawEntities;
                alert("Entity Rendering " + (ChunkEntitySet.drawEntities ? "ENABLED" : "DISABLED"));
            } else if (keyIsPressed(KeyCode.I)) {
                menu.show(MenuType.INVENTORY);
            } else if (keyIsPressed(KeyCode.CTRL) && keyIsPressed(KeyCode.SHIFT) && keyIsPressed(KeyCode.P)) {
                setDevStatsOpen(!isDevStatsOpen());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (keyIsPressed(KeyCode.SHIFT) && keyIsPressed(KeyCode.E)) {
            //Toggle entity rendering
            drawEntities = !drawEntities;
            alert("Entity Rendering " + (drawEntities ? "ENABLED" : "DISABLED"));
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void mouseEvent(MouseEvent me) {
    }


}
