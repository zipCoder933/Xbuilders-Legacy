package com.xbuilders.engine.player;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameMode;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.player.camera.Camera;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.player.raycasting.RayCasting;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.game.blockMode.BlockTools;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import com.xbuilders.window.ui4j.UIExtensionFrame;

import java.util.List;
import java.util.function.BiConsumer;

public class CursorRaycast {
    public int maxCursorRayDist = 1000;//Max distance for front ray
    public int cursorRayDist;
    public final Ray cursorRay;
    public boolean cursorRayHitAllBlocks = false;
    Camera camera;

    public final static KeyCode RAYCASTING_HIT_ALL_BLOCKS = KeyCode.TAB;
    public final static KeyCode BOUNDARY_PARAM1 = KeyCode.K;
    public final static KeyCode BOUNDARY_PARAM2 = KeyCode.L;


    public CursorRaycast(Camera camera) {
        cursorRay = new Ray();
        this.camera = camera;
    }

    public boolean hitTarget() {
        return cursorRay.hitTarget || cursorRayHitAllBlocks;
    }

    public Vector3i getHitPositionAsInt() {
        return cursorRay.getHitPositionAsInt();
    }

    public Vector3i getHitPosPlusNormal() {
        return cursorRay.getHitPosPlusNormal();
    }

    public Vector3i getHitPos() {
        return cursorRay.getHitPositionAsInt();
    }

    public Vector3i getHitNormalAsInt() {
        return cursorRay.getHitNormalAsInt();
    }

    private void setBoundaryStartNode(Vector3i node) {
        if (boundary_useHitPos || (boundary_lockToPlane && boundary_isStartNodeSet)) node.set(getHitPos());
        else node.set(getHitPosPlusNormal());
    }

    private void setBoundaryEndNode(Vector3i node) {

        setBoundaryStartNode(node);

    }


    public void cast(Vector3f position, Vector3f cursorRaycastLook, UserControlledPlayer player) {
        if (cursorRayHitAllBlocks) cursorRayDist = MathUtils.clamp(cursorRayDist, 1, maxCursorRayDist);
        else cursorRayDist = maxCursorRayDist;
        Vector2i simplifiedPanTilt = VoxelGame.getPlayer().camera.simplifiedPanTilt;

        RayCasting.traceComplexRay(cursorRay, position, cursorRaycastLook, cursorRayDist,
                ((block, forbiddenBlock, rx, ry, rz) -> {
                    //block ray if we are locking boundary to plane
                    if (useBoundary && boundary_lockToPlane && boundary_isStartNodeSet) {
                        if (simplifiedPanTilt.y != 0) {
                            if (ry == boundary_startNode.y) {
                                return true;
                            }
                        } else if (simplifiedPanTilt.x == 1 || simplifiedPanTilt.x == 3) {
                            if (rx == boundary_startNode.x) {
                                return true;
                            }
                        } else {
                            if (rz == boundary_startNode.z) {
                                return true;
                            }
                        }
                    }
                    if (cursorRayHitAllBlocks) {
                        return block != forbiddenBlock;
                    } else return block != BlockList.BLOCK_AIR.id &&
                            block != forbiddenBlock;
                }),
                ((entity) -> {
                    if (entity != null && entity.playerIsRidingThis()) return false;
                    return true;
                }),
                VoxelGame.getWorld());

        //Keep the boundary flat
        if (boundary_lockToPlane && boundary_isStartNodeSet) {
            if (simplifiedPanTilt.y != 0) {
                cursorRay.hitPostition.y = boundary_startNode.y;
            } else if (simplifiedPanTilt.x == 1 || simplifiedPanTilt.x == 3) {
                cursorRay.hitPostition.x = boundary_startNode.x;
            } else {
                cursorRay.hitPostition.z = boundary_startNode.z;
            }
        }
    }

    public Entity getEntity() {
        return cursorRay.entity;
    }

    private boolean boundaryIsTooBig() {
        return (boundary_aabb.getXLength() * boundary_aabb.getYLength() * boundary_aabb.getZLength() >
                VoxelGame.getSettings().getSettingsFile().maxBlockBoundaryArea);
    }

    protected void drawCursor(BlockTools blockModes, PGraphics pg) {
        if (!camera.cursorRay.hitTarget()) return;

        if (useBoundary) {
            if (!boundary_isStartNodeSet) {
                setBoundaryStartNode(boundary_startNode);
                boundary_aabb.setPosAndSize(boundary_startNode.x, boundary_startNode.y, boundary_startNode.z, 1, 1, 1);
            } else {
                setBoundaryEndNode(boundary_endNode);
                makeAABBFrom2Points(boundary_startNode, boundary_endNode, boundary_aabb);
            }
            if (boundaryIsTooBig()) drawBox(pg, boundary_aabb, 255, 0, 0, 255);
            else drawBox(pg, boundary_aabb, 255, 255, 255, 255);
        } else if (blockModes.drawCursor(this, pg)) {
        } else {
            drawCursorBlock(pg);
        }
    }

