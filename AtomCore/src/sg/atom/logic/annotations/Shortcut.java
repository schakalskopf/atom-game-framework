
package sg.atom.logic.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Key binding, may include up to 7 keys (not that you'd want that many).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Shortcut {

    /**
     * Each value maps to the field names as defined in {@link Keys}.
     *
     * @return Ze shortcut keys.
     */
    int[] value();
}
