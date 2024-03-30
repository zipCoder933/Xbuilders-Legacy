// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.light;

import java.util.Objects;
import org.joml.Vector3i;
import com.xbuilders.engine.world.chunk.SubChunk;

public class SubChunkNode {

    public SubChunk chunk;
    public Vector3i coords;

    public SubChunkNode(final SubChunk chunk, final Vector3i coords) {
        this.coords = coords;
        this.chunk = chunk;
    }

    public SubChunkNode(final SubChunk chunk, final int x, final int y, final int z) {
        this.coords = new Vector3i(x, y, z);
        this.chunk = chunk;
    }

    public void set(SubChunk chunk, int x, int y, int z) {
        this.coords.set(x, y, z);
        this.chunk = chunk;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.chunk);
        hash = 29 * hash + Objects.hashCode(this.coords);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final SubChunkNode other = (SubChunkNode) obj;
        return Objects.equals(this.chunk, other.chunk) && Objects.equals(this.coords, other.coords);
    }

}
