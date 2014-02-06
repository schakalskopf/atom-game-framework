package sg.atom.fx.tween;

/**
 *
 * @author atomix
 */
public interface Interpolator<V> {

    public V interpolate(V a, V b);
}
