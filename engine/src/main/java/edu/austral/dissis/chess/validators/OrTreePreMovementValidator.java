package edu.austral.dissis.chess.validators;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.rules.premovement.PreMovementRule;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.InvalidPlay;
import edu.austral.dissis.common.utils.result.PlayResult;
import edu.austral.dissis.common.utils.result.ValidPlay;

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
  public PlayResult getMoveValidity(GameMove move, ChessGame game) {
    return getValidity(move, game) ? new ValidPlay() : new InvalidPlay("some error");
  }

  private boolean getValidity(GameMove move, ChessGame game) {
    if (noRule()) {
      return validityWithoutRule(move, game);
    }
    return validityWithRule(move, game);
  }

  private boolean validityWithRule(GameMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return rule.isValidRule(move, game)
          || right.getMoveValidity(move, game).getClass() != InvalidPlay.class;
    } else if (!noLeftChild() && noRightChild()) {
      return left.getMoveValidity(move, game).getClass() != InvalidPlay.class
          || rule.isValidRule(move, game);
    } else if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return left.getMoveValidity(move, game).getClass() != InvalidPlay.class
        || right.getMoveValidity(move, game).getClass() != InvalidPlay.class
        || rule.isValidRule(move, game);
  }

  private boolean validityWithoutRule(GameMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return right.getMoveValidity(move, game).getClass() != InvalidPlay.class;
    }
    if (noRightChild() && !noLeftChild()) {
      return left.getMoveValidity(move, game).getClass() != InvalidPlay.class;
    }
    if (isLeaf()) {
      return false;
    }
    return left.getMoveValidity(move, game).getClass() != InvalidPlay.class
        || right.getMoveValidity(move, game).getClass() != InvalidPlay.class;
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
