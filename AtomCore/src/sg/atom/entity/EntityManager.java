/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.stage.StageManager;

/**
 * An simple EntityManager implementation which have basic Spatial - Entity
 * relationship management
 *
 * FIXME: Replace or intergrate with Zay-ES or Artemis
 *
 * @author atomix
 */
public class EntityManager implements IGameCycle {

    protected StageManager stageManager;
    protected EntityFactory entityFactory;
    // Entity management 
    protected HashMap<Long, Entity> entities = new HashMap<Long, Entity>();
    private long totalEntityId = -1;

    public EntityManager(StageManager stageManager) {
        this.stageManager = stageManager;
        this.entityFactory = new EntityFactory(this, stageManager);
    }

    /* Manage entities's type as primary lookup methods */
    public void registerEntityType() {
    }

    public void registerEntityTypes() {
    }

    public ArrayList<String> getEntityAssets() {
        return new ArrayList<String>();
    }

    public Long getNewEntityId() {
        totalEntityId++;
        return new Long(totalEntityId);
    }

    public void addEntity(Entity e) {
        if (e.id == null) {
            Long newId = getNewEntityId();
            e.id = newId;
        }
        entities.put(e.id, e);
    }

    public void removeEntity(Long id) {
        entities.remove(id);
    }

    public void removeEntity(Entity e) {
        entities.remove(e.id);
    }

    /**
     * Should be overiden to determinate the relationship between a spatial and
     * its associated Entity
     *
     * @param selectableSpatial
     * @return
     */
    public boolean isEntitySpatial(Spatial selectableSpatial) {
        return true;
    }
    /* Search and filter over entities */

    public ArrayList<SpatialEntity> getAllSpatialEntities() {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();
        for (Entity entity : entities.values()) {
            if (entity instanceof SpatialEntity) {
                result.add((SpatialEntity) entity);
            }
        }
        //
        return result;
    }

    public ArrayList<SpatialEntity> getAllSpatialEntitiesByGroup(String groupName) {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();
        for (Entity entity : entities.values()) {
            if (entity instanceof SpatialEntity) {
                if (entity.getGroup().equals(groupName)) {
                    result.add((SpatialEntity) entity);
                }
            }
        }
        //
        return result;
    }

    public <T extends Entity> ArrayList<T> getEntitiesByClass(Class<T> clazz) {
        // do filter...
        ArrayList<T> result = new ArrayList<T>();
        for (Entity entity : entities.values()) {
            if (clazz.isAssignableFrom(entity.getClass())) {
                result.add((T) entity);
            }
        }
        //
        return result;
    }

    public <T extends EntityFactory> T getEntityFactory(Class<T> clazz) {
        return (T) entityFactory;
    }

    public Entity toEntity(Spatial sp) {
        return null;
    }

    public Entity getEntityBySpatial(Spatial spatial) {
        return null;
    }

    public Entity getEntityById(long id) {
        return entities.get(id);
    }

    public void setEntityById(long id, Entity newEntity) {
        entities.put(id, newEntity);
    }

    @Override
    public void init() {
    }

    @Override
    public void load() {
    }

    @Override
    public void config(Properties props) {
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void finish() {
    }

    @Override
    public LifeCyclePhase getCurrentPhase() {
        return null;
    }

    @Override
    public float getProgressPercent(LifeCyclePhase aPhrase) {
        return 0;
    }
}
