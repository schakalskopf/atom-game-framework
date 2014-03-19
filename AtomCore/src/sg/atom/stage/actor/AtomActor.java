package sg.atom.stage.actor;

import sg.atom.gameplay.GameAction;
import groovyx.gpars.actor.Actor;

/**
 * Actor is a "term" for special kind of "Managed Game Object" beside of Entity.
 * This class is the main entrance of Actor model in Atom framework.
 *
 * <p>Actor has Identification and actions. And should be managed within an
 * Excutive context by Excecutor,
 *
 * <p>in contrast with Entity in Data like Context with EntityManager. In pure
 * ES term (with no System deffinition), Actor are a special kind of System who
 * has operations over Entities. By contract with the underlying dataflow and
 * the atomicity level, the consistent of both AtomActor and EntityRepository
 * are waranty.
 *
 * <p>So compare to pure ES, Actor framework: <ul>
 *
 * <li>also success in <b>Decoupling, Reusuable, Primitive unit, Repository
 * existence, Managable workload, Managable dataflow </b>
 *
 * <li>but not <b>Everything is Data, Homogeneous </b> <b>Note: Data driven is
 * not in this layer but supported in Bean layer with Modelling, Binding,
 * Mapping ... of Atom fw.</b>
 *
 * <li>but include <b>Messaging with ease, Real OOP</b></ul>
 *
 * <p><b> This is a "half-ass" implementation but in acording to the philosopy
 * of Atom fw "best of both world" is a design choice! ... and after all, it
 * work magically like Component Oriented programming!</b>
 *
 * <p>Read the ES topic in JME forum :
 * http://hub.jmonkeyengine.org/forum/topic/entity-system-topic-united/
 *
 * <p>Designed to work within Actor framework. FIXME: Move Actor to AKKA.
 *
 * <p>Read: http://en.wikipedia.org/wiki/Actor_model
 *
 * http://sujitpal.blogspot.com/2009/01/more-java-actor-frameworks-compared.html
 * http://akka.io/ </p>
 *
 */
public abstract class AtomActor {
    //FIXME: Remove the dependency on the GameLoop!
    //public abstract void simpleUpdate(float tpf);

    public abstract int getId();

    public abstract void doAction(GameAction a);

    public groovyx.gpars.actor.Actor warpGparsActor() {
        return null;
    }
}