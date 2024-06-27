/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.items.block.construction.blockTypes.DefaultBlockType;
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

/**
 * @author zipCoder933
 */
public class StairsRenderer extends BlockType {

    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        BlockOrientation orientation = player.cameraBlockOrientation();
        if (player.blockTools.getSelectedTool() instanceof LineTool) {
            System.out.println("LineSet");
            if (Math.abs(ray.getHitNormalAsInt().x) == 0
                    && Math.abs(ray.getHitNormalAsInt().z) == 0) {
                orientation.setY((byte) 3);
            }
        } else if (orientation.getY() == 0
                && (Math.abs(ray.getHitNormalAsInt().x) != 0
                || Math.abs(ray.getHitNormalAsInt().z) != 0)) {
            orientation.setY((byte) 3);
        }
        return orientation;
    }

    @Override
    public void constructBlock(BlockMesh_Base buffers, Block block, BlockData data, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);

        if (orientation == null) {
            makeRegularStairs(block, data, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
        } else {
            if (orientation.getY() == 3) {
                makeSideStairs(block, data, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
            } else if (orientation.getY() >= 0) {
                makeRegularStairs(block, data, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
            } else {
                makeUpsideDownStairs(block, data, buffers, negativeX, positiveX, negativeY, positiveY, negativeZ, positiveZ, x, y, z);
            }
        }
    }

    public void makeRegularStairs(Block block, BlockData data, BlockMesh_Base shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        //<editor-fold defaultstate="collapsed" desc="Rotate the object">
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        Vector3f[] verts2 = verts;
        if (orientation != null) {
            verts2 = rotateVerticiesYAxis(verts, orientation.getXZ());

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

        if (renderSideSubBlock(block, positiveX)) {
            make_positiveX_faces(verts2, block, shape, x, y, z);
        }
        make_center_faces(verts2, block, shape, x, y, z);
        if (renderSideSubBlock(block, positiveY)) {
            make_positiveY_faces(verts2, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeY)) {
            make_negativeY_faces(verts2, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, positiveZ)) {
            make_positiveZ_faces(verts2, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeX)) {
            make_negativeX_faces(verts2, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeZ)) {
            make_negativeZ_faces(verts2, block, shape, x, y, z);
        }
    }

    public void makeUpsideDownStairs(Block block, BlockData data, BlockMesh_Base shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        //<editor-fold defaultstate="collapsed" desc="Rotate the object">
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        Vector3f[] verts2 = verts_stairs_upside_down;
        if (orientation != null) {
            verts2 = rotateVerticiesYAxis(verts_stairs_upside_down, orientation.getXZ());

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
        if (renderSideSubBlock(block, positiveZ)) {
            make_stairs_upside_down_positiveZ_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeY)) {
            make_stairs_upside_down_negativeY_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        }
        make_stairs_upside_down_center_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        if (renderSideSubBlock(block, positiveY)) {
            make_stairs_upside_down_positiveY_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeX)) {
            make_stairs_upside_down_negativeX_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, positiveX)) {
            make_stairs_upside_down_positiveX_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeZ)) {
            make_stairs_upside_down_negativeZ_faces(verts2, uv_stairs_upside_down, block, shape, x, y, z);
        }
    }

    public void makeSideStairs(Block block, BlockData data, BlockMesh_Base shape, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        //<editor-fold defaultstate="collapsed" desc="Rotate the object">
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);
        Vector3f[] verts2 = verts_stairs_side;
        if (orientation != null) {
            verts2 = rotateVerticiesYAxis(verts_stairs_side, orientation.getXZ());

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
        if (renderSideSubBlock(block, positiveX)) {
            make_stairs_side_positiveX_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeZ)) {
            make_stairs_side_negativeZ_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        }
        make_stairs_side_center_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        if (renderSideSubBlock(block, positiveZ)) {
            make_stairs_side_positiveZ_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeY)) {
            make_stairs_side_negativeY_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, positiveY)) {
            make_stairs_side_positiveY_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        }
        if (renderSideSubBlock(block, negativeX)) {
            make_stairs_side_negativeX_faces(verts2, uv_stairs_side, block, shape, x, y, z);
        }
    }

    public static boolean renderSideSubBlock(Block thisBlock, Block neighbor) {
        if (neighbor == null) {
            return true;
        } else if (!neighbor.isOpaque()
                || !DefaultBlockType.blockIsBlock(neighbor)) {
            return true;
        }
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Draw Mesh">
    //NOTES:
    //The UV map for this block only exists on the top face.
    //<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts = {
            new Vector3f(-0.0f, 1.0f, 1.0f), //0
            new Vector3f(1.0f, 1.0f, 1.0f), //1
            new Vector3f(0.0f, 1.0f, -0.0f), //2
            new Vector3f(0.0f, -0.0f, -0.0f), //3
            new Vector3f(1.0f, 1.0f, 0.0f), //4
            new Vector3f(1.0f, -0.0f, 0.0f), //5
            new Vector3f(-0.0f, 0.5f, 1.0f), //6
            new Vector3f(1.0f, 0.5f, 1.0f), //7
            new Vector3f(1.0f, -0.0f, 0.5f), //8
            new Vector3f(-0.0f, -0.0f, 0.5f), //9
            new Vector3f(-0.0f, 0.5f, 0.5f), //10
            new Vector3f(1.0f, 0.5f, 0.5f), //11
    };
    static Vector2f[] uvVerts = {
            new Vector2f(0.5f, 0.0f), //0
            new Vector2f(1.0f, 1.0f), //1
            new Vector2f(0.5f, 1.0f), //2
            new Vector2f(1.0f, 0.5f), //3
            new Vector2f(0.0f, 1.0f), //4
            new Vector2f(0.0f, 0.5f), //5
            new Vector2f(0.0f, 0.5f), //6
            new Vector2f(1.0f, 0.0f), //7
            new Vector2f(1.0f, 0.5f), //8
            new Vector2f(0.0f, 0.0f), //9
            new Vector2f(1.0f, 1.0f), //10
            new Vector2f(1.0f, 0.0f), //11
            new Vector2f(0.0f, 1.0f), //12
            new Vector2f(0.5f, 0.0f), //13
            new Vector2f(0.5f, 1.0f), //14
            new Vector2f(1.0f, 0.0f), //15
            new Vector2f(0.5f, 0.5f), //16
            new Vector2f(1.0f, 1.0f), //17
            new Vector2f(0.0f, 0.0f), //18
            new Vector2f(0.5f, 0.5f), //19
            new Vector2f(0.0f, 0.0f), //20
            new Vector2f(1.0f, 0.0f), //21
            new Vector2f(1.0f, 1.0f), //22
            new Vector2f(0.0f, 1.0f), //23
            new Vector2f(0.0f, 0.0f), //24
            new Vector2f(0.0f, 1.0f), //25
            new Vector2f(0.5f, 1.0f), //26
            new Vector2f(0.0f, 0.5f), //27
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_positiveX_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(block, uvVerts[1].x), getUVTextureCoord_Y(block, uvVerts[1].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(block, uvVerts[16].x), getUVTextureCoord_Y(block, uvVerts[16].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(block, uvVerts[15].x), getUVTextureCoord_Y(block, uvVerts[15].y));//FACE

        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(block, uvVerts[16].x), getUVTextureCoord_Y(block, uvVerts[16].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(block, uvVerts[18].x), getUVTextureCoord_Y(block, uvVerts[18].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(block, uvVerts[15].x), getUVTextureCoord_Y(block, uvVerts[15].y));//FACE

        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(block, uvVerts[16].x), getUVTextureCoord_Y(block, uvVerts[16].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(block, uvVerts[6].x), getUVTextureCoord_Y(block, uvVerts[6].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(block, uvVerts[18].x), getUVTextureCoord_Y(block, uvVerts[18].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(block, uvVerts[1].x), getUVTextureCoord_Y(block, uvVerts[1].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(block, uvVerts[2].x), getUVTextureCoord_Y(block, uvVerts[2].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(block, uvVerts[16].x), getUVTextureCoord_Y(block, uvVerts[16].y));//FACE
    }

    private static void make_center_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(block, uvVerts[5].x), getUVTextureCoord_Y(block, uvVerts[5].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(block, uvVerts[4].x), getUVTextureCoord_Y(block, uvVerts[4].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[3].x), getUVTextureCoord_Y(block, uvVerts[3].y));//FACE

        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(block, uvVerts[14].x), getUVTextureCoord_Y(block, uvVerts[14].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[13].x), getUVTextureCoord_Y(block, uvVerts[13].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(block, uvVerts[12].x), getUVTextureCoord_Y(block, uvVerts[12].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(block, uvVerts[4].x), getUVTextureCoord_Y(block, uvVerts[4].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(block, uvVerts[22].x), getUVTextureCoord_Y(block, uvVerts[22].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[3].x), getUVTextureCoord_Y(block, uvVerts[3].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[13].x), getUVTextureCoord_Y(block, uvVerts[13].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(block, uvVerts[24].x), getUVTextureCoord_Y(block, uvVerts[24].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(block, uvVerts[12].x), getUVTextureCoord_Y(block, uvVerts[12].y));//FACE
    }

    private static void make_positiveY_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(block, uvVerts[15].x), getUVTextureCoord_Y(block, uvVerts[15].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(block, uvVerts[18].x), getUVTextureCoord_Y(block, uvVerts[18].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(block, uvVerts[17].x), getUVTextureCoord_Y(block, uvVerts[17].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(block, uvVerts[18].x), getUVTextureCoord_Y(block, uvVerts[18].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uvVerts[25].x), getUVTextureCoord_Y(block, uvVerts[25].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(block, uvVerts[17].x), getUVTextureCoord_Y(block, uvVerts[17].y));//FACE
    }

    private static void make_negativeY_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(block, uvVerts[2].x), getUVTextureCoord_Y(block, uvVerts[2].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(block, uvVerts[1].x), getUVTextureCoord_Y(block, uvVerts[1].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(block, uvVerts[0].x), getUVTextureCoord_Y(block, uvVerts[0].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(block, uvVerts[1].x), getUVTextureCoord_Y(block, uvVerts[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uvVerts[21].x), getUVTextureCoord_Y(block, uvVerts[21].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(block, uvVerts[0].x), getUVTextureCoord_Y(block, uvVerts[0].y));//FACE
    }

    private static void make_positiveZ_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(block, uvVerts[8].x), getUVTextureCoord_Y(block, uvVerts[8].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uvVerts[7].x), getUVTextureCoord_Y(block, uvVerts[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(block, uvVerts[6].x), getUVTextureCoord_Y(block, uvVerts[6].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uvVerts[7].x), getUVTextureCoord_Y(block, uvVerts[7].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(block, uvVerts[18].x), getUVTextureCoord_Y(block, uvVerts[18].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(block, uvVerts[6].x), getUVTextureCoord_Y(block, uvVerts[6].y));//FACE
    }

    private static void make_negativeX_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uvVerts[20].x), getUVTextureCoord_Y(block, uvVerts[20].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[19].x), getUVTextureCoord_Y(block, uvVerts[19].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(block, uvVerts[11].x), getUVTextureCoord_Y(block, uvVerts[11].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[19].x), getUVTextureCoord_Y(block, uvVerts[19].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uvVerts[10].x), getUVTextureCoord_Y(block, uvVerts[10].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(block, uvVerts[11].x), getUVTextureCoord_Y(block, uvVerts[11].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[19].x), getUVTextureCoord_Y(block, uvVerts[19].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(block, uvVerts[26].x), getUVTextureCoord_Y(block, uvVerts[26].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uvVerts[10].x), getUVTextureCoord_Y(block, uvVerts[10].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(block, uvVerts[20].x), getUVTextureCoord_Y(block, uvVerts[20].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(block, uvVerts[27].x), getUVTextureCoord_Y(block, uvVerts[27].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(block, uvVerts[19].x), getUVTextureCoord_Y(block, uvVerts[19].y));//FACE
    }

    private static void make_negativeZ_faces(Vector3f[] verts2, Block block, BlockMesh_Base shape, int x, int y, int z) {

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(block, uvVerts[11].x), getUVTextureCoord_Y(block, uvVerts[11].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uvVerts[10].x), getUVTextureCoord_Y(block, uvVerts[10].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(block, uvVerts[9].x), getUVTextureCoord_Y(block, uvVerts[9].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(block, uvVerts[10].x), getUVTextureCoord_Y(block, uvVerts[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(block, uvVerts[23].x), getUVTextureCoord_Y(block, uvVerts[23].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(block, uvVerts[9].x), getUVTextureCoord_Y(block, uvVerts[9].y));//FACE
    }

    //</editor-fold>
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Draw stairs_upside_down">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_stairs_upside_down = {
            new Vector3f(0.0f, 0.5f, 1.0f), //0
            new Vector3f(0.0f, 0.0f, 1.0f), //1
            new Vector3f(1.0f, 0.5f, 1.0f), //2
            new Vector3f(1.0f, 0.0f, 1.0f), //3
            new Vector3f(0.0f, 0.5f, 0.0f), //4
            new Vector3f(0.0f, 0.0f, 0.0f), //5
            new Vector3f(1.0f, 0.5f, 0.0f), //6
            new Vector3f(1.0f, 0.0f, 0.0f), //7
            new Vector3f(0.0f, 1.0f, 0.5f), //8
            new Vector3f(0.0f, 0.5f, 0.5f), //9
            new Vector3f(1.0f, 1.0f, 0.5f), //10
            new Vector3f(1.0f, 0.5f, 0.5f), //11
            new Vector3f(0.0f, 1.0f, 0.0f), //12
            new Vector3f(1.0f, 1.0f, 0.0f), //13
    };
    static Vector2f[] uv_stairs_upside_down = {
            new Vector2f(0.0f, 0.0f), //0
            new Vector2f(1.0f, 0.5f), //1
            new Vector2f(0.0f, 0.5f), //2
            new Vector2f(0.0f, 0.0f), //3
            new Vector2f(1.0f, 0.5f), //4
            new Vector2f(0.0f, 0.5f), //5
            new Vector2f(1.0f, 0.5f), //6
            new Vector2f(0.0f, 0.0f), //7
            new Vector2f(1.0f, 0.0f), //8
            new Vector2f(1.0f, 0.5f), //9
            new Vector2f(0.0f, 0.0f), //10
            new Vector2f(1.0f, 0.0f), //11
            new Vector2f(1.0f, 1.0f), //12
            new Vector2f(1.0f, 0.0f), //13
            new Vector2f(1.0f, 1.0f), //14
            new Vector2f(0.0f, 0.0f), //15
            new Vector2f(1.0f, 0.0f), //16
            new Vector2f(0.0f, 0.5f), //17
            new Vector2f(1.0f, 1.0f), //18
            new Vector2f(0.0f, 1.0f), //19
            new Vector2f(0.5f, 0.5f), //20
            new Vector2f(1.0f, 1.0f), //21
            new Vector2f(0.5f, 1.0f), //22
            new Vector2f(0.0f, 0.5f), //23
            new Vector2f(1.0f, 0.5f), //24
            new Vector2f(0.0f, 0.5f), //25
            new Vector2f(0.5f, 1.0f), //26
            new Vector2f(0.0f, 1.0f), //27
            new Vector2f(0.0f, 1.0f), //28
            new Vector2f(1.0f, 0.5f), //29
            new Vector2f(1.0f, 1.0f), //30
            new Vector2f(1.0f, 0.0f), //31
            new Vector2f(0.0f, 0.5f), //32
            new Vector2f(0.0f, 0.5f), //33
            new Vector2f(0.0f, 1.0f), //34
            new Vector2f(0.0f, 1.0f), //35
            new Vector2f(1.0f, 0.5f), //36
            new Vector2f(0.5f, 0.5f), //37
            new Vector2f(0.0f, 0.5f), //38
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_stairs_upside_down_positiveZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[31].x), getUVTextureCoord_Y(pos, uv2[31].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    private static void make_stairs_upside_down_negativeY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[35].x), getUVTextureCoord_Y(pos, uv2[35].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE
    }

    private static void make_stairs_upside_down_center_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[34].x), getUVTextureCoord_Y(pos, uv2[34].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[36].x), getUVTextureCoord_Y(pos, uv2[36].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE
    }

    private static void make_stairs_upside_down_positiveY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[30].x), getUVTextureCoord_Y(pos, uv2[30].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[29].x), getUVTextureCoord_Y(pos, uv2[29].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[28].x), getUVTextureCoord_Y(pos, uv2[28].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[29].x), getUVTextureCoord_Y(pos, uv2[29].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[38].x), getUVTextureCoord_Y(pos, uv2[38].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[28].x), getUVTextureCoord_Y(pos, uv2[28].y));//FACE
    }

    private static void make_stairs_upside_down_negativeX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[27].x), getUVTextureCoord_Y(pos, uv2[27].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[26].x), getUVTextureCoord_Y(pos, uv2[26].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[33].x), getUVTextureCoord_Y(pos, uv2[33].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[26].x), getUVTextureCoord_Y(pos, uv2[26].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[37].x), getUVTextureCoord_Y(pos, uv2[37].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));//FACE
    }

    private static void make_stairs_upside_down_positiveX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));//FACE
    }

    private static void make_stairs_upside_down_negativeZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[32].x), getUVTextureCoord_Y(pos, uv2[32].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[34].x), getUVTextureCoord_Y(pos, uv2[34].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Draw stairs_side">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_stairs_side = {
            new Vector3f(1.0f, 0.0f, 0.5f), //0
            new Vector3f(1.0f, 0.0f, 0.0f), //1
            new Vector3f(1.0f, 1.0f, 0.5f), //2
            new Vector3f(1.0f, 1.0f, 0.0f), //3
            new Vector3f(0.0f, 0.0f, 0.5f), //4
            new Vector3f(0.0f, 0.0f, 0.0f), //5
            new Vector3f(0.0f, 1.0f, 0.5f), //6
            new Vector3f(0.0f, 1.0f, 0.0f), //7
            new Vector3f(0.5f, 0.0f, 1.0f), //8
            new Vector3f(0.5f, 0.0f, 0.5f), //9
            new Vector3f(0.5f, 1.0f, 1.0f), //10
            new Vector3f(0.5f, 1.0f, 0.5f), //11
            new Vector3f(0.0f, 0.0f, 1.0f), //12
            new Vector3f(0.0f, 1.0f, 1.0f), //13
    };
    static Vector2f[] uv_stairs_side = {
            new Vector2f(0.0f, 0.0f), //0
            new Vector2f(1.0f, 0.5f), //1
            new Vector2f(0.0f, 0.5f), //2
            new Vector2f(0.0f, 0.0f), //3
            new Vector2f(1.0f, 0.5f), //4
            new Vector2f(0.0f, 0.5f), //5
            new Vector2f(1.0f, 0.5f), //6
            new Vector2f(0.0f, 0.0f), //7
            new Vector2f(1.0f, 0.0f), //8
            new Vector2f(1.0f, 0.5f), //9
            new Vector2f(0.0f, 0.0f), //10
            new Vector2f(1.0f, 0.0f), //11
            new Vector2f(1.0f, 1.0f), //12
            new Vector2f(1.0f, 0.0f), //13
            new Vector2f(1.0f, 1.0f), //14
            new Vector2f(0.0f, 0.0f), //15
            new Vector2f(1.0f, 0.0f), //16
            new Vector2f(0.0f, 0.5f), //17
            new Vector2f(1.0f, 1.0f), //18
            new Vector2f(0.0f, 1.0f), //19
            new Vector2f(0.5f, 0.5f), //20
            new Vector2f(1.0f, 1.0f), //21
            new Vector2f(0.5f, 1.0f), //22
            new Vector2f(1.0f, 0.5f), //23
            new Vector2f(0.0f, 1.0f), //24
            new Vector2f(0.0f, 0.5f), //25
            new Vector2f(0.0f, 0.5f), //26
            new Vector2f(0.5f, 1.0f), //27
            new Vector2f(0.0f, 1.0f), //28
            new Vector2f(0.0f, 0.5f), //29
            new Vector2f(1.0f, 1.0f), //30
            new Vector2f(0.0f, 1.0f), //31
            new Vector2f(1.0f, 0.0f), //32
            new Vector2f(0.0f, 0.5f), //33
            new Vector2f(0.0f, 0.5f), //34
            new Vector2f(0.0f, 1.0f), //35
            new Vector2f(1.0f, 0.5f), //36
            new Vector2f(0.5f, 0.5f), //37
            new Vector2f(1.0f, 0.5f), //38
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_stairs_side_positiveX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[32].x), getUVTextureCoord_Y(pos, uv2[32].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }

    private static void make_stairs_side_negativeZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[35].x), getUVTextureCoord_Y(pos, uv2[35].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE
    }

    private static void make_stairs_side_center_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[36].x), getUVTextureCoord_Y(pos, uv2[36].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));//FACE
    }

    private static void make_stairs_side_positiveZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[31].x), getUVTextureCoord_Y(pos, uv2[31].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[30].x), getUVTextureCoord_Y(pos, uv2[30].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[29].x), getUVTextureCoord_Y(pos, uv2[29].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[30].x), getUVTextureCoord_Y(pos, uv2[30].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[38].x), getUVTextureCoord_Y(pos, uv2[38].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[29].x), getUVTextureCoord_Y(pos, uv2[29].y));//FACE
    }

    private static void make_stairs_side_negativeY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[28].x), getUVTextureCoord_Y(pos, uv2[28].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[27].x), getUVTextureCoord_Y(pos, uv2[27].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[26].x), getUVTextureCoord_Y(pos, uv2[26].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[34].x), getUVTextureCoord_Y(pos, uv2[34].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[27].x), getUVTextureCoord_Y(pos, uv2[27].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[37].x), getUVTextureCoord_Y(pos, uv2[37].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[26].x), getUVTextureCoord_Y(pos, uv2[26].y));//FACE
    }

    private static void make_stairs_side_positiveY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));//FACE
    }

    private static void make_stairs_side_negativeX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[33].x), getUVTextureCoord_Y(pos, uv2[33].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));//FACE
    }

    //</editor-fold>
