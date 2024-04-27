package edu.austral.dissis.chess.piece.movement;

import static edu.austral.dissis.chess.utils.MoveType.VERTICAL;

import edu.austral.dissis.chess.engine.Board;
import edu.austral.dissis.chess.utils.Position;
import edu.austral.dissis.chess.validator.PiecePathValidator;

public class VerticalMovement implements PieceMovement {
  @Override
  public boolean isValidMove(Position oldPos, Position newPos, Board context) {
    return oldPos.getColumn() == newPos.getColumn()
        && oldPos.getRow() != newPos.getRow()
        && new PiecePathValidator().isNoPieceBetween(oldPos, newPos, context, VERTICAL);
  }
}
