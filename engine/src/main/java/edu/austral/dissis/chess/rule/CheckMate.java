package edu.austral.dissis.chess.rule;

import edu.austral.dissis.chess.engine.Board;

public class CheckMate implements GameRule{
  @Override
  public boolean isValidRule(Board context) {
    return false;
  }
}
