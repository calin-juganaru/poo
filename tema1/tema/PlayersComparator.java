package tema;

import java.util.Comparator;

public final class PlayersComparator implements Comparator<Pair<Integer, Integer>> {
	@Override
	public int compare(final Pair<Integer, Integer> x,
					   final Pair<Integer, Integer> y) {
		return y.getSecond() - x.getSecond();
	}
}
