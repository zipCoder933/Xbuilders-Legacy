/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.player.camera.frustum;

import com.xbuilders.engine.utils.math.AABB;
import org.joml.Vector3f;
import processing.core.PGraphics;

/**
 * from http://www.lighthouse3d.com/tutorials/view-frustum-culling/
 *
 * @author zipCoder933
 */
public class Frustum {

    public Plane leftPlane;
    public Plane topPlane;
    public Plane bottomPlane;
    public Plane rightPlane;

    //We dont need the near or far planes, we we are commenting them out.
//    public Plane nearPlane;
//    public Plane farPlane;
    public Plane[] planeList;

    public enum intersection {
        OUTSIDE, INTERSECT, INSIDE
    }

    ;

    public Frustum() {
        leftPlane = new Plane();
        topPlane = new Plane();
        bottomPlane = new Plane();
        rightPlane = new Plane();
//        nearPlane = new Plane();
//        farPlane = new Plane();
        planeList = new Plane[]{leftPlane, topPlane, bottomPlane, rightPlane, //            nearPlane, farPlane
        };
    }

    Vector3f ntl, ntr, nbl, nbr, ftl, ftr, fbl, fbr;

    private void point(Vector3f pos, PGraphics g, boolean close) {
        g.strokeWeight(10);
        g.noFill();
        g.translate(pos.x, pos.y, pos.z);
        if (close) {
            g.stroke(0, 0, 255);
            g.box(0.001f);
        } else {
            g.stroke(0, 255, 0);
            g.box(10f);
        }
        g.translate(-pos.x, -pos.y, -pos.z);
    }

    public void draw(PGraphics g) {
        g.resetShader();
        point(ftl, g, false);
        point(ftr, g, false);
        point(fbl, g, false);
        point(fbr, g, false);

        point(ntl, g, true);
        point(ntr, g, true);
        point(nbl, g, true);
        point(nbr, g, true);
        leftPlane.draw(g);
        topPlane.draw(g);
        rightPlane.draw(g);
        bottomPlane.draw(g);
//        nearPlane.draw(g);
//        farPlane.draw(g);
    }

    float nearD, farD, ratio, angle, tang;
    float nw, nh, fw, fh;
//    private final double ANG2RAD = Math.PI / 180.0;

    /**
     * This function takes exactly the same parameters as the function
     * gluPerspective. Each time the perspective definitions change, for
     * instance when a window is resized, this function should be called as
     * well.
     * <br><br>The function stores all the information, and computes the width
     * and height of the rectangular sections of the near and far plane and
     * stores them in nh (near height), nh (near width), fh (far height), and fw
     * (far width).
     *
     * @param angle the camera field of view (in radians)
     * @param ratio the camera ratio
     * @param nearD the camera near distance
     * @param farD  the camera far distance
     */
    public void setCamInternals(float angle, float ratio, float nearD, float farD) {
        // store the information
        this.ratio = ratio;
        this.angle = angle;
        this.nearD = nearD;
        this.farD = farD;

        // compute width and height of the near and far plane sections
        tang = (float) Math.tan(angle * 0.5);//ANG2RAD * angle * 0.5

        nh = nearD * tang;
        nw = nh * ratio;
        fh = farD * tang;
        fw = fh * ratio;

//        nh/=0.9f;
//        fh/=0.9f;
//        nw/=0.9f;
//        fw/=0.9f;
//        System.out.println("Near Plane: " + nw + ", " + nh);
//        System.out.println("Far Plane: " + fw + ", " + fh);
    }

    private static Vector3f sub(Vector3f a, Vector3f b) {
        return new Vector3f(a).sub(b);
    }

    private static Vector3f add(Vector3f a, Vector3f b) {
        return new Vector3f(a).add(b);
    }

    private static Vector3f cross(Vector3f a, Vector3f b) {
        return new Vector3f(a).cross(b);
    }

    private static Vector3f mul(Vector3f a, float b) {
        return new Vector3f(a).mul(b);
    }

