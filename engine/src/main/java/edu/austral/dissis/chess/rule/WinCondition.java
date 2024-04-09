package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.UnallowedMoveException;

public interface WinCondition {
  boolean isValidRule(Board context) throws UnallowedMoveException;
}
