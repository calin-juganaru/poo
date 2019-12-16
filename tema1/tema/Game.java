package tema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Game {
	private int playersNr;

	private void ranking(final BasicPlayer[] players,
						 final ArrayList<String> playerNames) {
		for (int playerID = 0; playerID < playersNr; ++playerID) {
			for (int assetID = Utilities.MAX_LEGAL_ASSET_ID;
					 assetID < Utilities.MAX_ASSET_ID; ++assetID) {
				int asset = Utilities.ID_TO_CARD[assetID].getBonusID();
				int count = players[playerID].getAssetsOnMerchantStand()[assetID];
				count *= Utilities.ID_TO_CARD[assetID].getBonusCount();

				players[playerID].setAssetsOnMerchantStand(asset, count);
			}
		}

		for (int assetID = 0; assetID < Utilities.MAX_LEGAL_ASSET_ID; ++assetID) {
			List<Pair<Integer, Integer>> rank =
				new ArrayList<Pair<Integer, Integer>>(Utilities.MAX_LEGAL_ASSET_ID);

			for (int playerID = 0, assetCount; playerID < playersNr; ++playerID) {
				assetCount = players[playerID].getAssetsOnMerchantStand()[assetID];
				rank.add(0, new Pair<Integer, Integer>(playerID, assetCount));
			}

			Collections.sort(rank, new PlayersComparator());

			Pair<Integer, Integer> king = rank.get(0), queen, nothing;
			int kingsBonus = Utilities.KINGS_BONUS[assetID];
			int queensBonus = Utilities.QUEENS_BONUS[assetID];

			players[king.getFirst()].setCoins(kingsBonus);

			for (int i = 1; i < playersNr; ++i) {
				queen = rank.get(i);
				if (queen.getSecond() == king.getSecond()) {
					players[queen.getFirst()].setCoins(kingsBonus);
				} else {
					players[queen.getFirst()].setCoins(queensBonus);

					while (++i < playersNr) {
						nothing = rank.get(i);
						if (queen.getSecond() == nothing.getSecond()) {
							players[nothing.getFirst()].setCoins(
									queensBonus);
						} else {
							break;
						}
					}
					break;
				}
			}
		}

		List<Pair<Integer, Integer>> rank =
			new ArrayList<Pair<Integer, Integer>>(Utilities.MAX_LEGAL_ASSET_ID);

		for (int playerID = 0; playerID < playersNr; ++playerID) {
			rank.add(playerID, new Pair<Integer, Integer>(
					 playerID, players[playerID].finalProfit()));
		}

		Collections.sort(rank, new PlayersComparator());

		for (Pair<Integer, Integer> it : rank) {
		    System.out.println(playerNames.get(it.getFirst()).toUpperCase()
		    					+ ": " + it.getSecond());
		}
	}

	public void playGame(final ArrayList<Integer> assetIDs,
						 final ArrayList<String> playerNames) {
		playersNr = playerNames.size();
		BasicPlayer[] players = new BasicPlayer[playersNr];

		int sheriff;
		int nextCardIndex = 0;

		for (int i = 0; i < playersNr; ++i) {
			String name = playerNames.get(i);
			switch (name) {
			case "basic":
				players[i] = new BasicPlayer();
				break;
			case "greedy":
				players[i] = new GreedyPlayer();
				break;
			case "bribed":
				players[i] = new BribedPlayer();
				break;
			case "wizard":
				players[i] = new WizardPlayer();
			default:
				break;
			}
		}

		for (int round = 1; round <= 2 * playersNr; ++round) {
			sheriff = (round - 1) % playersNr;

			for (int i = 0; i < playersNr; ++i) {
				int cardsCount = Utilities.MAX_CARDS_IN_HAND
							   - players[i].getNrAssetsInHand();
				players[i].setAssetsInHand(assetIDs, nextCardIndex,
									nextCardIndex + cardsCount);
				nextCardIndex += cardsCount;

				if (i != sheriff) {
					players[i].playMerchant();
				}
			}

			players[sheriff].playSheriff(players, sheriff);
		}

		ranking(players, playerNames);
	}
}
