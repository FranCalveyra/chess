package edu.austral.dissis.online.listeners.messages;

import edu.austral.dissis.chess.ui.mains.Main;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;


public class TurnListener implements MessageListener<Color> {
  private Color team;

  @Override
  public void handleMessage(@NotNull Message<Color> message) {
    team = message.getPayload();
    Main.ChessApplication.team = team;
    System.out.println("Team: " + team);
  }

  public Color getTeam() {
    return team;
  }
}
