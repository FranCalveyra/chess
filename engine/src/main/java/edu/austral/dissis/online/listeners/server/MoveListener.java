package edu.austral.dissis.online.listeners.server;

import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.common.utils.Pair;
import edu.austral.dissis.online.listeners.main.ServerMain;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import org.jetbrains.annotations.NotNull;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getClassName;
import static edu.austral.dissis.common.utils.AuxStaticMethods.getPlayerColor;
import static edu.austral.dissis.online.listeners.main.ServerMain.currentState;


public class MoveListener implements MessageListener<Pair<String, Move>> {
  private final GameEngine engine;
  private Server server;

  public MoveListener(GameEngine engine) {
    this.engine = engine;
  }

  @Override
  public void handleMessage(@NotNull Message<Pair<String,Move>> message) {
    if (server == null) {
      return;
    }
      if(currentState instanceof NewGameState && !isPlayersTurn(message)){
        currentState = new InvalidMove("Not your turn");
        server.broadcast(new Message<>(getClassName(currentState), currentState));
      }
      else{
        currentState = engine.applyMove(message.getPayload().second());
        String className = getClassName(currentState);
        server.broadcast(new Message<>(className, currentState));
      }

  }

  private boolean isPlayersTurn(Message<Pair<String, Move>> message) {
    return ((NewGameState) currentState).getCurrentPlayer().equals(getPlayerColor(ServerMain.colors.get(message.getPayload().first())));
  }

  public void setServer(Server server) {
        this.server = server;
    }
}
