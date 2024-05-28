package edu.austral.dissis.online.listeners.server;

import static edu.austral.dissis.common.utils.AuxStaticMethods.getClassName;

import edu.austral.dissis.chess.gui.GameEngine;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import edu.austral.ingsis.clientserver.Server;
import org.jetbrains.annotations.NotNull;

public class UndoRedoListener implements MessageListener<String> {
  private Server server;
  private final GameEngine engine;
  private MoveResult currentState;

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
    currentState = engine.undo();
    server.broadcast(new Message<>(getClassName(currentState), currentState));
  }

  private void redo() {
    currentState = engine.redo();
    server.broadcast(new Message<>(getClassName(currentState), currentState));
  }
}
