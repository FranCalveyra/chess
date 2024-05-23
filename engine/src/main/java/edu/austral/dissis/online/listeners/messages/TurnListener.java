package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.dissis.chess.gui.PlayerColor;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class TurnListener implements MessageListener<NewGameState> {
    private PlayerColor currentTurn;

  @Override
  public void handleMessage(@NotNull Message<NewGameState> message) {
      NewGameState gameState = message.getPayload();
      currentTurn = gameState.getCurrentPlayer();
  }
}
