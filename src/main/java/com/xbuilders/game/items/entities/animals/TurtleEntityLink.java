/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.rendering.entity.glEntityMesh;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.LandAndWaterAnimal;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.window.utils.texture.TextureUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import processing.opengl.PJOGL;

import java.io.IOException;

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

    public glEntityMesh fin1, fin2, body;
    //    back_fin1, back_fin2;
    public String textureFile;


    @Override
    public void initialize() {

        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();
            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            fin1 = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            fin2 = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);

            try {
                int texture = TextureUtils.loadTexture(pgl.gl.getGL4(),
                        ResourceUtils.resourcePath("items\\entities\\animals\\turtle\\" + textureFile), false).id;

                body.loadFromOBJ(ResourceUtils.resource("items\\entities\\animals\\turtle\\body.obj"));
                fin1.loadFromOBJ(ResourceUtils.resource("items\\entities\\animals\\turtle\\left_fin.obj"));
                fin2.loadFromOBJ(ResourceUtils.resource("items\\entities\\animals\\turtle\\right_fin.obj"));
                body.setTexture(texture);
                fin1.setTexture(texture);
                fin2.setTexture(texture);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }

    }


    public class Turtle extends LandAndWaterAnimal {
        boolean youngAnimal = false;

        public Turtle() {
            super(new Vector3f(1.0f, 0.6f, 1.0f), 2);
            setMaxSpeed(0.1f);
            setActivity(0.5f);
            aabb.offset.y += 0.2f;
//            setFreezeMode(true);
        }

        @Override
        public void animalClicked() {
        }

        @Override
        public final void renderAnimal() {
            if (playerCanSeeAnimalUnderwater()) {
                float animationTarget = 0f;
                if (getWalkAmt() > 0) {
                    animationTarget = MathUtils.map(getWalkAmt(), 0, getMaxSpeed(), 0, 0.5f);
                }
                body.updateModelMatrix(modelMatrix);
                body.draw();
                drawFin(fin2, 0, 0, ONE_SIXTEENTH * 7,
                        animationTarget, 0.0f, getPointerHandler().getApplet().frameCount, 0.4f);

                drawFin(fin1, 0, 0, ONE_SIXTEENTH * 7,
                        animationTarget, 1.5f, getPointerHandler().getApplet().frameCount, 0.4f);

//                drawFin(g, back_fin1,
//                        ONE_SIXTEENTH * -4, ONE_SIXTEENTH * -1, ONE_SIXTEENTH * -10,
//                        finAnimation, 0.0f, getPointerHandler().getApplet().frameCount, 0.15f);
//
//                drawFin(g, back_fin2,
//                        ONE_SIXTEENTH * 4, ONE_SIXTEENTH * -1, ONE_SIXTEENTH * -10,
//                        -finAnimation, 0.0f, getPointerHandler().getApplet().frameCount, 0.15f);
            }
        }

        Matrix4f finModelMatrix = new Matrix4f();

        private void drawFin(glEntityMesh fin,
                             float x, float y, float z,
                             float animationSpeed, float animationAdd, int frameCount, float multiplier) {

            finModelMatrix.set(modelMatrix).translate(x, y, z);
            float rot = (float) Math.sin((frameCount * animationSpeed) + animationAdd) * multiplier;

            if (animationSpeed != 0) {
                finModelMatrix.rotateY(rot);
            }

            fin.updateModelMatrix(finModelMatrix);
            fin.draw();
        }

        @Override
        public byte[] animalToBytes() {
            return new byte[]{
                    (byte) (youngAnimal ? 1 : 0)};
        }

        @Override
        public void initAnimal(byte[] bytes) {
//            if (bytes != null && bytes.length > 0) {
//                youngAnimal = bytes[0] == 1;
//            } else youngAnimal = Math.random() > 0.75;
        }

    }
}
