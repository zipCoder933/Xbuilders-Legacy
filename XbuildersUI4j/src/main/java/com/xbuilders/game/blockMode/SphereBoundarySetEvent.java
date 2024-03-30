/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.blockMode;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.ItemType;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.engine.world.chunk.blockData.BlockOrientation;
import static com.xbuilders.game.blockMode.SettingUtils.deleteEntitiesInsideBoundary;
import com.xbuilders.game.items.other.boundaryBlocks.BoundarySetEvent;
import java.util.HashSet;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class SphereBoundarySetEvent extends BoundarySetEvent {

    BlockMode parent;

    public SphereBoundarySetEvent(BlockMode parent) {
        this.parent = parent;
    }

    @Override
    public void onBoundarySet(AABB boundary, Vector3i start, Vector3i end) {
        ItemQuantity item = VoxelGame.getPlayer().blockPanel.getCurItem();
        if (item != null) {
            Item item1 = item.getItem();
            if (item1.type == ItemType.BLOCK) {

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
                Block block = (Block) item.getItem();
                BlockOrientation orientation = VoxelGame.getPlayer().cameraBlockOrientation();

                for (int x = (int) boundary.minPoint.x; x < boundary.maxPoint.x; x++) {
                    for (int y = (int) boundary.minPoint.y; y < boundary.maxPoint.y; y++) {
                        for (int z = (int) boundary.minPoint.z; z < boundary.maxPoint.z; z++) {
                            if (checkIfToSet(x, y, z, radius, center)) {
                                WCCi wcc = new WCCi().set(x, y, z);
                                subChunks.add(wcc.getSubChunk());
                                parent.blockSetter.addToBlockQueue(block, new Vector3i(x, y, z), orientation);
                            }
                        }
                    }
                }
                deleteEntitiesInsideBoundary(subChunks, boundary);
            }
        }
        parent.blockSetter.wakeUp();
    }

    public boolean checkIfToSet(int x, int y, int z, double radius, Vector3i center) {
        return MathUtils.dist(center.x, center.y, center.z, x, y, z) < radius;
    }
}
