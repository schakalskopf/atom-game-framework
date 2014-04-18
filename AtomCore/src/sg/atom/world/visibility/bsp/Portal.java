package sg.atom.world.visibility.bsp;

import java.io.*;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.geometry.Triangle;

/**
 * A class modeling a portal between two BSP leafs
 *
 * Last Modified: 23.04.2002
 *
 */
public class Portal implements Externalizable {

    /**
     * The set of portal triangles
     */
    public Triangle[] portalSet;
    /**
     * The other side
     */
    public Object target;
    /**
     * The weighted center of the portal polys by area
     */
    public Vec3d center;

    public Portal() {
    }

    /**
     * Creates a new portal from the given set of polygons and a target BSP
     * leaf. Calculates the center of the portal polygon set.
     */
    public Portal(Triangle[] portalSet, Bsp target) {
        this.portalSet = portalSet;
        this.target = target;
        float[] areas = new float[portalSet.length];
        float totalArea = 0;
        for (int i = 0; i < portalSet.length; i++) {
            areas[i] = portalSet[i].area();
            totalArea += areas[i];
        }
        center = new Vec3d(0, 0, 0);
        for (int i = 0; i < portalSet.length; i++) {
            center = center.add(portalSet[i].center().scale(areas[i]));
        }
        center = center.scale(1.0f / totalArea);
    }

    /**
     * Reads in an externalized Portal
     *
     * @param in The object input stream
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int portalSetLength = in.readInt();

        portalSet = new Triangle[portalSetLength];
        for (int i = 0; i < portalSetLength; i++) {
            portalSet[i] = new Triangle();
            portalSet[i].readExternal(in);
        }

        center = new Vec3d();
        center.readExternal(in);
        target = new Integer(in.readInt());
    }

    /**
     * Writes the Portal to the externalized stream
     *
     * @param out The object output stream
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(portalSet.length);
        for (int i = 0; i < portalSet.length; i++) {
            portalSet[i].writeExternal(out);
        }

        center.writeExternal(out);
        out.writeInt(((Bsp) target).leafCounter);
    }
}
