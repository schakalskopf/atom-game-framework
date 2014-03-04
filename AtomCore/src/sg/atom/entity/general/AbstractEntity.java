/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.general;

/**
 * Simple and fake Entity with Java type and all the inheriatance fails!.
 *
 * <p><b>NOTE: This is not the Entity as seen in a "Pure" EntityComponent
 * framework!</b></p>
 *
 * <p>Entity hold (contain of link to) associcated its Components and Functions.
 * It also act like a Bean, exposed its Property</p>
 *
 * <p>Atom's Entity framework have no contract about the enviroment of Entities
 * and Components. The method ask for identify of the Entity is "misleaded"! It
 * should be in an "Enviroment" implementation who take carefully index the
 * Entity as well as store them in some manner.</p>
 *
 * <p>Note that Atom Entity replicate Object oriented programming and involve
 * Data oriented programming at the same time. Also check Aspect programming and
 * reactive functional programming!</p>
 *
 * <p><b>NOTE:</b> Here in the scope of this "first citizen interface", we only
 * see three things available: Object, Interface and Class. That's a most level
 * of abstraction we got in Java language to capture Component Oriented
 * Programming. We remove some concepts: primitive, methods,..etc. Class who
 * implements this interface "should" only have Component and its Identify in
 * the context. Its internal exposed via Class based query with single
 * parameter, this permit absent of tranditional accessor of Java language such
 * as property or methods. Of course it come with caveat as mentioned in the
 * topic.</p>
 *
 * Read: http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * FIXME: Use Real ES!!!
 *
 * @author atomix
 */
public interface AbstractEntity {

    /**
     * Get a meaningful indetify in some embeded context.
     *
     * The implementation has to be taking care of its conrespondent context and
     * meaning.
     *
     * @return
     */
    public Object getIdentify();

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
