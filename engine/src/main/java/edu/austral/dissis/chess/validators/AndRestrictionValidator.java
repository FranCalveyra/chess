package edu.austral.dissis.chess.validators;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.MovementRestriction;
import edu.austral.dissis.common.utils.move.GameMove;

public class AndRestrictionValidator implements MovementRestrictionValidator {
  private final MovementRestrictionValidator left;
  private final MovementRestrictionValidator right;
  private final MovementRestriction rule;

  public AndRestrictionValidator(
      MovementRestriction rule,
      MovementRestrictionValidator leftValidator,
      MovementRestrictionValidator rightValidator) {
    this.rule = rule;
    this.left = leftValidator;
    this.right = rightValidator;
  }

  public AndRestrictionValidator(MovementRestriction rule) {
    this(rule, null, null);
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    if (isLeaf()) {
      return rule.isValidRestriction(move, context);
    }
    return getValidity(move, context);
  }

  private boolean isLeaf() {
    return left == null && right == null;
  }

  private boolean noRule() {
    return rule == null;
  }

  private boolean noLeftChild() {
    return left == null;
  }

  private boolean noRightChild() {
    return right == null;
  }

  private boolean getValidity(GameMove move, Board board) {
    if (noRule()) {
      return validityWithoutRule(move, board);
    }
    return validityWithRule(move, board);
  }

  private boolean validityWithRule(GameMove move, Board board) {
    if (noLeftChild() && !noRightChild()) {
      return rule.isValidRestriction(move, board) && right.isValidMove(move, board);
    }
    if (!noLeftChild() && noRightChild()) {
      return left.isValidMove(move, board) && rule.isValidRestriction(move, board);
    }
    if (isLeaf()) {
      return rule.isValidRestriction(move, board);
    }
    return left.isValidMove(move, board)
        && right.isValidMove(move, board)
        && rule.isValidRestriction(move, board);
  }

  private boolean validityWithoutRule(GameMove move, Board board) {
    if (noLeftChild() && !noRightChild()) {
      return right.isValidMove(move, board);
    }
    if (noRightChild() && !noLeftChild()) {
      return left.isValidMove(move, board);
    }
    if (isLeaf()) {
      return false;
    }
    return left.isValidMove(move, board) && right.isValidMove(move, board);
  }
}
