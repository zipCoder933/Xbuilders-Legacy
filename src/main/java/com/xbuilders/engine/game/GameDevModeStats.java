/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.game;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.wcc.WCCi;

import java.io.IOException;

import org.joml.Vector3f;
import org.joml.Vector3i;
import processing.core.KeyCode;

/**
 * @author zipCoder933
 */
public class GameDevModeStats {

    public static String getDevModeStats(GameScene game) throws IOException {
        Ray cursor = game.player.camera.cursorRay.cursorRay;
        UserControlledPlayer userControlledPlayer = game.player;
        Vector3f playerPos = userControlledPlayer.worldPos;
        String gameText2 = "";
        gameText2 += "\nFPS: " + Math.round(game.ph.getApplet().frameRate) + "\n"
                + "\nPLAYER:"
                + "\n    biome: " + VoxelGame.getWorld().terrain.getBiomeOfVoxel((int) playerPos.x, (int) playerPos.y, (int) playerPos.z)
                + "\n    darkness: " + game.player.isInDarkness()
                + "\n    cam block-orientation: " + game.player.cameraBlockOrientation();

        if (cursor != null) {
            Vector3i cursorPos = new Vector3i();
            try {
                gameText2 += "\n\n\n\nCURSOR";
                if (game.keyIsPressed(KeyCode.V)) {
                    cursorPos = cursor.getHitPositionAsInt();
                    gameText2 += " HIT";
                } else {
                    cursorPos = cursor.getHitPosPlusNormal();
                    gameText2 += " HIT+NORMAL";
                }
                gameText2 += " (V = cursor mode): ";
            } catch (Exception e) {
                gameText2 += "\nError.";
                return gameText2;
            }

            WCCi wcc = new WCCi().set(cursorPos);
            gameText2 += "\n\nRAY POSITION:"
                    + "\n    " + cursorPos.toString()
                    + "\n    WCC: " + wcc.toString();

            if (wcc.chunkExists()) {
                Chunk chunk = wcc.getChunk();
                SubChunk subchunk = wcc.getSubChunk();
                gameText2
                        += "\n\nCHUNK:"
                        + "\n   coordiantes: " + chunk.getPosition()
                        + "\n    needs saving: " + chunk.needsSaving()
                        + "\n    modified by user: " + chunk.isModifiedByUser()
                        + "\n    " + chunk.genStatus();
                gameText2 += "\n    neighbors:\n" + chunk.neighbors.toString();

                gameText2
                        += "\n\nSUB-CHUNK:"
                        + "\n   coordiantes: " + subchunk.position
                        + "\n    Generated meshes: " + subchunk.hasGeneratedMeshes();
                if (subchunk.lightMap.inBoundsOfSLM()) {
                    gameText2 += "\n    lightmap.isInSLM(): " + subchunk.lightMap.inSLM();
                } else {
                    gameText2 += "\n    out of bounds for SLM";
                }
                gameText2 += "\n    lighmap init:" + subchunk.lightMap.initialized;

//                gameText2 += "\n\nMISC:";
//                TorchChannelSet torch = subchunk.lightMap.getTorchlight(wcc.subChunkVoxel);
//                int slmVal = ShaderLightMap.getImagePixels()[ShaderLightMap.coordsToIndex(ShaderLightMap.worldCoordsToLightmapCoords(
//                        cursorPos.x, cursorPos.y, cursorPos.z))];
//                gameText2 += "\n\nBLOCK:"
//                        + "\n    data: " + VoxelGame.getWorld().getBlockData(cursorPos.x, cursorPos.y, cursorPos.z);
//
//                gameText2 += "\n\nLIGHT:"
//                        + "\n    Light Frag: sun:" + subchunk.lightMap.getSunlight(wcc.subChunkVoxel)
//                        + "  torch: " + (torch != null ? torch.toString() : "null")
//                        + "  brightestChannel: " + (torch != null ? torch.getBrightestChannel() : "null")
//                        + "\n    Shader WLM: " + slmVal
//                        + "  (Sun: " + ChunkLightMap.getSunlightFromPackedByte(slmVal)
//                        + "  Torch: " + ChunkLightMap.getTorchLightFromPackedByte(slmVal) + ")"
//                        + "\n        In bounds: " + ShaderLightMap.inBounds(cursorPos.x, cursorPos.y, cursorPos.z)
//                        + "\n        " + ShaderLightMap.boundariesToString();
            }


        }

        return gameText2;
    }
}
