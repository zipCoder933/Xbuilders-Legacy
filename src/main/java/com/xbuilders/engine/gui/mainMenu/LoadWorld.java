package com.xbuilders.engine.gui.mainMenu;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.gui.Button;
import com.xbuilders.engine.gui.GuiUtils;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.WorldUtils;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.game.Main;
import com.xbuilders.game.PointerHandler;

import static com.xbuilders.engine.gui.mainMenu.MainMenu.menuBackdrop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

import com.xbuilders.game.terrain.TerrainsList;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.EventAction;
import processing.ui4j.UIExtension;

public class LoadWorld extends UIExtension implements MenuPage {

    ArrayList<WorldInfo> savedWorlds;
    WorldInfo selectedWorld = null;
    //---------------------------------------
    Button load, delete, back, gameModeButton;
    ListItem[] listButtons;
    MainMenu menu;
    final PointerHandler ph;


    public LoadWorld(final PointerHandler ph, final MainMenu menu, UIExtension f) {
        super(f);
        this.ph = ph;
        this.menu = menu;
        load = new Button(this);
        delete = new Button(this);
        back = new Button(this);
        gameModeButton = new Button(this);

        listButtons = new ListItem[10];
        for (int i = 0; i < listButtons.length; i++) {
            listButtons[i] = new ListItem(this);
            listButtons[i].setAction(new EventAction<ListItem>() {
                @Override
                public void run(ListItem e) {
//                    System.out.println("Selected world " + e.getIndex());
                    try {
                        selectedWorld = savedWorlds.get(e.getIndex());
                    } catch (IndexOutOfBoundsException ex) {
//                        System.out.println("Error with selecting world " + ex);
                        if (savedWorlds.size() > e.getIndex() - 1) {
                            selectedWorld = savedWorlds.get(e.getIndex() - 1);
                        } else if (!savedWorlds.isEmpty()) {
                            selectedWorld = savedWorlds.get(0);
                        }
                    }
                }
            });
        }

        back.setAction(new Runnable() {
            @Override
            public void run() {
                menu.setPage(MainMenu.MenuPages.HOME);
            }
        });
        gameModeButton.setAction(new Runnable() {

            @Override
            public void run() {
                int modeIndex = Arrays.asList(GameMode.values()).indexOf(VoxelGame.getGame().mode) + 1;
                VoxelGame.getGame().mode = GameMode.values()[modeIndex % GameMode.values().length];
            }
        });
        delete.setAction(new Runnable() {
            @Override
            public void run() {
                VoxelGame.getMessageBox().show("DELETE WORLD",
                        "Are you sure you want to delete your world?\nThis action cannot be undone.",
                        new Runnable() {
                            @Override
                            public void run() {
                                WorldUtils.deleteWorld(selectedWorld.getDirectory());
                                loadSavedWorlds();
                            }
                        });
            }
        });
        load.setAction(new Runnable() {
            @Override
            public void run() {
                try {
                    WorldUtils.loadWorld(menu, selectedWorld, ph);
                } catch (IOException ex) {
                }
            }
        });
        addToFrame();
    }

    private void loadSavedWorlds() {
        try {
            savedWorlds = WorldUtils.loadSavedWorlds(ph);
            scroll = 0;
            if (!savedWorlds.isEmpty()) {
                selectedWorld = savedWorlds.get(0);
            } else {
                selectedWorld = null;
            }
        } catch (IOException ex) {
            ErrorHandler.saveErrorToLogFile(ex, "Error loading saved worlds");
        }
    }

