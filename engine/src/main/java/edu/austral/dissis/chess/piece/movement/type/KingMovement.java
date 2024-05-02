package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;

public class KingMovement implements PieceMovement {

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    //TODO: delete and replace it with horizontal, diagonal, vertical
    // King should move only in one direction, one tile at a time
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int deltaX = Math.abs(oldPos.getColumn() - newPos.getColumn());
    int deltaY = Math.abs(oldPos.getRow() - newPos.getRow());
    boolean horizontal = deltaX == 1 && deltaY == 0;
    boolean vertical = deltaX == 0 && deltaY == 1;
    boolean diagonal = deltaX == 1 && deltaY == 1;
    if (horizontal) {
      return new HorizontalMovement().isValidMove(move, context);
    } else if (vertical) {
      return new VerticalMovement().isValidMove(move,context);
    } else if (diagonal) {
      return new DiagonalMovement().isValidMove(move,context);
    }
    return false;
  }
}
