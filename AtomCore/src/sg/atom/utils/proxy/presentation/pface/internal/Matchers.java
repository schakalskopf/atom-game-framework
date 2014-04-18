package sg.atom.utils.proxy.presentation.pface.internal;

/**
 * Factory class for {@link Matcher}s
 */
public enum Matchers {

    ;// unimplemented

	/**
	 * 'and' two or more matchers together
	 * 
	 * @param one
	 * @param other
	 * @param more
	 * @param <T>
	 * @return a new matcher with the given matchers ANDed together
	 */
	public static <T> Matcher<T> and(final Matcher<T> one, final Matcher<T> other, final Matcher<T>... more) {
        return new Matcher<T>() {
            public boolean matches(final T thingOne, final T thingTwo) {
                final boolean matches = one.matches(thingOne, thingTwo) && other.matches(thingOne, thingTwo);
                if (matches) {
                    for (final Matcher<T> m : more) {
                        if (!m.matches(thingOne, thingTwo)) {
                            return false;
                        }
                    }
                }
                return matches;
            }
        };
    }
}
