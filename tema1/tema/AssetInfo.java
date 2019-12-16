package tema;

public final class AssetInfo {
	private String asset;
	private int profit;
	private int penalty;
	private int bonusID;
	private int bonusCount;

	protected AssetInfo(final String asset, final int profit, final int penalty,
					 final int bonusID, final int bonusCount) {
		this.asset = new String(asset);
		this.profit = profit;
		this.penalty = penalty;
		this.bonusID = bonusID;
		this.bonusCount = bonusCount;
	}

	protected int getProfit() {
		return profit;
	}

	protected String getAsset() {
		return asset;
	}

	protected int getPenalty() {
		return penalty;
	}

	protected int getBonusID() {
		return bonusID;
	}

	protected int getBonusCount() {
		return bonusCount;
	}
}
