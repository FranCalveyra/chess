package edu.austral.dissis.chess.ui.mains;

import static edu.austral.dissis.common.utils.AuxStaticMethods.buildClient;
import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.GameEventListener;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.ImageResolver;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.dissis.online.listeners.main.ServerMain;
import edu.austral.dissis.online.listeners.server.SimpleEventListener;
import edu.austral.ingsis.clientserver.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {

  public static void main(String[] args) {
    Application.launch(ChessApplication.class);
  }

  public static class ChessApplication extends Application {
    private final Pair<GameEngine, ImageResolver> setup = setupGame(GameType.DEFAULT_CHESS);
    private final ImageResolver imageResolver = setup.second();
    private final GameView root = new GameView(imageResolver);
    private final GameEngine gameEngine = ServerMain.engine;

    private final GameEventListener eventListener = new SimpleEventListener(gameEngine, root);
    private final Client client = buildClient(root);

    @Override
    public void start(Stage stage) {
      root.addListener(eventListener);
      stage.setTitle("Chess");
      client.connect();
      stage.setScene(new Scene(root));
      stage.show();
      //End process and disconnect on window close
      stage.setOnCloseRequest(e-> {
        client.closeConnection();
        Platform.exit();});
    }
  }
}
