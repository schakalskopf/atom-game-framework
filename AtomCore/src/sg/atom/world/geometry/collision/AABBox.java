package sg.atom.world.geometry.collision;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.geometry.Polygon;

import java.io.*;

//import gl.*;
/**
 * Axis-aligned bounding box.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class AABBox implements Serializable {

    public Vec3d min;
    public Vec3d max;

    public AABBox() {
    }

    public AABBox(Polygon[] polySet) {
        float minX, minY, minZ, maxX, maxY, maxZ;

        minX = minY = minZ = Float.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < polySet.length; i++) {
            for (int j = 0; j < polySet[i].getNumPoints(); j++) {
                Vec3d point = polySet[i].getVertex(j).pos;

                if (point.c[0] < minX) {
                    minX = point.c[0];
                }
                if (point.c[1] < minY) {
                    minY = point.c[1];
                }
                if (point.c[2] < minZ) {
                    minZ = point.c[2];
                }

                if (point.c[0] > maxX) {
                    maxX = point.c[0];
                }
                if (point.c[1] > maxY) {
                    maxY = point.c[1];
                }
                if (point.c[2] > maxZ) {
                    maxZ = point.c[2];
                }
            }
        }

        min = new Vec3d(minX, minY, minZ);
        max = new Vec3d(maxX, maxY, maxZ);
    }

    public Vec3d getMin() {
        return min;
    }

    public Vec3d getMax() {
        return max;
    }

    public String toString() {
        return new String("Min: " + min + " Max: " + max);
    }

    public boolean pointInside(Vec3d point, float epsilon) {
        if ((point.c[0] >= (min.c[0] - epsilon))
                && (point.c[0] <= (max.c[0] + epsilon))
                && (point.c[1] >= (min.c[1] - epsilon))
                && (point.c[1] <= (max.c[1] + epsilon))
                && (point.c[2] >= (min.c[2] - epsilon))
                && (point.c[2] <= (max.c[2] + epsilon))) {
            return true;
        }

        return false;
    }
    /*
     public void draw()
     {
     GL.gl.glColor3f(0,1,0);
     GL.gl.glPolygonMode(GL.gl.GL_FRONT_AND_BACK, GL.gl.GL_LINE);
     GL.gl.glDisable(GL.gl.GL_DEPTH_TEST);
     GL.gl.glDisable(GL.gl.GL_CULL_FACE);

     GL.gl.glBegin(GL.gl.GL_QUADS);

     // front quad
     GL.gl.glVertex3fv(min.c);
     GL.gl.glVertex3f(min.c[0], max.c[1], min.c[2]);
     GL.gl.glVertex3f(max.c[0], max.c[1], min.c[2]);
     GL.gl.glVertex3f(max.c[0], min.c[1], min.c[2]);

     // back quad
     GL.gl.glVertex3f(max.c[0], min.c[1], max.c[2]);
     GL.gl.glVertex3fv(max.c);
     GL.gl.glVertex3f(min.c[0], max.c[1], max.c[2]);
     GL.gl.glVertex3f(min.c[0], min.c[1], max.c[2]);

     // top quad
     GL.gl.glVertex3f(min.c[0], max.c[1], min.c[2]);
     GL.gl.glVertex3f(min.c[0], max.c[1], max.c[2]);
     GL.gl.glVertex3fv(max.c);
     GL.gl.glVertex3f(max.c[0], max.c[1], min.c[2]);
    
     // bottom quad
     GL.gl.glVertex3f(min.c[0], min.c[1], max.c[2]);
     GL.gl.glVertex3fv(min.c);
     GL.gl.glVertex3f(max.c[0], min.c[1], min.c[2]);
     GL.gl.glVertex3f(max.c[0], min.c[1], max.c[2]);
    
     // left quad
     GL.gl.glVertex3f(min.c[0], min.c[1], max.c[2]);
     GL.gl.glVertex3f(min.c[0], max.c[1], max.c[2]);
     GL.gl.glVertex3f(min.c[0], max.c[1], min.c[2]);
     GL.gl.glVertex3fv(min.c);

     // right quad
     GL.gl.glVertex3f(max.c[0], min.c[1], min.c[2]);
     GL.gl.glVertex3f(max.c[0], max.c[1], min.c[2]);
     GL.gl.glVertex3fv(max.c);
     GL.gl.glVertex3f(max.c[0], min.c[1], max.c[2]);
    
     GL.gl.glEnd();

     GL.gl.glColor3f(1,1,1);

     GL.gl.glEnable(GL.gl.GL_DEPTH_TEST);
     GL.gl.glEnable(GL.gl.GL_CULL_FACE);
     }
     */
}
