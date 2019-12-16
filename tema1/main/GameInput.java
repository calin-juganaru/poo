package main;

import java.util.ArrayList;

public class GameInput {
    private final ArrayList<Integer> mAssetOrder;
    private final ArrayList<String> mPlayersOrder;

    public GameInput() {
        mAssetOrder = null;
        mPlayersOrder = null;
    }

    public GameInput(final ArrayList<Integer> assets, final ArrayList<String> players) {
        mAssetOrder = assets;
        mPlayersOrder = players;
    }

    public final ArrayList<Integer> getAssetIds() {
        return mAssetOrder;
    }

    public final ArrayList<String> getPlayerNames() {
        return mPlayersOrder;
    }

    public final boolean isValidInput() {
        boolean membersInstantiated = mAssetOrder != null && mPlayersOrder != null;
        boolean membersNotEmpty = mAssetOrder.size() > 0 && mPlayersOrder.size() > 0;

        return membersInstantiated && membersNotEmpty;
    }
}
