package com.xbuilders.engine.gui.mainMenu;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.gui.Button;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.WorldUtils;
import com.xbuilders.engine.world.info.WorldInfo;
import com.xbuilders.game.Main;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.terrain.TerrainsList;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.EventAction;
import com.xbuilders.window.ui4j.UIExtension;
import com.xbuilders.window.components.NumberBox;
import com.xbuilders.window.components.TextBox;

import static com.xbuilders.engine.gui.mainMenu.MainMenu.menuBackdrop;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

public class NewWorld extends UIExtension implements MenuPage {

    TextBox worldNameBox;
    Button create, back, worldTypeButton;
    NumberBox seed, resolution;
    int worldType;
    PointerHandler ph;
    private final double DEFAULT_TERRAIN_DENSITY = 2;//No additional operations are performed to the density value
    final String[] terrainStrings;


    public NewWorld(final PointerHandler ph, VoxelGame main, final MainMenu menu, UIExtension f) {
        super(f);
        this.ph = ph;
        worldType = 0;
        terrainStrings = Main.DEV_MODE ? TerrainsList.terrainList : TerrainsList.visibleTerrainList;

        resolution = new NumberBox(this);
        resolution.setValue(DEFAULT_TERRAIN_DENSITY);

        create = new Button(this);
        back = new Button(this);
        worldTypeButton = new Button(this);
        worldTypeButton.setAction(new Runnable() {
            @Override
            public void run() {
                int index = worldType + 1;
                worldType = index % terrainStrings.length;

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
                //A resolution that is too small is not allowed for performance reasons
                resolution.setValue(MathUtils.clamp(resolution.getValue(),
                        DEFAULT_TERRAIN_DENSITY / 8,
                        DEFAULT_TERRAIN_DENSITY * 8));
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
                    WorldInfo info = new WorldInfo(worldNameBox.getValue(), terrainStrings[worldType], ph);
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
                        terrainStrings[worldType].replace("_", " "))
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
        resolution.setValue(DEFAULT_TERRAIN_DENSITY);
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
