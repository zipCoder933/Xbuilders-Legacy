/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals.fish;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.game.items.entities.mobile.FishAnimal;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

/**
 * @author zipCoder933
 */
public class FishBLink extends EntityLink {

    public FishBLink(int id, String name, String textureFile) {
        super(id, name);
        setSupplier(() -> new FishObject());
        setIconAtlasPosition(1, 3);
        this.textureFile = textureFile;

    }

    public PImage texture;
    public PShape body;
    public String textureFile;


    public class FishObject extends FishAnimal {

        public FishObject() {
            super(new Vector3f(0.6f, 0.6f, 0.6f));
            setMaxSpeed(0.15f);
//            setFreezeMode(true);
        }

        @Override
        public final void renderFish(PGraphics g) {
            g.shape(body);
        }

        @Override
        public byte[] animalToBytes() {
            return null;
        }

        @Override
        public void initAnimal(byte[] bytes) {
            if (body == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\fish\\textures\\fish_B\\" + textureFile)));
                    body = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\fish\\fish_B.obj"));
                    body.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public void animalClicked() {
        }
    }
}
