package com.xbuilders.engine.player.camera;

import com.jogamp.newt.opengl.GLWindow;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.camera.frustum.Frustum;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.player.raycasting.RayCasting;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.math.MathUtils;

import java.awt.*;

import org.joml.Vector2i;
import org.joml.Vector3f;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.ui4j.UIExtensionFrame;

public class Camera {
    UIExtensionFrame app;
    public boolean cursorRayHitAllBlocks = false;
    public final Vector3f up = new Vector3f(0f, 1f, 0f);
    public final Vector3f right = new Vector3f(1f, 0f, 0f);
    public final Vector3f look = new Vector3f(0f, 0.5f, 1f);
    public final Vector3f target = new Vector3f();
    public final Vector3f position = new Vector3f();
    public final Vector3f cursorRaycastLook = new Vector3f();
    public final Vector3f cameraRaycastLook = new Vector3f();
    public final Vector3f cameraForward = new Vector3f();
    private Point mouse;
    public final Frustum frustum;
    UserControlledPlayer player;
    GameScene game;
    float sensitivity = 0.15f;
    public Robot robot;

    private final float PI = (float) MathUtils.PI;
    private final float TWO_PI = PI * 2;

    public float tilt, pan, normalizedPan;
    public final Vector2i simplifiedPanTilt = new Vector2i();

    public boolean isMouseFocused = true;

    public int rayMaxDistance = 1000;//Max distance for front ray
    public final Ray cursorRay, cameraViewRay;
    private float thirdPersonDist = 0;


    /**
     * @return the thirdPersonDist
     */
    public float getThirdPersonDist() {
        return thirdPersonDist;
    }

    /**
     * @param thirdPersonDist the thirdPersonDist to set
     */
    public void setThirdPersonDist(float thirdPersonDist) {
        this.thirdPersonDist = MathUtils.clamp(thirdPersonDist, -70, 70);
        if (MathUtils.dist(thirdPersonDist, 0) < 2) {
            this.thirdPersonDist = 0;
        }
    }

    public void cycleToNextView(int dist) {
        if (getThirdPersonDist() == 0) {
            setThirdPersonDist(dist);
        } else if (getThirdPersonDist() > 0) {
            setThirdPersonDist(-dist);
        } else {
            setThirdPersonDist(0);
        }
    }


    public Camera(UIExtensionFrame app, GameScene game, UserControlledPlayer player) {
        this.app = app;
        this.game = game;
        this.player = player;

        try {
            robot = new Robot();
        } catch (Exception e) {
        }

        cursorRay = new Ray();
        cameraViewRay = new Ray();
        pan = 0;
        tilt = 0f;
        frustum = new Frustum();
        lastWidth = player.getParentFrame().width;
        lastHeight = player.getParentFrame().height;
        frustum.setCamInternals(game.cameraFOV, game.getCameraRatio(), game.cameraNearDist, game.cameraFarDist);
    }

    int lastWidth = 0;
    int lastHeight = 0;

    public void centerMouse() {
        if (app.focused) {
            int x = ((GLWindow) app.getSurface().getNative()).getX();
            int y = ((GLWindow) app.getSurface().getNative()).getY();
            int w = app.width;
            int h = app.height;

            robot.mouseMove(w / 2 + x, h / 2 + y);
        }
    }


