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


    //<editor-fold defaultstate="collapsed" desc="Export blocks to XBuilders 3">
    private String textureName(Block block) {
        String texture =
                block.name.toLowerCase()
                        .replaceAll("hidden", "")
                        .replaceAll("stairs", "")
                        .replaceAll("steps", "")
                        .replaceAll("slab", "")
                        .replaceAll("fence", "")
                        .replaceAll("pillar", "")
                        .replaceAll("post", "")
                        .replaceAll("block", "")
                        .replaceAll("pane", "")
                        .replaceAll("pillar", "")
                        .replaceAll("-", " ")
                        .replaceAll("[^a-zA-Z0-9\\s_]+", "").trim();
        return texture;
    }

    static class AtlasPosition {
        int[] pos;

        public AtlasPosition(int[] pos) {
            this.pos = pos;
        }

        //Hashcode and equals
        @Override
        public int hashCode() {
            return Arrays.hashCode(pos);
        }

        @Override
        public boolean equals(Object obj) {
            final AtlasPosition other = (AtlasPosition) obj;
            return this.pos[0] == other.pos[0] && this.pos[1] == other.pos[1];
        }
    }

    public void exportListToXbuilders3() throws IOException {
        String illegalCharsRegex = "[<>:\"/\\|?*]";
        Map<AtlasPosition, String> textureMap = new HashMap<>();

        File outputFile = ResourceUtils.resource("Exported XB3 blocks\\blocks.java");
        File iconsDir = ResourceUtils.resource("Exported XB3 blocks\\textures");
        String outputString = "";
        String listString = "";
        int i = 0;
        for (Block block : itemList) {
            String formattedName = "BLOCK_" +
                    block.name.toUpperCase().replaceAll("HIDDEN", "")
                            .replaceAll("-", " ")
                            .replaceAll("[^a-zA-Z0-9\\s_]+", "").trim()
                            .replaceAll(" ", "_");

            if (block.texture == null) {
                System.out.println("No texture for " + block.name);
                outputString += "public static final Block "
                        + formattedName
                        + " = new Block(" + block.id + ", \"" + block.name + "\", null, "
                        + block.isSolid() + ", " + block.isOpaque()
                        + ", " + getMatchingRenderType(block.type) + ");\n";
            } else {
                String topTexture = textureName(block);
                String bottomTexture = textureName(block);
                String sidesTexture = textureName(block);


                if (textureMap.containsKey(new AtlasPosition(block.texture.TOP))) {
                    topTexture = textureMap.get(new AtlasPosition(block.texture.TOP));
                    topTexture += ".png";
                } else {
                    textureMap.put(new AtlasPosition(block.texture.TOP), topTexture);

                    topTexture += ".png";
                    writeTextureAtlasFile(iconsDir, topTexture, block.getAnimationLength(), block.texture.TOP);
                }


                if (textureMap.containsKey(new AtlasPosition(block.texture.FRONT))) {
                    sidesTexture = textureMap.get(new AtlasPosition(block.texture.FRONT));
                    sidesTexture += ".png";
                } else {
                    if (block.texture.FRONT[0] != block.texture.TOP[0] ||
                            block.texture.FRONT[1] != block.texture.TOP[1]) {
                        sidesTexture += " side";
                    }
                    textureMap.put(new AtlasPosition(block.texture.FRONT), sidesTexture);

                    sidesTexture += ".png";
                    writeTextureAtlasFile(iconsDir, sidesTexture, block.getAnimationLength(), block.texture.FRONT);
                }


                if (textureMap.containsKey(new AtlasPosition(block.texture.BOTTOM))) {
                    bottomTexture = textureMap.get(new AtlasPosition(block.texture.BOTTOM));
                    bottomTexture += ".png";
                } else {
                    if (block.texture.BOTTOM[0] != block.texture.TOP[0] ||
                            block.texture.BOTTOM[1] != block.texture.TOP[1]) {
                        bottomTexture += " bottom";
                    }
                    textureMap.put(new AtlasPosition(block.texture.BOTTOM), bottomTexture);

                    bottomTexture += ".png";
                    writeTextureAtlasFile(iconsDir, bottomTexture, block.getAnimationLength(), block.texture.BOTTOM);
                }


                if (block.type == BlockList.DEFAULT_BLOCK_TYPE_ID) {
                    outputString += "public static final Block "
                            + formattedName
                            + " = new Block(" + block.id + ", \"" + block.name + "\", "
                            + "new BlockTexture(\"" + topTexture + "\", \"" + bottomTexture +
                            "\", \"" + sidesTexture + "\"), " + block.isSolid() + ", " + block.isOpaque() + ");\n";

                } else {
                    outputString += "public static final Block "
                            + formattedName
                            + " = new Block(" + block.id + ", \"" + block.name + "\", "
                            + "new BlockTexture(\"" + topTexture + "\", \"" + bottomTexture + "\", \"" + sidesTexture + "\"), " + block.isSolid() + ", " + block.isOpaque()
                            + ", " + getMatchingRenderType(block.type) + ");\n";
                }
            }
            listString += formattedName + ", ";
            if (i % 50 == 0) {
                listString += "\n";
            }
            i++;
        }
        Files.writeString(outputFile.toPath(), outputString + "\n\n\n" + listString);
        System.out.println("Xbuilders 3 blocks exported to " + outputFile.getAbsolutePath());
    }

    private String getMatchingRenderType(int type) {
        switch (type) {
            case BlockRenderType.SLAB:
                return "BlockRenderType.SLAB";
            case BlockRenderType.STAIRS:
                return "BlockRenderType.STAIRS";
            case BlockRenderType.ORIENTABLE_BLOCK:
                return "BlockRenderType.ORIENTABLE_BLOCK";
            case BlockRenderType.SPRITE:
                return "BlockRenderType.SPRITE";
            case BlockRenderType.PILLAR:
                return "BlockRenderType.PILLAR";
            case BlockRenderType.PANE:
                return "BlockRenderType.PANE";
            case BlockRenderType.WIRE:
                return "BlockRenderType.WIRE";
            case BlockRenderType.LAMP:
                return "BlockRenderType.LAMP";
            case BlockRenderType.WALL_ITEM:
                return "BlockRenderType.WALL_ITEM";
            case BlockRenderType.FENCE:
                return "BlockRenderType.FENCE";
            case BlockRenderType.FLOOR:
                return "BlockRenderType.FLOOR";
            case BlockRenderType.TORCH:
                return "BlockRenderType.TORCH";
            case BlockRenderType.LIQUID:
                return "BlockRenderType.LIQUID";
            case BlockRenderType.TRACK:
                return "BlockRenderType.TRACK";
            case BlockRenderType.SUNFLOWER_HEAD:
                return "BlockRenderType.SUNFLOWER_HEAD";
            default:
                return "BlockList.DEFAULT_BLOCK_TYPE_ID";
        }
    }

    private void writeTextureAtlasFile(File iconsDir, String topTexture, int animation, int[] atlas) throws IOException {
        File outputTexture = new File(iconsDir, topTexture);
        if (outputTexture.exists()) {
            return;
        }
        if (animation < 1) {
            animation = 1;
        }

        int x = atlas[0] * 16; // example x-coordinate
        int y = atlas[1] * 16;  // example y-coordinate
        int width = 16; // example width
        int height = 16 * animation; // example height

        // Extract the specified region from the original image
        BufferedImage croppedImage = (BufferedImage) textureAtlas.getImage().getImage();
        croppedImage = croppedImage.getSubimage(x, y, width, height);

        outputTexture.getParentFile().mkdirs();
        outputTexture.createNewFile();

        // Save the cropped image to a file
        ImageIO.write(croppedImage, "png", outputTexture);
    }

    //Generate separate icons
     private void separateIcons(File iconsDir) throws IOException {

        System.out.println(iconsDir.getAbsolutePath());
        BufferedImage image = ImageIO.read(iconsDir);

        for (int x = 0; x < image.getWidth() / 16; x++) {
            for (int y = 0; y < image.getHeight() / 16; y++) {
                int x2 = x * 16; // example x-coordinate
                int y2 = y * 16;  // example y-coordinate

                File outputTexture = new File(iconsDir.getParentFile(), "icon_" + x + "_" + y + ".png");

                BufferedImage subImage = image.getSubimage(x2, y2, 16, 16);
                //Check if subImage is all transparent
                if (!isAllTransparentPixels(subImage)) {
                    outputTexture.getParentFile().mkdirs();
                    outputTexture.createNewFile();
                    ImageIO.write(subImage, "png", outputTexture);
                }
            }
        }

    }

    private boolean isAllTransparentPixels(BufferedImage subImage) {
        for (int x = 0; x < subImage.getWidth(); x++) {
            for (int y = 0; y < subImage.getHeight(); y++) {
                if (subImage.getRGB(x, y) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
//</editor-fold>
}
