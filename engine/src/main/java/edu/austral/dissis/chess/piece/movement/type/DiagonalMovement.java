package edu.austral.dissis.chess.piece.movement.type;

import static edu.austral.dissis.chess.utils.MoveType.DIAGONAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsColumnDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.AbsRowDistance;
import edu.austral.dissis.chess.piece.movement.restrictions.NoPieceInPath;
import edu.austral.dissis.chess.piece.movement.restrictions.NoTeammateAtDestination;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.AndRestrictionValidator;
import edu.austral.dissis.chess.validators.MovementRestrictionValidator;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class DiagonalMovement implements PieceMovement {


    @Override
  public boolean isValidMove(ChessMove move, Board context) {
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());

    MovementRestrictionValidator validator = getDiagonalMovementRestrictions(deltaX);
    return validator.isValidMove(move,context);
    // They have to move the same amount in both coordinates.
  }

  private MovementRestrictionValidator getDiagonalMovementRestrictions(int distance) {
    MovementRestrictionValidator dx = new AndRestrictionValidator(new AbsColumnDistance(distance));
    MovementRestrictionValidator dy = new AndRestrictionValidator(new AbsRowDistance(distance));
    MovementRestrictionValidator right = new AndRestrictionValidator(new NoTeammateAtDestination());
    MovementRestrictionValidator diagonalMove = new AndRestrictionValidator(new NoPieceInPath(DIAGONAL),dx,dy);
    return new AndRestrictionValidator(null,diagonalMove,right);
  }

}
