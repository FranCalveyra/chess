package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.WinCondition;
import java.awt.*;
import java.util.Set;

public class WinConditionValidator {
  // Single Responsibility, removes winning check responsibility from ChessGame
  private final Set<WinCondition> winConditions;

  public WinConditionValidator(Set<WinCondition> conditions) {
    this.winConditions = conditions;
  }

  public boolean isGameWon(Board context) {
    for (WinCondition condition : winConditions) {
      if (condition.isValidRule(context)) return true;
    }
    return false;
  }
  //  TODO
  //  public Color getWinner(Board context){
  //    if (isGameWon(context)) return Color.BLACK;
  //    return Color.WHITE;
  //  }
}
