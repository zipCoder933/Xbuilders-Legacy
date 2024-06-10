package com.xbuilders.engine.items.icons;

import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;

import static com.xbuilders.engine.items.icons.IconGeneratorUtils.deleteAllExistingIcons;

import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.game.Main;
import com.xbuilders.game.PointerHandler;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PShader;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 * @author zipCoder933
 */
public class BlockIconGenerator extends UIExtensionFrame {

    PShader blockShader;
    PointerHandler ph;

    public BlockIconGenerator(PointerHandler ph) {
        this.ph = ph;
        startWindow();
    }

    PGraphicsOpenGL pg;
    static boolean ready = false;
    IconGeneratorUtils utils;

    @Override
    public void settings() {
        size(128, 128, P3D);
    }

    @Override
    public void setup() {
        System.out.println("Generating Icons...");
        utils = new IconGeneratorUtils(ph, this);
        blockShader = loadShader(
                ResourceUtils.resourcePath("Shaders\\iconGenerator\\iconFrag.glsl"),
                ResourceUtils.resourcePath("Shaders\\iconGenerator\\iconVert.glsl"));

        // textureMode(NORMAL);
        // hint(DISABLE_DEPTH_TEST);
        pg = (PGraphicsOpenGL) g;
        hint(DISABLE_TEXTURE_MIPMAPS);
        ((PGraphicsOpenGL) g).textureSampling(2);
        pg.setSize(width, height);
        index = 0;

        if (Main.BLOCK_ICON_DIR.exists()) {
            System.out.println("Deleting all existing icons...");
            deleteAllExistingIcons();
        }
        ready = true;
    }

    int index = 0;

    @Override
    public void render() {
        background(0, 0);
        if (ready) {
            index++;
            pg.shader(blockShader);
            pg.clear();
            Block block = ItemList.blocks.getList()[index];
            System.out.println("Rendering: " + block);
            utils.generateBlockIcon(block, g);
            if (index >= ItemList.blocks.getList().length - 1) {
                noLoop();
                System.out.println("Done generating icons!");
                Runtime.getRuntime().exit(0);
            }
        }
    }

    @Override
    public void uiMouseEvent(MouseEvent me) {
    }

    @Override
    public void uikeyPressed(KeyEvent ke) {
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

    @Override
    public void windowCloseEvent() {
    }

}
