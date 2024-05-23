package edu.austral.dissis.online.listeners.main;

import static edu.austral.dissis.common.utils.AuxStaticMethods.buildServer;

import edu.austral.dissis.online.listeners.server.ServerListener;
import edu.austral.ingsis.clientserver.Server;

public class ServerMain {
  public static void main(String[] args) {
    ServerListener serverListener = new ServerListener();
    Server server = buildServer(serverListener);
    server.start();
  }
}
