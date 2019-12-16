package main;

import tema.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Main {
    private Main() {
    }

    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
        	ArrayList<Integer> assetsIds = new ArrayList<>();
        	ArrayList<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                    if (assetsIds.get(assetsIds.size() - 1) > 3) {
                    	assetsIds.set(assetsIds.size() - 1,
                    				  assetsIds.get(assetsIds.size() - 1) - 6);
                    }
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }

                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new GameInput(assetsIds, playerOrder);
        }
    }

    public static void main(final String[] args) {
    	GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        Game game = new Game();
        ArrayList<String> names = gameInput.getPlayerNames();
        if (names.size() == 1) {
        	names.add("wizard");
        }

        game.playGame(gameInput.getAssetIds(), names);
    }
}
