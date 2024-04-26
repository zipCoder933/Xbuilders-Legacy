package com.xbuilders.engine.utils.imageAtlas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import processing.core.PImage;

public class ImageAtlas {

    /**
     * @return the individualTextureSize
     */
    public int getIndividualTextureSize() {
        return individualTextureSize;
    }

    /**
     * @return the imageSize
     */
    public int getImageWidth() {
        return imageSize;
    }

    /**
     * @return the image
     */
    public PImage getImage() {
        return image;
    }

    private PImage image;
    private int imageSize;
    private final int individualTextureSize = 16;


    public ImageAtlas(File imagePath) throws IOException {
        //atlasRows=32 for a 512x512 image with 16x16 blocks each
        if (!imagePath.exists()) {
            throw new IOException("The image \"" + imagePath.getAbsolutePath() + "\" does not exist!");
        }
        BufferedImage img = ImageIO.read(imagePath);

        if (img.getWidth() != img.getHeight()) {
            throw new IOException("The image \"" + imagePath.getAbsolutePath() + "\" is not square!");
        } else if (img.getWidth() % getIndividualTextureSize() != 0) {
            throw new IOException("The image \"" + imagePath.getAbsolutePath() +
                    "\" is not divisible by " + getIndividualTextureSize() + "!");
        }
        image = new PImage(img);
        imageSize = image.width;
    }

    public ImageAtlasPosition getImageIndex(int[] pos) {
        float texturePerRow = (float) getImageWidth() / (float) getIndividualTextureSize();
        float indvTexSize = 1.0f / texturePerRow;
        float pixelSize = 1.0f / (float) getImageWidth();

        float xMin = (pos[0] * indvTexSize) + 0.0f * pixelSize;
        float yMin = (pos[1] * indvTexSize) + 0.0f * pixelSize;

        float xMax = (xMin + indvTexSize) - 0.0f * pixelSize;
        float yMax = (yMin + indvTexSize) - 0.0f * pixelSize;
        return new ImageAtlasPosition(xMin, yMin, xMax, yMax);
    }
}
