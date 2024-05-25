/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.game.items.entities.mobile.LandAndWaterAnimal;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
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
public class TurtleEntityLink extends EntityLink {

    public TurtleEntityLink(int id, String name, String textureFile) {
        super(id, name);
        setSupplier(() -> new Turtle());
        setIconAtlasPosition(6, 3);
        this.textureFile = textureFile;
        tags.add("animal");
        tags.add("turtle");
    }

    public PImage texture;
    public PShape fin1, fin2, body;
    //    back_fin1, back_fin2;
    public String textureFile;


    public class Turtle extends LandAndWaterAnimal {
        boolean youngAnimal = false;

        public Turtle() {
            super(new Vector3f(1.0f, 0.6f, 1.0f), 2);
            setMaxSpeed(0.1f);
            setActivity(0.5f);
//            setFreezeMode(true);
        }

        @Override
        public void animalClicked() {
        }

        @Override
        public final void renderAnimal(PGraphics g) {
            if (playerCanSeeAnimalUnderwater()) {
                //Z = animal front
                //X = animal side
//                if (youngAnimal) {
//                    g.scale(0.4f);
//                    g.pushMatrix();
//                    g.translate(0, -0.45f, 0);
//
//                }
                float animationTarget = 0f;
//                float finAnimation = 0;
                if (getWalkAmt() > 0) {
                    animationTarget = MathUtils.map(getWalkAmt(), 0, getMaxSpeed(), 0, 0.5f);
//                    if (isInWater()) {
//                        finAnimation = MathUtils.mapFloat(getWalkAmt(), 0, getMaxSpeed(), 0, 0.25f);
//                    }
                }
                g.shape(body);
                drawFin(g, fin2, 0, 0, ONE_SIXTEENTH * 7,
                        animationTarget, 0.0f, getPointerHandler().getApplet().frameCount, 0.4f);

                drawFin(g, fin1, 0, 0, ONE_SIXTEENTH * 7,
                        animationTarget, 1.5f, getPointerHandler().getApplet().frameCount, 0.4f);

//                drawFin(g, back_fin1,
//                        ONE_SIXTEENTH * -4, ONE_SIXTEENTH * -1, ONE_SIXTEENTH * -10,
//                        finAnimation, 0.0f, getPointerHandler().getApplet().frameCount, 0.15f);
//
//                drawFin(g, back_fin2,
//                        ONE_SIXTEENTH * 4, ONE_SIXTEENTH * -1, ONE_SIXTEENTH * -10,
//                        -finAnimation, 0.0f, getPointerHandler().getApplet().frameCount, 0.15f);
//                if (youngAnimal) {
//                    g.popMatrix();
//                    g.scale(1 / 0.4f);
//                }
            }
        }

        private void drawFin(PGraphics g, PShape fin,
                             float x, float y, float z,
                             float animationSpeed, float animationAdd, int frameCount, float multiplier) {

            modelMatrix.translate(x, y, z);
            float rot = (float) Math.sin((frameCount * animationSpeed) + animationAdd) * multiplier;

            if (animationSpeed != 0) {
                modelMatrix.rotateY(rot);
            }

            g.shape(fin);

            if (animationSpeed != 0) {
                modelMatrix.rotateY(-rot);
            }
            modelMatrix.translate(-x, -y, -z);
        }

        @Override
        public byte[] animalToBytes() {
            return new byte[]{
                    (byte) (youngAnimal ? 1 : 0)};
        }

        @Override
        public void initAnimal(byte[] bytes) {

            if (body == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\turtle\\" + textureFile)));
                    body = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\turtle\\body.obj"));
                    fin1 = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\turtle\\left_fin.obj"));
                    fin2 = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\turtle\\right_fin.obj"));
//                back_fin1 = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\turtle\\left_back_fin.obj"));
//                back_fin2 = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\turtle\\right_back_fin.obj"));
                    body.setTexture(texture);
                    fin1.setTexture(texture);
                    fin2.setTexture(texture);
                    aabb.offset.y -= 0.5f;
//                back_fin1.setTexture(texture);
//                back_fin2.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

//            if (bytes != null && bytes.length > 0) {
//                youngAnimal = bytes[0] == 1;
//            } else youngAnimal = Math.random() > 0.75;
        }

    }
}
