/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.entity.Entity;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.entity.shapeSet.OrientedShape;
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import com.xbuilders.game.items.entities.trapdoors.BirchTrapdoorLink;
import org.joml.Vector3f;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

import static com.xbuilders.engine.items.block.construction.blockTypes.BlockType.ONE_SIXTEENTH;

/**
 * @author zipCoder933
 */
public class TrapdoorLink extends EntityLink {

    PImage texture;
    PShape openTrapdoor = null;
    PShape closedTrapdoor = null;
    String textureFile;

    public TrapdoorLink(int id, String name, String textureFile) {
        super(id, name);
        this.textureFile = textureFile;
        setSupplier(() -> new TrapdoorEntity());

    }

    public TrapdoorLink(int id, String name, String textureFile, int atlasX, int atlasY) {
        super(id, name);
        this.textureFile = textureFile;
        setSupplier(() -> new TrapdoorEntity());
        setIconAtlasPosition(atlasX, atlasY);
    }

    public class TrapdoorEntity extends Entity {

        @Override
        public boolean update() {
            return true;
        }

        //TODO: Choose if a door is left or right when the entity is set.
        public TrapdoorEntity() {
            super();
            setFrustumSphereRadius(1.5f);
            closed = true;
        }

        int xzOrientation = 0;
        boolean closed = true;


        @Override
        public boolean onClickEvent() {
            closed = !closed;
            updateEntityMesh();
            updateAABB();
            return false;
        }

        private void updateAABB() {
            if (closed) {
                aabb.setOffsetAndSize(
                        0, ONE_SIXTEENTH * 15, 0,
                        1, ONE_SIXTEENTH * 3, 1);
            } else {
                if (xzOrientation == 0) {
                    aabb.setOffsetAndSize(
                            0, ONE_SIXTEENTH * 2, 0,
                            1, 1, ONE_SIXTEENTH * 3);

                } else if (xzOrientation == 3) {
                    aabb.setOffsetAndSize(
                            0, 0 + ONE_SIXTEENTH * 2, 0,
                            ONE_SIXTEENTH * 3, 1, 1);

                } else if (xzOrientation == 2) {
                    aabb.setOffsetAndSize(
                            0, ONE_SIXTEENTH * 2, ONE_SIXTEENTH * 13,
                            1, 1, ONE_SIXTEENTH * 3);
                } else {
                    aabb.setOffsetAndSize(
                            ONE_SIXTEENTH * 13, ONE_SIXTEENTH * 2, 0,
                            ONE_SIXTEENTH * 3, 1, 1);
                }
            }
            aabb.setCursorOffsetAndSize(
                    0, 0, 0,
                    1, 1, 1);
        }

        @Override
        public void onDestroyClickEvent() {
            destroy(true);
        }


        @Override
        public ArrayList<OrientedShape> getStaticMeshes() {
            ArrayList<OrientedShape> shapes = new ArrayList<>();

            float rotation = 0;
            Vector3f offset = new Vector3f(0, 1, 0);

            if (xzOrientation == 1) {
                rotation = (-PConstants.PI / 2);
                if (!closed) {
                    offset.x += ONE_SIXTEENTH * 13;
                }
            } else if (xzOrientation == 2) {
                rotation = (PConstants.PI);
            } else if (xzOrientation == 3) {
                rotation = (PConstants.PI / 2);
                if (!closed) {
                    offset.x -= ONE_SIXTEENTH * 13;
                }
            }

            if (closed) {
                shapes.add(new OrientedShape(rotation, offset, closedTrapdoor));
            } else {
                shapes.add(new OrientedShape(rotation, offset, openTrapdoor));
            }
            return shapes;
        }

        @Override
        public PImage getStaticTexture() {
            return texture;
        }

        //
//        @Override
//        public void draw(PGraphics g) {
//            if (xzOrientation == 1) {
//                g.translate(1, 1, 0);
//                g.rotateY(-PConstants.PI / 2);
//            } else if (xzOrientation == 2) {
//                g.translate(1, 1, 1);
//                g.rotateY(PConstants.PI);
//            } else if (xzOrientation == 3) {
//                g.translate(0, 1, 1);
//                g.rotateY(PConstants.PI / 2);
//            } else {
//                g.translate(0, 1, 0);
//            }
//            if (closed) {
//                g.shape(closedTrapdoor);
//            } else {
//                g.shape(openTrapdoor);
//            }
//        }
        @Override
        public void toBytes(XBFilterOutputStream fout) throws IOException {
            fout.write((byte) xzOrientation);
            fout.write((byte) (closed ? 1 : 0));
        }

        @Override
        public void initialize(byte[] bytes, boolean setByUser) {
            if (openTrapdoor == null) {
                try {
                    openTrapdoor = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\trapdoor\\open.obj"));
                    closedTrapdoor = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\trapdoor\\closed.obj"));
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\trapdoor\\textures\\" + textureFile)));
                    openTrapdoor.setTexture(texture);
                    closedTrapdoor.setTexture(texture);
                } catch (IOException ex) {
                    Logger.getLogger(BirchTrapdoorLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (bytes != null) {
                xzOrientation = bytes[0];
                closed = bytes[1] == 1;
            } else {
                xzOrientation = getPointerHandler().getPlayer().cameraBlockOrientation().getXZ();
                closed = true;
                if (VoxelGame.getWorld().getBlock((int) worldPosition.x,
                        (int) worldPosition.y - 1,
                        (int) worldPosition.z).isSolid()) {
                    destroy(false);
                    return;
                }
            }
            updateAABB();
        }


//        @Override
//        public ArrayList<Vector3i> getStaticBoxes(int x, int y, int z) {
//            ArrayList<Vector3i> list = new ArrayList<Vector3i>();
//            list.add(new Vector3i(x, y, z));
//            return list;
//        }

    }

}
