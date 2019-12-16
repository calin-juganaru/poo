package tema;

import java.util.ArrayList;
import java.util.List;

public class BasicPlayer {
	private int[] assetsInHand;
	private int[] assetsOnMerchantStand;
	private int[] assetsInBag;
	private ArrayList<Integer> assetsInOrder;
	private int declaredType;
	private int nrAssetsInHand;
	private int coins;
	private int bribe;

	protected BasicPlayer() {
		this.assetsInHand = new int[Utilities.MAX_ASSET_ID];
		this.assetsOnMerchantStand = new int[Utilities.MAX_ASSET_ID];
		this.assetsInBag = new int[Utilities.MAX_ASSET_ID];
		this.assetsInOrder =
			new ArrayList<Integer>(Utilities.MAX_CARDS_IN_HAND);
		this.coins = Utilities.INITIAL_COINS;
		this.bribe = 0;
		this.nrAssetsInHand = 0;
	}

	protected final int[] getAssetsInHand() {
		return assetsInHand;
	}

	private void setAssetsInHand(final int assetID, int count) {
		this.assetsInHand[assetID] += count;
		this.nrAssetsInHand += count;

		if (count > 0) {
			while (count-- > 0) {
				this.assetsInOrder.add(assetID);
			}
		} else {
			for (int i = 0; i < this.assetsInOrder.size() && count < 0; ++i) {
				if (this.assetsInOrder.get(i) == assetID) {
					this.assetsInOrder.remove(i);
					++count; --i;
				}
			}
		}
	}

	protected final void setAssetsInHand(final List<Integer> assets,
								final int beginIndex,
								final int endIndex) {
		for (int i = beginIndex; i < endIndex; ++i) {
			this.setAssetsInHand(assets.get(i), 1);
		}
	}

	protected final int getNrAssetsInHand() {
		return nrAssetsInHand;
	}

	protected final int[] getAssetsOnMerchantStand() {
		return assetsOnMerchantStand;
	}

	protected final void setAssetsOnMerchantStand(final int assetID, final int count) {
		this.assetsInBag[assetID] -= count;
		this.assetsOnMerchantStand[assetID] += count;
	}

	protected final int getDeclaredType() {
		return declaredType;
	}

	protected final void setDeclaredType(final int declaredType) {
		this.declaredType = declaredType;
	}

	protected final int getCoins() {
		return coins;
	}

	protected final void setCoins(final int coins) {
		this.coins += coins;
	}

	protected final int getBribe() {
		return bribe;
	}

	protected final void setBribe(final int bribe) {
		this.bribe = bribe;
	}

	protected final int[] getAssetsInBag() {
		return assetsInBag;
	}

	protected final int getNrAssetsInBag() {
		int count = 0;
		for (int assetID = 0; assetID < Utilities.MAX_ASSET_ID; ++assetID) {
			count += this.assetsInBag[assetID];
		}
		return count;
	}

	private boolean hasOnlyIllegals() {
		for (int i = 0; i < Utilities.MAX_LEGAL_ASSET_ID; ++i) {
			if (assetsInHand[i] > 0) {
				return false;
			}
		}
		return true;
	}

	private boolean breadFirst() {
		for (Integer it : this.assetsInOrder) {
			if (it == Utilities.BREAD_ID) {
				return true;
			} else if (it == Utilities.CHICKEN_ID) {
				return false;
			} else {
				continue;
			}
		}
		return false;
	}

	protected final int bestIllegalAsset() {
		int currentProfit;
		int bestProfit = 0;
		int bestAssetID = Utilities.MAX_ASSET_ID;

		for (int i = Utilities.MAX_LEGAL_ASSET_ID; i < Utilities.MAX_ASSET_ID; ++i) {
			if (assetsInHand[i] > 0) {
				currentProfit = Utilities.ID_TO_CARD[i].getProfit();
				if (currentProfit > bestProfit) {
					bestProfit = currentProfit;
					bestAssetID = i;
				}
			}
		}

		return bestAssetID;
	}

