package edu.austral.dissis.common.piece.movement.restrictions.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public class AndRestrictionValidator implements MovementRestrictionValidator {
  private final MovementRestrictionValidator left;
  private final MovementRestrictionValidator right;

  public AndRestrictionValidator(
      MovementRestrictionValidator leftValidator, MovementRestrictionValidator rightValidator) {
    this.left = leftValidator;
    this.right = rightValidator;
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    if (noLeftChild()) {
      return right.isValidMove(move, context);
    }
    if (noRightChild()) {
      return left.isValidMove(move, context);
    }
    if (!left.isValidMove(move, context)) {
      return false;
    }
    return right.isValidMove(move, context);
  }

  private boolean noLeftChild() {
    return left == null;
  }

  private boolean noRightChild() {
    return right == null;
  }
}