    public void draw(PGraphics graphics) {
        try {
            mouse = MouseInfo.getPointerInfo().getLocation();
        } catch (NullPointerException ex) {
        }
        int width = player.getParentFrame().width;
        int height = player.getParentFrame().height;

        if (isMouseFocused && app.focused) {
            int x = ((GLWindow) app.getSurface().getNative()).getX();
            int y = ((GLWindow) app.getSurface().getNative()).getY();
            int w = app.width;
            int h = app.height;

            int middleX = w / 2 + x;
            int middleY = h / 2 + y;

            int deltaX = mouse.x - middleX;
            int deltaY = mouse.y - middleY;

            app.noCursor();

            centerMouse();

            if (thirdPersonDist > 0) {
                tilt -= PApplet.map(deltaY, 0, app.height, 0, PI) * sensitivity;
            } else {
                tilt += PApplet.map(deltaY, 0, app.height, 0, PI) * sensitivity;
            }
            pan += PApplet.map(deltaX, 0, app.width, 0, TWO_PI) * sensitivity;
        } else {
            app.cursor();
        }

        tilt = (float) MathUtils.clamp(tilt, -PI / 2.01f, PI / 2.01f);
        if (tilt == PI / 2) {
            tilt += 0.001f;
        }


        //Calculate simplified pan/tilt
        normalizedPan = (float) ((float) pan / MathUtils.TWO_PI);
        if (normalizedPan < 0) {
            normalizedPan += ((int) -normalizedPan) + 1;
        } else {
            normalizedPan -= (int) normalizedPan;
        }
        simplifiedPanTilt.x = Math.round(normalizedPan * 4);
        if (simplifiedPanTilt.x == 4) {
            simplifiedPanTilt.x = 0;
        }
        //Add 1 to simplifiedPanTilt.x but make it still wrap around to 0
        switch (simplifiedPanTilt.x) {
            case 0 -> simplifiedPanTilt.x = 1;
            case 1 -> simplifiedPanTilt.x = 2;
            case 2 -> simplifiedPanTilt.x = 3;
            default -> simplifiedPanTilt.x = 0;
        }
        simplifiedPanTilt.y = Math.round(tilt);


        //Set camera position
        if (getThirdPersonDist() == 0) {
            cameraForward.set(Math.cos(pan), 0, Math.sin(pan));
            right.set(Math.cos(pan - Math.PI / 2), 0, Math.sin(pan - Math.PI / 2));
        } else {
            cameraForward.set(-Math.cos(pan), 0, -Math.sin(pan));
            right.set(-Math.cos(pan - Math.PI / 2), 0, -Math.sin(pan - Math.PI / 2));
        }

        look.set(Math.cos(pan), Math.tan(tilt), Math.sin(pan));
        right.normalize();
        look.normalize();
        cameraForward.normalize();
        target.set(player.worldPos);
        position.set(player.worldPos);

        if (getThirdPersonDist() == 0) {
            target.add(look);
            cursorRaycastLook.set(look);
        } else {
            float thirdPersonDist2 = Math.abs(thirdPersonDist);
            if (getThirdPersonDist() > 0) {
                cameraRaycastLook.set(look);
            } else {
                cameraRaycastLook.set(0).sub(look);
            }
            RayCasting.traceSimpleRay(cameraViewRay, position, cameraRaycastLook, (int) thirdPersonDist2 + 1,
                    ((block, forbiddenBlock) -> {
                        Block block2 = ItemList.getBlock(block);
                        return block != BlockList.BLOCK_AIR.id &&
                                block != forbiddenBlock
                                && (block2.isSolid() || block2.isOpaque());
                    })
                    , VoxelGame.getWorld());
            look.mul(MathUtils.clamp(cameraViewRay.distanceTraveled, 2, thirdPersonDist2) - 1);


            if (getThirdPersonDist() > 0) {
                position.add(look);
                cursorRaycastLook.set(0).sub(look);
            } else {
                position.sub(look);
                cursorRaycastLook.set(0).add(look);
            }
        }
        RayCasting.traceComplexRay(cursorRay, position, cursorRaycastLook, rayMaxDistance,
                ((block, forbiddenBlock) -> {
                    if (cursorRayHitAllBlocks) {
                        return block != forbiddenBlock;
                    } else return block != BlockList.BLOCK_AIR.id &&
                            block != forbiddenBlock;
                }),
                ((entity) -> {
                    if (player.positionLock != null) {
                        return entity != player.positionLock.entity;
                    }
                    return true;
                }),
                VoxelGame.getWorld());


        //Update frustum
//        if (app.keyIsPressed(KeyCode.K)) { //To test frustum
        if (width != lastWidth || height != lastHeight) {
            frustum.setCamInternals(game.cameraFOV, game.getCameraRatio(), game.cameraNearDist, game.cameraFarDist);
            lastWidth = width;
            lastHeight = height;
        }
        frustum.setCamDef(position, target, up);
//        }

        graphics.camera(position.x, position.y, position.z, //p (camera position)
                target.x, target.y, target.z, //l (3D scene origin)
                up.x, up.y, up.z);//u (up)
    }

}
