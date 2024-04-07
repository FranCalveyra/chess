package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.Check;
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
      if (rule instanceof Check) {
        continue; // TODO: change it to not affect during normal game.
        // Solution: make Check a Rule apart from the others, and validate it separately
      }
      // May need to change Check/CheckMate validation inside ChessGame
      // Idea: check if move leaves own king in check
      if (!rule.isValidRule(context)) {
        return true;
      }
    }
    return false;
  }
}
