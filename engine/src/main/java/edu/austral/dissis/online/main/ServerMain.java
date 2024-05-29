package edu.austral.dissis.online.main;

import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.dissis.online.Config;
import edu.austral.dissis.online.listeners.server.MoveListener;
import edu.austral.dissis.online.listeners.server.ServerConnectionListenerImpl;
import edu.austral.dissis.online.listeners.server.UndoRedoListener;
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
    private final Server server = buildServer(colors, engine);

    public ServerApplication() {}

    public void initApplication() {
      server.start();
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
