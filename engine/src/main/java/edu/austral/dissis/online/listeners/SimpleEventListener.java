package edu.austral.dissis.online.listeners;

import edu.austral.dissis.chess.gui.*;
import edu.austral.dissis.online.listeners.client.ClientListener;
import edu.austral.dissis.online.listeners.messages.MoveListener;
import edu.austral.dissis.online.listeners.messages.UndoRedoListener;
import edu.austral.ingsis.clientserver.Message;
import org.jetbrains.annotations.NotNull;

public class SimpleEventListener implements GameEventListener {
  private final MoveListener moveListener;
  private final UndoRedoListener undoRedoListener;
  private final GameEngine gameEngine;
  private final GameView root;

  public SimpleEventListener(
      GameEngine gameEngine, GameView gameView, ClientListener clientListener) {
    this.gameEngine = gameEngine;
    this.root = gameView;
    this.moveListener = clientListener.getMoveListener();
    this.undoRedoListener = clientListener.getUndoRedoListener();
  }

  @Override
  public void handleMove(@NotNull Move move) {
    MoveResult result = gameEngine.applyMove(move);
    moveListener.handleMessage(new Message<>(move.toString(), move));
    root.handleMoveResult(result);
  }

  @Override
  public void handleUndo() {
    var undone = gameEngine.undo();
    UndoState state = undone.getUndoState();
    undoRedoListener.handleMessage(new Message<>(state.toString(), state));
    root.handleMoveResult(undone);
  }

  @Override
  public void handleRedo() {
    var redone = gameEngine.redo();
    UndoState state = redone.getUndoState();
    undoRedoListener.handleMessage(new Message<>(state.toString(), state));
    root.handleMoveResult(redone);
  }
}
