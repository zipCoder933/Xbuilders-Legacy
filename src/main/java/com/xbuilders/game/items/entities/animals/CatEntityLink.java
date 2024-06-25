/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.rendering.entity.glEntityMesh;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
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
public class CatEntityLink extends EntityLink {

    public String texturePath;
    public PImage texture;

    public CatEntityLink(int id, String name, String texture) {
        super(id, name);
        setSupplier(() -> new CatObject());
        setIconAtlasPosition(4, 3);
        this.texturePath = texture;
        tags.add("cat");
        tags.add("animal");
    }

    glEntityMesh body;

    @Override
    public void initialize() {

        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();

            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            try {
                body.setOBJ(ResourceUtils.resource("items\\entities\\animals\\cat\\cat.obj"));
                body.setTexture(ResourceUtils.resource("items\\entities\\animals\\cat\\" + texturePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }
    }


    class CatObject extends LandAnimal {

        public CatObject() {
            super(new Vector3f(0.9f, 0.8f, 0.9f), 1);
            setMaxSpeed(0.19f);
            setActivity(0.8f);
            jumpWithSideCollision = true;
//            setFreezeMode(true);
//            showAABBCollisionBoxes(true);
        }

        @Override
        public void renderAnimal(PGraphics g) {
            body.updateModelMatrix(modelMatrix);
            body.draw();
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
