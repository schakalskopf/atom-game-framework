/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity;

import sg.atom.entity.general.AbstractComponent;
import sg.atom.entity.general.AbstractEntity;

/**
 * Simple and fake Entity (GameObject) with Java type and all the inheriatance
 * fails!.
 *
 * <p><b>NOTE: This is not the Entity as seen in a EntityComponent
 * framework!</b></p>
 *
 * <p>Entity hold (contain of link to) associcated its Components and Functions.
 * It also act like a Bean, exposed its Property. So via Entity you can "lookup"
 * its associated Component.</p>
 *
 * <p>This implementation only Support minimal management by:
 * id,name,type,group,status. With entities's type ( a String) as primary lookup method.
 * Already enough for common usecases. </p>
 *
 * <p>This implementation have no contract about the enviroment of Entities,
 * also the purity of the Entity. To be managed as Actor in a Actor framework
 * for high concurent performance, or Entity in a context/enviroment such as
 * World in Artemis; possible extends this class!</p>
 *
 * <p>Note that Atom Entity replicate Object oriented programming and involve
 * Data oriented programming at the same time. Also check Aspect programming and
 * reactive functional programming!</p>
 *
 * Read: http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * FIXME: Use Real ES!!!
 *
 * @author atomix
 */
public class Entity implements AbstractEntity {

    public Long id;
    // Basic properties. FIXME: Remove them!
    protected String entityName;
    protected String entityType;
    protected String entityGroup = "";
    public boolean isActived = true;

    // Components and Functions
    public Entity(Long id, String name, String type) {
        this.id = id;
        this.entityName = name;
        this.entityType = type;
        /*
         this.isActived = true;
         */
    }

    public Entity(Long id, String type) {
        this(id, "", type);
    }
    /* For simple management */

    public String getGroup() {
        return this.entityGroup;
    }

    public void setGroup(String group) {
        this.entityGroup = group;
    }

    public String getName() {
        return entityName;
    }

    @Override
    public Object getIdentify() {
        return getId();
    }
    
    public Long getId() {
        return id;
    }

    public String getType() {
        return entityType;
    }

    /*Components and Functions */
    public Object getComponent(Object type) {
        return null;
    }

    public Object getProperty(String propertyName) {
        return null;
    }

    @Override
    public <T extends AbstractComponent> T get(Class<T> type) {
        return null;
    }

    @Override
    public void set(AbstractComponent c) {
    }

    /**
     * Provide "read only" view of its internal component.
     * @return 
     */
    @Override
    public AbstractComponent[] getComponents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
