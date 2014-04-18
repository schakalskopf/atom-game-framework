package sg.atom.world.visibility.bsp;

import sg.atom.world.geometry.algebra.Vec3d;

import java.util.LinkedList;
import java.util.ListIterator;

import sg.atom.world.geometry.Plane;
import sg.atom.world.geometry.Ray;
import sg.atom.world.geometry.Triangle;
import sg.atom.world.geometry.collision.AABBox;

/**
 * Potentially Visible Set (PVS). PVS is used to precompute the visibility
 * information of a scene. Doing so allows us to avoid expensive calculations at
 * runtime because we can simple look up the visible polygons from a list.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Pvs {

    /**
     * Actually calculate the PVS. If false, all leafs are marked visible
     */
    static boolean USE_PVS = true;
    /**
     * Misc information
     */
    private static int numSamples = 0;
    static int numProcessed = 0;
    static int totalNodes = 0;
    /**
     * The greatest error encountered in creating sample points
     */
    private static float maxError = 0.05f;
    /**
     * Number of samples per unit of length
     */
    static float SAMPLE_RESOLUTION = 0.2f;

    /**
     * Distribute sample points with certain interval along the splitting plane
     * of the node, within the boundaries of the bounding box of the node.
     *
     * @param node a BSP tree node
     * @return a set of sample points
     */
    private static LinkedList distributePoints(Bsp node) {
        LinkedList pointList = new LinkedList();

        Plane plane = node.getDivider();
        Vec3d n = plane.getNormal();

        /**
         * Compute two orthonormal vectors (u, v) that lie on the plane.
         */
        Vec3d x = new Vec3d(1, 0, 0);
        if (Math.abs(n.c[1]) < Math.abs(n.c[0])) {
            x = new Vec3d(0, 1, 0);
        }
        if (Math.abs(n.c[2]) < Math.abs(n.c[0])) {
            x = new Vec3d(0, 0, 1);
        }

        Vec3d u = x.sub(n.scale(x.dot(n)));
        u.normalize();

        Vec3d v = u.cross(n);

        /**
         * Scale the vectors to target resolution
         */
        u.scaleTo(1 / SAMPLE_RESOLUTION);
        v.scaleTo(1 / SAMPLE_RESOLUTION);

        int interpCoordU = u.getGreatestComponent();
        int interpCoordV = v.getGreatestComponent();

        Vec3d p;
        AABBox bbox = node.getBBox();
        Vec3d min = bbox.getMin();
        Vec3d max = bbox.getMax();

        /**
         * Decide what coordinates to interpolate and their limits and calculate
         * the start point of interpolation on the plane.
         */
        if (interpCoordU == 0) {
            if (interpCoordV == 1) {
                p = new Vec3d(min.c[0], min.c[1], -(n.c[0] * min.c[0] + n.c[1] * min.c[1] - plane.getConstant()) / n.c[2]);
            } else {
                p = new Vec3d(min.c[0], -(n.c[0] * min.c[0] + n.c[2] * min.c[2] - plane.getConstant()) / n.c[1], min.c[2]);
            }
        } else if (interpCoordU == 1) {
            if (interpCoordV == 0) {
                p = new Vec3d(min.c[0], min.c[1], -(n.c[0] * min.c[0] + n.c[1] * min.c[1] - plane.getConstant()) / n.c[2]);
            } else {
                p = new Vec3d(-(n.c[0] * min.c[0] + n.c[2] * min.c[2] - plane.getConstant()) / n.c[0], min.c[1], min.c[2]);
            }
        } else if (interpCoordU == 2) {
            if (interpCoordV == 0) {
                p = new Vec3d(min.c[0], -(n.c[0] * min.c[0] + n.c[2] * min.c[2] - plane.getConstant()) / n.c[1], min.c[2]);
            } else {
                p = new Vec3d(-(n.c[1] * min.c[1] + n.c[2] * min.c[2] - plane.getConstant()) / n.c[0], min.c[1], min.c[2]);
            }
        } else {
            System.out.println("ERROR: dont't know what to interpolate!");
            System.exit(-1);
            return null;
        }

        /**
         * Make sure we sample inside the bounding box
         */
        if (u.c[interpCoordU] < 0) {
            u.scaleTo(-1);
        }
        if (v.c[interpCoordV] < 0) {
            v.scaleTo(-1);
        }

        /**
         * Interpolate u and v and distribute a grid of sample points on the
         * plane.
         */
        while (p.c[interpCoordU] < max.c[interpCoordU]) {
            int count = 0;

            while (p.c[interpCoordV] < max.c[interpCoordV]) {
                if (bbox.pointInside(p, maxError)) {
                    float dist = plane.classifyPoint(p);

                    /**
                     * Keep track of numerical errors occured
                     */
                    float error = Math.abs(dist);
                    if (error > maxError) {
                        maxError = error;
                    }

                    numSamples++;
                    pointList.add(new Vec3d(p));
                }

                p.addTo(v);
                count++;
            }

            p.subFrom(v.scale(count));
            p.addTo(u);
        }

        return pointList;
    }

    /**
     * Remove points outside the bounding box of the node.
     *
     * @param node a BSP tree node
     * @param points a set of points to cleanup
     */
    private static void cleanupPointsOutsideBBox(Bsp node, LinkedList points) {
        ListIterator i = points.listIterator();

        while (i.hasNext()) {
            Vec3d point = (Vec3d) i.next();

            /* If the sample is outside the bounding box of this node, remove it */
            if (!node.getBBox().pointInside(point, maxError)) {
                i.remove();
            }
        }

        if (points.size() == 0) {
            System.out.println("ERROR: all samples removed (outside bbox)!");
            System.exit(-1);
        }
    }

    /**
     * Remove points that are outside the bounding box of this node or that lie
     * on a polygon in the node.
     *
     * @param node BSP tree node
     * @param points sample points to clean
     */
    private static void cleanupLeafNodePoints(Bsp node, LinkedList points) {
        ListIterator i = points.listIterator();

        while (i.hasNext()) {
            Vec3d point = (Vec3d) i.next();

            if (!node.getBBox().pointInside(point, maxError)
                    || !validSamplePoint(point, node.getTris(), 1)) {
                i.remove();
            }
        }

        if (points.size() == 0) {
            System.out.println("ERROR: all samples removed (outside convex set)!");
            System.out.println("The following set didn't sample:");
            for (int j = 0; j < node.getTris().length; j++) {
                System.out.println(node.getTris()[j].toPlane());
            }
            System.exit(-1);
        }
    }

    /**
     * Distribute sample points along splitting planes of the BSP tree.
     *
     * @param node BSP tree node to sample
     * @param points list of points to add new points into
     */
    public static void distributeSamplePoints(Bsp node, LinkedList points) {
        if (node.isLeaf()) {
            cleanupLeafNodePoints(node, points);

            Vec3d[] samplePoints = new Vec3d[points.size()];
            points.toArray(samplePoints);

            node.setSamplePoints(samplePoints);
            System.out.print("\r" + (int) (100f * (float) ++numProcessed / (float) totalNodes) + "% done");
        } else {
            LinkedList newPoints = distributePoints(node);
            LinkedList rightPart = (LinkedList) newPoints.clone();
            LinkedList leftPart = (LinkedList) newPoints.clone();

            if (points != null) {
                cleanupPointsOutsideBBox(node, points);

                for (int i = 0; i < points.size(); i++) {
                    Vec3d point = (Vec3d) points.get(i);

                    int side = node.getDivider().calculateSide(point, maxError);

                    if (side == Plane.INFRONT) {
                        rightPart.add(point);
                    } else if (side == Plane.BEHIND) {
                        leftPart.add(point);
                    } else {
                        rightPart.add(point);
                        leftPart.add(point);
                    }
                }
            }

            distributeSamplePoints(node.getLeftChild(), leftPart);
            distributeSamplePoints(node.getRightChild(), rightPart);
        }
    }

    /**
     * Create sample points into entire BSP tree.
     *
     * @param tree BSP tree root node
     */
    public static void createSamples(Bsp tree) {
        // If the tree isn't divided, no need for PVS
        if (!tree.isLeaf()) {
            distributeSamplePoints(tree, null);
        }

        if (Bsp.verbose) {
            System.out.println(numSamples + " samples created.");
        }
        if (Bsp.verbose) {
            System.out.println("Greatest error was " + maxError);
        }
    }

    /**
     * Check if there exists a line of sight along the given ray.
     *
     * @param ray ray to check
     * @param startLeaf BSP tree leaf node that contains the start point of the
     * ray
     * @param endLeaf BSP tree leaf node that contains the end point of the ray
     * @return true if line of sight exists
     */
    public static boolean canSee(Ray ray, Bsp startLeaf, Bsp endLeaf) {
        if (startLeaf.equals(endLeaf)) {
            return true;
        }
        startLeaf.dfsValue = startLeaf.dfsCounter;
        for (int i = 0; i < startLeaf.portals.size(); i++) {
            Portal portal = (Portal) startLeaf.portals.get(i);
            Bsp leaf = (Bsp) portal.target;
            if (leaf.dfsValue < leaf.dfsCounter) {
                for (int j = 0; j < portal.portalSet.length; j++) {
                    if (ray.intersects(portal.portalSet[j])) {
                        boolean found = canSee(ray, leaf, endLeaf);
                        if (found) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if node2 can be seen from node1.
     *
     * @param node1 BSP tree leaf node
     * @param node2 BSP tree leaf node
     * @param treeRootNode root of the BSP tree
     * @return true, if node2 can be seen from node1
     */
    public static boolean checkVisibility(Bsp node1, Bsp node2, Bsp treeRootNode) {
        Vec3d[] samples1 = node1.getSamplePoints();
        Vec3d[] samples2 = node2.getSamplePoints();

        for (int i = 0; i < samples1.length; i++) {
            for (int j = 0; j < samples2.length; j++) {
                node1.prepareDFS();
                Ray ray = new Ray(samples1[i], samples2[j]);
                ray.extend(1);
                if (canSee(ray, node1, node2)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get the leaf nodes from BSP tree.
     *
     * @param tree BSP tree to get the leafs from
     * @param list list to add the leafs into
     */
    public static void getNodesFromBsp(Bsp tree, LinkedList list) {
        if (tree.isLeaf()) {
            list.add(tree);
        } else {
            getNodesFromBsp(tree.getLeftChild(), list);
            getNodesFromBsp(tree.getRightChild(), list);
        }
    }

    /**
     * Compute the visibility information to every leaf of the BSP tree.
     *
     * @param tree BSP tree root node
     */
    public static void traceVisibility(Bsp tree) {
        LinkedList nodeList = new LinkedList();

        getNodesFromBsp(tree, nodeList);
        numProcessed = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            Bsp node1 = (Bsp) nodeList.get(i);

            // Node is visible from itself
            node1.getPvs().add(node1);

            for (int j = 0; j < nodeList.size(); j++) {
                if (i == j) {
                    continue;
                }

                Bsp node2 = (Bsp) nodeList.get(j);
                if (node1.getPvs().contains(node2)) {
                    continue;
                }

                if (USE_PVS) {
                    if (checkVisibility(node1, node2, tree)) {
                        node1.getPvs().add(node2);
                        node2.getPvs().add(node1);
                    }
                } else {
                    node1.getPvs().add(node2);
                    node2.getPvs().add(node1);
                }

                if (Bsp.verbose) {
                    System.out.println(node1.getPvs().size() + " leafs visible");
                }
            }

            System.out.print("\r" + (int) (100f * (float) ++numProcessed / (float) totalNodes) + "% done");
        }
    }

    /**
     * Test whether a given point is a valid sample point to use in ray tracing
     * visibility. The point is NOT valid if it is behind any polygon or it lies
     * inside any polygon.
     *
     * @param point point to test
     * @param epsilon error-margin used in test
     * @return true if this sample is valid
     */
    public static boolean validSamplePoint(Vec3d point, Triangle[] polySet, float epsilon) {
        for (int i = 0; i < polySet.length; i++) {
            Plane plane = new Plane(polySet[i]);

            int side = plane.calculateSide(point, epsilon);

            if (side == Plane.BEHIND) {
                return false;
            }
            if (side == Plane.COINCIDAL && polySet[i].pointInside2(point, epsilon)) {
                return false;
            }
        }

        return true;
    }

//    /**
//     * For testing.
//     *
//     * @param args ignored
//     */
//    public static void main(String[] args) {
//        Bsp.create("data/test1.nse");
//    }
}
