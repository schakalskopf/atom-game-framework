package sg.atom.world.geometry;

/**
 * A class represending an edge between two vertices.
 */
public class Edge {
    // The endpoints

    public Vertex a, b;

    /**
     * Constructs an empty edge.
     */
    public Edge() {
        a = new Vertex();
        b = new Vertex();
    }

    /**
     * Constructs an edge from the given vertices.
     *
     * @param a A vertex
     * @param b A vertex
     */
    public Edge(Vertex a, Vertex b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Returns the string representation of this Edge
     */
    public String toString() {
        return "[" + a.pos + ";" + a.pos + "]";
    }

    /**
     * Tests for equality.
     *
     * @param o The object to test.
     * @return True if the Edges are equal, false otherwise
     */
    public boolean equals(Object o) {
        Edge other = (Edge) o;
        if (a.pos.equals(other.a.pos) && b.pos.equals(other.b.pos)) {
            return true;
        } else if (a.pos.equals(other.b.pos) && b.pos.equals(other.a.pos)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tests for equality within a given presision.
     *
     * @param o The object to test.
     * @param epsilon The presicion.
     * @return True if the Edges are equal, false otherwise
     */
    public boolean equalsPresicion(Edge other, float epsilon) {
        if (a.pos.equalsPresicion(other.a.pos, epsilon) && b.pos.equalsPresicion(other.b.pos, epsilon)) {
            return true;
        } else if (a.pos.equalsPresicion(other.b.pos, epsilon) && b.pos.equalsPresicion(other.a.pos, epsilon)) {
            return true;
        } else {
            return false;
        }
    }
}
