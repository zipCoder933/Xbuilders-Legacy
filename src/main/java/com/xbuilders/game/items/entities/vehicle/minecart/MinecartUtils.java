/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.vehicle.minecart;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import com.xbuilders.game.PointerHandler;
import com.xbuilders.game.items.GameItems;
import com.xbuilders.game.items.entities.vehicle.minecart.MinecartEntityLink.Minecart;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public class MinecartUtils {

    private static boolean keyEvent = false;
    public static boolean switchJunctionKeyEvent = false;

    public static void resetKeyEvent() {
        keyEvent = true;
        switchJunctionKeyEvent = true;
    }

    public static int assignForwardOrBackward(Minecart e, int direction) {
        if (e.getPointerHandler().getPlayer().forwardKeyPressed()) {
            if (keyEvent) {
                if (direction == 0) {
                    direction = 1;
                } else {
                    direction = 0;
                }
                keyEvent = false;
            }
        } else if (e.getPointerHandler().getPlayer().backKeyPressed()) {
            if (keyEvent) {
                if (direction == 0) {
                    direction = -1;
                } else {
                    direction = 0;
                }
                keyEvent = false;
            }
        } else {
            keyEvent = true;
        }
        return direction;
    }

//    public static void drawTrackPieces(Minecart e, PGraphics g) {
//        TrackPieceSet pieces = getTrackPieces(e);
//
//        g.popMatrix();
//        g.pushMatrix();
//        g.strokeWeight(10);
//        g.noFill();
//
//        if (pieces.straightTrackPiece != null) {
//            g.pushMatrix();
//            g.stroke(255, 0, 0);
//            g.translate(
//                    pieces.straightTrackPiece.x + 0.5f,
//                    pieces.straightTrackPiece.y + 0.5f,
//                    pieces.straightTrackPiece.z + 0.5f);
//            g.box(1);
//            g.popMatrix();
//        }
//        if (pieces.curvedTrackPiece != null) {
//            g.pushMatrix();
//            g.stroke(0, 255, 0);
//            g.translate(
//                    pieces.curvedTrackPiece.x + 0.5f,
//                    pieces.curvedTrackPiece.y + 0.5f,
//                    pieces.curvedTrackPiece.z + 0.5f);
//            g.box(1);
//            g.popMatrix();
//        }
//        if (pieces.raisedTrackPiece != null) {
//            g.pushMatrix();
//            g.stroke(0, 0, 255);
//            g.translate(
//                    pieces.raisedTrackPiece.x + 0.5f,
//                    pieces.raisedTrackPiece.y + 0.5f,
//                    pieces.raisedTrackPiece.z + 0.5f);
//            g.box(1);
//            g.popMatrix();
//        }
//        if (pieces.raisedTrackPieceA != null) {
//            g.pushMatrix();
//            g.stroke(0, 0, 255);
//            g.translate(
//                    pieces.raisedTrackPieceA.x + 0.5f,
//                    pieces.raisedTrackPieceA.y + 0.5f,
//                    pieces.raisedTrackPieceA.z + 0.5f);
//            g.box(1);
//            g.popMatrix();
//        }
//        g.popMatrix();
//    }


    private static boolean check(Minecart e, TrackPieceSet pieces, int x, int y, int z) {
        Block block = e.getPointerHandler().getWorld().getBlock(x, y, z);
        if (pieces.straightTrackPiece == null && block == GameItems.TRACK) {
            pieces.straightTrackPiece = new Vector3i(x, y, z);

        } else if (pieces.curvedTrackPiece == null && block == GameItems.CURVED_TRACK) {
            pieces.curvedTrackPiece = new Vector3i(x, y, z);

        } else if (pieces.raisedTrackPiece == null && block == GameItems.RAISED_TRACK) {
            pieces.raisedTrackPiece = new Vector3i(x, y, z);

        } else if (pieces.raisedTrackPieceA == null && block == GameItems.RAISED_TRACK) {
            pieces.raisedTrackPieceA = new Vector3i(x, y, z);

        }
        return pieces.raisedTrackPiece != null
                && pieces.curvedTrackPiece != null
                && pieces.straightTrackPiece != null;
    }

    public static TrackPieceSet getTrackPieces(Minecart e) {
        TrackPieceSet pieces = new TrackPieceSet();
        int x = Math.round(e.worldPosition.x);
        int y = Math.round(e.worldPosition.y);
        int z = Math.round(e.worldPosition.z);
        if (check(e, pieces, x, y, z)) {
            return pieces;
        } else if (check(e, pieces, x, y + 1, z)) {
            return pieces;
        } else if (check(e, pieces, x + 1, y, z)) {
            return pieces;
        } else if (check(e, pieces, x - 1, y, z)) {
            return pieces;
        } else if (check(e, pieces, x, y, z + 1)) {
            return pieces;
        } else if (check(e, pieces, x, y, z - 1)) {
            return pieces;
        } else if (check(e, pieces, x + 1, y + 1, z)) {
            return pieces;
        } else if (check(e, pieces, x - 1, y + 1, z)) {
            return pieces;
        } else if (check(e, pieces, x, y + 1, z + 1)) {
            return pieces;
        } else if (check(e, pieces, x, y + 1, z - 1)) {
            return pieces;
        }
        return pieces;
    }

    public static boolean isTrack(PointerHandler ph, int x, int y, int z) {
        Block block = ph.getWorld().getBlock(x, y, z);
        return isTrack(block);

    }

    public static boolean mergeTrackLeftCurvedPath(Vector3i previousTrackPos, Vector3i curvedTrackPos) {
        boolean left = MinecartUtils.leftCurvedPath(previousTrackPos, curvedTrackPos);
        BlockOrientation curvedTrackOrientaiton
                = BlockOrientation.getBlockOrientation(VoxelGame.getWorld().getBlockData(curvedTrackPos.x, curvedTrackPos.y, curvedTrackPos.z));

        if (curvedTrackOrientaiton.getXZ() == 1 || curvedTrackOrientaiton.getXZ() == 2) {
            if (previousTrackPos.z < curvedTrackPos.z) {
                left = !left;
            }
        } else {
            if (previousTrackPos.z > curvedTrackPos.z) {
                left = !left;
            }
        }
        return left;
    }

    public static boolean leftCurvedPath(Vector3i previousTrackPos, Vector3i curvedTrackPos) {
        BlockOrientation curvedTrackOrientaiton
                = BlockOrientation.getBlockOrientation(
                        VoxelGame.getWorld().getBlockData(curvedTrackPos.x, curvedTrackPos.y, curvedTrackPos.z));

        if (previousTrackPos != null && curvedTrackOrientaiton != null) {
            switch (curvedTrackOrientaiton.getXZ()) {
                case 0 -> {
                    if (curvedTrackPos.x < previousTrackPos.x) {
                        return true;
                    } else {
                        return false;
                    }
                }
                case 1 -> {
                    if (curvedTrackPos.x > previousTrackPos.x) {
                        return false;
                    } else {
                        return true;
                    }
                }
                case 2 -> {
                    if (curvedTrackPos.x > previousTrackPos.x) {
                        return true;
                    } else {
                        return false;
                    }
                }
                default -> {
                    if (curvedTrackPos.x < previousTrackPos.x) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isTrack(Block block) {
        return block == GameItems.TRACK
                || block == GameItems.RAISED_TRACK
                || block == GameItems.CROSS_TRACK
                || block == GameItems.CURVED_TRACK
                || block == GameItems.SWITCH_JUNCTION
                || block == GameItems.MERGE_TRACK
                || block == GameItems.STOP_TRACK;

    }

    public static Vector3i getNearestTrackPiece(Minecart e) {
        int x = Math.round(e.worldPosition.x);
        int y = Math.round(e.worldPosition.y);
        int z = Math.round(e.worldPosition.z);
        if (isTrack(e.getPointerHandler(), x, y, z)) {
            return new Vector3i(x, y, z);
        } else if (isTrack(e.getPointerHandler(), x, y + 1, z)) {
            return new Vector3i(x, y + 1, z);
        } else if (isTrack(e.getPointerHandler(), x + 1, y + 1, z)) {
            return new Vector3i(x + 1, y + 1, z);
        } else if (isTrack(e.getPointerHandler(), x - 1, y + 1, z)) {
            return new Vector3i(x - 1, y + 1, z);
        } else if (isTrack(e.getPointerHandler(), x, y + 1, z + 1)) {
            return new Vector3i(x, y + 1, z + 1);
        } else if (isTrack(e.getPointerHandler(), x, y + 1, z - 1)) {
            return new Vector3i(x, y + 1, z - 1);
        } else if (isTrack(e.getPointerHandler(), x + 1, y, z)) {
            return new Vector3i(x + 1, y, z);
        } else if (isTrack(e.getPointerHandler(), x - 1, y, z)) {
            return new Vector3i(x - 1, y, z);
        } else if (isTrack(e.getPointerHandler(), x, y, z + 1)) {
            return new Vector3i(x, y, z + 1);
        } else if (isTrack(e.getPointerHandler(), x, y, z - 1)) {
            return new Vector3i(x, y, z - 1);
        }
        return null;
    }
}
