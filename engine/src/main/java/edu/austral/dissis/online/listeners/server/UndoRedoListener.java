package edu.austral.dissis.online.listeners.server;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getClassName;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.online.main.ServerMain;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import org.jetbrains.annotations.NotNull;

public class UndoRedoListener implements MessageListener<String> {
  private Server server;
  private final GameEngine engine;

  public UndoRedoListener(GameEngine engine) {
    this.engine = engine;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  @Override
  public void handleMessage(@NotNull Message<String> message) {
    if (message.getPayload().equals("Do undo")) {
      undo();
    } else {
      redo();
    }
  }

  private void undo() {
    ServerMain.currentState = engine.undo();
    server.broadcast(new Message<>(getClassName(ServerMain.currentState), ServerMain.currentState));
  }

  private void redo() {
    ServerMain.currentState = engine.redo();
    server.broadcast(new Message<>(getClassName(ServerMain.currentState), ServerMain.currentState));
  }
}
