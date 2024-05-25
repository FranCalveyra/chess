package edu.austral.dissis.online.listeners.server;

import edu.austral.ingsis.clientserver.ServerConnectionListener;
import java.awt.Color;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class ServerConnectionListenerImpl implements ServerConnectionListener {
  // TODO: implement all needed listeners
  // TODO: move map to ServerMain
  private final Map<String, Color> teamColors;
  private int userCount = 0;
  public ServerConnectionListenerImpl(Map<String, Color> teamColors) {
    this.teamColors = teamColors;
  }

  @Override
  public void handleClientConnection(@NotNull String clientId) {
    System.out.println(("User connected with id: " + clientId));
    userCount++;
    if (userCount > 2) {
      return;
    }
    teamColors.put(clientId, userCount == 1 ? Color.WHITE : Color.BLACK);
    System.out.println(teamColors);
  }

  @Override
  public void handleClientConnectionClosed(@NotNull String clientId) {
    System.out.println(("User disconnected with id: " + clientId));
    teamColors.remove(clientId);
    userCount--;
  }
}
