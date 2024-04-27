package edu.austral.dissis.chess.rules;

import edu.austral.dissis.chess.engine.Board;

public interface WinCondition {
  boolean isValidRule(Board context);
}
