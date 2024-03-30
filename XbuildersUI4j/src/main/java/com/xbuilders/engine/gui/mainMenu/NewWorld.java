package com.xbuilders.engine.gui.mainMenu;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.gui.Button;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.WorldUtils;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.terrain.TerrainsList;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.ui4j.EventAction;
import processing.ui4j.UIExtension;
import processing.ui4j.components.NumberBox;
import processing.ui4j.components.TextBox;

import static com.xbuilders.engine.gui.mainMenu.MainMenu.menuBackdrop;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

public class NewWorld extends UIExtension implements MenuPage {

    TextBox worldNameBox;
    Button create, back, worldTypeButton;
    NumberBox seed,  resolution;
    //resolution
    int worldType;
    PointerHandler ph;
    private final float DEFAULT_RESOLUTION = 1f;


    public NewWorld(final PointerHandler ph, VoxelGame main, final MainMenu menu, UIExtension f) {
        super(f);
        this.ph = ph;
        worldType = 0;

        resolution = new NumberBox(this);
        resolution.setValue(DEFAULT_RESOLUTION);

        create = new Button(this);
        back = new Button(this);
        worldTypeButton = new Button(this);
        worldTypeButton.setAction(new Runnable() {
            @Override
            public void run() {
                int index = worldType + 1;
                worldType = index % TerrainsList.terrains.length;
            }
        });
        worldNameBox = new TextBox(this, "World Name");
        seed = new NumberBox(this);
//        size = new NumberBox(this);
        seed.setValue(0);
//        size.setValue(VoxelGame.getSettings().getSettingsFile().maxWorldSize);
//        size.setOnchangeEvent(new EventAction<NumberBox>() {
//            @Override
//            public void run(NumberBox t) {
//                t.setValue((int) MathUtils.clamp(t.getValue(), 1,
//                        VoxelGame.getSettings().getSettingsFile().maxWorldSize));
//            }
//        });
        worldNameBox.setBorderWidth(1);
        worldNameBox.setTextSize(14);

        seed.setBorderWidth(1);
//        size.setBorderWidth(1);

        worldNameBox.setOnchangeEvent(new EventAction<TextBox>() {
            @Override
            public void run(TextBox e) {
                worldNameBox.setValue(WorldUtils.formatWorldName(worldNameBox.getValue()));
            }
        });

        resolution.setOnchangeEvent(new EventAction<NumberBox>() {
            @Override
            public void run(NumberBox e) {
                resolution.setValue(MathUtils.clamp(resolution.getValue(), 0.1f, 3.0f));
            }
        });

        back.setAction(new Runnable() {
            @Override
            public void run() {
                menu.setPage(MainMenu.MenuPages.HOME);
            }
        });

        create.setAction(new Runnable() {
            @Override
            public void run() {
                try {
                    if (WorldUtils.worldNameAlreadyExists(worldNameBox.getValue(), ph)) {
                        VoxelGame.getMessageBox().show("Error creating world", "A World already exists under that name.");
                        return;
                    }
                    WorldInfo info = new WorldInfo(worldNameBox.getValue(), TerrainsList.terrains[worldType], ph);
                    if (seed.getValue() > 0) {
                        info.setSeed((int) seed.getValue());
                    }
                    System.out.println("TERRAIN RESOLUTION: " + resolution.getValue());
                    info.setResolution((float) resolution.getValue());
                    WorldUtils.createWorld(menu, info);
                } catch (Exception ex) {
                    VoxelGame.getMessageBox().handleError(ex, "Error creating world");
//                    ErrorHandler.handleFatalError(ex);
                }
            }
        });
    }

    @Override
    public void render() {
        getParentFrame().noStroke();
        getParentFrame().fill(20, 20, 20, 100);
        getParentFrame().rect(0, 0, getParentFrame().width, getParentFrame().height);

        int w = 480;
        int h = 580;
        int y = 20;
        int textBoxWidth = w - 50;

        menuBackdrop(getParentFrame(), w, h);

        fill(255);
        textSize(18);
        getParentFrame().text("Create New World", w / 2, y);

        y = label("World Name", y);
        worldNameBox.render((w / 2) - (textBoxWidth / 2), y, textBoxWidth, 30);

        y = label("Seed (Optional)", y);
        seed.render((w / 2) - (textBoxWidth / 2), y, textBoxWidth, 30);

//        y = label("World Size", y);
//        size.render((w / 2) - (textBoxWidth / 2), y, textBoxWidth, 30);

        y = label("Noise Density (x)", y);
        resolution.render((w / 2) - (textBoxWidth / 2), y, textBoxWidth, 30);

        y = label("World Type", y);
        worldTypeButton.draw(MiscUtils.toTitleCase(
                TerrainsList.terrains[worldType].replace("_", " "))
                , (w / 2) - (textBoxWidth / 2), y, textBoxWidth);

        y += 85;

        create.draw("Create New World", 20, y, w - 40);
        y += 50;

        back.draw("Back", 20, y, w - 40);
    }

    private int label(String label, int y) {
        textAlign(LEFT, TOP);
        y += 50;
        fill(255, 180);
        textSize(12);
        getParentFrame().text(label, 25, y);
        y += 20;
        return y;
    }

    @Override
    public void onOpen() {
        worldType = 0;
        resolution.setValue(DEFAULT_RESOLUTION);
        seed.setValue(0);
//        size.setValue(VoxelGame.getSettings().getSettingsFile().maxWorldSize);
        worldNameBox.setValue("World " + Long.toHexString(System.currentTimeMillis()).toUpperCase());
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
