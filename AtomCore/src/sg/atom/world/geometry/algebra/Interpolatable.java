package sg.atom.world.geometry.algebra;

/**
 * An interface describing methods needed for a type to be interpolatable.
 */
public interface Interpolatable {
    // addition with a copied result

    public Interpolatable iadd(Interpolatable v);
    // addition with the result stored in object

    public Interpolatable iaddTo(Interpolatable v);
    // subtraction with a copied result

    public Interpolatable isub(Interpolatable v);
    // subtraction with the result stored in object

    public Interpolatable isubFrom(Interpolatable v);
    // scale with a copied result

    public Interpolatable iscale(float s);
    // scale with the result stored in object

    public Interpolatable iscaleTo(float s);
    // square of the length

    public float lensq();
    // length

    public float len();
    // description string

    public String toString();
}
