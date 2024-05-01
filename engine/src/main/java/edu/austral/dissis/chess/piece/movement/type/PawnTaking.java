package edu.austral.dissis.chess.piece.movement.type;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.piece.movement.PieceMovement;
import edu.austral.dissis.chess.utils.ChessMove;
import edu.austral.dissis.chess.utils.ChessPosition;

import java.awt.Color;

public class PawnTaking implements PieceMovement {

  @Override
  public boolean isValidMove(ChessMove move, Board context) {
    //TODO: change to validator
    ChessPosition oldPos = move.from();
    ChessPosition newPos = move.to();
    int deltaY = newPos.getRow() - oldPos.getRow();
    int deltaX = Math.abs(newPos.getColumn() - oldPos.getColumn());
    boolean colorBasedVerticalMovement =
        context.pieceAt(oldPos).getPieceColour() == Color.BLACK ? deltaY == -1 : deltaY == 1;
    boolean diagonalMovement = colorBasedVerticalMovement && deltaX == 1;
    if (!diagonalMovement) {
      return false;
    }
    return context.pieceAt(newPos) != null
        && context.pieceAt(oldPos).getPieceColour() != context.pieceAt(newPos).getPieceColour();
  }
}
