package edu.austral.dissis.online.listeners.client;

import edu.austral.dissis.chess.gui.*;
import edu.austral.ingsis.clientserver.Client;
import edu.austral.ingsis.clientserver.Message;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class SimpleEventListener implements GameEventListener {
  private final Client client;

  public SimpleEventListener(Client client) {
    this.client = client;
  }

  @Override
  public void handleMove(@NotNull Move move) {
    Platform.runLater(() -> client.send(new Message<>("Move",  move)));
  }

  @Override
  public void handleUndo() {
    Platform.runLater(() -> client.send(new Message<>("UndoRedo", "Do undo")));
  }

  @Override
  public void handleRedo() {
    Platform.runLater(() -> client.send(new Message<>("UndoRedo", "Do redo")));
  }
}
