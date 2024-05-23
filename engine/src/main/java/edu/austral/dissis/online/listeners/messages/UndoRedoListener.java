package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.UndoState;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class UndoRedoListener implements MessageListener<UndoState> {
  private UndoState currentState;

  @Override
  public void handleMessage(@NotNull Message<UndoState> message) {
    currentState = message.getPayload();
  }
}
