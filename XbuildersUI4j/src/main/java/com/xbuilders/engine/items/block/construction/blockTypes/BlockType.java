/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.block.construction.blockTypes;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTextureAtlas;
import com.xbuilders.engine.utils.MiscUtils;
import com.xbuilders.engine.utils.imageAtlas.ImageAtlasPosition;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import org.joml.Vector3f;
import processing.core.PShape;
import processing.core.PVector;

import java.util.function.Consumer;

/**
 *
 * @author zipCoder933
 */
public abstract class BlockType {

    //<editor-fold defaultstate="collapsed" desc="static methods">
    public static final float ONE_SIXTEENTH = 0.0625f;


    public static PVector[] rotateVerticiesYAxis(PVector[] verts, int rotationXZ) {
        double rot = (Math.PI / 2) * rotationXZ;
        return MiscUtils.rotateVerticiesYAxis(verts, rot);
    }
    public static Vector3f[] rotateVerticiesYAxis(Vector3f[] verts, int rotationXZ) {
        double rot = (Math.PI / 2) * rotationXZ;
        return MiscUtils.rotateVerticiesYAxis(verts, rot);
    }

    public static boolean shouldRenderSide(Block thisBlock, Block neighboringBlock) {
        if (neighboringBlock == null) {
            return false;
        }
        if (thisBlock.isOpaque()) {
            return !neighboringBlock.isSolid()
                    || !neighboringBlock.isOpaque();
        } else {
            return neighboringBlock.isAir()
                    || neighboringBlock.getRenderType() == BlockRenderType.SPRITE
                    || neighboringBlock.getRenderType() == BlockRenderType.FENCE;
        }
    }

    public static float getUVTextureCoord_X(Block block, float uvCoord_x) {
        ImageAtlasPosition texturePos = getTextureManager().getTextureIndex(block.texture.TOP);
        return MathUtils.map(uvCoord_x, 0, 1, texturePos.minX, texturePos.maxX);
    }

    public static float getUVTextureCoord_Y(Block block, float uvCoord_y) {
        ImageAtlasPosition texturePos = getTextureManager().getTextureIndex(block.texture.TOP);
        return MathUtils.map(uvCoord_y, 0, 1, texturePos.minY, texturePos.maxY);

    }

    public static float getUVTextureCoord_X(int[] pos, float uvCoord_x) {
        ImageAtlasPosition texturePos = getTextureManager().getTextureIndex(pos);
        return MathUtils.map(uvCoord_x, 0, 1, texturePos.minX, texturePos.maxX);
    }

    public static float getUVTextureCoord_Y(int[] pos, float uvCoord_y) {
        ImageAtlasPosition texturePos = getTextureManager().getTextureIndex(pos);
        return MathUtils.map(uvCoord_y, 0, 1, texturePos.minY, texturePos.maxY);
    }

    /**
     * @return the textureManager
     */
    public static BlockTextureAtlas getTextureManager() {
        return ItemList.blocks.textureAtlas;
    }
//</editor-fold>

    public BlockType() {
    }

    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        return null;
    }


    public abstract void constructBlock(PShape buffers, Block block, BlockData data,
                                        Block negativeX, Block positiveX, Block negativeY,
                                        Block positiveY, Block negativeZ, Block positiveZ,
                                        int x, int y, int z);

    public void getCursorBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        getCollisionBoxes(consumer, box, block, data, x, y, z);
    }

    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        box.setPosAndSize(x, y, z, 1, 1, 1);
        consumer.accept(box);
    }

    public boolean isCubeShape() {
        return false;
    }

}
