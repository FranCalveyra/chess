package edu.austral.dissis.chess.utils;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.GameRule;
import java.util.List;

public class RuleValidator {
  private final List<GameRule> rules;

  public RuleValidator(List<GameRule> rules) {
    this.rules = rules;
  }

  public boolean isAnyNotValid(Board context) {
    // O(N)
    for (GameRule rule : rules) {
      if (!rule.isValidRule(context)) return true;
    }
    return false;
  }
}
