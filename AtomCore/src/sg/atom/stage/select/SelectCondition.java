package sg.atom.stage.select;

import com.jme3.scene.Spatial;

/**
 *
 * <p>FIXME: Replace with Guava predicate or atom condition
 * @author atomix
 */
public interface SelectCondition {

    enum SelectStatus {
        Selected, Deselected, Unknown
    }

    boolean isSelected(Spatial spatial);
}
