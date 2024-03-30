/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.entity.shapeSet.OrientedShape;
import com.xbuilders.engine.utils.ResourceUtils;

import java.util.ArrayList;

import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.joml.Vector3f;
import org.joml.Vector3i;

import static com.xbuilders.engine.items.block.construction.blockTypes.BlockType.ONE_SIXTEENTH;

/**
 * @author zipCoder933
 */
public class DoorEntityLink extends EntityLink {

    PImage texture = null;
    PShape leftDoor = null;
    PShape rightDoor = null;
    PShape openDoorLeftHinge = null;
    PShape openDoorRightHinge = null;
    String textureFile;

    public DoorEntityLink(int id, String name, String textureFile, int atlasX) {
        super(id, name);
        setSupplier(() -> new Door());
        setIconAtlasPosition(atlasX, 0);
        this.textureFile = textureFile;
        verifyTexture(textureFile);
    }

    public DoorEntityLink(int id, String name, String textureFile, int atlasX, int atlasY) {
        super(id, name);
        setSupplier(() -> new Door());
        setIconAtlasPosition(atlasX, atlasY);
        this.textureFile = textureFile;
        verifyTexture(textureFile);
    }

    private void verifyTexture(String textureFile) {
        if (!ResourceUtils.resource("items\\entities\\door\\textures\\" + textureFile).exists()) {
            throw new IllegalArgumentException("Error! Entity " + this.toString() + " Has a missing texture!");
        }
    }


    class Door extends Entity {

        @Override
        public boolean update() {
            return true;
        }

        //TODO: Choose if a door is left or right when the entity is set.
        public Door() {
            super();
            closed = true;
        }

        int xzOrientation = 0;
        boolean right = false;
        boolean closed = true;

