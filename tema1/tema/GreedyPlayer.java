package tema;

public final class GreedyPlayer extends BasicPlayer {
	private int round;

	protected GreedyPlayer() {
		super();
		round = 0;
	}

	protected int getRound() {
		return round;
	}

	protected void setRound() {
		this.round++;
	}

	@Override
	protected void playMerchant() {
		this.setRound();
		super.playMerchant();

		if (this.getRound() % 2 == 0 && this.getNrAssetsInHand() > 1) {
			int bestIllegalAssetId = this.bestIllegalAsset();
			if (bestIllegalAssetId < Utilities.MAX_ASSET_ID) {
				this.chooseAsset(bestIllegalAssetId, 1);
			}
		}
	}

	@Override
	protected void playSheriff(final BasicPlayer[] players, final int sheriff) {
		for (int playerID = 0; playerID < players.length; ++playerID) {
			if (playerID != sheriff) {
				int bribe = players[playerID].getBribe();
				if (bribe == 0) {
					super.playBasicSheriff(players[playerID]);
				} else {
					players[playerID].verifyBag(true);
					players[playerID].setBribe(0);
					players[playerID].setCoins(-bribe);
					this.setCoins(bribe);
				}
			}
		}
	}
}
