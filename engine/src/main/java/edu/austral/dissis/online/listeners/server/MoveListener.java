package edu.austral.dissis.online.listeners.server;

import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.online.listeners.main.ServerMain;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import org.jetbrains.annotations.NotNull;


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

  private String getClassName(MoveResult currentState) {
    if(currentState instanceof NewGameState){
      return "NewGameState";
    }
    if (currentState instanceof InvalidMove){
      return "InvalidMove";
    }
    if (currentState instanceof GameOver){
      return "GameOver";
    }
    return "";
  }

  public void setServer(Server server) {
        this.server = server;
    }
}
