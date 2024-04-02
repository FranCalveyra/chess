package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

public class Stalemate implements TieRule {
  // Should handle "tie"
  @Override
  public boolean isValidRule(Board context) {
    // TODO
    return false;
  }
}
