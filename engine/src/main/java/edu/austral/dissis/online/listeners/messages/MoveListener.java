package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.gui.Move;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class MoveListener implements MessageListener<Move> {
  @Override
  public void handleMessage(@NotNull Message<Move> message) {
    System.out.println(message.getPayload());
  }
}
