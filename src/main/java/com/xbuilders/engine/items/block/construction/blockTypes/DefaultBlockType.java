/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.block.construction.blockTypes;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.texture.BlockTextureAtlas;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.chunk.blockData.BlockData;
import processing.core.PShape;
import processing.core.PVector;

import java.util.function.Consumer;

import static com.xbuilders.engine.items.BlockList.DEFAULT_BLOCK_TYPE_ID;

/**
 * @author zipCoder933
 */
public class DefaultBlockType extends BlockType {

    static PVector[] vertices = {
            new PVector(0, 0, 0),//0
            new PVector(1, 0, 0),//1
            new PVector(1, 0, 1),//2
            new PVector(0, 0, 1),//3
            new PVector(0, 1, 0),//4
            new PVector(1, 1, 0),//5
            new PVector(1, 1, 1),//6
            new PVector(0, 1, 1)};//7

    static int[] blockIndices = {
            //Back
            0, 1, 5,
            0, 5, 4,
            //Right
            1, 2, 6,
            1, 6, 5,
            //Front
            2, 3, 7,
            2, 7, 6,
            //Left
            3, 0, 4,
            3, 4, 7,
            //Top
            2, 1, 0,
            3, 2, 0,
            //Bottom
            4, 5, 6,
            4, 6, 7
    };

    @Override
    public void constructBlock(PShape buffers, Block block, BlockData data,
                               Block negativeX, Block positiveX, Block negativeY,
                               Block positiveY, Block negativeZ, Block positiveZ,
                               int x, int y, int z) {

//        System.out.println("MAKING MESH: " + block + "," + negativeX + "," + positiveX + "," + negativeZ + "," + positiveZ
//                + "," + negativeY + "," + positiveY);
        if (shouldRenderSide(block, negativeX)) {
            constructLeft(getTextureManager(), block, buffers, x, y, z //,shading
            );
        }
        if (shouldRenderSide(block, positiveX)) {
            constructRight(getTextureManager(), block, buffers, x, y, z //,shading
            );
        }
        if (shouldRenderSide(block, negativeY)) {
            constructTop(getTextureManager(), block, buffers, x, y, z //,shading
            );
        }

        if (shouldRenderSide(block, positiveY)) {
            constructBottom(getTextureManager(), block, buffers, x, y, z //,shading
            );
        }
        if (shouldRenderSide(block, negativeZ)) {
            constructBack(getTextureManager(), block, buffers, x, y, z //,shading
            );
        }
        if (shouldRenderSide(block, positiveZ)) {
            constructFront(getTextureManager(), block, buffers, x, y, z //,shading
            );
        }
    }

    private static void constructBack(BlockTextureAtlas manager, com.xbuilders.engine.items.block.Block block, PShape shape, int x, int y, int z) {
        shape.vertex(vertices[blockIndices[0]].x + x, vertices[blockIndices[0]].y + y, vertices[blockIndices[0]].z + z, manager.getTextureIndex(block.texture.BACK).maxX, manager.getTextureIndex(block.texture.BACK).minY);
        shape.vertex(vertices[blockIndices[1]].x + x, vertices[blockIndices[1]].y + y, vertices[blockIndices[1]].z + z, manager.getTextureIndex(block.texture.BACK).minX, manager.getTextureIndex(block.texture.BACK).minY);
        shape.vertex(vertices[blockIndices[2]].x + x, vertices[blockIndices[2]].y + y, vertices[blockIndices[2]].z + z, manager.getTextureIndex(block.texture.BACK).minX, manager.getTextureIndex(block.texture.BACK).maxY);
        shape.vertex(vertices[blockIndices[3]].x + x, vertices[blockIndices[3]].y + y, vertices[blockIndices[3]].z + z, manager.getTextureIndex(block.texture.BACK).maxX, manager.getTextureIndex(block.texture.BACK).minY);
        shape.vertex(vertices[blockIndices[4]].x + x, vertices[blockIndices[4]].y + y, vertices[blockIndices[4]].z + z, manager.getTextureIndex(block.texture.BACK).minX, manager.getTextureIndex(block.texture.BACK).maxY);
        shape.vertex(vertices[blockIndices[5]].x + x, vertices[blockIndices[5]].y + y, vertices[blockIndices[5]].z + z, manager.getTextureIndex(block.texture.BACK).maxX, manager.getTextureIndex(block.texture.BACK).maxY);
    }

