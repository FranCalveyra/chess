package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

public interface GameRule {
  /**
   *
   * @param context: refers to the game state at checking moment
   * @return if rule is valid at the moment
   */
  boolean isValidRule(Board context);
}
