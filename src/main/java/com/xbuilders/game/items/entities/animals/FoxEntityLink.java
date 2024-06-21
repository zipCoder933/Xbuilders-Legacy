/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.animals;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.rendering.EntityMesh;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.LandAnimal;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.xbuilders.test.joglDemo.mesh.glTextureMesh;
import com.xbuilders.test.joglDemo.shader.TextureShader;
import com.xbuilders.window.MVP;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

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


    public glTextureMesh model;
    public MVP mvp;
    public TextureShader shader;
    public String texturePath;


    class Fox extends LandAnimal {

        public Fox() {
            super(new Vector3f(0.9f, 0.8f, 0.9f), 1);
            setMaxSpeed(0.17f);
            setActivity(0.8f);
//            setFreezeMode(true);
//            showAABBCollisionBoxes(true);
        }

        @Override
        public void renderAnimal(PGraphics g) {
//            mvp.update(VoxelGame.getGameScene().projection, VoxelGame.getGameScene().view, modelMatrix);
//            shader.bind();
//            mvp.sendToShader(VoxelGame.gl, shader.getID(), shader.uniformMVP);
//            model.draw();
//            shader.unbind();
        }


        @Override
        public void initAnimal(byte[] bytes) {
            if (model == null) {
                try {
                    mvp = new MVP(); //TODO: This is causing a crash because it needs to happen on the main thread
                    System.out.println("GL: "+VoxelGame.gl);
                    shader = new TextureShader(VoxelGame.gl);
                    model = new glTextureMesh(VoxelGame.gl, shader.attributePosition, shader.attributeUV);
                    model.setOBJ(ResourceUtils.resource("items\\entities\\animals\\fox\\fox.obj"));
                    model.setTexture(ResourceUtils.resource("items\\entities\\animals\\fox\\" + texturePath));
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public byte[] animalToBytes() {
            return null;
        }
    }

}
