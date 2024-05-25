/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.block.liquid;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.blockPipeline.LocalChangeRecord;
import com.xbuilders.engine.player.Player;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.BFS.TravelNode;
import com.xbuilders.engine.world.chunk.blockData.BlockData;

import java.awt.Color;

/**
 * @author zipCoder933
 */
public abstract class Liquid extends Block {

    /**
     * @return the waterMurkyness
     */
    public float getWaterMurkyness() {
        return waterMurkyness;
    }

    /**
     * @param waterMurkyness the waterMurkyness to set
     */
    public void setWaterMurkyness(float waterMurkyness) {
        this.waterMurkyness = waterMurkyness;
    }

    /**
     * @return the fallSpeed
     */
    public int getFallSpeed() {
        return fallSpeed;
    }

    /**
     * @param fallSpeed the fallSpeed to set
     */
    public void setFallSpeed(int fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    LiquidPropagator propagator;

    public Liquid(int id, String name, BlockTexture texture) {
        super(id, name, texture);
        setRenderType(BlockRenderType.LIQUID);
    }

    @Override
    public final void initialize() {
        propagator = new LiquidPropagator(this, getFallSpeed(), 4);
    }

    public abstract boolean setBlock(int x, int y, int z);

    public boolean isPenetrableCustom(Block block) {
        return true;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public final void onWorldOpen() {
        propagator.newWorld();
    }

    @Override
    public final void onWorldClose() {
        propagator.closeWorld();
    }

    private int fallSpeed = 400;

    public void startLiquid(int x, int y, int z) {
        if (VoxelGame.getWorld().getBlock(x, y + 1, z).isAir()) {
            synchronized (propagator.addLock) {
                propagator.queue.add(new TravelNode(x, y, z, 0));
            }
            propagator.thread.wake();
        }
    }

    @Override
    public final boolean setBlock(final int x, final int y, final int z, BlockData orientation) {
        this.set(x, y, z);
        startLiquid(x, y, z);
        return true;
    }

    @Override
    public final void onLocalChange(LocalChangeRecord record) {
        startLiquid(
                record.getTargetBlockPosition().x,
                record.getTargetBlockPosition().y,
                record.getTargetBlockPosition().z);
    }

    @Override
    public final void afterRemovalEvent(final int x, final int y, final int z) {
        propagator.onRemoveEvent(x, y, z);
    }

    @Override
    public final void drawOverlayInPlayerHead() {
        if (backgroundColor != null) {
            getPointerHandler().getApplet().fill(
                    backgroundColor.getRed(),
                    backgroundColor.getGreen(),
                    backgroundColor.getBlue(), 90);
            getPointerHandler().getApplet().rect(-2, -2, getPointerHandler().getApplet().width + 4,
                    getPointerHandler().getApplet().height + 4);
        }
    }

    private float waterMurkyness = 0.08f;
    private Color backgroundColor;

    @Override
    public final int playerHeadEnterBlockEvent(Player player) {
        backgroundColor = inWaterBackgroundColor();
        VoxelGame.getShaderHandler().setNaturalBackgroundEnabled(false);
        VoxelGame.getShaderHandler().setFogDistance(waterMurkyness);
        VoxelGame.getShaderHandler().setSkyColor(
                backgroundColor.getRed(),
                backgroundColor.getGreen(),
                backgroundColor.getBlue());
        return id;
    }

    public abstract Color inWaterBackgroundColor();

}
