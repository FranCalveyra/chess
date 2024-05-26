package edu.austral.dissis.online.listeners.client;

import edu.austral.dissis.chess.gui.GameEventListener;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.dissis.online.utils.Initial;
import edu.austral.dissis.online.utils.MovePayload;
import edu.austral.ingsis.clientserver.Client;
import edu.austral.ingsis.clientserver.Message;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class SimpleEventListener implements GameEventListener {
  private final Client client;
  private final Initial initial;

  public SimpleEventListener(Client client, Initial init) {
    this.client = client;
    this.initial = init;
  }

  @Override
  public void handleMove(@NotNull Move move) {
    Platform.runLater(
        () -> client.send(new Message<>("Move", new MovePayload(initial.clientId(), move))));
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