//</editor-fold>

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        BlockOrientation orientation = BlockOrientation.getBlockOrientation(data);

        if (orientation != null) {
            if (orientation.getY() == 3) {
                if (orientation.getXZ() == 1) {
                    box.setPosAndSize(x + 0.5f, y, z + 0.5f, 0.5f, 1f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z, 1f, 1f, 0.5f);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 2) {
                    box.setPosAndSize(x + 0.5f, y, z, 0.5f, 1f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z + 0.5f, 1f, 1f, 0.5f);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 3) {
                    box.setPosAndSize(x, y, z, 0.5f, 1f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z + 0.5f, 1f, 1f, 0.5f);
                    consumer.accept(box);
                } else {
                    box.setPosAndSize(x, y, z + 0.5f, 0.5f, 1f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z, 1f, 1f, 0.5f);
                    consumer.accept(box);
                }
            } else if (orientation.getY() >= 0) {
                if (orientation.getXZ() == 1) {
                    box.setPosAndSize(x, y + 0.5f, z, 0.5f, 0.5f, 1f);
                    consumer.accept(box);
                    box.setPosAndSize(x + 0.5f, y, z, 0.5f, 1f, 1);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 2) {
                    box.setPosAndSize(x, y + 0.5f, z, 1f, 0.5f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z + 0.5f, 1f, 1f, 0.5f);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 3) {
                    box.setPosAndSize(x + 0.5f, y + 0.5f, z, 0.5f, 0.5f, 1f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z, 0.5f, 1f, 1f);
                    consumer.accept(box);
                } else {
                    box.setPosAndSize(x, y + 0.5f, z + 0.5f, 1f, 0.5f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z, 1f, 1f, 0.5f);
                    consumer.accept(box);
                }
            } else {
                if (orientation.getXZ() == 1) {
                    box.setPosAndSize(x, y, z, 0.5f, 0.5f, 1f);
                    consumer.accept(box);
                    box.setPosAndSize(x + 0.5f, y, z, 0.5f, 1f, 1);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 2) {
                    box.setPosAndSize(x, y, z, 1f, 0.5f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z + 0.5f, 1f, 1f, 0.5f);
                    consumer.accept(box);
                } else if (orientation.getXZ() == 3) {
                    box.setPosAndSize(x + 0.5f, y, z, 0.5f, 0.5f, 1f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z, 0.5f, 1f, 1f);
                    consumer.accept(box);
                } else {
                    box.setPosAndSize(x, y, z + 0.5f, 1f, 0.5f, 0.5f);
                    consumer.accept(box);
                    box.setPosAndSize(x, y, z, 1f, 1f, 0.5f);
                    consumer.accept(box);
                }
            }
        }
    }
}
