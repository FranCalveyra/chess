package edu.austral.dissis.online.listeners.server;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;

import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.dissis.common.ui.gameengine.BoardGameEngine;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.Server;
import edu.austral.ingsis.clientserver.ServerConnectionListener;
import java.awt.Color;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class ServerConnectionListenerImpl implements ServerConnectionListener {
  private final Map<String, Color> teamColors;
  private int userCount = 0;
  private Server server;
  private final BoardGameEngine engine;

  public ServerConnectionListenerImpl(Map<String, Color> teamColors, BoardGameEngine engine) {
    this.teamColors = teamColors;
    this.engine = engine;
  }

  @Override
  public void handleClientConnection(@NotNull String clientId) {
    server.sendMessage(clientId, new Message<>("ID", clientId));
    System.out.println(("User connected with id: " + clientId));
    userCount++;
    server.broadcast(new Message<>("InitialState", engine.init()));
    if (userCount > 2) {
      return;
    }
    teamColors.put(clientId, userCount == 1 ? Color.WHITE : Color.BLACK);
  }

  @Override
  public void handleClientConnectionClosed(@NotNull String clientId) {
    System.out.println(("User disconnected with id: " + clientId));
    if (teamColors.containsKey(clientId)) {
      MoveResult gameOver =
          new GameOver(
              getPlayerColor(teamColors.entrySet().stream().toList().getFirst().getValue()));
      server.broadcast(new Message<>("GameOver", gameOver));
    }
    teamColors.remove(clientId);
    userCount--;
  }

  public void setServer(Server server) {
    this.server = server;
  }
}
