package sg.atom.utils.repository.cache;

import java.util.List;
import sg.atom.utils.repository.pojomvcc.RefreshOptions;

/**
 * A {@link RevisionObjectCache} is a modifiable view (or "working copy") of a
 * {@link RootObjectCache}. All additions/modifications/removals for
 * {@code sg.atom.utils.repository.pojomvcc.CacheElement}s are performed against
 * an instance of this class.
 * <p/>
 * To persist the changes back into the {@link RootObjectCache} the
 * {@link RootObjectCache#commit(RevisionObjectCache)} operation should be
 * invoked.
 *
 * @author Aidan Morgan
 */
public interface RevisionObjectCache<K, V> extends ReadOnlyRevisionObjectCache<K, V> {

    /**
     * Adds the provided {@code sg.atom.utils.repository.pojomvcc.CacheElement}
     * with the provided {@code sg.atom.utils.repository.pojomvcc.CacheKey} to
     * this {@code sg.atom.utils.repository.pojomvcc.ObjectCache}.
     *
     * @param key
     * @param object
     */
    public void addElement(K key, V object);

    /**
     * Removes the {@code sg.atom.utils.repository.pojomvcc.CacheElement}
     * currently registered with the provided
     * {@code sg.atom.utils.repository.pojomvcc.CacheKey} from this
     * {@link ObjectCache}.
     *
     * @param key
     */
    public void removeElement(K key);

    /**
     * Returns a {@code List} of {@link K}s for all
     * {@code sg.atom.utils.repository.pojomvcc.CacheElement}s that were added
     * to this {@link RevisionObjectCache}.
     *
     * @return
     */
    public List<K> getAddedElements();

    /**
     * Returns a {@code List} of {@link K}s for all
     * {@code sg.atom.utils.repository.pojomvcc.CacheElement}s that were
     * modified in this {@link RevisionObjectCache}.
     *
     * @return
     */
    public List<K> getModifiedElements();

    /**
     * Returns a {@code List} of {@link K}s for all
     * {@code sg.atom.utils.repository.pojomvcc.CacheElement}s that were removed
     * from this {@link RevisionObjectCache}.
     *
     * @return
     */
    public List<K> getRemovedElements();

    /**
     * Updates this {@link RevisionObjectCache} with the latest revision of the
     * {@code sg.atom.utils.repository.pojomvcc.RootObjectCache} automatically.
     * The provided {@link RefreshOptions} dictates what should happen when
     * conflicts between this
     * {@code sg.atom.utils.repository.pojomvcc.RevisionObjectCache} and the
     * {@code sg.atom.utils.repository.pojomvcc.RootObjectCache} occur.
     *
     * @param options
     */
    public void update(RefreshOptions options);

    /**
     * Removes all changes to this {@link RevisionObjectCache} and reverts back
     * to the revision in the {@link RootObjectCache}.
     */
    public void revert();

    /**
     * Closes and releases all resources used by this
     * {@link RevisionObjectCache}.
     * <p/>
     * Implementations need to ensure that the
     * {@link RootObjectCache#close(RevisionObjectCache)} method is called as
     * part of the implementation to ensure that resources are removed from the
     * {@link RootObjectCache}.
     */
    public void close();

    /**
     * Returns the {@code java.util.List} of {@link K} that are in this
     * {@link sg.atom.utils.repository.pojomvcc.RevisionObjectCache}.
     *
     * This method can be very slow to operate, so it should not be called
     * often.
     *
     * @return
     */
    public List<K> getKeys();
}
