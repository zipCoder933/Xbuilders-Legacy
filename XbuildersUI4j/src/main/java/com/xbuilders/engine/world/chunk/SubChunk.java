// 
// Decompiled by Procyon v0.5.36
// 
package com.xbuilders.engine.world.chunk;

import com.xbuilders.engine.player.camera.frustum.Frustum;
import com.xbuilders.engine.rendering.ShaderHandler;
import com.xbuilders.game.Main;
import com.xbuilders.engine.utils.MiscUtils;
import processing.core.PVector;
import com.xbuilders.engine.items.entity.shapeSet.OrientedShape;

import java.util.Map;

import com.xbuilders.engine.items.entity.Entity;
import processing.core.PImage;

import java.util.HashMap;

import com.xbuilders.engine.world.chunk.meshing.NaiveCulling;
import com.xbuilders.game.PointerHandler;
import org.joml.Vector3i;
import com.xbuilders.engine.world.chunk.lightMap.ChunkLightMap;
import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.items.entity.ChunkEntitySet;
import processing.core.PShape;

import java.util.ArrayList;

import com.xbuilders.engine.utils.math.AABB;
import org.joml.Matrix4f;

public class SubChunk {

    public static final int WIDTH = 16;
    AABB aabb;
    private Chunk parentChunk;
    private ArrayList<PShape> entityMeshes;
    public Vector3i position;
    public ChunkVoxels data;
    public ChunkEntitySet entities;
    public ChunkLightMap lightMap;
    public boolean hasLightNeighbor;
    private Vector3i currentTransformation;
    private Vector3i offset;
    PShape entityMesh;
    PShape opaqueMesh, transparentMesh;
    boolean needsRegenerating;
    private float distToPlayer;

