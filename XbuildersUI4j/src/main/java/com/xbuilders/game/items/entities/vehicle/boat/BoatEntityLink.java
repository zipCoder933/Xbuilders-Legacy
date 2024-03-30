/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.vehicle.boat;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.PositionLock;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import com.xbuilders.game.items.entities.mobile.Vehicle;
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

/**
 * @author zipCoder933
 */
public class BoatEntityLink extends EntityLink {

    public BoatEntityLink(int id, String name, String texturePath) {
        super(id, name);
        setSupplier(() -> new Boat());
        this.texturePath = texturePath;
    }

    public PImage texture;
    public PShape model;
    public String texturePath;

    class Boat extends Vehicle {

        public Boat() {
            super(new Vector3f(1, 0.8f, 1), true);
            setFrustumSphereRadius(1.5f);
        }

        @Override
        public void renderMob(PGraphics g) {
            g.shape(model);
        }

        boolean inWater = false;
        boolean rise;

        @Override
        public boolean move() {
            int wx = (int) worldPosition.x;
            int wy = (int) worldPosition.y;
            int wz = (int) worldPosition.z;

            if (playerIsRidingThis()) {
                if (isInWater()) {
                    posHandler.setGravityEnabled(false);
                    inWater = true;
                    if (VoxelGame.getWorld().getBlock(wx, wy - 1, wz).isLiquid()) {
                        rise = true;
                    }
                } else {
                    worldPosition.y -= 0.03;
                    posHandler.setGravityEnabled(true);
                    inWater = false;
                    rise = false;
                }

                float rotateSpeed = 0.5f;
                float targetSpeed = 0;
                if (getPlayer().forwardKeyPressed()) {
                    if (inWater) {
                        targetSpeed = 0.15f;
                        rotateSpeed = 2.0f;
                    } else {
                        rotateSpeed = 0.5f;
                        targetSpeed = 0.03f;
                        speedCurve = targetSpeed;
                    }
                } else if (getPlayer().backKeyPressed()) {
                    if (inWater) {
                        targetSpeed = -0.08f;
                        rotateSpeed = 1.0f;
                    } else {
                        rotateSpeed = 0.5f;
                        targetSpeed = -0.01f;
                        speedCurve = targetSpeed;
                    }
                }

                if (rise) {
                    worldPosition.y -= 0.01;
                }

                if (getPlayer().leftKeyPressed()) {
                    float rotationY1 = rotationYDeg + rotateSpeed;
                    this.rotationYDeg = rotationY1;
                } else if (getPlayer().rightKeyPressed()) {
                    float rotationY1 = rotationYDeg - rotateSpeed;
                    this.rotationYDeg = rotationY1;
                }
                speedCurve = (float) MathUtils.curve(speedCurve, targetSpeed, 0.03f);
                goForward(speedCurve);
            } else if (inWater) {
                speedCurve = (float) MathUtils.curve(speedCurve, 0, 0.03f);
                goForward(speedCurve / 2);
            }
            return get3DDistToPlayer() < getPointerHandler().getSettingsFile().playerRayMaxDistance;
        }


        float speedCurve;

//        @Override
//        public void postProcessMovement(EntityCollisionHandler.CollisionOutput lastPenetration) {
//        }

        @Override
        public void onDestructionInitiated() {
        }

        @Override
        public void onDestructionCancel() {
        }


        @Override
        public boolean onClickEvent() {
            getPointerHandler().getPlayer().setPositionLock(new PositionLock(this));
            return false;
        }

        @Override
        public void onDestroyClickEvent() {
            destroy(true);
        }

        private boolean isInWater() {
            int wx = (int) worldPosition.x;
            int wy = (int) worldPosition.y;
            int wz = (int) worldPosition.z;
            return VoxelGame.getWorld().getBlock(wx, wy + 1, wz).isLiquid()
                    || VoxelGame.getWorld().getBlock(wx, wy, wz).isLiquid()
                    || VoxelGame.getWorld().getBlock(wx, wy - 1, wz).isLiquid();
        }

        public void toBytes(XBFilterOutputStream fout) throws IOException {
        }

        @Override
        public void initialize(byte[] bytes, boolean setByUser) {
            if (model == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\boat\\textures\\" + texturePath)));
                    model = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\boat\\boat.obj"));
                    model.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            posHandler.setGravityEnabled(false);
        }


//        @Override
//        public ArrayList<OrientedShape> getStaticMeshes() {
//            if (playerIsRidingThis()) {
//                return null;
//            } else {
//                ArrayList<OrientedShape> list = new ArrayList<OrientedShape>();
//                list.add(new OrientedShape(getRotationY(), worldPosition, model));
//                return list;
//            }
//        }
//
//        @Override
//        public PImage getStaticTexture() {
//            return  texture;
//        }

    }

}
