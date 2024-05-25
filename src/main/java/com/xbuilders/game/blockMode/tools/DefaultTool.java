package com.xbuilders.game.blockMode.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.ItemQuantity;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.entity.EntityLink;
import com.xbuilders.engine.player.CursorRaycast;
import com.xbuilders.engine.player.blockPipeline.BlockPipeline;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.game.blockMode.BlockTools;
import com.xbuilders.window.BaseWindow;
import org.joml.Vector3i;
import processing.core.KeyCode;
import processing.event.KeyEvent;

public class DefaultTool extends Tool {

    public DefaultTool(BlockTools tools) {
        super("Default", tools);
    }

    @Override
    public boolean shouldActivate(BaseWindow window, KeyEvent ke) {
        return window.keyIsPressed(KeyCode.ONE);
    }

    @Override
    public boolean setBlock(ItemQuantity item, CursorRaycast ray, BlockData initialData, boolean isCreationMode) {
        if (isCreationMode) {
            Vector3i hitPosNormal = ray.getHitPosPlusNormal();
            Vector3i hitPos = ray.getHitPositionAsInt();
            if (VoxelGame.getWorld().getBlock(hitPos.x, hitPos.y, hitPos.z).isSolid()) {
                placeItem(item, hitPosNormal.x, hitPosNormal.y, hitPosNormal.z, initialData);
            } else { //If the item is not solid, place the block directly inside of it
                placeItem(item, hitPos.x, hitPos.y, hitPos.z, initialData);
            }
        } else {
            Vector3i hitPos = ray.getHitPositionAsInt();
            deleteItem(item, hitPos);
        }
        return true;
    }

    public void deleteItem(ItemQuantity item, Vector3i hitPos) {
        Block hitBlock = VoxelGame.getPlayer().getPointerHandler().getWorld().getBlock(hitPos);
        if (hitBlock == null) return;
        hitBlock.afterRemovalEvent(hitPos.x, hitPos.y, hitPos.z);
        VoxelGame.getPlayer().setBlock(BlockList.BLOCK_AIR, null, hitPos.x, hitPos.y, hitPos.z);
    }

    public boolean placeItem(ItemQuantity item, int x, int y, int z, BlockData initialBlockData) {
        if (y >= Chunk.CHUNK_Y_LENGTH - 1 || y == 0) {
            return false;
        }
        boolean wasSet = false;
        if (item != null && item.getQuantity() > 0) {
            WCCi wcc = new WCCi().set(x, y, z);
            if (wcc.chunkExists()) {
                Chunk chunk = wcc.getChunk();

                Item item2 = item.getItem();
                if (null != item2.itemType) {
                    Item item1 = item.getItem();
                    switch (item1.itemType) {
                        case BLOCK -> {
//                            if (!blockIsIntersectingEntity(new Vector3i(x, y, z))) {
                            Block block = (Block) item.getItem();
                            BlockPipeline.startLocalChange(new Vector3i(x, y, z), block);
                            block.setBlock((int) x, (int) y, (int) z, initialBlockData);
                            if (!item.isInfiniteResource()) {
                                item.setQuantity(item.getQuantity() - 1);
                            }
//                            }
                        }
                        case ENTITY_LINK -> {
                            EntityLink entity = (EntityLink) item.getItem();
                            entity.placeNew(x, y, z, true);
                            wasSet = true;
                            if (!item.isInfiniteResource()) {
                                item.setQuantity(item.getQuantity() - 1);
                            }
                        }
                        case TOOL -> {
                            com.xbuilders.engine.items.tool.Tool tool = (com.xbuilders.engine.items.tool.Tool) item.getItem();
                            wasSet = true;
                            if (tool.onPlace(x, y, z) && !item.isInfiniteResource()) {
                                item.setQuantity(item.getQuantity() - 1);
                            }
                        }
                    }
                }
                chunk.markAsModifiedByUser();
                chunk.markAsNeedsSaving();
            }
        }
        return wasSet;

    }
}
