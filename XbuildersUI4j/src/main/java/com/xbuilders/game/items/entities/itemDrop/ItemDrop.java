///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.xbuilders.game.items.entities.itemDrop;
//
//import com.xbuilders.engine.VoxelGame;
//import com.xbuilders.engine.items.Item;
//import com.xbuilders.engine.player.UserControlledPlayer;
//import com.xbuilders.engine.items.ItemQuantity;
//import com.xbuilders.engine.items.ItemType;
//import com.xbuilders.engine.items.block.Block;
//import com.xbuilders.engine.utils.InventoryUtils;
//import com.xbuilders.engine.utils.math.MathUtils;
//import com.xbuilders.engine.world.holograms.Hologram;
//import com.xbuilders.game.items.GameItemList;
//import org.joml.Vector3f;
//import org.joml.Vector3i;
//import processing.core.PGraphics;
//
///**
// * @author zipCoder933
// */
//public class ItemDrop extends Hologram {
//
//    private boolean hasRoomForThis(UserControlledPlayer player, ItemQuantity iq) {
//        return InventoryUtils.isRoomForItem(player.blockPanel.itemList, iq);
//    }
//
//    private void addThis(UserControlledPlayer player, ItemQuantity iq) {
//        InventoryUtils.appendBlocksToInventory(player.blockPanel.itemList, iq);
//    }
//
//    /**
//     * @param player the player
//     * @param startingPos the spawn position of the drop
//     * @param iq the item
//     * @param playerDropping if the player dropped it
//     */
//    public ItemDrop(Vector3i startingPos, ItemQuantity iq, boolean playerDropping) {
//        if (iq != null && GameItemList.isVisibleToGameList(iq.getItem())) {
//            Item item = iq.getItem();
//            if (item.type == ItemType.BLOCK && ((Block) iq.getItem()).isAir()) {
//                return;
//            } else if (!playerDropping && hasRoomForThis(VoxelGame.getPlayer(), iq)
//                    && getDistanceToPlayer(VoxelGame.getPlayer(), new Vector3f(startingPos)) < 2.0) {
//                addThis(VoxelGame.getPlayer(), iq);
//                return;
//            }
//            this.iq = iq;
//            dropped = playerDropping;
//            secondsWhenCreated = System.currentTimeMillis();
//        }
//    }
//
//    private double getDistanceToPlayer(UserControlledPlayer player, Vector3f startingPos) {
//        float playerX = player.worldPos.x - 0.5f;
//        float playerY = player.worldPos.y + 0.5f;
//        float playerZ = player.worldPos.z - 0.5f;
//        return MathUtils.dist(
//                playerX, playerY, playerZ,
//                startingPos.x, startingPos.y, startingPos.z);
//    }
//
//    long secondsWhenCreated = 0;
//    private ItemQuantity iq;
//    boolean dropped = false;
//    float momemtum = 0;
//    boolean lastVal = false;
//
//    private void moveWithBlocks() {
//        if (VoxelGame.ph().getApplet().frameCount % 2 == 0) {
//            int wx = (int) worldPosition.x;
//            int wy = (int) worldPosition.y;
//            int wz = (int) worldPosition.z;
//            Block b = VoxelGame.getWorld().getBlock(wx, wy, wz);
//            Block b1 = VoxelGame.getWorld().getBlock(wx, wy + 1, wz);
//
//            if (!b1.isSolid()) {
//                if (lastVal != true) {
//                    momemtum = 0;
//                } else {
//                    worldPosition.y += momemtum;
//                    if (momemtum < 0.4) {
//                        momemtum += 0.01;
//                    }
//                }
//                lastVal = true;
//            } else if (b1.isSolid() || b.isSolid()) {
//                if (lastVal != false) {
//                    momemtum = 0;
//                } else {
//                    worldPosition.y -= momemtum;
//                    if (momemtum < 0.4) {
//                        momemtum += 0.01;
//                    }
//                }
//                lastVal = false;
//            }
//        }
//    }
//
//    @Override
//    public void render(PGraphics graphics) {
//        float timeSinceCreated = System.currentTimeMillis() - secondsWhenCreated;
//        UserControlledPlayer userControlledPlayer = VoxelGame.ph().getPlayer();
//        float playerX = userControlledPlayer.worldPos.x - 0.5f;
//        float playerY = userControlledPlayer.worldPos.y + 0.5f;
//        float playerZ = userControlledPlayer.worldPos.z - 0.5f;
//        double distToPlayer2 = MathUtils.dist(playerX, playerY, playerZ,
//                worldPosition.x, worldPosition.y, worldPosition.z);
//
//        if (timeSinceCreated > 20000) {
//            remove();
//        } else if ((!dropped || timeSinceCreated > 2000)
//                && hasRoomForThis(VoxelGame.ph().getPlayer(), iq)) {
//            if (distToPlayer2 < 0.5f) {
//                addThis(VoxelGame.ph().getPlayer(), iq);
//                remove();
//            } else if (distToPlayer2 < 2.2f) {
//                worldPosition.x = (float) MathUtils.curve(worldPosition.x, playerX, 0.3);
//                worldPosition.y = (float) MathUtils.curve(worldPosition.y, playerY, 0.3);
//                worldPosition.z = (float) MathUtils.curve(worldPosition.z, playerZ, 0.3);
//            } else {
//                moveWithBlocks();
//            }
//        } else {
//            moveWithBlocks();
//        }
//
//        float yRot = (float) (VoxelGame.ph().getApplet().frameCount * 0.02f);
//        graphics.pushMatrix();
//        graphics.translate(
//                worldPosition.x + 0.5f,
//                worldPosition.y + 0.5f + (float) (Math.sin(VoxelGame.ph().getApplet().frameCount * 0.1f) * 0.1),
//                worldPosition.z + 0.5f);
//        graphics.rotateY(yRot);
//        graphics.rotateX(yRot / 2);
//        graphics.stroke(0, 100, 255);
//        graphics.strokeWeight(4);
//        graphics.box(0.3f);
//        graphics.popMatrix();
//
//    }
//}
