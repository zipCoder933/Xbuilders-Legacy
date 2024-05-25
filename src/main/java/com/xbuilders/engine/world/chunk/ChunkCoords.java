// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import org.joml.Vector3i;

public class ChunkCoords {

    public int x;
    public int z;

    public ChunkCoords set(final int x, final int z) {
        this.x = x;
        this.z = z;
        return this;
    }

    public ChunkCoords(final String str) {
        final String[] chunks = str.split(",");
        this.x = Integer.parseInt(chunks[0]);
        this.z = Integer.parseInt(chunks[1]);
    }

    public ChunkCoords(final int x, final int z) {
        this.x = x;
        this.z = z;
    }

    public ChunkCoords(final ChunkCoords coords) {
        this.x = coords.x;
        this.z = coords.z;
    }

    public ChunkCoords(final Vector3i chunkCoords) {
        this.x = chunkCoords.x;
        this.z = chunkCoords.z;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj.getClass() == this.getClass()) {
            final ChunkCoords otherObject = (ChunkCoords) obj;
            return otherObject.x == this.x && otherObject.z == this.z;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.z;
        return hash;
    }


    @Override
    public String toString() {
        return "(" + x + "," + z + ")";
    }

    public String toFileString() {
        return x + "," + z;
    }
}
