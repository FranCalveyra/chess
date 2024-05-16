package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.engine.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;

public class AndValidator implements PreMovementValidator {
  // TODO: change messages in depending on which branch fails
  // TODO: modify it in order to create a validator per rule (or simplify its structure)
  private final PreMovementValidator left;
  private final PreMovementValidator right;
  private String failureMessage = "";

  public AndValidator(PreMovementValidator leftValidator, PreMovementValidator rightValidator) {
    this.left = leftValidator;
    this.right = rightValidator;
  }

  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    if (!left.isValidRule(move, game)) {
      failureMessage = left.getFailureMessage();
      return false;
    }
    if (!right.isValidRule(move, game)) {
      failureMessage = right.getFailureMessage();
      return false;
    }
    return true;
  }

  @Override
  public String getFailureMessage() {
    return failureMessage;
  }
}
