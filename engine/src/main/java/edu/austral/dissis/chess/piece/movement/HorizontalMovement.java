package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.HORIZONTAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessPosition;
import edu.austral.dissis.chess.validator.PiecePathValidator;

public class HorizontalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    boolean validMove =
        (oldPos.getColumn() != newPos.getColumn()) && (oldPos.getRow() == newPos.getRow());
    if (!validMove) {
      return false;
    }
    return new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, HORIZONTAL);
  }
}
