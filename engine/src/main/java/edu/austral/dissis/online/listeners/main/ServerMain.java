package edu.austral.dissis.online.listeners.main;

import edu.austral.dissis.online.listeners.server.ServerListener;
import edu.austral.ingsis.clientserver.Server;

import static edu.austral.dissis.common.utils.AuxStaticMethods.buildServer;

public class ServerMain {
  public static void main(String[] args) {
   Server server = buildServer(new ServerListener());
   server.start();
  }
}
