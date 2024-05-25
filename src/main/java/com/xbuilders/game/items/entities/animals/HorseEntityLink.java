/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.player.PositionLock;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
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
public class HorseEntityLink extends EntityLink {

    public HorseEntityLink(int id, String name, String textureFile) {
        super(id, name);
        setIconAtlasPosition(3, 3);
        setSupplier(() -> new HorseObject());
        this.textureFile = textureFile;
        tags.add("animal");
        tags.add("horse");
    }

    public PImage texture;
    public PShape leg;
    public PShape body;
    public static PShape saddle;
    public String textureFile;

    public class HorseObject extends LandAnimal {

        boolean youngAnimal = false;
        public boolean saddled = false;

        public HorseObject() {
            super( new Vector3f(1.0f, 1.2f, 1.0f), 2);
            setMaxSpeed(0.17f);
            setActivity(0.6f);
            jumpWithEntitySideCollision = false;
//            setFreezeMode(true);
//            showAABBCollisionBoxes(true);
        }

        @Override
        public void animalClicked() {
            if (distToPlayer < 5 && saddled) {
                getPointerHandler().getPlayer().setPositionLock(new PositionLock(this, -1f));
            } else if (isTamed() && !getPointerHandler().getPlayer().blockPanel.curItemIsNull()) {
                if (getPointerHandler().getPlayer().blockPanel.curItemEquals(GameItems.SADDLE)) {
                    getPointerHandler().getPlayer().blockPanel.getCurItem().addQuantity(-1);
                    saddled = true;
                }
            }
        }

        //        float animatedSpeed = 0;
        @Override
        public final void renderAnimal(PGraphics g) {
            if (youngAnimal) {
               modelMatrix.scale(0.7f);//pony
            }
            float animationTarget = 0f;
            if (getWalkAmt() > 0) {
                animationTarget = MathUtils.map(getWalkAmt(), 0, getMaxSpeed(), 0, 0.5f);
            }
            sendModelMatrixToShader();
            g.shape(body);
            if (saddled) {
                g.shape(saddle);
            }
            int yLegPos = -12;
            drawLeg(g, leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 7, -animationTarget, 0.0f, getPointerHandler().getApplet().frameCount);
            drawLeg(g, leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 7, -animationTarget, 1.5f, getPointerHandler().getApplet().frameCount);
            drawLeg(g, leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -9, animationTarget, 1.5f, getPointerHandler().getApplet().frameCount);
            drawLeg(g, leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -9, animationTarget, 2.0f, getPointerHandler().getApplet().frameCount);
        }


        @Override
        public byte[] animalToBytes() {

            return new byte[]{(byte) (saddled ? 1 : 0),
                    (byte) (youngAnimal ? 1 : 0)};
        }

        @Override
        public void initAnimal(byte[] bytes) {
            if (saddle == null) {
                try {
                    saddle = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\horse\\horse_saddle.obj"));
                    saddle.setTexture(new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\horse\\textures\\brown.png"))));
                } catch (IOException ex) {
                    Logger.getLogger(HorseEntityLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (body == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\horse\\textures\\" + textureFile)));
                    body = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\horse\\horse.obj"));
                    leg = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\animals\\horse\\leg.obj"));
                    body.setTexture(texture);
                    leg.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (bytes != null) {
                saddled = bytes[0] == 1;
                youngAnimal = bytes[1] == 1;
            } else youngAnimal = getRandom().getSeed() > (int) (SEED_MAXIMUM * 0.8f);
        }

    }
}