    public ChunkEntitySet getEntities() {
        return this.entities;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public ChunkVoxels getVoxels() {
        return this.data;
    }

    public ChunkLightMap getLightMap() {
        return this.lightMap;
    }

    public Chunk getParentChunk() {
        return this.parentChunk;
    }

    public boolean hasGeneratedMeshes() {
        return this.opaqueMesh != null;
    }

    public void regenerateMesh(final int x, final int y, final int z) {
        this.getParentChunk().markChunksAsNeedsRegenerating(this.position.y, x, y, z);
    }

    public PointerHandler getPointerHandler() {
        return this.parentChunk.getPointerHandler();
    }

    public SubChunk(final Chunk parent) {
        this.parentChunk = parent;
        this.position = new Vector3i(0, 0, 0);
        this.offset = new Vector3i(0, 0, 0);
        this.currentTransformation = new Vector3i(0, 0, 0);
        this.entities = new ChunkEntitySet(this);
        this.entityMeshes = new ArrayList<PShape>();
        this.aabb = new AABB();
        this.lightMap = new ChunkLightMap(this, SubChunk.WIDTH, SubChunk.WIDTH, SubChunk.WIDTH);
        this.data = new ChunkVoxels(SubChunk.WIDTH, SubChunk.WIDTH, SubChunk.WIDTH);
    }

    public void init(final int x, final int y, final int z) {
        this.hasLightNeighbor = false;
        this.position = new Vector3i(x, y, z);
        this.offset.y = this.getPosition().y * SubChunk.WIDTH;
        this.currentTransformation.x = this.getPosition().x * SubChunk.WIDTH;
        this.currentTransformation.z = this.getPosition().z * SubChunk.WIDTH;
        this.aabb.setPosAndSize((float) (x * SubChunk.WIDTH), (float) (y * SubChunk.WIDTH),
                (float) (z * SubChunk.WIDTH), SubChunk.WIDTH, SubChunk.WIDTH, SubChunk.WIDTH);
        this.lightMap.reset(this);
        this.data.reset();
        this.entityMeshes.clear();
        this.entities.clear();

        modelMatrix.identity().translate(
                (float) (this.getPosition().x * SubChunk.WIDTH),
                0,
                (float) (this.getPosition().z * SubChunk.WIDTH));
    }

    public void generateMesh() {
        if (lightMap.allDarkness && !hasLightNeighbor) {// Check for any light neighbors
            hasLightNeighbor = NaiveCulling.checkAllNeighborsForNonDarkness(this);
        }
        NaiveCulling.generateMesh(this,
                this.opaqueMesh = this.getPointerHandler().getApplet().createShape(),
                this.transparentMesh = this.getPointerHandler().getApplet().createShape(), this.offset);

        this.needsRegenerating = false;
        this.getParentChunk().markAsNeedsSaving();
    }

    public void generateStaticEntityMesh() {
        this.entityMeshes.clear();
        final HashMap<PImage, ArrayList<Entity>> orginizedEntities = new HashMap<PImage, ArrayList<Entity>>();
        for (final Entity e : this.entities.list) {
            if (e.hasStaticMeshes()) {
                final PImage texture = e.getStaticTexture();
                if (!orginizedEntities.containsKey(texture)) {
                    orginizedEntities.put(texture, new ArrayList<Entity>());
                }
                orginizedEntities.get(texture).add(e);
            }
        }
        for (final Map.Entry<PImage, ArrayList<Entity>> entry : orginizedEntities.entrySet()) {
            this.entityMeshes.add(this.generateStaticEntityType(entry.getKey(), entry.getValue()));
        }
    }

    public PShape generateStaticEntityType(final PImage key, final ArrayList<Entity> value) {
        this.entityMesh = this.getPointerHandler().getApplet().createShape();
        final PShape entityMesh = this.entityMesh;
        this.getPointerHandler().getApplet();
        entityMesh.beginShape(8);
        this.entityMesh.noStroke();
        this.entityMesh.texture(key);
        for (final Entity e : value) {
            if (e.hasStaticMeshes()) {
                final ArrayList<OrientedShape> shapes = e.getStaticMeshes();
                for (final OrientedShape shape : shapes) {
                    for (int i = 0; i < shape.shape.getChildCount(); ++i) {
                        final PShape child = shape.shape.getChild(i);
                        if (shape.yRotation == 0.0f) {
                            for (int j = 0; j < child.getVertexCount(); ++j) {
                                final PVector vertex = child.getVertex(j);
                                final float U = child.getTextureU(j);
                                final float V = child.getTextureV(j);
                                this.entityMesh.vertex(
                                        vertex.x + shape.offset.x + (e.worldPosition.x - this.currentTransformation.x),
                                        vertex.y + shape.offset.y + (e.worldPosition.y - this.currentTransformation.y),
                                        vertex.z + shape.offset.z + (e.worldPosition.z - this.currentTransformation.z),
                                        U, V);
                            }
                        } else {
                            final int vertexCount = child.getVertexCount();
                            PVector[] vertices = new PVector[vertexCount];
                            for (int k = 0; k < vertexCount; ++k) {
                                final PVector vertex2 = child.getVertex(k);
                                vertices[k] = vertex2;
                            }
                            vertices = MiscUtils.rotateVerticiesYAxis(vertices, shape.yRotation);
                            for (int k = 0; k < child.getVertexCount(); ++k) {
                                final PVector vertex2 = vertices[k];
                                final float U2 = child.getTextureU(k);
                                final float V2 = child.getTextureV(k);
                                this.entityMesh.vertex(
                                        vertex2.x + shape.offset.x + (e.worldPosition.x - this.currentTransformation.x),
                                        vertex2.y + shape.offset.y + (e.worldPosition.y - this.currentTransformation.y),
                                        vertex2.z + shape.offset.z + (e.worldPosition.z - this.currentTransformation.z),
                                        U2, V2);
                            }
                        }
                    }
                }
            }
        }
        this.entityMesh.endShape();
        return this.entityMesh;
    }

    boolean inFrustum;

    private boolean checkInFrustum() {
        final Frustum frustum = this.parentChunk.getPointerHandler().getPlayer().camera.frustum;
        inFrustum = frustum.isAABBInside(this.aabb);
        return inFrustum;
    }

    private boolean darknessCull() {
        // TODO: When a dark chunk is culled and it is drawing a face on the side of it,
        // it won't render the face. Fix this by rednering outside neibhboring chunks
        // that are next to a dark chunk
        return (!lightMap.allDarkness
                || distToPlayer < SubChunk.WIDTH * 2
                || hasLightNeighbor) && !data.isEmpty();
        // return true;
    }

    Matrix4f modelMatrix = new Matrix4f();

    public void drawOpaqueAndEntities(final ShaderHandler shader, boolean drawEntities) {
        distToPlayer = VoxelGame.getPlayer().aabb.worldPosition.distance(
                position.x * SubChunk.WIDTH + WIDTH / 2,
                position.y * SubChunk.WIDTH + WIDTH / 2,
                position.z * SubChunk.WIDTH + WIDTH / 2);

        if (darknessCull() && checkInFrustum()) {

            // // Draw chunks as a box for debugging
            // Main.getPG().pushMatrix();
            // Main.getPG().translate(offset.x + WIDTH / 2, offset.y + WIDTH / 2, offset.z +
            // WIDTH / 2);
            // Main.getPG().resetShader();
            // Main.getPG().stroke(255, 0, 0);
            // Main.getPG().strokeWeight(3);
            // Main.getPG().box(2);
            // // Main.getPG().strokeWeight(1);
            // // Main.getPG().stroke(255);
            // // Main.getPG().box(WIDTH);
            // VoxelGame.getShaderHandler().setShader(Main.getPG());
            // Main.getPG().popMatrix();
            // // -----------------------------------------
            if (this.needsRegenerating) {
                // (new Thread() {// TODO: Explore this multithreading to see if it leads to
                // better performance
                // @Override
                // public void run() {
                generateMesh();
                // }
                // }).start();
            }
            if (shader != null) {
                shader.setAnimatedTexturesEnabled(true);
                shader.setWorldSpaceOffset((float) (this.getPosition().x * SubChunk.WIDTH), 0.0f,
                        (float) (this.getPosition().z * SubChunk.WIDTH));
                shader.setModelMatrix(modelMatrix);
            }
            Main.getPG().shape(this.opaqueMesh);
            if (drawEntities) {
                if (shader != null) {
                    shader.setAnimatedTexturesEnabled(false);
                }
                for (int i = 0; i < this.entityMeshes.size(); ++i) {
                    Main.getPG().shape(this.entityMeshes.get(i));
                }
            }
        }
        // The entities must as least be updated even if not in frustum
        this.getEntities().updateAndDrawEntities(Main.getPG(), inFrustum, drawEntities);
    }

    public void drawTransparent(final ShaderHandler shader) {
        if (inFrustum && darknessCull()) {
            if (shader != null) {
                shader.setAnimatedTexturesEnabled(true);
                shader.setWorldSpaceOffset((float) (this.getPosition().x * SubChunk.WIDTH), 0.0f,
                        (float) (this.getPosition().z * SubChunk.WIDTH));
                shader.setModelMatrix(modelMatrix);
            }
            Main.getPG().shape(this.transparentMesh);
        }
    }

    public static boolean inBounds(final int x, final int y, final int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < SubChunk.WIDTH && y < SubChunk.WIDTH && z < SubChunk.WIDTH;
    }
}