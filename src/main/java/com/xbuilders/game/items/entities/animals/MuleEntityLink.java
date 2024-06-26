/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
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
public class MuleEntityLink extends EntityLink {

    public MuleEntityLink(int id, String name, String textureFile) {
        super(id, name);
        setSupplier(() -> new MuleObject());
        setIconAtlasPosition(5, 3);
        this.textureFile = textureFile;
        tags.add("animal");
        tags.add("mule");

    }

    public PImage texture;
    public PShape leg;
    public PShape body;
    public String textureFile;


    public class MuleObject extends LandAnimal {

        boolean youngAnimal = false;

        public MuleObject() {
            super(new Vector3f(1.0f, 1.2f, 1.0f), 2);
            setMaxSpeed(0.16f);
            setActivity(0.75f);
            jumpWithEntitySideCollision = false;
//            setFreezeMode(true);
//            showAABBCollisionBoxes(true);
        }

        //        float animatedSpeed = 0;
        @Override
        public final void renderAnimal(PGraphics g) {
            try {
                if (youngAnimal) {
                    g.scale(0.7f);//pony
                }
                //Z = animal front
                //X = animal side
//                float animationTarget = 0f;
//                if (getWalkAmt() > 0) {
//                    animationTarget = MathUtils.map(getWalkAmt(), 0, getMaxSpeed(), 0, 0.5f);
//                }
//                g.shape(body);
//                int yLegPos = -8;
//                int frameCount = getPointerHandler().getApplet().frameCount;
//                drawLeg(g, leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 8, -animationTarget, 0.0f, frameCount);
//                drawLeg(g, leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -6, animationTarget, 1.5f, frameCount);
//                drawLeg(g, leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 8, -animationTarget, 1.5f, frameCount);
//                drawLeg(g, leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -6, animationTarget, 2.0f, frameCount);
                if (youngAnimal) {
                    g.scale(1 / 0.7f);//pony
                }
            } catch (Exception ex) {
                Logger.getLogger(MuleEntityLink.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\horse\\textures\\" + textureFile)));
                    body = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\horse\\mule.obj"));
                    leg = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\horse\\leg_short.obj"));
                    body.setTexture(texture);
                    leg.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            if (bytes != null && bytes.length > 0) youngAnimal = bytes[0] == 1;
            else youngAnimal = Math.random() > 0.5;
        }

    }
}
