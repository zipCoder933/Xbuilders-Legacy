/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

 /*
  Part of the Processing project - http://processing.org

  Copyright (c) 2006-10 Ben Fry and Casey Reas

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License version 2.1 as published by the Free Software Foundation.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
 */
package com.xbuilders.engine.rendering;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

public class ChunkMesh {

    public void setTexture(PImage texture) {
        this.texture = texture;
        model.setTexture(texture);
    }

    public ChunkMesh(PGraphics g, String filename) {
        model = g.loadShape(filename);
    }

    public void draw(PGraphics g) {
        for (int i = 0; i < model.childCount; i++) {
//            draw(model.children[i], g);
            model.children[i].draw(g);
        }
    }

    private void draw(PShape s, PGraphics g) {
        if (s.visible) {
            s.pre(g);
            s.drawImpl(g);
            s.post(g);
        }
    }

    private PImage texture;
   public PShape model;
}
