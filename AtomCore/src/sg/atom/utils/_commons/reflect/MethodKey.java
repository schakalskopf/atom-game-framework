/**
 *
 */
package sg.atom.utils._commons.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * offers control over how we map {@link Method} objects
 */
public class MethodKey {
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.method.getName().hashCode();
        for (final Class<?> type : this.method.getParameterTypes()) {
            result = prime * result + type.hashCode();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MethodKey other = (MethodKey) obj;
        if (this.method == null) {
            if (other.method != null) {
                return false;
            }
        } else if (!this.method.getName().equals(other.method.getName())) {
            return false;
        }

        final TypeVariable<Method>[] theseTypes = this.method.getTypeParameters();
        final TypeVariable<Method>[] otherTypes = other.method.getTypeParameters();
        if (!Arrays.equals(theseTypes, otherTypes)) {
            return false;
        }
        return true;
    }
    private final Method method;

    public MethodKey(final Method m) {
        this.method = m;
        if (this.method == null) {
            throw new NullPointerException();
        }
    }
}