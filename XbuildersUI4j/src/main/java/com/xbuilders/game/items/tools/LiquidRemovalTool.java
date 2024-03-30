package com.xbuilders.game.items.tools;

import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.tool.Tool;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.BFS.ListQueue;
import com.xbuilders.engine.utils.BFS.TravelNode;

public class LiquidRemovalTool extends Tool {

    public LiquidRemovalTool() {
        super(2, "Liquid Removal Tool");
        setIconAtlasPosition(0, 6);
    }

    private void checkNeighbor(ListQueue<TravelNode> queue, int x, int y, int z, UserControlledPlayer player, TravelNode node) {
        if (VoxelGame.getWorld().getBlock(x, y, z).isLiquid()) {
            queue.add(new TravelNode(x, y, z, node.getTravel() + 1));
        }
    }

    public boolean isContinuous() {
        return true;
    }

    @Override
    public boolean onPlace(int x, int y, int z) {
        ListQueue<TravelNode> queue = new ListQueue<>();
        queue.add(new TravelNode(x, y, z, 0));
        while (queue.containsNodes()) {
            TravelNode node = queue.getAndRemove(0);
            int nx = node.getCoords().x;
            int ny = node.getCoords().y;
            int nz = node.getCoords().z;
            if (node.getTravel() < 7) {
                if (node.getTravel() < 1 || VoxelGame.getWorld().getBlock(nx, ny, nz).isLiquid()) {
                    Block block = VoxelGame.getWorld().getBlock(nx, ny, nz);
                    if (block.isLiquid()) {
                        getPointerHandler().getPlayer().setBlock(BlockList.BLOCK_AIR,null,nx, ny, nz);
//                        if (block.isLuminous()) {
//                            TorchRemovingUtils.removeTorchlight(getPointerHandler().getWorld(), nx, ny, nz);
//                        }
                    }

                    checkNeighbor(queue, nx, ny - 1, nz, getPointerHandler().getPlayer(), node);
                    checkNeighbor(queue, nx, ny + 1, nz, getPointerHandler().getPlayer(), node);
                    checkNeighbor(queue, nx - 1, ny, nz, getPointerHandler().getPlayer(), node);
                    checkNeighbor(queue, nx + 1, ny, nz, getPointerHandler().getPlayer(), node);
                    checkNeighbor(queue, nx, ny, nz - 1, getPointerHandler().getPlayer(), node);
                    checkNeighbor(queue, nx, ny, nz + 1, getPointerHandler().getPlayer(), node);
                }
            }
        }
        return true;
    }

}
