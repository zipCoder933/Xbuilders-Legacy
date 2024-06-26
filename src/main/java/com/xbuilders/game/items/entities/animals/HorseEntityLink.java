/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.player.PositionLock;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.rendering.entity.glEntityMesh;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import com.xbuilders.window.utils.texture.TextureUtils;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.PJOGL;

import javax.imageio.ImageIO;
import java.io.File;
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

    public glEntityMesh leg;
    public glEntityMesh body;
    public static glEntityMesh saddle;
    public String textureFile;

    public void initialize() {
        if (body == null) {
            try {
                PJOGL pgl = Main.beginPJOGL();
                saddle = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
                leg = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
                body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);


                int texture = TextureUtils.loadTexture(pgl.gl.getGL4(),
                        ResourceUtils.resourcePath("items\\entities\\animals\\horse\\textures\\" + textureFile), false).id;

                saddle.setOBJ(ResourceUtils.resource("items\\entities\\animals\\horse\\horse_saddle.obj"));
                body.setOBJ(ResourceUtils.resource("items\\entities\\animals\\horse\\horse.obj"));
                leg.setOBJ(ResourceUtils.resource("items\\entities\\animals\\horse\\leg.obj"));
                body.setTexture(texture);
                saddle.setTexture(texture);
                leg.setTexture(texture);
                Main.endPJOGL();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class HorseObject extends LandAnimal {

        boolean youngAnimal = false;
        public boolean saddled = false;

        public HorseObject() {
            super(new Vector3f(1.0f, 1.2f, 1.0f), 2);
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
            float animationTarget = travelAmount;

            body.updateModelMatrix(modelMatrix);
            body.draw();
            if (saddled) {
                saddle.draw();
            }
            int yLegPos = -12;
            drawLeg(leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 7, -animationTarget);
            drawLeg(leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 7, -animationTarget + 1.5f);
            drawLeg(leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -9, animationTarget + 1.5f);
            drawLeg(leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -9, animationTarget + 2.0f);
        }


        @Override
        public byte[] animalToBytes() {

            return new byte[]{(byte) (saddled ? 1 : 0),
                    (byte) (youngAnimal ? 1 : 0)};
        }

        @Override
        public void initAnimal(byte[] bytes) {

            if (bytes != null) {
                saddled = bytes[0] == 1;
                youngAnimal = bytes[1] == 1;
            } else youngAnimal = getRandom().getSeed() > (int) (SEED_MAXIMUM * 0.8f);
        }

    }
}
