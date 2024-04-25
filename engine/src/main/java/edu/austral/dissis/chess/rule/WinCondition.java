package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

public interface WinCondition {
  boolean isValidRule(Board context);
}
