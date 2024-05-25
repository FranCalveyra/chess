package edu.austral.dissis.online.listeners.messages;

import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;

public class TurnListener implements MessageListener<Color> {
  private Color team;

  @Override
  public void handleMessage(@NotNull Message<Color> message) {
    team = message.getPayload();
    System.out.println("Team: " + team);
  }
}
