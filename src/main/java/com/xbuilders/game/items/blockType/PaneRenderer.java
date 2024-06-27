/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.rendering.blocks.BlockMesh_Base;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import processing.core.PVector;

import java.util.function.Consumer;

/**
 *
 * @author zipCoder933
 */
public class PaneRenderer extends BlockType {

    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        return player.cameraBlockOrientation();
    }

//<editor-fold defaultstate="collapsed" desc="Draw pane">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_pane = {
        new PVector(0.0f, 1.0f, 0.5625f), //0
        new PVector(0.0f, 0.0f, 0.5625f), //1
        new PVector(1.0f, 1.0f, 0.5625f), //2
        new PVector(1.0f, 0.0f, 0.5625f), //3
        new PVector(0.0f, 1.0f, 0.4375f), //4
        new PVector(0.0f, 0.0f, 0.4375f), //5
        new PVector(1.0f, 1.0f, 0.4375f), //6
        new PVector(1.0f, 0.0f, 0.4375f), //7
    };
    static PVector[] uv_pane = {
        new PVector(1.0f, 1.0f), //0
        new PVector(0.0f, 0.0f), //1
        new PVector(1.0f, 0.0f), //2
        new PVector(0.0625f, 1.0f), //3
        new PVector(-0.0f, 0.0f), //4
        new PVector(0.0625f, 0.0f), //5
        new PVector(1.0f, 1.0f), //6
        new PVector(-0.0f, 0.0f), //7
        new PVector(1.0f, 0.0f), //8
        new PVector(1.0f, 1.0f), //9
        new PVector(0.93750006f, 0.0f), //10
        new PVector(1.0f, 0.0f), //11
        new PVector(1.0f, 0.0625f), //12
        new PVector(0.0f, 0.0f), //13
        new PVector(1.0f, -0.0f), //14
        new PVector(0.0f, 0.93750006f), //15
        new PVector(0.0f, 1.0f), //16
        new PVector(0.0f, 1.0f), //17
        new PVector(0.0f, 1.0f), //18
        new PVector(0.93750006f, 1.0f), //19
        new PVector(0.0f, 0.0625f), //20
        new PVector(1.0f, 0.93750006f), //21
    };

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_pane_negativeX_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE
    }

    private static void make_pane_positiveX_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_pane_positiveY_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE
    }

    private static void make_pane_center_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

    private static void make_pane_negativeY_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));//FACE
    }

//</editor-fold>
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Draw pane_flat">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_pane_flat = {
        new PVector(0.0f, 0.56f, 0.0f), //0
        new PVector(0.0f, 0.56f, 1.0f), //1
        new PVector(1.0f, 0.56f, 0.0f), //2
        new PVector(1.0f, 0.56f, 1.0f), //3
        new PVector(0.0f, 0.44f, 0.0f), //4
        new PVector(0.0f, 0.44f, 1.0f), //5
        new PVector(1.0f, 0.44f, 0.0f), //6
        new PVector(1.0f, 0.44f, 1.0f), //7
    };
    static PVector[] uv_pane_flat = {
        new PVector(1.0f, 1.0f), //0
        new PVector(0.0f, 0.0f), //1
        new PVector(1.0f, 0.0f), //2
        new PVector(0.06f, 1.0f), //3
        new PVector(0.0f, 0.0f), //4
        new PVector(0.06f, 0.0f), //5
        new PVector(1.0f, 1.0f), //6
        new PVector(0.0f, 0.0f), //7
        new PVector(1.0f, 0.0f), //8
        new PVector(1.0f, 0.0f), //9
        new PVector(0.94f, 1.0f), //10
        new PVector(0.94f, 0.0f), //11
        new PVector(1.0f, 0.06f), //12
        new PVector(0.0f, 0.0f), //13
        new PVector(1.0f, 0.0f), //14
        new PVector(0.0f, 0.94f), //15
        new PVector(1.0f, 1.0f), //16
        new PVector(0.0f, 1.0f), //17
        new PVector(0.0f, 1.0f), //18
        new PVector(0.0f, 1.0f), //19
        new PVector(0.0f, 0.06f), //20
        new PVector(1.0f, 0.94f), //21
    };

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_pane_flat_negativeX_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE
    }

    private static void make_pane_flat_positiveX_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_pane_flat_negativeZ_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE
    }

    private static void make_pane_flat_positiveZ_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));//FACE
    }

    private static void make_pane_flat_center_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

