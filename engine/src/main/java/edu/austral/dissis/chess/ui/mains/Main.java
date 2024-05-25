package edu.austral.dissis.chess.ui.mains;

import static edu.austral.dissis.online.listeners.client.ClientMain.buildClient;

import edu.austral.dissis.chess.gui.*;
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
    final ImageResolver imageResolver = new CachedImageResolver(new DefaultImageResolver());
    private final GameView root = new GameView(imageResolver);
    private final Client client = buildClient(root);
    private final GameEventListener eventListener = new SimpleEventListener(client);

    @Override
    public void start(Stage stage) {
      client.connect();
      root.addListener(eventListener);
      stage.setTitle("Chess");
      stage.setScene(new Scene(root));
      stage.show();
      // End process and disconnect on window close
      stage.setOnCloseRequest(
          e -> {
            client.closeConnection();
            Platform.exit();
          });
    }
  }
}
