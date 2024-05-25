package edu.austral.dissis.chess.ui.mains;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.online.listeners.client.ClientConnectionListenerImpl;
import edu.austral.dissis.online.listeners.messages.*;
import edu.austral.dissis.online.listeners.client.SimpleEventListener;
import edu.austral.ingsis.clientserver.Client;
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder;
import edu.austral.ingsis.clientserver.serialization.json.JsonDeserializer;
import edu.austral.ingsis.clientserver.serialization.json.JsonSerializer;
import java.net.InetSocketAddress;
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

  private static Client buildClient(GameView gameView) {
    final Client client =
        new NettyClientBuilder(new JsonDeserializer(), new JsonSerializer())
            .withAddress(new InetSocketAddress("localhost", 8020))
            .withConnectionListener(new ClientConnectionListenerImpl())
            .addMessageListener(
                "InitialState", new TypeReference<>() {}, new InitialStateListener(gameView))
            .addMessageListener(
                "NewGameState", new TypeReference<>() {}, new NewGameStateListener(gameView))
            .addMessageListener(
                "InvalidMove", new TypeReference<>() {}, new InvalidMoveListener(gameView))
            .addMessageListener(
                "GameOver", new TypeReference<>() {}, new GameOverListener(gameView))
            .addMessageListener("Color", new TypeReference<>() {}, new TurnListener())
            .build();
    return client;
  }
}
