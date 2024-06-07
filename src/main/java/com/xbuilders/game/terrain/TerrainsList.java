package com.xbuilders.game.terrain;

import com.xbuilders.game.terrain.cityTerrain.CityTerrain;
import com.xbuilders.game.terrain.defaultTerrain.TerrainV1;
import com.xbuilders.game.terrain.defaultTerrain.TerrainV2;

import java.io.OptionalDataException;

public class TerrainsList {
    public static final String[] terrains = {
            "DEFAULT_V2", "DEFAULT_V2_CAVES",
            "MOON", "MOON_SHALLOW",
            "SIMPLE", "SIMPLE_DEEP",
            "DEV",
            "CITY",
            "DEFAULT_V1", "DEFAULT_V1_CAVES"};

    public static String[] visibleTerrains = {
            "DEFAULT_V2", "DEFAULT_V2_CAVES",
            "MOON", "MOON_SHALLOW",
            "SIMPLE", "SIMPLE_DEEP",
            "CITY"};

    public static Terrain getTerrain(String name) {
        switch (name) {
            case "SIMPLE" -> {
                return new SimpleTerrain();
            }
            case "SIMPLE_DEEP" -> {
                return new SimpleTerrainDeep();
            }
            case "MOON" -> {
                return new MoonTerrain(true);
            }
            case "MOON_SHALLOW" -> {
                return new MoonTerrain(false);
            }
            case "DEV" -> {
                return new DevTerrain();
            }
            case "CITY" -> {
                return new CityTerrain();
            }
            case "DEFAULT_V1" -> {
                return new TerrainV1(false);
            }
            case "DEFAULT" -> {
                return new TerrainV1(false);
            }
            case "DEFAULT_V1_CAVES" -> {
                return new TerrainV1(true);
            }
            case "DEFAULT_V2_CAVES" -> {
                return new TerrainV1(true);
            }
            default -> {
                return new TerrainV2(false, false);
            }
        }
    }

    public static String getTerrain(int index) {
        try {
            return terrains[index];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
