package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;
import java.awt.Color;

public interface WinCondition {
  boolean isValidRule(Board context);

  Color getTeam();
}
