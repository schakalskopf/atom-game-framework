/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import sg.atom.core.StageManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EntityManager {
    protected StageManager stageManager;
    protected EntityFactory entityFactory;
    protected HashMap<Long, Entity> entities = new HashMap<Long, Entity>();
    private long totalEntityId = -1;
    public EntityManager(StageManager stageManager) {
        this.stageManager = stageManager;
        this.entityFactory = new EntityFactory(this, stageManager);
    }


    void registerEntityType() {
    }

    void registerEntityTypes() {
    }

    ArrayList<String> getEntityAssets() {
        return new ArrayList<String>();
    }

    public void addEntity(Entity e) {
        Long newId = getNewEntityId();
        e.id = newId;
        entities.put(newId, e);
    }

    public void removeEntity(Long id) {
    }

    public void removeEntity(Entity e) {
    }

    public boolean isEntitySpatial(Spatial selectableSpatial) {
        return true;
    }

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

    public Long getNewEntityId() {
        totalEntityId++;
        return new Long(totalEntityId);
    }

    public <T extends EntityFactory> T getEntityFactory(Class<T> clazz) {
        return (T) entityFactory;
    }

    public void init() {
    }

    public Entity toEntity(Spatial sp) {
        return null;
    }

    public Spatial getEntity(Spatial spatial) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Entity getEntityById(long id) {
        return entities.get(id);
    }

    public void setEntityById(long id, Entity newEntity) {
        entities.put(id, newEntity);
    }
}
