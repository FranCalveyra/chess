package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsRowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.ClearTile;
import edu.austral.dissis.common.piece.movement.restrictions.validators.IsAnEnemy;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.OrRestrictionValidator;
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
    return new AndRestrictionValidator(getLeftValidator(), getRightValidator());
  }

  private @NotNull MovementRestrictionValidator getRightValidator() {
    MovementRestrictionValidator clear = new ClearTile();
    MovementRestrictionValidator isEnemy = new IsAnEnemy();
    return new OrRestrictionValidator(clear, isEnemy);
  }

  private @NotNull MovementRestrictionValidator getLeftValidator() {
    MovementRestrictionValidator c1 = new AbsColumnDistance(1);
    MovementRestrictionValidator c2 = new AbsColumnDistance(2);
    MovementRestrictionValidator r1 = new AbsRowDistance(1);
    MovementRestrictionValidator r2 = new AbsRowDistance(2);

    MovementRestrictionValidator and1 = new AndRestrictionValidator(c1, r2);
    MovementRestrictionValidator and2 = new AndRestrictionValidator(c2, r1);
    return new OrRestrictionValidator(and2, and1);
  }
}
