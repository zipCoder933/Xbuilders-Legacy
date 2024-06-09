/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.blockData.BlockData;
import processing.core.PShape;
import processing.core.PVector;

import java.util.function.Consumer;

/**
 * @author zipCoder933
 */
public class SpriteRenderer extends BlockType {

    static PVector[] vertices = {
            new PVector(0, 0, 0),//0
            new PVector(1, 0, 0),//1
            new PVector(1, 0, 1),//2
            new PVector(0, 0, 1),//3
            new PVector(0, 1, 0),//4
            new PVector(1, 1, 0),//5
            new PVector(1, 1, 1),//6
            new PVector(0, 1, 1)};//7
    static int[] spriteIndices = {
            0, 2, 6,
            0, 6, 4,
            3, 1, 5,
            3, 5, 7,
            2, 0, 4,
            2, 4, 6,
            1, 3, 7,
            1, 7, 5,};

    @Override
    public void constructBlock(PShape buffers, Block block, BlockData data,
                               Block negativeX, Block positiveX, Block negativeY,
                               Block positiveY, Block negativeZ, Block positiveZ,
                               int x, int y, int z) {
        //shading.applySpriteShading(shape);
        buffers.vertex(vertices[spriteIndices[0]].x + x, vertices[spriteIndices[0]].y + y, vertices[spriteIndices[0]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[1]].x + x, vertices[spriteIndices[1]].y + y, vertices[spriteIndices[1]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[2]].x + x, vertices[spriteIndices[2]].y + y, vertices[spriteIndices[2]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[3]].x + x, vertices[spriteIndices[3]].y + y, vertices[spriteIndices[3]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[4]].x + x, vertices[spriteIndices[4]].y + y, vertices[spriteIndices[4]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[5]].x + x, vertices[spriteIndices[5]].y + y, vertices[spriteIndices[5]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);

        buffers.vertex(vertices[spriteIndices[6]].x + x, vertices[spriteIndices[6]].y + y, vertices[spriteIndices[6]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[7]].x + x, vertices[spriteIndices[7]].y + y, vertices[spriteIndices[7]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[8]].x + x, vertices[spriteIndices[8]].y + y, vertices[spriteIndices[8]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[9]].x + x, vertices[spriteIndices[9]].y + y, vertices[spriteIndices[9]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[10]].x + x, vertices[spriteIndices[10]].y + y, vertices[spriteIndices[10]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[11]].x + x, vertices[spriteIndices[11]].y + y, vertices[spriteIndices[11]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);

        buffers.vertex(vertices[spriteIndices[12]].x + x, vertices[spriteIndices[12]].y + y, vertices[spriteIndices[12]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[13]].x + x, vertices[spriteIndices[13]].y + y, vertices[spriteIndices[13]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[14]].x + x, vertices[spriteIndices[14]].y + y, vertices[spriteIndices[14]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[15]].x + x, vertices[spriteIndices[15]].y + y, vertices[spriteIndices[15]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[16]].x + x, vertices[spriteIndices[16]].y + y, vertices[spriteIndices[16]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[17]].x + x, vertices[spriteIndices[17]].y + y, vertices[spriteIndices[17]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);

        buffers.vertex(vertices[spriteIndices[18]].x + x, vertices[spriteIndices[18]].y + y, vertices[spriteIndices[18]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[19]].x + x, vertices[spriteIndices[19]].y + y, vertices[spriteIndices[19]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[20]].x + x, vertices[spriteIndices[20]].y + y, vertices[spriteIndices[20]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[21]].x + x, vertices[spriteIndices[21]].y + y, vertices[spriteIndices[21]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
        buffers.vertex(vertices[spriteIndices[22]].x + x, vertices[spriteIndices[22]].y + y, vertices[spriteIndices[22]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        buffers.vertex(vertices[spriteIndices[23]].x + x, vertices[spriteIndices[23]].y + y, vertices[spriteIndices[23]].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);

    }

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
    }

    public void getCursorBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        float width = 1 - (ONE_SIXTEENTH * 2);
        box.setPosAndSize(x + (ONE_SIXTEENTH), y, z + (ONE_SIXTEENTH), width, 1, width);
        consumer.accept(box);
    }
}
