package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.piece.movement.type.HorizontalMovement.getBaseValidator;
import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsColumnDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsRowDistance;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;

public class VerticalMovement implements PieceMovement {

  private final int maxDistance;

  public VerticalMovement(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  public VerticalMovement() {
    maxDistance = Integer.MAX_VALUE;
  }

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
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
