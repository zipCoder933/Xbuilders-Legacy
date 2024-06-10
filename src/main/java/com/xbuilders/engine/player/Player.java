package com.xbuilders.engine.player;

import com.xbuilders.engine.game.PlayerSkin;
import com.xbuilders.engine.utils.worldInteraction.collision.EntityAABB;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.UIExtension;

public class Player extends UIExtension {

    public final Vector3f worldPos;
    public final EntityAABB aabb;
    public PlayerSkin skin;

    protected void drawSkin(PGraphics pg, float yRotationRad, float Yoffset) {
        pg.pushMatrix();
        pg.pushStyle();


        if (skin == null) {
            pg.translate(aabb.box.minPoint.x + (aabb.box.getXLength() / 2),
                    aabb.box.minPoint.y + (aabb.box.getYLength() / 2) + Yoffset,
                    aabb.box.minPoint.z + (aabb.box.getZLength() / 2));
            pg.noFill();
            pg.stroke(255, 255, 0);
            pg.rotateY(yRotationRad);
            pg.box(aabb.box.getXLength(), aabb.box.getYLength(), aabb.box.getZLength());
        } else {
            pg.translate(aabb.box.minPoint.x - aabb.offset.x - skin.skinOffset.x,
                    aabb.box.minPoint.y - aabb.offset.y - skin.skinOffset.y + Yoffset,
                    aabb.box.minPoint.z - aabb.offset.z - skin.skinOffset.z);
            pg.rotateY(yRotationRad);

//            VoxelGame.getShaderHandler().setShader(pg);
//            WCCf wcc = new WCCf().set(worldPos);
//            SubChunk sc = wcc.getSubChunk();
//            if (sc != null) {
//                int torch = sc.getLightMap().getSunPlusTorch(
//                        (int) Math.floor(wcc.subChunkVoxel.x),
//                        (int) Math.floor(wcc.subChunkVoxel.y),
//                        (int) Math.floor(wcc.subChunkVoxel.z));
//                float tint = MathUtils.mapAndClamp(torch, 0, 15, 0.5f, 1f);
//                VoxelGame.getShaderHandler().setMaxBrightness(tint);
//            }
            pg.resetShader();
            skin.render(pg);
//            VoxelGame.getShaderHandler().setMaxBrightness(1);
        }

        pg.popStyle();
        pg.popMatrix();

    }

    public Player(UIExtension ext) {
        super(ext);
        aabb = new EntityAABB();
        aabb.size.set(.8f, 1.95f, .8f);
        aabb.offset.set(-.4f, -.4f, -.4f);
        worldPos = aabb.worldPosition;
    }

    @Override
    public void mouseEvent(MouseEvent mouseEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {

    }


}
