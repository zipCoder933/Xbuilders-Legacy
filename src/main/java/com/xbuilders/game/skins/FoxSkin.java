package com.xbuilders.game.skins;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.PlayerSkin;
import com.xbuilders.engine.player.Player;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.ResourceUtils;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

import javax.imageio.ImageIO;
import java.io.IOException;

public class FoxSkin extends PlayerSkin {
    public static PImage texture;
    public static PShape model;

    public FoxSkin(Player player) {
        super(player);
//        player.aabb.size.y = 1.5f;
        skinOffset.set(0f, -1.5f, 0f);
    }

    public void render(PGraphics g) {
        if (model == null) {
            System.out.println("loading texture");
            try {
                texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\fox\\red.png")));
                model = VoxelGame.ph().getApplet()
                        .loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\fox\\fox.obj"));
                model.setTexture(texture);
            } catch (IOException ex) {
                ErrorHandler.handleFatalError(ex);
            }
        }
        g.shape(model);
    }
}
