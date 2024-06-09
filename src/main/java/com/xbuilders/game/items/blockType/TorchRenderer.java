/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import processing.core.PShape;
import processing.core.PVector;

import java.util.function.Consumer;

/**
 * @author zipCoder933
 */
public class TorchRenderer extends BlockType {

    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        return player.cameraBlockOrientation();
    }

    private boolean sideIsSolid(Block block) {
        return block != null && block.isSolid();
    }

    @Override
    public void constructBlock(PShape buffers, Block block, BlockData data, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);

        if (orientation != null && orientation.getXZ() == 2 && sideIsSolid(positiveZ)) {
            if (positiveZ.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 2);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 2);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (orientation != null && orientation.getXZ() == 3 && sideIsSolid(negativeX)) {
            if (negativeX.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 3);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 3);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (orientation != null && orientation.getXZ() == 0 && sideIsSolid(negativeZ)) {
            if (negativeZ.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 0);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 0);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (orientation != null && orientation.getXZ() == 1 && sideIsSolid(positiveX)) {
            if (positiveX.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 1);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 1);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (sideIsSolid(positiveY)) {
            make_torch_center_faces(verts_torch, uv_torch, block, buffers, x, y, z);
        } else if (sideIsSolid(positiveZ)) {
            if (positiveZ.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 2);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 2);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (sideIsSolid(negativeX)) {
            if (negativeX.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 3);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 3);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (sideIsSolid(negativeZ)) {
            if (negativeZ.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 0);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 0);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else if (sideIsSolid(positiveX)) {
            if (positiveX.getRenderType() == BlockRenderType.FENCE) {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side_fence, 1);
                make_torch_side_fence_center_faces(verts2, uv_torch_side_fence, block, buffers, x, y, z);
            } else {
                PVector[] verts2 = verts2 = rotateVerticiesYAxis(verts_torch_side, 1);
                make_torch_side_center_faces(verts2, uv_torch_side, block, buffers, x, y, z);
            }
        } else {
            make_torch_center_faces(verts_torch, uv_torch, block, buffers, x, y, z);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Draw torch_side">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_torch_side = {
            new PVector(0.4375f, 0.896686f, -0.078452f), //0
            new PVector(0.5625f, 0.896686f, -0.078452f), //1
            new PVector(0.5625f, 0.9850739f, 0.009936f), //2
            new PVector(0.4375f, 0.9850739f, 0.009936f), //3
            new PVector(0.4375f, 0.454744f, 0.36349f), //4
            new PVector(0.5625f, 0.454744f, 0.36349f), //5
            new PVector(0.4375f, 0.543132f, 0.45187798f), //6
            new PVector(0.5625f, 0.543132f, 0.45187798f), //7
            new PVector(0.588388f, -0.49374396f, 0.454123f), //8
            new PVector(0.588388f, 0.568756f, 0.454123f), //9
            new PVector(0.41161197f, -0.49374396f, 0.277346f), //10
            new PVector(0.41161197f, 0.568756f, 0.277346f), //11
            new PVector(0.41161197f, -0.49374396f, 0.277346f), //12
            new PVector(0.41161197f, 0.568756f, 0.277346f), //13
            new PVector(0.588388f, -0.49374396f, 0.454123f), //14
            new PVector(0.588388f, 0.568756f, 0.454123f), //15
            new PVector(0.41161197f, -0.49374396f, 0.454123f), //16
            new PVector(0.41161197f, 0.568756f, 0.454123f), //17
            new PVector(0.588389f, -0.49374396f, 0.277347f), //18
            new PVector(0.588389f, 0.568756f, 0.277346f), //19
            new PVector(0.588388f, -0.49374396f, 0.277346f), //20
            new PVector(0.588389f, 0.568756f, 0.277346f), //21
            new PVector(0.41161197f, -0.49374396f, 0.454123f), //22
            new PVector(0.41161197f, 0.568756f, 0.454123f), //23
    };
    static PVector[] uv_torch_side = {
            new PVector(0.5625f, 0.385548f), //0
            new PVector(0.4375f, 0.503195f), //1
            new PVector(0.4375f, 0.385548f), //2
            new PVector(0.5625f, 0.97378397f), //3
            new PVector(0.4375f, 0.97378397f), //4
            new PVector(0.5625f, 0.97378397f), //5
            new PVector(0.5625f, 0.385548f), //6
            new PVector(0.4375f, 0.97378397f), //7
            new PVector(0.25f, 1.0f), //8
            new PVector(0.0f, 0.0f), //9
            new PVector(0.25f, 0.0f), //10
            new PVector(0.25f, 1.0f), //11
            new PVector(0.0f, 0.0f), //12
            new PVector(0.25f, 0.0f), //13
            new PVector(0.25f, 0.0f), //14
            new PVector(0.0f, 1.0f), //15
            new PVector(0.0f, 0.0f), //16
            new PVector(0.25f, 1.0f), //17
            new PVector(0.0f, 0.0f), //18
            new PVector(0.25f, 0.0f), //19
            new PVector(0.5625f, 0.503195f), //20
            new PVector(0.4375f, 0.385548f), //21
            new PVector(0.0f, 1.0f), //22
            new PVector(0.0f, 1.0f), //23
            new PVector(0.25f, 1.0f), //24
            new PVector(0.0f, 1.0f), //25
    };

    //</editor-fold>
    public boolean constructBlock_torch_side(Block block, PShape shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        make_torch_side_center_faces(verts_torch_side, uv_torch_side, block, shape, x, y, z);

        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_torch_side_center_faces(PVector[] verts2, PVector[] uv2, Block block, PShape shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Draw torch">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_torch = {
            new PVector(0.5625f, 1.0f, 0.4375f), //0
            new PVector(0.5625f, 1.0f, 0.5625f), //1
            new PVector(0.4375f, 1.0f, 0.5625f), //2
            new PVector(0.4375f, 1.0f, 0.4375f), //3
            new PVector(0.5625f, 0.375f, 0.4375f), //4
            new PVector(0.5625f, 0.375f, 0.5625f), //5
            new PVector(0.4375f, 0.375f, 0.4375f), //6
            new PVector(0.4375f, 0.375f, 0.5625f), //7
            new PVector(0.41563198f, -0.59375f, 0.588388f), //8
            new PVector(0.41563198f, 0.46875003f, 0.588388f), //9
            new PVector(0.592409f, -0.59375f, 0.41161197f), //10
            new PVector(0.592409f, 0.46875003f, 0.41161197f), //11
            new PVector(0.592409f, -0.59375f, 0.41161197f), //12
            new PVector(0.592409f, 0.46875003f, 0.41161197f), //13
            new PVector(0.41563198f, -0.59375f, 0.588388f), //14
            new PVector(0.41563198f, 0.46875003f, 0.588388f), //15
            new PVector(0.41563198f, -0.59375f, 0.41161197f), //16
            new PVector(0.41563198f, 0.46875003f, 0.41161197f), //17
            new PVector(0.592409f, -0.59375f, 0.588388f), //18
            new PVector(0.592409f, 0.46875003f, 0.588388f), //19
            new PVector(0.592409f, -0.59375f, 0.588388f), //20
            new PVector(0.592409f, 0.46875003f, 0.588388f), //21
            new PVector(0.41563198f, -0.59375f, 0.41161197f), //22
            new PVector(0.41563198f, 0.46875003f, 0.41161197f), //23
    };
    static PVector[] uv_torch = {
            new PVector(0.5625f, 0.385548f), //0
            new PVector(0.4375f, 0.503195f), //1
            new PVector(0.4375f, 0.385548f), //2
            new PVector(0.5625f, 0.97378397f), //3
            new PVector(0.4375f, 0.97378397f), //4
            new PVector(0.5625f, 0.97378397f), //5
            new PVector(0.5625f, 0.385548f), //6
            new PVector(0.4375f, 0.97378397f), //7
            new PVector(0.25f, 1.0f), //8
            new PVector(0.0f, 0.0f), //9
            new PVector(0.25f, 0.0f), //10
            new PVector(0.25f, 0.0f), //11
            new PVector(0.0f, 1.0f), //12
            new PVector(0.0f, 0.0f), //13
            new PVector(0.25f, 1.0f), //14
            new PVector(0.0f, 0.0f), //15
            new PVector(0.25f, 0.0f), //16
            new PVector(0.25f, 1.0f), //17
            new PVector(0.0f, 0.0f), //18
            new PVector(0.25f, 0.0f), //19
            new PVector(0.5625f, 0.503195f), //20
            new PVector(0.4375f, 0.385548f), //21
            new PVector(0.0f, 1.0f), //22
            new PVector(0.25f, 1.0f), //23
            new PVector(0.0f, 1.0f), //24
            new PVector(0.0f, 1.0f), //25
    };

    //</editor-fold>
    public boolean constructBlock_torch(Block block, PShape shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        make_torch_center_faces(verts_torch, uv_torch, block, shape, x, y, z);

        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_torch_center_faces(PVector[] verts2, PVector[] uv2, Block block, PShape shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Draw torch_side_fence">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_torch_side_fence = {
            new PVector(0.4375f, 0.896686f, -0.45345196f), //0
            new PVector(0.5625f, 0.896686f, -0.45345196f), //1
            new PVector(0.5625f, 0.9850739f, -0.365064f), //2
            new PVector(0.4375f, 0.9850739f, -0.365064f), //3
            new PVector(0.4375f, 0.454744f, -0.01151f), //4
            new PVector(0.5625f, 0.454744f, -0.01151f), //5
            new PVector(0.4375f, 0.543132f, 0.076878f), //6
            new PVector(0.5625f, 0.543132f, 0.076878f), //7
            new PVector(0.588388f, -0.49374396f, 0.079123f), //8
            new PVector(0.588388f, 0.568756f, 0.079123f), //9
            new PVector(0.41161197f, -0.49374396f, -0.097654f), //10
            new PVector(0.41161197f, 0.568756f, -0.097654f), //11
            new PVector(0.41161197f, -0.49374396f, -0.097654f), //12
            new PVector(0.41161197f, 0.568756f, -0.097654f), //13
            new PVector(0.588388f, -0.49374396f, 0.079123f), //14
            new PVector(0.588388f, 0.568756f, 0.079123f), //15
            new PVector(0.41161197f, -0.49374396f, 0.079123f), //16
            new PVector(0.41161197f, 0.568756f, 0.079123f), //17
            new PVector(0.588389f, -0.49374396f, -0.097653f), //18
            new PVector(0.588389f, 0.568756f, -0.097653f), //19
            new PVector(0.588388f, -0.49374396f, -0.097654f), //20
            new PVector(0.588389f, 0.568756f, -0.097654f), //21
            new PVector(0.41161197f, -0.49374396f, 0.079123f), //22
            new PVector(0.41161197f, 0.568756f, 0.079123f), //23
    };
    static PVector[] uv_torch_side_fence = {
            new PVector(0.5625f, 0.385548f), //0
            new PVector(0.4375f, 0.503195f), //1
            new PVector(0.4375f, 0.385548f), //2
            new PVector(0.5625f, 0.97378397f), //3
            new PVector(0.4375f, 0.97378397f), //4
            new PVector(0.5625f, 0.97378397f), //5
            new PVector(0.5625f, 0.385548f), //6
            new PVector(0.4375f, 0.97378397f), //7
            new PVector(0.25f, 1.0f), //8
            new PVector(0.0f, 0.0f), //9
            new PVector(0.25f, 0.0f), //10
            new PVector(0.25f, 1.0f), //11
            new PVector(0.0f, 0.0f), //12
            new PVector(0.25f, 0.0f), //13
            new PVector(0.25f, 0.0f), //14
            new PVector(0.0f, 1.0f), //15
            new PVector(0.0f, 0.0f), //16
            new PVector(0.25f, 1.0f), //17
            new PVector(0.0f, 0.0f), //18
            new PVector(0.25f, 0.0f), //19
            new PVector(0.5625f, 0.503195f), //20
            new PVector(0.4375f, 0.385548f), //21
            new PVector(0.0f, 1.0f), //22
            new PVector(0.0f, 1.0f), //23
            new PVector(0.25f, 1.0f), //24
            new PVector(0.0f, 1.0f), //25
    };

    //</editor-fold>
    public boolean constructBlock_torch_side_fence(Block block, PShape shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        make_torch_side_fence_center_faces(verts_torch_side_fence, uv_torch_side_fence, block, shape, x, y, z);

        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_torch_side_fence_center_faces(PVector[] verts2, PVector[] uv2, Block block, PShape shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE
    }

    private void side0(Consumer<AABB> consumer, AABB box, int x, int y, int z) {
        box.setPosAndSize(x + ONE_SIXTEENTH * 6.5f, y, z, ONE_SIXTEENTH * 3, 1, ONE_SIXTEENTH * 8);
        consumer.accept(box);
    }

    private void side1(Consumer<AABB> consumer, AABB box, int x, int y, int z) {
        box.setPosAndSize(x + ONE_SIXTEENTH * 8f, y, z + ONE_SIXTEENTH * 6.5f, ONE_SIXTEENTH * 8, 1, ONE_SIXTEENTH * 3);
        consumer.accept(box);
    }

    private void side2(Consumer<AABB> consumer, AABB box, int x, int y, int z) {
        box.setPosAndSize(x + ONE_SIXTEENTH * 6.5f, y, z + ONE_SIXTEENTH * 8, ONE_SIXTEENTH * 3, 1, ONE_SIXTEENTH * 8);
        consumer.accept(box);
    }

    private void side3(Consumer<AABB> consumer, AABB box, int x, int y, int z) {
        box.setPosAndSize(x, y, z + ONE_SIXTEENTH * 6.5f, ONE_SIXTEENTH * 8, 1, ONE_SIXTEENTH * 3);
        consumer.accept(box);
    }

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
    }

    @Override
    public void getCursorBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        if (orientation == null) {
            box.setPosAndSize(x, y, z, 1, 1, 1);
            consumer.accept(box);
        } else {
            if (orientation.getXZ() == 2 && sideIsSolid(VoxelGame.getWorld().getBlock(x, y, z + 1))) {
                side2(consumer, box, x, y, z);
            } else if (orientation.getXZ() == 3 && sideIsSolid(VoxelGame.getWorld().getBlock(x - 1, y, z))) {
                side3(consumer, box, x, y, z);
            } else if (orientation.getXZ() == 0 && sideIsSolid(VoxelGame.getWorld().getBlock(x, y, z - 1))) {
                side0(consumer, box, x, y, z);
            } else if (orientation.getXZ() == 1 && sideIsSolid(VoxelGame.getWorld().getBlock(x + 1, y, z))) {
                side1(consumer, box, x, y, z);
            } else if (VoxelGame.getWorld().getBlock(x, y + 1, z).isSolid()) {
                box.setPosAndSize(x + ONE_SIXTEENTH * 6, y, z + ONE_SIXTEENTH * 6, ONE_SIXTEENTH * 4, 1, ONE_SIXTEENTH * 4);
                consumer.accept(box);
            } else if (sideIsSolid(VoxelGame.getWorld().getBlock(x, y, z + 1))) {
                side2(consumer, box, x, y, z);
            } else if (sideIsSolid(VoxelGame.getWorld().getBlock(x - 1, y, z))) {
                side3(consumer, box, x, y, z);
            } else if (sideIsSolid(VoxelGame.getWorld().getBlock(x, y, z - 1))) {
                side0(consumer, box, x, y, z);
            } else if (sideIsSolid(VoxelGame.getWorld().getBlock(x + 1, y, z))) {
                side1(consumer, box, x, y, z);
            }
        }
    }

}
