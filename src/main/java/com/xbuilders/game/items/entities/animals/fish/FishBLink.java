/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals.fish;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.rendering.entity.glEntityMesh;
import com.xbuilders.game.Main;
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
import processing.opengl.PJOGL;

/**
 * @author zipCoder933
 */
public class FishBLink extends EntityLink {

    public FishBLink(int id, String name, String textureFile) {
        super(id, name);
        setSupplier(() -> new FishObject());
        setIconAtlasPosition(1, 3);
        this.textureFile = textureFile;
        entityMaxDistToPlayer = 30;
    }

    public PImage texture;
    glEntityMesh body;

    @Override
    public void initialize() {

        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();

            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            try {
                body.setOBJ(ResourceUtils.resource("items\\entities\\animals\\fish\\fish_B.obj"));
                body.setTexture(ResourceUtils.resource("items\\entities\\animals\\fish\\textures\\fish_B\\" + textureFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }

    }

    public String textureFile;


    public class FishObject extends FishAnimal {

        public FishObject() {
            super(new Vector3f(0.6f, 0.6f, 0.6f));
            setMaxSpeed(0.15f);
//            setFreezeMode(true);
        }

        @Override
        public final void renderFish(PGraphics g) {
            body.updateModelMatrix(modelMatrix);
            body.draw();
        }

        @Override
        public byte[] animalToBytes() {
            return null;
        }

        @Override
        public void initAnimal(byte[] bytes) {

        }

        @Override
        public void animalClicked() {
        }
    }
}
