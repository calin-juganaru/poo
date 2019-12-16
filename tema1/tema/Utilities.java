package tema;

public final class Utilities {
	private Utilities() {
	}

	public static final AssetInfo[] ID_TO_CARD =
	    {
	        new AssetInfo("Apple", 2, 2, 0, 0),
	        new AssetInfo("Cheese", 3, 2, 0, 0),
	        new AssetInfo("Bread", 4, 2, 0, 0),
	        new AssetInfo("Chicken", 4, 2, 0, 0),
	        new AssetInfo("Silk", 9, 4, 1, 3),
	        new AssetInfo("Pepper", 8, 4, 3, 2),
	        new AssetInfo("Barrel", 7, 4, 2, 2)
	    };

	public static final int MAX_LEGAL_ASSET_ID = 4;
	public static final int MAX_ASSET_ID = 7;

	public static final int MAX_PLAYERS = 3;
	public static final int MAX_CARDS_IN_HAND = 6;
	public static final int INITIAL_COINS = 50;

	public static final int BRIBE_5 = 5;
	public static final int BRIBE_10 = 10;

	public static final int BREAD_ID = 2;
	public static final int CHICKEN_ID = 3;

	public static final int[] KINGS_BONUS = {20, 15, 15, 10};
	public static final int[] QUEENS_BONUS = {10, 10, 10, 5};
}
