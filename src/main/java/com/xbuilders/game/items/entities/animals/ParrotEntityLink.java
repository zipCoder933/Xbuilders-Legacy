/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.rendering.entity.glEntityMesh;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.FlyingAnimal;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PJOGL;

import java.io.IOException;

import static com.xbuilders.engine.items.block.construction.blockTypes.BlockType.ONE_SIXTEENTH;

/**
 * @author zipCoder933
 */
public class ParrotEntityLink extends EntityLink {

    public ParrotEntityLink(int id, String name, String texture) {
        super(id, name);
        setSupplier(() -> new Parrot());
        setIconAtlasPosition(8, 3);
        this.texturePath = texture;
        tags.add("animal");
        tags.add("bird");
        tags.add("parrot");
    }

    public PImage texture;
    public glEntityMesh body, wing;
    public String texturePath;

    public void initialize() {
        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();
            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            wing = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            try {
                body.setOBJ(ResourceUtils.resource("items\\entities\\animals\\parrot\\body.obj"));
                wing.setOBJ(ResourceUtils.resource("items\\entities\\animals\\parrot\\wing.obj"));

                body.setTexture(ResourceUtils.resource("items\\entities\\animals\\parrot\\" + texturePath));
                wing.setTexture(ResourceUtils.resource("items\\entities\\animals\\parrot\\" + texturePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }
    }


    class Parrot extends FlyingAnimal {

        public Parrot() {
            super(new Vector3f(0.4f, 0.9f, 0.4f), 1);
            setActivity(0.9f);
            enableCollisionWithPlayer(false); //If parrots should have collision boxes with he player and with each other.
        }

        @Override
        public void renderAnimal() {
            //x=side, z=front
            body.updateModelMatrix(modelMatrix);
            body.draw();
            float animSpeed = getFlyAnimationSpeed();


            drawWing(ONE_SIXTEENTH * 1.5f, ONE_SIXTEENTH * -7, ONE_SIXTEENTH * 0, animSpeed, 0.0f, -2.0f);
            drawWing( ONE_SIXTEENTH * -1.5f, ONE_SIXTEENTH * -7, ONE_SIXTEENTH * 0, animSpeed, 0.0f, 2.0f);
        }

        Matrix4f wingMatrix = new Matrix4f();

        private void drawWing(
                              float x, float y, float z,
                              float animationSpeed, float min, float max) {
            wingMatrix.set(modelMatrix).translate(x, y, z);
            float rot = (float) MathUtils.map(
                    Math.sin((getPointerHandler().getApplet().frameCount * animationSpeed)), -1, 1, min, max);
            if (animationSpeed != 0) {
                wingMatrix.rotateZ(rot);
            }
            wing.updateModelMatrix(wingMatrix);
            wing.draw();
        }


        @Override
        public byte[] animalToBytes() {
            return null;
        }

        @Override
        public void initAnimal(byte[] bytes) {
        }
    }
}
