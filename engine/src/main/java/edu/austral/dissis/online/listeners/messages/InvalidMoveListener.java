package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class InvalidMoveListener implements MessageListener<InvalidMove> {
  private final GameView gameView;

  public InvalidMoveListener(final GameView gameView) {
    this.gameView = gameView;
  }

  @Override
  public void handleMessage(@NotNull Message<InvalidMove> message) {
    Platform.runLater(() -> gameView.handleMoveResult(message.getPayload()));
  }
}
