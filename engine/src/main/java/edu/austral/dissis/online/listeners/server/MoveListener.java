package edu.austral.dissis.online.listeners.server;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getClassName;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;
import static edu.austral.dissis.online.listeners.main.ServerMain.currentState;

import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.online.listeners.main.ServerMain;
import edu.austral.dissis.online.utils.MovePayload;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import org.jetbrains.annotations.NotNull;

public class MoveListener implements MessageListener<MovePayload> {
  private final GameEngine engine;
  private Server server;

  public MoveListener(GameEngine engine) {
    this.engine = engine;
  }

  @Override
  public void handleMessage(@NotNull Message<MovePayload> message) {
    if (server == null) {
      return;
    }
    boolean notTurn = (currentState instanceof NewGameState)&& !isPlayersTurn(message.getPayload().id());
    boolean invalid = currentState instanceof InvalidMove;
    if (notTurn||invalid) {
      server.broadcast(new Message<>(getClassName(currentState), currentState));
    }
    else {
      currentState = engine.applyMove(message.getPayload().move());
      String className = getClassName(currentState);
      server.broadcast(new Message<>(className, currentState));
    }
  }

  private boolean isPlayersTurn(String id) {
    return ((NewGameState) currentState)
        .getCurrentPlayer()
         == getPlayerColor(ServerMain.colors.get(id));
  }

  public void setServer(Server server) {
    this.server = server;
  }
}
