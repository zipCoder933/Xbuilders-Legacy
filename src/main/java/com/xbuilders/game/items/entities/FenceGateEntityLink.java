/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.entity.Entity;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.items.entity.shapeSet.OrientedShape;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

/**
 * @author zipCoder933
 */
public class FenceGateEntityLink extends EntityLink {

    PShape openTrapdoor = null;
    PShape closedTrapdoor = null;
    PImage texture;
    String textureFile;

    public FenceGateEntityLink(int id, String name, String textureFile) {
        super(id, name);
        setSupplier(() -> new FenceGateEntity());
        this.textureFile = textureFile;
    }

    public FenceGateEntityLink(int id, String name, String textureFile, int atlasX, int atlasY) {
        super(id, name);
        this.textureFile = textureFile;
        setSupplier(() -> new FenceGateEntity());
        setIconAtlasPosition(atlasX, atlasY);
    }

    public class FenceGateEntity extends Entity {

        @Override
        public boolean update() {
            return true;
        }

        //TODO: Choose if a door is left or right when the entity is setBlock.
        public FenceGateEntity() {
            super();
            closed = true;
        }

        private void updateAABB() {
            aabb.collisionEnabled = closed;
            aabb.setOffsetAndSize(0, 0, 0, 1, 1, 1);
        }

        int xzOrientation = 0;
        boolean closed = true;

        @Override
        public void initializeImmediate(byte[] bytes, boolean setByUser) {
            if (bytes != null) {
                xzOrientation = bytes[0];
                closed = bytes[1] == 1;
            } else {
                xzOrientation = getPointerHandler().getPlayer().cameraBlockOrientation().getXZ() + 1;
                if (xzOrientation > 3) {
                    xzOrientation = 0;
                }
                int setX = (int) worldPosition.x;
                int setY = (int) worldPosition.y;
                int setZ = (int) worldPosition.z;
                if (xzOrientation == 0 || xzOrientation == 2) {
                    if (VoxelGame.getWorld().getBlock(
                            setX - 1, setY, setZ).getRenderType() != BlockRenderType.FENCE) {
                        if (VoxelGame.getWorld().getBlock(
                        setX + 1, setY, setZ).getRenderType() != BlockRenderType.FENCE) {
                            xzOrientation += 1;
                        }
                    }
                } else {
                    if (VoxelGame.getWorld().getBlock(
                            setX, setY, setZ - 1).getRenderType() != BlockRenderType.FENCE) {
                        if (VoxelGame.getWorld().getBlock(
                        setX, setY, setZ + 1).getRenderType() != BlockRenderType.FENCE) {
                            xzOrientation += 1;
                        }
                    }
                }
                closed = true;
            }

            if (texture == null) {
                try {
                    texture = new PImage(ImageIO.read(ResourceUtils.resource("items\\entities\\fence gate\\textures\\" + textureFile)));
                    openTrapdoor = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\fence gate\\open.obj"));
                    closedTrapdoor = getPointerHandler().getApplet().loadShape(ResourceUtils.resourcePath("items\\entities\\fence gate\\closed.obj"));
                    openTrapdoor.setTexture(texture);
                    closedTrapdoor.setTexture(texture);
                } catch (IOException ex) {
                    ErrorHandler.handleFatalError(ex);
                }
            }
            aabb.collisionEnabled = closed;
            updateAABB();
        }

        @Override
        public boolean onClickEvent() {
            closed = !closed;
            for (Entity e : getChunk().getEntities().list) {
                int entityYPos = (int) e.worldPosition.y;
                int thisYPos = (int) worldPosition.y;
                if (e instanceof FenceGateEntity
                        && (entityYPos == thisYPos + 1
                        || entityYPos == thisYPos - 1)
                        && (int) e.worldPosition.x == (int) worldPosition.x) {
                    if ((int) e.worldPosition.z == (int) worldPosition.z) {
                        FenceGateEntity otherFence = (FenceGateEntity) e;
                        if (otherFence.closed != this.closed
                                && otherFence.xzOrientation == this.xzOrientation) {
                            e.onClickEvent();
                        }
                    }
                }
            }
            updateEntityMesh();
            updateAABB();
            return false;
        }

        @Override
        public void onDestroyClickEvent() {
            getPointerHandler().getPlayer().setBlock(BlockList.BLOCK_AIR, null,
                    (int) worldPosition.x,
                    (int) worldPosition.y,
                    (int) worldPosition.z);
            destroy(true);
        }

        @Override
        public ArrayList<OrientedShape> getStaticMeshes() {
            ArrayList<OrientedShape> shapes = new ArrayList<>();

            float rotation = 0;
            if (xzOrientation == 1) {
                rotation = (-PConstants.PI / 2);
            } else if (xzOrientation == 2) {
                rotation = (PConstants.PI);
            } else if (xzOrientation == 3) {
                rotation = (PConstants.PI / 2);
            }

            if (closed) {
                shapes.add(new OrientedShape(rotation, new Vector3f(), closedTrapdoor));
            } else {
                shapes.add(new OrientedShape(rotation, new Vector3f(), openTrapdoor));
            }
            return shapes;
        }

        @Override
        public PImage getStaticTexture() {
            return texture;
        }

        //        @Override
//        public void draw(PGraphics g) {
//            if (xzOrientation == 1) {
//                g.translate(1, 0, 0);
//                g.rotateY(-PConstants.PI / 2);
//            } else if (xzOrientation == 2) {
//                g.translate(1, 0, 1);
//                g.rotateY(PConstants.PI);
//            } else if (xzOrientation == 3) {
//                g.translate(0, 0, 1);
//                g.rotateY(PConstants.PI / 2);
//            }
//            if (closed) {
//                drawShape(g, closedTrapdoor);
//            } else {
//                drawShape(g, openTrapdoor);
//            }
//        }
//        private void drawShape(PGraphics g, PShape shape) {
//            if (texture != null) {
//                shape.setTexture(texture);
//                g.shape(shape);
//            }
//        }
        @Override
        public void toBytes(XBFilterOutputStream fout) throws IOException {
            fout.write((byte) xzOrientation);
            fout.write((byte) (closed ? 1 : 0));
        }

//        @Override
//        public ArrayList<Vector3i> getStaticBoxes(int x, int y, int z) {
//            ArrayList<Vector3i> boxes = new ArrayList<>();
//            boxes.add(new Vector3i(x, y, z));
//            return boxes;
//        }

    }

}
