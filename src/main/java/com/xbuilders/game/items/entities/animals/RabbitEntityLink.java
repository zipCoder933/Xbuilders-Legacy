/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zipCoder933
 */
public class RabbitEntityLink extends EntityLink {

    public RabbitEntityLink(int id, String name, String texture) {
        super(id, name);
        setSupplier(() -> new Rabbit());
        setIconAtlasPosition(7, 3);
        this.texturePath = texture;
        tags.add("animal");
        tags.add("rabbit");
    }

    public PImage texture;
    public PShape model;
    public String texturePath;

    class Rabbit extends LandAnimal {

        public Rabbit() {
            super(new Vector3f(0.6f, 0.7f, 0.6f), 1);
            setMaxSpeed(0.15f);
            setActivity(0.8f);
            jumpWithSideCollision = true;
//            showAABBCollisionBoxes(true);
        }

        @Override
        public void renderAnimal(PGraphics g) {

            g.shape(model);
        }

//        @Override
//        public void postProcessMovement() {
//            if (collisionOut != null) {
//                Vector3f penetration = collisionOut.getBlockPenatatration();
//                penetration = new Vector3f(penetration).add(collisionOut.getEntityPenatatration());
//                if (Math.abs(penetration.x) > 0.02 || Math.abs(penetration.z) > 0.02) {
//                    if (System.currentTimeMillis() - lastJumpMS > 400) {
//                        getPosHandler().jump(getWorldPosition(), 8.0f);
//                        lastJumpMS = System.currentTimeMillis();
//                    }
//                } else if (getPosHandler().onGround && System.currentTimeMillis() - lastJumpMS > 400) {
//                    if (getWalkAmt() > 0 && getRandom().nextFloat() > 0.6) {
//                        getPosHandler().jump(getWorldPosition(), 5.0f);
//                    } else if (getRandom().nextFloat() > 0.99) {
//                        getPosHandler().jump(getWorldPosition(), 8.0f);
//                    }
//                }
//            }
//        }


        @Override
        public byte[] animalToBytes() {
            return null;
        }

        @Override
        public void initAnimal(byte[] bytes) {
            if (model == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\rabbit\\" + texturePath)));
                    model = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\rabbit\\body.obj"));
                    model.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
