package com.xbuilders.engine.items.block.construction.texture;

import java.util.Arrays;

public class BlockTexture {

    public int[] TOP, FRONT, BACK, LEFT, RIGHT, BOTTOM;

    public BlockTexture() {
        TOP = new int[]{0, 0};
        FRONT = new int[]{0, 0};
        BACK = new int[]{0, 0};
        LEFT = new int[]{0, 0};
        RIGHT = new int[]{0, 0};
        BOTTOM = new int[]{0, 0};
    }

    public BlockTexture(int indexX, int indexY) {
        TOP = new int[]{indexX, indexY};
        BOTTOM = new int[]{indexX, indexY};
        FRONT = new int[]{indexX, indexY};
        BACK = new int[]{indexX, indexY};
        LEFT = new int[]{indexX, indexY};
        RIGHT = new int[]{indexX, indexY};
    }

    public BlockTexture(int topX, int topY, int bottomX, int bottomY, int sidesX, int sidesY) {
        TOP = new int[]{topX, topY};
        BOTTOM = new int[]{bottomX, bottomY};
        FRONT = new int[]{sidesX, sidesY};
        BACK = new int[]{sidesX, sidesY};
        LEFT = new int[]{sidesX, sidesY};
        RIGHT = new int[]{sidesX, sidesY};
    }

    public BlockTexture(int topX, int topY,
                        int bottomX, int bottomY,
                        int FrontX, int FrontY,
                        int BackX, int BackY,
                        int leftX, int leftY,
                        int rightX, int rightY) {
        TOP = new int[]{topX, topY};
        BOTTOM = new int[]{bottomX, bottomY};
        FRONT = new int[]{FrontX, FrontY};
        BACK = new int[]{BackX, BackY};
        LEFT = new int[]{leftX, leftY};
        RIGHT = new int[]{rightX, rightY};
    }

    public BlockTexture(int top_bottomX, int top_bottomY, int sidesX, int sidesY) {
        TOP = new int[]{top_bottomX, top_bottomY};
        BOTTOM = new int[]{top_bottomX, top_bottomY};
        FRONT = new int[]{sidesX, sidesY};
        BACK = new int[]{sidesX, sidesY};
        LEFT = new int[]{sidesX, sidesY};
        RIGHT = new int[]{sidesX, sidesY};
    }

    public BlockTexture(BlockTexture tex) {
        TOP = new int[]{tex.TOP[0], tex.TOP[1]};
        FRONT = new int[]{tex.FRONT[0], tex.FRONT[1]};
        BOTTOM = new int[]{tex.BOTTOM[0], tex.BOTTOM[1]};
        LEFT = new int[]{tex.LEFT[0], tex.LEFT[1]};
        RIGHT = new int[]{tex.RIGHT[0], tex.RIGHT[1]};
        BACK = new int[]{tex.BACK[0], tex.BACK[1]};
    }

    /*For some reason does not work...*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        BlockTexture other = (BlockTexture) obj;
        if (!Arrays.equals(this.TOP, other.TOP)) {
            return false;
        }
        if (!Arrays.equals(this.FRONT, other.FRONT)) {
            return false;
        }
        return Arrays.equals(this.BOTTOM, other.BOTTOM);

//            return TOP[0] == other.TOP[0]
//                    && TOP[1] == other.TOP[1]
//                    && SIDES[0] == other.SIDES[0]
//                    && SIDES[1] == other.SIDES[1]
//                    && BOTTOM[0] == other.BOTTOM[0]
//                    && BOTTOM[1] == other.BOTTOM[1];
    }


    public void setTOP(int x, int y) {
        this.TOP[0] = x;
        this.TOP[1] = y;
    }

    public void setBOTTOM(int x, int y) {
        this.BOTTOM[0] = x;
        this.BOTTOM[1] = y;
    }

    public void setSIDES(int x, int y) {
        this.FRONT[0] = x;
        this.FRONT[1] = y;
        this.BACK[0] = x;
        this.BACK[1] = y;
        this.LEFT[0] = x;
        this.LEFT[1] = y;
        this.RIGHT[0] = x;
        this.RIGHT[1] = y;
    }

    public void setFRONT(int x, int y) {
        this.FRONT[0] = x;
        this.FRONT[1] = y;
    }

    public void setBACK(int x, int y) {
        this.BACK[0] = x;
        this.BACK[1] = y;
    }

    public void setLEFT(int x, int y) {
        this.LEFT[0] = x;
        this.LEFT[1] = y;
    }

    public void setRIGHT(int x, int y) {
        this.RIGHT[0] = x;
        this.RIGHT[1] = y;
    }

    public String toString() {
        return Arrays.toString(TOP) + " " + Arrays.toString(FRONT) + " " + Arrays.toString(BOTTOM)
                + " " + Arrays.toString(LEFT) + " " + Arrays.toString(RIGHT) + " " + Arrays.toString(BACK);
    }


}
