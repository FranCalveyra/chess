package edu.austral.dissis.common.piece.movement.restrictions.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.utils.move.GameMove;

public class OrRestrictionValidator implements MovementRestrictionValidator {
  private final MovementRestrictionValidator left;
  private final MovementRestrictionValidator right;

  public OrRestrictionValidator(
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
    return left.isValidMove(move, context) || right.isValidMove(move, context);
  }

  private boolean noLeftChild() {
    return left == null;
  }

  private boolean noRightChild() {
    return right == null;
  }
}
