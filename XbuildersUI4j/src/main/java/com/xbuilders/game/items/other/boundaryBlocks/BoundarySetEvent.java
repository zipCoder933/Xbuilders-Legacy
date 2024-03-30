/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.other.boundaryBlocks;

import com.xbuilders.engine.utils.math.AABB;
import org.joml.Vector3i;

/**
 *
 * @author zipCoder933
 */
public abstract class BoundarySetEvent {

    public abstract void onBoundarySet(
            AABB boundary,
            Vector3i startPosition,
            Vector3i endPosition);
}
