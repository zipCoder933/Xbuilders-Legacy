// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.holograms;

import com.xbuilders.engine.VoxelGame;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HologramSet {

    private List<Hologram> hologramList;

    public HologramSet() {
        this.hologramList = new ArrayList<>();
    }

    public void newGame() {
        this.hologramList.clear();
    }

    public void add(final Hologram hologram) {
        this.hologramList.add(hologram);
    }

    public void get(final int i) {
        this.hologramList.get(i);
    }

    public void size() {
        this.hologramList.size();
    }

    public void renderHolograms(final PGraphics graphics) {
        for (int i = this.hologramList.size() - 1; i >= 0; --i) {
            final Hologram e = this.hologramList.get(i);
            if (e.willBeDestroyed) {
                this.hologramList.remove(i);
                e.willBeDestroyed = false;
            } else {
                try {
                    graphics.pushStyle();
                    graphics.pushMatrix();
                    graphics.translate(e.worldPosition.x, e.worldPosition.y, e.worldPosition.z);
                    VoxelGame.getShaderHandler().setAnimatedTexturesEnabled(false);
                    VoxelGame.getShaderHandler().setWorldSpaceOffset(e.worldPosition);
                    e.render(graphics);
                } finally {
                    graphics.popMatrix();
                    graphics.popStyle();
                }
            }
        }
    }
}
