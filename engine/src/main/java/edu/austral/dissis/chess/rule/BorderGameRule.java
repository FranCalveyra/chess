package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.UnallowedMoveException;

public interface BorderGameRule {
  boolean isValidRule(Board context) throws UnallowedMoveException;
}
