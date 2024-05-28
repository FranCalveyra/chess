package edu.austral.dissis.online.main;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.online.Config;
import edu.austral.dissis.online.listeners.server.MoveListener;
import edu.austral.dissis.online.listeners.server.ServerConnectionListenerImpl;
import edu.austral.dissis.online.listeners.server.UndoRedoListener;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.Server;
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder;
import edu.austral.ingsis.clientserver.serialization.json.JsonDeserializer;
import edu.austral.ingsis.clientserver.serialization.json.JsonSerializer;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {

  public static void main(String[] args) {
    ServerApplication application = new ServerApplication();
    application.initApplication();
  }

  private static class ServerApplication {
    private final BoardGameEngine engine = Config.engine;
    private final Map<String, Color> colors = new HashMap<>();

    ServerApplication() {}

    public void initApplication() {
      Server server = buildServer(colors, engine);
      server.start();
      InitialState initialState = engine.init();
      MoveResult currentState =
          new NewGameState(
              initialState.getPieces(), initialState.getCurrentPlayer(), new UndoState());
      fetchPlayers(colors, server, initialState);
    }
  }

  private static void fetchPlayers(
      Map<String, Color> colors, Server server, InitialState initialState) {
    while (true) {
      try {
        Thread.sleep(1000);
        // move to ServerConnectionListener (deprecated)
        if (colors.size() == 2) {
          server.broadcast(new Message<>("InitialState", initialState));
          colors.forEach((key, value) -> server.sendMessage(key, new Message<>("Color", value)));
          break;
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  // BUILDER
  public static Server buildServer(Map<String, Color> teamColor, BoardGameEngine engine) {
    final MoveListener moveListener = new MoveListener(engine, teamColor);
    final ServerConnectionListenerImpl connectionListener =
        new ServerConnectionListenerImpl(teamColor, engine);
    final UndoRedoListener undoRedoListener = new UndoRedoListener(engine);
    final Server server =
        new NettyServerBuilder(new JsonDeserializer(), new JsonSerializer())
            .withPort(8020)
            .withConnectionListener(connectionListener)
            .addMessageListener("Move", new TypeReference<>() {}, moveListener)
            .addMessageListener("UndoRedo", new TypeReference<>() {}, undoRedoListener)
            .build();
    moveListener.setServer(server);
    connectionListener.setServer(server);
    undoRedoListener.setServer(server);
    return server;
  }
}
