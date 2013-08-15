package sg.atom.gameplay;

/**
 *
 * @author hungcuong
 */
public abstract class GameItem {

    long id;
    String name;
    String type;

    public GameItem(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public abstract boolean isHoldable(Class who);
}
