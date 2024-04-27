package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;

public class KnightMovement implements PieceMovement {

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    int deltaX = Math.abs(newX - oldX);
    int deltaY = Math.abs(newY - oldY);
    Piece pieceAtDestination = context.pieceAt(newPos);
    boolean noPieceAt =
        pieceAtDestination == null
            || pieceAtDestination.getPieceColour()
                != context
                    .pieceAt(oldPos)
                    .getPieceColour(); // Before moving, game sets the turn for the next movement.
    // (May change that in case of invalid moves)
    return (deltaX == 2 && deltaY == 1 || deltaX == 1 && deltaY == 2) && noPieceAt;
  }
}
