package com.xbuilders.game.items.blocks.vehicle;

import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.player.PositionLock;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.XBFilterOutputStream;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.entities.mobile.Vehicle;
import com.xbuilders.game.items.entities.vehicle.minecart.MinecartUtils;
import com.xbuilders.game.items.other.BlockGrid;
import com.xbuilders.game.items.other.BlockMesh;
import org.joml.Vector3f;
import processing.core.PGraphics;
import processing.core.PShape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomVehicleEntityLink extends EntityLink {

    public CustomVehicleEntityLink(int id) {
        super(id, "Custom Vehicle hidden");
        setSupplier(() -> new CustomVehicle());
    }

    public class CustomVehicle extends Vehicle {

        public BlockGrid blocks;
        byte speed, direction;
        boolean isWatercraft, allTerrain;
        boolean canFly = false;
        // List<Vector3f> helecopterBlades = new ArrayList<>();
        public PShape mesh;

        public CustomVehicle() {
            super(new Vector3f(1, 0.8f, 1), true);
            setFrustumSphereRadius(3f);
            positionLock = new PositionLock(this);

            // posHandler.stepHeight = 1.1f;
        }

        @Override
        public boolean move() {
            if (playerIsRidingThis()) {
                float rotateSpeed = 0;
                float targetSpeed = 0;

                rotateSpeed = 0.5f;
                if (getPlayer().forwardKeyPressed()) {
                    targetSpeed = speed * 0.006f;
                    rotateSpeed = 2.0f;
                } else if ((posHandler.onGround || isWatercraft) && getPlayer().backKeyPressed()) {
                    targetSpeed = speed * -0.002f;
                    rotateSpeed = 1.0f;
                }

                if (isWatercraft) {
                    if (!posHandler.blockAtPosition.isLiquid()) {
                        targetSpeed *= 0.1f;
                    }
                } else if (canFly) {
                    if (posHandler.onGround) {
                        targetSpeed *= 0.2f;
                    }
                } else if (!allTerrain && !isOnRoad()) {
                    targetSpeed /= 10;
                    rotateSpeed /= 2;
                }
                speedCurve = (float) MathUtils.curve(speedCurve, targetSpeed, 0.03f);

                if (getPlayer().leftKeyPressed()) {
                    float rotationY1 = rotationYDeg + rotateSpeed;
                    this.rotationYDeg = rotationY1;
                } else if (getPlayer().rightKeyPressed()) {
                    float rotationY1 = rotationYDeg - rotateSpeed;
                    this.rotationYDeg = rotationY1;
                }
                if (isWatercraft) {
                    if (getPlayer().downKeyPressed()) {
                        this.posHandler.velocity.y -= (speedCurve + 0.1f) * 0.12f;
                    }
                    if (posHandler.blockAtPosition.isLiquid()) {
                        if (getPlayer().upKeyPressed()) {
                            this.posHandler.velocity.y += (speedCurve + 0.1f) * 0.12f;
                            this.posHandler.onGround = false;
                        }
                        this.posHandler.setGravityEnabled(false);
                    } else {
                        this.posHandler.setGravityEnabled(true);
                    }
                } else if (canFly) {
                    if (getPlayer().downKeyPressed() && !this.posHandler.isGravityEnabled()) {
                        this.posHandler.velocity.y -= .7 * 0.18f;
                        if (this.posHandler.onGround) {
                            this.posHandler.setGravityEnabled(true);
                        }
                    } else if (getPlayer().upKeyPressed()) {
                        this.posHandler.setGravityEnabled(false);
                        this.posHandler.velocity.y += speedCurve * 0.15f;
                        this.posHandler.onGround = false;
                    } else {
                        if (getPlayer().forwardKeyPressed()) {
                            this.posHandler.velocity.y -= 0.01f;
                        } else {
                            this.posHandler.velocity.y -= 0.04f;
                        }
                    }
                }
                rotationYDeg = normalizeRotation(rotationYDeg);
                smoothYRotation = rotationYDeg;

                switch (direction) {
                    case 0 -> {
                        goForward(-speedCurve, rotationYDeg + 180f);
                    }
                    case 2 -> {
                        goForward(-speedCurve, rotationYDeg);
                    }
                    default -> {
                        goForward(-speedCurve, rotationYDeg + (direction * 90f));
                    }
                }
                return true;
            } else {
                posHandler.setGravityEnabled(!posHandler.blockAtPosition.isLiquid());
            }

            return get3DDistToPlayer() < getPointerHandler().getSettingsFile().playerRayMaxDistance;
        }

        @Override
        public void onDestructionInitiated() {
        }

        @Override
        public void onDestructionCancel() {
        }

        float speedCurve;
        float smoothYRotation;

        final PositionLock positionLock;

        @Override
        public void renderMob(PGraphics g) {

            g.shape(mesh);
        }

        @Override
        public void draw(PGraphics g) {
            float yRotationRadians = (float) (smoothYRotation * (Math.PI / 180));
            if (mesh != null) {
                if (playerIsRidingThis()) {
                    positionLock.playerDisplacement.identity()
                            .rotateY(yRotationRadians);
                }

                g.fill(0);
                smoothYRotation = (float) MathUtils.curve(smoothYRotation, rotationYDeg, 0.25f);
                modelMatrix.rotateY(yRotationRadians);

                if (playerIsRidingThis()) {
                    positionLock.playerDisplacement.translate(seatPosition).translate(0, -1.0f, 0);
                }

                modelMatrix.translate(renderOffset.x, renderOffset.y, renderOffset.z);

                sendModelMatrixToShader();
                renderMob(g);
            }

        }

        @Override
        public void onDestroyClickEvent() {
            destroy(true);
        }

        @Override
        public boolean onClickEvent() {
            UserControlledPlayer userControlledPlayer = getPointerHandler().getPlayer();
            if (userControlledPlayer.positionLock == null) {
                getPointerHandler().getPlayer().setPositionLock(positionLock);
                MinecartUtils.resetKeyEvent();
                // onTrack = alignToNearestTrack();
                // if (onTrack) {
                // getPointerHandler().getGame().alert("Press the forward and backward keys to
                // toggle minecart direction.");
                // } else {
                getPointerHandler().getGame().alert("Use WASD or arrow keys to navigate");
                // }
            }
            return false;
        }

        @Override
        public void toBytes(XBFilterOutputStream fout) throws IOException {
            fout.write((byte) speed);// 0
            fout.write((byte) direction);// 1
            fout.write((byte) (canFly ? 1 : 0));// 2
            fout.write((byte) (isWatercraft ? 1 : 0));// 3
            fout.write((byte) (normalizeRotation(rotationYDeg) / 2));// 4
            fout.write((byte) (allTerrain ? 1 : 0));// 5
            //MAKE SURE TO SET THE STARTING VALUE!!!
            for (int i = 6; i < METADATA; i++) {
                fout.write((byte) 0);
            }
            blocks.toBytes(fout);
        }

        public final int METADATA = 20;
        final Vector3f seatPosition = new Vector3f();

        public void reloadMesh() {
            if (blocks != null) {
                int width = Math.min(blocks.size.x, blocks.size.z);

                aabb.setOffsetAndSize(
                        (float) -width / 2,
                        (float) -blocks.size.y / 2,
                        (float) -width / 2,
                        width, blocks.size.y, width);
                setFrustumSphereRadius(Math.max(Math.max(blocks.size.x, blocks.size.y), blocks.size.z) / 2f);

                renderOffset.x = (float) -blocks.size.x / 2;
                renderOffset.y = (float) -blocks.size.y / 2;
                renderOffset.z = (float) -blocks.size.z / 2;
                mesh = BlockMesh.createMesh(blocks, false, true);

                // Find the driver seat
                for (int i = 0; i < blocks.size.x; i++) {
                    for (int j = 0; j < blocks.size.y; j++) {
                        for (int k = 0; k < blocks.size.z; k++) {
                            if (blocks.get(i, j, k) == GameItems.BLOCK_DRIVERS_SEAT.id) {
                                seatPosition.set(i + renderOffset.x + 0.5f, j + renderOffset.y + 0.5f,
                                        k + renderOffset.z + 0.5f);
                                break;
                            }
                        }
                    }
                }

            }
        }

        @Override
        public void initialize(byte[] bytes, boolean setByUser) {
            // //speed,direction,canFly,verticalTakeoff
            if (bytes != null) {
                // System.out.println("Loading custom vehicle entity! " +
                // Arrays.toString(bytes));
                speed = bytes[0];
                direction = bytes[1];
                canFly = bytes[2] == 1;
                isWatercraft = bytes[3] == 1;
                this.rotationYDeg = (float) (bytes[4] * 2);
                allTerrain = bytes[5] == 1;
                smoothYRotation = this.rotationYDeg;

                blocks = new BlockGrid(0, 0, 0);
                blocks.fromBytes(bytes, METADATA, bytes.length);
                // System.out.println("Loaded blocks: " + blocks);
            }
            jumpWithSideCollision = allTerrain && !canFly;

            reloadMesh();
        }
    }
}