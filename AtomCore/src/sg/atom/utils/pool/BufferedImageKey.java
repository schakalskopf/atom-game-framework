package sg.atom.utils.pool;

import java.util.*;

class BufferedImageKey {
	final int width;
	final int height;
	final boolean alpha;
	final int hash;

	BufferedImageKey(int width, int height, boolean alpha) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		hash = Arrays.hashCode(new Object[] {width, height, alpha});
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public String toString() {
		return width+"x"+height+(alpha?"alpha":"");
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		BufferedImageKey that = (BufferedImageKey)obj;
		return this.width==that.width && this.height==that.height && this.alpha==that.alpha;
	}

}