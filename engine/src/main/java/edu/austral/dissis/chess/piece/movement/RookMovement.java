package edu.austral.dissis.chess.piece.movement;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.ChessPosition;

public class RookMovement implements PieceMovement {
  //TO DELETE

  @Override
  public boolean isValidMove(ChessPosition oldPos, ChessPosition newPos, Board context) {
    return new HorizontalMovement().isValidMove(oldPos, newPos, context)
        || new VerticalMovement().isValidMove(oldPos, newPos, context);
  }
}
