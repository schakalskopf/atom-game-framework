package sg.atom.world.geometry;

import sg.atom.world.geometry.algebra.Vec3d;
import sg.atom.world.geometry.algebra.Vec2d;
import java.io.*;

/**
 * Vertex is a point in 3D space that has surface normal, texture coordinates
 * and color specified.
 *
 * @author <a href="mailto:enuuros@cc.hut.fi">Esa Nuuros</a>
 */
public class Vertex {
    // Position in space

    public Vec3d pos;
    // Vertex normal
    public Vec3d normal;
    // Texture coordinates 1
    public Vec2d tex;
    // Texture coordinates 2
    public Vec2d tex2;
    // Vertex color
    public Vec3d color;

    public Vertex() {
        this(new Vec3d());
    }

    /**
     * Constructs a Vertex in the given position.
     *
     * @param pos The position of the Vertex.
     */
    public Vertex(Vec3d pos) {
        this.pos = new Vec3d(pos);
        normal = new Vec3d();
        tex = new Vec2d();
        tex2 = new Vec2d();
        color = new Vec3d();
    }

    /**
     * Copy constructor
     *
     * @param copy The copy Vertex
     */
    public Vertex(Vertex copy) {
        pos = new Vec3d(copy.pos);
        normal = new Vec3d(copy.normal);
        tex = new Vec2d(copy.tex);
        tex2 = new Vec2d(copy.tex2);
        color = new Vec3d(copy.color);
    }

    /**
     * Full constructor.
     *
     */
    public Vertex(Vec3d position, Vec3d vertexNormal, Vec2d texcoord, Vec2d texcoord2, Vec3d vertexColor) {
        this.pos = position;
        this.normal = vertexNormal;
        this.tex = texcoord;
        this.tex2 = texcoord2;
        this.color = vertexColor;
    }

    /**
     * A constructor without secondary texture coordinates.
     *
     */
    public Vertex(Vec3d position, Vec3d vertexNormal, Vec2d texcoord, Vec3d vertexColor) {
        this.pos = position;
        this.normal = vertexNormal;
        this.tex = texcoord;
        this.tex2 = new Vec2d();
        this.color = vertexColor;
    }

    /**
     * Adds another Vertex to this.
     *
     * @param other The other Vertex to add.
     */
    public void addTo(Vertex other) {
        pos.addTo(other.pos);
        normal.addTo(other.normal);
        tex.addTo(other.tex);
        tex2.addTo(other.tex2);
        color.addTo(other.color);
    }

    /**
     * Scales this Vertex.
     *
     * @param value The scale factor.
     */
    public void scaleTo(float value) {
        pos.scaleTo(value);
        normal.scaleTo(value);
        tex.scaleTo(value);
        tex2.scaleTo(value);
        color.scaleTo(value);
    }

    /**
     * The string description of this Vertex.
     *
     * @return The string description of this Vertex.
     */
    public String toString() {
        return new String("Pos: " + pos + "\nNormal: " + normal + "\nUV: " + tex + "\nColor: " + color);
    }

    /**
     * Read vertex from ObjectInput stream.
     *
     * @param in stream to read from
     * @exception IOException if an error occurs
     * @exception ClassNotFoundException if an error occurs
     */
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        pos.readExternal(in);
        normal.readExternal(in);
        tex.readExternal(in);
        tex2.readExternal(in);
        color.readExternal(in);
    }

    /**
     * Write vertex into ObjectOutput stream.
     *
     * @param out stream to write into
     * @exception IOException if an error occurs
     */
    public void writeExternal(ObjectOutput out)
            throws IOException {
        pos.writeExternal(out);
        normal.writeExternal(out);
        tex.writeExternal(out);
        tex2.writeExternal(out);
        color.writeExternal(out);
    }
}
