package sg.atom.world.visibility.bsp;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.geometry.Polygon;
import sg.atom.world.geometry.Triangle;
import sg.atom.world.geometry.Vertex;

/**
 * Triangulate is used to triangulate convex polygons into triangles.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Triangulate {

    /**
     * Triangulate convex polygon into triangles and return the trianges as
     * polygons with only three points.
     *
     * @param poly polygon to triangulate
     * @return array of three point polygons
     */
    public static Polygon[] triangulateConvexPolygonToPolySet(Polygon poly) {
        Polygon[] result = new Polygon[poly.getNumPoints() - 2];

        Vertex A = poly.getVertex(0);

        for (int i = 1; i <= poly.getNumPoints() - 2; i++) {
            Vertex B = poly.getVertex(i);
            Vertex C = poly.getVertex(i + 1);
            result[i - 1] = new Polygon(new Vertex(A),
                    new Vertex(B),
                    new Vertex(C),
                    poly.getPrimaryTextureName(),
                    poly.getSecondaryTextureName());
        }

        return result;
    }

    /**
     * Triangulate convex polygon.
     *
     * @param poly polygon to triangulate
     * @return array of triangles produced
     */
    public static Triangle[] triangulateConvexPolygon(Polygon poly) {
        Triangle[] result = new Triangle[poly.getNumPoints() - 2];

        Vertex A = poly.getVertex(0);

        for (int i = 1; i <= poly.getNumPoints() - 2; i++) {
            Vertex B = poly.getVertex(i);
            Vertex C = poly.getVertex(i + 1);

            result[i - 1] = new Triangle(new Vertex(A),
                    new Vertex(B),
                    new Vertex(C),
                    poly.getPrimaryTextureName(),
                    poly.getSecondaryTextureName());
        }

        return result;
    }

    /**
     * Triangulate a set of convex polygons.
     *
     * @param polySet array of polygons to triangulate
     * @return array of produced triangles
     */
    public static Triangle[] triangulateConvexPolygonSet(Polygon[] polySet) {
        int numTris = 0;

        for (int i = 0; i < polySet.length; i++) {
            numTris += polySet[i].getNumPoints() - 2;
        }

        Triangle[] result = new Triangle[numTris];

        int k = 0;

        for (int i = 0; i < polySet.length; i++) {
            Triangle[] tmp = triangulateConvexPolygon(polySet[i]);

            for (int j = 0; j < tmp.length; j++) {
                result[k++] = tmp[j];
            }
        }

        return result;
    }
}