    @Override
    public void onOpen() {
        VoxelGame.getGame().mode = GameMode.FREEPLAY;
        loadSavedWorlds();
        if (VoxelGame.LOAD_WORLD_ON_STARTUP && Main.DEV_MODE && savedWorlds.size() > 0) {
            try {
                WorldUtils.loadWorld(menu, savedWorlds.get(0), ph);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onClose() {
        savedWorlds = null;
        selectedWorld = null;
    }

    //<editor-fold defaultstate="collapsed" desc="UI rendering">
    @Override
    public void mouseEvent(MouseEvent me) {
        if (me.getAction() == MouseEvent.WHEEL) {
            if (scrollPaneHighlighted && savedWorlds != null) {
                if (savedWorlds.size() < listButtons.length) {
                    scroll = 0;
                } else {
                    int max = savedWorlds.size() - listButtons.length;
                    scroll = MathUtils.clamp(scroll + me.getCount(), 0, max);
                }
            }
        }
    }

    int w = 780;
    int h = 540;
    int y = 20;
    int centerPointOffset = -60;
    boolean scrollPaneHighlighted = false;
    int scroll = 0;

    @Override
    public void render() {
        getParentFrame().noStroke();
        getParentFrame().fill(20, 20, 20, 100);
        getParentFrame().rect(0, 0, getParentFrame().width, getParentFrame().height);

        y = 20;

        textAlign(CENTER, TOP);
        menuBackdrop(getParentFrame(), w, h);

        fill(255);
        textSize(18);
        textAlign(CENTER, TOP);
        getParentFrame().text("Load / Manage Worlds", w / 2, y);
        y += 40;

        double mouseX = getParentFrame().mouseX - getTranslations().x;
        double mouseY = getParentFrame().mouseY - getTranslations().y;
        scrollPaneHighlighted = (mouseX > 20
                && mouseY > 60
                && mouseX < (w / 2 - 20 + centerPointOffset)
                && mouseY < (h - 60));

        if (savedWorlds != null) {
            int max = listButtons.length;
            if (savedWorlds.size() < listButtons.length) {
                max = savedWorlds.size();
            } else {
                GuiUtils.drawScrollbar(getParentFrame(), w / 2 - 4 + centerPointOffset,
                        y, listButtons.length * 41, scroll, savedWorlds.size() - listButtons.length);
            }
            for (int i = 0; i < listButtons.length; i++) {
                if (i < max) {
                    if ((i + scroll) < savedWorlds.size()) {
                        listButtons[i].enable();
                        listButtons[i].setIndex(i + scroll);
                        listButtons[i].setHighlighted(selectedWorld == savedWorlds.get(i + scroll));
                        listButtons[i].draw(savedWorlds.get(i + scroll).getName(), 20, y, w / 2 - 30 + centerPointOffset);
                        y += 41;
                    } else {
                        listButtons[i].disable();
                    }
                } else {
                    listButtons[i].disable();
                }
            }
        }

        y = 60;

        translate((float) w / 2, 0.0f);

        if (selectedWorld != null) {
            int imageBoxSize = 110;

            translate(40, 0);
            fill(100, 150);
            rect(centerPointOffset, y, (float) (imageBoxSize * 1.1), imageBoxSize);
            if (selectedWorld.hasImage()) {
                getParentFrame().image(selectedWorld.getImage(), centerPointOffset, y, (float) (imageBoxSize * 1.1), imageBoxSize);
            }
            textAlign(LEFT, TOP);
            textSize(15);
            fill(255);
            y += imageBoxSize + 25;

            int rightPanelX = centerPointOffset;
            int rightPanelWidth = w / 2 + 30 + centerPointOffset;

            String selectedWorldInfo = selectedWorld.getInfoFile().worldType + " Terrain ("
                    + selectedWorld.getSize() + " x " + selectedWorld.getSize() + ")\n\n"
                    + "Last Opened " + selectedWorld.getLastSaved();

            getParentFrame().text(selectedWorld.getName(),
                    rightPanelX,
                    y + 5,
                    rightPanelWidth,
                    40);

            textSize(11);
            y += 55;
            fill(255, 150);

            getParentFrame().text(selectedWorldInfo,
                    rightPanelX,
                    y,
                    rightPanelWidth,
                    300);

            y += 80;

            translate(-40, 0);

            gameModeButton.draw(MiscUtils.snakeToCamel(VoxelGame.getGame().mode.toString()) + " Mode", 10, y, (w / 2 - 20) + centerPointOffset);
            y += 50;
            load.draw("Load World", 10, y, (w / 2 - 20) + centerPointOffset);
            y += 50;

            delete.draw("Delete World", 10, y, (w / 2 - 20) + centerPointOffset);
            y += 50;
        }
        back.draw("Back", 10, y, (w / 2 - 20) + centerPointOffset);
    }
//</editor-fold>

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

}
