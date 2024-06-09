package com.xbuilders.game.items.blockType.wheel;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.player.UserControlledPlayer;
import com.xbuilders.engine.player.raycasting.Ray;
import com.xbuilders.engine.world.blockData.BlockData;
import org.joml.Vector2f;
import org.joml.Vector3f;
import processing.core.PShape;

public class HalfWheelRenderer extends BlockType {
    @Override
    public BlockData getInitialBlockData(UserControlledPlayer player, Block block, Ray ray) {
        BlockData data = new BlockData(1);
        data.set(0, (byte) player.camera.simplifiedPanTilt.x);
//        data.set(1, (byte) 0);
        return data;
    }

    public HalfWheelRenderer() {
//        ObjToBlockTypeConversion.parseOBJToRendererClass(new File(ResourceUtils.LOCAL_DIR, "half_wheel.obj"));
    }


//<editor-fold defaultstate="collapsed" desc="Draw half_wheel">
//NOTES:
//The UV map for this block only exists on the top face.


    //<editor-fold defaultstate="collapsed" desc="Verticies">
    static Vector3f[] verts_half_wheel = {new Vector3f(1.02f, 0.7f, 0.0f / 2), //VERT 0
            new Vector3f(1.02f, 0.7f, 0.96f / 2), //VERT 1
            new Vector3f(1.02f, 0.26f, 0.0f / 2), //VERT 2
            new Vector3f(1.02f, 0.26f, 0.96f / 2), //VERT 3
            new Vector3f(0.7f, -0.06f, 0.0f / 2), //VERT 4
            new Vector3f(0.7f, -0.06f, 0.96f / 2), //VERT 5
            new Vector3f(0.26f, -0.06f, 0.0f / 2), //VERT 6
            new Vector3f(0.26f, -0.06f, 0.96f / 2), //VERT 7
            new Vector3f(-0.06f, 0.26f, 0.0f / 2), //VERT 8
            new Vector3f(-0.06f, 0.26f, 0.96f / 2), //VERT 9
            new Vector3f(-0.06f, 0.7f, 0.0f / 2), //VERT 10
            new Vector3f(-0.06f, 0.7f, 0.96f / 2), //VERT 11
            new Vector3f(0.26f, 1.02f, 0.0f / 2), //VERT 12
            new Vector3f(0.26f, 1.02f, 0.96f / 2), //VERT 13
            new Vector3f(0.7f, 1.02f, 0.0f / 2), //VERT 14
            new Vector3f(0.7f, 1.02f, 0.96f / 2), //VERT 15
    };
    static Vector2f[] uv_half_wheel = {new Vector2f(0.5f, 0.0f), //UV 0
            new Vector2f(0.0f, 0.12f), //UV 1
            new Vector2f(0.0f, 0.0f), //UV 2
            new Vector2f(0.5f, 0.12f), //UV 3
            new Vector2f(0.0f, 0.26f), //UV 4
            new Vector2f(0.5f, 0.26f), //UV 5
            new Vector2f(0.0f, 0.38f), //UV 6
            new Vector2f(0.5f, 0.38f), //UV 7
            new Vector2f(0.0f, 0.5f), //UV 8
            new Vector2f(0.5f, 0.5f), //UV 9
            new Vector2f(0.0f, 0.62f), //UV 10
            new Vector2f(0.5f, 0.62f), //UV 11
            new Vector2f(0.0f, 0.76f), //UV 12
            new Vector2f(0.3f, 1.0f), //UV 13
            new Vector2f(0.0f, 0.3f), //UV 14
            new Vector2f(0.7f, 0.0f), //UV 15
            new Vector2f(0.5f, 0.76f), //UV 16
            new Vector2f(0.0f, 0.88f), //UV 17
            new Vector2f(0.5f, 0.88f), //UV 18
            new Vector2f(0.0f, 1.0f), //UV 19
            new Vector2f(0.3f, 0.0f), //UV 20
            new Vector2f(0.0f, 0.7f), //UV 21
            new Vector2f(0.7f, 1.0f), //UV 22
            new Vector2f(1.0f, 0.3f), //UV 23
            new Vector2f(1.0f, 0.7f), //UV 24
            new Vector2f(0.5f, 1.0f), //UV 25
    };

//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="Face methods">

    private static void make_half_wheel_negativeY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.TOP;


        buffers.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));//FACE

        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        buffers.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));//FACE
    }


    private static void make_half_wheel_positiveY_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.TOP;


        buffers.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));//FACE

        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        buffers.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));//FACE
    }


    private static void make_half_wheel_negativeX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.TOP;


        buffers.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        buffers.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        buffers.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        buffers.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        buffers.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));
        buffers.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE
    }


    private static void make_half_wheel_positiveX_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.TOP;


        buffers.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        buffers.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        buffers.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        buffers.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        buffers.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));
        buffers.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE
    }


    private static void make_half_wheel_negativeZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.FRONT;


        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        buffers.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));//FACE

        buffers.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        buffers.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));//FACE

        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        buffers.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        buffers.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));//FACE

        buffers.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        buffers.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));//FACE

        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        buffers.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        buffers.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));//FACE

        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        buffers.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));//FACE
    }


    private static void make_half_wheel_positiveZ_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.FRONT;


        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        buffers.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));//FACE

        buffers.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        buffers.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));//FACE

        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        buffers.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));
        buffers.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));//FACE

        buffers.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        buffers.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));//FACE

        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        buffers.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        buffers.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));//FACE

        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));
        buffers.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));//FACE
    }


    private static void make_half_wheel_center_faces(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {
        int[] pos = block.texture.TOP;


        buffers.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        buffers.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        buffers.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        buffers.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        buffers.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        buffers.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        buffers.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        buffers.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        buffers.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        buffers.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        buffers.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        buffers.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));//FACE

        buffers.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        buffers.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        buffers.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        buffers.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));
        buffers.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        buffers.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));//FACE

        buffers.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        buffers.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));
        buffers.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        buffers.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));
        buffers.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));
        buffers.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));//FACE
    }


//</editor-fold>
//</editor-fold>

    @Override
    public void constructBlock(PShape buffers, Block block, BlockData data, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        Vector3f[] verts2 = verts_half_wheel;

        if (data != null && data.getLength() >= 1) {
            verts2 = rotateVerticiesYAxis(verts_half_wheel, data.get(0));
        }
        make_half_wheel_negativeY_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
        make_half_wheel_positiveY_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
        make_half_wheel_negativeX_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
        make_half_wheel_positiveX_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
        make_half_wheel_negativeZ_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
        make_half_wheel_positiveZ_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
        make_half_wheel_center_faces(verts2, uv_half_wheel, block, buffers, x, y, z);
    }
}
