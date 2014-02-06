package sg.atom.utils.pool;

class ArrayPoolKey {
	int limit;
	int capacity;
	
	public ArrayPoolKey(int limit, int capacity) {
		this.limit = limit;
		this.capacity = capacity;
	}
	
	@Override
	public int hashCode() {
		return capacity;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		ArrayPoolKey that = (ArrayPoolKey) obj;
		return this.capacity == that.capacity;
	}

	@Override
	public String toString() {
		return "[limit]"+limit+" [capacity]"+capacity;
	}
	
	
}
