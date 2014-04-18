package sg.atom.world.visibility.bsp;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.visibility.bsp.Portal;
import sg.atom.world.geometry.*;
import sg.atom.world.geometry.texture.TextureBank;
import sg.atom.world.geometry.texture.TextureInfo;

//import gl4java.*;

import java.io.*;
import java.util.Vector;

/**
 * BSP tree class used during runtime.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class RenderingBsp {

    /**
     * Verbose printing
     */
    static boolean verbose = false;
    /**
     * Left subtree
     */
    public RenderingBsp leftChild;
    /**
     * Right subtree
     */
    public RenderingBsp rightChild;
    /**
     * Dividing plane
     */
    public Plane divider;
    /**
     * Leaf number
     */
    public int leafCounter;
    /**
     * Textures used by polys in this leaf
     */
    public TextureInfo[][] textures;
    /**
     * Triangles grouped by texture
     */
    public Triangle[][] trisByTexture;
    /**
     * Non-triangulated polygons in this leaf
     */
    public Polygon[] actualPolySet;
    /**
     * Portals in this leaf
     */
    public Portal[] portals;
    /**
     * PVS of this leaf
     */
    public Vector pvs;
    /**
     * The center of the leaf
     */
    public Vec3d center;
    // Couters used in the Depth First Search
    public static int dfsCounter = 0;
    public transient int dfsValue = 0;

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
    private boolean DFS(Vector result, RenderingBsp currentLeaf, RenderingBsp destination) {

        if (currentLeaf.equals(destination)) {
            return true;
        }
        currentLeaf.dfsValue = dfsCounter;
        for (int i = 0; i < currentLeaf.portals.length; i++) {
            Portal portal = currentLeaf.portals[i];
            RenderingBsp leaf = (RenderingBsp) portal.target;
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

    public RenderingBsp() {
    }

    /**
     * Is the node a leaf node
     *
     * @return true, if this is a leaf node
     */
    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }

    /**
     * Read a RenderingBsp object from ObjectInput stream
     *
     * @param in stream to read the RenderingBsp from
     * @exception IOException if an error occurs
     */
    public void readExternal(ObjectInput in, TextureBank bank) throws IOException {
        boolean isLeaf = in.readBoolean();

        if (isLeaf) {
            try {
                leafCounter = in.readInt();
                if (verbose) {
                    System.out.println("Leaf counter: " + leafCounter);
                }

                int numTextures = in.readInt();
                if (verbose) {
                    System.out.println("Leaf textures: " + numTextures);
                }
                trisByTexture = new Triangle[numTextures][];
                textures = new TextureInfo[2][numTextures];

                for (int i = 0; i < numTextures; i++) {
                    String textureFilename1 = in.readUTF();
                    String textureFilename2 = in.readUTF();
                    if (verbose) {
                        System.out.println("Leaf texture1 " + i + 1 + ": " + textureFilename1);
                    }
                    if (verbose) {
                        System.out.println("Leaf texture2 " + i + 1 + ": " + textureFilename2);
                    }
                    textures[0][i] = bank.find(textureFilename1, textureFilename1);
                    textures[1][i] = bank.find(textureFilename2 + ".png", textureFilename2 + ".png");

                    // Lightmaps should never repeat
//                    textures[1][i].setWrapMode(GLEnum.GL_CLAMP);

                    int tris = in.readInt();
                    trisByTexture[i] = new Triangle[tris];

                    if (verbose) {
                        System.out.println("Textures " + textureFilename1 + " and " + textureFilename2 + " have " + tris + " triangles");
                    }
                    for (int j = 0; j < tris; j++) {
                        trisByTexture[i][j] = new Triangle();
                        trisByTexture[i][j].readExternal(in);
                    }
                }

                int polys = in.readInt();
                actualPolySet = new Polygon[polys];

                if (verbose) {
                    System.out.println("Leaf has " + polys + " polygons");
                }
                for (int i = 0; i < polys; i++) {
                    actualPolySet[i] = new Polygon();
                    actualPolySet[i].readExternal(in);
                }

                int ports = in.readInt();
                portals = new Portal[ports];

                if (verbose) {
                    System.out.println("Leaf has " + ports + " portals");
                }
                for (int i = 0; i < ports; i++) {
                    portals[i] = new Portal();
                    portals[i].readExternal(in);
                }

                int pvsSize = in.readInt();
                pvs = new Vector();

                if (verbose) {
                    System.out.println(pvsSize + " leafs are visible");
                }
                for (int i = 0; i < pvsSize; i++) {
                    pvs.add(new Integer(in.readInt()));
                }

                center = new Vec3d();
                center.readExternal(in);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            divider = new Plane();
            divider.readExternal(in);

            leftChild = new RenderingBsp();
            rightChild = new RenderingBsp();

            leftChild.readExternal(in, bank);
            rightChild.readExternal(in, bank);
        }
    }

    /**
     * Get the leafs nodes from RenderingBsp in a Vector
     *
     * @param vector container to store the leafs to
     */
    public void getLeafs(Vector vector) {
        if (isLeaf()) {
            vector.add(this);
        } else {
            leftChild.getLeafs(vector);
            rightChild.getLeafs(vector);
        }
    }

    /**
     * Convert the PVS sets from integers to references. The PVS are stored in
     * file as integers and they need to be converted to references for easier
     * handling.
     *
     */
    private void convertPvs() {
        Vector leafs = new Vector();

        getLeafs(leafs);

        for (int i = 0; i < leafs.size(); i++) {
            for (int j = 0; j < ((RenderingBsp) leafs.get(i)).pvs.size(); j++) {
                int id = ((Integer) ((RenderingBsp) leafs.get(i)).pvs.get(j)).intValue();

                for (int k = 0; k < leafs.size(); k++) {
                    if (id == ((RenderingBsp) leafs.get(k)).leafCounter) {
                        ((RenderingBsp) leafs.get(i)).pvs.set(j, leafs.get(k));
                    }
                }
            }
        }
    }

    /**
     * Convert the portal targets from integers to references. The portal
     * targets are stored in file as integers and they need to be converted to
     * references for easier handling.
     *
     */
    private void convertPortals() {
        Vector leafs = new Vector();

        getLeafs(leafs);

        for (int i = 0; i < leafs.size(); i++) {
            for (int j = 0; j < ((RenderingBsp) leafs.get(i)).portals.length; j++) {
                Portal portal = ((RenderingBsp) leafs.get(i)).portals[j];

                int counter = -1;
                while (((RenderingBsp) leafs.get(++counter)).leafCounter != ((Integer) portal.target).intValue());

                portal.target = leafs.get(counter);
            }
        }
    }
//
//    /**
//     * Load the BSP tree from file.
//     *
//     * @param filename filename to load from
//     * @param bank texture bank to store the textures into
//     * @return true if succeeded
//     */
//    public boolean load(String filename, TextureBank bank) {
//        try {
//            System.out.println("Loading BSP from " + filename);
//            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
//
//            readExternal(in, bank);
//            in.close();
//
//            convertPvs();
//            convertPortals();
//
//            return true;
//        } catch (Exception e) {
//            System.out.println("Loading BSP failed");
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Render everything visible from given leaf node.
//     *
//     * @param currentLeaf the leaf node the viewer is in
//     * @param bank texture bank containing the textures of the tree
//     */
//    public void render(RenderingBsp currentLeaf, TextureBank bank) {
//        for (int i = 0; i < currentLeaf.pvs.size(); i++) {
//            ((RenderingBsp) currentLeaf.pvs.get(i)).renderLeaf(bank);
//        }
//    }
//
//    /**
//     * Render the contents of this node.
//     *
//     * @param bank texture bank containing the textures of this node
//     */
//    private void renderLeaf(TextureBank bank) {
//        GL.gl.glActiveTextureARB(GLEnum.GL_TEXTURE1_ARB);
//        GL.gl.glEnable(GLEnum.GL_TEXTURE_2D);
//        GL.gl.glActiveTextureARB(GLEnum.GL_TEXTURE0_ARB);
//        GL.gl.glEnable(GLEnum.GL_TEXTURE_2D);
//
//        for (int j = 0; j < textures[0].length; j++) {
//            textures[0][j].bind();
//            GL.gl.glActiveTextureARB(GLEnum.GL_TEXTURE1_ARB);
//            textures[1][j].bind();
//            GL.gl.glActiveTextureARB(GLEnum.GL_TEXTURE0_ARB);
//
//            GL.gl.glBegin(GLEnum.GL_TRIANGLES);
//            for (int i = 0; i < trisByTexture[j].length; i++) {
//                GL.gl.glTexCoord2fv(trisByTexture[j][i].a.tex.c);
//                GL.gl.glMultiTexCoord2fv(GLEnum.GL_TEXTURE1_ARB, trisByTexture[j][i].a.tex2.c);
//                GL.gl.glNormal3fv(trisByTexture[j][i].a.normal.c);
//                GL.gl.glVertex3fv(trisByTexture[j][i].a.pos.c);
//
//                GL.gl.glTexCoord2fv(trisByTexture[j][i].b.tex.c);
//                GL.gl.glMultiTexCoord2fv(GLEnum.GL_TEXTURE1_ARB, trisByTexture[j][i].b.tex2.c);
//                GL.gl.glNormal3fv(trisByTexture[j][i].b.normal.c);
//                GL.gl.glVertex3fv(trisByTexture[j][i].b.pos.c);
//
//                GL.gl.glTexCoord2fv(trisByTexture[j][i].c.tex.c);
//                GL.gl.glMultiTexCoord2fv(GLEnum.GL_TEXTURE1_ARB, trisByTexture[j][i].c.tex2.c);
//                GL.gl.glNormal3fv(trisByTexture[j][i].c.normal.c);
//                GL.gl.glVertex3fv(trisByTexture[j][i].c.pos.c);
//            }
//            GL.gl.glEnd();
//
//        }
//
//
//        GL.gl.glActiveTextureARB(GLEnum.GL_TEXTURE1_ARB);
//        GL.gl.glDisable(GLEnum.GL_TEXTURE_2D);
//        GL.gl.glActiveTextureARB(GLEnum.GL_TEXTURE0_ARB);
//    }

    /**
     * Get the leaf node that contains the given point by brute force.
     *
     * @param pos position to find
     * @return leaf node that contains the point
     */
    public RenderingBsp locateLeafBrute(Vec3d pos) {
        if (isLeaf()) {
            return this;
        }

        int side = divider.calculateSide(pos, 0.01f);
        if (side == Plane.BEHIND) {
            return leftChild.locateLeafBrute(pos);
        } else {
            return rightChild.locateLeafBrute(pos);
        }
    }

    /**
     * Locate a point in the tree by searching adjacent leafs to this leaf using
     * portals. Tests for ray intersection from the center of the leaf to the
     * point
     *
     * @param pos The position to locate.
     * @return The leaf to contain pos.
     */
    public RenderingBsp locateLeaf(Vec3d pos) {
        Ray path = new Ray(center, pos);

        for (int i = 0; i < portals.length; i++) {
            Portal portal = portals[i];
            Triangle[] polySet = portal.portalSet;
            for (int j = 0; j < polySet.length; j++) {
                if (path.intersects(polySet[j])) {
                    return (RenderingBsp) portal.target;
                }
            }
        }

        return this;
    }

    /**
     * Locate a point in the tree by searching adjacent leafs to this leaf using
     * portals. Tests for ray intersection from the given oldPos to the pos
     *
     * @param oldPos The position in the current leaf.
     * @param pos The position to locate.
     * @return The leaf to contain pos.
     */
    public RenderingBsp locateLeaf(Vec3d pos, Vec3d oldPos) {
        Ray path = new Ray(oldPos, pos);

        for (int i = 0; i < portals.length; i++) {
            Portal portal = portals[i];
            Triangle[] polySet = portal.portalSet;
            for (int j = 0; j < polySet.length; j++) {
                if (path.intersects(polySet[j])) {
                    return (RenderingBsp) portal.target;
                }
            }
        }

        return this;
    }

    /**
     * Checks if the given point is inside the set
     *
     * @param pos The point to check.
     * @return True if the point is inside the set , false otherwise.
     */
    public boolean pointInsideSimple(Vec3d pos) {
        for (int j = 0; j < actualPolySet.length; j++) {
            Plane plane = actualPolySet[j].toPlane();
            float dist = plane.classifyPoint(pos);
            if (dist < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the number of leafs in this tree.
     *
     * @return Number of leafs in this tree.
     */
    public int getNumLeafs() {
        Vector leafs = new Vector();

        getLeafs(leafs);
        return leafs.size();
    }

    /**
     * If the given point is within given distance from polygons in this leaf,
     * return the plane that the point collides with.
     *
     * @param point point to test
     * @param distance threshold distance
     * @return null if no collision
     */
    public Plane collidesWithWalls(Vec3d point, float distance) {
        for (int j = 0; j < textures[0].length; j++) {
            for (int i = 0; i < trisByTexture[j].length; i++) {
                Plane plane = trisByTexture[j][i].toPlane();

                if ((plane.classifyPoint(point) < distance)
                        && (trisByTexture[j][i].pointInside2(point, distance))) {
                    return plane;
                }
            }
        }

        return null;
    }

    /**
     * Finds a path from point1 to point2 in the tree. The points are located
     * with brute force from the root node. Uses the DFS with portals to find
     * the path.
     *
     * @param point1 The starting point
     * @param point2 The ending point
     * @param root The root of this tree
     * @return A vector of points describing the path
     */
    public Vector findPath(Vec3d point1, Vec3d point2, RenderingBsp root) {
        RenderingBsp startLeaf = root.locateLeafBrute(point2);
        RenderingBsp endLeaf = root.locateLeafBrute(point1);
        prepareDFS();
        startLeaf.dfsValue = dfsCounter;
        Vector route = new Vector();
        route.add(point1);
        if (DFS(route, startLeaf, endLeaf) == true) {
            route.add(point2);
            for (int i = 0; i < route.size(); i++) {
                Vec3d point = (Vec3d) route.get(i);
            }
            return route;
        } else {
            return null;
        }
    }

    /**
     * Returns the PVS
     *
     * @return The PVS
     */
    public Vector getPvs() {
        return pvs;
    }

    /**
     * Determines visibility in the tree using portals and a DFS search.
     *
     * @param startPoint The "starting" position.
     * @param endPoint The "end" position.
     * @param root The root of this tree
     * @return A value indicating if the points are visible from each other.
     */
    public synchronized boolean canSee(Vec3d startPoint, Vec3d endPoint, RenderingBsp root) {
        RenderingBsp startLeaf = this;
        RenderingBsp endLeaf = root.locateLeafBrute(endPoint);
        Ray ray = new Ray(startPoint, endPoint);
        ray.extend(10.0f);
        prepareDFS();
        return canSee(ray, startLeaf, endLeaf);
    }

    /**
     * Determines visibility in the tree using portals. Points are visibly
     * connected if the ray intersects only portal polygons.
     *
     * @param The ray between the points.
     * @param startLeaf The "starting" leaf.
     * @param endLeaf The "ending" leaf.
     * @return A value indicating if the points are visible from each other.
     */
    public static boolean canSee(Ray ray, RenderingBsp startLeaf, RenderingBsp endLeaf) {
        if (startLeaf.equals(endLeaf)) {
            return true;
        }
        startLeaf.dfsValue = startLeaf.dfsCounter;
        for (int i = 0; i < startLeaf.portals.length; i++) {
            Portal portal = startLeaf.portals[i];
            RenderingBsp leaf = (RenderingBsp) portal.target;
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

//    /**
//     * For testing.
//     *
//     * @param args ignored
//     */
//    public static void main(String[] args) {
//        RenderingBsp bsp = new RenderingBsp();
//
//        bsp.load("data/test3.bsp", new TextureBank());
//    }
}
