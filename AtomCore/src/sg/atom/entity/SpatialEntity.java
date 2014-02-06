package sg.atom.entity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import sg.atom.gameplay.GameAction;

/**
 * A Predefined Entity which assume it has an associated Spatial.
 *
 * SptialEntity also contain a list(empty by default) of its action which is contribute to the gameworld. This is the most "common" kind of Entity available in a "comon" JME3 game
 *
 * @author atomix
 */
public class SpatialEntity extends Entity {

    protected Spatial spatial;
    protected ArrayList<GameAction> actions = new ArrayList<GameAction>();

    public SpatialEntity(String name, String type) {
        super(new Long(-1), name, type);
    }

    public SpatialEntity(Long id, String name, String type) {
        super(id, name, type);
    }

    public SpatialEntity(Long id, String type) {
        super(id, type);
    }

    public SpatialEntity(long id, String type) {
        super(new Long(id), type);
    }

    public SpatialEntity(String name, String type, Spatial spatial) {
        this(new Long(-1), name, type, spatial);
    }

    public SpatialEntity(Long id, String name, String type, Spatial spatial) {
        super(id, name, type);
        this.spatial = spatial;
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial model) {
        this.spatial = model;

    }

    public void addAction(GameAction action) {
        actions.add(action);
    }

    public <T extends Control> T getControl(Class<T> controlType) {
        return spatial.getControl(controlType);
    }
}
