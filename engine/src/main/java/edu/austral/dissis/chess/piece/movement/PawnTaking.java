package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.Piece;
import edu.austral.dissis.chess.utils.Position;
import java.awt.Color;

public class PawnTaking implements PieceMovement {

  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    int oldX = oldPos.getColumn();
    int oldY = oldPos.getRow();
    int newX = newPos.getColumn();
    int newY = newPos.getRow();
    int deltaY = newY - oldY;
    boolean colorBasedVerticalMovement =
        context.pieceAt(oldPos).getPieceColour() == Color.BLACK ? deltaY == -1 : deltaY == 1;
    boolean diagonalMove = colorBasedVerticalMovement && Math.abs(newX - oldX) == 1;
    if (!diagonalMove) {
      return false;
    }
    Piece pieceAtNewPos = context.getPiecesAndPositions().get(newPos);
    return pieceAtNewPos != null && pieceAtNewPos.getPieceColour() != context.pieceAt(oldPos).getPieceColour();
  }
}
