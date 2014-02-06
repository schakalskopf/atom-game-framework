package mulova.common;

import java.util.EnumSet;

public class EnumUtil {

	/**
	 * error는 없지만 효율이 낮다. Enum.valueOf를 사용하라.
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> cls, final String name) {
		EnumSet<T> set = EnumSet.allOf(cls);
		for (final T e : set) {
			if (e.name().equals(name))
				return e;
		}
		return null;
	}
	
	/**
	 * ToString() 을 사용해서 Enum entry를 찾는다.
	 * @param name
	 * @return
	 */
	public static <T extends Enum<T>> T valueOfToString(Class<T> cls, final String name) {
		EnumSet<T> set = EnumSet.allOf(cls);
		for (final T e : set) {
			if (e.toString().equals(name))
				return e;
		}
		return null;
	}
	
	public static <T extends Enum<T>> T guess(Class<T> cls, final String name) {
		for (final T e : EnumSet.allOf(cls)) {
			if (name.indexOf(e.name()) >= 0)
				return e;
		}
		for (final T e : EnumSet.allOf(cls)) {
			if (name.indexOf(e.name().toLowerCase()) >= 0)
				return e;
		}
		return null;
	}
}
