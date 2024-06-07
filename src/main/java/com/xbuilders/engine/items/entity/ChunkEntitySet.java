/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.entity;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.Chunk;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.game.Main;

import java.util.ArrayList;

import processing.core.PGraphics;

/**
 * @author zipCoder933
 */
// TODO: Replace all static entitys with blocks
public class ChunkEntitySet {

    SubChunk chunk;
    public ArrayList<Entity> list;

    public ChunkEntitySet(SubChunk chunk) {
        super();
        list = new ArrayList<>();
        this.chunk = chunk;
    }

    public Entity get(int i) {
        return list.get(i);
    }

    public void updateAndDrawEntities(PGraphics graphics, boolean drawEntities, boolean chunkInFrustum) {
        Entity.defaultShader();
        for (int i = list.size() - 1; i >= 0; i--) { // Loop through the list of entities in reverse order
            Entity e = get(i); // Get the entity at index i

            UserControlledPlayer userControlledPlayer = Main.ph().getPlayer();
            e.distToPlayer = e.worldPosition.distance(userControlledPlayer.worldPos); // Calculate the distance to the


            if (e.destroyMode) { // Check if the entity is in destroy mode
                list.remove(i); // Remove the entity from the list
                if (e.hasStaticMeshes()) { // Check if the entity has static meshes
                    chunk.generateStaticEntityMesh(); // Generate static entity mesh for the chunk
                }
                chunk.getParentChunk().markAsNeedsSaving(); // Mark the parent chunk as needing saving
                continue;
            } else if (!e.playerIsRidingThis()
                    && e.distToPlayer > VoxelGame.getSettings().getSettingsFile().entityMaxDistance) {
                continue; // Skip the rest of the loop for this entity
            } else if (e.needsUpdating) { // Check if the entity needs updating
                if (e.hasStaticMeshes()) { // Check if the entity has static meshes
                    chunk.generateStaticEntityMesh(); // Generate static entity mesh for the chunk
                }
                e.needsUpdating = false; // Set the needsUpdating flag to false
            }

            if (!e.hasStaticMeshes()) { // Check if the entity does not have static meshes
                // check if the entity is in frustum
                if (e.playerIsRidingThis() || e.getFrustumSphereRadius() <= 0) {
                    e.inFrustum = true;
                } else {
                    e.inFrustum = chunkInFrustum &&
                            VoxelGame.getPlayer().camera.frustum.isSphereInside(e.worldPosition,
                            e.getFrustumSphereRadius());
                }

                // update the entity
                if (e.update() && e.inFrustum) {
                    try {
                        VoxelGame.getShaderHandler().setAnimatedTexturesEnabled(false);
                        VoxelGame.getShaderHandler().setWorldSpaceOffset(e.worldPosition);
                        e.modelMatrix.identity().translate(
                                e.worldPosition.x,
                                e.worldPosition.y,
                                e.worldPosition.z);
                        e.sendModelMatrixToShader();
//                        ShaderHandler.setEntityLightLevel(e.getLightLevel());
                        if (drawEntities) e.draw(graphics);
                    } catch (Exception ex) {
                        ErrorHandler.saveErrorToLogFile(ex);
                    }
                }
            }

            e.worldPosition.y = MathUtils.clamp(e.worldPosition.y, 1, Chunk.HEIGHT - 1);
            e.hasMoved = !e.lastWorldPosition.equals(e.worldPosition);

            if (e.hasMoved) {// If the entity has moved
                e.lastWorldPosition.set(e.worldPosition);
                e.chunkPosition.set(e.worldPosition); // update entity's chunk position
                if (!e.chunkPosition.subChunk.equals(e.chunk.position)) { // Check if the entity's chunk position is
                    // different from the entity's chunk
                    SubChunk toChunk = e.chunkPosition.getSubChunk();
                    if (toChunk != null) {
                        e.chunk = toChunk; // Set the entity's chunk to the new chunk
                        toChunk.entities.list.add(e); // Add the entity to the new chunk's entities list
                        list.remove(i); // Remove the entity from the list

                        // Save both chunks
                        e.chunk.getParentChunk().markAsNeedsSaving();
                        toChunk.getParentChunk().markAsNeedsSaving();
                    }
                }
            }
        }
        VoxelGame.getShaderHandler().

                resetModelMatrix();
    }


    public static boolean drawEntities = true;

    public void clear() {
        list.clear();
    }

}