//</editor-fold>
//</editor-fold>
    @Override
    public void constructBlock(BlockMesh_Base buffers, Block block, BlockData data, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);

        if (orientation == null || orientation.getY() == 0) {
            makeVerticalPane(block, orientation, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
        } else {
            makeFlatPane(block, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
        }
    }

    private void makeVerticalPane(Block block, BlockOrientation orientation, BlockMesh_Base shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        //<editor-fold defaultstate="collapsed" desc="rotate the object">
        PVector[] verts = verts_pane;
        if (orientation != null) {
            verts = rotateVerticiesYAxis(verts_pane, orientation.getXZ());

            Block positiveX2 = positiveX;
            Block negativeX2 = negativeX;
            Block positiveZ2 = positiveZ;
            Block negativeZ2 = negativeZ;

            switch (orientation.getXZ()) {
                case 1:
                    positiveX = positiveZ2;
                    negativeX = negativeZ2;
                    positiveZ = negativeX2;
                    negativeZ = positiveX2;
                    break;
                case 2:
                    positiveX = negativeX2;
                    negativeX = positiveX2;
                    positiveZ = negativeZ2;
                    negativeZ = positiveZ2;
                    break;
                case 3:
                    positiveX = negativeZ2;
                    negativeX = positiveZ2;
                    positiveZ = positiveX2;
                    negativeZ = negativeX2;
                    break;
            }
        }
//</editor-fold>
        if (shouldRenderSide(block, negativeX)) {
            make_pane_negativeX_faces(verts, uv_pane, block, shape, x, y, z);
        }
        if (shouldRenderSide(block, positiveX)) {
            make_pane_positiveX_faces(verts, uv_pane, block, shape, x, y, z);
        }
        if (shouldRenderSide(block, positiveY)) {
            make_pane_positiveY_faces(verts, uv_pane, block, shape, x, y, z);
        }
        make_pane_center_faces(verts, uv_pane, block, shape, x, y, z);
        if (shouldRenderSide(block, negativeY)) {
            make_pane_negativeY_faces(verts, uv_pane, block, shape, x, y, z);
        }
    }

    private void makeFlatPane(Block block, BlockMesh_Base shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        if (shouldRenderSide(block, negativeX)) {
            make_pane_flat_negativeX_faces(verts_pane_flat, uv_pane_flat, block, shape, x, y, z);
        }
        if (shouldRenderSide(block, positiveX)) {
            make_pane_flat_positiveX_faces(verts_pane_flat, uv_pane_flat, block, shape, x, y, z);
        }
        if (shouldRenderSide(block, negativeZ)) {
            make_pane_flat_negativeZ_faces(verts_pane_flat, uv_pane_flat, block, shape, x, y, z);
        }
        if (shouldRenderSide(block, positiveZ)) {
            make_pane_flat_positiveZ_faces(verts_pane_flat, uv_pane_flat, block, shape, x, y, z);
        }
        make_pane_flat_center_faces(verts_pane_flat, uv_pane_flat, block, shape, x, y, z);

    }

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        if (orientation == null) {
            box.setPosAndSize(x + (ONE_SIXTEENTH * 7), y, z, ONE_SIXTEENTH * 2, 1, 1);
            consumer.accept(box);
        } else {
            if (orientation.getY() == 0) {
                if (orientation.getXZ() == 1 || orientation.getXZ() == 3) {
                    box.setPosAndSize(x + (ONE_SIXTEENTH * 7), y, z, ONE_SIXTEENTH * 2, 1, 1);
                    consumer.accept(box);
                } else {
                    box.setPosAndSize(x, y, z + (ONE_SIXTEENTH * 7), 1, 1, ONE_SIXTEENTH * 2);
                    consumer.accept(box);
                }
            } else {
                box.setPosAndSize(x, y + (ONE_SIXTEENTH * 7), z, 1, ONE_SIXTEENTH * 2, 1);
                consumer.accept(box);
            }
        }
    }

    @Override
    public void getCursorBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        if (orientation == null) {
            box.setPosAndSize(x + (ONE_SIXTEENTH * 7), y, z, ONE_SIXTEENTH * 2, 1, 1);
            consumer.accept(box);
        } else {
            if (orientation.getY() == 0) {
                if (orientation.getXZ() == 1 || orientation.getXZ() == 3) {
                    box.setPosAndSize(x + (ONE_SIXTEENTH * 7), y, z, ONE_SIXTEENTH * 2, 1, 1);
                    consumer.accept(box);
                } else {
                    box.setPosAndSize(x, y, z + (ONE_SIXTEENTH * 7), 1, 1, ONE_SIXTEENTH * 2);
                    consumer.accept(box);
                }
            } else {
                box.setPosAndSize(x, y + (ONE_SIXTEENTH * 7), z, 1, ONE_SIXTEENTH * 2, 1);
                consumer.accept(box);
            }
        }
    }

}
