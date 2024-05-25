package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.dissis.chess.gui.GameView;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class GameOverListener implements MessageListener<GameOver> {
  private final GameView gameView;

  public GameOverListener(final GameView gameView) {
    this.gameView = gameView;
  }

  @Override
  public void handleMessage(@NotNull Message<GameOver> message) {
    Platform.runLater(() -> gameView.handleMoveResult(message.getPayload()));
  }
}