    private static void constructRight(BlockTextureAtlas manager, com.xbuilders.engine.items.block.Block block, PShape shape, int x, int y, int z) {
        shape.vertex(vertices[blockIndices[6]].x + x, vertices[blockIndices[6]].y + y, vertices[blockIndices[6]].z + z, manager.getTextureIndex(block.texture.RIGHT).maxX, manager.getTextureIndex(block.texture.RIGHT).minY);
        shape.vertex(vertices[blockIndices[7]].x + x, vertices[blockIndices[7]].y + y, vertices[blockIndices[7]].z + z, manager.getTextureIndex(block.texture.RIGHT).minX, manager.getTextureIndex(block.texture.RIGHT).minY);
        shape.vertex(vertices[blockIndices[8]].x + x, vertices[blockIndices[8]].y + y, vertices[blockIndices[8]].z + z, manager.getTextureIndex(block.texture.RIGHT).minX, manager.getTextureIndex(block.texture.RIGHT).maxY);
        shape.vertex(vertices[blockIndices[9]].x + x, vertices[blockIndices[9]].y + y, vertices[blockIndices[9]].z + z, manager.getTextureIndex(block.texture.RIGHT).maxX, manager.getTextureIndex(block.texture.RIGHT).minY);
        shape.vertex(vertices[blockIndices[10]].x + x, vertices[blockIndices[10]].y + y, vertices[blockIndices[10]].z + z, manager.getTextureIndex(block.texture.RIGHT).minX, manager.getTextureIndex(block.texture.RIGHT).maxY);
        shape.vertex(vertices[blockIndices[11]].x + x, vertices[blockIndices[11]].y + y, vertices[blockIndices[11]].z + z, manager.getTextureIndex(block.texture.RIGHT).maxX, manager.getTextureIndex(block.texture.RIGHT).maxY);
    }

    private static void constructFront(BlockTextureAtlas manager, com.xbuilders.engine.items.block.Block block, PShape shape, int x, int y, int z) {
        //shading.applyTopVertex3Shading(shape, Face.FRONT);
        shape.vertex(vertices[blockIndices[12]].x + x, vertices[blockIndices[12]].y + y, vertices[blockIndices[12]].z + z, manager.getTextureIndex(block.texture.FRONT).maxX, manager.getTextureIndex(block.texture.FRONT).minY);
        shape.vertex(vertices[blockIndices[13]].x + x, vertices[blockIndices[13]].y + y, vertices[blockIndices[13]].z + z, manager.getTextureIndex(block.texture.FRONT).minX, manager.getTextureIndex(block.texture.FRONT).minY);
        shape.vertex(vertices[blockIndices[14]].x + x, vertices[blockIndices[14]].y + y, vertices[blockIndices[14]].z + z, manager.getTextureIndex(block.texture.FRONT).minX, manager.getTextureIndex(block.texture.FRONT).maxY);
        shape.vertex(vertices[blockIndices[15]].x + x, vertices[blockIndices[15]].y + y, vertices[blockIndices[15]].z + z, manager.getTextureIndex(block.texture.FRONT).maxX, manager.getTextureIndex(block.texture.FRONT).minY);
        shape.vertex(vertices[blockIndices[16]].x + x, vertices[blockIndices[16]].y + y, vertices[blockIndices[16]].z + z, manager.getTextureIndex(block.texture.FRONT).minX, manager.getTextureIndex(block.texture.FRONT).maxY);
        shape.vertex(vertices[blockIndices[17]].x + x, vertices[blockIndices[17]].y + y, vertices[blockIndices[17]].z + z, manager.getTextureIndex(block.texture.FRONT).maxX, manager.getTextureIndex(block.texture.FRONT).maxY);
    }

