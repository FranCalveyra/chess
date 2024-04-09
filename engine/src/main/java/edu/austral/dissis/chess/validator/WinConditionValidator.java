package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.WinCondition;
import edu.austral.dissis.chess.utils.UnallowedMoveException;

import java.util.List;
import java.util.Set;

public class WinConditionValidator {
  // Single Responsibility, removes winning check responsibility from ChessGame
  private final List<WinCondition> winConditions;

  public WinConditionValidator(List<WinCondition> conditions) {
    this.winConditions = conditions;
  }

  public boolean isGameWon(Board context) throws UnallowedMoveException {
    for (WinCondition condition : winConditions) {
      if (condition.isValidRule(context)) {
        return true;
      }
    }
    return false;
  }
}
