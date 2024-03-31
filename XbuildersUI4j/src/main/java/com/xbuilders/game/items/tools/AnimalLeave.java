package com.xbuilders.game.items.tools;

import com.xbuilders.engine.VoxelGame;
import com.xbuilders.engine.game.GameScene;
import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.items.tool.Tool;
import com.xbuilders.engine.world.chunk.SubChunk;
import com.xbuilders.engine.world.chunk.wcc.WCCi;
import com.xbuilders.game.Main;
import com.xbuilders.game.items.entities.mobile.Animal;
import org.joml.Vector3i;

public class AnimalLeave extends Tool {

    public AnimalLeave() {
        super(7, "Ask Animals to Leave");
        setIconAtlasPosition(0, 3);
    }

    public boolean isInfiniteResource() {
        return true;
    }

    @Override
    public boolean onPlace(int x, int y, int z) {
        WCCi wcc = new WCCi();
        wcc.set(x, y, z);
        for (int x2 = -20; x2 < 21; x2++) {
            for (int y2 = -20; y2 < 21; y2++) {
                for (int z2 = -20; z2 < 21; z2++) {
                    SubChunk sc = VoxelGame.getWorld().getSubChunk(
                            new Vector3i(
                                    wcc.subChunk.x + y2,
                                    wcc.subChunk.y + y2,
                                    wcc.subChunk.z + z2));
                    if (sc != null) {
                        for (Entity e : sc.entities.list) {
                            if (e instanceof Animal) {
                                if (Main.getSettings().getSettingsFile().additionalFeatures) {
                                    e.destroy(true);
                                }
                                Animal a = (Animal) e;
                                a.makeAnimalLeave(true);
                            }
                        }
                    }
                }
            }
        }
        VoxelGame.getGame().alert("\"Leave, animals!\"");
        return false;
    }

}
