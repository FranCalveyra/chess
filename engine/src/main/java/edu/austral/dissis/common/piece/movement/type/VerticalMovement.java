package edu.austral.dissis.common.piece.movement.type;

import static edu.austral.dissis.chess.utils.enums.MoveType.VERTICAL;
import static edu.austral.dissis.common.piece.movement.type.HorizontalMovement.getBaseValidator;

import edu.austral.dissis.common.board.Board;
import edu.austral.dissis.common.piece.movement.restrictions.rules.AbsColumnDistance;
import edu.austral.dissis.common.piece.movement.restrictions.rules.AbsRowDistance;
import edu.austral.dissis.common.piece.movement.restrictions.validators.AndRestrictionValidator;
import edu.austral.dissis.common.piece.movement.restrictions.validators.MovementRestrictionValidator;
import edu.austral.dissis.common.utils.move.BoardPosition;
import edu.austral.dissis.common.utils.move.GameMove;

public class VerticalMovement implements PieceMovement {

  private final int maxDistance;

  public VerticalMovement(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  public VerticalMovement() {
    maxDistance = Integer.MAX_VALUE;
  }

  @Override
  public boolean isValidMove(GameMove move, Board context) {
    BoardPosition oldPos = move.from();
    BoardPosition newPos = move.to();
    int rowDistance =
        maxDistance != Integer.MAX_VALUE
            ? maxDistance
            : Math.abs(newPos.getRow() - oldPos.getRow());
    MovementRestrictionValidator validator = getVerticalValidator(rowDistance);
    return validator.isValidMove(move, context);
  }

  private MovementRestrictionValidator getVerticalValidator(int distance) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsRowDistance(distance));
    MovementRestrictionValidator dy = new AndRestrictionValidator(new AbsColumnDistance(0));
    return getBaseValidator(dx, dy, VERTICAL);
  }
}
