/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.jogamp.opengl.GL4;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.BlockShader;
import com.xbuilders.engine.rendering.EntityMesh;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import com.xbuilders.test.joglDemo.demoModified.demo.glTextureMesh;
import com.xbuilders.window.MVP;
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.PJOGL;

/**
 * @author zipCoder933
 */
public class FoxEntityLink extends EntityLink {

    public FoxEntityLink(int id, String name, String texture) {
        super(id, name);
        setIconAtlasPosition(2, 3);
        setSupplier(() -> new Fox());
        this.texturePath = texture;
        tags.add("animal");
        tags.add("fox");
    }

    @Override
    public void initialize() {

        if (model == null) {
            PJOGL pgl = Main.beginPJOGL();
            try {
                // PImage texture = new PImage(
                // ImageIO.read(ResourceUtils.resource("items\\entities\\animals\\fox\\" +
                // texturePath)));

                // model = new EntityMesh(Main.getPG(),
                // ResourceUtils.resourcePath("items\\entities\\animals\\fox\\fox.obj"));

                model = new glTextureMesh(pgl,
                        VoxelGame.getShaderHandler().blockShader.attribute_pos,
                        VoxelGame.getShaderHandler().blockShader.attribute_uv);

                model.setTexture(ResourceUtils.resource("items\\entities\\animals\\fox\\" + texturePath));
                model.setOBJ(ResourceUtils.resource("items\\entities\\animals\\fox\\fox.obj"));

            } catch (IOException ex) {
                Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.endPJOGL();
        }

    }

    public MVP mvp = new MVP();

    public glTextureMesh model;
    public String texturePath;

    class Fox extends LandAnimal {

        public Fox() {
            super(new Vector3f(0.9f, 0.8f, 0.9f), 1);
            setMaxSpeed(0.17f);
            setActivity(0.8f);
            // setFreezeMode(true);
            // showAABBCollisionBoxes(true);
        }

        @Override
        public void renderAnimal(PGraphics g) {
            PJOGL pgl = Main.beginPJOGL();
            GL4 gl = pgl.gl.getGL4();
            mvp.update(
                    VoxelGame.getGameScene().projection,
                    VoxelGame.getGameScene().view,
                    modelMatrix);

            // BlockShader blockShader = VoxelGame.getShaderHandler().blockShader;
            // mvp.sendToShader(gl, blockShader.getID(), blockShader.uniform_ProjViewMatrix);
            // VoxelGame.getShaderHandler().resetModelMatrix();
            
            model.draw();
            Main.endPJOGL();
        }

        @Override
        public void initAnimal(byte[] bytes) {
        }

        @Override
        public byte[] animalToBytes() {
            return null;
        }
    }

}
