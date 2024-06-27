/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.items.ItemList;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.rendering.blocks.BlockMesh_Base;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.blockData.BlockData;
import processing.core.PVector;

import java.util.function.Consumer;

/**
 * 1. outline what vertex positions are touching what block neighbor<br>
 * 2. if all neighbors touching that vertex are gone, make the neighbor fall to
 * the floor<br>
 *
 * @author zipCoder933
 */
public class LiquidRenderer extends BlockType {

    @Override
    public boolean isCubeShape() {
        return true;
    }

    final static float FLOOR_LEVEL = 0.8f;

    final static PVector[] vertices = {
            new PVector(0, 0, 0),//0
            new PVector(1, 0, 0),//1
            new PVector(1, 0, 1),//2
            new PVector(0, 0, 1),//3

            //ground
            new PVector(0, 1, 0),//4
            new PVector(1, 1, 0),//5
            new PVector(1, 1, 1),//6
            new PVector(0, 1, 1),//7

            //floor (close to ground)
            new PVector(0, FLOOR_LEVEL, 0),//8
            new PVector(1, FLOOR_LEVEL, 0),//9
            new PVector(1, FLOOR_LEVEL, 1),//10
            new PVector(0, FLOOR_LEVEL, 1),//11
    };

    //top verticies
    int vi0 = 0;//8
    int vi1 = 1;//9
    int vi2 = 2;//10
    int vi3 = 3;//11

    //bottom verticies
    int vi4 = 4;
    int vi5 = 5;
    int vi6 = 6;
    int vi7 = 7;

    private boolean drawFace(Block block) {
        if (block == null) {
            return false;
        }
        BlockType type = ItemList.blocks.getBlockType(block.type);
        return block.isAir() ||
                (!type.isCubeShape() && block.type != BlockRenderType.SPRITE);
    }

    private boolean drawTopFace(Block block) {
        if (block == null) {
            return true;
        }
        BlockType type = ItemList.blocks.getBlockType(block.type);
        return block.isAir() || (!type.isCubeShape());
    }

