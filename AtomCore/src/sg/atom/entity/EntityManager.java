/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import com.google.common.base.Function;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.entity.general.AbstractComponent;
import sg.atom.entity.general.AbstractEntity;
import sg.atom.entity.general.EntityRepository;
import sg.atom.stage.StageManager;

/**
 * An simple EntityManager implementation which have basic Spatial - Entity
 * relationship management.
 *
 * <ul>
 *
 * <li>It has a Cache implementation of original entities beside of one in
 * AssetManager.</li>
 *
 * <li> Also support dependency injection to create Entity.</li>
 *
 * <li> It hashed its managed entities with regular "good" hash method to manage
 * indexing.</li>
 *
 * <li> provides Functions like
 * set/get/insert/remove/swap/replace/lookup/search/scanning/distributing/inheritance
 * inspecting over its managed entities. <b>aka. 1 step ref or direct
 * modification</b></li>
 *
 * <li> Provide services over its managed entities. <b>aka. 2 steps
 * refs</b></li>
 *
 * <li> Provide reflection, references/ versioned tricks and other functions
 * over lookup result of Components. <b>aka. 3 steps refs</b></li>
 *
 * <li> open gates to manage also components of Entities (like other ES
 * implementation). In the moment of speech, other ES implementation "usually"
 * manage a "isolated view" of available components. Its meant to be using
 * "Class level of atomicity" in Java language!</li>
 *
 * <li> Most important, It open gates to managed actor framework and the
 * transactional memory model.</li> </ul>
 *
 * <p><b>NOTE:</b> Overview, the system works like this: <ul>
 *
 * <li>A waving of access (can be asynchoronous as in concurrent processing...)
 * to Entities lead to a waving of lookup (non-access) to their associated
 * Component(s).</li>
 *
 * <li>The reference allow direct modification or transactional
 * modification.</li>
 *
 * <li></li> </p> FIXME: Replace or intergrate with Zay-ES or Artemis.
 *
 * @author atomix
 */
@Deprecated
public class EntityManager implements IGameCycle, EntityRepository {

    protected StageManager stageManager;
    protected EntityFactory entityFactory;
    // Entity management 
    protected HashMap<Long, Entity> entities = new HashMap<Long, Entity>();
    private long totalEntityId = -1;
    // Lookup
    protected Function<AbstractEntity, AbstractComponent> lookupFunction = null;
    // Services
    public static long NONE_ID = -1;

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
        if (e.id == null || e.id == NONE_ID) {
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
            System.out.println(" ByGroup " + entity.id);
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
