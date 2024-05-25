package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.GameView;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

public class NewGameStateListener implements MessageListener<NewGameState> {
  private final GameView gameView;

  public NewGameStateListener(final GameView gameView) {
    this.gameView = gameView;
  }

  @Override
  public void handleMessage(@NotNull Message<NewGameState> message) {
    Platform.runLater(() -> gameView.handleMoveResult(message.getPayload()));
  }
}
