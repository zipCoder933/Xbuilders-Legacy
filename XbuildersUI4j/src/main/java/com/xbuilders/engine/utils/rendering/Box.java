//package com.xbuilders.engine.utils.rendering;
//
//import com.xbuilders.engine.utils.math.AABB;
//import com.xbuilders.game.Main;
//import org.joml.Matrix4f;
//import org.joml.Vector3f;
//import org.joml.Vector4f;
//import processing.core.PGraphics;
//
//public class Box {
//
//    /**
//     * @param lineWidth the lineWidth to set
//     */
//    public void setLineWidth(float lineWidth) {
//        this.lineWidth = lineWidth;
//    }
//
//    private float lineWidth = 1.0f;
//    private final Vector4f color = new Vector4f(0, 0, 0, 1);
//    private final AABB box = new AABB();
//    float[] vertices;
//
//    public void setPosition(float x, float y, float z) {
//        box.minPoint.set(x, y, z);
//    }
//
//    public void setPosition(Vector3f pos) {
//        box.minPoint.set(pos);
//    }
//
//    public void setColor(Vector4f color) {
//        color.set(color.x, color.y, color.z, color.w);
//    }
//
//    public void updateMVP(Matrix4f projection, Matrix4f view) {
////        mvp.update(projection, view, position);
//    }
//
//    public void setToAABB(Matrix4f projection, Matrix4f view, AABB box) {
//        setSize(box.minPoint.x, box.minPoint.y, box.minPoint.z,
//                box.maxPoint.x, box.maxPoint.y, box.maxPoint.z);
//    }
//
//
//    public void setSize(
//            float minX, float minY, float minZ,
//            float maxX, float maxY, float maxZ) {
//        if (vertices == null
//                || minX != box.minPoint.x
//                || minY != box.minPoint.y
//                || minZ != box.minPoint.z
//                || maxX != box.maxPoint.x
//                || maxY != box.maxPoint.y
//                || maxZ != box.maxPoint.z) {
//
//            box.minPoint.set(minX, minY, minZ);
//            box.maxPoint.set(maxX, maxY, maxZ);
//
//            vertices = new float[]{
//                    box.minPoint.x, box.minPoint.y, box.minPoint.z, // V0
//                    box.maxPoint.x, box.minPoint.y, box.minPoint.z, // VMAX
//                    box.maxPoint.x, box.maxPoint.y, box.minPoint.z, // V2
//                    box.minPoint.x, box.maxPoint.y, box.minPoint.z, // V3
//                    box.minPoint.x, box.minPoint.y, box.maxPoint.z, // V4
//                    box.maxPoint.x, box.minPoint.y, box.maxPoint.z, // V5
//                    box.maxPoint.x, box.maxPoint.y, box.maxPoint.z, // V6
//                    box.minPoint.x, box.maxPoint.y, box.maxPoint.z, // V7
//                    box.minPoint.x, box.minPoint.y, box.minPoint.z, // V8
//                    box.minPoint.x, box.minPoint.y, box.maxPoint.z, // V9
//                    box.maxPoint.x, box.minPoint.y, box.minPoint.z, // VMAXMIN
//                    box.maxPoint.x, box.minPoint.y, box.maxPoint.z, // VMAXMAX
//                    box.maxPoint.x, box.maxPoint.y, box.minPoint.z, // VMAX2
//                    box.maxPoint.x, box.maxPoint.y, box.maxPoint.z, // VMAX3
//                    box.minPoint.x, box.maxPoint.y, box.minPoint.z, // VMAX4
//                    box.minPoint.x, box.maxPoint.y, box.maxPoint.z, // VMAX5
//                    box.minPoint.x, box.minPoint.y, box.minPoint.z, // VMAX6
//                    box.maxPoint.x, box.minPoint.y, box.minPoint.z, // VMAX7
//                    box.minPoint.x, box.minPoint.y, box.maxPoint.z, // VMAX8
//                    box.maxPoint.x, box.minPoint.y, box.maxPoint.z // V19
//            };
//        }
//    }
//
//    public void draw() {
//        PGraphics pg = Main.getPG();
//        pg.pushStyle();
//        pg.noFill();
//        pg.stroke(color.x * 255, color.y * 255, color.z * 255);
//        pg.strokeWeight(lineWidth);
//        pg.translate(box.minPoint.x - box.getXLength() / 2,
//                box.minPoint.y - box.getYLength() / 2,
//                box.minPoint.z - box.getZLength() / 2);
//        pg.box(box.getXLength(), box.getYLength(), box.getZLength());
//        pg.popStyle();
//    }
//}
