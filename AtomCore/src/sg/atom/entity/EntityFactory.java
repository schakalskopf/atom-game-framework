/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import sg.atom.stage.StageManager;
import sg.atom.stage.WorldManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class EntityFactory {

    protected EntityManager entityManager;
    protected StageManager stageManager;
    protected WorldManager worldManager;

    public EntityFactory(EntityManager entityManager, StageManager stageManager) {
        this.entityManager = entityManager;
        this.stageManager = stageManager;
        this.worldManager = stageManager.getWorldManager();
    }

}