        @Override
        public void initialize(byte[] bytes, boolean setByUser) {

            if (texture == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\door\\textures\\" + textureFile)));
                    leftDoor = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\door\\left_door.obj"));
                    leftDoor.setTexture(texture);
                    rightDoor = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\door\\right_door.obj"));
                    rightDoor.setTexture(texture);
                    openDoorLeftHinge = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\door\\left_door_open.obj"));
                    openDoorLeftHinge.setTexture(texture);
                    openDoorRightHinge = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\door\\right_door_open.obj"));
                    openDoorRightHinge.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(DoorEntityLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (bytes != null) {
                xzOrientation = bytes[0];
                closed = bytes[1] == 1;
                right = bytes[2] == 1;
            } else {
                xzOrientation = getPointerHandler().getPlayer().cameraBlockOrientation().getXZ();
                closed = true;
                right = false;
                if (xzOrientation == 0) {
                    if (VoxelGame.getWorld().getBlock((int) worldPosition.x - 1, (int) worldPosition.y, (int) worldPosition.z).isSolid()) {
                        right = true;
                    }
                } else if (xzOrientation == 1) {
                    if (VoxelGame.getWorld().getBlock((int) worldPosition.x, (int) worldPosition.y, (int) worldPosition.z - 1).isSolid()) {
                        right = true;
                    }
                } else if (xzOrientation == 2) {
                    if (VoxelGame.getWorld().getBlock((int) worldPosition.x + 1, (int) worldPosition.y, (int) worldPosition.z).isSolid()) {
                        right = true;
                    }
                } else {
                    if (VoxelGame.getWorld().getBlock((int) worldPosition.x, (int) worldPosition.y, (int) worldPosition.z + 1).isSolid()) {
                        right = true;
                    }
                }
            }

            updateAABB();
        }

        private void updateAABB() {
            if (closed) {
                if (xzOrientation == 0) {
                    aabb.setOffsetAndSize(0, -1, ONE_SIXTEENTH * 13, 1, 2, ONE_SIXTEENTH * 3);
                } else if (xzOrientation == 1) {
                    aabb.setOffsetAndSize(0, -1, 0, ONE_SIXTEENTH * 3, 2, 1);
                } else if (xzOrientation == 2) {
                    aabb.setOffsetAndSize(0, -1, 0, 1, 2, ONE_SIXTEENTH * 3);
                } else {
                    aabb.setOffsetAndSize(ONE_SIXTEENTH * 13, -1, 0, ONE_SIXTEENTH * 3, 2, 1);
                }
            } else if (right) {
                if (xzOrientation == 1) {
                    aabb.setOffsetAndSize(0, -1, 0, 1, 2, ONE_SIXTEENTH * 3);
                } else if (xzOrientation == 2) {
                    aabb.setOffsetAndSize(ONE_SIXTEENTH * 13, -1, 0, ONE_SIXTEENTH * 3, 2, 1);
                } else if (xzOrientation == 3) {
                    aabb.setOffsetAndSize(0, -1, ONE_SIXTEENTH * 13, 1, 2, ONE_SIXTEENTH * 3);
                } else {
                    aabb.setOffsetAndSize(0, -1, 0, ONE_SIXTEENTH * 3, 2, 1);
                }
            } else {
                if (xzOrientation == 1) {
                    aabb.setOffsetAndSize(0, -1, ONE_SIXTEENTH * 13, 1, 2, ONE_SIXTEENTH * 3);
                } else if (xzOrientation == 2) {
                    aabb.setOffsetAndSize(0, -1, 0, ONE_SIXTEENTH * 3, 2, 1);
                } else if (xzOrientation == 3) {
                    aabb.setOffsetAndSize(0, -1, 0, 1, 2, ONE_SIXTEENTH * 3);
                } else {
                    aabb.setOffsetAndSize(ONE_SIXTEENTH * 13, -1, 0, ONE_SIXTEENTH * 3, 2, 1);
                }
            }
        }

        @Override
        public boolean onClickEvent() {
            closed = !closed;
            updateEntityMesh();
            updateAABB();
            return false;
        }

        @Override
        public void onDestroyClickEvent() {
            getPointerHandler().getPlayer().setBlock(
                    BlockList.BLOCK_AIR, null,
                    (int) worldPosition.x,
                    (int) worldPosition.y,
                    (int) worldPosition.z);
            getPointerHandler().getPlayer().setBlock(
                    BlockList.BLOCK_AIR, null,
                    (int) worldPosition.x,
                    (int) worldPosition.y - 1,
                    (int) worldPosition.z);
            destroy(true);
        }


        @Override
        public ArrayList<OrientedShape> getStaticMeshes() {
            ArrayList<OrientedShape> shapes = new ArrayList<>();

            float rotation = 0;
            Vector3f offset = new Vector3f();

            if (xzOrientation == 0) {
                rotation = (PConstants.PI / 2);
                if (closed) {
                    offset = new Vector3f(0, 0, 1 - ONE_SIXTEENTH * 3);
                } else {
                    if (right) {
                        offset = new Vector3f(-1 + ONE_SIXTEENTH * 3, 0, 0);
                    } else {
                        offset = new Vector3f(1 - ONE_SIXTEENTH * 3, 0, 0);
                    }
                }
            } else if (xzOrientation == 2) {
                rotation = (-(PConstants.PI / 2));
                if (closed) {
                    offset = new Vector3f(0, 0, -1 + ONE_SIXTEENTH * 3);
                } else {
                    if (right) {
                        offset = new Vector3f(1 - ONE_SIXTEENTH * 3, 0, 0);
                    } else {
                        offset = new Vector3f(-1 + ONE_SIXTEENTH * 3, 0, 0);
                    }
                }
            } else if (xzOrientation == 3) {
                rotation = (PConstants.PI);
                offset = new Vector3f(0, 0, 0);
            }
            if (right) {
                if (closed) {
                    shapes.add(new OrientedShape(rotation, offset, rightDoor));
                } else {
                    shapes.add(new OrientedShape(rotation, offset, openDoorRightHinge));
                }
            } else {
                if (closed) {
                    shapes.add(new OrientedShape(rotation, offset, leftDoor));
                } else {
                    shapes.add(new OrientedShape(rotation, offset, openDoorLeftHinge));
                }
            }
            return shapes;
        }

        @Override
        public PImage getStaticTexture() {
            return texture;
        }

        //        @Override
//        public void draw(PGraphics g) {
//            if (xzOrientation == 0) {
//                g.translate(0, 0, 1);
//                g.rotateY(PConstants.PI / 2);
//            } else if (xzOrientation == 2) {
//                g.translate(1, 0, 0);
//                g.rotateY(-(PConstants.PI / 2));
//            } else if (xzOrientation == 3) {
//                g.translate(1, 0, 1);
//                g.rotateY(PConstants.PI);
//            }
//            if (right) {
//                if (closed) {
//                    drawShape(g, rightDoor);
//                } else {
//                    drawShape(g, openDoorRightHinge);
//                }
//            } else {
//                if (closed) {
//                    drawShape(g, leftDoor);
//                } else {
//                    drawShape(g, openDoorLeftHinge);
//                }
//            }
//        }
//        private void drawShape(PGraphics g, PShape shape) {
//            if (texture != null) {
//                g.shape(shape);
//            }
//        }
        @Override
        public void toBytes(XBFilterOutputStream fout) throws IOException {
            fout.write((byte) xzOrientation);
            fout.write((byte) (closed ? 1 : 0));
            fout.write((byte) (right ? 1 : 0));
        }

        @Override
        public ArrayList<Vector3i> getStaticBoxes(int x, int y, int z) {
            ArrayList<Vector3i> list = new ArrayList<Vector3i>();
            list.add(new Vector3i(x, y, z));
            list.add(new Vector3i(x, y - 1, z));
            return list;
        }


    }
}
