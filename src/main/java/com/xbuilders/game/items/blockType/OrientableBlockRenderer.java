/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.world.blockData.BlockData;
import com.xbuilders.engine.world.blockData.BlockOrientation;
import org.joml.Vector3f;
import processing.core.PShape;

import static com.xbuilders.engine.items.block.construction.blockTypes.DefaultBlockType.blockIsBlock;

/**
 * @author zipCoder933
 */
public class OrientableBlockRenderer extends BlockType {

    @Override
    public boolean isCubeShape() {
        return true;
    }

    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        return player.cameraBlockOrientation();
    }

    //<editor-fold defaultstate="collapsed" desc="Draw block">
    //NOTES:
    //The UV map for this block only exists on the top face.
    //<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_block = {
            new Vector3f(1.0f, 1.0f, 1.0f), //0
            new Vector3f(1.0f, 0.0f, 1.0f), //1
            new Vector3f(1.0f, 1.0f, 0.0f), //2
            new Vector3f(1.0f, 0.0f, 0.0f), //3
            new Vector3f(0.0f, 1.0f, 1.0f), //4
            new Vector3f(0.0f, 0.0f, 1.0f), //5
            new Vector3f(0.0f, 1.0f, 0.0f), //6
            new Vector3f(0.0f, 0.0f, 0.0f), //7
    };
    static Vector3f[] uv_block = {
            new Vector3f(0.0f, 0.0f, 0), //0
            new Vector3f(1.0f, 1.0f, 0), //1
            new Vector3f(0.0f, 1.0f, 0), //2
            new Vector3f(0.0f, 0.0f, 0), //3
            new Vector3f(1.0f, 1.0f, 0), //4
            new Vector3f(0.0f, 1.0f, 0), //5
            new Vector3f(0.0f, 1.0f, 0), //6
            new Vector3f(1.0f, 0.0f, 0), //7
            new Vector3f(1.0f, 1.0f, 0), //8
            new Vector3f(0.0f, 0.0f, 0), //9
            new Vector3f(1.0f, 1.0f, 0), //10
            new Vector3f(0.0f, 1.0f, 0), //11
            new Vector3f(0.0f, 0.0f, 0), //12
            new Vector3f(1.0f, 1.0f, 0), //13
            new Vector3f(0.0f, 1.0f, 0), //14
            new Vector3f(1.0f, 0.0f, 0), //15
            new Vector3f(1.0f, 0.0f, 0), //16
            new Vector3f(0.0f, 0.0f, 0), //17
            new Vector3f(1.0f, 0.0f, 0), //18
            new Vector3f(1.0f, 0.0f, 0), //19
    };

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_block_negativeX_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

    private static void make_block_negativeZ_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_block_positiveZ_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE
    }

    private static void make_block_negativeY_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_block_positiveY_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE
    }

    private static void make_block_positiveX_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Draw block_side">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_block_side = {
            new Vector3f(1.0f, 1.0f, 0.0f), //0
            new Vector3f(1.0f, 1.0f, 1.0f), //1
            new Vector3f(1.0f, 0.0f, 0.0f), //2
            new Vector3f(1.0f, 0.0f, 1.0f), //3
            new Vector3f(0.0f, 1.0f, 0.0f), //4
            new Vector3f(0.0f, 1.0f, 1.0f), //5
            new Vector3f(0.0f, 0.0f, 0.0f), //6
            new Vector3f(0.0f, 0.0f, 1.0f), //7
    };
    static Vector3f[] uv_block_side = {
            new Vector3f(0.0f, 0.0f, 0), //0
            new Vector3f(1.0f, 1.0f, 0), //1
            new Vector3f(0.0f, 1.0f, 0), //2
            new Vector3f(0.0f, 0.0f, 0), //3
            new Vector3f(1.0f, 1.0f, 0), //4
            new Vector3f(0.0f, 1.0f, 0), //5
            new Vector3f(0.0f, 1.0f, 0), //6
            new Vector3f(1.0f, 0.0f, 0), //7
            new Vector3f(1.0f, 1.0f, 0), //8
            new Vector3f(0.0f, 0.0f, 0), //9
            new Vector3f(1.0f, 1.0f, 0), //10
            new Vector3f(0.0f, 1.0f, 0), //11
            new Vector3f(1.0f, 0.0f, 0), //12
            new Vector3f(1.0f, 1.0f, 0), //13
            new Vector3f(0.0f, 1.0f, 0), //14
            new Vector3f(1.0f, 0.0f, 0), //15
            new Vector3f(1.0f, 0.0f, 0), //16
            new Vector3f(0.0f, 0.0f, 0), //17
            new Vector3f(1.0f, 0.0f, 0), //18
            new Vector3f(0.0f, 0.0f, 0), //19
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_block_side_negativeX_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

    private static void make_block_side_negativeY_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_block_side_positiveY_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE
    }

    private static void make_block_side_positiveZ_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_block_side_negativeZ_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));//FACE
    }

    private static void make_block_side_positiveX_faces(Vector3f[] verts2, Vector3f[] uv2, Block block, PShape shape, int x, int y, int z, int[] pos) {

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    @Override
    public void constructBlock(PShape buffers, Block block, BlockData data,
                               Block negativeX, Block positiveX, Block negativeY,
                               Block positiveY, Block negativeZ, Block positiveZ,
                               int x, int y, int z) {

        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        if (orientation != null && orientation.getY() != 0) {
            //<editor-fold defaultstate="collapsed" desc="Rotate Block">
            Vector3f[] verts = rotateVerticiesYAxis(verts_block_side, orientation.getXZ());
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
            //</editor-fold>
            drawSideCube(block, verts, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
        } else if (orientation != null) {
            //<editor-fold defaultstate="collapsed" desc="Rotate Block">
            Vector3f[] verts = rotateVerticiesYAxis(verts_block, orientation.getXZ());
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
            //</editor-fold>
            drawRegularCube(block, verts, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
        } else {
            drawRegularCube(block, verts_block, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
        }
    }

    private void drawRegularCube(Block block, Vector3f[] verts, PShape shape,
                                 Block negativeX, Block positiveX, Block negativeY,
                                 Block positiveY, Block negativeZ, Block positiveZ,
                                 int x, int y, int z) {
        if (shouldRenderSide(block, negativeX) || orRule(block, negativeX)) {
            make_block_negativeX_faces(verts, uv_block, block, shape, x, y, z, block.texture.FRONT);
        }
        if (shouldRenderSide(block, positiveX) || orRule(block, positiveX)) {
            make_block_positiveX_faces(verts, uv_block, block, shape, x, y, z, block.texture.BACK);
        }

        if (shouldRenderSide(block, negativeZ) || orRule(block, negativeZ)) {
            make_block_negativeZ_faces(verts, uv_block, block, shape, x, y, z, block.texture.LEFT);
        }
        if (shouldRenderSide(block, positiveZ) || orRule(block, positiveZ)) {
            make_block_positiveZ_faces(verts, uv_block, block, shape, x, y, z, block.texture.RIGHT);
        }

        if (shouldRenderSide(block, negativeY) || orRule(block, negativeY)) {
            make_block_negativeY_faces(verts, uv_block, block, shape, x, y, z, block.texture.TOP);
        }
        if (shouldRenderSide(block, positiveY) || orRule(block, positiveY)) {
            make_block_positiveY_faces(verts, uv_block, block, shape, x, y, z, block.texture.BOTTOM);
        }
    }

    private void drawSideCube(Block block, Vector3f[] verts, PShape shape,
                              Block negativeX, Block positiveX, Block negativeY,
                              Block positiveY, Block negativeZ, Block positiveZ,
                              int x, int y, int z) {

        if (shouldRenderSide(block, positiveZ)) {
            make_block_side_positiveZ_faces(verts, uv_block_side, block, shape, x, y, z, block.texture.TOP);
        }
        if (shouldRenderSide(block, negativeZ)) {
            make_block_side_negativeZ_faces(verts, uv_block_side, block, shape, x, y, z, block.texture.BOTTOM);
        }

        if (shouldRenderSide(block, positiveX)) {
            make_block_side_positiveX_faces(verts, uv_block_side, block, shape, x, y, z, block.texture.FRONT);
        }
        if (shouldRenderSide(block, negativeX)) {
            make_block_side_negativeX_faces(verts, uv_block_side, block, shape, x, y, z, block.texture.BACK);
        }

        if (shouldRenderSide(block, negativeY)) {
            make_block_side_negativeY_faces(verts, uv_block_side, block, shape, x, y, z, block.texture.LEFT);
        }
        if (shouldRenderSide(block, positiveY)) {
            make_block_side_positiveY_faces(verts, uv_block_side, block, shape, x, y, z, block.texture.RIGHT);
        }

    }


    private boolean orRule(Block block, Block neighbor) {
        if (neighbor == null) {
            return true;
        } else if (!block.isOpaque() && (neighbor.isLiquid() || !blockIsBlock(neighbor))) {
            return true;
        }
        return false;
    }


}
