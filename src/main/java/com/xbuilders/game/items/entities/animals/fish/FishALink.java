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
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;

import org.joml.Vector3f;
import processing.core.PImage;
import processing.opengl.PJOGL;

/**
 * @author zipCoder933
 */
public class FishALink extends EntityLink {

    public FishALink(int id, String name, String textureFile) {
        super(id, name);
        setSupplier(() -> new FishObject());
        setIconAtlasPosition(1, 3);
        this.textureFile = textureFile;
        entityMaxDistToPlayer = 30;
    }

    glEntityMesh body;

    @Override
    public void initialize() {

        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();

            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            try {
                body.loadFromOBJ(ResourceUtils.resource("items\\entities\\animals\\fish\\fish_A.obj"));
                body.setTexture(ResourceUtils.resource("items\\entities\\animals\\fish\\textures\\fish_A\\" + textureFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }

    }

    public PImage texture;
    public String textureFile;


    public class FishObject extends FishAnimal {

        public FishObject() {
            super(new Vector3f(0.6f, 0.4f, 0.6f));
            setMaxSpeed(0.17f);
//            setFreezeMode(true);
        }

        @Override
        public final void renderFish() {
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
