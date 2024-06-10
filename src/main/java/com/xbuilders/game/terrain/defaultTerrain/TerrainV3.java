//
// Decompiled by Procyon v0.5.36
//
package com.xbuilders.game.terrain.defaultTerrain;

import com.xbuilders.engine.utils.math.MathUtils;

public class TerrainV3 extends TerrainV2 {

    public TerrainV3(boolean cave) {
        super(cave);
        caveFrequency = 5.0f;
    }

    @Override
    public int getTerrainHeight(float valley, final int wx, final int wz) {
        int val = (int) (getValueFractal((float) wx, (float) wz) * 50f);

        if (val > OCEAN_THRESH) {
            val = (int) (((val - OCEAN_THRESH) * 1.5f) + OCEAN_THRESH);
        }
        //else if (val < -MOUNTAIN_THRESH) {
//            val = (int) (((val + MOUNTAIN_THRESH) * 2f) - MOUNTAIN_THRESH);
//        }
        if (val < 0) {//If the height value is less than 0, normalize it
            val = (int) MathUtils.map(valley, -1, 1, val, val / 10f);//Normalize for valleys
        }
        return WORLD_HEIGHT_OFFSET + val;
    }

}