    private static void constructLeft(BlockTextureAtlas manager, com.xbuilders.engine.items.block.Block block, PShape shape, int x, int y, int z) {
        //shading.applyTopVertex4Shading(shape, Face.LEFT);
        shape.vertex(vertices[blockIndices[18]].x + x, vertices[blockIndices[18]].y + y, vertices[blockIndices[18]].z + z, manager.getTextureIndex(block.texture.LEFT).maxX, manager.getTextureIndex(block.texture.LEFT).minY);
        shape.vertex(vertices[blockIndices[19]].x + x, vertices[blockIndices[19]].y + y, vertices[blockIndices[19]].z + z, manager.getTextureIndex(block.texture.LEFT).minX, manager.getTextureIndex(block.texture.LEFT).minY);
        shape.vertex(vertices[blockIndices[20]].x + x, vertices[blockIndices[20]].y + y, vertices[blockIndices[20]].z + z, manager.getTextureIndex(block.texture.LEFT).minX, manager.getTextureIndex(block.texture.LEFT).maxY);
        shape.vertex(vertices[blockIndices[21]].x + x, vertices[blockIndices[21]].y + y, vertices[blockIndices[21]].z + z, manager.getTextureIndex(block.texture.LEFT).maxX, manager.getTextureIndex(block.texture.LEFT).minY);
        shape.vertex(vertices[blockIndices[22]].x + x, vertices[blockIndices[22]].y + y, vertices[blockIndices[22]].z + z, manager.getTextureIndex(block.texture.LEFT).minX, manager.getTextureIndex(block.texture.LEFT).maxY);
        shape.vertex(vertices[blockIndices[23]].x + x, vertices[blockIndices[23]].y + y, vertices[blockIndices[23]].z + z, manager.getTextureIndex(block.texture.LEFT).maxX, manager.getTextureIndex(block.texture.LEFT).maxY);
    }

    private static void constructTop(BlockTextureAtlas manager, com.xbuilders.engine.items.block.Block block, PShape shape, int x, int y, int z) {
        //shading.applyTopVertex3Shading(shape, Face.TOP);
        shape.vertex(vertices[blockIndices[24]].x + x, vertices[blockIndices[24]].y + y, vertices[blockIndices[24]].z + z, manager.getTextureIndex(block.texture.TOP).minX, manager.getTextureIndex(block.texture.TOP).minY);
        // if(CALCULATE_AO){
        //shading.applyTopVertex2Shading(shape, Face.TOP);
        // }
        shape.vertex(vertices[blockIndices[25]].x + x, vertices[blockIndices[25]].y + y, vertices[blockIndices[25]].z + z, manager.getTextureIndex(block.texture.TOP).minX, manager.getTextureIndex(block.texture.TOP).maxY);
        // if(CALCULATE_AO){
        //shading.applyTopVertex1Shading(shape, Face.TOP);
        //     }
        shape.vertex(vertices[blockIndices[26]].x + x, vertices[blockIndices[26]].y + y, vertices[blockIndices[26]].z + z, manager.getTextureIndex(block.texture.TOP).maxX, manager.getTextureIndex(block.texture.TOP).maxY);
        // if(CALCULATE_AO){
        //shading.applyTopVertex4Shading(shape, Face.TOP);
        //}
        shape.vertex(vertices[blockIndices[27]].x + x, vertices[blockIndices[27]].y + y, vertices[blockIndices[27]].z + z, manager.getTextureIndex(block.texture.TOP).maxX, manager.getTextureIndex(block.texture.TOP).minY);
        //if(CALCULATE_AO){
        //shading.applyTopVertex3Shading(shape, Face.TOP);
        // }
        shape.vertex(vertices[blockIndices[28]].x + x, vertices[blockIndices[28]].y + y, vertices[blockIndices[28]].z + z, manager.getTextureIndex(block.texture.TOP).minX, manager.getTextureIndex(block.texture.TOP).minY);
        // if(CALCULATE_AO){
        //shading.applyTopVertex1Shading(shape, Face.TOP);
        // }
        shape.vertex(vertices[blockIndices[29]].x + x, vertices[blockIndices[29]].y + y, vertices[blockIndices[29]].z + z, manager.getTextureIndex(block.texture.TOP).maxX, manager.getTextureIndex(block.texture.TOP).maxY);
    }

