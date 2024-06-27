/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.blockType;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.rendering.blocks.BlockMesh_Base;
import com.xbuilders.engine.utils.math.AABB;
import com.xbuilders.engine.world.blockData.BlockData;
import processing.core.PVector;

import java.util.function.Consumer;

import static com.xbuilders.game.items.blockType.StairsRenderer.renderSideSubBlock;

/**
 * @author zipCoder933
 */
public class PillarRenderer extends BlockType {


    //<editor-fold defaultstate="collapsed" desc="Draw pillar">
//NOTES:
//The UV map for this block only exists on the top face.
//<editor-fold defaultstate="collapsed" desc="Verticies">
    static PVector[] verts_pillar = {
            new PVector(0.8125f, 1.0f, 0.1875f), //0
            new PVector(0.1875f, 1.000001f, 0.1875f), //1
            new PVector(0.8125f, 1.0f, 0.8125f), //2
            new PVector(0.1875f, 1.000001f, 0.8125f), //3
            new PVector(0.25f, 1.0f, 0.875f), //4
            new PVector(0.25f, 1.0f, 0.1875f), //5
            new PVector(0.25f, 1.0f, 0.125f), //6
            new PVector(0.25f, 1.0f, 0.8125f), //7
            new PVector(0.75f, 1.0f, 0.875f), //8
            new PVector(0.75f, 1.0f, 0.1875f), //9
            new PVector(0.75f, 1.0f, 0.8125f), //10
            new PVector(0.75f, 1.0f, 0.125f), //11
            new PVector(0.875f, 1.0f, 0.25f), //12
            new PVector(0.125f, 1.000001f, 0.25f), //13
            new PVector(0.8125f, 1.0f, 0.25f), //14
            new PVector(0.1875f, 1.000001f, 0.25f), //15
            new PVector(0.125f, 1.000001f, 0.75f), //16
            new PVector(0.8125f, 1.0f, 0.75f), //17
            new PVector(0.1875f, 1.000001f, 0.75f), //18
            new PVector(0.875f, 1.0f, 0.75f), //19
            new PVector(0.125f, 0.0f, 0.75f), //20
            new PVector(0.125f, 0.0f, 0.25f), //21
            new PVector(0.1875f, 0.0f, 0.25f), //22
            new PVector(0.8125f, 0.0f, 0.25f), //23
            new PVector(0.875f, -0.0f, 0.25f), //24
            new PVector(0.875f, -0.0f, 0.75f), //25
            new PVector(0.75f, 0.0f, 0.875f), //26
            new PVector(0.25f, 0.0f, 0.875f), //27
            new PVector(0.25f, 0.0f, 0.8125f), //28
            new PVector(0.25f, 0.0f, 0.125f), //29
            new PVector(0.75f, 0.0f, 0.125f), //30
            new PVector(0.25f, 0.0f, 0.1875f), //31
            new PVector(0.1875f, 0.0f, 0.1875f), //32
            new PVector(0.75f, 0.0f, 0.8125f), //33
            new PVector(0.1875f, 0.0f, 0.8125f), //34
            new PVector(0.1875f, 0.0f, 0.75f), //35
            new PVector(0.8125f, 0.0f, 0.8125f), //36
            new PVector(0.8125f, 0.0f, 0.75f), //37
            new PVector(0.8125f, 0.0f, 0.1875f), //38
            new PVector(0.75f, 0.0f, 0.1875f), //39
    };
    static PVector[] uv_pillar = {
            new PVector(0.1875f, 0.25f), //0
            new PVector(0.75f, 0.1875f), //1
            new PVector(0.8125f, 0.75f), //2
            new PVector(0.25f, 0.875f), //3
            new PVector(0.75f, 0.8125f), //4
            new PVector(0.75f, 0.875f), //5
            new PVector(0.25f, 0.1875f), //6
            new PVector(0.75f, 0.125f), //7
            new PVector(0.875f, 0.75f), //8
            new PVector(0.8125f, 0.25f), //9
            new PVector(0.875f, 0.25f), //10
            new PVector(0.125f, 0.75f), //11
            new PVector(0.1875f, 0.75f), //12
            new PVector(0.25f, 0.0f), //13
            new PVector(0.75f, 1.0f), //14
            new PVector(0.75f, 0.0f), //15
            new PVector(0.75f, 0.0f), //16
            new PVector(0.8125f, 1.0f), //17
            new PVector(0.8125f, 0.0f), //18
            new PVector(0.25f, 0.0f), //19
            new PVector(0.75f, 1.0f), //20
            new PVector(0.75f, 0.0f), //21
            new PVector(0.125f, 0.0f), //22
            new PVector(0.1875f, 1.0f), //23
            new PVector(0.1875f, 0.0f), //24
            new PVector(0.25f, 0.0f), //25
            new PVector(0.1875f, 1.0f), //26
            new PVector(0.25f, 1.0f), //27
            new PVector(0.8125f, 0.0f), //28
            new PVector(0.875f, 1.0f), //29
            new PVector(0.875f, 0.0f), //30
            new PVector(0.1875f, 0.0f), //31
            new PVector(0.25f, 1.0f), //32
            new PVector(0.25f, 0.0f), //33
            new PVector(0.8125f, 0.0f), //34
            new PVector(0.875f, 1.0f), //35
            new PVector(0.875f, 0.0f), //36
            new PVector(0.8125f, 0.0f), //37
            new PVector(0.875f, 1.0f), //38
            new PVector(0.875f, 0.0f), //39
            new PVector(0.1875f, 0.0f), //40
            new PVector(0.25f, 1.0f), //41
            new PVector(0.25f, 0.0f), //42
            new PVector(0.25f, 0.0f), //43
            new PVector(0.75f, 1.0f), //44
            new PVector(0.75f, 0.0f), //45
            new PVector(0.125f, 0.0f), //46
            new PVector(0.1875f, 1.0f), //47
            new PVector(0.1875f, 0.0f), //48
            new PVector(0.125f, 0.0f), //49
            new PVector(0.1875f, 1.0f), //50
            new PVector(0.1875f, 0.0f), //51
            new PVector(0.75f, 0.0f), //52
            new PVector(0.8125f, 1.0f), //53
            new PVector(0.8125f, 0.0f), //54
            new PVector(0.75f, 0.0f), //55
            new PVector(0.8125f, 1.0f), //56
            new PVector(0.8125f, 0.0f), //57
            new PVector(0.8125f, 0.0f), //58
            new PVector(0.875f, 1.0f), //59
            new PVector(0.875f, 0.0f), //60
            new PVector(0.75f, 0.0f), //61
            new PVector(0.25f, 1.0f), //62
            new PVector(0.75f, 1.0f), //63
            new PVector(0.75f, 0.0f), //64
            new PVector(0.8125f, 1.0f), //65
            new PVector(0.8125f, 0.0f), //66
            new PVector(0.1875f, 0.0f), //67
            new PVector(0.25f, 1.0f), //68
            new PVector(0.25f, 0.0f), //69
            new PVector(0.125f, 0.0f), //70
            new PVector(0.1875f, 1.0f), //71
            new PVector(0.1875f, 0.0f), //72
            new PVector(0.1875f, 0.75f), //73
            new PVector(0.8125f, 0.25f), //74
            new PVector(0.75f, 0.8125f), //75
            new PVector(0.25f, 0.1875f), //76
            new PVector(0.75f, 0.125f), //77
            new PVector(0.75f, 0.1875f), //78
            new PVector(0.25f, 0.875f), //79
            new PVector(0.25f, 0.8125f), //80
            new PVector(0.875f, 0.75f), //81
            new PVector(0.8125f, 0.75f), //82
            new PVector(0.125f, 0.25f), //83
            new PVector(0.1875f, 0.25f), //84
            new PVector(0.8125f, 0.8125f), //85
            new PVector(0.25f, 0.8125f), //86
            new PVector(0.1875f, 0.8125f), //87
            new PVector(0.1875f, 0.1875f), //88
            new PVector(0.8125f, 0.1875f), //89
            new PVector(0.25f, 0.125f), //90
            new PVector(0.125f, 0.25f), //91
            new PVector(0.25f, 1.0f), //92
            new PVector(0.75f, 1.0f), //93
            new PVector(0.25f, 1.0f), //94
            new PVector(0.125f, 1.0f), //95
            new PVector(0.1875f, 0.0f), //96
            new PVector(0.8125f, 1.0f), //97
            new PVector(0.1875f, 1.0f), //98
            new PVector(0.8125f, 1.0f), //99
            new PVector(0.8125f, 1.0f), //100
            new PVector(0.1875f, 1.0f), //101
            new PVector(0.25f, 1.0f), //102
            new PVector(0.125f, 1.0f), //103
            new PVector(0.125f, 1.0f), //104
            new PVector(0.75f, 1.0f), //105
            new PVector(0.75f, 1.0f), //106
            new PVector(0.8125f, 1.0f), //107
            new PVector(0.25f, 0.0f), //108
            new PVector(0.75f, 1.0f), //109
            new PVector(0.1875f, 1.0f), //110
            new PVector(0.125f, 1.0f), //111
            new PVector(0.1875f, 0.1875f), //112
            new PVector(0.8125f, 0.1875f), //113
            new PVector(0.1875f, 0.8125f), //114
            new PVector(0.8125f, 0.8125f), //115
            new PVector(0.25f, 0.125f), //116
            new PVector(0.75f, 0.875f), //117
            new PVector(0.875f, 0.25f), //118
            new PVector(0.125f, 0.75f), //119
    };

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Face methods">
    private static void make_pillar_center_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.FRONT;

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[10].x), getUVTextureCoord_Y(pos, uv2[10].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[15].x), getUVTextureCoord_Y(pos, uv2[15].y));
        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));//FACE

        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[18].x), getUVTextureCoord_Y(pos, uv2[18].y));
        shape.vertex(verts2[32].x + x, verts2[32].y + y, verts2[32].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[21].x), getUVTextureCoord_Y(pos, uv2[21].y));
        shape.vertex(verts2[29].x + x, verts2[29].y + y, verts2[29].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));//FACE

        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[24].x), getUVTextureCoord_Y(pos, uv2[24].y));
        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));//FACE

        shape.vertex(verts2[28].x + x, verts2[28].y + y, verts2[28].z + z, getUVTextureCoord_X(pos, uv2[27].x), getUVTextureCoord_Y(pos, uv2[27].y));
        shape.vertex(verts2[34].x + x, verts2[34].y + y, verts2[34].z + z, getUVTextureCoord_X(pos, uv2[26].x), getUVTextureCoord_Y(pos, uv2[26].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));//FACE

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[30].x), getUVTextureCoord_Y(pos, uv2[30].y));
        shape.vertex(verts2[27].x + x, verts2[27].y + y, verts2[27].z + z, getUVTextureCoord_X(pos, uv2[29].x), getUVTextureCoord_Y(pos, uv2[29].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[28].x), getUVTextureCoord_Y(pos, uv2[28].y));//FACE

        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[33].x), getUVTextureCoord_Y(pos, uv2[33].y));
        shape.vertex(verts2[39].x + x, verts2[39].y + y, verts2[39].z + z, getUVTextureCoord_X(pos, uv2[32].x), getUVTextureCoord_Y(pos, uv2[32].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[31].x), getUVTextureCoord_Y(pos, uv2[31].y));//FACE

        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[36].x), getUVTextureCoord_Y(pos, uv2[36].y));
        shape.vertex(verts2[30].x + x, verts2[30].y + y, verts2[30].z + z, getUVTextureCoord_X(pos, uv2[35].x), getUVTextureCoord_Y(pos, uv2[35].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[34].x), getUVTextureCoord_Y(pos, uv2[34].y));//FACE

        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[39].x), getUVTextureCoord_Y(pos, uv2[39].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[38].x), getUVTextureCoord_Y(pos, uv2[38].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[37].x), getUVTextureCoord_Y(pos, uv2[37].y));//FACE

        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[42].x), getUVTextureCoord_Y(pos, uv2[42].y));
        shape.vertex(verts2[37].x + x, verts2[37].y + y, verts2[37].z + z, getUVTextureCoord_X(pos, uv2[41].x), getUVTextureCoord_Y(pos, uv2[41].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[40].x), getUVTextureCoord_Y(pos, uv2[40].y));//FACE

        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[45].x), getUVTextureCoord_Y(pos, uv2[45].y));
        shape.vertex(verts2[24].x + x, verts2[24].y + y, verts2[24].z + z, getUVTextureCoord_X(pos, uv2[44].x), getUVTextureCoord_Y(pos, uv2[44].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[43].x), getUVTextureCoord_Y(pos, uv2[43].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[48].x), getUVTextureCoord_Y(pos, uv2[48].y));
        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[47].x), getUVTextureCoord_Y(pos, uv2[47].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[46].x), getUVTextureCoord_Y(pos, uv2[46].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[51].x), getUVTextureCoord_Y(pos, uv2[51].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[50].x), getUVTextureCoord_Y(pos, uv2[50].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[49].x), getUVTextureCoord_Y(pos, uv2[49].y));//FACE

        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[54].x), getUVTextureCoord_Y(pos, uv2[54].y));
        shape.vertex(verts2[34].x + x, verts2[34].y + y, verts2[34].z + z, getUVTextureCoord_X(pos, uv2[53].x), getUVTextureCoord_Y(pos, uv2[53].y));
        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[52].x), getUVTextureCoord_Y(pos, uv2[52].y));//FACE

        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[57].x), getUVTextureCoord_Y(pos, uv2[57].y));
        shape.vertex(verts2[38].x + x, verts2[38].y + y, verts2[38].z + z, getUVTextureCoord_X(pos, uv2[56].x), getUVTextureCoord_Y(pos, uv2[56].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[55].x), getUVTextureCoord_Y(pos, uv2[55].y));//FACE

        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[60].x), getUVTextureCoord_Y(pos, uv2[60].y));
        shape.vertex(verts2[25].x + x, verts2[25].y + y, verts2[25].z + z, getUVTextureCoord_X(pos, uv2[59].x), getUVTextureCoord_Y(pos, uv2[59].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[58].x), getUVTextureCoord_Y(pos, uv2[58].y));//FACE

        shape.vertex(verts2[26].x + x, verts2[26].y + y, verts2[26].z + z, getUVTextureCoord_X(pos, uv2[63].x), getUVTextureCoord_Y(pos, uv2[63].y));
        shape.vertex(verts2[27].x + x, verts2[27].y + y, verts2[27].z + z, getUVTextureCoord_X(pos, uv2[62].x), getUVTextureCoord_Y(pos, uv2[62].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[61].x), getUVTextureCoord_Y(pos, uv2[61].y));//FACE

        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[66].x), getUVTextureCoord_Y(pos, uv2[66].y));
        shape.vertex(verts2[36].x + x, verts2[36].y + y, verts2[36].z + z, getUVTextureCoord_X(pos, uv2[65].x), getUVTextureCoord_Y(pos, uv2[65].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[64].x), getUVTextureCoord_Y(pos, uv2[64].y));//FACE

        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[69].x), getUVTextureCoord_Y(pos, uv2[69].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[68].x), getUVTextureCoord_Y(pos, uv2[68].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[67].x), getUVTextureCoord_Y(pos, uv2[67].y));//FACE

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[72].x), getUVTextureCoord_Y(pos, uv2[72].y));
        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[71].x), getUVTextureCoord_Y(pos, uv2[71].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[70].x), getUVTextureCoord_Y(pos, uv2[70].y));//FACE

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[85].x), getUVTextureCoord_Y(pos, uv2[85].y));//FACE

        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[86].x), getUVTextureCoord_Y(pos, uv2[86].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[89].x), getUVTextureCoord_Y(pos, uv2[89].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[86].x), getUVTextureCoord_Y(pos, uv2[86].y));
        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));//FACE

        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[9].x), getUVTextureCoord_Y(pos, uv2[9].y));
        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[2].x), getUVTextureCoord_Y(pos, uv2[2].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[8].x), getUVTextureCoord_Y(pos, uv2[8].y));//FACE

        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[14].x), getUVTextureCoord_Y(pos, uv2[14].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[92].x), getUVTextureCoord_Y(pos, uv2[92].y));
        shape.vertex(verts2[13].x + x, verts2[13].y + y, verts2[13].z + z, getUVTextureCoord_X(pos, uv2[13].x), getUVTextureCoord_Y(pos, uv2[13].y));//FACE

        shape.vertex(verts2[32].x + x, verts2[32].y + y, verts2[32].z + z, getUVTextureCoord_X(pos, uv2[17].x), getUVTextureCoord_Y(pos, uv2[17].y));
        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[93].x), getUVTextureCoord_Y(pos, uv2[93].y));
        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[16].x), getUVTextureCoord_Y(pos, uv2[16].y));//FACE

        shape.vertex(verts2[29].x + x, verts2[29].y + y, verts2[29].z + z, getUVTextureCoord_X(pos, uv2[20].x), getUVTextureCoord_Y(pos, uv2[20].y));
        shape.vertex(verts2[30].x + x, verts2[30].y + y, verts2[30].z + z, getUVTextureCoord_X(pos, uv2[94].x), getUVTextureCoord_Y(pos, uv2[94].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[19].x), getUVTextureCoord_Y(pos, uv2[19].y));//FACE

        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[23].x), getUVTextureCoord_Y(pos, uv2[23].y));
        shape.vertex(verts2[26].x + x, verts2[26].y + y, verts2[26].z + z, getUVTextureCoord_X(pos, uv2[95].x), getUVTextureCoord_Y(pos, uv2[95].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[22].x), getUVTextureCoord_Y(pos, uv2[22].y));//FACE

        shape.vertex(verts2[34].x + x, verts2[34].y + y, verts2[34].z + z, getUVTextureCoord_X(pos, uv2[26].x), getUVTextureCoord_Y(pos, uv2[26].y));
        shape.vertex(verts2[3].x + x, verts2[3].y + y, verts2[3].z + z, getUVTextureCoord_X(pos, uv2[96].x), getUVTextureCoord_Y(pos, uv2[96].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[25].x), getUVTextureCoord_Y(pos, uv2[25].y));//FACE

        shape.vertex(verts2[27].x + x, verts2[27].y + y, verts2[27].z + z, getUVTextureCoord_X(pos, uv2[29].x), getUVTextureCoord_Y(pos, uv2[29].y));
        shape.vertex(verts2[28].x + x, verts2[28].y + y, verts2[28].z + z, getUVTextureCoord_X(pos, uv2[97].x), getUVTextureCoord_Y(pos, uv2[97].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[28].x), getUVTextureCoord_Y(pos, uv2[28].y));//FACE

        shape.vertex(verts2[39].x + x, verts2[39].y + y, verts2[39].z + z, getUVTextureCoord_X(pos, uv2[32].x), getUVTextureCoord_Y(pos, uv2[32].y));
        shape.vertex(verts2[38].x + x, verts2[38].y + y, verts2[38].z + z, getUVTextureCoord_X(pos, uv2[98].x), getUVTextureCoord_Y(pos, uv2[98].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[31].x), getUVTextureCoord_Y(pos, uv2[31].y));//FACE

        shape.vertex(verts2[30].x + x, verts2[30].y + y, verts2[30].z + z, getUVTextureCoord_X(pos, uv2[35].x), getUVTextureCoord_Y(pos, uv2[35].y));
        shape.vertex(verts2[39].x + x, verts2[39].y + y, verts2[39].z + z, getUVTextureCoord_X(pos, uv2[99].x), getUVTextureCoord_Y(pos, uv2[99].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[34].x), getUVTextureCoord_Y(pos, uv2[34].y));//FACE

        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[38].x), getUVTextureCoord_Y(pos, uv2[38].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[100].x), getUVTextureCoord_Y(pos, uv2[100].y));
        shape.vertex(verts2[15].x + x, verts2[15].y + y, verts2[15].z + z, getUVTextureCoord_X(pos, uv2[37].x), getUVTextureCoord_Y(pos, uv2[37].y));//FACE

        shape.vertex(verts2[37].x + x, verts2[37].y + y, verts2[37].z + z, getUVTextureCoord_X(pos, uv2[41].x), getUVTextureCoord_Y(pos, uv2[41].y));
        shape.vertex(verts2[36].x + x, verts2[36].y + y, verts2[36].z + z, getUVTextureCoord_X(pos, uv2[101].x), getUVTextureCoord_Y(pos, uv2[101].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[40].x), getUVTextureCoord_Y(pos, uv2[40].y));//FACE

        shape.vertex(verts2[24].x + x, verts2[24].y + y, verts2[24].z + z, getUVTextureCoord_X(pos, uv2[44].x), getUVTextureCoord_Y(pos, uv2[44].y));
        shape.vertex(verts2[25].x + x, verts2[25].y + y, verts2[25].z + z, getUVTextureCoord_X(pos, uv2[102].x), getUVTextureCoord_Y(pos, uv2[102].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[43].x), getUVTextureCoord_Y(pos, uv2[43].y));//FACE

        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[47].x), getUVTextureCoord_Y(pos, uv2[47].y));
        shape.vertex(verts2[29].x + x, verts2[29].y + y, verts2[29].z + z, getUVTextureCoord_X(pos, uv2[103].x), getUVTextureCoord_Y(pos, uv2[103].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[46].x), getUVTextureCoord_Y(pos, uv2[46].y));//FACE

        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[50].x), getUVTextureCoord_Y(pos, uv2[50].y));
        shape.vertex(verts2[24].x + x, verts2[24].y + y, verts2[24].z + z, getUVTextureCoord_X(pos, uv2[104].x), getUVTextureCoord_Y(pos, uv2[104].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[49].x), getUVTextureCoord_Y(pos, uv2[49].y));//FACE

        shape.vertex(verts2[34].x + x, verts2[34].y + y, verts2[34].z + z, getUVTextureCoord_X(pos, uv2[53].x), getUVTextureCoord_Y(pos, uv2[53].y));
        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[105].x), getUVTextureCoord_Y(pos, uv2[105].y));
        shape.vertex(verts2[18].x + x, verts2[18].y + y, verts2[18].z + z, getUVTextureCoord_X(pos, uv2[52].x), getUVTextureCoord_Y(pos, uv2[52].y));//FACE

        shape.vertex(verts2[38].x + x, verts2[38].y + y, verts2[38].z + z, getUVTextureCoord_X(pos, uv2[56].x), getUVTextureCoord_Y(pos, uv2[56].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[106].x), getUVTextureCoord_Y(pos, uv2[106].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[55].x), getUVTextureCoord_Y(pos, uv2[55].y));//FACE

        shape.vertex(verts2[25].x + x, verts2[25].y + y, verts2[25].z + z, getUVTextureCoord_X(pos, uv2[59].x), getUVTextureCoord_Y(pos, uv2[59].y));
        shape.vertex(verts2[37].x + x, verts2[37].y + y, verts2[37].z + z, getUVTextureCoord_X(pos, uv2[107].x), getUVTextureCoord_Y(pos, uv2[107].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[58].x), getUVTextureCoord_Y(pos, uv2[58].y));//FACE

        shape.vertex(verts2[27].x + x, verts2[27].y + y, verts2[27].z + z, getUVTextureCoord_X(pos, uv2[62].x), getUVTextureCoord_Y(pos, uv2[62].y));
        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[108].x), getUVTextureCoord_Y(pos, uv2[108].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[61].x), getUVTextureCoord_Y(pos, uv2[61].y));//FACE

        shape.vertex(verts2[36].x + x, verts2[36].y + y, verts2[36].z + z, getUVTextureCoord_X(pos, uv2[65].x), getUVTextureCoord_Y(pos, uv2[65].y));
        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[109].x), getUVTextureCoord_Y(pos, uv2[109].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[64].x), getUVTextureCoord_Y(pos, uv2[64].y));//FACE

        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[68].x), getUVTextureCoord_Y(pos, uv2[68].y));
        shape.vertex(verts2[32].x + x, verts2[32].y + y, verts2[32].z + z, getUVTextureCoord_X(pos, uv2[110].x), getUVTextureCoord_Y(pos, uv2[110].y));
        shape.vertex(verts2[1].x + x, verts2[1].y + y, verts2[1].z + z, getUVTextureCoord_X(pos, uv2[67].x), getUVTextureCoord_Y(pos, uv2[67].y));//FACE

        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[71].x), getUVTextureCoord_Y(pos, uv2[71].y));
        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[111].x), getUVTextureCoord_Y(pos, uv2[111].y));
        shape.vertex(verts2[16].x + x, verts2[16].y + y, verts2[16].z + z, getUVTextureCoord_X(pos, uv2[70].x), getUVTextureCoord_Y(pos, uv2[70].y));//FACE
    }

    private static void make_pillar_positiveY_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[4].x + x, verts2[4].y + y, verts2[4].z + z, getUVTextureCoord_X(pos, uv2[5].x), getUVTextureCoord_Y(pos, uv2[5].y));
        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE

        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[2].x + x, verts2[2].y + y, verts2[2].z + z, getUVTextureCoord_X(pos, uv2[87].x), getUVTextureCoord_Y(pos, uv2[87].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[86].x), getUVTextureCoord_Y(pos, uv2[86].y));//FACE

        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        shape.vertex(verts2[0].x + x, verts2[0].y + y, verts2[0].z + z, getUVTextureCoord_X(pos, uv2[88].x), getUVTextureCoord_Y(pos, uv2[88].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[17].x + x, verts2[17].y + y, verts2[17].z + z, getUVTextureCoord_X(pos, uv2[12].x), getUVTextureCoord_Y(pos, uv2[12].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[86].x), getUVTextureCoord_Y(pos, uv2[86].y));//FACE

        shape.vertex(verts2[5].x + x, verts2[5].y + y, verts2[5].z + z, getUVTextureCoord_X(pos, uv2[1].x), getUVTextureCoord_Y(pos, uv2[1].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));
        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));//FACE

        shape.vertex(verts2[7].x + x, verts2[7].y + y, verts2[7].z + z, getUVTextureCoord_X(pos, uv2[4].x), getUVTextureCoord_Y(pos, uv2[4].y));
        shape.vertex(verts2[10].x + x, verts2[10].y + y, verts2[10].z + z, getUVTextureCoord_X(pos, uv2[86].x), getUVTextureCoord_Y(pos, uv2[86].y));
        shape.vertex(verts2[8].x + x, verts2[8].y + y, verts2[8].z + z, getUVTextureCoord_X(pos, uv2[3].x), getUVTextureCoord_Y(pos, uv2[3].y));//FACE

        shape.vertex(verts2[6].x + x, verts2[6].y + y, verts2[6].z + z, getUVTextureCoord_X(pos, uv2[7].x), getUVTextureCoord_Y(pos, uv2[7].y));
        shape.vertex(verts2[11].x + x, verts2[11].y + y, verts2[11].z + z, getUVTextureCoord_X(pos, uv2[90].x), getUVTextureCoord_Y(pos, uv2[90].y));
        shape.vertex(verts2[9].x + x, verts2[9].y + y, verts2[9].z + z, getUVTextureCoord_X(pos, uv2[6].x), getUVTextureCoord_Y(pos, uv2[6].y));//FACE

        shape.vertex(verts2[14].x + x, verts2[14].y + y, verts2[14].z + z, getUVTextureCoord_X(pos, uv2[0].x), getUVTextureCoord_Y(pos, uv2[0].y));
        shape.vertex(verts2[12].x + x, verts2[12].y + y, verts2[12].z + z, getUVTextureCoord_X(pos, uv2[91].x), getUVTextureCoord_Y(pos, uv2[91].y));
        shape.vertex(verts2[19].x + x, verts2[19].y + y, verts2[19].z + z, getUVTextureCoord_X(pos, uv2[11].x), getUVTextureCoord_Y(pos, uv2[11].y));//FACE
    }

    private static void make_pillar_negativeY_faces(PVector[] verts2, PVector[] uv2, Block block, BlockMesh_Base shape, int x, int y, int z) {
        int[] pos = block.texture.TOP;

        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[75].x), getUVTextureCoord_Y(pos, uv2[75].y));
        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));//FACE

        shape.vertex(verts2[28].x + x, verts2[28].y + y, verts2[28].z + z, getUVTextureCoord_X(pos, uv2[78].x), getUVTextureCoord_Y(pos, uv2[78].y));
        shape.vertex(verts2[27].x + x, verts2[27].y + y, verts2[27].z + z, getUVTextureCoord_X(pos, uv2[77].x), getUVTextureCoord_Y(pos, uv2[77].y));
        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[76].x), getUVTextureCoord_Y(pos, uv2[76].y));//FACE

        shape.vertex(verts2[39].x + x, verts2[39].y + y, verts2[39].z + z, getUVTextureCoord_X(pos, uv2[80].x), getUVTextureCoord_Y(pos, uv2[80].y));
        shape.vertex(verts2[30].x + x, verts2[30].y + y, verts2[30].z + z, getUVTextureCoord_X(pos, uv2[79].x), getUVTextureCoord_Y(pos, uv2[79].y));
        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[75].x), getUVTextureCoord_Y(pos, uv2[75].y));//FACE

        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[82].x), getUVTextureCoord_Y(pos, uv2[82].y));
        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[81].x), getUVTextureCoord_Y(pos, uv2[81].y));
        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));//FACE

        shape.vertex(verts2[37].x + x, verts2[37].y + y, verts2[37].z + z, getUVTextureCoord_X(pos, uv2[84].x), getUVTextureCoord_Y(pos, uv2[84].y));
        shape.vertex(verts2[25].x + x, verts2[25].y + y, verts2[25].z + z, getUVTextureCoord_X(pos, uv2[83].x), getUVTextureCoord_Y(pos, uv2[83].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));//FACE

        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[76].x), getUVTextureCoord_Y(pos, uv2[76].y));
        shape.vertex(verts2[37].x + x, verts2[37].y + y, verts2[37].z + z, getUVTextureCoord_X(pos, uv2[84].x), getUVTextureCoord_Y(pos, uv2[84].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));//FACE

        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[76].x), getUVTextureCoord_Y(pos, uv2[76].y));
        shape.vertex(verts2[36].x + x, verts2[36].y + y, verts2[36].z + z, getUVTextureCoord_X(pos, uv2[112].x), getUVTextureCoord_Y(pos, uv2[112].y));
        shape.vertex(verts2[37].x + x, verts2[37].y + y, verts2[37].z + z, getUVTextureCoord_X(pos, uv2[84].x), getUVTextureCoord_Y(pos, uv2[84].y));//FACE

        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));
        shape.vertex(verts2[28].x + x, verts2[28].y + y, verts2[28].z + z, getUVTextureCoord_X(pos, uv2[78].x), getUVTextureCoord_Y(pos, uv2[78].y));
        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[76].x), getUVTextureCoord_Y(pos, uv2[76].y));//FACE

        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));
        shape.vertex(verts2[34].x + x, verts2[34].y + y, verts2[34].z + z, getUVTextureCoord_X(pos, uv2[113].x), getUVTextureCoord_Y(pos, uv2[113].y));
        shape.vertex(verts2[28].x + x, verts2[28].y + y, verts2[28].z + z, getUVTextureCoord_X(pos, uv2[78].x), getUVTextureCoord_Y(pos, uv2[78].y));//FACE

        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));
        shape.vertex(verts2[38].x + x, verts2[38].y + y, verts2[38].z + z, getUVTextureCoord_X(pos, uv2[114].x), getUVTextureCoord_Y(pos, uv2[114].y));
        shape.vertex(verts2[39].x + x, verts2[39].y + y, verts2[39].z + z, getUVTextureCoord_X(pos, uv2[80].x), getUVTextureCoord_Y(pos, uv2[80].y));//FACE

        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[75].x), getUVTextureCoord_Y(pos, uv2[75].y));
        shape.vertex(verts2[32].x + x, verts2[32].y + y, verts2[32].z + z, getUVTextureCoord_X(pos, uv2[115].x), getUVTextureCoord_Y(pos, uv2[115].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[82].x), getUVTextureCoord_Y(pos, uv2[82].y));//FACE

        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));
        shape.vertex(verts2[39].x + x, verts2[39].y + y, verts2[39].z + z, getUVTextureCoord_X(pos, uv2[80].x), getUVTextureCoord_Y(pos, uv2[80].y));
        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[75].x), getUVTextureCoord_Y(pos, uv2[75].y));//FACE

        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));
        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[76].x), getUVTextureCoord_Y(pos, uv2[76].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));//FACE

        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[75].x), getUVTextureCoord_Y(pos, uv2[75].y));
        shape.vertex(verts2[22].x + x, verts2[22].y + y, verts2[22].z + z, getUVTextureCoord_X(pos, uv2[82].x), getUVTextureCoord_Y(pos, uv2[82].y));
        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));//FACE

        shape.vertex(verts2[27].x + x, verts2[27].y + y, verts2[27].z + z, getUVTextureCoord_X(pos, uv2[77].x), getUVTextureCoord_Y(pos, uv2[77].y));
        shape.vertex(verts2[26].x + x, verts2[26].y + y, verts2[26].z + z, getUVTextureCoord_X(pos, uv2[116].x), getUVTextureCoord_Y(pos, uv2[116].y));
        shape.vertex(verts2[33].x + x, verts2[33].y + y, verts2[33].z + z, getUVTextureCoord_X(pos, uv2[76].x), getUVTextureCoord_Y(pos, uv2[76].y));//FACE

        shape.vertex(verts2[30].x + x, verts2[30].y + y, verts2[30].z + z, getUVTextureCoord_X(pos, uv2[79].x), getUVTextureCoord_Y(pos, uv2[79].y));
        shape.vertex(verts2[29].x + x, verts2[29].y + y, verts2[29].z + z, getUVTextureCoord_X(pos, uv2[117].x), getUVTextureCoord_Y(pos, uv2[117].y));
        shape.vertex(verts2[31].x + x, verts2[31].y + y, verts2[31].z + z, getUVTextureCoord_X(pos, uv2[75].x), getUVTextureCoord_Y(pos, uv2[75].y));//FACE

        shape.vertex(verts2[21].x + x, verts2[21].y + y, verts2[21].z + z, getUVTextureCoord_X(pos, uv2[81].x), getUVTextureCoord_Y(pos, uv2[81].y));
        shape.vertex(verts2[20].x + x, verts2[20].y + y, verts2[20].z + z, getUVTextureCoord_X(pos, uv2[118].x), getUVTextureCoord_Y(pos, uv2[118].y));
        shape.vertex(verts2[35].x + x, verts2[35].y + y, verts2[35].z + z, getUVTextureCoord_X(pos, uv2[74].x), getUVTextureCoord_Y(pos, uv2[74].y));//FACE

        shape.vertex(verts2[25].x + x, verts2[25].y + y, verts2[25].z + z, getUVTextureCoord_X(pos, uv2[83].x), getUVTextureCoord_Y(pos, uv2[83].y));
        shape.vertex(verts2[24].x + x, verts2[24].y + y, verts2[24].z + z, getUVTextureCoord_X(pos, uv2[119].x), getUVTextureCoord_Y(pos, uv2[119].y));
        shape.vertex(verts2[23].x + x, verts2[23].y + y, verts2[23].z + z, getUVTextureCoord_X(pos, uv2[73].x), getUVTextureCoord_Y(pos, uv2[73].y));//FACE
    }

    //</editor-fold>
//</editor-fold>
    @Override
    public void constructBlock(BlockMesh_Base buffers, Block block, BlockData data, Block negativeX, Block positiveX, Block negativeY, Block positiveY, Block negativeZ, Block positiveZ, int x, int y, int z) {
        if (renderSideSubBlock(block, negativeY)) {
            make_pillar_negativeY_faces(verts_pillar, uv_pillar, block, buffers, x, y, z);
        }
        if (renderSideSubBlock(block, positiveY)) {
            make_pillar_positiveY_faces(verts_pillar, uv_pillar, block, buffers, x, y, z);
        }
        make_pillar_center_faces(verts_pillar, uv_pillar, block, buffers, x, y, z);
    }

    private final float sixteenthConstant = 0.0625f;
    private final float width = 1 - (sixteenthConstant * 4);

    @Override
    public void getCollisionBoxes(Consumer<AABB> consumer, AABB box, Block block, BlockData data, int x, int y, int z) {
        box.setPosAndSize(x + (sixteenthConstant * 2), y, z + (sixteenthConstant * 2), width, 1, width);
        consumer.accept(box);
    }
}
