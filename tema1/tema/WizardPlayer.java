package tema;

public final class WizardPlayer extends BasicPlayer {
	protected WizardPlayer() {
		super();
	}

	@Override
	protected void playMerchant() {
		super.playMerchant();

		for (int assetID = Utilities.MAX_LEGAL_ASSET_ID;
				assetID < Utilities.MAX_ASSET_ID; ++assetID) {
			if (this.getAssetsInBag()[assetID] > 0) {
				this.setBribe(Utilities.BRIBE_5);
				return;
			}
		}
	}

	@Override
	protected void playSheriff(final BasicPlayer[] players, final int sheriff) {
		int playerID = (sheriff + 1) % 2;
		BasicPlayer merchant = players[playerID];

		if (merchant.getDeclaredType() == 0
				|| merchant.getNrAssetsInBag() != 2) {
			super.playBasicSheriff(merchant);
		} else {
			int bribe = merchant.getBribe();
			merchant.verifyBag(true);
			merchant.setBribe(0);
			merchant.setCoins(-bribe);
			this.setCoins(bribe);
		}
	}
}
