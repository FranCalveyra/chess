package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.winConditions.WinCondition;
import java.util.List;

public class WinConditionValidator {
  // Single Responsibility, removes winning check responsibility from ChessGame
  private final List<WinCondition> winConditions;

  public WinConditionValidator(List<WinCondition> conditions) {
    this.winConditions = conditions;
  }

  public boolean isGameWon(Board context) {
    for (WinCondition condition : winConditions) {
      if (condition.isValidRule(context)) {
        return true;
      }
    }
    return false;
  }
}
