/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.engine.items.block.construction;

import com.xbuilders.engine.utils.ErrorHandler;
import com.xbuilders.engine.utils.math.MathUtils;
import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author zipCoder933
 */
public class ObjToBlockTypeConversion {

    private static float round(double input) {
        float i = (float) Math.round(input * 50) / 50;
        return i;
    }

    public static void parseOBJToRendererClass(File inputOBJ) {
        try {

            System.out.println("PARSING FILE " + inputOBJ + " TO BLOCK RENDERER...");
//            String title = inputOBJ.getName().split("\\.")[0];
            File outputClass = new File(inputOBJ.getParentFile(),
                    inputOBJ.getName().replace(".obj", "_output.txt"));
            outputClass.getParentFile().mkdirs();
            if (!outputClass.exists()) {
                outputClass.createNewFile();
            }
//            System.out.println("File: "+outputClass);

            String namePrefix = inputOBJ.getName().replace(".obj", "").replace(" ", "_");
            String vertsName = "verts_" + namePrefix;
            String uvName = "uv_" + namePrefix;

            ArrayList<Vector3f> vertsList = new ArrayList<>();
            StringBuilder verts = new StringBuilder();
            verts.append("static Vector3f[] " + vertsName + " = {");

            StringBuilder uv = new StringBuilder();
            uv.append("static Vector2f[] " + uvName + " = {");

            HashMap<Side, String> faces = new HashMap<>();

            int vertexCount = 0;
            int uvCount = 0;

            for (String line : Files.readAllLines(inputOBJ.toPath())) {
                if (line.startsWith("v ")) {
                    line = line.replace("v ", "");
                    String[] chunks = line.split(" ");

                    float x = round(Double.parseDouble(chunks[0].trim()));
                    float y = round(Double.parseDouble(chunks[1].trim()));
                    float z = round(Double.parseDouble(chunks[2].trim()));

                    verts.append("\nnew Vector3f(").append(x).append("f, ").append(y).append("f, ").append(z).append("f), //VERT ").append(vertexCount);
                    vertsList.add(new Vector3f(x, y, z));
                    vertexCount++;
                } else if (line.startsWith("vt ")) { // uv
                    line = line.replace("vt ", "");
                    String[] chunks = line.split(" ");

                    float x = round(Double.parseDouble(chunks[0].trim()));
                    float y = round(Double.parseDouble(chunks[1].trim()));

                    uv.append("\nnew Vector2f(").append(x).append("f, ").append(y).append("f), //UV ").append(uvCount);
                    uvCount++;
                } else if (line.startsWith("f ")) {
                    drawFace(faces, vertsList, line);
                }
            }
            verts.append("\n};\n");
            uv.append("\n};\n");

            String facesCode = "";
            for (HashMap.Entry<Side, String> set : faces.entrySet()) {
                facesCode += "\nprivate static void " + methodName(set.getKey(), namePrefix) + "(Vector3f[] verts2, Vector2f[] uv2, Block block, PShape buffers, int x, int y, int z) {\n"
                        + "int[] pos = block.texture.TOP;\n\n"
                        + set.getValue() + "\n}\n\n";
            }
            facesCode = "//<editor-fold defaultstate=\"collapsed\" desc=\"Face methods\">\n" + facesCode + "\n//</editor-fold>";

            String methodCode
                    = "public void constructBlock_" + namePrefix + "(Block block, PShape buffers,"
                    + "Block negativeX, Block positiveX, Block negativeY,"
                    + "Block positiveY, Block negativeZ, Block positiveZ,"
                    + "int x, int y, int z) {\n";

            for (HashMap.Entry<Side, String> set : faces.entrySet()) {
                if (set.getKey() != Side.center) {
                    methodCode += " if (shouldRenderSide(block, " + set.getKey().toString() + ")) {\n";
                }
                methodCode += methodName(set.getKey(), namePrefix) + "(" + vertsName + ", " + uvName + ", block, buffers, x, y, z);\n";
                if (set.getKey() != Side.center) {
                    methodCode += " }\n";
                }
            }
            methodCode += "\n}";

            String output
                    = "\n//<editor-fold defaultstate=\"collapsed\" desc=\"Draw " + namePrefix + "\">\n"
                    + "//NOTES:\n"
                    + "//The UV map for this block only exists on the top face.\n\n"
                    + "\n\n//<editor-fold defaultstate=\"collapsed\" desc=\"Verticies\">\n"
                    + verts.toString() + uv.toString() + "\n//</editor-fold>\n\n" + methodCode + "\n\n" + facesCode
                    + "\n//</editor-fold>";

            Files.write(outputClass.toPath(), output.getBytes(), StandardOpenOption.WRITE);

            System.out.println("======================================================\n"
                    + "RENDERER CLASS FILE WRITTEN TO: " + outputClass
                    + "\n======================================================");
        } catch (IOException ex) {
            ErrorHandler.report(ex);
        }
    }

