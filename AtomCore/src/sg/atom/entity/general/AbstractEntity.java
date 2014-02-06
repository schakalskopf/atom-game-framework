/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.entity.general;

/**
 * Simple and fake Entity (GameObject) with Java type and all the inheriatance
 * fails!.
 *
 * <p><b>NOTE: This is not the Entity as seen in a "Pure" EntityComponent
 * framework!</b></p>
 *
 * <p>Entity hold (contain of link to) associcated its Components and Functions.
 * It also act like a Bean, exposed its Property</p>
 *
 * <p>Atom's Entity framework have no contract about the enviroment of Entities
 * and Components.</p>
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
public interface AbstractEntity {

    public Long getId();

    public <T extends AbstractComponent> T get(Class<T> type);

    public void set(AbstractComponent c);

    public AbstractComponent[] getComponents();
}
