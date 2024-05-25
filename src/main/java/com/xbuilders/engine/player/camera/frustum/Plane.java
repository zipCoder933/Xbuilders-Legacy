/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.player.camera.frustum;

import org.joml.Vector3f;
import processing.core.PGraphics;

/**
 * From http://www.lighthouse3d.com/tutorials/maths/plane/
 *
 * @author zipCoder933
 */
public class Plane {

    /**
     * @return the normal
     */
    public Vector3f getNormal() {
        return normal;
    }

    /**
     * @return the point0
     */
    public Vector3f getPoint0() {
        return point0;
    }

    /**
     * @return the point1
     */
    public Vector3f getPoint1() {
        return point1;
    }

    /**
     * @return the point2
     */
    public Vector3f getPoint2() {
        return point2;
    }

    public Plane() {

    }

    private Vector3f point0, point1, point2;
    private Vector3f v, u, normal;
    private float A, B, C, D;

    public void set3Points(Vector3f point0, Vector3f point1, Vector3f point2) {
        this.point0 = point0;
        this.point1 = point1;
        this.point2 = point2;
        calculateGeometry();
    }

    private void calculateGeometry() {
        v = new Vector3f(getPoint1());
        v.sub(getPoint0());

        u = new Vector3f(getPoint2());
        u.sub(getPoint0());

        normal = new Vector3f(v);
        normal.cross(u);

        normal.normalize();
        A = getNormal().x;
        B = getNormal().y;
        C = getNormal().z;
        D = (-A * getPoint0().x - B * getPoint0().y - C * getPoint0().z);
//        System.out.println("V: " + v.toString());
//        System.out.println("U: " + u.toString());
//        System.out.println("n: " + normal.toString());
    }

    /**
     * @param point the point to compare.
     * @return the distance from a point to the plane. If the distance is
     * negative, than we are on the opposite side (direction opposite to the
     * normal) of the plane.
     */
    public float getDistanceToPoint(Vector3f point) {
        return getDistanceToPoint(point.x, point.y, point.z);
    }

    /**
     * @param x point X
     * @param y point Y
     * @param z point Z
     * @return the distance from a point to the plane. If the distance is
     * negative, than we are on the opposite side (direction opposite to the
     * normal) of the plane.
     */
    public float getDistanceToPoint(float x, float y, float z) {
        float dist = (A * x) + (B * y) + (C * z) + D;
        return dist;

    }

    /**
     * @param q the point
     * @return the projected point on the plane.
     */
    public Vector3f getProjectedPointOnPlane(Vector3f q) {
        Vector3f point = new Vector3f(q);
        float dist = getDistanceToPoint(point);
        point = point.sub(dist, dist, dist);
        point = new Vector3f(point);
        point.cross(normal);
        return point;
    }

//    private void point(Vector3f pos, PGraphics g) {
//        g.strokeWeight(10);
//        g.noFill();
//        g.pushMatrix();
//        g.translate(pos.x, pos.y, pos.z);
//        g.stroke(0, 255, 0);
//        g.box(10f);
//        g.popMatrix();
//    }
    public void draw(PGraphics pg) {

//        if (point2 == null) {
//            return;
//        }
//
//        Vector3f point4 = getProjectedPointOnPlane(point0);
        pg.beginShape();
        pg.strokeWeight(1);
        pg.stroke(255);
        pg.fill(255, 50, 50);
        pg.vertex(point0.x, point0.y, point0.z);
        pg.fill(50, 255, 50);
        pg.vertex(point1.x, point1.y, point1.z);
        pg.fill(50, 50, 255);
        pg.vertex(point2.x, point2.y, point2.z);

//        pg.fill(255, 255, 255);
//        pg.vertex(point4.x, point4.y, point4.z);
        pg.endShape();

    }

}