	private int bestLegalAsset() {
		int currentProfit;
		int bestProfit = 0;
		int bestAssetID = 0;
		int maxFrequency = 0;

		for (int i = 0; i < Utilities.MAX_LEGAL_ASSET_ID; ++i) {
			if (assetsInHand[i] > maxFrequency) {
				maxFrequency = assetsInHand[i];
			}
		}

		for (int i = 0; i < Utilities.MAX_LEGAL_ASSET_ID; ++i) {
			currentProfit = Utilities.ID_TO_CARD[i].getProfit();
			if (assetsInHand[i] == maxFrequency && bestProfit < currentProfit) {
				bestProfit = currentProfit;
				bestAssetID = i;
			}
		}

		if (assetsInHand[Utilities.BREAD_ID] == assetsInHand[Utilities.CHICKEN_ID]
			&& bestProfit == Utilities.MAX_LEGAL_ASSET_ID) {
			if (breadFirst()) {
				bestAssetID = Utilities.BREAD_ID;
			} else {
				bestAssetID = Utilities.CHICKEN_ID;
			}
		}

		return bestAssetID;
	}

	protected final void chooseAsset(final int assetID, int count) {
		this.setAssetsInHand(assetID, -count);
		this.assetsInBag[assetID] += count;
		for (int i = 0; i < this.assetsInOrder.size() && count > 0; ++i) {
			if (this.assetsInOrder.get(i) == assetID) {
				this.assetsInOrder.remove(i);
				--count; --i;
			}
		}
	}

	protected final void confiscateAsset(final int assetID, final int count) {
		this.assetsInBag[assetID] -= count;
	}

	protected final void acceptAsset(final int assetID, final int count) {
		this.setAssetsOnMerchantStand(assetID, count);
	}

	protected final int inspectBag() {
		int penalty = 0;
		for (int i = Utilities.MAX_LEGAL_ASSET_ID; i < Utilities.MAX_ASSET_ID; ++i) {
			if (this.assetsInBag[i] > 0) {
				penalty -= (this.assetsInBag[i]
						 * Utilities.ID_TO_CARD[i].getPenalty());
				this.confiscateAsset(i, this.assetsInBag[i]);
			}
		}

		for (int i = 0; i < Utilities.MAX_LEGAL_ASSET_ID; ++i) {
			if (this.assetsInBag[i] > 0 && i != this.declaredType) {
				penalty -= (this.assetsInBag[i]
						 * Utilities.ID_TO_CARD[i].getPenalty());
				this.confiscateAsset(i, this.assetsInBag[i]);
			}
		}

		if (penalty == 0) {
			penalty = this.verifyBag(false);
		} else {
			this.verifyBag(false);
		}

		return penalty;
	}

	protected final int verifyBag(final boolean bribed) {
		int penalty = 0;
		for (int assetID = 0; assetID < Utilities.MAX_ASSET_ID; ++assetID) {
			if (!bribed) {
				penalty += (this.assetsInBag[assetID]
						 * Utilities.ID_TO_CARD[assetID].getPenalty());
			}
			this.acceptAsset(assetID, this.assetsInBag[assetID]);
		}
		return penalty;
	}

	protected final int finalProfit() {
		int profit = this.coins;

		for (int assetID = 0; assetID < Utilities.MAX_ASSET_ID; ++assetID) {
			profit += (Utilities.ID_TO_CARD[assetID].getProfit()
					* this.assetsOnMerchantStand[assetID]);
		}

		return profit;
	}

	protected void playMerchant() {
		int bestAssetID;
		int assetsCount;

		if (this.hasOnlyIllegals()) {
			bestAssetID = this.bestIllegalAsset();
			assetsCount = 1;

			this.chooseAsset(bestAssetID, assetsCount);
			this.setDeclaredType(0);
			return;
		}

		bestAssetID = this.bestLegalAsset();
		assetsCount = this.getAssetsInHand()[bestAssetID];

		this.chooseAsset(bestAssetID, assetsCount);
		this.setDeclaredType(bestAssetID);
	}

	protected void playBasicSheriff(final BasicPlayer player) {
		int penalty = player.inspectBag();
		player.setCoins(penalty);
		this.setCoins(-penalty);
		player.setBribe(0);
	}

	protected void playSheriff(final BasicPlayer[] players, final int sheriff) {
		for (int playerID = 0; playerID < players.length; ++playerID) {
			if (playerID != sheriff) {
				this.playBasicSheriff(players[playerID]);
			}
		}
	}
}
