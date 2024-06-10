package com.xbuilders.game.terrain;

import com.xbuilders.game.terrain.cityTerrain.CityTerrain;
import com.xbuilders.game.terrain.defaultTerrain.TerrainV1;
import com.xbuilders.game.terrain.defaultTerrain.TerrainV2;

import java.util.HashMap;

public class TerrainsList {
    private static HashMap<String, Terrain> terrainsMap = new HashMap<>();

    public static String[] terrainList;

    public static String[] visibleTerrainList = {
            "DEFAULT_V2", "DEFAULT_V2_CAVES",
            "MOON", "MOON_SHALLOW",
            "SIMPLE", "SIMPLE_DEEP"};

    static {
        terrainsMap.put("SIMPLE", new SimpleTerrain());
        terrainsMap.put("SIMPLE_DEEP", new SimpleTerrainDeep());

        terrainsMap.put("MOON", new MoonTerrain(true));
        terrainsMap.put("MOON_SHALLOW", new MoonTerrain(false));

        terrainsMap.put("DEV", new DevTerrain());
        terrainsMap.put("CITY", new CityTerrain());

        //V1
        terrainsMap.put("DEFAULT_V1", new TerrainV1(false));
        terrainsMap.put("DEFAULT", new TerrainV1(false));
        terrainsMap.put("DEFAULT_V1_CAVES", new TerrainV1(true));

        //V2
        terrainsMap.put("DEFAULT_V2", new TerrainV2(false));

        //V3
//        terrainsMap.put("DEFAULT_V3", new TerrainV3(false)); //Lets wait for v3
//        terrainsMap.put("DEFAULT_V3_CAVES", new TerrainV3(true));

        //Set terrains list
        terrainList = terrainsMap.keySet().toArray(new String[0]);
    }


    public static Terrain getTerrain(String name) {
        if (terrainsMap.containsKey(name)) {
            return terrainsMap.get(name);
        }
        return new SimpleTerrain(); //Our default terrain
    }

    public static String getTerrain(int index) {
        try {
            return terrainList[index];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}