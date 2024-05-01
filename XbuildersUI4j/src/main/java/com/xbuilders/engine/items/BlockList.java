/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.BlockAir;
import com.xbuilders.game.items.blockType.BlockRenderType;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.items.block.construction.blockTypes.DefaultBlockType;
import com.xbuilders.engine.items.block.construction.texture.BlockTextureAtlas;
import com.xbuilders.engine.utils.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.xbuilders.engine.utils.ResourceUtils.LOCAL_DIR;

/**
 * @author zipCoder933
 */
public class BlockList extends ItemGroup<Block> {

    public final BlockTextureAtlas textureAtlas;
    private final HashMap<Integer, BlockType> blockTypes;

    public final static int DEFAULT_BLOCK_TYPE_ID = 0;
    public final static DefaultBlockType defaultBlockType = new DefaultBlockType();

    public BlockList() throws IOException {
        textureAtlas = new BlockTextureAtlas();
        blockTypes = new HashMap<>();
        blockTypes.put(DEFAULT_BLOCK_TYPE_ID, defaultBlockType);
    }

    public BlockType getBlockType(int typeID) {
        BlockType type = blockTypes.get(typeID);
        return type;
    }

    public void addBlockType(int typeID, BlockType type) {
        if (blockTypes.containsKey(typeID)) {
            throw new IllegalArgumentException("Type ID " + DEFAULT_BLOCK_TYPE_ID + " already in use");
        }
        blockTypes.put(typeID, type);
    }

    public static final BlockAir BLOCK_AIR = new BlockAir();

    @Override
    public void setItems(Block[] inputBlocks) {
        assignIDMapAndCheckIDs(inputBlocks);
        idMap.put(BLOCK_AIR.id, BLOCK_AIR);
        itemList = new Block[getIdMap().size() + 1];
        itemList[0] = BLOCK_AIR;
        int i = 1;
        //Initialize all blocks
        for (Block block : getIdMap().values()) {
            itemList[i] = block;
            i++;
        }
    }


}
