// 
// Decompiled by Procyon v0.5.36
// 

package com.xbuilders.engine.world.clouds;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.random.FastNoise;
import com.xbuilders.game.PointerHandler;
import processing.core.PGraphics;
import processing.core.PShape;

public class Clouds
{
    FastNoise noise;
    boolean[][][] cloudSections;
    PShape cloudShape;
    int cloudWidth;
    int cloudDepth;
    float scale;
    float cloudX;
    float cloudZ;
    float lastPlayerX;
    float lastPlayerZ;
    float translationX;
    float translationZ;
    PointerHandler ph;
    float seed;
    
    public Clouds(final PointerHandler ph) {
        this.cloudWidth = 60;
        this.cloudDepth = 3;
        this.scale = 13.0f;
        this.cloudX = -1.0f;
        this.cloudZ = -1.0f;
        this.translationX = 0.0f;
        this.translationZ = 0.0f;
        this.seed = 0.0f;
        this.ph = ph;
        this.noise = new FastNoise();
        this.cloudSections = new boolean[this.cloudWidth][this.cloudDepth][this.cloudWidth];
        this.cloudShape = new PShape();
    }
    
    public void initialize() {
        this.translationX = 0.0f;
        this.translationZ = 0.0f;
        this.seed = (float)(Math.random() * 1000.0);
        UserControlledPlayer userControlledPlayer1 = this.ph.getPlayer();
        final int playerX = (int) userControlledPlayer1.worldPos.x;
        UserControlledPlayer userControlledPlayer = this.ph.getPlayer();
        final int playerZ = (int) userControlledPlayer.worldPos.z;
        this.generateCloudPattern((float)playerX, (float)playerZ, this.seed);
    }
    
    private void updateCloudMesh() {
        (this.cloudShape = this.ph.getApplet().createShape()).beginShape(8);
        this.cloudShape.noStroke();
        this.cloudShape.fill(255);
        for (int x = 0; x < this.cloudWidth; ++x) {
            for (int y = 0; y < this.cloudDepth; ++y) {
                for (int z = 0; z < this.cloudWidth; ++z) {
                    boolean posX = true;
                    boolean negX = true;
                    boolean posY = true;
                    boolean negY = true;
                    boolean posZ = true;
                    boolean negZ = true;
                    if (x + 1 < this.cloudWidth) {
                        posX = !this.cloudSections[x + 1][y][z];
                    }
                    if (y + 1 < this.cloudDepth) {
                        posY = !this.cloudSections[x][y + 1][z];
                    }
                    if (z + 1 < this.cloudWidth) {
                        posZ = !this.cloudSections[x][y][z + 1];
                    }
                    if (x - 1 > 0) {
                        negX = !this.cloudSections[x - 1][y][z];
                    }
                    if (y - 1 > 0) {
                        negY = !this.cloudSections[x][y - 1][z];
                    }
                    if (z - 1 > 0) {
                        negZ = !this.cloudSections[x][y][z - 1];
                    }
                    if (this.cloudSections[x][y][z]) {
                        CloudBlockRenderer.constructBlock(this.cloudShape, negX, posX, negY, posY, negZ, posZ, x, y, z);
                    }
                }
            }
        }
        this.cloudShape.endShape();
    }
    
    private void generateCloudPattern(final float Xpos, final float Zpos, final float seed) {
        VoxelGame.getShaderHandler().cloudShader.set("cloudWidth", this.cloudWidth);
        VoxelGame.getShaderHandler().cloudShader.set("cloudDepth", this.cloudDepth);
        for (int x = 0; x < this.cloudWidth; ++x) {
            for (int y = 0; y < this.cloudDepth; ++y) {
                final float yVal = MathUtils.map((float)y, 0.0f, (float)this.cloudDepth, 0.25f, 0.1f);
                for (int z = 0; z < this.cloudWidth; ++z) {
                    this.cloudSections[x][y][z] = (this.noise.GetValueFractal(x * 10 + Xpos, (y + seed) * 4.0f, z * 10 + Zpos) > yVal);
                }
            }
        }
        this.updateCloudMesh();
        this.setCloudPosition(Xpos, Zpos);
        this.lastPlayerX = Xpos;
        this.lastPlayerZ = Zpos;
    }
    
    private void setCloudPosition(final float x, final float z) {
        this.cloudX = x - this.cloudWidth / 2 * this.scale;
        this.cloudZ = z - this.cloudWidth / 2 * this.scale;
    }
    
    public void draw(final PGraphics g) {
        this.translationX += 0.05f;
        this.translationZ += 0.007f;
        UserControlledPlayer userControlledPlayer1 = this.ph.getPlayer();
        final int playerX = (int) userControlledPlayer1.worldPos.x;
        UserControlledPlayer userControlledPlayer = this.ph.getPlayer();
        final int playerZ = (int) userControlledPlayer.worldPos.z;
        g.pushMatrix();
        g.shader(VoxelGame.getShaderHandler().cloudShader);
        g.noStroke();
        g.translate(this.cloudX + this.translationX, 0.0f, this.cloudZ + this.translationZ);
        g.scale(this.scale, this.scale, this.scale);
        g.shape(this.cloudShape);
        g.popMatrix();
        if (MathUtils.dist(playerX - this.translationX, playerZ - this.translationZ, this.lastPlayerX, this.lastPlayerZ) > 90.0) {
            this.generateCloudPattern(playerX - this.translationX, playerZ - this.translationZ, this.seed);
        }
    }
}
