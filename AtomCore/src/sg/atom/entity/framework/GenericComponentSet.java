/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.framework;

import sg.atom.entity.general.AbstractComponent;

/**
 * Generic Holder for Component from different Component framework (Zay-ES,
 * Artemis..etc)
 *
 * Read: http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * @author atomix
 */
@Deprecated
public interface GenericComponentSet {

    /**
     * Get a "unique" Component of a specific Class.
     *
     * The Component should be valid in this Entity scope at the moment of
     * return, that mean if the Entity context contract to be an consitent view
     * of all Entities, so do the components.
     *
     * @param <T>
     * @param type
     * @return
     */
    public <T extends AbstractComponent> T get(Class<T> type);

    /**
     * The a "unique" Component of a specific Class.
     *
     * The Component should be valid in this Entity scope at the moment of
     * return, that mean if the Entity context contract to be an consitent view
     * of all Entities, so do the components.
     *
     * @param c
     */
    public void set(AbstractComponent c);

    /**
     * Capture a view of all components that this entity associated with.
     *
     * @return
     */
    public AbstractComponent[] getComponents();
}
