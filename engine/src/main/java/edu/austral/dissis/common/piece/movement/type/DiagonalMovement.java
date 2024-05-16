package edu.austral.dissis.common.piece.movement.type;

import static edu.austral.dissis.common.piece.movement.type.HorizontalMovement.getBaseValidator;
import static edu.austral.dissis.common.utils.enums.MoveType.DIAGONAL;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AbsRowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class DiagonalMovement implements PieceMovement {
  private final int maxDistance;

  public DiagonalMovement(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  public DiagonalMovement() {
    maxDistance = Integer.MAX_VALUE;
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    int distance =
        maxDistance != Integer.MAX_VALUE
            ? maxDistance
            : Math.abs(newPos.getColumn() - oldPos.getColumn());

    MovementRestrictionValidator validator = getDiagonalMovementRestrictions(distance);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getDiagonalMovementRestrictions(int distance) {
    MovementRestrictionValidator dx = new AbsColumnDistance(distance);
    MovementRestrictionValidator dy = new AbsRowDistance(distance);
    return getBaseValidator(dx, dy, DIAGONAL);
  }
}
