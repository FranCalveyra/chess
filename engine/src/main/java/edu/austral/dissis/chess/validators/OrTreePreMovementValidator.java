package edu.austral.dissis.chess.validators;

import static edu.austral.dissis.chess.utils.ChessMoveResult.INVALID_MOVE;
import static edu.austral.dissis.chess.utils.ChessMoveResult.VALID_MOVE;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.rules.PreMovementRule;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessMoveResult;

public class OrTreePreMovementValidator implements PreMovementValidator {
  private final OrTreePreMovementValidator left;
  private final OrTreePreMovementValidator right;
  private final PreMovementRule rule;

  public OrTreePreMovementValidator(
      OrTreePreMovementValidator left, OrTreePreMovementValidator right, PreMovementRule rule) {
    this.left = left;
    this.right = right;
    this.rule = rule;
  }

  @Override
  public ChessMoveResult getMoveValidity(ChessMove move, ChessGame game) {
    return getValidity(move, game) ? VALID_MOVE : INVALID_MOVE;
  }

  private boolean getValidity(ChessMove move, ChessGame game) {
    if (noRule()) {
      return validityWithoutRule(move, game);
    }
    return validityWithRule(move, game);
  }

  private boolean validityWithRule(ChessMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return rule.isValidRule(move, game) || right.getMoveValidity(move, game) == VALID_MOVE;
    } else if (!noLeftChild() && noRightChild()) {
      return left.getMoveValidity(move, game) == VALID_MOVE || rule.isValidRule(move, game);
    } else if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return left.getMoveValidity(move, game) == VALID_MOVE
        || right.getMoveValidity(move, game) == VALID_MOVE
        || rule.isValidRule(move, game);
  }

  private boolean validityWithoutRule(ChessMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return right.getMoveValidity(move, game) == VALID_MOVE;
    }
    if (noRightChild() && !noLeftChild()) {
      return left.getMoveValidity(move, game) == VALID_MOVE;
    }
    if (isLeaf()) {
      return false;
    }
    return left.getMoveValidity(move, game) == VALID_MOVE
        || right.getMoveValidity(move, game) == VALID_MOVE;
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
}
