/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.engine.rendering.entity.glEntityMesh;
import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.utils.ResourceUtils;

import processing.core.PConstants;
import processing.opengl.PJOGL;

import java.io.IOException;

import static com.xbuilders.engine.items.block.construction.blockTypes.BlockType.ONE_SIXTEENTH;

/**
 * @author zipCoder933
 */
public class BannerEntityLink extends EntityLink {

    String texturePath;

    public BannerEntityLink(int id, String name, String texturePath) {
        super(id, name);
        setSupplier(() -> new Banner());
        this.texturePath = texturePath;
        setIconAtlasPosition(0, 4);
    }


    glEntityMesh body;

    @Override
    public void initialize() {

        if (body == null) {
            PJOGL pgl = Main.beginPJOGL();

            body = new glEntityMesh(Main.getFrame(), pgl, ShaderHandler.entityShader);
            try {
                body.setOBJ(ResourceUtils.resource("items\\entities\\banner\\banner.obj"));
                body.setTexture(
                        ResourceUtils.resource("items\\entities\\banner\\" + texturePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main.endPJOGL();
        }
    }

    class Banner extends Entity {

        public Banner() {
            setFrustumSphereRadius(2f);
        }

        int xzOrientation = 0;
        float seed = 0;
        boolean againstFencepost;

        @Override
        public void initializeImmediate(byte[] bytes, boolean setByUser) {
            if (bytes != null) {
                xzOrientation = bytes[0];
                againstFencepost = (bytes[1] == 1);
                seed = (float) (Math.random() * 10);
            }

            xzOrientation = getPointerHandler().getPlayer().cameraBlockOrientation().getXZ();
            seed = (float) (Math.random() * 1000);

            int wx = (int) worldPosition.x;
            int wy = (int) worldPosition.y;
            int wz = (int) worldPosition.z;

            if (xzOrientation == 0) {
                againstFencepost = VoxelGame.getWorld().getBlock(wx, wy, wz - 1)
                        .getRenderType() == BlockRenderType.FENCE;
            } else if (xzOrientation == 1) {
                againstFencepost = VoxelGame.getWorld().getBlock(wx + 1, wy, wz)
                        .getRenderType() == BlockRenderType.FENCE;
            } else if (xzOrientation == 2) {
                againstFencepost = VoxelGame.getWorld().getBlock(wx, wy, wz + 1)
                        .getRenderType() == BlockRenderType.FENCE;
            } else {
                againstFencepost = VoxelGame.getWorld().getBlock(wx - 1, wy, wz)
                        .getRenderType() == BlockRenderType.FENCE;
            }

            switch (xzOrientation) {
                case 1:
                    aabb.setOffsetAndSize((ONE_SIXTEENTH * 13), 0, 0,
                            ONE_SIXTEENTH * 3, 2, 1);
                    break;
                case 2:
                    aabb.setOffsetAndSize(0, 0, (ONE_SIXTEENTH * 13),
                            1, 2, ONE_SIXTEENTH * 3);
                    break;
                case 3:
                    aabb.setOffsetAndSize(0, 0, 0,
                            ONE_SIXTEENTH * 3, 2, 1);
                    break;
                default:
                    aabb.setOffsetAndSize(0, 0, 0,
                            1, 2, ONE_SIXTEENTH * 3);
                    break;
            }

        }

        @Override
        public boolean onClickEvent() {
            return true;
        }

        @Override
        public void onDestroyClickEvent() {
            getPointerHandler().getPlayer().setBlock(
                    BlockList.BLOCK_AIR, null,
                    (int) worldPosition.x,
                    (int) worldPosition.y,
                    (int) worldPosition.z);
            destroy(true);
        }

        @Override
        public void draw() {
            if (xzOrientation == 0) {
                modelMatrix.translate(0, 0, 1);
                modelMatrix.rotateY(PConstants.PI / 2);

            } else if (xzOrientation == 2) {
                modelMatrix.translate(1, 0, 0);
                modelMatrix.rotateY(-(PConstants.PI / 2));
            } else if (xzOrientation == 3) {
                modelMatrix.translate(1, 0, 1);
                modelMatrix.rotateY(PConstants.PI);
            }

            if (againstFencepost) {
                modelMatrix.translate(0.4f, 0, 0);
            }
            modelMatrix.translate(1f - (ONE_SIXTEENTH * 2), 0, 0.5f);
            float frameCount = (float) (getPointerHandler().getApplet().frameCount * 0.05) + seed;
            modelMatrix.rotateZ((float) (Math.sin(frameCount) * 0.1) + 0.1f);
            body.updateModelMatrix(modelMatrix);
            body.draw();
        }


        @Override
        public void toBytes(XBFilterOutputStream fout) throws IOException {
            fout.write((byte) xzOrientation);
            fout.write((byte) (againstFencepost ? 1 : 0));
        }

        @Override
        public boolean update() {
            return true;
        }

    }
}