    @Override
    public void constructBlock(BlockMesh_Base buffers, Block block, BlockData data,
                               Block negativeX, Block positiveX, Block negativeY,
                               Block positiveY, Block negativeZ, Block positiveZ,
                               int x, int y, int z) {

        boolean drawNegativeX = drawFace(negativeX);
        boolean drawNegativeY = drawTopFace(negativeY);
        boolean drawNegativeZ = drawFace(negativeZ);
        boolean drawPositiveX = drawFace(positiveX);
        boolean drawPositiveY = drawFace(positiveY);
        boolean drawPositiveZ = drawFace(positiveZ);

        vi0 = 0;//8
        vi1 = 1;//9
        vi2 = 2;//10
        vi3 = 3;//11
        boolean drawTopPlane = true;

        if (drawNegativeY && !(drawNegativeX && drawNegativeZ && drawPositiveX && drawPositiveZ)) {
            if (drawNegativeX && drawNegativeZ) {
                vi0 = 8;
                drawTopPlane = false;
            }
            if (drawPositiveX && drawNegativeZ) {
                vi1 = 9;
                drawTopPlane = false;
            }
            if (drawPositiveX && drawPositiveZ) {
                vi2 = 10;
                drawTopPlane = false;
            }
            if (drawNegativeX && drawPositiveZ) {
                vi3 = 11;
                drawTopPlane = false;
            }
        }

        if (drawNegativeX) {
            buffers.vertex(vertices[vi3].x + x, vertices[vi3].y + y, vertices[vi3].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi0].x + x, vertices[vi0].y + y, vertices[vi0].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi4].x + x, vertices[vi4].y + y, vertices[vi4].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi3].x + x, vertices[vi3].y + y, vertices[vi3].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi4].x + x, vertices[vi4].y + y, vertices[vi4].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi7].x + x, vertices[vi7].y + y, vertices[vi7].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        }
        if (drawPositiveX) {
            buffers.vertex(vertices[vi1].x + x, vertices[vi1].y + y, vertices[vi1].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi2].x + x, vertices[vi2].y + y, vertices[vi2].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi6].x + x, vertices[vi6].y + y, vertices[vi6].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi1].x + x, vertices[vi1].y + y, vertices[vi1].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi6].x + x, vertices[vi6].y + y, vertices[vi6].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi5].x + x, vertices[vi5].y + y, vertices[vi5].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        }
        if (drawNegativeY) {
            buffers.vertex(vertices[vi2].x + x, vertices[vi2].y + y, vertices[vi2].z + z, getTextureManager().getTextureIndex(block.texture.TOP).minX, getTextureManager().getTextureIndex(block.texture.TOP).minY);
            buffers.vertex(vertices[vi1].x + x, vertices[vi1].y + y, vertices[vi1].z + z, getTextureManager().getTextureIndex(block.texture.TOP).minX, getTextureManager().getTextureIndex(block.texture.TOP).maxY);
            buffers.vertex(vertices[vi0].x + x, vertices[vi0].y + y, vertices[vi0].z + z, getTextureManager().getTextureIndex(block.texture.TOP).maxX, getTextureManager().getTextureIndex(block.texture.TOP).maxY);
            buffers.vertex(vertices[vi3].x + x, vertices[vi3].y + y, vertices[vi3].z + z, getTextureManager().getTextureIndex(block.texture.TOP).maxX, getTextureManager().getTextureIndex(block.texture.TOP).minY);
            buffers.vertex(vertices[vi2].x + x, vertices[vi2].y + y, vertices[vi2].z + z, getTextureManager().getTextureIndex(block.texture.TOP).minX, getTextureManager().getTextureIndex(block.texture.TOP).minY);
            buffers.vertex(vertices[vi0].x + x, vertices[vi0].y + y, vertices[vi0].z + z, getTextureManager().getTextureIndex(block.texture.TOP).maxX, getTextureManager().getTextureIndex(block.texture.TOP).maxY);
            if (drawTopPlane) {
                make_block_negativeY_faces(verts_block, uv_block, block, buffers, x, y, z);
            }
        }

        if (drawPositiveY) {
            buffers.vertex(vertices[vi4].x + x, vertices[vi4].y + y, vertices[vi4].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxX, getTextureManager().getTextureIndex(block.texture.BOTTOM).minY);
            buffers.vertex(vertices[vi5].x + x, vertices[vi5].y + y, vertices[vi5].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).minX, getTextureManager().getTextureIndex(block.texture.BOTTOM).minY);
            buffers.vertex(vertices[vi6].x + x, vertices[vi6].y + y, vertices[vi6].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).minX, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxY);
            buffers.vertex(vertices[vi4].x + x, vertices[vi4].y + y, vertices[vi4].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxX, getTextureManager().getTextureIndex(block.texture.BOTTOM).minY);
            buffers.vertex(vertices[vi6].x + x, vertices[vi6].y + y, vertices[vi6].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).minX, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxY);
            buffers.vertex(vertices[vi7].x + x, vertices[vi7].y + y, vertices[vi7].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxX, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxY);
        }
        if (drawNegativeZ) {
            buffers.vertex(vertices[vi0].x + x, vertices[vi0].y + y, vertices[vi0].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi1].x + x, vertices[vi1].y + y, vertices[vi1].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi5].x + x, vertices[vi5].y + y, vertices[vi5].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi0].x + x, vertices[vi0].y + y, vertices[vi0].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi5].x + x, vertices[vi5].y + y, vertices[vi5].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi4].x + x, vertices[vi4].y + y, vertices[vi4].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        }
        if (drawPositiveZ) {
            buffers.vertex(vertices[vi2].x + x, vertices[vi2].y + y, vertices[vi2].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi3].x + x, vertices[vi3].y + y, vertices[vi3].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi7].x + x, vertices[vi7].y + y, vertices[vi7].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi2].x + x, vertices[vi2].y + y, vertices[vi2].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).minY);
            buffers.vertex(vertices[vi7].x + x, vertices[vi7].y + y, vertices[vi7].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).minX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
            buffers.vertex(vertices[vi6].x + x, vertices[vi6].y + y, vertices[vi6].z + z, getTextureManager().getTextureIndex(block.texture.FRONT).maxX, getTextureManager().getTextureIndex(block.texture.FRONT).maxY);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Liquid underwater face">
    //NOTES:
    //The UV map for this block only exists on the top face.
    //<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_block = {
            new PVector(1.0f, -0.0f, 0.0f), //0
            new PVector(1.0f, -0.0f, 1.0f), //1
            new PVector(0.0f, 0.0f, 0.0f), //2
            new PVector(-0.0f, -0.0f, 1.0f), //3
    };
    static PVector[] uv_block = {
            new PVector(0.0f, 1.0f), //0
            new PVector(0.0f, 0.0f), //1
            new PVector(1.0f, 1.0f), //2
            new PVector(1.0f, 0.0f), //3
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_block_negativeY_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uv2[2].x), getUVTextureCoord_Y(block, uv2[2].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uv2[1].x), getUVTextureCoord_Y(block, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(block, uv2[0].x), getUVTextureCoord_Y(block, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(block, uv2[3].x), getUVTextureCoord_Y(block, uv2[3].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uv2[1].x), getUVTextureCoord_Y(block, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uv2[2].x), getUVTextureCoord_Y(block, uv2[2].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    @Override
    public void getCursorBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        box.setPosAndSize(x, y, z, 1, 1, 1);
        consumer.accept(box);
    }

}


/*if ((shouldRenderSide(block, negativeX) && liquidSpriteRule(block, negativeX)) || orRule(block, negativeX)) {
            shape.vertex(vertices[blockIndices[18]].x + x, vertices[blockIndices[18]].y + y, vertices[blockIndices[18]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[19]].x + x, vertices[blockIndices[19]].y + y, vertices[blockIndices[19]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[20]].x + x, vertices[blockIndices[20]].y + y, vertices[blockIndices[20]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[21]].x + x, vertices[blockIndices[21]].y + y, vertices[blockIndices[21]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[22]].x + x, vertices[blockIndices[22]].y + y, vertices[blockIndices[22]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[23]].x + x, vertices[blockIndices[23]].y + y, vertices[blockIndices[23]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
        }
        if ((shouldRenderSide(block, positiveX) && liquidSpriteRule(block, positiveX)) || orRule(block, positiveX)) {
            shape.vertex(vertices[blockIndices[6]].x + x, vertices[blockIndices[6]].y + y, vertices[blockIndices[6]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[7]].x + x, vertices[blockIndices[7]].y + y, vertices[blockIndices[7]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[8]].x + x, vertices[blockIndices[8]].y + y, vertices[blockIndices[8]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[9]].x + x, vertices[blockIndices[9]].y + y, vertices[blockIndices[9]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[10]].x + x, vertices[blockIndices[10]].y + y, vertices[blockIndices[10]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[11]].x + x, vertices[blockIndices[11]].y + y, vertices[blockIndices[11]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
        }
        if (shouldRenderSide(block, negativeY) || orRule(block, negativeY)) {
            shape.vertex(vertices[blockIndices[24]].x + x, vertices[blockIndices[24]].y + y, vertices[blockIndices[24]].z + z, getTextureManager().getTextureIndex(block.texture.TOP).minX, getTextureManager().getTextureIndex(block.texture.TOP).minY);
            shape.vertex(vertices[blockIndices[25]].x + x, vertices[blockIndices[25]].y + y, vertices[blockIndices[25]].z + z, getTextureManager().getTextureIndex(block.texture.TOP).minX, getTextureManager().getTextureIndex(block.texture.TOP).maxY);
            shape.vertex(vertices[blockIndices[26]].x + x, vertices[blockIndices[26]].y + y, vertices[blockIndices[26]].z + z, getTextureManager().getTextureIndex(block.texture.TOP).maxX, getTextureManager().getTextureIndex(block.texture.TOP).maxY);
            shape.vertex(vertices[blockIndices[27]].x + x, vertices[blockIndices[27]].y + y, vertices[blockIndices[27]].z + z, getTextureManager().getTextureIndex(block.texture.TOP).maxX, getTextureManager().getTextureIndex(block.texture.TOP).minY);
            shape.vertex(vertices[blockIndices[28]].x + x, vertices[blockIndices[28]].y + y, vertices[blockIndices[28]].z + z, getTextureManager().getTextureIndex(block.texture.TOP).minX, getTextureManager().getTextureIndex(block.texture.TOP).minY);
            shape.vertex(vertices[blockIndices[29]].x + x, vertices[blockIndices[29]].y + y, vertices[blockIndices[29]].z + z, getTextureManager().getTextureIndex(block.texture.TOP).maxX, getTextureManager().getTextureIndex(block.texture.TOP).maxY);
        }

        if (shouldRenderSide(block, negativeY) && !negativeY.isOpaque()) {
            make_block_negativeY_faces(verts_block, uv_block, block, shape, x, y, z);
        }

        if ((shouldRenderSide(block, positiveY) && liquidSpriteRule(block, positiveY)) || orRule(block, positiveY)) {
            shape.vertex(vertices[blockIndices[30]].x + x, vertices[blockIndices[30]].y + y, vertices[blockIndices[30]].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxX, getTextureManager().getTextureIndex(block.texture.BOTTOM).minY);
            shape.vertex(vertices[blockIndices[31]].x + x, vertices[blockIndices[31]].y + y, vertices[blockIndices[31]].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).minX, getTextureManager().getTextureIndex(block.texture.BOTTOM).minY);
            shape.vertex(vertices[blockIndices[32]].x + x, vertices[blockIndices[32]].y + y, vertices[blockIndices[32]].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).minX, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxY);
            shape.vertex(vertices[blockIndices[33]].x + x, vertices[blockIndices[33]].y + y, vertices[blockIndices[33]].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxX, getTextureManager().getTextureIndex(block.texture.BOTTOM).minY);
            shape.vertex(vertices[blockIndices[34]].x + x, vertices[blockIndices[34]].y + y, vertices[blockIndices[34]].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).minX, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxY);
            shape.vertex(vertices[blockIndices[35]].x + x, vertices[blockIndices[35]].y + y, vertices[blockIndices[35]].z + z, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxX, getTextureManager().getTextureIndex(block.texture.BOTTOM).maxY);
        }
        if ((shouldRenderSide(block, negativeZ) && liquidSpriteRule(block, negativeZ)) || orRule(block, negativeZ)) {
            shape.vertex(vertices[blockIndices[0]].x + x, vertices[blockIndices[0]].y + y, vertices[blockIndices[0]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[1]].x + x, vertices[blockIndices[1]].y + y, vertices[blockIndices[1]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[2]].x + x, vertices[blockIndices[2]].y + y, vertices[blockIndices[2]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[3]].x + x, vertices[blockIndices[3]].y + y, vertices[blockIndices[3]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[4]].x + x, vertices[blockIndices[4]].y + y, vertices[blockIndices[4]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[5]].x + x, vertices[blockIndices[5]].y + y, vertices[blockIndices[5]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);

        }
        if ((shouldRenderSide(block, positiveZ) && liquidSpriteRule(block, positiveZ)) || orRule(block, positiveZ)) {
            shape.vertex(vertices[blockIndices[12]].x + x, vertices[blockIndices[12]].y + y, vertices[blockIndices[12]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[13]].x + x, vertices[blockIndices[13]].y + y, vertices[blockIndices[13]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[14]].x + x, vertices[blockIndices[14]].y + y, vertices[blockIndices[14]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[15]].x + x, vertices[blockIndices[15]].y + y, vertices[blockIndices[15]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).minY);
            shape.vertex(vertices[blockIndices[16]].x + x, vertices[blockIndices[16]].y + y, vertices[blockIndices[16]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).minX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
            shape.vertex(vertices[blockIndices[17]].x + x, vertices[blockIndices[17]].y + y, vertices[blockIndices[17]].z + z, getTextureManager().getTextureIndex(block.texture.SIDES).maxX, getTextureManager().getTextureIndex(block.texture.SIDES).maxY);
        }*/
//        boolean drawNXPZ = drawFace2(VoxelGame.getWorld().getBlock(x - 1, y, z + 1));
//        boolean drawPXNZ = drawFace2(VoxelGame.getWorld().getBlock(x + 1, y, z - 1));
//        boolean drawNXZ = drawFace2(VoxelGame.getWorld().getBlock(x - 1, y, z - 1));
//        boolean drawPXZ = drawFace2(VoxelGame.getWorld().getBlock(x + 1, y, z + 1));
//            if (drawNXPZ) {
//                vi3 = 11;
//                facesDrawn++;
//            }
//            if (drawPXNZ) {
//                vi1 = 9;
//                facesDrawn++;
//            }
//            if (drawNXZ) {
//                vi0 = 8;
//                facesDrawn++;
//            }
//            if (drawPXZ) {
//                vi2 = 10;
//                facesDrawn++;
//            }
//            if (drawPositiveX) {
//                vi1 = 9;
//                vi2 = 10;
//            }
//            if (drawPositiveZ) {
//                vi3 = 11;
//                vi2 = 10;
//            }
//            if (drawNegativeX) {
//                vi3 = 11;
//                vi0 = 8;
//            }
//            if (drawNegativeZ) {
//                vi0 = 8;
//                vi1 = 9;
//            }
/*
        Vertex points of top face
             +Z
            3   2
        -X         +X
            0   1
             -Z
 */
