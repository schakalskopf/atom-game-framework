package sg.atom.world.visibility.bsp;

import sg.atom.world.geometry.algebra.Vec3d;

import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;
import java.io.*;

import sg.atom.world.geometry.Plane;
import sg.atom.world.geometry.Polygon;
import sg.atom.world.geometry.Ray;
import sg.atom.world.geometry.Triangle;
import sg.atom.world.geometry.Vertex;

import sg.atom.world.geometry.PolygonData;
import sg.atom.world.geometry.collision.AABBox;

/**
 * Binary Space Partitioning tree. This data structure is used to to store the
 * world geometry. It provides fast elimination of unseen polygons and speeds up
 * collision detection.
 *
 * Last Modified 22.04.2002
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Bsp {

    // The root node of this tree.
    public Bsp root = null;
    // The name of the level from which the tree is generated.
    static String levelName;
    // Counters to identify a node.
    static int counter = 0;
    public int leafCounter;
    // The epsilon value used in algebraic calculations.
    public static float EPSILON = 1e-3F;
    // Constant used in binary space partition
    private static int MINRELATION_SCALE = 2;
    private static float MIN_RELATION = 1F;
    // Statistics
    private static int numSplits = 0;
    private static int numNodes = 0;
    private static int numTris = 0;
    // The amount of leafs. Only updated in the root node.
    private int numLeafs = 0;
    // Enables verbose printing
    public static boolean verbose = false;
    /**
     * The polygon that lies in the middle of the two sub trees.
     */
    public Plane divider;
    /**
     * The base for a portal polygon in the dividing plane.
     */
    public Polygon[] hugePortal;
    /**
     * The right sub tree of this node
     */
    public Bsp rightChild;
    /**
     * The left subtree of this node
     */
    public Bsp leftChild;
    /**
     * The set of triangles in this node before lightmap division
     */
    public Triangle[] prelightPolySet;
    /**
     * The set of triangles in this node
     */
    public Triangle[] polySet;
    /**
     * A list of polygons created in the lightmap process to eliminate borders
     */
    public LinkedList gumPolySet = new LinkedList();
    /**
     * The normals for the gum polygons (the gums are degenerate)
     */
    public LinkedList gumNormalSet = new LinkedList();
    /**
     * The set of polygons in this node
     */
    public Polygon[] actualPolySet;
    /**
     * The set of triangulated portals in this node
     */
    public Triangle[] portalSet;
    /**
     * The set of portals in this node
     */
    public Polygon[] portalPolySet;
    /**
     * The set of portals in this node
     */
    public Vector portals;
    /**
     * Bounding box for this node
     */
    private AABBox bbox;
    /**
     * Sample points used in ray tracing visibility
     */
    private Vec3d[] samplePoints;
    /**
     * The PVS set of the node
     */
    private LinkedList pvs = new LinkedList();
    /**
     * Static lights in scene
     */
    private static Vector lights = new Vector();
    /**
     * The center of this leaf (mass approximation)
     */
    private Vec3d center;
    /**
     * Values for handling DFS searches into the tree
     */
    public static int dfsCounter = 0;
    public transient int dfsValue = 0;
    // static temporary storage fields for portal generation
    public static Vector dividers;
    public static Vector nodes;
    public static Vector hugePortals;
    public static Vector leafs;

    /**
     * Creates a new Bsp mode.
     *
     * @param root The root node of this tree.
     * @param levelName The name of this level.
     * @param polySet The set of polygons to use in this node.
     * @param portalSet The set of possible portal polygons in this node.
     */
    private Bsp(Bsp root, String levelName, Polygon[] polySet, Polygon[] portalSet) {
        if (root == null) {
            this.root = this;
        } else {
            this.root = root;
        }
        this.levelName = levelName;
        portals = new Vector();
        numNodes++;

        if (polySet != null) {
            bbox = new AABBox(polySet);
            generateTree(this, polySet, portalSet);
        } else {
            System.out.println("WARNING: empty node created.");
        }
    }

    /**
     * Determines visibility in the tree using portals and a DFS search.
     *
     * @param startPoint The "starting" position.
     * @param endPoint The "end" position.
     * @return A value indicating if the points are visible from each other.
     */
    public synchronized boolean canSee(Vec3d startPoint, Vec3d endPoint) {
        Bsp startLeaf = this;
        Bsp endLeaf = root.locateLeafBrute(endPoint);
        Ray ray = new Ray(startPoint, endPoint);
        // A constant from the hat
        ray.extend(10.0f);
        prepareDFS();
        return Pvs.canSee(ray, startLeaf, endLeaf);
    }

    /**
     * Returns the number of leafs in this tree. Value is got from the root
     * node.
     *
     * @return Number of leafs in this tree.
     */
    public int getNumLeafs() {
        if (root == this) {
            return numLeafs;
        } else {
            return root.getNumLeafs();
        }
    }

    /**
     * Sets the number of leafs in the tree.
     *
     * @param value The number of leafs in the tree.
     */
    public void setNumLeafs(int value) {
        if (root == this) {
            numLeafs = value;
        } else {
            root.setNumLeafs(value);
        }
    }

    /**
     * Readies the DFS routine.
     */
    public void prepareDFS() {
        dfsCounter++;
    }

    /**
     * Performs a Depth First Search in the tree between two leafs. Portals are
     * considered edges here.
     *
     * @param result The resulting path is stored here if found.
     * @param currentLeaf The leaf from which to start.
     * @param destinaion The leaf to find.
     */
    private boolean DFS(Vector result, Bsp currentLeaf, Bsp destination) {
        if (currentLeaf.equals(destination)) {
            return true;
        }
        currentLeaf.dfsValue = dfsCounter;
        for (int i = 0; i < currentLeaf.portals.size(); i++) {
            Portal portal = (Portal) currentLeaf.portals.get(i);
            Bsp leaf = (Bsp) portal.target;
            if (leaf.dfsValue < dfsCounter) {
                boolean found = DFS(result, leaf, destination);
                if (found) {
                    result.add(portal.center);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests is the parameter is a decendant of this node.
     *
     * @param node The node to test.
     * @return A value indicating is the parameter is a decendant of this node.
     */
    public boolean isDecendant(Bsp node) {
        if (isLeaf()) {
            return false;
        }
        if (rightChild.equals(node)) {
            return true;
        }
        if (leftChild.equals(node)) {
            return true;
        }
        if (rightChild.isDecendant(node) == true) {
            return true;
        } else {
            return leftChild.isDecendant(node);
        }
    }

    /**
     * Return true, if this is a leaf node.
     *
     * @return true, if this node is a leaf
     */
    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }

    /**
     * Creates the portals in the tree. Detects connected leafs and clips the
     * possible portal polygons from each pair of connected leafs. If nonzero
     * polygons exist after the clipping, they are regarded as portals. Uses
     * data collected into temporary storage.
     *
     * @see #collectElements(Bsp)
     */
    void createConnections() {
        if (verbose) {
            System.out.println("The level has " + leafs.size() + " leafs.");
        }
        if (verbose) {
            System.out.println("The level has " + dividers.size() + " dividers.");
        }
        // For each divider find connected leafs
        for (int i = 0; i < dividers.size(); i++) {
            Plane divider = (Plane) (dividers.elementAt(i));
            Bsp dividerNode = (Bsp) (nodes.elementAt(i));
            Polygon[] hugePortal = (Polygon[]) (hugePortals.elementAt(i));
            Vector allLeafs = new Vector();
            for (int j = 0; j < leafs.size(); j++) {
                Bsp leaf = (Bsp) (leafs.elementAt(j));
                if (dividerNode.isDecendant(leaf)) {
                    boolean inserted = false;
                    Triangle[] polys = leaf.polySet;
                    for (int k = 0; k < polys.length; k++) {
                        if (divider.containsEdge(polys[k].getEdge(0), EPSILON)) {
                            if (inserted == false) {
                                allLeafs.add(leaf);
                                inserted = true;
                            }
                        }
                        if (divider.containsEdge(polys[k].getEdge(1), EPSILON)) {
                            if (inserted == false) {
                                allLeafs.add(leaf);
                                inserted = true;
                            }
                        }
                        if (divider.containsEdge(polys[k].getEdge(2), EPSILON)) {
                            if (inserted == false) {
                                allLeafs.add(leaf);
                                inserted = true;
                            }
                        }
                    }
                    Triangle[] portals = leaf.portalSet;
                    for (int k = 0; k < portals.length; k++) {
                        if (divider.containsEdge(portals[k].getEdge(0), EPSILON)) {
                            if (inserted == false) {
                                allLeafs.add(leaf);
                                inserted = true;
                            }
                        }
                        if (divider.containsEdge(portals[k].getEdge(1), EPSILON)) {
                            if (inserted == false) {
                                allLeafs.add(leaf);
                                inserted = true;
                            }
                        }
                        if (divider.containsEdge(portals[k].getEdge(2), EPSILON)) {
                            if (inserted == false) {
                                allLeafs.add(leaf);
                                inserted = true;
                            }
                        }
                    }
                }
            }
            /**
             * Clip each pair of connected leafs to find portal polygons. Create
             * portals as necessary. The algorithm is overkill but seems to
             * work.
             */
            for (int j = 0; j < allLeafs.size(); j++) {
                Bsp leaf = (Bsp) allLeafs.elementAt(j);
                Polygon[] portalSet = leaf.portalPolySet;
                for (int k = j + 1; k < allLeafs.size(); k++) {
                    Bsp leaf2 = (Bsp) allLeafs.elementAt(k);
                    Polygon[] combined = reducePortals(leaf2.actualPolySet, hugePortal);
                    combined = reducePortals(leaf2.portalPolySet, combined);
                    combined = reducePortals(leaf.actualPolySet, combined);
                    combined = reducePortals(leaf.portalPolySet, combined);
                    if (combined.length > 0) {
                        Triangle[] set = Triangulate.triangulateConvexPolygonSet(combined);
                        Triangle[] set2 = new Triangle[set.length];
                        for (int a = 0; a < set.length; a++) {
                            set2[a] = set[a].flipped();
                        }
                        float area = 0;
                        for (int a = 0; a < set.length; a++) {
                            area += set[a].area();
                        }
                        // determine the right directions
                        if (divider.calculateSide(leaf.center, EPSILON) == Plane.INFRONT) {
                            leaf.portals.add(new Portal(set2, leaf2));
                            leaf2.portals.add(new Portal(set, leaf));
                        } else {
                            leaf.portals.add(new Portal(set, leaf2));
                            leaf2.portals.add(new Portal(set2, leaf));
                        }
                        if (verbose) {
                            System.out.println("Divider " + i + "(" + divider + ") : Leafs " + leaf.leafCounter + " and " + leaf2.leafCounter + " are connected (" + area + ")!");
                        }
                    }
                }
            }
        }
    }

    /**
     * Collects various tree elements into temporary storage. The tree is
     * traversed recursively.
     *
     * @param node The current node.
     */
    void collectElements(Bsp node) {
        if ((node.isLeaf()) == false) {
            dividers.add(node.divider);
            nodes.add(node);
            hugePortals.add(node.hugePortal);
            collectElements(node.rightChild);
            collectElements(node.leftChild);
        } else {
            leafs.add(node);
        }
    }

    /**
     * Calculates the center point of the leaf as a centre of mass.
     */
    void calculateCenter() {
        float[] areas = new float[actualPolySet.length];
        float totalArea = 0;
        for (int i = 0; i < actualPolySet.length; i++) {
            areas[i] = actualPolySet[i].area();
            totalArea += areas[i];
        }
        center = new Vec3d(0, 0, 0);
        for (int i = 0; i < actualPolySet.length; i++) {
            center = center.add(actualPolySet[i].center().pos.scale(areas[i]));
        }
        center = center.scale(1.0f / totalArea);
    }

    /**
     * Removes degenerate polygons (small area)
     *
     * @param A set to prune.
     * @return The pruned set.
     */
    Triangle[] pruneDegenerates(Triangle[] tris) {
        LinkedList list = new LinkedList();
        for (int i = 0; i < tris.length; i++) {
            if (tris[i].area() > 0.05) {
                list.add(tris[i]);
            }
        }
        tris = new Triangle[list.size()];
        list.toArray(tris);
        return tris;
    }

    /**
     * Combines two triangles into a quad if possible. Used to quadify the
     * original level data. Quads make better lightmaps.
     *
     * @param a Triangle a
     * @param b Triangle b
     * @return The combined quad of null if not able to combine.
     */
    private static Polygon createQuad(Polygon a, Polygon b) {
        Vertex a0 = a.vertices[0];
        Vertex a1 = a.vertices[1];
        Vertex a2 = a.vertices[2];
        Vertex b0 = b.vertices[0];
        Vertex b1 = b.vertices[1];
        Vertex b2 = b.vertices[2];

        LinkedList shared = new LinkedList();
        LinkedList corners = new LinkedList();

        // check connection
        if (a0.equals(b0)) {
            shared.add(a0);
        }
        if (a0.equals(b1)) {
            shared.add(a0);
        }
        if (a0.equals(b2)) {
            shared.add(a0);
        }
        if (a1.equals(b0)) {
            shared.add(a1);
        }
        if (a1.equals(b1)) {
            shared.add(a1);
        }
        if (a1.equals(b2)) {
            shared.add(a1);
        }
        if (a2.equals(b0)) {
            shared.add(a2);
        }
        if (a2.equals(b1)) {
            shared.add(a2);
        }
        if (a2.equals(b2)) {
            shared.add(a2);
        }
        // check rectangularity
        if (shared.size() == 2) {
            Vec3d v1, v2;
            // a0
            v1 = a1.pos.sub(a0.pos);
            v2 = a2.pos.sub(a0.pos);
            if (v1.dot(v2) == 0) {
                if (!shared.contains(a0)) {
                    shared.add(a0);
                }
            }
            // a1
            v1 = a0.pos.sub(a1.pos);
            v2 = a2.pos.sub(a1.pos);
            if (v1.dot(v2) == 0) {
                if (!shared.contains(a1)) {
                    shared.add(a1);
                }
            }
            // a2
            v1 = a0.pos.sub(a2.pos);
            v2 = a1.pos.sub(a2.pos);
            if (v1.dot(v2) == 0) {
                if (!shared.contains(a2)) {
                    shared.add(a2);
                }
            }

            // b0
            v1 = b1.pos.sub(b0.pos);
            v2 = b2.pos.sub(b0.pos);
            if (v1.dot(v2) == 0) {
                if (!shared.contains(b0)) {
                    shared.add(b0);
                }
            }
            // b1
            v1 = b0.pos.sub(b1.pos);
            v2 = b2.pos.sub(b1.pos);
            if (v1.dot(v2) == 0) {
                if (!shared.contains(b1)) {
                    shared.add(b1);
                }
            }
            // b2
            v1 = b0.pos.sub(b2.pos);
            v2 = b1.pos.sub(b2.pos);
            if (v1.dot(v2) == 0) {
                if (!shared.contains(b2)) {
                    shared.add(b2);
                }
            }

            if (shared.size() == 4) {
                Vertex[] quad = new Vertex[4];
                quad[0] = (Vertex) shared.get(0);
                quad[1] = (Vertex) shared.get(2);
                quad[2] = (Vertex) shared.get(1);
                quad[3] = (Vertex) shared.get(3);
                Polygon result = new Polygon(quad, a.getPrimaryTextureName(), a.getSecondaryTextureName());
                if (result.toPlane().getNormal().equals(a.toPlane().getNormal())) {
                    return result;
                } else {
                    return result.flipped();
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Tries to combine a set of triangles into quads. Useful with lightmaps.
     *
     * @param data The triangle set to quadify.
     * @return The quadified set.
     */
    private static Polygon[] quadify(Polygon[] data) {
        LinkedList collection = new LinkedList();
        int i = 0;
        for (; i < data.length - 1;) {
            Polygon quad = createQuad(data[i], data[i + 1]);
            if (quad != null) {
                collection.add(quad);
                i += 2;
            } else {
                collection.add(data[i]);
                i++;
            }
        }
        if (i == data.length - 1) {
            collection.add(data[i]);
        }
        Polygon[] result = new Polygon[collection.size()];
        collection.toArray(result);
        return result;
    }

    /**
     * Performs the actual BSP tree generation from the given file. Also loads
     * other data from the level file.
     *
     * @param filename The file from which to load the level data.
     */
    public static Bsp create(String filename) {
        // Parse the filename for \s and change them to /s
        filename = filename.replace('\\', '/');

        long initTime = System.currentTimeMillis();
//        try {
//            // load supplemental free object data from the level file
//            ImportObjects objs = new ImportObjects(filename);
//            String objectFilename = filename.substring(0, filename.lastIndexOf('.')) + ".fol";
//            FileOutputStream stream = new FileOutputStream(objectFilename);
//            ObjectOutputStream os = new ObjectOutputStream(stream);
//            os.writeObject(objs.getObjects());
//            os.flush();
//            stream.close();
//        } catch (Exception e) {
//            System.out.println("Error saving objects !");
//            System.out.println(e.toString());
//            return null;
//        }

        // get the actual polygon data
        PolygonData data = new PolygonData(filename);
        Polygon[] polySet = data.toSingleArray();

        // create epsilon from the polygon data
        System.out.println("Computing epsilon for " + polySet.length + " polygons...");
        computeEpsilon(polySet);
        // quadify the raw triangle data
        polySet = quadify(polySet);


        if (EPSILON < 1e-3) {
            System.out.println("WARNING: computed epsilon is very small.");
        }

        System.out.println("Constructing BSP tree with epsilon " + EPSILON + "...");
        // make the BSP
        Bsp result = new Bsp(null, filename.substring(0, filename.lastIndexOf('.')), polySet, new Polygon[0]);

        long bspTime = (System.currentTimeMillis() - initTime);

        System.out.println("BSP tree constructed in " + (bspTime / 1000) + " seconds");
        System.out.println("\nStatistics:");
        System.out.println(numTris + " triangles");
        System.out.println(numSplits + " polygon splits");
        System.out.println(numNodes + " nodes in the tree");
        System.out.println(result.getNumLeafs() + " leafs in the tree\n");

        // lazy init
        dividers = new Vector();
        nodes = new Vector();
        hugePortals = new Vector();
        leafs = new Vector();

        System.out.println("Creating portals...");
        // calculate the portals
        result.collectElements(result);
        result.createConnections();

        System.out.println("Computing PVS...");
        // calculate the potentially visible set
        Pvs.totalNodes = result.getNumLeafs();
        System.out.println("Distributing samples...");
        Pvs.createSamples(result);
        System.out.println("\nTracing visibility...");
        Pvs.traceVisibility(result);



        long totalTime = (System.currentTimeMillis() - initTime) / 1000;
        System.out.println("\nTotal processing time: " + totalTime + " seconds.\n");

        return result;
    }

    /**
     * Compute the largest error we can safely use in building the BSP tree.
     * This is calculated to avoid artifacts produced by round-off errors in
     * floating point arithmetic.
     *
     * @param polySet a <code>Polygon[]</code> value
     */
    public static void computeEpsilon(Polygon[] polySet) {
        float candidate = Float.MAX_VALUE;

        for (int i = 0; i < polySet.length; i++) {
            for (int j = 0; j < polySet.length; j++) {
                if (i == j) {
                    continue;
                }

                for (int k = 0; k < polySet[i].getNumPoints(); k++) {
                    for (int l = 0; l < polySet[j].getNumPoints(); l++) {
                        Vec3d p1 = polySet[i].getVertex(k).pos;
                        Vec3d p2 = polySet[j].getVertex(l).pos;

                        float dx = p1.c[0] - p2.c[0];
                        float dy = p1.c[1] - p2.c[1];
                        float dz = p1.c[2] - p2.c[2];

                        float squaredDist = dx * dx + dy * dy + dz * dz;
                        if (squaredDist > 0 && squaredDist < candidate) {
                            candidate = squaredDist;
                        }

                    }
                }
            }
        }

        EPSILON = (float) Math.sqrt(candidate) / 10;
    }

    /**
     * Check if the given set of polygons is convex.
     *
     * @param polySet set of polygons to check
     * @return true, if the set is convex
     */
    public static boolean isConvexSet(Polygon[] polySet) {
        for (int i = 0; i < polySet.length; i++) {
            for (int j = 0; j < polySet.length; j++) {
                if ((i != j) && polySet[i].polygonInBack(polySet[j], EPSILON)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Search through the set of polygons and return the polygon that splits the
     * set into two with best results. If the set is convex, no splitting
     * polygon can be found.
     *
     * @param polySet set of polygons to search through
     * @param point a point from the divider is stored here
     * @return the best dividing polygon
     */
    public static Plane chooseDividingPolygon(Polygon[] polySet, Vec3d point) {
        if (verbose) {
            System.out.println("DEBUG: finding divider from " + polySet.length + " polys");
        }

        Polygon bestPoly = null;
        float minRelation = MIN_RELATION;
        float bestRelation = 0;
        int leastSplits = Integer.MAX_VALUE;

        while (bestPoly == null) {
            if (minRelation <= 0) {
                System.out.println("ERROR: couldn't find polygon to divide with!\n");
                System.out.println("The following set caused the error:");
                for (int i = 0; i < polySet.length; i++) {
                    System.out.println(polySet[i].toPlane());
                }

                System.exit(-1);
            }

            for (int i = 0; i < polySet.length; i++) {
                Polygon poly1 = polySet[i];
                if (poly1.getHasBeenUsed()) {
                    continue;
                }

                Plane plane = poly1.toPlane();

                /* Count the number of polygons on both sides and number of polygons
                 spanning the plane defined by the current polygon.*/
                int numPositive = 0, numNegative = 0, numSpanning = 0;
                for (int j = 0; j < polySet.length; j++) {
                    Polygon poly2 = polySet[j];
                    if (poly1 == poly2) {
                        continue;
                    }

                    int val = plane.calculateSide(poly2, EPSILON);

                    if (val == 1) {
                        numPositive++;
                    } else if (val == -1) {
                        numNegative++;
                    } else if (val == 2) {
                        numSpanning++;
                    }

                    if (numSpanning > leastSplits) {
                        break;
                    }
                }

                /* Calculate the relation between the number of polygons in the two
                 sets divided by the current polygon.*/
                float relation;
                if (numPositive < numNegative) {
                    relation = (float) numPositive / (float) numNegative;
                } else {
                    relation = (float) numNegative / (float) numPositive;
                }

                /* Compare the results given by the current polygon to the best this
                 far. If this polygon splits fewer polygons and the relation between
                 the resulting sets is acceptable, this is the new candidate polygon.
                 If the current polygon splits the same amount of polygons as the 
                 best polygon this far and the relation between the two resulting 
                 sets is better, this polygon is the new candidate polygon.*/

                if ((relation > minRelation) && ((numSpanning < leastSplits)
                        || ((numSpanning == leastSplits)
                        && (relation > bestRelation)))) {
                    bestPoly = poly1;
                    leastSplits = numSpanning;
                    bestRelation = relation;
                }
            }
            minRelation /= MINRELATION_SCALE;
        }

        bestPoly.setHasBeenUsed(true);
        Vec3d pos = bestPoly.getVertex(0).pos;
        point.c[0] = pos.c[0];
        point.c[1] = pos.c[1];
        point.c[2] = pos.c[2];
        return bestPoly.toPlane();
    }

    /**
     * Creates a huge polygon from a divider plane and a plane point. Used in
     * the portal calculation.
     *
     * @param divider The plane.
     * @param planePoint A point in the plane.
     * @return The resulting polygon (quad).
     */
    Polygon createHugePortal(Plane divider, Vec3d planePoint) {
        Vec3d n = divider.getNormal();
        /**
         * Compute two orthonormal vectors (u, v) that lay on the plane.
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
        Vertex[] vertices = new Vertex[4];
        vertices[0] = new Vertex(planePoint.add(u.add(v).scale(1000.0f)));
        vertices[1] = new Vertex(planePoint.add(v.sub(u).scale(1000.0f)));
        vertices[2] = new Vertex(planePoint.sub(u.add(v).scale(1000.0f)));
        vertices[3] = new Vertex(planePoint.add(u.sub(v).scale(1000.0f)));
        return new Polygon(vertices, null);
    }

    /**
     * Clips a set of portal polygons with a convex set of polygons. Redundant
     * code, but used to be sure.
     *
     * @param polySet The convex divider set.
     * @param portalSet The portal polygon set to clip.
     * @return The clipped set.
     */
    Polygon[] reducePortals(Polygon[] polySet, Polygon[] portalSet) {
        for (int i = 0; i < polySet.length; i++) {
            LinkedList positiveList = new LinkedList();
            Plane divider = polySet[i].toPlane();
            for (int j = 0; j < portalSet.length; j++) {
                Polygon poly = portalSet[j];
                int val = divider.calculateSide(poly, EPSILON);
                if (val == 1 || val == 0) {
                    positiveList.add(poly);
                } else if (val == 2) {
                    Polygon[] newPolys = poly.split(divider, EPSILON);
                    Polygon front = newPolys[0];
                    Polygon back = newPolys[1];
                    positiveList.add(front);
                }
            }
            portalSet = new Polygon[positiveList.size()];
            positiveList.toArray(portalSet);
        }
        return portalSet;
    }

    /**
     * Generates a BSP-tree recursively out of a set of polygons. The generation
     * stops when a convex sub-space is found.
     *
     * @param polySet set of polygons to create the tree from
     * @param portalSet set of potential portal polygons created so far.
     * @return the root node of the tree
     */
    private void generateTree(Bsp node, Polygon[] polySet, Polygon[] portalSet) {
        // are we ready ?
        if (isConvexSet(polySet)) {
            // Store the actual polygon set
            node.actualPolySet = polySet;
            // Calculate the center (uses actualPolySet)
            calculateCenter();
            // Store triangle data before lightmap division
            node.prelightPolySet = Triangulate.triangulateConvexPolygonSet(polySet);

            // Store the potential portals and reduce them to the convex set
            node.portalPolySet = reducePortals(polySet, portalSet);
            node.portalPolySet = reducePortals(portalSet, node.portalPolySet);
            // Store triangulated version too
            node.portalSet = Triangulate.triangulateConvexPolygonSet(portalPolySet);
            // Statistics and tracking
            leafCounter = counter++;
            setNumLeafs(getNumLeafs() + 1);
            numTris += node.polySet.length;
            if (verbose) {
                System.out.println("Leaf " + leafCounter + " is ready.");
            }
        } else {
            // find the space divider and create the potential portal 
            Vec3d planePoint = new Vec3d();
            node.divider = chooseDividingPolygon(polySet, planePoint);
            node.hugePortal = new Polygon[1];
            node.hugePortal[0] = createHugePortal(divider, planePoint);

            if (verbose) {
                System.out.println("DEBUG: dividing by plane " + node.divider);
            }

            // divide the set
            LinkedList positiveList = new LinkedList();
            LinkedList negativeList = new LinkedList();
            for (int i = 0; i < polySet.length; i++) {
                Polygon poly = polySet[i];

                int val = node.divider.calculateSide(poly, EPSILON);

                if (val == 1 || val == 0) {
                    positiveList.add(poly);
                } else if (val == -1) {
                    negativeList.add(poly);
                } else if (val == 2) {
                    Polygon[] newPolys = poly.split(node.divider, EPSILON);
                    numSplits++;

                    Polygon front = newPolys[0];
                    Polygon back = newPolys[1];

                    positiveList.add(front);
                    negativeList.add(back);
                }
            }

            Polygon[] negSet = new Polygon[negativeList.size()];
            Polygon[] posSet = new Polygon[positiveList.size()];

            negativeList.toArray(negSet);
            positiveList.toArray(posSet);

            // divide the portal set too
            LinkedList positivePortalList = new LinkedList();
            LinkedList negativePortalList = new LinkedList();
            for (int i = 0; i < portalSet.length; i++) {
                Polygon poly = portalSet[i];

                int val = node.divider.calculateSide(poly, EPSILON);

                if (val == 1 || val == 0) {
                    positivePortalList.add(poly);
                } else if (val == -1) {
                    negativePortalList.add(poly);
                } else if (val == 2) {
                    Polygon[] newPolys = poly.split(node.divider, EPSILON);
                    numSplits++;

                    Polygon front = newPolys[0];
                    Polygon back = newPolys[1];

                    positivePortalList.add(front);
                    negativePortalList.add(back);
                }
            }

            positivePortalList.add(node.hugePortal[0].flipped());
            negativePortalList.add(node.hugePortal[0]);

            Polygon[] negPortalSet = new Polygon[negativePortalList.size()];
            Polygon[] posPortalSet = new Polygon[positivePortalList.size()];

            negativePortalList.toArray(negPortalSet);
            positivePortalList.toArray(posPortalSet);

            // continue with the division
            node.leftChild = new Bsp(root, levelName, negSet, negPortalSet);
            node.rightChild = new Bsp(root, levelName, posSet, posPortalSet);
        }
    }

    /**
     * Locate a point in the tree with brute force. This is run from the root
     * leaf.
     *
     * @param pos The position to locate.
     * @return The leaf to contain pos.
     */
    public Bsp locateLeafBrute(Vec3d pos) {
        if (isLeaf()) {
            return this;
        }
        int side = divider.calculateSide(pos, EPSILON);
        if (side == Plane.BEHIND) {
            return leftChild.locateLeafBrute(pos);
        } else {
            return rightChild.locateLeafBrute(pos);
        }
    }

    /**
     * Locate a point in the tree by searching adjacent leafs to this leaf using
     * portals
     *
     * @param pos The position to locate.
     * @return The leaf to contain pos.
     */
    public Bsp locateLeaf(Vec3d pos) {
        Ray path = new Ray(center, pos);
        for (int i = 0; i < portals.size(); i++) {
            Portal portal = (Portal) portals.elementAt(i);
            Triangle[] polySet = portal.portalSet;
            for (int j = 0; j < polySet.length; j++) {
                if (path.intersects(polySet[j])) {
                    return (Bsp) portal.target;
                }
            }
        }
        return this;
    }

    /**
     * Checks if the given point is inside the set with a given tolerance.
     *
     * @param pos The point to check.
     * @param distance The cutting distance.
     * @return True if the point is inside the set , false otherwise.
     */
    public boolean pointInside(Vec3d pos, float distance) {
        for (int i = 0; i < polySet.length; i++) {
            if (polySet[i].distanceTo(pos) < distance) {
                return false;
            }
        }
        return true;
    }

    /**
     * A getter.
     */
    public Plane getDivider() {
        return divider;
    }

    /**
     * A getter.
     */
    public AABBox getBBox() {
        return bbox;
    }

    /**
     * Setter.
     *
     * @param points A set of pvs sample points.
     */
    public void setSamplePoints(Vec3d[] points) {
        samplePoints = points;
    }

    /**
     * A getter.
     */
    public Vec3d[] getSamplePoints() {
        return samplePoints;
    }

    /**
     * A getter.
     */
    public Bsp getLeftChild() {
        return leftChild;
    }

    /**
     * A getter.
     */
    public Bsp getRightChild() {
        return rightChild;
    }

    /**
     * A getter.
     */
    public Triangle[] getTris() {
        return prelightPolySet;
    }

    /**
     * A getter.
     */
    public LinkedList getPvs() {
        return pvs;
    }

    /**
     * Return a vector of primary texture names used in this leaf nodes
     * polygons.
     *
     * @return a vector of Strings
     */
    private Vector getPrimaryTextureNames() {
        if (!isLeaf()) {
            return null;
        }

        Vector vector = new Vector();

        for (int i = 0; i < polySet.length; i++) {
            if (!vector.contains(polySet[i].getPrimaryTextureName())) {
                vector.add(polySet[i].getPrimaryTextureName());
            }
        }

        return vector;
    }

    /**
     * Return a vector of secondary texture names used in this leaf nodes
     * polygogs.
     *
     * @return a vector of Strings
     */
    private Vector getSecondaryTextureNames() {
        if (!isLeaf()) {
            return null;
        }

        Vector vector = new Vector();

        for (int i = 0; i < polySet.length; i++) {
            if (!vector.contains(polySet[i].getSecondaryTextureName())) {
                vector.add(polySet[i].getSecondaryTextureName());
            }
        }

        return vector;
    }

    /**
     * Return an array of vectors containing polygons of this leaf node sorted
     * by their texture.
     *
     * @return polygons sorted by texture in an array of vectors
     */
    private Vector[][] getTrisByTexture() {
        if (!isLeaf()) {
            return null;
        }

        Vector primaryTextureNames = getPrimaryTextureNames();
        Vector secondaryTextureNames = getSecondaryTextureNames();

        Vector[][] byTexture;

        // The vector is indexed by primary and secondary texture indices.
        byTexture = new Vector[primaryTextureNames.size()][];
        for (int i = 0; i < primaryTextureNames.size(); i++) {
            byTexture[i] = new Vector[secondaryTextureNames.size()];
            for (int j = 0; j < secondaryTextureNames.size(); j++) {
                byTexture[i][j] = new Vector();
                for (int k = 0; k < polySet.length; k++) {
                    if (primaryTextureNames.get(i).equals(polySet[k].getPrimaryTextureName())
                            && secondaryTextureNames.get(j).equals(polySet[k].getSecondaryTextureName())) {
                        byTexture[i][j].add(polySet[k]);
                    }
                }
            }
        }
        return byTexture;
    }

    /**
     * Write the BSP tree into a ObjectOutput stream.
     *
     * @param out stream to write into
     * @exception IOException if an error occurs
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        if (isLeaf()) {
            out.writeBoolean(true);
            out.writeInt(leafCounter);

            Vector primaryTextureNames = getPrimaryTextureNames();
            Vector secondaryTextureNames = getSecondaryTextureNames();
            Vector[][] byTexture = getTrisByTexture();

            out.writeInt(primaryTextureNames.size()
                    * secondaryTextureNames.size());

            for (int i = 0; i < primaryTextureNames.size(); i++) {
                for (int j = 0; j < secondaryTextureNames.size(); j++) {
                    out.writeUTF((String) primaryTextureNames.get(i));
                    out.writeUTF((String) secondaryTextureNames.get(j));
                    out.writeInt(byTexture[i][j].size());
                    for (int k = 0; k < byTexture[i][j].size(); k++) {
                        ((Triangle) byTexture[i][j].get(k)).writeExternal(out);
                    }
                }
            }

            out.writeInt(actualPolySet.length);
            for (int i = 0; i < actualPolySet.length; i++) {
                actualPolySet[i].writeExternal(out);
            }

            out.writeInt(portals.size());
            for (Iterator it = portals.iterator(); it.hasNext();) {
                ((Portal) it.next()).writeExternal(out);
            }

            out.writeInt(pvs.size());
            for (Iterator it = pvs.iterator(); it.hasNext();) {
                out.writeInt(((Bsp) it.next()).leafCounter);
            }

            center.writeExternal(out);
        } else {
            out.writeBoolean(false);
            divider.writeExternal(out);
            leftChild.writeExternal(out);
            rightChild.writeExternal(out);
        }
    }

    /**
     * Save the BSP tree into a file.
     *
     * @param bsp BSP tree to save
     * @param levelFile name of file to save
     */
    public static void saveBsp(Bsp bsp, String levelFile) {
        try {
            System.out.println("Saving BSP to " + levelFile + ".bsp...");
            FileOutputStream stream = new FileOutputStream(levelFile + ".bsp");
            ObjectOutputStream os = new ObjectOutputStream(stream);
            bsp.writeExternal(os);
            os.flush();
            os.close();
            stream.close();
        } catch (Exception e) {
            System.out.println("Error saving BSP!");
            e.printStackTrace();
        }
    }
//FIXME: @atomix commented out!    
//    /**
//     * Entry point to the preprocessor.
//     *
//     * @param args command line arguments
//     */
//    public static void main(String[] args) {
//        if (args.length == 0) {
//            System.out.println("Usage: Bsp [-options] file\n");
//            System.out.println("Where options include:");
//            System.out.println("\t-verbose\tuse verbose output");
//            System.out.println("\t-lmap <x>\tspecify lightmap size");
//            System.out.println("\t-pvs <x>\tspecify PVS sample resolution");
//            System.out.println("\t-nolmaps\tdon't create lightmaps");
//            System.out.println("\t-nopvs\tdon't create PVS");
//            System.exit(0);
//        }
//
//        for (int i = 0; i < args.length - 1; i++) {
//            if (args[i].compareTo("-verbose") == 0) {
//                verbose = true;
//            } else if (args[i].compareTo("-lmap") == 0) {
//                LIGHTMAP_SIZE = Integer.parseInt(args[++i]);
//            } else if (args[i].compareTo("-pvs") == 0) {
//                Pvs.SAMPLE_RESOLUTION = Float.parseFloat(args[++i]);
//            } else if (args[i].compareTo("-nolmaps") == 0) {
//                lmaps = false;
//            } else if (args[i].compareTo("-nopvs") == 0) {
//                Pvs.USE_PVS = false;
//            }
//        }
//
//        System.out.println("building BSP from " + args[args.length - 1] + "...");
//        Bsp bsp = Bsp.create(args[args.length - 1]);
//        Bsp.saveBsp(bsp, args[args.length - 1].substring(0, args[args.length - 1].lastIndexOf('.')));
//
//    }
}
