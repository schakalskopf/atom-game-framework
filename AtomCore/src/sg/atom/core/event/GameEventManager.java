package sg.atom.core.event;

import java.util.ArrayList;

/**
 *
@author atomix
 */
/**
 * GameEventManager. <p>This class is a general Game event manager, which take
 * care of event managing and direct the event dispatching when needed. It also
 * use small pieces of EventInfo to map Event to related objects.</p>
 *
 * <p>Trying to get the "best of both worlds" into the game engine, that should
 * be maximize the ease of use, clear design and performance wise.I used 3
 * design pattern:
 *
 * <ul> <li> EventBus </li>
 *
 * <li> Minimum coupling</li>
 *
 * <li>2 ways dispatching</li></ul>
 *
 * </p>
 *
 * <p> More over the GameEventManager can also be change it behaviors and
 * activites ex:muted and adapt to up-date-per-frame alike mechanisms if needed.
 */
@Deprecated
public class GameEventManager {

    ArrayList<GameEvent> queue;

    public GameEventManager() {
        queue = new ArrayList<GameEvent>();

    }
}
