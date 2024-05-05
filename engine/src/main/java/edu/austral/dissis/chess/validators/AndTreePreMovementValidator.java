package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.chess.rules.PreMovementRule;
import edu.austral.dissis.chess.utils.move.ChessMove;
import edu.austral.dissis.chess.utils.result.ChessMoveResult;
import edu.austral.dissis.chess.utils.result.InvalidMove;
import edu.austral.dissis.chess.utils.result.ValidMove;

public class AndTreePreMovementValidator implements PreMovementValidator {
  // TODO: change messages in depending on which branch fails
  private final AndTreePreMovementValidator left;
  private final AndTreePreMovementValidator right;
  private final PreMovementRule rule;

  public AndTreePreMovementValidator(
      PreMovementRule rule,
      AndTreePreMovementValidator leftValidator,
      AndTreePreMovementValidator rightValidator) {
    this.rule = rule;
    this.left = leftValidator;
    this.right = rightValidator;
  }

  public AndTreePreMovementValidator(PreMovementRule rule) {
    this.rule = rule;
    this.left = null;
    this.right = null;
  }

  @Override
  public ChessMoveResult getMoveValidity(ChessMove move, ChessGame game) {
    boolean validMove = isValidMove(move, game);
    return !validMove ? new InvalidMove("") : new ValidMove();
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
      return validityWithoutRule(move, game);
    }
    return validityWithRule(move, game);
  }

  private boolean isValidMove(ChessMove move, ChessGame game) {
    if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return getValidity(move, game);
  }

  private boolean validityWithRule(ChessMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return rule.isValidRule(move, game)
          && right.getMoveValidity(move, game).getClass() != InvalidMove.class;
    } else if (!noLeftChild() && noRightChild()) {
      return left.getMoveValidity(move, game).getClass() != InvalidMove.class
          && rule.isValidRule(move, game);
    } else if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return left.getMoveValidity(move, game).getClass() != InvalidMove.class
        && right.getMoveValidity(move, game).getClass() != InvalidMove.class
        && rule.isValidRule(move, game);
  }

  private boolean validityWithoutRule(ChessMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return right.getMoveValidity(move, game).getClass() != InvalidMove.class;
    }
    if (noRightChild() && !noLeftChild()) {
      return left.getMoveValidity(move, game).getClass() != InvalidMove.class;
    }
    if (isLeaf()) {
      return false;
    }
    return left.getMoveValidity(move, game).getClass() != InvalidMove.class
        && right.getMoveValidity(move, game).getClass() != InvalidMove.class;
  }
}
