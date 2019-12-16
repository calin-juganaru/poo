package tema;

public final class BribedPlayer extends BasicPlayer {

	protected BribedPlayer() {
		super();
	}

	@Override
	protected void playMerchant() {
		int bestAssetID = this.bestIllegalAsset();
		if (bestAssetID == Utilities.MAX_ASSET_ID
				|| this.getCoins() < Utilities.BRIBE_5) {
			super.playMerchant();
			return;
		}

		this.chooseAsset(bestAssetID, 1);
		this.setBribe(Utilities.BRIBE_5);
		this.setDeclaredType(0);

		bestAssetID = this.bestIllegalAsset();
		if (bestAssetID < Utilities.MAX_ASSET_ID) {
			this.chooseAsset(bestAssetID, 1);
		} else {
			return;
		}

		bestAssetID = this.bestIllegalAsset();
		if (bestAssetID < Utilities.MAX_ASSET_ID
				&& this.getCoins() >= Utilities.BRIBE_10) {
			this.chooseAsset(bestAssetID, 1);
			this.setBribe(Utilities.BRIBE_10);
		} else {
			return;
		}

		for (int i = 0; i < 2; ++i) {
			bestAssetID = this.bestIllegalAsset();
			if (bestAssetID < Utilities.MAX_ASSET_ID) {
				this.chooseAsset(bestAssetID, 1);
			} else {
				return;
			}
		}
	}

	@Override
	protected void playSheriff(final BasicPlayer[] players, final int sheriff) {
		if (players.length == 2) {
			int playerID = (sheriff + 1) % 2;
			super.playBasicSheriff(players[playerID]);
		} else if (players.length == Utilities.MAX_PLAYERS) {

			int playerID = (sheriff + 2) % players.length;
			super.playBasicSheriff(players[playerID]);

			playerID = (sheriff + 1) % players.length;
			super.playBasicSheriff(players[playerID]);
		} else {
			return;
		}
	}
}
