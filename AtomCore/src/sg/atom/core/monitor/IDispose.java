package sg.atom.core.monitor;

/**
 * Resource that owns the object must implement the interface<br>
 *
 * @author mulova
 */
public interface IDispose {

    /**
     * When the object is no longer used is called. <br> Reentrant should
     * be.<br>
     */
    public void dispose();
}
