package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.chess.validators.OrRestrictionValidator;
import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.AbsRowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.ClearTile;
import edu.austral.dissis.common.piece.movement.restrictions.IsAnEnemy;
import edu.austral.dissis.common.piece.movement.type.PieceMovement;
import edu.austral.dissis.common.utils.move.GameMove;
import org.jetbrains.annotations.NotNull;

public class KnightMovement implements PieceMovement {
  MovementRestrictionValidator validator;

  public KnightMovement() {
    this.validator = getKnightRestrictions();
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getKnightRestrictions() {
    MovementRestrictionValidator left = getLeftValidator();

    MovementRestrictionValidator right = getRightValidator();
    return new AndRestrictionValidator(null, left, right);
  }

  private @NotNull MovementRestrictionValidator getRightValidator() {
    MovementRestrictionValidator clear = new AndRestrictionValidator(new ClearTile());
    MovementRestrictionValidator isEnemy = new AndRestrictionValidator(new IsAnEnemy());
    return new OrRestrictionValidator(null, clear, isEnemy);
  }

  private @NotNull MovementRestrictionValidator getLeftValidator() {
    MovementRestrictionValidator c1 = new AndRestrictionValidator(new AbsColumnDistance(1));
    MovementRestrictionValidator c2 = new AndRestrictionValidator(new AbsColumnDistance(2));
    MovementRestrictionValidator r1 = new AndRestrictionValidator(new AbsRowDistance(1));
    MovementRestrictionValidator r2 = new AndRestrictionValidator(new AbsRowDistance(2));

    MovementRestrictionValidator and1 = new AndRestrictionValidator(null, c1, r2);
    MovementRestrictionValidator and2 = new AndRestrictionValidator(null, c2, r1);
    return new OrRestrictionValidator(null, and2, and1);
  }
}
