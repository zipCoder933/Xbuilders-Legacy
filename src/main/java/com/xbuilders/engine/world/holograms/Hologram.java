// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.holograms;

import processing.core.PGraphics;
import org.joml.Vector3f;

public abstract class Hologram
{
    public final Vector3f worldPosition = new Vector3f();;
    protected boolean willBeDestroyed;

    public boolean willBeRemoved() {
        return this.willBeDestroyed;
    }
    
    public void remove() {
        this.willBeDestroyed = true;
    }
    
    public abstract void render(final PGraphics p0);
}
