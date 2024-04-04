package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

public interface GameRule {
  boolean isValidRule(Board context);
}
