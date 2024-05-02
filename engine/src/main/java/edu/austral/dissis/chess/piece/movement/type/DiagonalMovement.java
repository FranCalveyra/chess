package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.piece.movement.type.HorizontalMovement.getBaseValidator;
import static edu.austral.dissis.chess.utils.MoveType.DIAGONAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.*;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.chess.validators.OrRestrictionValidator;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class DiagonalMovement implements PieceMovement {
  private final int maxDistance;
  public DiagonalMovement(int maxDistance) {
    this.maxDistance = maxDistance;
  }
  public DiagonalMovement(){
    maxDistance = Integer.MAX_VALUE;
  }

    @Override
  public boolean isValidMove(ChessMove move, Board context) {
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int distance = maxDistance != Integer.MAX_VALUE ? maxDistance : Math.abs(newPos.getColumn() - oldPos.getColumn());

    MovementRestrictionValidator validator = getDiagonalMovementRestrictions(distance);
    return validator.isValidMove(move,context);
  }

  private MovementRestrictionValidator getDiagonalMovementRestrictions(int distance) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(distance));
    MovementRestrictionValidator dy = new AndRestrictionValidator(new AbsRowDistance(distance));
    return getBaseValidator(dx,dy,DIAGONAL);
  }

}
