package edu.austral.dissis.online.listeners.client;

import edu.austral.dissis.online.listeners.messages.MoveListener;
import edu.austral.dissis.online.listeners.messages.UndoRedoListener;

public class ClientListener {
  private final MoveListener moveListener;
  private final UndoRedoListener undoRedoListener;

  public ClientListener() {
    moveListener = new MoveListener();

    undoRedoListener = new UndoRedoListener();
  }

  public MoveListener getMoveListener() {
    return moveListener;
  }

  public UndoRedoListener getUndoRedoListener() {
    return undoRedoListener;
  }
}
