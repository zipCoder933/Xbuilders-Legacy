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
import com.xbuilders.engine.utils.ResourceUtils;
import org.joml.Vector3f;
import processing.core.PImage;
import processing.opengl.PJOGL;

import java.io.IOException;

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
    glEntityMesh body;

    @Override
    public void initialize() {
        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();

            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            try {
                body.setOBJ(ResourceUtils.resource("items\\entities\\animals\\rabbit\\body.obj"));
                body.setTexture(ResourceUtils.resource("items\\entities\\animals\\rabbit\\" + texturePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }
    }

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
        public void renderAnimal() {
            body.updateModelMatrix(modelMatrix);
            body.draw();
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
        }
    }

}
