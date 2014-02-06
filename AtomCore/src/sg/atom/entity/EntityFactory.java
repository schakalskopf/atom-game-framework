/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import sg.atom.stage.StageManager;
import sg.atom.stage.WorldManager;
import sg.atom.utils.factory.AtomFactory;

/**
 * EntityFactory to procedure Entity. (CommonImplementation) Consider as
 * Suggestion to use the Factory pattern along with EntitySystem.
 *
 * @author atomix
 */
public class EntityFactory implements AtomFactory<Entity> {

    protected EntityManager entityManager;
    protected StageManager stageManager;
    protected WorldManager worldManager;

    public EntityFactory(EntityManager entityManager, StageManager stageManager) {
        this.entityManager = entityManager;
        this.stageManager = stageManager;
        this.worldManager = stageManager.getWorldManager();
    }

    @Override
    public Entity create(Object param) {
        return null;
    }

    @Override
    public Entity create(Object... params) {
        return null;
    }

    @Override
    public Entity cloneObject(Entity orginal) {
        return null;
    }
}
