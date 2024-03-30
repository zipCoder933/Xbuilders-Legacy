/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.icons;

import com.xbuilders.engine.utils.ResourceUtils;
import com.xbuilders.engine.utils.imageAtlas.ImageAtlas;
import java.io.File;
import java.io.IOException;
import processing.core.PApplet;

/**
 *
 * @author zipCoder933
 */
public class CustomIconAtlas extends ImageAtlas {
    
    public CustomIconAtlas() throws IOException {
        super(ResourceUtils.resource("items\\icons.png"), 32);
    }
    
}
