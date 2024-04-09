package edu.austral.dissis.chess.validator;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.rule.BorderGameRule;
import edu.austral.dissis.chess.rule.Check;
import edu.austral.dissis.chess.utils.UnallowedMoveException;
import java.util.List;

// May delete this class, quite useless. Check is a rule apart from the others, and win conditions
// are what really matter.
public class RuleValidator {
  private final List<BorderGameRule> rules;

  public RuleValidator(List<BorderGameRule> rules) {
    this.rules = rules;
  }

  public boolean isAnyActive(Board context) throws UnallowedMoveException {
    // O(N)
    for (BorderGameRule rule : rules) {
      if (rule instanceof Check) {
        continue; // TODO: change it to not affect during normal game.
        // Solution: make Check a Rule apart from the others, and validate it separately
      }
      // May need to change Check/CheckMate validation inside ChessGame
      // Idea: check if move leaves own king in check
      if (rule.isValidRule(context)) {
        return true;
      }
    }
    return false;
  }
}
