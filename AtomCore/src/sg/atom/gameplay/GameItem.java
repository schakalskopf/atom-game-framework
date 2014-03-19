package sg.atom.gameplay;

import java.lang.ref.SoftReference;
import sg.atom.entity.Entity;
import sg.atom.stage.actor.AtomActor;

@Deprecated
/**
 * Represent Slot to hold other Entity (GameObject), the same as Component Slot
 * of Component Entity Framework.
 *
 * <p>In abstract level, it represent a single soft link (reference) to another
 * object in the same context. In this implementation, GameItem indicate a soft
 * link from an Actor to an Entity(since Actor is not an Entity by default!).
 *
 * <p>Three level of nested object <ul>
 *
 * <li>Actor has Items (slots)</li>
 *
 * <li>Item contain Entity (slots)</li>
 *
 * <li>Entity has Components (slots) </li> </ul>
 *
 * <p>Concepts borrowed from ES and Arrianne. <p>FIXME: Replace with ES!
 *
 * @author atomix
 */
public abstract class GameItem {

    public long id;
    public String name;
    public String type;
    public Entity entity;
    public SoftReference<GameCharacter> owner;

    public GameItem(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public abstract boolean isHoldable(Class who);
}