    private static void constructBottom(BlockTextureAtlas manager, com.xbuilders.engine.items.block.Block block, PShape shape, int x, int y, int z) {
        //shading.applyBottomVertex1Shading(shape, Face.BOTTOM);
        shape.vertex(vertices[blockIndices[30]].x + x, vertices[blockIndices[30]].y + y, vertices[blockIndices[30]].z + z, manager.getTextureIndex(block.texture.BOTTOM).maxX, manager.getTextureIndex(block.texture.BOTTOM).minY);
        // if(CALCULATE_AO){
        //shading.applyBottomVertex2Shading(shape, Face.BOTTOM);
        // }
        shape.vertex(vertices[blockIndices[31]].x + x, vertices[blockIndices[31]].y + y, vertices[blockIndices[31]].z + z, manager.getTextureIndex(block.texture.BOTTOM).minX, manager.getTextureIndex(block.texture.BOTTOM).minY);
        // if(CALCULATE_AO){
        //shading.applyBottomVertex3Shading(shape, Face.BOTTOM);
        //  }
        shape.vertex(vertices[blockIndices[32]].x + x, vertices[blockIndices[32]].y + y, vertices[blockIndices[32]].z + z, manager.getTextureIndex(block.texture.BOTTOM).minX, manager.getTextureIndex(block.texture.BOTTOM).maxY);
        // if(CALCULATE_AO){
        //shading.applyBottomVertex1Shading(shape, Face.BOTTOM);
        // }
        shape.vertex(vertices[blockIndices[33]].x + x, vertices[blockIndices[33]].y + y, vertices[blockIndices[33]].z + z, manager.getTextureIndex(block.texture.BOTTOM).maxX, manager.getTextureIndex(block.texture.BOTTOM).minY);
        // if(CALCULATE_AO){
        //shading.applyBottomVertex3Shading(shape, Face.BOTTOM);
        //  }
        shape.vertex(vertices[blockIndices[34]].x + x, vertices[blockIndices[34]].y + y, vertices[blockIndices[34]].z + z, manager.getTextureIndex(block.texture.BOTTOM).minX, manager.getTextureIndex(block.texture.BOTTOM).maxY);
        //if(CALCULATE_AO){
        //shading.applyBottomVertex4Shading(shape, Face.BOTTOM);
        // }
        shape.vertex(vertices[blockIndices[35]].x + x, vertices[blockIndices[35]].y + y, vertices[blockIndices[35]].z + z, manager.getTextureIndex(block.texture.BOTTOM).maxX, manager.getTextureIndex(block.texture.BOTTOM).maxY);
    }

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        box.setPosAndSize(x, y, z, 1, 1, 1);
        consumer.accept(box);
    }

    @Override
    public void getCursorBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        getCollisionBoxes(consumer, box, block, data, x, y, z);
    }

    public static boolean blockIsBlock(Block b) {
        if (b.getRenderType() == DEFAULT_BLOCK_TYPE_ID) return true;
        return b.type == BlockRenderType.ORIENTABLE_BLOCK;
    }

    private boolean orRule(Block block, Block neighbor) {
//        if (neighbor != null && !block.isOpaque() && (neighbor.isLiquid() || !blockIsBlock(neighbor))) {
//            return true;
//        }
        return false;
    }


    @Override
    public boolean isCubeShape() {
        return true;
    }

}
