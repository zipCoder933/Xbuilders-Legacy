/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items;

import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.BlockAir;
import com.xbuilders.engine.utils.IntMap;
import com.xbuilders.engine.items.block.construction.blockTypes.BlockType;
import com.xbuilders.engine.items.block.construction.blockTypes.DefaultBlockType;
import com.xbuilders.engine.items.block.construction.texture.BlockTextureAtlas;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author zipCoder933
 */
public class BlockList extends ItemGroup<Block> {

    public final BlockTextureAtlas textureAtlas;
    private final IntMap<BlockType> blockTypes = new IntMap<>(BlockType.class);
    private final HashMap<Integer, BlockType> blockTypesPrivateHashmap = new HashMap<>();

    public final static int DEFAULT_BLOCK_TYPE_ID = 0;
    public final static DefaultBlockType defaultBlockType = new DefaultBlockType();
    public static final BlockAir BLOCK_AIR = new BlockAir();

    public BlockList() throws IOException {
        super(Block.class);
        textureAtlas = new BlockTextureAtlas();
        blockTypesPrivateHashmap.put(DEFAULT_BLOCK_TYPE_ID, defaultBlockType);
        blockTypes.setList(blockTypesPrivateHashmap);
    }

    public BlockType getBlockType(int typeID) {
        BlockType type = blockTypes.get(typeID);
        return type;
    }

    public void addBlockType(int typeID, BlockType type) {
        if (blockTypes.get(typeID) != null) {
            throw new IllegalArgumentException("Type ID " + DEFAULT_BLOCK_TYPE_ID + " already in use");
        }
        blockTypesPrivateHashmap.put(typeID, type);
        blockTypes.setList(blockTypesPrivateHashmap);
    }


    @Override
    public void setAndInitItems(Block[] inputBlocks) {
        Block[] inputBlocks2 = new Block[inputBlocks.length + 1];
        inputBlocks2[0] = BLOCK_AIR; //Add the default block first
        for (int i = 1; i < inputBlocks2.length; i++) {
            inputBlocks2[i] = inputBlocks[i - 1];
        }
        assignItems(inputBlocks2);
    }


}
