package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.common.game.BoardGame;
import edu.austral.dissis.common.utils.move.GameMove;

public class AndValidator implements PreMovementValidator {
  private final PreMovementValidator left;
  private final PreMovementValidator right;
  private String failureMessage = "";

  public AndValidator(PreMovementValidator leftValidator, PreMovementValidator rightValidator) {
    this.left = leftValidator;
    this.right = rightValidator;
  }

  @Override
  public boolean isValidRule(GameMove move, BoardGame game) {
    if (noLeftChild()) {
      return right.isValidRule(move, game);
    }
    if (noRightChild()) {
      return left.isValidRule(move, game);
    }
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

  private boolean noLeftChild() {
    return left == null;
  }

  private boolean noRightChild() {
    return right == null;
  }

  @Override
  public String getFailureMessage() {
    return failureMessage;
  }
}
