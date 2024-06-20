package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.core.PGraphics;
import processing.event.KeyEvent;

public class SphereTool extends Tool {
    public SphereTool(BlockTools blockTools) {
        super("Sphere", blockTools);
        sphereBoundaryEvent = new SphereBoundarySetEvent(blockTools, false);
        hollowSphereBoundaryEvent = new SphereBoundarySetEvent(blockTools, true);
    }

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        return window.keyIsPressed(KeyCode.SIX);
    }

    @Override
    public void deactivate() {
        mode = SphereMode.HOLLOW_CENTERED;
        VoxelGame.getPlayer().camera.cursorRay.disableBoundaryMode();
    }

    SphereBoundarySetEvent sphereBoundaryEvent;
    SphereBoundarySetEvent hollowSphereBoundaryEvent;

    @Override
    public void activate() {
        setMode();
    }

    private void setMode() {
        VoxelGame.getPlayer().camera.cursorRay.disableBoundaryMode();
        if (mode == SphereMode.BOUNDED) {
            VoxelGame.getPlayer().camera.cursorRay.enableBoundaryMode(sphereBoundaryEvent.event);
        } else if (mode == SphereMode.HOLLOW_BOUNDED) {
            VoxelGame.getPlayer().camera.cursorRay.enableBoundaryMode(hollowSphereBoundaryEvent.event);
        }
    }

    @Override
    public boolean keyReleased(BaseWindow window, KeyEvent ke) {
        return false;
    }

    enum SphereMode {
        CENTERED, HOLLOW_CENTERED, BOUNDED, HOLLOW_BOUNDED
    }

    SphereMode mode = SphereMode.CENTERED;

    @Override
    public void changeMode() {
        //Advance to the next mode
        SphereMode[] modes = SphereMode.values();
        mode = modes[(mode.ordinal() + 1) % modes.length];
        setMode();
    }

    @Override
    public String toolDescription() {
        String str = mode.toString().replace("_", " ").toLowerCase() + " sphere";
        if (mode == SphereMode.CENTERED || mode == SphereMode.HOLLOW_CENTERED)
            str += " (x" + blockTools.getSize() + ")";
        return str;
    }

    @Override
    public boolean drawCursor(CursorRaycast ray, PGraphics g) {
        if (mode == SphereMode.CENTERED || mode == SphereMode.HOLLOW_CENTERED) {
            BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();
            int size = blockTools.getSize();
            int add = 0;
            g.strokeWeight(1.5f);
            g.noFill();
            g.stroke(255);
            g.translate(
                    ray.cursorRay.hitPostition.x + 0.5f,
                    ray.cursorRay.hitPostition.y + 0.5f,
                    ray.cursorRay.hitPostition.z + 0.5f);

            g.translate(ray.cursorRay.hitNormal.x, ray.cursorRay.hitNormal.y, ray.cursorRay.hitNormal.z);
            g.box((size * 2) - 1f, (size * 2) - 1f, (size * 2) - 1f);

            return true;
        }
        return false;
    }

    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData data, boolean isCreationMode) {
        Vector3i hitPosition = ray.cursorRay.getHitPositionAsInt();
        switch (mode) {
            case CENTERED -> {
                int size2 = blockTools.getSize() * 2;
                sphereBoundaryEvent.event.accept(
                        new AABB().setPosAndSize(hitPosition.x - blockTools.getSize(),
                                hitPosition.y - blockTools.getSize(),
                                hitPosition.z - blockTools.getSize(),
                                size2, size2, size2), isCreationMode);
                return true;
            }
            case HOLLOW_CENTERED -> {
                int size2 = blockTools.getSize() * 2;
                hollowSphereBoundaryEvent.event.accept(
                        new AABB().setPosAndSize(hitPosition.x - blockTools.getSize(),
                                hitPosition.y - blockTools.getSize(),
                                hitPosition.z - blockTools.getSize(),
                                size2, size2, size2), isCreationMode);
                return true;
            }
        }
        return false;
    }


}
