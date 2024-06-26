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
import com.xbuilders.window.utils.texture.TextureUtils;
import org.joml.Vector3f;
import processing.opengl.PJOGL;

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

    public glEntityMesh leg;
    public glEntityMesh body;
    public String textureFile;

    final String TEXTURE_DIR = "items\\entities\\animals\\horse\\textures\\";
    final String BODY_OBJ = "items\\entities\\animals\\horse\\mule.obj";
    final String LEG_OBJ = "items\\entities\\animals\\horse\\leg_short.obj";

    public void initialize() {
        if (body == null) {
            try {
                PJOGL pgl = Main.beginPJOGL();
                leg = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
                body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);


                int texture = TextureUtils.loadTexture(pgl.gl.getGL4(),
                        ResourceUtils.resourcePath(TEXTURE_DIR + textureFile), false).id;

                body.setOBJ(ResourceUtils.resource(BODY_OBJ));
                leg.setOBJ(ResourceUtils.resource(LEG_OBJ));
                body.setTexture(texture);
                leg.setTexture(texture);
                Main.endPJOGL();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


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

        @Override
        public final void renderAnimal() {
            try {
                if (youngAnimal) {
                    modelMatrix.scale(0.7f);//pony
                }
                //Z = animal front
                //X = animal side
                float animationTarget = travelAmount * 2;
                body.updateModelMatrix(modelMatrix);
                body.draw();
                int yLegPos = -8;
                drawLeg(leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 8, -animationTarget);
                drawLeg(leg, 0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -6, animationTarget);
                drawLeg(leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * 8, -animationTarget);
                drawLeg(leg, -0.25f, ONE_SIXTEENTH * yLegPos, ONE_SIXTEENTH * -6, animationTarget);
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
            if (bytes != null && bytes.length > 0) youngAnimal = bytes[0] == 1;
            else youngAnimal = Math.random() > 0.5;
        }

    }
}
