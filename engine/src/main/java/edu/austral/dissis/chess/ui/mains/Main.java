package edu.austral.dissis.chess.ui.mains;

import static edu.austral.dissis.chess.gui.AdapterKt.createGameViewFrom;
import static edu.austral.dissis.common.utils.AuxStaticMethods.buildClient;
import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.online.listeners.SimpleEventListener;
import edu.austral.dissis.online.listeners.client.ClientListener;
import edu.austral.ingsis.clientserver.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {

  public static void main(String[] args) {
    Application.launch(ChessApplication.class);
  }

  public static class ChessApplication extends Application {
    private static final Pair<GameEngine, ImageResolver> setup = setupGame(GameType.DEFAULT_CHESS);
    private static final GameEngine gameEngine = setup.first();
    private static final ImageResolver imageResolver = setup.second();
    private static final GameView root = createGameViewFrom(gameEngine, imageResolver);

    private static final ClientListener CLIENT_LISTENER = new ClientListener();
    private static final SimpleEventListener eventListener =
        new SimpleEventListener(gameEngine, root, CLIENT_LISTENER);
    private static final Client client = buildClient(CLIENT_LISTENER);

    @Override
    public void start(Stage stage) {
      stage.setTitle("Chess");
      root.addListener(eventListener);
      stage.setScene(new Scene(root));
      client.connect();
      stage.show();
    }
  }
}
