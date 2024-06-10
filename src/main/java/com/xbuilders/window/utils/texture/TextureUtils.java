/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.xbuilders.window.utils.texture;

import com.jogamp.opengl.GL4;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;


/**
 * @author zipCoder933
 */
public class TextureUtils {

    /**
     * Converts a BufferedImage to a ByteBuffer
     *
     * @param bufferedImage
     * @return
     */
    private static ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ColorModel glAlphaColorModel = new ComponentColorModel(
                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8, 8},
                true,
                false,
                Transparency.TRANSLUCENT,
                DataBuffer.TYPE_BYTE
        );

        WritableRaster raster = Raster.createInterleavedRaster(
                DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                4,
                null
        );

        BufferedImage texImage = new BufferedImage(
                glAlphaColorModel,
                raster,
                true,
                new Hashtable<>()
        );

        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, 256, 256);
        g.drawImage(bufferedImage, 0, 0, null);

        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
        ByteBuffer imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }


    public static Texture loadTexture(GL4 gl, String resourceName, boolean linearFiltering) throws IOException {
        IntBuffer textureID = IntBuffer.allocate(1);
        BufferedImage img = ImageIO.read(new File(resourceName));
        ByteBuffer imageBuffer = convertImageData(img);

        gl.glGenTextures(1, textureID);//Generate the texture ID
        gl.glBindTexture(gl.GL_TEXTURE_2D, textureID.get(0)); //Bind the texture ID

        gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, gl.GL_RGBA, img.getWidth(), img.getHeight(), 0, gl.GL_RGBA, gl.GL_UNSIGNED_BYTE, imageBuffer); //Load the texture

        gl.glPixelStorei(gl.GL_UNPACK_ALIGNMENT, 1); //1 byte per pixel

        if (linearFiltering) {
            gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);// When MAGnifying the image (no bigger mipmap available), use LINEAR filtering
            gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);// When MINifying the image, use a LINEAR blend of two mipmaps, each filtered LINEARLY too
        } else {
            gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_NEAREST);
            gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_NEAREST);
        }

        gl.glGenerateMipmap(gl.GL_TEXTURE_2D);// Generate mipmap
        gl.glBindTexture(gl.GL_TEXTURE_2D, 0); //Unbind the texture ID

        return new Texture(textureID.get(0), img.getWidth(), img.getHeight());
    }


}
