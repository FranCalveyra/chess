package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rules.winconds.WinCondition;
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
