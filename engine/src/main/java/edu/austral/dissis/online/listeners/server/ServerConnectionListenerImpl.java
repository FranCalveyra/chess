package edu.austral.dissis.online.listeners.server;

import edu.austral.ingsis.clientserver.ServerConnectionListener;
import org.jetbrains.annotations.NotNull;

public class ServerConnectionListenerImpl implements ServerConnectionListener {
  // TODO: implement all needed listeners
  @Override
  public void handleClientConnection(@NotNull String clientId) {}

  @Override
  public void handleClientConnectionClosed(@NotNull String clientId) {}
}
