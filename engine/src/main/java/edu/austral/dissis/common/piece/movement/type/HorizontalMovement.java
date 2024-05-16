package edu.austral.dissis.common.piece.movement.type;

import static edu.austral.dissis.common.utils.enums.MoveType.HORIZONTAL;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsRowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.ClearTile;
import edu.austral.dissis.common.piece.movement.restrictions.validators.IsAnEnemy;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.NoPieceInPath;
import edu.austral.dissis.common.piece.movement.restrictions.validators.OrRestrictionValidator;
import edu.austral.dissis.common.utils.enums.MoveType;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;
import org.jetbrains.annotations.NotNull;

public class HorizontalMovement implements PieceMovement {
  private final int maxDistance;

  public HorizontalMovement(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  public HorizontalMovement() {
    maxDistance = Integer.MAX_VALUE;
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    int colDistance =
        maxDistance != Integer.MAX_VALUE
            ? maxDistance
            : Math.abs(newPos.getColumn() - oldPos.getColumn());
    MovementRestrictionValidator validator = getHorizontalRestrictionValidator(colDistance);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getHorizontalRestrictionValidator(int distance) {
    MovementRestrictionValidator dx = new AbsColumnDistance(distance);
    MovementRestrictionValidator dy = new AbsRowDistance(0);
    return getBaseValidator(dx, dy, HORIZONTAL);
  }

  @NotNull
  protected static MovementRestrictionValidator getBaseValidator(
      MovementRestrictionValidator dx, MovementRestrictionValidator dy, MoveType moveType) {
    MovementRestrictionValidator moveCorrectly = new AndRestrictionValidator(dx, dy);
    MovementRestrictionValidator isEnemy = new IsAnEnemy();
    MovementRestrictionValidator right = new OrRestrictionValidator(new ClearTile(), isEnemy);
    MovementRestrictionValidator left =
        new AndRestrictionValidator(moveCorrectly, new NoPieceInPath(moveType));
    return new AndRestrictionValidator(left, right);
  }
}
