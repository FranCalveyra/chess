package edu.austral.dissis.common.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.rules.winconds.WinCondition;
import java.util.List;

public class WinConditionValidator {
  // Single Responsibility, removes winning check responsibility from ChessGame
  private final List<WinCondition> winConditions;

  public WinConditionValidator(List<WinCondition> conditions) {
    this.winConditions = conditions;
  }

  public boolean isGameWon(Board context) {
    return winConditions.stream().anyMatch(condition -> condition.isValidRule(context));
  }
}
