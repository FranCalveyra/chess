package edu.austral.dissis.common.rules.premovement.validators;

import edu.austral.dissis.chess.engine.ChessGame;
import edu.austral.dissis.common.rules.premovement.rules.PreMovementRule;
import edu.austral.dissis.common.utils.move.GameMove;
import edu.austral.dissis.common.utils.result.InvalidPlay;
import edu.austral.dissis.common.utils.result.PlayResult;
import edu.austral.dissis.common.utils.result.ValidPlay;

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
  public PlayResult getMoveValidity(GameMove move, ChessGame game) {
    boolean validMove = isValidMove(move, game);
    return !validMove ? new InvalidPlay("") : new ValidPlay();
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

  private boolean getValidity(GameMove move, ChessGame game) {
    if (noRule()) {
      return validityWithoutRule(move, game);
    }
    return validityWithRule(move, game);
  }

  private boolean isValidMove(GameMove move, ChessGame game) {
    if (isLeaf()) {
      boolean cond = rule.isValidRule(move, game);
      if(!cond){
        System.out.println(rule);
      }
      return cond;
    }
    return getValidity(move, game);
  }

  private boolean validityWithRule(GameMove move, ChessGame game) {
    if (noLeftChild() && !noRightChild()) {
      return rule.isValidRule(move, game)
          && right.getMoveValidity(move, game).getClass() != InvalidPlay.class;
    } else if (!noLeftChild() && noRightChild()) {
      return left.getMoveValidity(move, game).getClass() != InvalidPlay.class
          && rule.isValidRule(move, game);
    } else if (isLeaf()) {
      return rule.isValidRule(move, game);
    }
    return left.getMoveValidity(move, game).getClass() != InvalidPlay.class
        && right.getMoveValidity(move, game).getClass() != InvalidPlay.class
        && rule.isValidRule(move, game);
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
        && right.getMoveValidity(move, game).getClass() != InvalidPlay.class;
  }
}
