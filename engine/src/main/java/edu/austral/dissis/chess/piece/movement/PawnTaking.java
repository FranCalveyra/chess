package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.ChessPosition;
import java.awt.Color;

public class PawnTaking implements PieceMovement {

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    int deltaY = newPos.getRow() - oldPos.getRow();
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    boolean colorBasedVerticalMovement =
        context.pieceAt(oldPos).getPieceColour() == Color.BLACK ? deltaY == -1 : deltaY == 1;
    boolean diagonalMovement = colorBasedVerticalMovement && deltaX == 1;
    if (!diagonalMovement) {
      return false;
    }
    Piece targetPiece = context.pieceAt(newPos);
    return targetPiece != null
        && targetPiece.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
}
