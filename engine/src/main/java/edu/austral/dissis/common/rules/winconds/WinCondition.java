package edu.austral.dissis.common.rules.winconds;

import edu.austral.dissis.common.board.Board;
import java.awt.Color;

public interface WinCondition {
  boolean isValidRule(Board context);

  Color getTeam();
}