    enum Side {
        positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, center
    }

    ;

    private static String methodName(Side side, String namePrefix) {
        return "make_" + namePrefix + "_" + side.toString() + "_faces";
    }

    private static Side determineSideOfFace(Vector3f[] vertex2) {
        //If all vericies are 0 or 1 on either an X Y or Z axis, they are a side.

        Vector3f[] vertex = vertex2;
        for (int i = 0; i < 3; i++) {
            if (MathUtils.dist(vertex[i].x, 0) < 0.1) {
                vertex[i].x = 0;
            } else if (MathUtils.dist(vertex[i].x, 1) < 0.1) {
                vertex[i].x = 1;
            }
            if (MathUtils.dist(vertex[i].y, 0) < 0.1) {
                vertex[i].y = 0;
            } else if (MathUtils.dist(vertex[i].y, 1) < 0.1) {
                vertex[i].y = 1;
            }
            if (MathUtils.dist(vertex[i].z, 0) < 0.1) {
                vertex[i].z = 0;
            } else if (MathUtils.dist(vertex[i].z, 1) < 0.1) {
                vertex[i].z = 1;
            }
        }

        if (vertex[0].x == 0 && vertex[1].x == 0 && vertex[2].x == 0) {
            return Side.negativeX;
        } else if (vertex[0].x == 1 && vertex[1].x == 1 && vertex[2].x == 1) {
            return Side.positiveX;
        } else if (vertex[0].y == 0 && vertex[1].y == 0 && vertex[2].y == 0) {
            return Side.negativeY;
        } else if (vertex[0].y == 1 && vertex[1].y == 1 && vertex[2].y == 1) {
            return Side.positiveY;
        } else if (vertex[0].z == 0 && vertex[1].z == 0 && vertex[2].z == 0) {
            return Side.negativeZ;
        } else if (vertex[0].z == 1 && vertex[1].z == 1 && vertex[2].z == 1) {
            return Side.positiveZ;
        }
        return Side.center;

    }

    private static void drawFace(HashMap<Side, String> sideFaces, ArrayList<Vector3f> vertsList, String line) {
        line = line.replace("f ", "");
        String[] verticies = line.split(" ");

        String face = "//FACE";
        int i = 0;
        Vector3f[] triangleVerticies = new Vector3f[3];
        for (String vert : verticies) {
            String[] vertexPieces = vert.split("/");
            int vertex = Integer.parseInt(vertexPieces[0].trim()) - 1;
            int uv_vertex = Integer.parseInt(vertexPieces[1].trim()) - 1;

            if (i > 2) {
                throw new Error("The OBJ Parser only supports meshes with triangulated faces!");
            }
            triangleVerticies[i] = vertsList.get(vertex);

            face = ("\nbuffers.vertex(verts2[" + vertex + "].x + x, verts2[" + vertex + "].y + y, verts2[" + vertex + "].z + z, "
                    + "getUVTextureCoord_X(pos, uv2[" + uv_vertex + "].x), getUVTextureCoord_Y(pos, uv2[" + uv_vertex + "].y));") + face;

            i++;
        }

        Side side = determineSideOfFace(triangleVerticies);
        if (sideFaces.containsKey(side)) {
            sideFaces.put(side, sideFaces.get(side) + "\n" + face);
        } else {
            sideFaces.put(side, face);
        }
    }
}
