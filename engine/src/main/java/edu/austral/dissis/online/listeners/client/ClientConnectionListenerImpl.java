package edu.austral.dissis.online.listeners.client;

import edu.austral.ingsis.clientserver.ClientConnectionListener;

public class ClientConnectionListenerImpl implements ClientConnectionListener {
  // TODO: implement all needed listeners
  // TODO: test all of online package classes in order to not bring coverage down (is already on
  // edge (?))
  @Override
  public void handleConnection() {
    System.out.println("Client connected");
  }

  @Override
  public void handleConnectionClosed() {
    System.out.println("Client disconnected");
  }
}
