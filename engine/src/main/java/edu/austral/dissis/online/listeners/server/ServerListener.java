package edu.austral.dissis.online.listeners.server;

import edu.austral.dissis.online.listeners.messages.TurnListener;

public class ServerListener {
  private final TurnListener turnListener = new TurnListener();

  public TurnListener getTurnListener() {
    return turnListener;
  }
}
