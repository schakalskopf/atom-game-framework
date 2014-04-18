package sg.atom.utils.proxy.presentation.pface;

import java.lang.reflect.Method;
import java.util.Arrays;

import sg.atom.utils.proxy.presentation.pface.internal.*;

/**
 * factory method used to create {@link Matcher} instances which match
 * {@link Method}s
 */
public enum MethodMatchers {

    ;// uninstantiable

	/**
	 * convenience method for finding a similar (matching) method in the given list.
	 */
	public static Method match(final Method matchMe, final Iterable<Method> methods) {
        final Matcher<Method> matcher = MethodMatchers.newMatcher();
        return match(matcher, matchMe, methods);
    }

    /**
     * convenience method for matching a method
     */
    private static Method match(final Matcher<Method> matcher, final Method matchMe, final Iterable<Method> methods) {
        for (final Method other : methods) {
            if (matcher.matches(matchMe, other)) {
                return other;
            }
        }
        return null;
    }

    /**
     * @return a new method matcher
     */
    @SuppressWarnings("unchecked")
    static Matcher<Method> newMatcher() {
        return Matchers.and(newNameMatcher(), newTypeMatcher(), newReturnTypeMatcher());
    }

    public static Matcher<Method> newNameMatcher() {
        return new Matcher<Method>() {
            public boolean matches(final Method thingOne, final Method thingTwo) {
                final boolean matches = thingOne.getName().equals(thingTwo.getName());
                return matches;
            }
        };
    }

    public static Matcher<Method> newTypeMatcher() {
        return new Matcher<Method>() {
            public boolean matches(final Method thingOne, final Method thingTwo) {
                final Class<?>[] typesOne = thingOne.getParameterTypes();
                final Class<?>[] typesTwo = thingTwo.getParameterTypes();
                final boolean matches = Arrays.equals(typesOne, typesTwo);
                return matches;
            }
        };
    }

    public static Matcher<Method> newReturnTypeMatcher() {
        return new Matcher<Method>() {
            public boolean matches(final Method thingOne, final Method thingTwo) {
                final Class<?> typeOne = thingOne.getReturnType();
                final Class<?> typeTwo = thingTwo.getReturnType();
                if (typeOne == null) {
                    return typeTwo == null;
                } else if (typeTwo == null) {
                    return false;
                }

                return typeOne.equals(typeTwo);
            }
        };
    }

    /**
     * match the given method on the implementation
     *
     * @param matchMe
     * @param implementation
     * @return
     */
    public static Method matchMethod(final Method matchMe, final Object implementation) {
        Iterable<Method> methods = Arrays.asList(implementation.getClass().getMethods());
        Method match = match(matchMe, methods);
        if (match == null) {
            methods = Arrays.asList(implementation.getClass().getDeclaredMethods());
            match = match(matchMe, methods);
        }
        return match;
    }
}
