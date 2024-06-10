package com.xbuilders.engine.gui.mainMenu;

import com.xbuilders.engine.VoxelGame;
import static com.xbuilders.engine.VoxelGame.Page.GAME;
import static com.xbuilders.engine.VoxelGame.Page.MENU;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.progress.ProgressData;
import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TOP;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.UIExtension;

public class ProgressBarScreen extends UIExtension {

    /**
     * @return the progressData
     */
    public ProgressData getProgressData() {
        return progressData;
    }

    MainMenu mainMenu;
    VoxelGame main;

    public ProgressBarScreen(VoxelGame main, MainMenu menu, UIExtension f) {
        super(f);
        this.mainMenu = menu;
        this.main = main;
    }

    private ProgressData progressData = null;
    private int w = 560;

    public void render() {
        getParentFrame().noStroke();
        getParentFrame().fill(20, 20, 20, 100);
        getParentFrame().rect(0, 0, getParentFrame().width, getParentFrame().height);

        mainMenu.menuBackdrop(getParentFrame(), w, 260);
        getParentFrame().fill(255);
        getParentFrame().textSize(16);
        textAlign(CENTER, TOP);
        if (getProgressData() != null) {
            if (getProgressData().getTitle() == null) {
                getParentFrame().text("Loading", w / 2, 20);
            } else {
                getParentFrame().text(getProgressData().getTitle(), w / 2, 20);
            }
            getParentFrame().textSize(12);
            textAlign(CENTER, BOTTOM);

            getParentFrame().text(getProgressData().getTask(), w / 2, 100);
            getParentFrame().noStroke();
            if (getProgressData().isSpinMode()) {
                double val = Math.sin((double) getParentFrame().frameCount / 60);
                int barWidth = 85;
                getParentFrame().rect((float) MathUtils.map(val, -1, 1, 40, w - 40 - barWidth), 140, barWidth, 10);
            } else {
                getParentFrame().rect(40, 140, (float) MathUtils.map(getProgressData().getBar().getProgress(), 0, 1, 0, w - 80), 10);
            }
            getParentFrame().noFill();
            getParentFrame().stroke(255);
            getParentFrame().strokeWeight(4);
            getParentFrame().rect(30, 130, w - 60, 30);
            if (getProgressData().isAborted()) {
                main.setPage(MENU);
                mainMenu.setPage(MainMenu.MenuPages.HOME);
            } else if (getProgressData().isFinished() //                    && !VoxelGame.getMessageBox().isShown()
                    ) {
                try {
                    main.setPage(GAME);
                    main.getGame().startGame();
                    mainMenu.setPage(MainMenu.MenuPages.HOME);
                } catch (Exception ex) {
                    ErrorHandler.handleFatalError(ex);
                }
            }
//            if (getProgressData().getBulletin() != null && !VoxelGame.getMessageBox().isShown()) {
//                VoxelGame.getMessageBox().show(getProgressData().getBulletin());
//                getProgressData().supressBulletins();
//            }
        } else {
            getParentFrame().text("Loading", w / 2, 20);
        }
    }



    /**
     * @param progressData the progressData to set
     */
    public void setProgressData(ProgressData progressData) {
        this.progressData = progressData;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
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
