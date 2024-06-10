/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.icons;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.construction.texture.BlockTextureAtlas;
import com.xbuilders.engine.utils.ResourceUtils;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

import processing.core.PGraphics;
import processing.core.PImage;
import com.xbuilders.window.ui4j.UIExtensionFrame;

/**
 *
 * @author zipCoder933
 */
public class IconManager {

    /**
     * @return the iconsHashmap
     */
    public HashMap<Item, PImage> getIconsHashmap() {
        return iconsHashmap;
    }

    private HashMap<Item, PImage> iconsHashmap;
    private CustomIconAtlas customIcons;
    private BlockTextureAtlas blockAtlas;
    PImage defaultIcon;

    /**
     *
     * @param id the id of the item
     * @return the icon
     */
    public PImage getIcon(int id) {
        return iconsHashmap.get(id);
    }

    UIExtensionFrame applet;

    public IconManager(UIExtensionFrame applet) {
        this.applet = applet;
    }

    public void loadIcons(Block[] blocks, BlockTextureAtlas blockAtlas) throws IOException {
        customIcons = new CustomIconAtlas();
        this.blockAtlas = blockAtlas;
        iconsHashmap = new HashMap<>();
        defaultIcon = new PImage(ImageIO.read(ResourceUtils.resource("items\\defaultIcon.png")));
        for (Block block : blocks) {
            int id = block.id;
            File imgPath = ResourceUtils.resource("items\\blocks\\icons\\" + id + ".png");
            if (imgPath.exists()) {
                Image img = ImageIO.read(imgPath);
                if (img != null) {
                    iconsHashmap.put(block, new PImage(img));
                }
            }
        }
    }

    public void drawItemIcon(UIExtensionFrame ex, Item item, int x, int y, int boxSize) {
        drawItemIcon(ex.getGraphics(), item, x, y, boxSize);
    }

    public void drawItemIcon(PGraphics pg, Item item, int x, int y, int boxSize) {
        boolean textureDrawn = false;
        if (item != null) {

            int[] atlas = item.getIconAtlasPosition();
            if (atlas != null) {
                textureDrawn = true;
                pg.image(customIcons.getImage(),
                        x, y,
                        boxSize, boxSize, atlas[0] * 16, atlas[1] * 16,
                        atlas[0] * 16 + 16, atlas[1] * 16 + 16);
            } else if (getIconsHashmap().containsKey(item)) {
                pg.image(getIconsHashmap().get(item), x, y, boxSize, boxSize);
                textureDrawn = true;
            } else if (item.itemType == ItemType.BLOCK) {
                Block block = (Block) item;
                if (block.texture != null) {
                    textureDrawn = true;
                    pg.image(blockAtlas.getImage(),
                            x, y,
                            boxSize, boxSize, block.texture.FRONT[0] * 16, block.texture.FRONT[1] * 16,
                            block.texture.FRONT[0] * 16 + 16, block.texture.FRONT[1] * 16 + 16);
                }
            }
        }
        if (!textureDrawn) {
            pg.image(defaultIcon, x, y, boxSize, boxSize);
        }
    }

}
