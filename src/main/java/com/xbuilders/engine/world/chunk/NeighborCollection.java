package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.VoxelGame;

public class NeighborCollection {

    final Chunk[] neighbors = new Chunk[8];
    final Chunk chunk;

    public NeighborCollection(Chunk chunk) {
        this.chunk = chunk;
    }

    private Chunk addChunkToNCList(final int x, final int z) {
        final ChunkCoords coords = new ChunkCoords(x, z);
        return VoxelGame.getWorld().getChunk(coords);
    }

    public void reset() {
        for (int i = 0; i < 8; ++i) {
            neighbors[i] = null;
        }
        cacheAllNeighbors();
    }


    public void cacheAllNeighbors() {
        // The first 4 indicies are facing neighbors
        if (neighbors[0] == null) neighbors[0] = addChunkToNCList(chunk.position.x + 1, chunk.position.z);
        if (neighbors[1] == null) neighbors[1] = addChunkToNCList(chunk.position.x - 1, chunk.position.z);
        if (neighbors[2] == null) neighbors[2] = addChunkToNCList(chunk.position.x, chunk.position.z + 1);
        if (neighbors[3] == null) neighbors[3] = addChunkToNCList(chunk.position.x, chunk.position.z - 1);
        // The last 4 indicies are not facing
        if (neighbors[4] == null) neighbors[4] = addChunkToNCList(chunk.position.x - 1, chunk.position.z - 1);
        if (neighbors[5] == null) neighbors[5] = addChunkToNCList(chunk.position.x + 1, chunk.position.z + 1);
        if (neighbors[6] == null) neighbors[6] = addChunkToNCList(chunk.position.x + 1, chunk.position.z - 1);
        if (neighbors[7] == null) neighbors[7] = addChunkToNCList(chunk.position.x - 1, chunk.position.z + 1);
    }

    public void cacheAllFacingNeighbors() {
        if (neighbors[0] == null)  neighbors[0] = addChunkToNCList(chunk.position.x + 1, chunk.position.z);
        if (neighbors[1] == null)  neighbors[1] = addChunkToNCList(chunk.position.x - 1, chunk.position.z);
        if (neighbors[2] == null)  neighbors[2] = addChunkToNCList(chunk.position.x, chunk.position.z + 1);
        if (neighbors[3] == null)  neighbors[3] = addChunkToNCList(chunk.position.x, chunk.position.z - 1);
    }

    public boolean allNeighborsLoaded() {
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] == null || !neighbors[i].gen_terrainGenerated) {
                return false;
            }
        }
        return true;
    }

    public boolean allFacingNeighborsLoaded() {
        for (int i = 0; i < 4; i++) {
            if (neighbors[i] == null || !neighbors[i].gen_terrainGenerated) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "Neighbors: " +
                (neighbors[0] == null ? "null" : neighbors[0].genStatus()) + "\n, " +
                (neighbors[1] == null ? "null" : neighbors[1].genStatus()) + "\n, " +
                (neighbors[2] == null ? "null" : neighbors[2].genStatus()) + "\n, " +
                (neighbors[3] == null ? "null" : neighbors[3].genStatus()) + "\n, " +
                (neighbors[4] == null ? "null" : neighbors[4].genStatus()) + "\n, " +
                (neighbors[5] == null ? "null" : neighbors[5].genStatus()) + "\n, " +
                (neighbors[6] == null ? "null" : neighbors[6].genStatus()) + "\n, " +
                (neighbors[7] == null ? "null" : neighbors[7].genStatus());
    }
}
