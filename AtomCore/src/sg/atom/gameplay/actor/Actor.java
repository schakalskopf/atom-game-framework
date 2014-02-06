package sg.atom.gameplay.actor;

import sg.atom.gameplay.GameAction;

/**
 * Actor is a "term" for special kind of "Managed Game Object" beside of Entity.
 * <br>
 *
 * <p>Actor has Identification and actions. And should be managed within an
 * Excutive context by Excecutor, in contrast with Entity in Data like Context
 * with EntityManager </p>
 *
 * <p>Designed to work within Actor framework</p>
 *
 */
public abstract class Actor {
    //FIXME: Remove the dependency on the GameLoop!
    //public abstract void simpleUpdate(float tpf);

    public abstract int getId();

    public abstract void doAction(GameAction a);
}