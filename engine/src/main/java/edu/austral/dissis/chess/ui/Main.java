package edu.austral.dissis.chess.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.online.listeners.client.ClientConnectionListenerImpl;
import edu.austral.dissis.online.listeners.client.ClientIdListener;
import edu.austral.dissis.online.listeners.client.SimpleEventListener;
import edu.austral.dissis.online.listeners.messages.*;
import edu.austral.dissis.online.utils.Initial;
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

    @Override
    public void start(Stage stage) {
      Initial initial = new Initial("");
      ClientIdListener idListener = new ClientIdListener(initial);
      final Client client = buildClient(root, idListener);
      client.connect();
      GameEventListener eventListener = new SimpleEventListener(client, initial);
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

  private static Client buildClient(GameView gameView, ClientIdListener idListener) {
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
            .addMessageListener("ID", new TypeReference<>() {}, idListener)
            .build();
    return client;
  }
}
