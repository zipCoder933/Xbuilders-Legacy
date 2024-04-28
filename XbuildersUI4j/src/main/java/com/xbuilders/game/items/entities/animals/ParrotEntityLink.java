/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.items.entities.mobile.FlyingAnimal;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public PShape body, wing;
    public String texturePath;


    class Parrot extends FlyingAnimal {

        public Parrot() {
            super(new Vector3f(0.4f, 0.9f, 0.4f), 1);
            setActivity(0.9f);
            enableCollisionWithPlayer(false); //If parrots should have collision boxes with he player and with each other.
        }

        @Override
        public void renderAnimal(PGraphics g) {
            //x=side, z=front
            g.shape(body);
            float animSpeed = getFlyAnimationSpeed();
            bodyMatrix.set(modelMatrix);

            drawWing(g, wing, ONE_SIXTEENTH * 1.5f, ONE_SIXTEENTH * -7, ONE_SIXTEENTH * 0, animSpeed, 0.0f, -2.0f);
            drawWing(g, wing, ONE_SIXTEENTH * -1.5f, ONE_SIXTEENTH * -7, ONE_SIXTEENTH * 0, animSpeed, 0.0f, 2.0f);
        }

        Matrix4f bodyMatrix = new Matrix4f();

        private void drawWing(PGraphics g, PShape fin,
                              float x, float y, float z,
                              float animationSpeed, float min, float max) {
          modelMatrix.set(bodyMatrix);
            modelMatrix.translate(x, y, z);
            float rot = (float) MathUtils.map(
                    Math.sin((getPointerHandler().getApplet().frameCount * animationSpeed)), -1, 1, min, max);
            if (animationSpeed != 0) {
                modelMatrix.rotateZ(rot);
            }
            sendModelMatrixToShader();
            g.shape(fin);
            if (animationSpeed != 0) {
                modelMatrix.rotateZ(-rot);
            }
        }


        @Override
        public byte[] animalToBytes() {
            return null;
        }

        @Override
        public void initAnimal(byte[] bytes) {
            if (body == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\parrot\\" + texturePath)));
                    body = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\parrot\\body.obj"));
                    wing = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\parrot\\wing.obj"));
                    body.setTexture(texture);
                    wing.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
