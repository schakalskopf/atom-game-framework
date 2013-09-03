package sg.atom.logic.trigger;

/**
 *
 * @author hungcuong
 */
public interface Trigger {
    public abstract void actived();
    public abstract void deactived();
    public abstract void setEnable(boolean enable);
    
}
