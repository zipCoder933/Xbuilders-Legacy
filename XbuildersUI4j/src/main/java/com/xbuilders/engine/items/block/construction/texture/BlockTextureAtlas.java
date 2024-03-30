package com.xbuilders.engine.items.block.construction.texture;

import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.imageAtlas.ImageAtlas;
import com.xbuilders.engine.utils.imageAtlas.ImageAtlasPosition;
import java.io.IOException;
import processing.core.PApplet;

public class BlockTextureAtlas extends ImageAtlas {

    public BlockTextureAtlas() throws IOException {
        super(ResourceUtils.resource("items\\blocks\\textures.png"), 32);
    }

    public ImageAtlasPosition getTextureIndex(int[] pos) {
        return getImageIndex(pos);
    }

}
