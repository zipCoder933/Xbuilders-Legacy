/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.player.PositionLock;
import com.xbuilders.engine.items.entity.shapeSet.OrientedShape;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.math.MathUtils;

import java.util.ArrayList;

import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import processing.core.PConstants;
import processing.core.PShape;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.PImage;

import static com.xbuilders.engine.items.block.construction.blockTypes.BlockType.ONE_SIXTEENTH;

/**
 * @author zipCoder933
 */
public class BedEntityLink extends EntityLink {

    PShape bed = null;
    PImage texture = null;
    String textureFile;

    public BedEntityLink(int id, String name, String textureFile, int atlasX) {
        super(id, name);
        setSupplier(() -> new Bed());
        setIconAtlasPosition(atlasX, 4);
        this.textureFile = textureFile;
    }


    class Bed extends Entity {

        //TODO: Choose if a door is left or right when the entity is set.
        public Bed() {
            super();
            setFrustumSphereRadius(2);
        }

        @Override
        public boolean update() {
            return true;
        }

        int xzOrientation = 0;

        @Override
        public ArrayList<OrientedShape> getStaticMeshes() {
            ArrayList<OrientedShape> list = new ArrayList<OrientedShape>();
            switch (xzOrientation) {
                case 3:
                    list.add(new OrientedShape(
                            -(PConstants.PI / 2),
                            new Vector3f(0, 0, 0),
                            bed));
                    break;
                case 1:
                    list.add(new OrientedShape(
                            (PConstants.PI / 2),
                            new Vector3f(0, 0, 0),
                            bed));
                    break;
                case 2:
                    list.add(new OrientedShape(
                            (PConstants.PI),
                            new Vector3f(0, 0, 0),
                            bed));
                    break;
                default:
                    list.add(new OrientedShape(0, new Vector3f(), bed));
            }
            return list;
        }

        @Override
        public PImage getStaticTexture() {
            return texture;
        }

        @Override
        public void initialize(byte[] bytes, boolean setByUser) {

            if (bed == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\bed\\textures\\" + textureFile)));
                    bed = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\bed\\bed.obj"));
                    bed.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BedEntityLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (bytes != null) {
                xzOrientation = bytes[0];
            } else xzOrientation = getPointerHandler().getPlayer().cameraBlockOrientation().getXZ();

            if (xzOrientation == 0) {
                aabb.setOffsetAndSize(0, (ONE_SIXTEENTH * 5), 0,
                        1, ONE_SIXTEENTH * 10, 2);
            } else if (xzOrientation == 1) {
                aabb.setOffsetAndSize(-1, (ONE_SIXTEENTH * 5), 0,
                        2, ONE_SIXTEENTH * 10, 1);
            } else if (xzOrientation == 2) {
                aabb.setOffsetAndSize(0, (ONE_SIXTEENTH * 5), -1,
                        1, ONE_SIXTEENTH * 10, 2);
            } else {
                aabb.setOffsetAndSize(0, (ONE_SIXTEENTH * 5), 0,
                        2, ONE_SIXTEENTH * 10, 1);
            }
        }

        @Override
        public boolean onClickEvent() {
            UserControlledPlayer userControlledPlayer = getPointerHandler().getPlayer();
            UserControlledPlayer userControlledPlayer1 = getPointerHandler().getPlayer();
            UserControlledPlayer userControlledPlayer2 = getPointerHandler().getPlayer();
            if (MathUtils.dist(userControlledPlayer2.worldPos.x,
                    userControlledPlayer1.worldPos.y,
                    userControlledPlayer.worldPos.z,
                    worldPosition.x, worldPosition.y, worldPosition.z) < 5) {
                if (VoxelGame.getShaderHandler().getDaylightLevel() < 0.3) {
                    getPointerHandler().getPlayer().setBedtimeMode(new PositionLock(this));
                } else {
                    VoxelGame.getGame().alert("It isn't dark enough to go to sleep!");
                }
            } else {
                VoxelGame.getGame().alert("You are too far away to go to sleep!");
            }
            return false;
        }

        @Override
        public void onDestroyClickEvent() {
            int wx = (int) worldPosition.x;
            int wy = (int) worldPosition.y;
            int wz = (int) worldPosition.z;

            VoxelGame.getWorld().setBlock(BlockList.BLOCK_AIR, wx, wy, wz);

            if (xzOrientation == 0) {
                VoxelGame.getWorld().setBlock(BlockList.BLOCK_AIR, wx, wy, wz + 1);
            } else if (xzOrientation == 1) {
                VoxelGame.getWorld().setBlock(BlockList.BLOCK_AIR, wx - 1, wy, wz);
            } else if (xzOrientation == 2) {
                VoxelGame.getWorld().setBlock(BlockList.BLOCK_AIR, wx, wy, wz - 1);
            } else {
                VoxelGame.getWorld().setBlock(BlockList.BLOCK_AIR, wx + 1, wy, wz);
            }
            destroy(true);
        }


        //        @Override
//        public void draw(PGraphics g) {
//            if (xzOrientation == 3) {
//                g.translate(0, 0, 1);
//                g.rotateY(PConstants.PI / 2);
//            } else if (xzOrientation == 1) {
//                g.translate(1, 0, 0);
//                g.rotateY(-(PConstants.PI / 2));
//            } else if (xzOrientation == 2) {
//                g.translate(1, 0, 1);
//                g.rotateY(PConstants.PI);
//            }
//
//            g.shape(bed);
//        }
        @Override
        public void toBytes(XBFilterOutputStream fout) throws IOException {
            fout.write((byte) xzOrientation);
        }


//        @Override
//        public ArrayList<Vector3i> getStaticBoxes(int x, int y, int z) {
//            ArrayList<Vector3i> list = new ArrayList<Vector3i>();
//            list.add(new Vector3i(x, y, z));
//            if (xzOrientation == 0) {
//                list.add(new Vector3i(x, y, z + 1));
//            } else if (xzOrientation == 1) {
//                list.add(new Vector3i(x - 1, y, z));
//            } else if (xzOrientation == 2) {
//                list.add(new Vector3i(x, y, z - 1));
//            } else {
//                list.add(new Vector3i(x + 1, y, z));
//            }
//            return list;
//        }


    }
}
