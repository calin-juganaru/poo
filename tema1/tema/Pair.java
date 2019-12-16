package tema;

public final class Pair<T1, T2> {
	private T1 first;
	private T2 second;

	public Pair(final T1 first, final T2 second) {
		this.first = first;
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}
}
