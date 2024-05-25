package edu.austral.dissis.online.listeners.main;

import static edu.austral.dissis.common.utils.AuxStaticMethods.buildServer;
import static edu.austral.dissis.common.utils.AuxStaticMethods.setupGame;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.common.utils.enums.GameType;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.Server;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {
  public static GameEngine engine = setupGame(GameType.DEFAULT_CHESS).first();
  public static void main(String[] args) {
    Map<String, Color> colors = new HashMap<>();
    Server server = buildServer(colors, engine);
    server.start();
    while(true) {
      try{
        Thread.sleep(1000);
        if (colors.size() == 1) {
          server.broadcast(new Message<>("InitialState", engine.init()));
          for (Map.Entry<String, Color> entry : colors.entrySet()) {
            server.sendMessage(entry.getKey(), new Message<>("Color", entry.getValue()));
          }
          break;
        }
      }catch(InterruptedException e){
        throw new RuntimeException(e);
      }
    }

  }
}
