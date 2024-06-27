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
import com.xbuilders.game.blockMode.tools.LineTool;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.function.Consumer;

import static com.xbuilders.engine.items.BlockList.DEFAULT_BLOCK_TYPE_ID;

/**
 * @author zipCoder933
 */
public class SlabRenderer extends BlockType {


    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        BlockOrientation orientation = player.cameraBlockOrientation();
        if (player.blockTools.getSelectedTool() instanceof LineTool) {
            if (Math.abs(ray.getHitNormalAsInt().x) == 0
                    && Math.abs(ray.getHitNormalAsInt().z) == 0) {
                orientation.setY((byte) 0);
            } else if (orientation.getY() == 0) {
                orientation.setY((byte) 1);
            }
        } else {
            if (Math.abs(ray.getHitNormalAsInt().x) != 0
                    || Math.abs(ray.getHitNormalAsInt().z) != 0) {
                orientation.setY((byte) 0);
            } else if (orientation.getY() == 0) {
                orientation.setY((byte) 1);
            }
        }
        return orientation;
    }

    //<editor-fold defaultstate="collapsed" desc="Draw floorSlab">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_floorSlab = {
            new Vector3f(1.0f, 1.0f, 0.0f), //0
            new Vector3f(1.0f, 1.0f, 1.0f), //1
            new Vector3f(-0.0f, 1.0f, 1.0f), //2
            new Vector3f(0.0f, 1.0f, -0.0f), //3
            new Vector3f(1.0f, 0.5f, 0.0f), //4
            new Vector3f(1.0f, 0.5f, 1.0f), //5
            new Vector3f(0.0f, 0.5f, -0.0f), //6
            new Vector3f(-0.0f, 0.5f, 1.0f), //7
    };
    static Vector2f[] uv_floorSlab = {
            new Vector2f(1.0f, 1.0f), //0
            new Vector2f(0.0f, 0.0f), //1
            new Vector2f(1.0f, 0.0f), //2
            new Vector2f(0.0f, 1.0f), //3
            new Vector2f(1.0f, 0.0f), //4
            new Vector2f(1.0f, 1.0f), //5
            new Vector2f(1.0f, 0.5f), //6
            new Vector2f(1.0f, 0.0f), //7
            new Vector2f(1.0f, 0.5f), //8
            new Vector2f(0.0f, 0.0f), //9
            new Vector2f(1.0f, 0.0f), //10
            new Vector2f(1.0f, 0.5f), //11
            new Vector2f(0.0f, 0.0f), //12
            new Vector2f(1.0f, 0.0f), //13
            new Vector2f(1.0f, 0.5f), //14
            new Vector2f(0.0f, -0.0f), //15
            new Vector2f(0.0f, 1.0f), //16
            new Vector2f(0.0f, 0.0f), //17
            new Vector2f(0.0f, 0.5f), //18
            new Vector2f(0.0f, 0.5f), //19
            new Vector2f(0.0f, 0.5f), //20
            new Vector2f(-0.0f, 0.5f), //21
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_floorSlab_positiveZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

    private static void make_floorSlab_positiveY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.BOTTOM;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    private static void make_floorSlab_negativeZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE
    }

    private static void make_floorSlab_center_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_floorSlab_positiveX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE
    }

    private static void make_floorSlab_negativeX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Draw ceilingSlab">
    //NOTES:
    //The UV map for this block only exists on the top face.
    //<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_ceilingSlab = {
            new Vector3f(1.0f, 0.5f, 0.0f), //0
            new Vector3f(1.0f, 0.5f, 1.0f), //1
            new Vector3f(-0.0f, 0.5f, 1.0f), //2
            new Vector3f(0.0f, 0.5f, -0.0f), //3
            new Vector3f(1.0f, 0.0f, 0.0f), //4
            new Vector3f(1.0f, 0.0f, 1.0f), //5
            new Vector3f(0.0f, 0.0f, -0.0f), //6
            new Vector3f(-0.0f, 0.0f, 1.0f), //7
    };
    static Vector2f[] uv_ceilingSlab = {
            new Vector2f(1.0f, 1.0f), //0
            new Vector2f(0.0f, 0.0f), //1
            new Vector2f(1.0f, 0.0f), //2
            new Vector2f(0.0f, 1.0f), //3
            new Vector2f(1.0f, 0.0f), //4
            new Vector2f(1.0f, 1.0f), //5
            new Vector2f(1.0f, 0.5f), //6
            new Vector2f(1.0f, 0.0f), //7
            new Vector2f(1.0f, 0.5f), //8
            new Vector2f(0.0f, 0.0f), //9
            new Vector2f(1.0f, 0.0f), //10
            new Vector2f(1.0f, 0.5f), //11
            new Vector2f(0.0f, 0.0f), //12
            new Vector2f(1.0f, 0.0f), //13
            new Vector2f(1.0f, 0.5f), //14
            new Vector2f(0.0f, -0.0f), //15
            new Vector2f(0.0f, 1.0f), //16
            new Vector2f(0.0f, 0.0f), //17
            new Vector2f(0.0f, 0.5f), //18
            new Vector2f(0.0f, 0.5f), //19
            new Vector2f(0.0f, 0.5f), //20
            new Vector2f(-0.0f, 0.5f), //21
    };

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_ceilingSlab_positiveZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

    private static void make_ceilingSlab_negativeX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE
    }

    private static void make_ceilingSlab_negativeY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.BOTTOM;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_ceilingSlab_negativeZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE
    }

    private static void make_ceilingSlab_positiveX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE
    }

    private static void make_ceilingSlab_center_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Draw sideSlab">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_sideSlab = {
            new Vector3f(0.0f, 1.0f, 0.5f), //0
            new Vector3f(1.0f, 1.0f, 0.5f), //1
            new Vector3f(1.0f, -0.0f, 0.5f), //2
            new Vector3f(-0.0f, 0.0f, 0.5f), //3
            new Vector3f(0.0f, 1.0f, -0.0f), //4
            new Vector3f(1.0f, 1.0f, 0.0f), //5
            new Vector3f(0.0f, 0.0f, -0.0f), //6
            new Vector3f(1.0f, -0.0f, 0.0f), //7
    };
    static Vector2f[] uv_sideSlab = {
            new Vector2f(1.0f, 1.0f), //0
            new Vector2f(0.0f, 0.0f), //1
            new Vector2f(1.0f, 0.0f), //2
            new Vector2f(0.0f, 1.0f), //3
            new Vector2f(1.0f, 0.0f), //4
            new Vector2f(1.0f, 1.0f), //5
            new Vector2f(0.5f, 0.0f), //6
            new Vector2f(0.0f, 1.0f), //7
            new Vector2f(0.0f, 0.0f), //8
            new Vector2f(0.5f, 0.0f), //9
            new Vector2f(0.0f, 1.0f), //10
            new Vector2f(0.0f, 0.0f), //11
            new Vector2f(0.5f, 1.0f), //12
            new Vector2f(0.0f, 1.0f), //13
            new Vector2f(0.5f, 0.0f), //14
            new Vector2f(0.0f, 1.0f), //15
            new Vector2f(0.0f, 0.0f), //16
            new Vector2f(0.0f, 0.0f), //17
            new Vector2f(0.5f, 1.0f), //18
            new Vector2f(0.5f, 1.0f), //19
            new Vector2f(0.5f, 0.0f), //20
            new Vector2f(0.5f, 1.0f), //21
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_sideSlab_center_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    private static void make_sideSlab_positiveX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE
    }

    private static void make_sideSlab_negativeY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE
    }

    private static void make_sideSlab_negativeZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.BOTTOM;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE
    }

    private static void make_sideSlab_negativeX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE
    }

    private static void make_sideSlab_positiveY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    @Override
    public void constructBlock(BlockMesh_Base buffers, Block block, BlockData data,
                               Block negativeX, Block positiveX, Block negativeY,
                               Block positiveY, Block negativeZ, Block positiveZ,
                               int x, int y, int z) {

        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);

        if (orientation != null && orientation.getY() > 0) {
            if (StairsRenderer.renderSideSubBlock(block, positiveZ)) {
                make_floorSlab_positiveZ_faces(verts_floorSlab, uv_floorSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, positiveY)) {
                make_floorSlab_positiveY_faces(verts_floorSlab, uv_floorSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeZ)) {
                make_floorSlab_negativeZ_faces(verts_floorSlab, uv_floorSlab, block, buffers, x, y, z);
            }
            make_floorSlab_center_faces(verts_floorSlab, uv_floorSlab, block, buffers, x, y, z);
            if (StairsRenderer.renderSideSubBlock(block, positiveX)) {
                make_floorSlab_positiveX_faces(verts_floorSlab, uv_floorSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeX)) {
                make_floorSlab_negativeX_faces(verts_floorSlab, uv_floorSlab, block, buffers, x, y, z);
            }
        } else if (orientation != null && orientation.getY() < 0) {
            if (StairsRenderer.renderSideSubBlock(block, positiveZ)) {
                make_ceilingSlab_positiveZ_faces(verts_ceilingSlab, uv_ceilingSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeX)) {
                make_ceilingSlab_negativeX_faces(verts_ceilingSlab, uv_ceilingSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeY)) {
                make_ceilingSlab_negativeY_faces(verts_ceilingSlab, uv_ceilingSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeZ)) {
                make_ceilingSlab_negativeZ_faces(verts_ceilingSlab, uv_ceilingSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, positiveX)) {
                make_ceilingSlab_positiveX_faces(verts_ceilingSlab, uv_ceilingSlab, block, buffers, x, y, z);
            }
            make_ceilingSlab_center_faces(verts_ceilingSlab, uv_ceilingSlab, block, buffers, x, y, z);
        } else {
            //<editor-fold defaultstate="collapsed" desc="rotate the object">
            Vector3f[] verts = verts_sideSlab;
            if (orientation != null) {
                verts = rotateVerticiesYAxis(verts_sideSlab, orientation.getXZ());

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
            make_sideSlab_center_faces(verts, uv_sideSlab, block, buffers, x, y, z);

            if (StairsRenderer.renderSideSubBlock(block, positiveY)) {
                make_sideSlab_positiveY_faces(verts, uv_sideSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeY)) {
                make_sideSlab_negativeY_faces(verts, uv_sideSlab, block, buffers, x, y, z);
            }

            if (StairsRenderer.renderSideSubBlock(block, positiveX) || positiveX.type != DEFAULT_BLOCK_TYPE_ID) {
                make_sideSlab_positiveX_faces(verts, uv_sideSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeX) || negativeX.type != DEFAULT_BLOCK_TYPE_ID) {
                make_sideSlab_negativeX_faces(verts, uv_sideSlab, block, buffers, x, y, z);
            }
            if (StairsRenderer.renderSideSubBlock(block, negativeZ) || negativeZ.type != DEFAULT_BLOCK_TYPE_ID) {
                make_sideSlab_negativeZ_faces(verts, uv_sideSlab, block, buffers, x, y, z);
            }
        }
    }

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);

        if (orientation != null) {
            if (orientation.getY() == -1) {
                box.setPosAndSize(x, y, z, 1, 0.5f, 1);
                consumer.accept(box);
            } else if (orientation.getY() == 1) {
                box.setPosAndSize(x, y + 0.5f, z, 1, 0.5f, 1);
                consumer.accept(box);
            } else {
                if (orientation.getXZ() == 0) {
                    box.setPosAndSize(x, y, z, 1, 1, 0.5f);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 1) {
                    box.setPosAndSize(x + 0.5f, y, z, 0.5f, 1, 1);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 2) {
                    box.setPosAndSize(x, y, z + 0.5f, 1, 1, 0.5f);
                    consumer.accept(box);
                } else {
                    box.setPosAndSize(x, y, z, 0.5f, 1, 1);
                    consumer.accept(box);
                }
            }
        }
    }


}
