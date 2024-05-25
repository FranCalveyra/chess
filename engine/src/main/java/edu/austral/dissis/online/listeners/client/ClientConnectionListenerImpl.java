package edu.austral.dissis.online.listeners.client;

import edu.austral.ingsis.clientserver.ClientConnectionListener;

public class ClientConnectionListenerImpl implements ClientConnectionListener {
  @Override
  public void handleConnection() {
    System.out.println("Client connected");
  }

  @Override
  public void handleConnectionClosed() {
    System.out.println("Client disconnected");
  }
}
