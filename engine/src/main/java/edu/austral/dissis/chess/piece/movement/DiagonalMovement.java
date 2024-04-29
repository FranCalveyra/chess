package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.DIAGONAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validators.PiecePathValidator;

public class DiagonalMovement implements PieceMovement {

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    int deltaY = Math.abs(newPos.getRow() - oldPos.getRow());
    boolean moveCondition = deltaX == deltaY;
    if (!moveCondition) {
      return false;
    }
    boolean isNotTeammate =
        context.pieceAt(newPos) == null
            || context.pieceAt(oldPos).getPieceColour() != context.pieceAt(newPos).getPieceColour();
    return new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, DIAGONAL)
        && isNotTeammate;
    // They have to move the same amount in both coordinates.
  }
}
