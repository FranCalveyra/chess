package edu.austral.dissis.online.listeners.server;

import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.online.listeners.main.ServerMain;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import org.jetbrains.annotations.NotNull;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getClassName;


public class MoveListener implements MessageListener<Move> {
  private final GameEngine engine;
  private Server server;

  public MoveListener(GameEngine engine) {
    this.engine = engine;
  }

  @Override
  public void handleMessage(@NotNull Message<Move> message) {
    if (server == null) {
      return;
    }
      ServerMain.currentState = engine.applyMove(message.getPayload());
      String className = getClassName(ServerMain.currentState);
      server.broadcast(new Message<>(className, ServerMain.currentState));
  }

  public void setServer(Server server) {
        this.server = server;
    }
}
