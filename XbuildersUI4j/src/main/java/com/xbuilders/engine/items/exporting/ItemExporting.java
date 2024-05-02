package com.xbuilders.engine.items.exporting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xbuilders.engine.items.BlockList;
import com.xbuilders.engine.items.Item;
import com.xbuilders.engine.items.block.Block;
import com.xbuilders.engine.items.block.construction.texture.BlockTexture;
import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.imageAtlas.ImageAtlas;
import com.xbuilders.game.items.blockType.BlockRenderType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static com.xbuilders.engine.utils.ResourceUtils.LOCAL_DIR;

public class ItemExporting {

    //<editor-fold defaultstate="collapsed" desc="Export blocks to XBuilders 3">
    private static String textureName(Block block) {
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

    public static void exportListToXbuilders3(ImageAtlas atlas, Block[] itemList) throws IOException {
        File outputDir = new File(LOCAL_DIR.getParentFile().getParentFile(), "Exported XB3 blocks");

        //Export doors
        File doorsPath = ResourceUtils.resource("items\\entities\\door\\textures");
        for (File subDir : doorsPath.listFiles()) {
            if (subDir.isDirectory()) {
                System.out.println("Subdir: " + subDir.getName());
                for (File file : subDir.listFiles()) {
                    separateIcons(file, new File(outputDir, "doors"), file.getName());
                }
            } else {
                separateIcons(subDir, new File(outputDir, "doors"), subDir.getName());
            }
        }

        Map<AtlasPosition, String> textureMap = new HashMap<>();
        File blockTextureDir = new File(outputDir, "textures");
        for (Block block : itemList) {
            if (block.texture == null) {
                System.out.println("No texture for " + block.name);
            } else {


                if (!textureMap.containsKey(new AtlasPosition(block.texture.TOP))) {
                    String name = textureName(block);
                    textureMap.put(new AtlasPosition(block.texture.TOP), name);
                    name += ".png";
                    writeTextureAtlasFile(atlas, blockTextureDir, name, block.getAnimationLength(), block.texture.TOP);
                }

                if (!textureMap.containsKey(new AtlasPosition(block.texture.FRONT))) {
                    String name = textureName(block);
                    if (block.texture.FRONT[0] != block.texture.TOP[0] ||
                            block.texture.FRONT[1] != block.texture.TOP[1]) {
                        name += " front";
                    }
                    textureMap.put(new AtlasPosition(block.texture.FRONT), name);
                    name += ".png";
                    writeTextureAtlasFile(atlas, blockTextureDir, name, block.getAnimationLength(), block.texture.FRONT);
                }

                if (!textureMap.containsKey(new AtlasPosition(block.texture.BACK))) {
                    String name = textureName(block);
                    if (block.texture.BACK[0] != block.texture.TOP[0] ||
                            block.texture.BACK[1] != block.texture.TOP[1]) {
                        name += " back";
                    }
                    textureMap.put(new AtlasPosition(block.texture.BACK), name);
                    name += ".png";
                    writeTextureAtlasFile(atlas, blockTextureDir, name, block.getAnimationLength(), block.texture.BACK);
                }


                if (!textureMap.containsKey(new AtlasPosition(block.texture.LEFT))) {
                    String name = textureName(block);
                    if (block.texture.LEFT[0] != block.texture.TOP[0] ||
                            block.texture.LEFT[1] != block.texture.TOP[1]) {
                        name += " left";
                    }
                    textureMap.put(new AtlasPosition(block.texture.LEFT), name);
                    name += ".png";
                    writeTextureAtlasFile(atlas, blockTextureDir, name, block.getAnimationLength(), block.texture.LEFT);
                }


                if (!textureMap.containsKey(new AtlasPosition(block.texture.RIGHT))) {
                    String name = textureName(block);
                    if (block.texture.RIGHT[0] != block.texture.TOP[0] ||
                            block.texture.RIGHT[1] != block.texture.TOP[1]) {
                        name += " right";
                    }
                    textureMap.put(new AtlasPosition(block.texture.RIGHT), name);
                    name += ".png";
                    writeTextureAtlasFile(atlas, blockTextureDir, name, block.getAnimationLength(), block.texture.RIGHT);
                }


                if (!textureMap.containsKey(new AtlasPosition(block.texture.BOTTOM))) {
                    String name = textureName(block);
                    if (block.texture.BOTTOM[0] != block.texture.TOP[0] ||
                            block.texture.BOTTOM[1] != block.texture.TOP[1]) {
                        name += " bottom";
                    }
                    textureMap.put(new AtlasPosition(block.texture.BOTTOM), name);
                    name += ".png";
                    writeTextureAtlasFile(atlas, blockTextureDir, name, block.getAnimationLength(), block.texture.BOTTOM);
                }
            }
        }

        //Write all blocks as json
        try {
            File jsonBlockFile = new File(outputDir, "blocks.json");

            XB3_BlockTextureTypeAdapter texture = new XB3_BlockTextureTypeAdapter(textureMap);
            Gson blockGson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Block.class, new XB3_BlockTypeAdapter(texture))
                    .registerTypeHierarchyAdapter(BlockTexture.class, texture)
                    .create();

            List<Block> jsonList = new ArrayList<>();

            for (Block block : itemList) {
                if (block == BlockList.BLOCK_AIR || block == null) continue;
                jsonList.add((Block) block); //jsonList
            }

            String jsonString = blockGson.toJson(jsonList,
                    new TypeToken<List<Block>>() {}.getType());
            Files.writeString(jsonBlockFile.toPath(), jsonString);
            System.out.println("Saved " + jsonList.size() + " blocks to " + jsonBlockFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMatchingRenderType(int type) {
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

    private static void writeTextureAtlasFile(ImageAtlas textureAtlas, File iconsDir, String name, int animation, int[] atlas) throws IOException {
        File outputTexture = new File(iconsDir, name);
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
    private static void separateIcons(File iconsDir, File outputDir, String name) throws IOException {

        System.out.println(iconsDir.getAbsolutePath());
        BufferedImage image = ImageIO.read(iconsDir);

        for (int x = 0; x < image.getWidth() / 16; x++) {
            for (int y = 0; y < image.getHeight() / 16; y++) {
                int x2 = x * 16; // example x-coordinate
                int y2 = y * 16;  // example y-coordinate

                File outputTexture = new File(outputDir,
                        name.toLowerCase()
                                .replace(".png", "")
                                .replace(".jpg", "")
                                + "_" + x + "_" + y + ".png");

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

    private static boolean isAllTransparentPixels(BufferedImage subImage) {
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
