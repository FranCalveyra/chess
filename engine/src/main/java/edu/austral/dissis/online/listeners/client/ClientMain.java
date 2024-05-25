package edu.austral.dissis.online.listeners.client;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.online.listeners.messages.*;
import edu.austral.ingsis.clientserver.Client;
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder;
import edu.austral.ingsis.clientserver.serialization.json.JsonDeserializer;
import edu.austral.ingsis.clientserver.serialization.json.JsonSerializer;
import java.net.InetSocketAddress;

public class ClientMain {
  // BUILDER
  public static Client buildClient(GameView gameView) {
    final Client client =
        new NettyClientBuilder(new JsonDeserializer(), new JsonSerializer())
            .withAddress(new InetSocketAddress("localhost", 8020))
            .withConnectionListener(new ClientConnectionListenerImpl())
            .addMessageListener(
                "InitialState", new TypeReference<>() {}, new InitialStateListener(gameView))
            .addMessageListener(
                "MoveResult", new TypeReference<>() {}, new NewGameStateListener(gameView))
            .addMessageListener(
                "MoveResult", new TypeReference<>() {}, new InvalidMoveListener(gameView))
            .addMessageListener(
                "MoveResult", new TypeReference<>() {}, new GameOverListener(gameView))
            .addMessageListener("Color", new TypeReference<>() {}, new TurnListener())
            .build();
    return client;
  }
}