    public void drawCursorBlock(PGraphics graphics) {


        graphics.pushStyle();
        graphics.stroke(255);
        graphics.noFill();
        if (cursorRay.cursorBoxes == null) {
            graphics.strokeWeight(2);
            graphics.translate(0.5f + cursorRay.hitPostition.x, 0.5f + cursorRay.hitPostition.y, 0.5f + cursorRay.hitPostition.z);
            graphics.box(1.02f);
        } else {
            graphics.strokeWeight(3);
            List<AABB> cursorAABBs1 = cursorRay.cursorBoxes;
            for (int i = 0; i < cursorAABBs1.size(); i++) {
                AABB box = cursorAABBs1.get(i);
                graphics.pushMatrix();
                graphics.translate(
                        box.minPoint.x + (box.getXLength() / 2),
                        box.minPoint.y + (box.getYLength() / 2),
                        box.minPoint.z + (box.getZLength() / 2));
                graphics.box(
                        box.getXLength(),
                        box.getYLength(),
                        box.getZLength());
                graphics.popMatrix();
            }
            graphics.translate(0.5f + cursorRay.hitPostition.x, 0.5f + cursorRay.hitPostition.y, 0.5f + cursorRay.hitPostition.z);
        }
        graphics.popStyle();
    }

    private void drawBox(PGraphics pg, AABB box, int r, int g, int b, int a) {
        pg.pushStyle();
        pg.stroke(r, g, b, a);
        pg.noFill();
        pg.strokeWeight(3);
        pg.pushMatrix();
        pg.translate(
                box.minPoint.x + (box.getXLength() / 2),
                box.minPoint.y + (box.getYLength() / 2),
                box.minPoint.z + (box.getZLength() / 2));
        pg.box(
                box.getXLength(),
                box.getYLength(),
                box.getZLength());
        pg.popMatrix();
        pg.popStyle();
    }

    boolean rayDistChanged = false;


    //Boundary mode:
    private boolean useBoundary = false;
    private boolean boundary_isStartNodeSet = false;
    public boolean boundary_useHitPos = false;
    public boolean boundary_lockToPlane = false;
    private BiConsumer<AABB, Boolean> boundaryConsumer;
    private final Vector3i boundary_startNode = new Vector3i();
    private final Vector3i boundary_endNode = new Vector3i();
    private final AABB boundary_aabb = new AABB();

    public void enableBoundaryMode(BiConsumer<AABB, Boolean> createBoundaryConsumer) {
        useBoundary = true;
        boundary_isStartNodeSet = false;
        boundary_lockToPlane = false;
        this.boundaryConsumer = createBoundaryConsumer;
    }

    public void disableBoundaryMode() {
        useBoundary = false;
        boundaryConsumer = null;
    }

    private void makeAABBFrom2Points(Vector3i start, Vector3i end, AABB aabb) {
        int minX = Math.min(start.x, end.x);
        int minY = Math.min(start.y, end.y);
        int minZ = Math.min(start.z, end.z);
        int maxX = Math.max(start.x, end.x);
        int maxY = Math.max(start.y, end.y);
        int maxZ = Math.max(start.z, end.z);
        aabb.setPosAndSize(
                minX,
                minY,
                minZ,
                (int) MathUtils.dist(minX, maxX + 1),
                (int) MathUtils.dist(minY, maxY + 1),
                (int) MathUtils.dist(minZ, maxZ + 1));
    }

    public void keyPressed(KeyEvent ke, UIExtensionFrame f) {
        if (VoxelGame.getGameScene().mode != GameMode.FREEPLAY) return;
        if (ke.getKeyCode() == RAYCASTING_HIT_ALL_BLOCKS) {
            cursorRayHitAllBlocks = true;
            maxCursorRayDist = 4;
        } else if (ke.getKeyCode() == BOUNDARY_PARAM1) {
            boundary_useHitPos = true;
        }
    }

    public void keyReleased(KeyEvent ke, UIExtensionFrame f) {
        if (VoxelGame.getGameScene().mode != GameMode.FREEPLAY) return;
        if (ke.getKeyCode() == RAYCASTING_HIT_ALL_BLOCKS) {
            cursorRayHitAllBlocks = false;
            maxCursorRayDist = VoxelGame.ph().getSettingsFile().playerRayMaxDistance;
        } else if (ke.getKeyCode() == KeyCode.B) {
            enableBoundaryMode((AABB, Boolean) -> {
                System.out.println("boundary created " + AABB + " boolean " + Boolean);
            });
        } else if (ke.getKeyCode() == BOUNDARY_PARAM1) {
            boundary_useHitPos = false;
        }
    }

    protected boolean createClickEvent() {
        if (useBoundary) {
            boundaryClickEvent(true);
            return false;
        }
        return true; //If we want to permit the click event to continue
    }

    protected boolean destroyClickEvent() {
        if (useBoundary) {
            boundaryClickEvent(false);
            return false;
        }
        return true; //If we want to permit the click event to continue
    }


    private void boundaryClickEvent(boolean create) {
        if (!boundary_isStartNodeSet) {
            setBoundaryStartNode(boundary_startNode);
            boundary_isStartNodeSet = true;
        } else {
            makeAABBFrom2Points(boundary_startNode, boundary_endNode, boundary_aabb);
            if (boundaryIsTooBig())
                return;
            if (boundaryConsumer != null) boundaryConsumer.accept(boundary_aabb, create);
            boundary_isStartNodeSet = false;
        }
    }


    public boolean mouseEvent(MouseEvent event, UIExtensionFrame f) {
        if (VoxelGame.getGameScene().mode == GameMode.FREEPLAY && f.keyIsPressed(RAYCASTING_HIT_ALL_BLOCKS)) {
            maxCursorRayDist =
                    MathUtils.clamp(camera.cursorRay.maxCursorRayDist - event.getCount(),
                            4, VoxelGame.ph().getSettingsFile().playerRayMaxDistance);
            rayDistChanged = true;
            return true;
        }
        return false;
    }
}
