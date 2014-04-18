package sg.atom.utils.proxy.presentation.pface.internal;

/**
 * Type which can match two things of the same type
 *
 * @param <T> the type to match
 */
public interface Matcher<T> {

    /**
     * @param thingOne the first object to match against the second object
     * @param thingTwo the object to match against the first
     * @return true if both objects 'match'
     */
    public boolean matches(T thingOne, T thingTwo);
}
