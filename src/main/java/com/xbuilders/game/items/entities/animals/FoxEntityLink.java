/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.EntityMesh;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import com.xbuilders.test.joglDemo.demoModified.demo.glTextureMesh;
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.PJOGL;

/**
 * @author zipCoder933
 */
public class FoxEntityLink extends EntityLink {

    public FoxEntityLink(int id, String name, String texture) {
        super(id, name);
        setIconAtlasPosition(2, 3);
        setSupplier(() -> new Fox());
        this.texturePath = texture;
        tags.add("animal");
        tags.add("fox");
    }

    @Override
    public void initialize() {
       
       
        if (model == null) {
            PJOGL pgl =  Main.beginPJOGL();
            try {
                PImage texture = new PImage(
                        ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\fox\\" + texturePath)));
                model = new EntityMesh(Main.getPG(),
                        ResourceUtils.resourcePath("items\\entities\\animals\\fox\\fox.obj"));
                // model = new glTextureMesh(pgl, ShaderHandler.);
                model.setTexture(texture);
            } catch (IOException ex) {
                Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.endPJOGL();
        }

       
    }

    public EntityMesh model;
    public String texturePath;

    class Fox extends LandAnimal {

        public Fox() {
            super(new Vector3f(0.9f, 0.8f, 0.9f), 1);
            setMaxSpeed(0.17f);
            setActivity(0.8f);
            // setFreezeMode(true);
            // showAABBCollisionBoxes(true);
        }

        @Override
        public void renderAnimal(PGraphics g) {
            model.draw(g);
        }

        @Override
        public void initAnimal(byte[] bytes) {
        }

        @Override
        public byte[] animalToBytes() {
            return null;
        }
    }

}