    /**
     * This function takes three vectors that contain the information for the
     * gluLookAt function: the position of the camera, a point to where the
     * camera is pointing and the up vector. Each time the camera position or
     * orientation changes, this function should be called as well.
     *
     * @param p the position of the camera
     * @param l the origin (center) of the 3d scene
     * @param u the up direction (vector)
     *          <p>
     *          All of these methods are asked for in the PApplet.camera() or the
     *          gluLookAt() function. For example:
     *          <p>
     *          gluLookAt(px,py,pz, lx,ly,lz, ux,uy,uz);
     */
    public void setCamDef(Vector3f p, Vector3f l, Vector3f u) {
        Vector3f nc, fc, X, Y, Z;

        // compute the Z axis of camera
        // this axis points in the opposite direction from
        // the looking direction
        Z = sub(p, l);
        Z.normalize();

        // X axis of camera with given "up" vector and Z axis
        X = cross(u, Z);
        X.normalize();

        // the real "up" vector is the cross product of Z and X
        Y = cross(Z, X);

        // compute the centers of the near and far planes
        nc = sub(p, mul(Z, nearD));
        fc = sub(p, mul(Z, farD));

        // compute the 4 corners of the frustum on the far plane
        Vector3f Yfh = mul(Y, fh);
        Vector3f Xfw = mul(X, fw);

        // compute the 4 corners of the frustum on the far plane
//	ftl = fc + (Y*fh) - (X*fw);
        ftl = sub(add(fc, Yfh), Xfw);
//	ftr = fc + (Y*fh) + (X*fw);
        ftr = new Vector3f(fc).add(Yfh).add(Xfw);
//	fbl = fc - (Y*fh) - (X*fw);
        fbl = new Vector3f(fc).sub(Yfh).sub(Xfw);
        ;
//	fbr = fc - (Y*fh) + (X*fw);
        fbr = add(sub(fc, Yfh), Xfw);

        Vector3f Ynh = mul(Y, nh);
        Vector3f Xnw = mul(X, nw);
        // compute the 4 corners of the frustum on the near plane
//        ntl = nc + Y * nh - X * nw;
        ntl = sub(add(nc, Ynh), Xnw);
//        ntr = nc + Y * nh + X * nw;
        ntr = new Vector3f(nc).add(Ynh).add(Xnw);
//        nbl = nc - Y * nh - X * nw;
        nbl = new Vector3f(nc).sub(Ynh).sub(Xnw);
//        nbr = nc - Y * nh + X * nw;
        nbr = add(sub(nc, Ynh), Xnw);

        topPlane.set3Points(ntr, ntl, ftl);
        bottomPlane.set3Points(nbl, nbr, fbr);
        leftPlane.set3Points(ntl, nbl, fbl);
        rightPlane.set3Points(nbr, ntr, fbr);
//        nearPlane.set3Points(ntl, ntr, nbr);
//        farPlane.set3Points(ftr, ftl, fbl);
    }

    private enum BoundaryResults {
        INSIDE,
        OUTSIDE,
        INTERSECT
    }

    public boolean isPointInside(Vector3f point) {
//        if (nearPlane.getDistanceToPoint(point) < 0) {
//            return false;
//        }
//        if (farPlane.getDistanceToPoint(point) < 0) {
//            return false;
//        }
        if (leftPlane.getDistanceToPoint(point) < 0) {
            return false;
        }
        if (rightPlane.getDistanceToPoint(point) < 0) {
            return false;
        }
        if (topPlane.getDistanceToPoint(point) < 0) {
            return false;
        }
        if (bottomPlane.getDistanceToPoint(point) < 0) {
            return false;
        }
        return true;
    }

    public boolean isSphereInside(Vector3f center, float radius) {


//        float distance = nearPlane.getDistanceToPoint(center);
//        if (distance < -radius) {
//            return false;
//        } else if (distance < radius) {
//            result = true;
//        }
//
//        distance = farPlane.getDistanceToPoint(center);
//        if (distance < -radius) {
//            return false;
//        } else if (distance < radius) {
//            result = true;
//        }
        float distance = leftPlane.getDistanceToPoint(center);
        if (distance < -radius) {
            return false;
        }

        distance = rightPlane.getDistanceToPoint(center);
        if (distance < -radius) {
            return false;
        }
        distance = topPlane.getDistanceToPoint(center);
        if (distance < -radius) {
            return false;
        }

        distance = bottomPlane.getDistanceToPoint(center);
        if (distance < -radius) {
            return false;
        }
        return true;
    }

    public boolean isAABBInside(AABB box) {
        // comment out all lines with result variable
        int out = 0;
        int in = 0;

        Vector3f[] boxVerts = new Vector3f[8];
        boxVerts[0] = new Vector3f(box.minPoint);
        boxVerts[1] = new Vector3f(box.maxPoint.x, box.minPoint.y, box.minPoint.z);
        boxVerts[2] = new Vector3f(box.minPoint.x, box.maxPoint.y, box.minPoint.z);
        boxVerts[3] = new Vector3f(box.minPoint.x, box.minPoint.y, box.maxPoint.z);
        boxVerts[4] = new Vector3f(box.maxPoint.x, box.maxPoint.y, box.minPoint.z);
        boxVerts[5] = new Vector3f(box.minPoint.x, box.maxPoint.y, box.maxPoint.z);
        boxVerts[6] = new Vector3f(box.maxPoint.x, box.minPoint.y, box.maxPoint.z);
        boxVerts[7] = new Vector3f(box.maxPoint.x, box.maxPoint.y, box.maxPoint.z);

        for (int i = 0; i < planeList.length; i++) {
            out = 0;
            in = 0;
            for (int k = 0; k < 8 && (in == 0 || out == 0); k++) {
                if (planeList[i].getDistanceToPoint(boxVerts[k]) < 0) {
                    out++;
                } else {
                    in++;
                }
            }
            if (in == 0) {
                return false;
            }
        }
        return true;
    }

}


