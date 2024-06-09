/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.wcc.WCCi;
import com.xbuilders.engine.world.blockData.BlockOrientation;

import static com.xbuilders.game.blockMode.SettingUtils.deleteEntitiesInsideBoundary;

import com.xbuilders.game.blockMode.BlockTools;

import java.util.HashSet;
import java.util.function.BiConsumer;

import org.joml.Vector3i;

/**
 * @author zipCoder933
 */
public class SphereBoundarySetEvent {

    BlockTools parent;
    public BiConsumer<AABB, Boolean> event;
    final boolean hollow;

    public SphereBoundarySetEvent(BlockTools parent, boolean hollow) {
        this.parent = parent;
        this.hollow = hollow;

        event = new BiConsumer<AABB, Boolean>() {
            @Override
            public void accept(AABB boundary, Boolean create) {
                Item item = BlockList.BLOCK_AIR;
                if (create) {
                    item = VoxelGame.getPlayer().blockPanel.getCurItem().getItem();
                    if (item == null || item.itemType != ItemType.BLOCK) return;
                }
                System.out.println("Creating sphere... item: " + item);

                Vector3i center = new Vector3i(
                        Math.round((boundary.minPoint.x + boundary.maxPoint.x) / 2),
                        Math.round((boundary.minPoint.y + boundary.maxPoint.y) / 2),
                        Math.round((boundary.minPoint.z + boundary.maxPoint.z) / 2)
                );

                double[] dist = new double[]{
                        MathUtils.dist(center.x, boundary.minPoint.x),
                        MathUtils.dist(center.y, boundary.minPoint.y),
                        MathUtils.dist(center.z, boundary.minPoint.z)
                };
                double radius = Math.min(dist[0], Math.min(dist[1], dist[2]));

                HashSet<SubChunk> subChunks = new HashSet<>();
                Block block = (Block) item;
                BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();

                for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                    for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                        for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                            if (checkIfToSet(x, y, z, radius, center, create)) {
                                WCCi wcc = new WCCi().set(x, y, z);
                                subChunks.add(wcc.getSubChunk());
                                parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
                            }
                        }
                    }
                }
                deleteEntitiesInsideBoundary(subChunks, boundary);
                parent.blockSetter.wakeUp();
            }
        };
    }

    public boolean checkIfToSet(int x, int y, int z, double radius, Vector3i center, boolean create) {
        if (hollow && create) {
            double dist = MathUtils.dist(center.x, center.y, center.z, x, y, z);
            return dist < radius && dist > radius - 2 && !VoxelGame.getWorld().getBlock(x, y, z).isSolid();
        } else return MathUtils.dist(center.x, center.y, center.z, x, y, z) < radius;
    }
}
