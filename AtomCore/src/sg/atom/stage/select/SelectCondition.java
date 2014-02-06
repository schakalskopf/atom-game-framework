package sg.atom.stage.select;

import com.jme3.scene.Spatial;

/**
 *
 * @author atomix
 */
public interface SelectCondition {

    enum SelectStatus {
        Selected, Deselected, Unknown
    }

    boolean isSelected(Spatial spatial);
}
