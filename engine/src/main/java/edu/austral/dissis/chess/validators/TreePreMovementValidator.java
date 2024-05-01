package edu.austral.dissis.chess.validators;

import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ChessMoveResult.VALID_MOVE;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.rules.PreMovementRule;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;

public class TreePreMovementValidator implements PreMovementValidator {
  private final TreePreMovementValidator left;
  private final TreePreMovementValidator right;
  private final PreMovementRule rule;

  public TreePreMovementValidator(
      PreMovementRule rule,
      TreePreMovementValidator leftValidator,
      TreePreMovementValidator rightValidator) {
    this.rule = rule;
    this.left = leftValidator;
    this.right = rightValidator;
  }

  public boolean isValidMove(ChessMove move, ChessGame game) {
    if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return getValidity(move, game);
  }

  @Override
  public ChessMoveResult getMoveValidity(ChessMove move, ChessGame game) {
    return !isValidMove(move, game) ? INVALID_MOVE : VALID_MOVE;
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

  private boolean getValidity(ChessMove move, ChessGame game) {
    if (noRule()) {
      if (left == null && right != null) { // TODO: Hide conditions with methods
        return right.getMoveValidity(move, game) == VALID_MOVE;
      }
      if (right == null && left != null) {
        return left.getMoveValidity(move, game) == VALID_MOVE;
      }
      if (isLeaf()) {
        return false;
      }
      return left.getMoveValidity(move, game) == VALID_MOVE
          && right.getMoveValidity(move, game) == VALID_MOVE;
    }
    if (left == null && right != null) {
      return rule.isValidRule(move, game) && right.getMoveValidity(move, game) == VALID_MOVE;
    } else if (left != null && right == null) {
      return left.getMoveValidity(move, game) == VALID_MOVE && rule.isValidRule(move, game);
    } else if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return left.getMoveValidity(move, game) == VALID_MOVE
        && right.getMoveValidity(move, game) == VALID_MOVE
        && rule.isValidRule(move, game);
  }
}
